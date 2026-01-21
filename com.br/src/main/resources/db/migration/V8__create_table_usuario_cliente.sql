create table usuario_cliente (
    id bigint primary key AUTO_INCREMENT,
    usuario_id bigint not null,
    cliente_id bigint not null,
    data_cadastro date not null,
    ativo boolean not null,
    constraint fk_usuario_cliente_usuario
        foreign key (usuario_id)
        references usuario(id),

    constraint fk_usuario_cliente_cliente
                foreign key (cliente_id)
                references clientes(id),

     constraint uk_usuario_cliente
                unique (usuario_id, cliente_id)
);