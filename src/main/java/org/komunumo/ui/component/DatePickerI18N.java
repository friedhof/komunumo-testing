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

package org.komunumo.ui.component;

import com.vaadin.componentfactory.EnhancedDatePicker;
import com.vaadin.flow.component.UI;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.stream.Collectors;

class DatePickerI18N extends EnhancedDatePicker.DatePickerI18n {

    public DatePickerI18N() {
        this(UI.getCurrent().getSession().getBrowser().getLocale());
    }

    public DatePickerI18N(@NotNull final Locale locale) {
        final var symbols = new DateFormatSymbols(locale);
        this.setMonthNames(Arrays.asList(symbols.getMonths()));
        this.setFirstDayOfWeek(Calendar.getInstance(locale).getFirstDayOfWeek());
        this.setWeekdays(Arrays.stream(symbols.getWeekdays()).filter(s -> !s.isEmpty()).collect(Collectors.toList()));
        this.setWeekdaysShort(Arrays.stream(symbols.getShortWeekdays()).filter(s -> !s.isEmpty()).collect(Collectors.toList()));
    }
}
