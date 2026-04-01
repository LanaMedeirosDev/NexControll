alter table pagamento add ativo tinyint;
update pagamento set ativo = 1;
alter table pagamento modify column ativo tinyint not null;