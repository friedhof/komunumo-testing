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

import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.komunumo.configuration.Configuration;
import org.komunumo.data.db.enums.NewsletterRegistrationStatus;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.komunumo.data.db.tables.NewsletterRegistration.NEWSLETTER_REGISTRATION;

@Service
@SuppressWarnings("ClassCanBeRecord")
public class NewsletterService {

    private final DSLContext dsl;
    private final Configuration configuration;
    private final MailSender mailSender;

    public NewsletterService(@NotNull final DSLContext dsl,
                             @NotNull final Configuration configuration,
                             @NotNull final MailSender mailSender) {
        this.dsl = dsl;
        this.configuration = configuration;
        this.mailSender = mailSender;
    }

    public void addRegistration(@NotNull final String emailAddress) {
        final var registration = dsl.newRecord(NEWSLETTER_REGISTRATION);

        final var validationCode = RandomStringUtils.randomAlphabetic(8);

        registration.setEmail(emailAddress);
        registration.setSubscriptionDate(LocalDateTime.now());
        registration.setStatus(NewsletterRegistrationStatus.PENDING);
        registration.setValidationCode(validationCode);
        registration.store();

        final var message = new SimpleMailMessage();
        message.setTo(emailAddress);
        message.setFrom(configuration.getEmail().getAddress());
        message.setSubject("Validate your newsletter subscription");
        message.setText("Please enter the following validation code: " + validationCode);
        mailSender.send(message);
    }

}
