-- creamos la base de datos
CREATE DATABASE Tp_grupo_9;
USE Tp_grupo_9;

-- Crear tabla Secretaria
CREATE TABLE Secretaria (
  DNI_Secretaria INT NOT NULL,
  nombre VARCHAR(50),
  apellido VARCHAR(50),
  direccion VARCHAR(100),
  titulo VARCHAR(50),
  CONSTRAINT pk_secretaria PRIMARY KEY (DNI_Secretaria)
);

-- Crear tabla Cliente
CREATE TABLE Cliente (
  DNI_Cliente INT,
  direccion VARCHAR(100),
  nombre VARCHAR(50),
  apellido VARCHAR(50),
  CONSTRAINT pk_cliente PRIMARY KEY (DNI_Cliente)
);

-- Crear tabla Mecanico
CREATE TABLE Mecanico (
  DNI_Mecanico INT NOT NULL,
  direccion VARCHAR(100),
  nombre VARCHAR(50),
  apellido VARCHAR(50),
  CONSTRAINT pk_mecanico PRIMARY KEY (DNI_Mecanico)
);

-- Crear tabla Telefonos_Mecanico
CREATE TABLE Telefonos_Mecanico (
  DNI_Mecanico INT,
  telefono VARCHAR(15),
  CONSTRAINT fk_mecanico FOREIGN KEY (DNI_Mecanico) REFERENCES Mecanico(DNI_Mecanico) ON DELETE CASCADE
);

-- Crear tabla Cargo
CREATE TABLE Cargo (
  id_cargo INT NOT NULL,
  descripcion VARCHAR(100),
  CONSTRAINT pk_cargo PRIMARY KEY (id_cargo)
);

-- Crear tabla Instructor
CREATE TABLE Instructor (
  DNI_Instructor INT NOT NULL,
  direccion VARCHAR(100),
  nombre VARCHAR(50),
  apellido VARCHAR(50),
  tipo_carnet VARCHAR(2),
  id_cargo INT NOT NULL,
  CONSTRAINT pk_instructor PRIMARY KEY (DNI_Instructor),
  CONSTRAINT fk_cargo FOREIGN KEY (id_cargo) REFERENCES Cargo(id_cargo) ON DELETE CASCADE,
  CONSTRAINT check_tipo_carnet CHECK (tipo_carnet='B2' or tipo_carnet='B3' or tipo_carnet='C1')
);

-- Crear tabla Clase_Teorica
CREATE TABLE Clase_Teorica (
  id_clase INT NOT NULL,
  nombre VARCHAR(50),
  cupo_max INT,
  descripcion VARCHAR(100),
  DNI_Secretaria INT,
  DNI_Instructor_responsable INT,
  CONSTRAINT pk_clase PRIMARY KEY (id_clase),
  CONSTRAINT fk_secretaria FOREIGN KEY (DNI_Secretaria) REFERENCES Secretaria(DNI_Secretaria) ON DELETE CASCADE,
  CONSTRAINT fk_instructor FOREIGN KEY (DNI_Instructor_responsable) REFERENCES Instructor(DNI_Instructor) ON DELETE CASCADE
);

-- Crear tabla Toma
CREATE TABLE Toma (
  DNI_Cliente INT,
  id_clase INT,
  CONSTRAINT fk_cliente FOREIGN KEY (DNI_Cliente) REFERENCES Cliente(DNI_Cliente) ON DELETE CASCADE,
  CONSTRAINT fk_cTeorica FOREIGN KEY (id_clase) REFERENCES Clase_Teorica(id_clase) ON DELETE CASCADE
);

-- Crear tabla Dicta
CREATE TABLE Dicta (
  id_clase INT,
  DNI_Instructor INT,
  CONSTRAINT fk_clTeorica FOREIGN KEY (Id_clase) REFERENCES Clase_Teorica(id_clase) ON DELETE CASCADE,
  CONSTRAINT fk_instruc FOREIGN KEY (DNI_Instructor) REFERENCES Instructor(DNI_Instructor) ON DELETE CASCADE
);

-- Crear tabla Material
CREATE TABLE Material (
  id_material INT NOT NULL,
  costo DECIMAL(10,2),
  descripcion VARCHAR(100),
  DNI_Instructor_dicta INT,
  id_clase INT,
  CONSTRAINT id_material PRIMARY KEY (id_material),
  CONSTRAINT fk_dictaI FOREIGN KEY (DNI_Instructor_dicta) REFERENCES Dicta(DNI_Instructor) ON DELETE CASCADE,
  CONSTRAINT fk_dictaC FOREIGN KEY (id_clase) REFERENCES Dicta(id_clase) ON DELETE CASCADE
);

-- creamos la tabla para las auditorias
CREATE TABLE Auditoria(
  id_auditoria serial NOT NULL,
  id_material INT,
  fecha_cambio DATE,
  costo_viejo DECIMAL(10,2),
  costo_nuevo DECIMAL(10,2),
  usuario VARCHAR(50),
  CONSTRAINT pk_auditoria PRIMARY KEY (id_auditoria),
CONSTRAINT fk_material FOREIGN KEY (id_material) REFERENCES Material(id_material) ON DELETE CASCADE
);

-- controla el costo de los materiales
DELIMITER $$
CREATE TRIGGER auditoria
AFTER UPDATE ON Material
FOR EACH ROW
BEGIN
  INSERT INTO Auditoria (id_material, fecha_cambio, costo_viejo, costo_nuevo, usuario) 
  VALUES (NEW.id_material, NOW(), OLD.costo, NEW.costo, CURRENT_USER());
END;
$$
DELIMITER ;

-- controla el cupo maximo de las clases 
DELIMITER $$
CREATE TRIGGER check_cupo_maximo
BEFORE INSERT ON Toma
FOR EACH ROW
BEGIN
  DECLARE cantidad_clientes INT;
  DECLARE cupo_maximo INT;
  SET cantidad_clientes = (SELECT COUNT(*) FROM Toma WHERE id_clase = NEW.id_clase);  
  SET cupo_maximo = (SELECT cupo_max FROM Clase_Teorica WHERE id_clase = NEW.id_clase);
  IF cantidad_clientes >= cupo_maximo THEN
    SIGNAL SQLSTATE '45000' 
    SET MESSAGE_TEXT = 'La clase ha alcanzado el cupo máximo de clientes.';
  END IF;
END
$$ 
DELIMITER ;

