-- phpMyAdmin SQL Dump
-- version 4.0.10.1
-- http://www.phpmyadmin.net
--
-- Host: 127.13.162.130:3306
-- Czas wygenerowania: 02 Wrz 2014, 15:46
-- Wersja serwera: 5.5.37
-- Wersja PHP: 5.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza danych: `rej`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `appointment`
--

CREATE TABLE IF NOT EXISTS `appointment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appointment_details` varchar(255) DEFAULT NULL,
  `appointment_endt_date` datetime DEFAULT NULL,
  `appointment_start_date` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `doctor` bigint(20) DEFAULT NULL,
  `patient` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gwfpw4dniri5s62kfvflto66x` (`doctor`),
  KEY `FK_swn8uotvddprfn0lrv540g50f` (`patient`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Zrzut danych tabeli `appointment`
--

INSERT INTO `appointment` (`id`, `appointment_details`, `appointment_endt_date`, `appointment_start_date`, `version`, `doctor`, `patient`) VALUES
(1, 'Wizyta controlna', '2014-08-22 12:50:00', '2014-08-22 12:31:00', 7, 4, 1),
(3, 'Wizyta controlna', '2014-08-15 12:50:00', '2014-08-15 12:31:00', 0, 4, 1),
(5, 'Test nowy', '2014-08-15 10:00:00', '2014-08-15 06:00:00', 1, 1, 1),
(6, 'Wizyta kontrolna', '2014-07-29 16:00:00', '2014-07-29 15:00:00', 5, 3, 2),
(7, 'Wizyta kontrolna', '2014-08-12 15:00:00', '2014-08-12 14:00:00', 5, 3, 2),
(8, 'test usuwanie', '2014-08-14 08:25:00', '2014-08-14 07:00:00', 3, 1, 2),
(9, '10:55 - 11 modyfikacja', '2014-08-14 12:50:00', '2014-08-14 11:25:00', 4, 1, 2),
(10, 'test', '2014-10-01 13:00:00', '2014-10-01 07:00:00', 0, 1, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `bhours`
--

CREATE TABLE IF NOT EXISTS `bhours` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `close_time` datetime DEFAULT NULL,
  `open_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `week_day` int(11) NOT NULL,
  `working` tinyint(1) DEFAULT NULL,
  `doctor` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_a5qdpfrix2oejdeprocq1ab2w` (`doctor`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Zrzut danych tabeli `bhours`
--

INSERT INTO `bhours` (`id`, `close_time`, `open_time`, `version`, `week_day`, `working`, `doctor`) VALUES
(1, '1970-01-01 14:00:00', '1970-01-01 07:00:00', 1, 3, NULL, 1),
(2, '1970-01-01 12:00:00', '1970-01-01 07:00:00', 0, 4, NULL, 1),
(3, '1970-01-01 14:00:00', '1970-01-01 06:00:00', 0, 5, NULL, 4),
(4, '1970-01-01 11:00:00', '1970-01-01 04:00:00', 0, 5, NULL, 1),
(5, '1970-01-01 17:00:00', '1970-01-01 15:00:00', 1, 3, NULL, 1),
(6, '1970-01-01 17:00:00', '1970-01-01 15:00:00', 1, 5, NULL, 1),
(7, '1970-01-01 12:00:00', '1970-01-01 08:00:00', 0, 2, NULL, 1),
(8, '1970-01-01 12:00:00', '1970-01-01 07:00:00', 0, 1, NULL, 2),
(9, '1970-01-01 16:00:00', '1970-01-01 07:00:00', 0, 3, NULL, 2),
(10, '1970-01-01 12:00:00', '1970-01-01 08:00:00', 0, 4, NULL, 2),
(11, '1970-01-01 16:00:00', '1970-01-01 12:00:00', 0, 2, NULL, 3);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `doctor`
--

CREATE TABLE IF NOT EXISTS `doctor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) NOT NULL,
  `profession` varchar(20) NOT NULL,
  `surname` varchar(20) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Zrzut danych tabeli `doctor`
--

INSERT INTO `doctor` (`id`, `first_name`, `profession`, `surname`, `version`) VALUES
(1, 'Przemek ', 'Logopeda', 'Zawadzki', 0),
(2, 'Jacek ', 'Laryngolog', 'Wojciechowski', 0),
(3, 'Krzysztof', 'Pediatra', 'Majewski', 0),
(4, 'Maciej ', 'Psycholog', 'Duda', 0),
(6, 'Remigiusz', 'test', 'Brzęłó', 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `mail`
--

CREATE TABLE IF NOT EXISTS `mail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `body` varchar(255) DEFAULT NULL,
  `date_mail` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `send` tinyint(1) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `appointment` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5hnba9rmh0qe1lrq9ivfbvl1y` (`appointment`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=38 ;

--
-- Zrzut danych tabeli `mail`
--

INSERT INTO `mail` (`id`, `body`, `date_mail`, `description`, `send`, `subject`, `version`, `appointment`) VALUES
(1, 'Szanowny pacjencie Celestyn  Gorski\nZosta?a ustalona wizyta z doktorem: Przemek  Zawadzki w terminie: Fri Aug 15 04:00:00 CEST 2014', '2014-08-11 16:34:21', 'Stworzono', 1, 'Nowa wizyta w poradni', 1, 5),
(2, 'Szanowny pacjencie Radzimierz  Sobczak\nZosta?a ustalona wizyta z doktorem: Krzysztof Majewski w terminie: Tue Aug 05 15:00:00 CEST 2014', '2014-08-11 21:23:08', 'Stworzono', 1, 'Nowa wizyta w poradni', 1, 6),
(3, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: 2014-08-05 15:00:00.0', '2014-08-11 21:23:43', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 6),
(4, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: 2014-08-05 15:00:00.0', '2014-08-11 21:23:56', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 6),
(5, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: 2014-08-05 15:00:00.0', '2014-08-11 21:24:27', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 6),
(6, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: 2014-08-05 15:00:00.0', '2014-08-11 21:25:05', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 6),
(7, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: 2014-08-12 15:00:00.0', '2014-08-11 21:26:06', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 6),
(8, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: 2014-08-12 15:00:00.0', '2014-08-11 21:26:39', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 6),
(9, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: 2014-08-12 15:00:00.0', '2014-08-11 21:27:07', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 6),
(10, 'Szanowny pacjencie Radzimierz  Sobczak\nZosta?a ustalona wizyta z doktorem: Krzysztof Majewski w terminie: Tue Aug 05 14:00:00 CEST 2014', '2014-08-11 21:29:26', 'Stworzono', 1, 'Nowa wizyta w poradni', 1, 7),
(11, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: 2014-08-05 14:00:00.0', '2014-08-11 21:29:46', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 7),
(12, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: 2014-08-05 14:00:00.0', '2014-08-11 21:29:53', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 7),
(13, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: 2014-08-05 14:00:00.0', '2014-08-11 21:30:03', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 7),
(15, 'Szanowny pacjencie Radzimierz  Sobczak\nZosta?a ustalona wizyta z doktorem: Przemek  Zawadzki w terminie: Thu Aug 14 07:00:00 CEST 2014', '2014-08-11 23:37:43', 'Stworzono', 1, 'Nowa wizyta w poradni', 1, 8),
(16, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Przemek  Zawadzki zosta?a przesuni?ta na termin: Thu Aug 14 07:00:00 CEST 2014', '2014-08-11 23:37:59', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 8),
(17, 'Szanowny pacjencie Radzimierz  Sobczak\nZosta?a ustalona wizyta z doktorem: Przemek  Zawadzki w terminie: Thu Aug 14 10:55:00 CEST 2014', '2014-08-11 23:38:21', 'Stworzono', 1, 'Nowa wizyta w poradni', 1, 9),
(18, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Przemek  Zawadzki zosta?a przesuni?ta na termin: Thu Aug 14 10:55:00 CEST 2014', '2014-08-11 23:38:29', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 9),
(19, 'Szanowny pacjencie Celestyn  Gorski\nWizyta z doktorem: Maciej  Duda zosta?a przesuni?ta na termin: 2014-08-22 12:31:00.0', '2014-08-20 12:54:37', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 1),
(20, 'Szanowny pacjencie Celestyn  Gorski\nWizyta z doktorem: Maciej  Duda zosta?a przesuni?ta na termin: 2014-08-08 12:31:00.0', '2014-08-20 12:54:42', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 1),
(21, 'Szanowny pacjencie Celestyn  Gorski\nWizyta z doktorem: Maciej  Duda zosta?a przesuni?ta na termin: 2014-08-22 12:31:00.0', '2014-08-20 12:54:47', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 1),
(22, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: 2014-08-19 14:00:00.0', '2014-08-20 12:55:06', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 7),
(23, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: 2014-08-05 14:00:00.0', '2014-08-20 12:55:08', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 7),
(24, 'Szanowny pacjencie Celestyn  Gorski\nZosta?a ustalona wizyta z doktorem: Przemek  Zawadzki w terminie: Wed Oct 01 07:00:00 CEST 2014', '2014-08-27 15:32:46', 'Stworzono', 1, 'Nowa wizyta w poradni', 1, 10),
(26, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Przemek  Zawadzki zosta?a przesuni?ta na termin: Thu Aug 14 07:00:00 CEST 2014', '2014-08-30 16:17:47', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 8),
(27, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Przemek  Zawadzki zosta?a przesuni?ta na termin: Thu Aug 14 11:25:00 CEST 2014', '2014-08-30 16:17:56', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 9),
(28, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Przemek  Zawadzki zosta?a przesuni?ta na termin: Thu Aug 14 11:25:00 CEST 2014', '2014-08-30 16:17:59', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 9),
(29, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: Tue Aug 19 15:00:00 CEST 2014', '2014-08-30 16:54:54', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 6),
(30, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: Tue Aug 12 15:00:00 CEST 2014', '2014-08-30 16:54:58', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 6),
(31, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: Tue Aug 19 15:00:00 CEST 2014', '2014-08-30 16:55:03', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 6),
(32, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: Tue Jul 29 15:00:00 CEST 2014', '2014-08-30 16:55:04', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 6),
(33, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: Tue Aug 12 14:00:00 CEST 2014', '2014-08-30 16:55:23', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 7),
(34, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: Tue Aug 05 14:00:00 CEST 2014', '2014-08-30 16:55:28', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 7),
(35, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Krzysztof Majewski zosta?a przesuni?ta na termin: Tue Aug 12 14:00:00 CEST 2014', '2014-08-30 16:55:58', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 7),
(36, 'Szanowny pacjencie Celestyn  Gorski\nWizyta z doktorem: Przemek  Zawadzki zosta?a przesuni?ta na termin: Fri Aug 15 06:00:00 CEST 2014', '2014-08-30 16:56:10', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 5),
(37, 'Szanowny pacjencie Radzimierz  Sobczak\nWizyta z doktorem: Przemek  Zawadzki zosta?a przesuni?ta na termin: Thu Aug 14 07:00:00 CEST 2014', '2014-08-30 16:56:22', 'Zmodyfikowano', 1, 'Zmiana terminu wizyty w poradni', 1, 8);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `person`
--

CREATE TABLE IF NOT EXISTS `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(40) NOT NULL,
  `birth_date` datetime DEFAULT NULL,
  `card_number` varchar(20) NOT NULL,
  `father_name` varchar(50) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `legitimationid` varchar(20) NOT NULL,
  `maiden_surname` varchar(20) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `nfz` varchar(20) NOT NULL,
  `pesel` varchar(12) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `telephone_number` varchar(20) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `attending_physician` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3tadys8jf6jgtftyisu1sgekm` (`attending_physician`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Zrzut danych tabeli `person`
--

INSERT INTO `person` (`id`, `address`, `birth_date`, `card_number`, `father_name`, `first_name`, `legitimationid`, `maiden_surname`, `mail`, `nfz`, `pesel`, `surname`, `telephone_number`, `version`, `attending_physician`) VALUES
(1, 'ul. Pasterska 4 waw', '1920-11-25 00:00:00', '123456', 'Mirosław', 'Celestyn', '123', 'Kamińska', 'remicek@gmail.com', '123456', '75091858791', ' Gorski', '51 513 20 57', 0, 1),
(2, 'ul. Brzegowa 54 Wro', '1969-11-11 00:00:00', '123', 'Arkadiusz', 'Radzimierz ', '123', 'Małgorzata', 'remicek@gmail.com', '12345', '63011133738', 'Sobczak', '1234', 0, 3),
(3, ' Gdańsk prosta 12', '1988-12-12 00:00:00', '1234', 'Zbigniew', 'Miron', '123414', 'Asia', 'arkwaw@gmail.com', '133', '82060919199', 'Nowakowski', '142424', 0, 2),
(4, 'Chrz?szczy?ewoszyce, powiat ??ko?ody', '2014-08-11 00:00:00', '666', 'Szczepan', 'Grzegorz', '666', 'Brz?czyszczewska', 'evil@niedobro.pl', '666', '11081467891', 'Brz?czyszczykiewicz', '666696969', 0, 4);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`username`, `password`, `enabled`) VALUES
('admin', '$2a$10$Upu3lux9ZafrSH7w9Yte.eTD6T1u/YpCChP42xGthRZRnNmGkH6A.', 1),
('user', '$2a$10$Upu3lux9ZafrSH7w9Yte.eTD6T1u/YpCChP42xGthRZRnNmGkH6A.', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_roles`
--

CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `ROLE` varchar(45) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE KEY `uni_username_role` (`ROLE`,`username`),
  KEY `fk_username_idx` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Zrzut danych tabeli `user_roles`
--

INSERT INTO `user_roles` (`user_role_id`, `username`, `ROLE`) VALUES
(2, 'admin', 'ROLE_ADMIN'),
(1, 'admin', 'ROLE_USER'),
(3, 'user', 'ROLE_USER');

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `appointment`
--
ALTER TABLE `appointment`
  ADD CONSTRAINT `FK_gwfpw4dniri5s62kfvflto66x` FOREIGN KEY (`doctor`) REFERENCES `doctor` (`id`),
  ADD CONSTRAINT `FK_swn8uotvddprfn0lrv540g50f` FOREIGN KEY (`patient`) REFERENCES `person` (`id`);

--
-- Ograniczenia dla tabeli `bhours`
--
ALTER TABLE `bhours`
  ADD CONSTRAINT `FK_a5qdpfrix2oejdeprocq1ab2w` FOREIGN KEY (`doctor`) REFERENCES `doctor` (`id`);

--
-- Ograniczenia dla tabeli `mail`
--
ALTER TABLE `mail`
  ADD CONSTRAINT `FK_5hnba9rmh0qe1lrq9ivfbvl1y` FOREIGN KEY (`appointment`) REFERENCES `appointment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `person`
--
ALTER TABLE `person`
  ADD CONSTRAINT `FK_3tadys8jf6jgtftyisu1sgekm` FOREIGN KEY (`attending_physician`) REFERENCES `doctor` (`id`);

--
-- Ograniczenia dla tabeli `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `fk_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
