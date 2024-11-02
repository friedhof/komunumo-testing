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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.jetbrains.annotations.NotNull;
import org.komunumo.configuration.Configuration;
import org.komunumo.configuration.WebsiteConfig;

public class WebsiteFooter extends Footer {

    private final transient WebsiteConfig websiteConfig;

    public WebsiteFooter(@NotNull final Configuration configuration) {
        this.websiteConfig = configuration.getWebsite();
        setId("website-footer");

        add(
                createAbout(),
                createContact()
        );
    }

    private Component createAbout() {
        final var layout = new HorizontalLayout();
        layout.setId("website-footer-about");

        final var title = new Div(new H2("About"));
        final var about = new Html("<div>%s</div>".formatted(websiteConfig.aboutText()));
        layout.add(new HorizontalLayout(title, about));

        return layout;
    }

    private Component createContact() {
        final var layout = new HorizontalLayout();
        layout.setId("website-footer-contact");

        final var title = new H2("Contact");
        final var association = new Div(new Text(websiteConfig.association()));
        final var address = new Div(new Text(websiteConfig.contactAddress()));
        final var email = createEmail(websiteConfig.contactEmail());
        final var copyright = new Div(new Text(websiteConfig.copyright()));
        layout.add(new HorizontalLayout(title, new Div(association, address, email, copyright)));

        return layout;
    }

    private Component createEmail(@NotNull final String email) {
        final var div = new Div(new Anchor(String.format("mailto:%s", email), email));
        div.addClassName("contact-email");
        return div;
    }

}
