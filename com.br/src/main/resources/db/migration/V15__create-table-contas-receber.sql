create table receitas (
    id bigint not null auto_increment,
    descricao varchar(150) not null,
    valorReceita decimal(10,2) not null,
    dataVencimento date not null,
    dataRecebimento date,
    statusReceita varchar(8) not null,

    pagamento_id bigint,

    primary key (id),
    constraint fk_receita_forma_pagamento
        foreign key (forma_pagamento_id)
        references forma_pagamento(id)
);