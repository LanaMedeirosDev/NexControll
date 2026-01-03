create table usuarios(
    id bigint not null auto_increment,
    nomeUsuario varchar(100) not null,
    email varchar(100) not null unique,
    senha varchar(8) not null unique,
    celularDoUsuario varchar (28) not null,
    logradouroUsuario varchar(100) not null,
    bairroUsuario varchar(100) not null,
    cepUsuario varchar(9) not null,
    complementoUsuario varchar(100),
    numeroUsuario varchar(20),
    ufUsuario char(2) not null,
    cidadeUsuario varchar(100) not null,
    primary key(id)
);