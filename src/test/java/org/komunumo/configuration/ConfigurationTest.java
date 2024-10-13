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
package org.komunumo.configuration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigurationTest {

    private static Configuration configuration;

    @BeforeAll
    static void initializeConfiguration() {
        final Map<String, String> configData = new HashMap<>();
        configData.put("website.url", "https://foobar.test");
        configData.put("website.name", "Foobar");
        configuration = new Configuration(configData);
    }

    @Test
    void testWebsiteBaseUrl() {
        assertEquals("https://foobar.test", configuration.getWebsiteBaseUrl());
    }

    @Test
    void testWebsiteName() {
        assertEquals("Foobar", configuration.getWebsiteName());
    }

}
