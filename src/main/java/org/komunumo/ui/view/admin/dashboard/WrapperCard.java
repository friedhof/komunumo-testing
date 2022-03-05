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

package org.komunumo.ui.view.admin.dashboard;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

import java.io.Serial;

import org.jetbrains.annotations.NotNull;

public class WrapperCard extends Div {

    @Serial
    private static final long serialVersionUID = -7214646065995668880L;

    public WrapperCard(@NotNull final String className,
                       @NotNull final Component[] components,
                       @NotNull final String... classes) {
        addClassName(className);

        final var card = new Div();
        card.addClassNames(classes);
        card.add(components);

        add(card);
    }

}
