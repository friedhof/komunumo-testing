/*
 * Komunumo - Open Source Community Manager
 * Copyright (C) Marcus Fihlon and the individual contributors to Komunumo.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.komunumo.scheduler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.komunumo.configuration.Configuration;
import org.komunumo.data.entity.Event;
import org.komunumo.data.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public final class ImportScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportScheduler.class);

    @NotNull private final DatabaseService databaseService;
    @NotNull private final String dbURL;
    @NotNull private final String dbUser;
    @NotNull private final String dbPass;

    public ImportScheduler(@NotNull final Configuration configuration,
                           @NotNull final DatabaseService databaseService) {
        this.databaseService = databaseService;

        final var metanetConfig = configuration.getMetanet();
        this.dbURL = metanetConfig.dbUrl();
        this.dbUser = metanetConfig.dbUser();
        this.dbPass = metanetConfig.dbPassword();
    }

    @Scheduled(initialDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void startImport() {
        if (dbURL.isBlank() || dbUser.isBlank() || dbPass.isBlank()) {
            LOGGER.warn("Metanet database credentials are missing. Skipping import.");
            return;
        }
        LOGGER.info("Starting import...");
        try (var connection = DriverManager.getConnection(dbURL, dbUser, dbPass)) {
            connection.setReadOnly(true);
            importEvents(connection);
        } catch (final SQLException e) {
            LOGGER.error("Error importing data from Java User Group Switzerland: {}", e.getMessage(), e);
        }
    }

    private void importEvents(@NotNull final Connection connection)
            throws SQLException {
        final var counter = new AtomicInteger(0);
        try (var statement = connection.createStatement()) {
            final var result = statement.executeQuery("""
                            SELECT id, ort, room, travel_instructions, datum, startzeit, zeitende, titel, untertitel, agenda, abstract, sichtbar,
                            verantwortung, urldatei, url_webinar, video_id, anm_formular FROM events_neu
                            WHERE sichtbar='ja' OR datum >= '2021-01-01' ORDER BY id""");
            while (result.next()) {
                if (databaseService.getEvent(result.getLong("id")).isEmpty()) {
                    final var event = new Event(
                            result.getLong("id"),
                            getPlainText(getEmptyForNull(result.getString("titel"))),
                            getPlainText(getEmptyForNull(result.getString("untertitel"))),
                            getEmptyForNull(result.getString("abstract")),
                            getDateTime(result.getString("datum"), result.getString("startzeit")),
                            getDuration(result.getString("startzeit"), result.getString("zeitende")),
                            getEmptyForNull(result.getString("ort")));
                    databaseService.storeEvent(event);
                    counter.incrementAndGet();
                    LOGGER.info("Event imported and stored: {}", event);
                }
            }
        }
        LOGGER.info("{} new events imported.", counter.get());
    }

    @NotNull
    private static String getEmptyForNull(@Nullable final String text) {
        return text != null ? text : "";
    }

    @NotNull
    private static String getPlainText(@NotNull final String html) {
        return Jsoup.parse(html).text();
    }

    @Nullable
    private static LocalDateTime getDateTime(@Nullable final String eventDate, @Nullable final String startTime) {
        if (eventDate == null || startTime == null) {
            return null;
        }
        try {
            return LocalDateTime.of(
                    LocalDate.parse(eventDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm:ss.S"))
            );
        } catch (final DateTimeParseException e1) {
            try {
                return LocalDateTime.of(
                        LocalDate.parse(eventDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm:ss"))
                );
            } catch (final DateTimeParseException e2) {
                throw new ImportException(String.format("Can't parse date (%s) and time(%s)", eventDate, startTime), e2);
            }
        }
    }

    @Nullable
    private static Duration getDuration(@NotNull final String startTime, @NotNull final String endTime) {
        final var start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        final var end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        return Duration.between(start, end);
    }

    static class ImportException extends RuntimeException {
        ImportException(@NotNull final String message, @NotNull final Throwable cause) {
            super(message, cause);
        }
    }

}