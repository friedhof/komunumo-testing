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

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class Configuration {

    private final Map<String, String> data;

    public Configuration(@NotNull final Map<String, String> data) {
        this.data = data;
    }

    public String getWebsiteBaseUrl() {
        return data.getOrDefault("website.url", "http://localhost:8080");
    }

    public String getWebsiteName() {
        return data.getOrDefault("website.name", "");
    }

    public String getWebsiteContactAddress() {
        return data.getOrDefault("website.contact.address", "");
    }

    public String getWebsiteContactEmail() {
        return data.getOrDefault("website.contact.email", "noreply@localhost");
    }

    public String getWebsiteCopyright() {
        return data.getOrDefault("website.copyright", "");
    }

    public String getWebsiteAboutText() {
        return data.getOrDefault("website.about.text", "");
    }

    public String getWebsiteLogoTemplate() {
        return data.getOrDefault("website.logo.template", "");
    }

    public int getWebsiteMinLogoNumber() {
        return Integer.parseInt(data.getOrDefault("website.logo.min", "0"));
    }

    public int getWebsiteMaxLogoNumber() {
        return Integer.parseInt(data.getOrDefault("website.logo.max", "0"));
    }

}
