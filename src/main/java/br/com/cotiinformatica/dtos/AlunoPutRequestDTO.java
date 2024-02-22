package br.com.cotiinformatica.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class AlunoPutRequestDTO {

	private UUID idAluno;
	private String nome;
	private String matricula;
	private String cpf;

}
