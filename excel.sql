-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 13, 2016 at 06:29 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `excel`
--

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rollNo` int(11) NOT NULL,
  `overAllP` double NOT NULL,
  `overAllS` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=33 ;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`id`, `rollNo`, `overAllP`, `overAllS`) VALUES
(17, 211, 87.5, 'Pass'),
(18, 211, 87.5, 'Pass'),
(19, 211, 87.5, 'Pass'),
(20, 211, 87.5, 'Pass'),
(21, 211, 87.5, 'Pass'),
(22, 211, 87.5, 'Pass'),
(23, 209, 87.5, 'Pass'),
(24, 210, 87.5, 'Pass'),
(25, 211, 87.5, 'Pass'),
(26, 211, 87.5, 'Pass'),
(27, 211, 87.5, 'Pass'),
(28, 211, 87.5, 'Pass'),
(29, 211, 87.5, 'Pass'),
(30, 211, 87.5, 'Pass'),
(31, 0, 87.5, 'Pass'),
(32, 0, 87.5, 'Pass');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
