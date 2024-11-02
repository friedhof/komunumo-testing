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
import org.komunumo.configuration.WebsiteConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WebsiteLogoTest {

    @Test
    void testRandomLogo() {
        final var configuration = mock(Configuration.class);
        final var websiteConfig = mock(WebsiteConfig.class);
        when(configuration.getWebsite()).thenReturn(websiteConfig);
        when(websiteConfig.logoTemplate()).thenReturn("test_%02d.svg");
        when(websiteConfig.logoMin()).thenReturn(1);
        when(websiteConfig.logoMax()).thenReturn(5);

        final var websiteLogo = new WebsiteLogo(configuration);
        assertEquals("website-logo", websiteLogo.getClassName());
        assertEquals("Website Logo", websiteLogo.getAlt().orElseThrow());
        assertTrue(websiteLogo.getSrc().matches("test_\\d{2}\\.svg"));
    }

    @Test
    void testStaticLogo() {
        final var configuration = mock(Configuration.class);
        final var websiteConfig = mock(WebsiteConfig.class);
        when(configuration.getWebsite()).thenReturn(websiteConfig);
        when(websiteConfig.logoTemplate()).thenReturn("test.svg");
        when(websiteConfig.logoMin()).thenReturn(0);
        when(websiteConfig.logoMax()).thenReturn(0);

        final var websiteLogo = new WebsiteLogo(configuration);
        assertEquals("website-logo", websiteLogo.getClassName());
        assertEquals("Website Logo", websiteLogo.getAlt().orElseThrow());
        assertTrue(websiteLogo.getSrc().matches("test\\.svg"));
    }

    @Test
    void testTemplateBlank() {
        final var configuration = mock(Configuration.class);
        final var websiteConfig = mock(WebsiteConfig.class);
        when(configuration.getWebsite()).thenReturn(websiteConfig);
        when(websiteConfig.logoTemplate()).thenReturn(" ");
        when(websiteConfig.logoMin()).thenReturn(0);
        when(websiteConfig.logoMax()).thenReturn(0);

        final var exception = assertThrows(InvalidApplicationConfigurationException.class,
                () -> new WebsiteLogo(configuration));
        assertEquals("Missing website logo URL template!", exception.getMessage());
    }

    @Test
    void testTemplateNull() {
        final var configuration = mock(Configuration.class);
        final var websiteConfig = mock(WebsiteConfig.class);
        when(configuration.getWebsite()).thenReturn(websiteConfig);
        when(websiteConfig.logoTemplate()).thenReturn(null);
        when(websiteConfig.logoMin()).thenReturn(0);
        when(websiteConfig.logoMax()).thenReturn(0);

        final var exception = assertThrows(InvalidApplicationConfigurationException.class,
                () -> new WebsiteLogo(configuration));
        assertEquals("Missing website logo URL template!", exception.getMessage());
    }

    @Test
    void testMaxLogoNumberLowerThanMinLogoNumber() {
        final var configuration = mock(Configuration.class);
        final var websiteConfig = mock(WebsiteConfig.class);
        when(configuration.getWebsite()).thenReturn(websiteConfig);
        when(websiteConfig.logoTemplate()).thenReturn("test_%02d.svg");
        when(websiteConfig.logoMin()).thenReturn(1);
        when(websiteConfig.logoMax()).thenReturn(0);

        final var exception = assertThrows(InvalidApplicationConfigurationException.class,
                () -> new WebsiteLogo(configuration));
        assertEquals("Max website logo number must be higher than min website logo number!", exception.getMessage());
    }

}
