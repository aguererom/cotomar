-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 15-06-2022 a las 18:17:41
-- Versión del servidor: 5.7.36
-- Versión de PHP: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `cotomar`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aforo`
--

DROP TABLE IF EXISTS `aforo`;
CREATE TABLE IF NOT EXISTS `aforo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha` varchar(255) NOT NULL,
  `hora` varchar(255) NOT NULL,
  `num_personas` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=99 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `aforo`
--

INSERT INTO `aforo` (`id`, `fecha`, `hora`, `num_personas`) VALUES
(98, '15/06/2022', '15:29:06', '99'),
(97, '15/06/2022', '15:27:23', '100'),
(95, '15/06/2022', '14:35:47', '47'),
(94, '15/06/2022', '14:35:38', '35'),
(96, '15/06/2022', '15:06:20', '21'),
(93, '15/06/2022', '14:34:17', '25'),
(90, '15/06/2022', '00:52:29', '0'),
(89, '15/06/2022', '00:52:24', '100'),
(88, '15/06/2022', '00:51:46', '1'),
(87, '15:06:2022', '00:51:16', '5'),
(86, '15/6/22 0:49', '00:49:45', '90');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `socios`
--

DROP TABLE IF EXISTS `socios`;
CREATE TABLE IF NOT EXISTS `socios` (
  `num_casa` varchar(255) NOT NULL,
  `adeudado` tinyint(1) NOT NULL,
  `apellidos` varchar(255) NOT NULL,
  `importe` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `year` varchar(255) NOT NULL,
  `year2` varchar(255) DEFAULT NULL,
  `year3` varchar(255) DEFAULT NULL,
  `year4` varchar(255) DEFAULT NULL,
  `year5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`num_casa`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `socios`
--

INSERT INTO `socios` (`num_casa`, `adeudado`, `apellidos`, `importe`, `nombre`, `year`, `year2`, `year3`, `year4`, `year5`) VALUES
('365', 0, 'Prados Perez', '250', 'Ana', '2022', '', '', '', ''),
('314', 1, 'Padilla Ramos', '550', 'Pedro', '2022', '', '', '2019', ''),
('110', 1, 'Cordero Linares', '750', 'Simon', '2022', '', '2020', '2019', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activo` tinyint(1) NOT NULL,
  `apellidos` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `num_casa` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `usuario` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_kfsp0s1tflm1cwlj8idhqsad0` (`email`),
  UNIQUE KEY `UK_3m5n1w5trapxlbo2s42ugwdmd` (`usuario`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `activo`, `apellidos`, `email`, `nombre`, `num_casa`, `password`, `role`, `usuario`) VALUES
(1, 1, 'Guerrero Marin', 'alum.aguerrerom@iesalixar.org', 'Angel', NULL, '$2a$15$y4GQyoH3wXVKWbu62ViW3OI90xZilH4OvSk6QHUvVaUb3kp89pE7q', 'ROLE_ADMIN', 'admin'),
(6, 1, 'usuario', 'usuario@gmail.com', 'usuario', NULL, '$2a$15$ieNt6tLBq0TM6RwVFoOF6ekVi4JDWVdF04UlUVLJla7U5nj4PY/GK', 'ROLE_USER', 'usuario');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
