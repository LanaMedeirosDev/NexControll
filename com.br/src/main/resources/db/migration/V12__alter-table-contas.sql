alter table contas add ativo tinyint;
update contas set ativo = 1;
alter table contas modify column ativo tinyint not null;