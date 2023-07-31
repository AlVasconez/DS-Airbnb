create database airbnb;
use airbnb;
CREATE TABLE cliente (
    usuario_id INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    contrasenia VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    correo VARCHAR(100),
    direccion_fisica VARCHAR(200),
    verificador BOOLEAN
);

CREATE TABLE anfitrion (
    usuario_id INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    contrasenia VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    correo VARCHAR(100),
    direccion_fisica VARCHAR(200),
    verificador BOOLEAN
);

CREATE TABLE alojamiento (
    alojamiento_id INT PRIMARY KEY,
    anfitrion_id INT,
    precio_noche DECIMAL(10, 2),
    habitaciones INT,
    ubicacion VARCHAR(100),
    calificacion DECIMAL(3, 2),
	FOREIGN KEY (anfitrion_id) REFERENCES anfitrion(usuario_id)

);

CREATE TABLE lista_favorito(
    alojamiento_id INT,
    cliente_id INT,
    PRIMARY KEY (alojamiento_id, cliente_id),
    FOREIGN KEY (cliente_id) REFERENCES cliente(usuario_id),
    FOREIGN KEY (alojamiento_id) REFERENCES alojamiento(alojamiento_id)
);

CREATE TABLE servicio_alojamiento (
    alojamiento_id INT,
    servicio_id INT,
    servicio TEXT,
    PRIMARY KEY (alojamiento_id, servicio_id),
    FOREIGN KEY (alojamiento_id) REFERENCES alojamiento(alojamiento_id)
);


CREATE TABLE regla_alojamiento (
    alojamiento_id INT,
    reglamento_id INT,
    regla TEXT,
    PRIMARY KEY (alojamiento_id, reglamento_id),
    FOREIGN KEY (alojamiento_id) REFERENCES alojamiento(alojamiento_id)
);

CREATE TABLE mensaje (
    mensaje_id INT PRIMARY KEY,
    mensaje VARCHAR(200),
    anfitrion_id INT,
    cliente_id INT,
    FOREIGN KEY (anfitrion_id) REFERENCES anfitrion(usuario_id),
    FOREIGN KEY (cliente_id) REFERENCES cliente(usuario_id)
);

CREATE TABLE reserva (
    reserva_id INT PRIMARY KEY,
    cliente_id INT,
    alojamiento_id INT,
    tiempo_estancia SMALLINT,
    tarifa_airbnb DECIMAL(10, 2),
    FOREIGN KEY (cliente_id) REFERENCES cliente(usuario_id),
    FOREIGN KEY (alojamiento_id) REFERENCES alojamiento(alojamiento_id)
);

CREATE TABLE pago_tarjeta (
    pago_id INT PRIMARY KEY,
    cliente_id INT,
    reserva_id INT,
    monto DECIMAL(10, 2),
    fecha DATE,
    numero_tarjeta INT,
    caducidad DATE,
    codigo_postal smallint,
    codigo_cvv smallint,
    FOREIGN KEY (cliente_id) REFERENCES cliente(usuario_id),
    FOREIGN KEY (reserva_id) REFERENCES reserva(reserva_id)
);

CREATE TABLE pago_paypal (
    pago_id INT PRIMARY KEY,
    cliente_id INT,
    reserva_id INT,
    monto DECIMAL(10, 2),
    fecha DATE,
    numero_cuenta CHAR(10),
    FOREIGN KEY (cliente_id) REFERENCES cliente(usuario_id),
    FOREIGN KEY (reserva_id) REFERENCES reserva(reserva_id)
);

CREATE TABLE pago_googlepay (
    pago_id INT PRIMARY KEY,
    cliente_id INT,
    reserva_id INT,
    monto DECIMAL(10, 2),
    fecha DATE,
    numero_cuenta CHAR(10),
    FOREIGN KEY (cliente_id) REFERENCES cliente(usuario_id),
    FOREIGN KEY (reserva_id) REFERENCES reserva(reserva_id)
);

CREATE TABLE fechas_reservadas (
    fecha DATE,
    alojamiento_id INT,
    reserva_id INT,
    PRIMARY KEY (fecha, alojamiento_id, reserva_id),
    FOREIGN KEY (alojamiento_id) REFERENCES alojamiento(alojamiento_id),
    FOREIGN KEY (reserva_id) REFERENCES reserva(reserva_id)
);

CREATE TABLE resenia (
    cliente_id INT,
    alojamiento_id INT,
    contenido TEXT,
    calificacion DECIMAL(3, 2),
    PRIMARY KEY(cliente_id,alojamiento_id),
    FOREIGN KEY (cliente_id) REFERENCES cliente(usuario_id),
    FOREIGN KEY (alojamiento_id) REFERENCES alojamiento(alojamiento_id)
);