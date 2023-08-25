-- drop database if exists airbnb;
 create database airbnb;
use airbnb;

-- drop table if exists cliente;
CREATE TABLE cliente (
    usuario_id INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    contrasenia VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    correo VARCHAR(100),
    direccion_fisica VARCHAR(200),
    verificador BOOLEAN
);

-- drop table if exists anfitrion;
CREATE TABLE anfitrion (
    usuario_id INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    contrasenia VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    correo VARCHAR(100),
    direccion_fisica VARCHAR(200),
    verificador BOOLEAN
);

-- drop table if exists alojamiento;
CREATE TABLE alojamiento (
    alojamiento_id INT PRIMARY KEY,
    anfitrion_id INT,
    precio_noche DECIMAL(10, 2),
    habitaciones INT,
    ubicacion VARCHAR(100),
    calificacion DECIMAL(3, 2),
    tarifa_airbnb DECIMAL(10, 2) default null,
	FOREIGN KEY (anfitrion_id) REFERENCES anfitrion(usuario_id)

);

-- drop table if exists lista_favorito;
CREATE TABLE lista_favorito(
    alojamiento_id INT,
    cliente_id INT,
    PRIMARY KEY (alojamiento_id, cliente_id),
    FOREIGN KEY (cliente_id) REFERENCES cliente(usuario_id),
    FOREIGN KEY (alojamiento_id) REFERENCES alojamiento(alojamiento_id)
);

-- drop table if exists servicio_alojamiento;
CREATE TABLE servicio_alojamiento (
    alojamiento_id INT,
    servicio_id INT,
    servicio VARCHAR(100) NOT NULL,
    PRIMARY KEY (alojamiento_id, servicio_id),
    FOREIGN KEY (alojamiento_id) REFERENCES alojamiento(alojamiento_id)
);

-- drop table if exists regla_alojamiento;
CREATE TABLE regla_alojamiento (
    alojamiento_id INT,
    reglamento_id INT,
    regla VARCHAR(100) NOT NULL,
    PRIMARY KEY (alojamiento_id, reglamento_id),
    FOREIGN KEY (alojamiento_id) REFERENCES alojamiento(alojamiento_id)
);

-- drop table if exists mensaje;
CREATE TABLE mensaje (
    mensaje_id INT PRIMARY KEY,
    mensaje VARCHAR(200),
    anfitrion_id INT,
    cliente_id INT,
    FOREIGN KEY (anfitrion_id) REFERENCES anfitrion(usuario_id),
    FOREIGN KEY (cliente_id) REFERENCES cliente(usuario_id)
);

-- drop table if exists reserva;
CREATE TABLE reserva (
    reserva_id INT PRIMARY KEY ,
    cliente_id INT,
    alojamiento_id INT,
    fecha_ingreso date,
    fecha_salida date,
    FOREIGN KEY (cliente_id) REFERENCES cliente(usuario_id),
    FOREIGN KEY (alojamiento_id) REFERENCES alojamiento(alojamiento_id)
);

-- drop table if exists pago_tarjeta;
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

-- drop table if exists pago_paypal;
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

-- drop table if exists pago_googlepay;
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

-- drop table if exists fechas_reservadas;
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
    comentario TEXT,
    calificacion DECIMAL(3, 2),
    PRIMARY KEY(cliente_id,alojamiento_id),
    FOREIGN KEY (cliente_id) REFERENCES cliente(usuario_id),
    FOREIGN KEY (alojamiento_id) REFERENCES alojamiento(alojamiento_id)
);

##############################################################################################################
###	PROCEDURES	##############################################################################################

-- sp para comprobar si hay reservas entre las fechas realizadas por el cliente
drop procedure if exists fechaReservada;
DELIMITER //
create procedure fechaReservada(in fechaI date,in fechaS date,in alojamiento_id int,out respuesta int)
begin
	set @cantidad = (Select count(fr.fecha) 
					from fechas_reservadas fr
					where fr.fecha between fechaI and fechaS and fr.alojamiento_id=alojamiento_id);
    if (@cantidad>0) then
		select 0 into respuesta;
	else
		select 1 into respuesta;
	end if;
end//
DELIMITER ;

call fechaReservada('2023-08-10','2023-08-11',1009,@respuesta);
select @respuesta;

-- otro sp---------------------------------------------------------------------------

##############################################################################################################
###	TRIGGERS	##############################################################################################

-- crea las fechas reservadas de forma automatica una vez se crea una reserva ----------------------------------------
 drop trigger if exists crearFechaReservada;
DELIMITER //
create trigger crearFechaReservada after insert ON reserva
for each row
begin
	declare fechaIn date;
    set fechaIn = new.fecha_ingreso;
    while (fechaIn<=new.fecha_salida) do
		insert into fechas_reservadas values(fechaIn,new.alojamiento_id,new.reserva_id);
        select DATE_ADD(fechaIn,interval 1 day) INTO fechaIn;
    end while;
end //
DELIMITER ; 


-- verifica que no se reserve en un periodo con fechas ya reservadas	--------------------------------------------
drop trigger if exists fechaEstaReservada;
DELIMITER //
create trigger fechaEstaReservada before insert on reserva
for each row
begin
    call fechaReservada(new.fecha_ingreso,new.fecha_salida,new.alojamiento_id,@reservas);
    if convert(new.fecha_ingreso,date)<=convert(new.fecha_salida,date) then
		if ((select @reservas)=0) then
			signal sqlstate '11111' set message_text='Existen fechas ya reservadas';		
		end if;
	else
		signal sqlstate '22222' set message_text='Ingreso erroneo de fechas';
	end if;
end//
DELIMITER ;

-- otro trigger --------------------------------------------------------------------------------------------------------------


######################################################################################################################################

###	ANFITRIONES	##############################################################################################

insert into anfitrion values(10,'Luis','luisriv10','0987654321','lrivadeneira@gmail.com','Guasmo norte coop. Galapagos',true);
insert into anfitrion values(11,'Angel','anto11','0234567890','angeltorres@gmail.com','Guasmo norte coop. 25 de enero',true);
insert into anfitrion values(12,'Emilia','emigomez12','0357908642','emigomez@gmail.com','Av. 25 de julio y Ernesto Alban',true);
insert into anfitrion values(13,'Michelle','michip13','0029384576','michelleperalta@gmail.com','Av. Dolores Sucre y Av. 25 de julio',true);
insert into anfitrion values(14,'Dara','darat14','0281973465','daratriviño@gmail.com','La sopeña',true);
insert into anfitrion values(15,'Alejandro','alevera15','0784913473','alevera@gmail.com','Floresta 2',true);
insert into anfitrion values(16,'Jostin','jossozo16','0026574893','jostinsozo@gmail.com','Barrio Centenario',true);
insert into anfitrion values(17,'Alessandro','alemoreno17','0839465901','sandromoreno@gmail.com','La granada',true);
insert into anfitrion values(18,'Josseline','jossolis18','0204936173','josssolis@gmail.com','Puerto Azul',true);
insert into anfitrion values(19,'Camila','camileon19','07354800274','camileon@gmail.com','Barrio Cuba',true);

###	ALOJAMIENTOS	##############################################################################################

insert into alojamiento values(1001,10,45,3,'Salinas-Ecuador',4.3,12.5);
insert into alojamiento values(1002,11,80,4,'Gyayaquil-Ecuador',4.8,31.3);
insert into alojamiento values(1003,12,70,2,'Manta-Ecuador',4.4,21);
insert into alojamiento values(1004,13,22,1,'Ayangue-Ecuador',3.8,7.5);
insert into alojamiento values(1005,14,95,4,'Montañita-Ecuador',4.7,18);
insert into alojamiento values(1006,15,120,5,'Quito-Ecuador',4.2,41.7);
insert into alojamiento values(1007,16,15,1,'Duran-Ecuador',2.7,11.5);
insert into alojamiento values(1008,17,35,2,'Alausi-Ecuador',3.3,16);
insert into alojamiento values(1009,18,55,3,'Tena-Ecuador',4.2,14.3);
insert into alojamiento values(1010,19,50,3,'Milagro-Ecuador',3.6,11.6);

###	cLIENTES	##############################################################################################

insert into cliente values(20,'Anthony','anthonyc20','0987654321','acastro@gmail.com','Guasmo norte',false);
insert into cliente values(21,'Angello','alvancon21','0234567890','alvancon@gmail.com','Pradera 1',false);
insert into cliente values(22,'Thais','thaivillon22','0357908642','tvillon@gmail.com','Barrio Cuba',false);
insert into cliente values(23,'Shirley','shirleyc23','0029384576','scuero@gmail.com','Los almendros',false);
insert into cliente values(24,'Jonai','jjonaiv24','0281973465','jonaivargas@gmail.com','Guasmo Central',false);
insert into cliente values(25,'Abigail','abisolorza25','0784913473','abisolorzano@gmail.com','Barrio 9 de octubre',false);
insert into cliente values(26,'Jennifer','jennybu26','0026574893','jennyburbano@gmail.com','Barrio centenario',false);
insert into cliente values(27,'Paula','paugcu27','0839465901','paulagomez@gmail.com','Urbasur',false);
insert into cliente values(28,'Alexa','alexale28','0204936173','alexaleon@gmail.com','Pradera 3',false);
insert into cliente values(29,'Giuliano','giusan29','07354800274','giusanchez@gmail.com','Guasmo Sur',false);


###	RESERVACIONES	##############################################################################################

insert into reserva values(9001,20,1001,"2023-08-12","2023-08-14"); 
insert into reserva values(9002,20,1002,"2023-09-05","2023-09-11");
insert into reserva values(9003,21,1003,"2023-08-04","2023-08-17"); 
insert into reserva values(9004,20,1004,"2023-08-25","2023-08-27"); 
insert into reserva values(9005,24,1005,"2023-09-10","2023-09-10");
insert into reserva values(9006,25,1006,"2023-08-02","2023-08-05"); 
insert into reserva values(9007,20,1007,"2023-08-29","2023-09-02");
insert into reserva values(9008,22,1008,"2023-08-14","2023-08-15"); 
insert into reserva values(9009,28,1009,"2023-09-13","2023-09-21");
insert into reserva values(9010,29,1010,"2023-09-23","2023-09-24");

###	REGLAS	##############################################################################################

insert into regla_alojamiento values(1001,1,'No se permiten mascotas.');
insert into regla_alojamiento values(1001,2,'Recoger su basura antes de salir.');
insert into regla_alojamiento values(1003,3,'Mantener el aire acondiconado a una temperatura de 20 grados.');
insert into regla_alojamiento values(1004,4,'No realizar fiestas.');
insert into regla_alojamiento values(1005,5,'Prohibido fumar dentro del alojamiento.');
insert into regla_alojamiento values(1005,6,'La salida es a las 12 del mediodia.');
insert into regla_alojamiento values(1005,7,'Cualquier pedido de servicio a la habitacion se le sumara al cliente.');
insert into regla_alojamiento values(1008,8,'Evitar saltar sobre la cama.');
insert into regla_alojamiento values(1009,9,'No se puede hacer bulla despues de las 9 de la noche.');
insert into regla_alojamiento values(1010,10,'El anfitrion no se responsabiliza por perdidas u olvidos materiales.');


###	SERVICIOS	##############################################################################################

insert into servicio_alojamiento values(1001,1,'Servicio a la habitacion');
insert into servicio_alojamiento values(1005,2,'Servicio de seguridad.');
insert into servicio_alojamiento values(1003,3,'Minibar con bebidas');
insert into servicio_alojamiento values(1007,4,'Salon para fiestas.');
insert into servicio_alojamiento values(1005,5,'Servicio a la habitacion.');
insert into servicio_alojamiento values(1006,6,'Servicio de limpieza diario.');
insert into servicio_alojamiento values(1004,7,'bebidas ilimitadas del minibar');
insert into servicio_alojamiento values(1008,8,'Piscina compartida.');
insert into servicio_alojamiento values(1009,9,'Playa privada.');
insert into servicio_alojamiento values(1010,10,'Zona de acampar privado.');

###	RESEÑAS	##############################################################################################

insert into resenia values(20,1001,"Buen ambiente, vecindario tranquilo.",4.2);
insert into resenia values(21,1003,"No es lo que se ve en la pagina, lugar asqueroso.",2.3);
insert into resenia values(22,1006,"Buena atencion de parte del anfitrion, posee buena vista",4.8);
insert into resenia values(25,1008,"Muy regular, esperabalojamientoa mas",3.5);

###	FAVORITOS	##############################################################################################

insert into lista_favorito values(1001,20);
insert into lista_favorito values(1002,20);
insert into lista_favorito values(1003,23);
insert into lista_favorito values(1004,27);
insert into lista_favorito values(1005,25);
insert into lista_favorito values(1006,24);
