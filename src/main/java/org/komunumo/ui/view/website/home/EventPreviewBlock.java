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

package org.komunumo.ui.view.website.home;

import com.vaadin.flow.component.html.Div;
import org.jetbrains.annotations.NotNull;
import org.komunumo.data.entity.Event;
import org.komunumo.data.service.EventService;
import org.komunumo.ui.view.website.ContentBlock;
import org.komunumo.ui.view.website.events.EventPreview;
import org.komunumo.ui.view.website.events.LocationSelector;

public class EventPreviewBlock extends ContentBlock {

    public EventPreviewBlock(@NotNull final EventService eventService) {
        super("Events");
        addClassName("home-view");

        final var events = eventService.upcomingEvents().toList();
        setSubMenu(new LocationSelector(events.stream()
                .map(Event::getLocation)
                .distinct()
                .sorted()
                .toList()));

        final var eventsList = new Div();
        eventsList.addClassName("events-list");
        eventService.upcomingEvents()
                .map(EventPreview::new)
                .forEach(eventsList::add);
        setContent(eventsList);
    }
}
