alter table receitas add ativo tinyint;
update receitas set ativo = 1
alter table receitas modify column ativo tinyint not null;