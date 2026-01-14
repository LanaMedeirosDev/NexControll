alter table produtos add ativoProduto tinyint;
update produtos set ativoProduto = 1;
alter table produtos modify column ativoProduto tinyint not null;