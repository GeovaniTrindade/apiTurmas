--criar a tabela de planos
create table aluno(
	idAluno		uuid			primary key,
	nome	    varchar(25)	    not null,
	matricula   varchar(25)     not null unique,
	cpf         varchar(11)     not null unique
	
);

-- inserindo aluno
insert into aluno(idAluno, nome, matricula, cpf)values(gen_random_uuid(), 'Geovani Trindade', '12345678910', '12123434567');


-----------------------------------------------------------------------------------------------------------------------------------

--criar a tabela de professores
create table professor(
	idProfessor		uuid			primary key,
	nome	    varchar(25)	    not null,
	telefone    varchar(15)     not null unique
	
);

insert into professor(idProfessor, nome, telefone)values(gen_random_uuid(), 'Sergio Mendes', '98787654');
insert into professor(idProfessor, nome, telefone)values(gen_random_uuid(), 'Wallace', '96545678');
insert into professor(idProfessor, nome, telefone)values(gen_random_uuid(), 'David', '99987654');

-------------------------------------------------------------------------------------------------------------------------------------------


--criar a tabela de clientes
create table turma(
	idTurma		        uuid			primary key,
	nome		    varchar(150)	not null,
	dataInicio 	    Date,
	dataTermino		Date,
	professor_id	uuid			not null,
	foreign key(professor_id) references professor(idProfessor)
);

insert into turma(idTurma, nome, dataInicio, dataTermino, professor_id)values(gen_random_uuid(), 'JAVA', '22/02/2024', '22/02/2025', 'f9746c4d-c69f-49e4-91d1-dd9ebb8dd604' );
insert into turma(idTurma, nome, dataInicio, dataTermino, professor_id)values(gen_random_uuid(), 'ANGULAR', '01/03/2024', '01/03/2025', 'f9746c4d-c69f-49e4-91d1-dd9ebb8dd604' );
insert into turma(idTurma, nome, dataInicio, dataTermino, professor_id)values(gen_random_uuid(), 'WEB DESIGNER', '22/02/2024', '22/02/2025', '1a87e63c-f50a-46be-a46e-88aaeb971f83' );

select t.idTurma, t.nome as NomeTurma, t.dataInicio, t.dataTermino, p.idProfessor as IdProfessor, p.nome as NomeProfessor from turma t inner join professor p on p.idProfessor = t.professor_id order by t.nome;




-------------------------------------------------------------- fazer
--criar a tabela matricula
create table matricula(
	id_matricula	uuid			primary key,
	turma_id		uuid			not null,
	aluno_id		uuid			not null,
	data_matricula 	Date,
	FOREIGN key(turma_id)
		references turma(id_turma),
	FOREIGN key(aluno_id)
		references aluno(id_aluno)
);
