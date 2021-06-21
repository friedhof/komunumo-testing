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

package org.komunumo.util;

import com.vaadin.componentfactory.EnhancedDatePicker;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

// TODO würde ich in der Komponente implementieren
public class LocalizedEnhancedDatePickerI18NProvider extends EnhancedDatePicker.DatePickerI18n {

    public LocalizedEnhancedDatePickerI18NProvider() {
        this(Locale.getDefault());
    }

    public LocalizedEnhancedDatePickerI18NProvider(@NotNull final Locale locale) {
        final var symbols = new DateFormatSymbols(locale);
        this.setMonthNames(Arrays.asList(symbols.getMonths()));
        this.setFirstDayOfWeek(Calendar.getInstance(locale).getFirstDayOfWeek() == Calendar.MONDAY ? 1 : 0);
        this.setWeekdays(Arrays.stream(symbols.getWeekdays()).filter(s -> !s.isEmpty()).collect(Collectors.toList()));
        this.setWeekdaysShort(Arrays.stream(symbols.getShortWeekdays()).filter(s -> !s.isEmpty()).collect(Collectors.toList()));
    }
}
