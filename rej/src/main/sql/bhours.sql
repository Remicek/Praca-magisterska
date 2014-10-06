CREATE TABLE IF NOT EXISTS `przychodnia`.`bhours` (
  `id` INT(11) NOT NULL,
  `weekDay` INT(11) NOT NULL,
  `open_time` TIME NOT NULL,
  `close_time` TIME NOT NULL,
  `doctor_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_bhours_doctor1_idx` (`doctor_id` ASC),
  CONSTRAINT `fk_bhours_doctor1`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `przychodnia`.`doctor` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8