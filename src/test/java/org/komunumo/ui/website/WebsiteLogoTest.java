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
package org.komunumo.ui.website;

import com.vaadin.flow.server.InvalidApplicationConfigurationException;
import org.junit.jupiter.api.Test;
import org.komunumo.configuration.Configuration;
import org.komunumo.data.service.DatabaseService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WebsiteLogoTest {

    @Test
    void testRandomLogo() {
        final var configuration = mock(Configuration.class);
        when(configuration.getWebsiteLogoTemplate()).thenReturn("test_%02d.svg");
        when(configuration.getWebsiteMinLogoNumber()).thenReturn(1);
        when(configuration.getWebsiteMaxLogoNumber()).thenReturn(5);
        final var databaseService = mock(DatabaseService.class);
        when(databaseService.configuration()).thenReturn(configuration);

        final var websiteLogo = new WebsiteLogo(databaseService);
        assertEquals("website-logo", websiteLogo.getClassName());
        assertEquals("Website Logo", websiteLogo.getAlt().orElseThrow());
        assertTrue(websiteLogo.getSrc().matches("test_\\d{2}\\.svg"));
    }

    @Test
    void testStaticLogo() {
        final var configuration = mock(Configuration.class);
        when(configuration.getWebsiteLogoTemplate()).thenReturn("test.svg");
        when(configuration.getWebsiteMinLogoNumber()).thenReturn(0);
        when(configuration.getWebsiteMaxLogoNumber()).thenReturn(0);
        final var databaseService = mock(DatabaseService.class);
        when(databaseService.configuration()).thenReturn(configuration);

        final var websiteLogo = new WebsiteLogo(databaseService);
        assertEquals("website-logo", websiteLogo.getClassName());
        assertEquals("Website Logo", websiteLogo.getAlt().orElseThrow());
        assertTrue(websiteLogo.getSrc().matches("test\\.svg"));
    }

    @Test
    void testTemplateBlank() {
        final var configuration = mock(Configuration.class);
        when(configuration.getWebsiteLogoTemplate()).thenReturn(" ");
        when(configuration.getWebsiteMinLogoNumber()).thenReturn(0);
        when(configuration.getWebsiteMaxLogoNumber()).thenReturn(0);
        final var databaseService = mock(DatabaseService.class);
        when(databaseService.configuration()).thenReturn(configuration);

        final var exception = assertThrows(InvalidApplicationConfigurationException.class,
                () -> new WebsiteLogo(databaseService));
        assertEquals("Missing website logo URL template!", exception.getMessage());
    }

    @Test
    void testTemplateNull() {
        final var configuration = mock(Configuration.class);
        when(configuration.getWebsiteLogoTemplate()).thenReturn(null);
        when(configuration.getWebsiteMinLogoNumber()).thenReturn(0);
        when(configuration.getWebsiteMaxLogoNumber()).thenReturn(0);
        final var databaseService = mock(DatabaseService.class);
        when(databaseService.configuration()).thenReturn(configuration);

        final var exception = assertThrows(InvalidApplicationConfigurationException.class,
                () -> new WebsiteLogo(databaseService));
        assertEquals("Missing website logo URL template!", exception.getMessage());
    }

}
