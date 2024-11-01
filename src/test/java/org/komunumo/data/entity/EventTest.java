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
package org.komunumo.data.entity;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventTest {

    @Test
    void testEvent() {
        final var testEvent = new Event(1L, "Foobar", "This is a test", "I am a description.",
                LocalDateTime.of(2024, 10, 16, 18, 15), Duration.ofHours(1));

        assertEquals(1L, testEvent.id());
        assertEquals("Foobar", testEvent.title());
        assertEquals("This is a test", testEvent.subtitle());
        assertEquals("I am a description.", testEvent.description());
        assertEquals(LocalDateTime.of(2024, 10, 16, 18, 15), testEvent.date());
        assertEquals(Duration.ofHours(1), testEvent.duration());
    }

    @Test
    void isUpcomingWithNulls() {
        assertFalse(new Event(1L, "Foobar", "This is a test", "I am a description.",
                null, null).isUpcoming());
        assertFalse(new Event(1L, "Foobar", "This is a test", "I am a description.",
                null, Duration.ofHours(1)).isUpcoming());
        assertFalse(new Event(1L, "Foobar", "This is a test", "I am a description.",
                LocalDateTime.of(2099, 1, 1, 18, 15), null).isUpcoming());
    }

    @Test
    void isUpcomingFalse() {
        assertFalse(new Event(1L, "Foobar", "This is a test", "I am a description.",
                LocalDateTime.of(2000, 1, 1, 18, 15), Duration.ofHours(1)).isUpcoming());
        assertFalse(new Event(1L, "Foobar", "This is a test", "I am a description.",
                LocalDateTime.now().minusMinutes(61), Duration.ofHours(1)).isUpcoming());
    }

    @Test
    void isUpcomingTrue() {
        assertTrue(new Event(1L, "Foobar", "This is a test", "I am a description.",
                LocalDateTime.of(2099, 1, 1, 18, 15), Duration.ofHours(1)).isUpcoming());
        assertTrue(new Event(1L, "Foobar", "This is a test", "I am a description.",
                LocalDateTime.now(), Duration.ofHours(1)).isUpcoming());
        assertTrue(new Event(1L, "Foobar", "This is a test", "I am a description.",
                LocalDateTime.now().minusMinutes(59), Duration.ofHours(1)).isUpcoming());
    }

}
