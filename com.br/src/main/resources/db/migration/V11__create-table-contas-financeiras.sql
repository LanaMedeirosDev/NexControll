create table contas(
    id bigint not null auto_increment,
    nomeConta varchar(50) not null,
    ativo boolean,
    primary key(id)
);