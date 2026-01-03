create table produtos(
    id bigint not null auto_increment,
    nomeDoProduto varchar(150) not null,
    ncm varchar(8) not null,
    valorDeVenda decimal(10,2) not null,
    valorDeCusto decimal(10,2),
    unidadeDeMedida varchar (5) not null,
    codigoSku varchar(15),
    codigoDeBarras varchar(13),
    cfopPreferencial varchar(4),
    tipoDeCadastroProduto varchar(7) not null,
    primary key(id)
);