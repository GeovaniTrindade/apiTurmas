package br.com.cotiinformatica.entities;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class Turma {
	
	private UUID idTurma;
	private String nome;
	private Date dataInicio;
	private Date dataTermino;
	private Professor professor;
}
