create table clientes(
    id bigint not null auto_increment,
    nome varchar(100) not null,
    cpfCnpj varchar(100) not null unique,
    ieRg varchar(20) not null unique,
    telefone varchar (28) not  null,
    celular varchar (28) not null,
    tipoDeCadastro varchar(100) not null,
    logradouro varchar(100) not null,
    bairro varchar(100) not null,
    cep varchar(9) not null,
    complemento varchar(100),
    numero varchar(20),
    uf char(2) not null,
    cidade varchar(100) not null,
    primary key(id)
);