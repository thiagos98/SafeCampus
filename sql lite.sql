create table sistema(
cod_sistema int not null  primary key,
nome_sistema TEXT,
cnpj TEXT
);

delimiter $$
create procedure sp_cadastro_sistema(nome TEXT,cnpj TEXT)
begin
insert into sistema (nome_sistema,cnpj) values ( nome,cnpj);
end $$
delimiter ;

call sp_cadastro_sistema();
select * from sistema;

create table administrador(
cod_adm int  not null primary key,
nome_adm TEXT,
email_adm TEXT,
senha_adm TEXT,
cod_sistema int,
constraint fk1_cod_sistema
foreign key (cod_sistema)
references sistema (cod_sistema)
);
delimiter $$

create procedure sp_cadastro_administrador(nome_adm TEXT,email_adm TEXT,
senha_adm TEXT,cod_sistema int)
begin
insert into administrador (nome_adm,email_adm,senha_adm,cod_sistema ) values
(nome_adm,email_adm,senha_adm,cod_sistema );
end $$
delimiter ;

call sp_cadastro_administrador();
select * from administrador;

delimiter $$
create procedure alterar_informação_do_administrador( cod int, nome_adm TEXT,email_adm TEXT,senha_adm TEXT)
begin
if exists(select cod_adm from administrador where (cod_adm = cod_adm )) then
update administrador
set nome_adm = @nome_adm,
email_adm = @email_adm,
senha_adm = @senha_adm
where cod_adm = cod;
end if;
end $$
delimiter ;

set @nome_adm = '';
set @email_adm ='';
set @senha_adm = '';
call alterar_informação_do_administrador('',@nome_adm,@email_adm,@senha_adm);
select * from administrador
where cod_adm = '';

delimiter $$
create procedure excluir_o_administrador( cod int)
begin
if exists(select cod_adm from administrador where (cod_adm = cod_adm )) then
delete from administrador
where cod_adm = cod;
end if;
end $$
delimiter ;

SET FOREIGN_KEY_CHECKS = 0;
set @cod= '';
call excluir_o_administrador(@cod);
select * from administrador;

create table usuario (
cod_usuario int not null  primary key,
nome_usuario TEXT,
email_usuario TEXT,
senha_usuario TEXT,
status_usuario TEXT,
cod_sistema int,
constraint fk2_cod_sistema
foreign key (cod_sistema)
references sistema (cod_sistema)
);

create trigger verifica_status_usuario before insert
on usuario
for each row
set new.status_usuario ='ATIVO';
delimiter $$

create procedure sp_cadastro_usuario (nome_usuario TEXT,email_usuario TEXT,senha_usuario TEXT,cod_sistema int )
begin
insert into usuario (nome_usuario,email_usuario,senha_usuario,cod_sistema)
values (nome_usuario,email_usuario,senha_usuario,cod_sistema);
end $$
delimiter ;

call sp_cadastro_usuario ();
select * from usuario;

delimiter $$
create procedure alterar_informação_do_usuario( cod int, nome_usuario TEXT,email_usuario TEXT,senha_usuario TEXT,status_usuario TEXT)
begin
if exists(select cod_usuario from usuario where (cod_usuario = cod_usuario )) then
update usuario
set nome_usuario = @nome_usuario,
email_usuario = @email_usuario,
senha_usuario = @senha_usuario,
status_usuario = @status_usuario
where cod_usuario = cod;
end if;
end $$
delimiter ;

set @nome_usuario = '';
set @email_usuario = '';
set @senha_usuario = '';
set @status_usuario = '';
call alterar_informação_do_usuario('',@nome_usuario,@email_usuario,@senha_usuario,@status_usuario);
select * from usuario
where cod_usuario = '';

delimiter $$
create procedure excluir_o_usuario( cod int)
begin
if exists(select cod_usuario from usuario where (cod_usuario = cod_usuario )) then
delete from usuario
where cod_usuario = cod;
end if;
end $$
delimiter ;
SET FOREIGN_KEY_CHECKS = 0;
set @cod= '';
call excluir_o_usuario(@cod);
select * from usuario;

create table ocorrencia (
cod_ocorrencia int not null  primary key,
data date,
hora time,
descricao TEXT,
categoria TEXT,
cod_sistema int,
cod_usuario int,
nome_usuario TEXT,
status_ocorrencia TEXT,
constraint fk3_cod_sistema
foreign key (cod_sistema)
references sistema (cod_sistema),
constraint fk3_cod_usuario
foreign key (cod_usuario)
references usuario (cod_usuario)
);

create trigger verifica_status_ocorrencia before insert
on ocorrencia
for each row
set new.status_ocorrencia ='OCORRENCIA REGISTRADA NO SISTEMA !';

delimiter $$
create procedure sp_cadastro_ocorrencia (data date,hora time,descricao TEXT,
categoria TEXT,cod_sistema int,cod_usuario int,nome_usuario TEXT)
begin
insert into ocorrencia (data,hora,descricao,categoria,
cod_sistema,cod_usuario,nome_usuario)
values (data,hora,descricao,categoria,
cod_sistema,cod_usuario,nome_usuario);
end $$
delimiter ;

call sp_cadastro_ocorrencia ();
select * from ocorrencia;

delimiter $$
create procedure excluir_a_ocorrencia( cod int)
begin
if exists(select cod_ocorrencia from ocorrencia where (cod_ocorrencia = cod_ocorrencia )) then
delete from ocorrencia
where cod_ocorrencia = cod;
end if;
end $$
delimiter ;

set @cod= '';
call excluir_a_ocorrencia(@cod);
select * from ocorrencia;

select nome_usuario
from usuario
order by nome_usuario asc;

delimiter $$
create function verificar_administradores_existentes(email_adm varchar (30),senha_adm TEXT)
returns TEXT
begin
declare mensagem TEXT;
case
when email_adm = 'email cadastrado aqui' and senha_adm = 'senha cadastrada aqui' then
set mensagem ='ACESSO PERMITIDO.VOCÊ POSSUI PRIVILEGIOS DE ADMINISTRADOR';
when email_adm = 'email cadastrado aqui'  and senha_adm = 'senha cadastrada aqui' then
set mensagem ='ACESSO PERMITIDO.VOCÊ POSSUI PRIVILEGIOS DE ADMINISTRADOR';
else
set mensagem ='ACESSO NEGADO. SEM PRIVILEGIOS DE ADMINISTRADOR';
end case;
return mensagem;
end $$
delimiter ;

select verificar_administradores_existentes() as 'STATUS DA SITUAÇÃO';

select usuario.nome_usuario,email_usuario,data,hora,descricao
from usuario
inner join ocorrencia
on usuario.cod_usuario = ocorrencia.cod_usuario;
