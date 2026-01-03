alter table clientes add ativo tinyint;
update clientes set ativo = 1;
alter table clientes modify column ativo tinyint not null;