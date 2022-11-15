CREATE SCHEMA diary;
CREATE sequence user_id_seq  START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647;
CREATE sequence measurement_id_seq  START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647;

CREATE TABLE user_entity (
        id INTEGER NOT NULL DEFAULT nextval('user_id_seq'),
        name varchar(50) not null,
        email varchar(50) not null ,
        password varchar(100) not null,
        UNIQUE(name,email));

CREATE TABLE measurement_entity (
        id INTEGER NOT NULL DEFAULT nextval('measurement_id_seq'),
        date DATE NOT NULL DEFAULT CURRENT_DATE,
        time TIME not null ,
        rr varchar(50) not null,
        pulse varchar(50) not null,
        id_user INTEGER not null,
        description text);
