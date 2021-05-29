/**
 * Projeto 3: Carrinho de compra
 
 *@author Caique Oliveira da Silva/ *@author Gabriel arias
 */
 
 create database dbcarrinho;
use dbcarrinho;

create table carrinho (
	codigo int primary key auto_increment,
    produto varchar(100) not null,
    quantidade int not null,
    valor decimal(10,2)
);

describe carrinho;

insert into carrinho
(codigo,produto,quantidade,valor)
values
('11111111','bola',50,45.50);

insert into carrinho
(codigo,produto,quantidade,valor)
values
('22222222','chuteira',20,89.50);

insert into carrinho
(codigo,produto,quantidade,valor)
values
('33333333','calcao',30,20.50);

insert into carrinho
(codigo,produto,quantidade,valor)
values
('44444444','meiao',80,15.50);

insert into carrinho
(codigo,produto,quantidade,valor)
values
('55555555','caneleira',50,35.50);

insert into carrinho
(codigo,produto,quantidade,valor)
values
('66666666','luva',70,55.50);

update carrinho set produto = 'luva', valor = '60' where codigo='66666666';

delete from carrinho where codigo= '55555555';

select * from carrinho;


 select * from carrinho order by produto;
 
 select sum(valor * quantidade) as Total from carrinho;



 