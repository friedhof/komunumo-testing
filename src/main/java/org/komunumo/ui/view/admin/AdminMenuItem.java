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

package org.komunumo.ui.view.admin;

import com.vaadin.flow.component.Component;
import org.jetbrains.annotations.NotNull;

public class AdminMenuItem {

    private final String title;
    private final Class<? extends Component> navigationTarget;
    private final boolean newSection;

    public AdminMenuItem(@NotNull final String title,
                         @NotNull final Class<? extends Component> navigationTarget,
                         final boolean newSection) {
        this.title = title;
        this.navigationTarget = navigationTarget;
        this.newSection = newSection;
    }

    public String getTitle() {
        return title;
    }

    public Class<? extends Component> getNavigationTarget() {
        return navigationTarget;
    }

    public boolean isNewSection() {
        return newSection;
    }
}
