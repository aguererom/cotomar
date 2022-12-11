-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 10-12-2022 a las 15:04:14
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
  `recinto_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7kwapdjfbpy7klb3ffidqew31` (`recinto_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `aforo`
--

INSERT INTO `aforo` (`id`, `fecha`, `hora`, `num_personas`, `recinto_id`) VALUES
(1, '09/12/2022', '07:27:11', '91', 1),
(2, '09/12/2022', '07:27:19', '90', 2),
(3, '09/12/2022', '07:27:32', '1', 1),
(4, '09/12/2022', '09:20:52', '37', 1),
(5, '09/12/2022', '09:21:30', '56', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `deudas_socio`
--

DROP TABLE IF EXISTS `deudas_socio`;
CREATE TABLE IF NOT EXISTS `deudas_socio` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `importe` varchar(255) NOT NULL,
  `year` varchar(255) NOT NULL,
  `socio_dni` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmxnyhyajjqm49ffeoxyq05aya` (`socio_dni`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `deudas_socio`
--

INSERT INTO `deudas_socio` (`id`, `importe`, `year`, `socio_dni`) VALUES
(5, '250', '2022', '47347598M');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recintos_urb`
--

DROP TABLE IF EXISTS `recintos_urb`;
CREATE TABLE IF NOT EXISTS `recintos_urb` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `aforo_max` int(11) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `recintos_urb`
--

INSERT INTO `recintos_urb` (`id`, `aforo_max`, `direccion`, `nombre`) VALUES
(1, 100, 'Sector A Segunda Fase 14', 'Piscina 1'),
(2, 90, 'Sector A Segunda Fase 14B', 'Piscina 2'),
(3, 50, 'C/ SN', 'Pistas de futbol');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `socios`
--

DROP TABLE IF EXISTS `socios`;
CREATE TABLE IF NOT EXISTS `socios` (
  `dni` varchar(255) NOT NULL,
  `apellidos` varchar(255) NOT NULL,
  `cp` varchar(255) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `num_casa` varchar(255) NOT NULL,
  PRIMARY KEY (`dni`),
  UNIQUE KEY `UK_soryh6x9bfe8rg6hr8ypm4p0g` (`num_casa`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `socios`
--

INSERT INTO `socios` (`dni`, `apellidos`, `cp`, `direccion`, `nombre`, `num_casa`) VALUES
('47347598M', 'Guerrero Marín', '41804', 'Calle Azahar n°23', 'Angel', '328');

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
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `usuario` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_kfsp0s1tflm1cwlj8idhqsad0` (`email`),
  UNIQUE KEY `UK_3m5n1w5trapxlbo2s42ugwdmd` (`usuario`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `activo`, `apellidos`, `email`, `nombre`, `password`, `role`, `usuario`) VALUES
(3, 0, 'admin', 'admin@gmail.com', 'admin', '$2a$15$KYTlDi6JQ9IKbbswJKMrVuOLa6uhmJCVimN7aZoXOmT/.6lt4fEAy', 'ROLE_ADMIN', 'admin'),
(2, 1, 'usuario', 'usuario@gmail.com', 'usuario', '$2a$15$ZSUJt1G5PD2lQfglTiD39OlgFtkwMRAi6r3K9Bhxyaxh02Tkal.Ou', 'ROLE_ADMIN', 'usuario');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
