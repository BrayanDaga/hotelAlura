CREATE DATABASE hotelalura;
USE hotelalura;
CREATE TABLE reservas
(
   id integer  AUTO_INCREMENT,
   fechaEntrada DATE,
   fechaSalida DATE,
   valor decimal(10,2),
   formaPago varchar (100),
   primary key (id)
);
CREATE TABLE huespedes
(
   id integer auto_increment,
   nombre varchar (100) NOT NULL,
   apellido varchar (100) NOT NULL,
   fechaDeNacimiento date not null,
   nacionalidad varchar (100) not null,
   telefono varchar (50) not null,
   reserva_id integer,
   primary key (id)
);
ALTER TABLE huespedes ADD CONSTRAINT fk_reserva_id FOREIGN KEY (reserva_id) REFERENCES reservas (id);
CREATE TABLE usuarios
(
   id integer auto_increment,
   nombre varchar (150) unique,
   contraseña varchar (300) not null,
   primary key (id)
);

INSERT INTO usuarios(nombre,contraseña) VALUES ('brayandaga','123456');