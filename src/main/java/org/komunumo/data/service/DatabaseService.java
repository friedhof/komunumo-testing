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

package org.komunumo.data.service;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.komunumo.configuration.Configuration;
import org.komunumo.data.service.getter.ConfigurationGetter;
import org.komunumo.data.service.getter.DSLContextGetter;
import org.komunumo.data.service.getter.MailSenderGetter;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;

import static org.komunumo.data.db.tables.Configuration.CONFIGURATION;

@Service
public class DatabaseService implements DSLContextGetter, ConfigurationGetter, MailSenderGetter, EventService, EventKeywordService,
        EventOrganizerService, EventSpeakerService, FaqService, KeywordService, LocationColorService, MemberService, NewsService, RedirectService,
        RegistrationService, SpeakerService, SponsorService, StatisticService, SubscriptionService {

    private final Configuration configuration;
    private final DSLContext dsl;
    private final MailSender mailSender;

    public DatabaseService(@NotNull final DSLContext dsl,
                           @NotNull final MailSender mailSender) {
        this.dsl = dsl;
        this.mailSender = mailSender;

        final var configurationData = new HashMap<String, String>();
        dsl().selectFrom(CONFIGURATION)
                .forEach(configurationRecord -> configurationData.put(configurationRecord.getKey(), configurationRecord.getValue()));
        configuration = new Configuration(Collections.unmodifiableMap(configurationData));
    }

    @Override
    public Configuration configuration() {
        return configuration;
    }

    @Override
    public DSLContext dsl() {
        return dsl;
    }

    @Override
    public MailSender mailSender() {
        return mailSender;
    }

}
