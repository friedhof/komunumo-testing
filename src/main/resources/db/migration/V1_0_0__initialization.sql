CREATE TABLE `event` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,

    `title` VARCHAR(255) NOT NULL,
    `subtitle` VARCHAR(255) NOT NULL DEFAULT '',
    `description` MEDIUMTEXT NOT NULL DEFAULT '',

    `date` DATETIME NULL,
    `duration` TIME NULL,

    `location` VARCHAR(255) NOT NULL DEFAULT '',

    PRIMARY KEY (`id`)
);

CREATE INDEX `event_date` ON `event` (`date`);
