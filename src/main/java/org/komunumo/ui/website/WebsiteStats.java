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

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;

import static org.komunumo.util.FormatterUtil.formatNumber;

public class WebsiteStats extends Div {

    public WebsiteStats() {
        final var stats = new Stats(8, "bits are a byte");
        final var number = new Span(new Text(formatNumber(stats.number())));
        number.addClassName("number");
        final var text = new Span(new Text(stats.text()));
        text.addClassName("text");

        add(number, text);
        addClassName("website-stats");
    }

    private record Stats(int number, String text) { }

}
