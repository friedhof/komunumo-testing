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

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.InvalidApplicationConfigurationException;
import org.jetbrains.annotations.NotNull;
import org.komunumo.configuration.Configuration;

import java.io.Serial;
import java.util.Random;

public class WebsiteLogo extends Image {

    @Serial
    private static final long serialVersionUID = 5073126350713287726L;
    private final String logoUrlTemplate;
    private final int minLogoNumber;
    private final int maxLogoNumber;
    private final boolean randomizeLogo;

    public WebsiteLogo(@NotNull final Configuration configuration) {
        final var websiteConfig = configuration.getWebsite();
        this.logoUrlTemplate = websiteConfig.logoTemplate();
        this.minLogoNumber = websiteConfig.logoMin();
        this.maxLogoNumber = websiteConfig.logoMax();
        this.randomizeLogo = minLogoNumber < maxLogoNumber;

        if (logoUrlTemplate == null || logoUrlTemplate.isBlank()) {
            throw new InvalidApplicationConfigurationException("Missing website logo URL template!");
        }

        if (maxLogoNumber < minLogoNumber) {
            throw new InvalidApplicationConfigurationException("Max website logo number must be higher than min website logo number!");
        }

        setAlt("Website Logo");
        setSrc(getLogoUrl());
        addClassName("website-logo");
    }

    private String getLogoUrl() {
        return randomizeLogo ? getRandomLogoUrl() : logoUrlTemplate;
    }

    private String getRandomLogoUrl() {
        final var randomNumber = new Random().nextInt(maxLogoNumber - minLogoNumber) + minLogoNumber;
        return String.format(logoUrlTemplate, randomNumber);
    }

}
