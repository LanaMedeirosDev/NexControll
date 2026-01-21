create table usuario_produto (
    id bigint primary key auto_increment,
    usuario_id bigint not null,
    produto_id bigint not null,
    data_cadastro date not null,
    ativo boolean not null,
    constraint fk_usuario_produto_usuario
        foreign key (usuario_id)
        references usuario(id),

    constraint fk_usuario_produto_produto
        foreign key (produto_id)
        references produtos(id),

    constraint uk_usuario_produto
        unique (usuario_id, produto_id)
);