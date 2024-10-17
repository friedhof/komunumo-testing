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

package org.komunumo.ui.website.home;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import org.jetbrains.annotations.NotNull;
import org.komunumo.data.entity.Event;

public class EventPreview extends Div {

    public EventPreview(@NotNull final Event event) {
        super();
        addClassName("event-preview");

        add(new H3(event.title()));
        add(new H4(event.subtitle()));
        add(new Paragraph(event.description()));

        add(new Paragraph(event.date().toString()));
        add(new Paragraph(event.duration().toString()));
    }

}
