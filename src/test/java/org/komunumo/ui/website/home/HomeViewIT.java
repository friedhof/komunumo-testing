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

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.komunumo.data.service.DatabaseService;
import org.komunumo.ui.KaribuTestBase;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.github.mvysny.kaributesting.v10.LocatorJ._find;
import static com.github.mvysny.kaributesting.v10.LocatorJ._get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.komunumo.data.db.Tables.EVENT;

class HomeViewIT extends KaribuTestBase {

    @Autowired
    private DSLContext dsl;

    @Autowired
    private DatabaseService databaseService;

    @Test
    void homeViewTest() {
        dsl.insertInto(EVENT, EVENT.ID, EVENT.TITLE, EVENT.SUBTITLE, EVENT.DESCRIPTION,
                        EVENT.DATE, EVENT.DURATION, EVENT.LOCATION)
                .values(1L, "Foobar 1", "This is a test", "This is event number one.",
                        LocalDateTime.of(2099, 1, 1, 18, 15), Duration.ofMinutes(90), "Terra")
                .values(2L, "Foobar 2", "This is a test", "This is event number two.",
                        LocalDateTime.of(2099, 1, 1, 18, 15), null, "Terra")
                .values(3L, "Foobar 3", "This is a test", "This is event number three.",
                        null, Duration.ofMinutes(90), "Terra")
                .values(4L, "Foobar 4", "This is a test", "This is event number four.",
                        null, null, "Terra")
                .execute();

        final var events = databaseService.upcomingEvents().toList();
        System.out.println(events);

        UI.getCurrent().navigate(HomeView.class);
        UI.getCurrent().getPage().reload();

        final var title = _get(H2.class, spec -> spec.withText("Home")).getText();
        assertEquals("Home", title);

        final var headings = _find(H3.class);
        assertEquals(1, headings.size());
        assertEquals("Foobar 1", headings.getFirst().getText());
    }

}
