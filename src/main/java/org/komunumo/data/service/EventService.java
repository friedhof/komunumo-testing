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
import org.komunumo.data.db.tables.records.EventRecord;
import org.komunumo.data.entity.Event;
import org.komunumo.data.service.getter.DSLContextGetter;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static org.komunumo.data.db.tables.Event.EVENT;

interface EventService extends DSLContextGetter {

    default void storeEvent(@NotNull Event event) {
        final EventRecord eventRecord;
        if (event.id() == 0) {
            eventRecord = dsl().newRecord(EVENT);
        } else {
            final var result = dsl().fetchOne(EVENT, EVENT.ID.eq(event.id()));
            eventRecord = result != null ? result : dsl().newRecord(EVENT);
        }
        eventRecord.from(event);
        eventRecord.store();
    }

    @NotNull
    default Optional<Event> getEvent(@NotNull final Long id) {
        return dsl().selectFrom(EVENT)
                .where(EVENT.ID.eq(id))
                .fetchOptionalInto(Event.class);
    }

    @NotNull
    default Stream<Event> upcomingEvents() {
        return dsl().selectFrom(EVENT)
                .where(EVENT.DATE.greaterOrEqual(LocalDateTime.now().withHour(0).withMinute(0)))
                .orderBy(EVENT.DATE.asc())
                .fetch()
                .stream()
                .map((eventRecord -> eventRecord.into(Event.class)))
                .filter(Event::isUpcoming);
    }

    default boolean deleteEvent(@NotNull final Event event) {
        return dsl().delete(EVENT).where(EVENT.ID.eq(event.id())).execute() > 0;
    }

}
