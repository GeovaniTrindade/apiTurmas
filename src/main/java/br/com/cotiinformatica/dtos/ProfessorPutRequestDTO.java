package br.com.cotiinformatica.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class ProfessorPutRequestDTO {
	
	private UUID idProfessor;
	private String nome;
	private String telefone;

}
