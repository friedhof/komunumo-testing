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

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import org.jetbrains.annotations.NotNull;
import org.komunumo.data.service.DatabaseService;

public class WebsiteHeader extends Header {

    public WebsiteHeader(@NotNull final DatabaseService databaseService) {
        setId("website-header");
        final var configuration = databaseService.configuration();
        add(
                new Anchor(configuration.getWebsiteBaseUrl(),
                new H1(configuration.getWebsiteName()))
        );
    }

}
