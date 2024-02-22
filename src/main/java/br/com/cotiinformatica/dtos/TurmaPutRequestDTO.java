package br.com.cotiinformatica.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class TurmaPutRequestDTO {
	
	private UUID idTurma;
	private String nome;
	private String dataInicio;
	private String dataTermino;
	private UUID professor_id;

}
