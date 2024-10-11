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

import com.vaadin.flow.component.html.Paragraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebsiteLayoutTest {

    @Test
    void testRouterLayoutContent() {
        final var websiteLayout = new WebsiteLayout();
        assertEquals(1, websiteLayout.getComponentCount());

        final var main = websiteLayout.getComponentAt(0);
        assertEquals(0, main.getElement().getChildCount());

        websiteLayout.showRouterLayoutContent(new Paragraph("foo"));
        assertEquals(1, main.getElement().getChildCount());
        assertEquals("foo", main.getElement().getChild(0).getText());

        websiteLayout.showRouterLayoutContent(new Paragraph("bar"));
        assertEquals(1, main.getElement().getChildCount());
        assertEquals("bar", main.getElement().getChild(0).getText());

        websiteLayout.removeRouterLayoutContent(null);
        assertEquals(0, main.getElement().getChildCount());
    }

}
