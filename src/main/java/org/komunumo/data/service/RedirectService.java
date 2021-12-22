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

package org.komunumo.data.service;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.komunumo.data.db.tables.records.RedirectRecord;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

import static org.komunumo.data.db.tables.Redirect.REDIRECT;

@Service
@SuppressWarnings("ClassCanBeRecord")
public class RedirectService {

    private final DSLContext dsl;

    public RedirectService(@NotNull final DSLContext dsl) {
        this.dsl = dsl;
    }

    public @NotNull Stream<RedirectRecord> getAllRedirects() {
        return dsl.selectFrom(REDIRECT).stream();
    }

    public void addRedirect(@NotNull final String oldUrl, @NotNull final String newUrl) {
        dsl.insertInto(REDIRECT, REDIRECT.OLD_URL, REDIRECT.NEW_URL)
                .values(oldUrl, newUrl)
                .onDuplicateKeyIgnore()
                .execute();
    }

}