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
package org.komunumo.data.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.LocalDateTime;

public record Event(@NotNull Long id,
                    @NotNull String title,
                    @NotNull String subtitle,
                    @NotNull String description,
                    @Nullable LocalDateTime date,
                    @Nullable Duration duration,
                    @NotNull String location) {

    public boolean isUpcoming() {
        if (date() == null || duration() == null) {
            return false;
        }
        final var endDate = date().plus(duration());
        return endDate.isAfter(LocalDateTime.now());
    }

}
