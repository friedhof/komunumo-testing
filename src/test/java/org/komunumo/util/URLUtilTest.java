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

package org.komunumo.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings({ "HttpUrlsUsage", "PMD.AvoidDuplicateLiterals" })
class URLUtilTest {

    @ParameterizedTest
    @CsvSource({
            "'',''",
            "There is no link in this text!,''",
            "Go to http://komunumo.org and try it out!,http://komunumo.org",
            "Go to https://komunumo.org and try it out!,https://komunumo.org",
            "Go to http://komunumo.org/ and try it out!,http://komunumo.org/",
            "Go to https://komunumo.org/ and try it out!,https://komunumo.org/",
            "Go to https://komunumo.org/test and try it out!,https://komunumo.org/test",
            "Go to https://komunumo.org/test/ and try it out!,https://komunumo.org/test/",
            "Go to https://komunumo.org/test.html and try it out!,https://komunumo.org/test.html",
            "Go to https://komunumo.org/test.php and try it out!,https://komunumo.org/test.php",
            "Go to https://komunumo.org/test.pdf and try it out!,https://komunumo.org/test.pdf",
            "Go to \"https://komunumo.org/test.pdf\" and try it out!,https://komunumo.org/test.pdf",
            "Go to 'https://komunumo.org/test.pdf' and try it out!,https://komunumo.org/test.pdf"
    })
    void extractLink(final String text, final String expected) {
        assertEquals(expected, URLUtil.extractLink(text));
    }

    @ParameterizedTest
    @CsvSource({
            "Hans im Glück,hans-im-glueck",
            "I have_a-really\\Amazing§§Idea,i-have_a-reallyamazingidea",
            "Count from 1 to 3,count-from-1-to-3"
    })
    void createReadableUrl(final String text, final String expected) {
        assertEquals(expected, URLUtil.createReadableUrl(text));
    }

    @ParameterizedTest
    @CsvSource({
            "'',''",
            "komunumo.org,komunumo.org",
            "www.komunumo.org,komunumo.org",
            "http://komunumo.org,komunumo.org",
            "http://komunumo.org/,komunumo.org",
            "http://www.komunumo.org/,komunumo.org",
            "https://komunumo.org/,komunumo.org",
            "https://komunumo.org/index.html,komunumo.org",
            "https://komunumo.org/subdir,komunumo.org",
            "https://komunumo.org/subdir/,komunumo.org",
            "https://komunumo.org/subdir/index.html,komunumo.org"
    })
    void getDomainFromUrl(final String url, final String expected) {
        assertEquals(expected, URLUtil.getDomainFromUrl(url));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "", "   ",
            "test", "http://non-existing.domain/",
            "komunumo.org", "www.komunumo.org",
            "http://", "https://" })
    void isValidFalse(final String url) {
        assertFalse(URLUtil.isValid(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "http://komunumo.org",
            "http://komunumo.org/",
            "http://www.komunumo.org",
            "http://www.komunumo.org/",
            "https://komunumo.org",
            "https://komunumo.org/",
            "https://www.komunumo.org",
            "https://www.komunumo.org/"
    })
    void isValidTrue(final String url) {
        assertTrue(URLUtil.isValid(url));
    }

    @Test
    @SuppressWarnings("PMD.AvoidAccessibilityAlteration") // this is exactly what we want to test
    void privateConstructorWithException() {
        final var cause = assertThrows(InvocationTargetException.class, () -> {
            Constructor<URLUtil> constructor = URLUtil.class.getDeclaredConstructor();
            if (Modifier.isPrivate(constructor.getModifiers())) {
                constructor.setAccessible(true);
                constructor.newInstance();
            }
        }).getCause();
        assertInstanceOf(IllegalStateException.class, cause);
        assertEquals("Utility class", cause.getMessage());
    }

}
