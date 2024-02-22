package br.com.cotiinformatica.dtos;

import lombok.Data;

@Data
public class AlunoPostRequestDTO {

	private String nome;
	private String matricula;
	private String cpf;
}
