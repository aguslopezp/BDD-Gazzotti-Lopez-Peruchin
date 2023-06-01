-- Insert data into Mecanico table
INSERT INTO `Mecanico` (`DNI_Mecanico`, `direccion`, `nombre`, `apellido`)
VALUES (33677039, 'Laprida 663','Jorge' , 'Monzon1'),
(17002415, 'Paunero 1329', 'Marcelo', 'Perez'),
(25841367, 'Dean Funes 34', 'Mario', 'Capeletti');

-- Insert data into Telefonos_Mecanico table
INSERT INTO `Telefonos_Mecanico` (`DNI_Mecanico`, `telefono`)
VALUES (33677039, 3584225603),
(17002415, '3585678999'),
(25841367, '3586025907');

-- Insert data into Cargo table
INSERT INTO `Cargo` (`id_cargo`, `descripcion`)
VALUES (204, 'Instructor teórico'),
(218, 'Instructor de seguridad vial'),
(206, 'Instructor de manejo especializado');

-- Insert data into Instructor table
INSERT INTO `Instructor` (`DNI_Instructor`, `direccion`, `nombre`, `apellido`, `tipo_carnet`, `id_cargo`)
VALUES (36584771, 'Colon 446', 'Laura', 'Marzol', 'B2', 204),
(24502112, 'La Madrid 1976', 'Jose', 'Paso', 'B3', 206),
(31906357, 'Antártida Argentina 309', 'Carlos', 'Urquiza', 'C1', 218),
(35780492, 'Laprida 1344', 'Matias', 'Gomez', 'B3', 218);

-- Insert data into Secretaria table
INSERT INTO `Secretaria` (`DNI_Secretaria`, `nombre`, `apellido`, `direccion`, `titulo`)
VALUES (19476298, 'Marta', 'Sanchez', 'Av Sabattini 3005', 'Secretaria ejecutiva'),
(22486253, 'Soledad', 'Ruiz', 'Cabrera 740', 'Asistente de recepción'),
(41025996, 'Victoria', 'Torres', 'Los Andes 673', 'Asistente de ventas');

-- Insert data into Cliente table
INSERT INTO `Cliente` (`DNI_Cliente`, `direccion`, `nombre`, `apellido`)
VALUES (39624857, 'Maria Olguin 312', 'Martin', 'Molina'),
(36558121, 'H. Yrigoyen 2074', 'Carla', 'Ponce'),
(40577954, 'San Lorenzo', 'Edgar', 'Balestra');



-- Insert data into Clase_Teorica table
INSERT INTO `Clase_Teorica` (`id_clase`, `nombre`, `cupo_max`, `descripcion`, `DNI_Secretaria`, `DNI_Instructor_responsable`)
VALUES (1, 'Estacionamiento', 22, 'Clase sobre como estacionar', 41025996, 36584771),
(2, 'Señales de Transito', 15, 'Explicacion de los significados de las señales viales', 19476298, 24502112),
(3, 'Cambios', 20, 'clase para explicar como realizar los cambios de marcha', 22486253, 31906357);

-- Insert data into Toma table
INSERT INTO `Toma` (`DNI_Cliente`, `id_clase`)
VALUES (39624857, 1),
(36558121, 2),
(40577954, 1),
(39624857, 2),
(40577954, 2),
(40577954, 3),
(39624857, 3);


-- Insert data into Dicta table
INSERT INTO `Dicta` (`id_clase`, `DNI_Instructor`)
VALUES (1, 36584771),
(2, 24502112),
(3, 31906357);

-- Insert data into Material table
INSERT INTO `Material` (`id_material`, `costo`, `descripcion`, `DNI_Instructor_dicta`, `id_clase`)
VALUES (4004, 1990800, 'Simulador', 36584771, 1),
(4006, 7400.50, 'Palanca de cambios con pedalera', 31906357, 3),
(4005, 2175.55, 'Manuales de señales de transito', 24502112, 2);

