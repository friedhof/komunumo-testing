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

package org.komunumo.views.admin.speakers;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import org.jetbrains.annotations.NotNull;
import org.komunumo.data.entity.Speaker;
import org.komunumo.data.service.SpeakerService;

public class SpeakerDialog extends Dialog {

    private final Focusable<? extends Component> focusField;

    public SpeakerDialog(@NotNull final Speaker speaker,
                         @NotNull final SpeakerService speakerService) {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(false);

        final var title = new H2(speaker.getId() == null ? "New speaker" : "Edit speaker");
        title.getStyle().set("margin-top", "0");

        final var firstNameField = new TextField("First name");
        firstNameField.setRequiredIndicatorVisible(true);
        firstNameField.setValue(speaker.getFirstName());

        final var lastNameField = new TextField("Last name");
        lastNameField.setRequiredIndicatorVisible(true);
        lastNameField.setValue(speaker.getLastName());

        final var companyField = new TextField("Company");
        companyField.setValue(speaker.getCompany());

        final var emailField = new EmailField("Email");
        emailField.setValue(speaker.getEmail());

        final var twitterField = new TextField("Twitter");
        twitterField.setValue(speaker.getTwitter());

        final var form = new FormLayout();
        form.add(firstNameField, lastNameField, companyField,
                emailField, twitterField);

        final var saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(event -> {
            if (firstNameField.getValue().isBlank()) {
                Notification.show("Please enter the first name of the speaker!");
            } else if (lastNameField.getValue().isBlank()) {
                Notification.show("Please enter the last name of the speaker!");
            } else {
                saveButton.setEnabled(false);
                speaker.setFirstName(firstNameField.getValue())
                        .setLastName(lastNameField.getValue())
                        .setCompany(companyField.getValue())
                        .setEmail(emailField.getValue())
                        .setTwitter(twitterField.getValue());
                speakerService.store(speaker);

                Notification.show("Speaker saved.");
                close();
            }
        });
        saveButton.addClickShortcut(Key.ENTER, KeyModifier.CONTROL);
        final var cancelButton = new Button("Cancel", event -> close());
        final var buttonBar = new HorizontalLayout(saveButton, cancelButton);

        add(title, form, buttonBar);

        focusField = firstNameField;
    }

    @Override
    public void open() {
        super.open();
        focusField.focus();
    }
}
