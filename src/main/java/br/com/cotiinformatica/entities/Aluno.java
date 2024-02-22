package br.com.cotiinformatica.entities;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Aluno {

	private UUID idAluno;
	private String nome;
	private String matricula;
	private String cpf;

}
