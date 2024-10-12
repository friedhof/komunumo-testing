CREATE TABLE `configuration` (
    `conf_key` VARCHAR(255) NOT NULL,
    `conf_value` MEDIUMTEXT NOT NULL DEFAULT '',

    PRIMARY KEY (`conf_key`)
);
