-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 04-04-2025 a las 11:26:01
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tfg_daw`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alimentos`
--

CREATE TABLE `alimentos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `calorias` double NOT NULL,
  `cantidad` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `alimentos`
--

INSERT INTO `alimentos` (`id`, `nombre`, `calorias`, `cantidad`) VALUES
(1, 'Aguacate', 234, 'mediano'),
(2, 'Almendras', 160, '1 onza'),
(3, 'Atún', 116, '100 gramos, enlatado en agua'),
(4, 'Avena', 150, '1/2 taza, cocida'),
(5, 'Brócoli', 55, '1/2 taza, hérvido'),
(6, 'Clara de huevo', 34, '2 claras'),
(7, 'Espinacas', 7, '1 taza, cruda'),
(8, 'Fresas', 50, '1 taza'),
(9, 'Manzana', 95, 'mediano'),
(10, 'Pavo', 165, '100 gramos, pechuga sin piel'),
(11, 'Pepino', 16, '1 taza, en rodajas'),
(12, 'Plátano', 105, 'pequeño'),
(13, 'Pollo', 165, '100 gramos, pechuga sin piel'),
(14, 'Queso cottage bajo en grasa', 82, '1/2 taza'),
(15, 'Quinoa', 111, '1/2 taza, cocida'),
(16, 'Salmón', 206, '100 gramos, filete'),
(17, 'Sandía', 46, '1 taza'),
(18, 'Tomate', 22, '1 tomate mediano'),
(19, 'Yogur griego bajo en grasa', 130, '1 taza'),
(20, 'Zanahorias', 52, '1 taza, en rodajas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `calorias`
--

CREATE TABLE `calorias` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `consumidas` double NOT NULL,
  `deseadas` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dietas`
--

CREATE TABLE `dietas` (
  `id` int(11) NOT NULL,
  `objetivo` varchar(255) NOT NULL,
  `dia_semana` varchar(255) NOT NULL,
  `desayuno` varchar(255) NOT NULL,
  `media_mañana` varchar(255) NOT NULL,
  `comida` varchar(255) NOT NULL,
  `merienda` varchar(255) NOT NULL,
  `cena` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `dietas`
--

INSERT INTO `dietas` (`id`, `objetivo`, `dia_semana`, `desayuno`, `media_mañana`, `comida`, `merienda`, `cena`) VALUES
(1, 'Definicion', 'Lunes', 'Omelet de claras de huevo con verduras mixtas (pimientos, espinacas, champiñones). Una rebanada de pan integral tostado. Una taza de té verde sin azúcar', 'Un puñado de nueces.', 'Ensalada de pollo a la parrilla con mezcla de lechugas, tomates cherry, pepino y aderezo bajo en grasa. Una porción de arroz integral', 'Un puñado de nueces.', 'Salmón a la parrilla con espárragos al vapor. Una porción de quinoa.'),
(2, 'Definicion', 'Martes', 'Batido de proteínas hecho con leche de almendras, proteína en polvo y una cucharada de mantequilla de maní. Una porción de fruta (por ejemplo, plátano o bayas).', 'Zanahorias baby con hummus.', 'Ensalada de atún con hojas verdes, tomate, cebolla morada y aceite de oliva. Una porción de pan integral.', 'Rodajas de pepino con queso cottage bajo en grasa.', 'Salmón a la parrilla con espárragos al vapor. Una porción de quinoa.'),
(3, 'Definicion', 'Miercoles', 'Tazón de avena con leche descremada, canela y una cucharada de semillas de chía. Una porción de fruta.', 'Un puñado de almendras.', 'Ensalada de garbanzos con tomates cherry, pepino, pimientos y vinagreta ligera. Una porción de queso fresco bajo en grasa.', 'Un yogur griego bajo en grasa. Un puñado de uvas.', 'Filete de ternera a la parrilla con espárragos y champiñones salteados. Ensalada verde mixta.'),
(4, 'Definicion', 'Jueves', 'Tortilla de espinacas con una rebanada de pan integral. Una taza de té verde sin azúcar.', 'Palitos de apio con mantequilla de almendras.', 'Sopa de verduras casera. Una porción de pollo a la plancha.', 'Rodajas de pepino con hummus.', 'Pescado al horno con espárragos y calabacín a la parrilla. Una porción de quinoa.'),
(5, 'Definicion', 'Viernes', 'Batido verde con espinacas, piña, plátano y leche de coco. Una porción de fruta.', 'Un puñado de nueces.', 'Ensalada de salmón ahumado con espinacas, aguacate, tomates cherry y aderezo bajo en grasa. Una porción de arroz integral.', 'Un yogur griego bajo en grasa. Una manzana.', 'Pechuga de pollo a la plancha con espárragos al vapor. Una porción de batata.'),
(6, 'Definicion', 'Sabado', 'Tortilla de claras de huevo con espinacas y champiñones. Una porción de pan integral tostado. Una taza de té verde sin azúcar.', 'Una porción de fruta fresca (por ejemplo, una naranja).', 'Ensalada de pollo a la parrilla con lechuga, tomate, pepino, zanahoria rallada y aderezo bajo en grasa. Una porción de quinoa.', 'Un puñado de almendras. Una porción de yogur griego bajo en grasa.', 'Filete de salmón al horno con espárragos y calabacín asados. Una porción de arroz integral.'),
(7, 'Definicion', 'Domingo', 'Batido de proteínas hecho con leche descremada, proteína en polvo y una cucharada de mantequilla de maní. Una porción de fruta (por ejemplo, bayas).', 'Palitos de zanahoria con hummus.', 'Ensalada de camarones con hojas verdes, tomate cherry, aguacate y vinagreta ligera. Una porción de pan integral.', 'Un yogur griego bajo en grasa. Un puñado de uvas.', 'Pechuga de pollo a la plancha con brócoli y champiñones salteados. Una porción de batata asada.'),
(8, 'Volumen', 'Lunes', '3 huevos enteros. 1 taza de avena cocida con leche descremada. 1 taza de frutas frescas (fresas, arándanos, plátanos, etc)', '1 batido de proteína de suero de leche. 1 manzana.', '150 gramos de pollo a la parrilla. 1 taza de arroz integral. 1 taza de brócoli al vapor.', '1 taza de yogur griego sin azúcar. 1 taza de frutas frescas (kiwi, mango, piña, etc.).', '150 gramos de salmón a la parrilla. 1 taza de quinoa cocida. 1 taza de espinacas cocidas.'),
(9, 'Volumen', 'Martes', '2 huevos revueltos o una tortilla francesa. 1 rebanada de pan integral tostado con aceite de oliva. 1 taza de frutas frescas.', '1 puñado de frutos secos (nueces, almendras, etc.). 1 pieza de fruta fresca.', '150 gramos de pechuga de pollo a la plancha o pescado blanco a la parrilla. 1 taza de verduras al vapor o ensalada mixta (lechuga, tomate, cebolla, zanahoria, etc.). 1/2 taza de arroz integral o quinoa.', '1 yogur natural bajo en grasas. 1 pieza de fruta fresca.', '150 gramos de salmón a la parrilla o tofu salteado. 1 taza de verduras al vapor o ensalada mixta. 1/2 taza de arroz integral o quinoa.'),
(10, 'Volumen', 'Miercoles', '2-3 huevos enteros revueltos o cocidos. 1 taza de avena con leche descremada y frutas. 1 rebanada de pan integral tostado con aceite de oliva.', 'Batido de proteínas en polvo mezclado con agua o leche descremada. 1 puñado de frutos secos (nueces, almendras, etc.).', '150-200 gramos de pechuga de pollo o ternera a la parrilla. 1 taza de arroz integral. 1 taza de verduras al vapor o ensalada mixta.', '1 batido de proteínas en polvo mezclado con agua o leche descremada. 1 pieza de fruta fresca.', '150-200 gramos de pescado blanco a la parrilla o pollo a la plancha. 1 taza de arroz integral o quinoa. 1 taza de verduras al vapor o ensalada mixta.'),
(11, 'Volumen', 'Jueves', 'Tazón de yogur con avena, orejones de melocotón y pipas de girasol.', 'Vaso de leche. Barritas proteicas de chocolate y almendras.', 'Pastel de arroz, atún y queso feta. Plátano.', 'Zumo de fruta. Bocadillo de jamón serrano y queso.', 'Guiso de pavo con pimientos y champiñones. Manzana.'),
(12, 'Volumen', 'Viernes', '1 taza de avena con leche de almendras y frutas frescas. 1 batido de proteínas en polvo mezclado con agua o leche de almendras.', '1 puñado de nueces o almendras. 1 pieza de fruta fresca.', '150-200 gramos de tofu salteado con verduras (cebolla, brócoli, champiñones, etc.). 1 taza de arroz integral o quinoa.', '1 batido de proteínas en polvo mezclado con agua o leche de almendras. 1 barra de proteínas vegetariana.', '150-200 gramos de lentejas o garbanzos.'),
(13, 'Volumen', 'Sabado', '2-3 huevos enteros revueltos o cocidos. 1 aguacate. 1 taza de café o té sin azúcar.', '1 batido de proteínas en polvo mezclado con agua o leche descremada. 1 puñado de frutos secos (nueces, almendras, etc.).', '150-200 gramos de salmón o carne roja a la parrilla. 1 taza de brócoli al vapor o ensalada de espinacas con aguacate y aceite de oliva.', '1 batido de proteínas en polvo mezclado con agua o leche descremada. 1 pieza de fruta fresca.', '150-200 gramos de pollo a la parrilla con salsa de mantequilla y ajo. 1 taza de coliflor asada con queso parmesano.'),
(14, 'Volumen', 'Domingo', 'Vaso de leche con tostadas de pan integral con crema de cacahuete.', 'Tazón de yogur con avena, nueces y plátano en rebanadas.', 'Sopa de fideos noodles al miso con atún y espinaca. Manzana.', 'Zumo de naranja. Medio bocadillo de pan integral con queso y pechuga de pavo.', 'Ternera asada con crema de calabaza. Mandarina.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `foro`
--

CREATE TABLE `foro` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `pregunta` varchar(255) NOT NULL,
  `respuesta` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `objetivos`
--

CREATE TABLE `objetivos` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `objetivo1` varchar(255) NOT NULL,
  `objetivo2` int(11) NOT NULL,
  `objetivo3` int(11) NOT NULL,
  `objetivo4` int(11) NOT NULL,
  `objetivo5` int(11) NOT NULL,
  `objetivo6` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `objetivos`
--

INSERT INTO `objetivos` (`id`, `usuario_id`, `objetivo1`, `objetivo2`, `objetivo3`, `objetivo4`, `objetivo5`, `objetivo6`) VALUES
(1, 1, 'Definicion', 1, 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tamanos`
--

CREATE TABLE `tamanos` (
  `id` int(11) NOT NULL,
  `tamano` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tamanos`
--

INSERT INTO `tamanos` (`id`, `tamano`) VALUES
(1, 'Grande'),
(2, 'Mediano'),
(3, 'Pequeño');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `nickname` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `num_telefono` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `premium` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `apellidos`, `nickname`, `password`, `num_telefono`, `email`, `direccion`, `premium`) VALUES
(1, 'Adrián', 'Polo Pérez', 'apolo', '123', 601361984, 'poloadrian3@gmail.com', NULL, 0),
(2, 'Carlos', 'Villegas', 'cvillegas', '123', 123456789, 'cvillegas@gmail.com', NULL, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alimentos`
--
ALTER TABLE `alimentos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `tamano_id` (`cantidad`);

--
-- Indices de la tabla `calorias`
--
ALTER TABLE `calorias`
  ADD PRIMARY KEY (`id`),
  ADD KEY `calorias_ibfk_1` (`usuario_id`);

--
-- Indices de la tabla `dietas`
--
ALTER TABLE `dietas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `foro`
--
ALTER TABLE `foro`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `objetivos`
--
ALTER TABLE `objetivos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `usuario_id_objetivos` (`usuario_id`);

--
-- Indices de la tabla `tamanos`
--
ALTER TABLE `tamanos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nickname` (`nickname`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alimentos`
--
ALTER TABLE `alimentos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `calorias`
--
ALTER TABLE `calorias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `dietas`
--
ALTER TABLE `dietas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `foro`
--
ALTER TABLE `foro`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `objetivos`
--
ALTER TABLE `objetivos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `tamanos`
--
ALTER TABLE `tamanos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `calorias`
--
ALTER TABLE `calorias`
  ADD CONSTRAINT `calorias_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `foro`
--
ALTER TABLE `foro`
  ADD CONSTRAINT `usuario_id_foro` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `objetivos`
--
ALTER TABLE `objetivos`
  ADD CONSTRAINT `usuario_id_objetivos` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
