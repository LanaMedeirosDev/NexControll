create table pagamento(
    id bigint not null auto_increment,
    nomePagamento varchar(100) not null,
    ativo boolean default true,

    conta_id bigint not null,

    primary key(id),
    constraint fk_forma_pagamento_conta
        foreign key (conta_id) references contas(id)
);