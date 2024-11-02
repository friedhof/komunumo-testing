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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class WebsiteConfigTest {

    @Autowired
    private Configuration configuration;

    @Test
    void testWebsiteConfig() {
        final var websiteConfig = configuration.getWebsite();
        assertEquals("Test about text", websiteConfig.aboutText());
        assertEquals("Test Association Name", websiteConfig.association());
        assertEquals("Test address", websiteConfig.contactAddress());
        assertEquals("noreply@localhost", websiteConfig.contactEmail());
        assertEquals("Test copyright text", websiteConfig.copyright());
        assertEquals("http://localhost:8080/icons/icon-512x512.png", websiteConfig.logoTemplate());
        assertEquals(0, websiteConfig.logoMin());
        assertEquals(0, websiteConfig.logoMax());
        assertEquals("http://localhost:8080", websiteConfig.url());
    }
}
