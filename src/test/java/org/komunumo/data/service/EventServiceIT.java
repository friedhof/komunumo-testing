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

import org.junit.jupiter.api.Test;
import org.komunumo.data.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EventServiceIT {

    @Autowired
    private EventService eventService;

    @Test
    void eventService() {
        final var previousEvent = new Event(1L, "Foobar 1", "This is a previous event", "This is event number one.",
                LocalDateTime.of(2020, 1, 1, 18, 15), Duration.ofMinutes(90), "Terra");
        final var runningEvent = new Event(2L, "Foobar 2", "This is a running event", "This is event number two.",
                LocalDateTime.now().withNano(0).minusMinutes(5), Duration.ofMinutes(90), "Terra");
        final var upcomingEvent = new Event(3L, "Foobar 3", "This is a upcoming event", "This is event number three.",
                LocalDateTime.of(2099, 1, 1, 18, 15), Duration.ofMinutes(90), "Terra");

        eventService.storeEvent(previousEvent);
        eventService.storeEvent(runningEvent);
        eventService.storeEvent(upcomingEvent);

        assertEquals(previousEvent, eventService.getEvent(1L).orElseThrow());
        assertEquals(runningEvent, eventService.getEvent(2L).orElseThrow());
        assertEquals(upcomingEvent, eventService.getEvent(3L).orElseThrow());

        final var upcomingEvents = eventService.upcomingEvents().toList();
        assertEquals(2, upcomingEvents.size());
        assertEquals(runningEvent, upcomingEvents.getFirst());
        assertEquals(upcomingEvent, upcomingEvents.get(1));
    }

}
