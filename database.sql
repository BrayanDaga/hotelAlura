CREATE DATABASE hotelalura;
USE hotelalura;
CREATE TABLE reservas
(
   id integer  AUTO_INCREMENT,
   fechaEntrada DATE,
   fechaSalida DATE,
   valor decimal(10,2),
   formaPago varchar (100),
   estado tinyint,
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
   reserva_id integer not null,
   estado tinyint,
   primary key (id),
   FOREIGN KEY (reserva_id)
        REFERENCES reservas (id)
        ON DELETE CASCADE
);

CREATE TABLE usuarios
(
   id integer auto_increment,
   nombre varchar (150) unique,
   contraseña varchar (300) not null,
   primary key (id)
);

INSERT INTO usuarios(nombre,contraseña) VALUES ('brayandaga','123456');


