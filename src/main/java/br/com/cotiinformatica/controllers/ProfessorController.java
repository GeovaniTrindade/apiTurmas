package br.com.cotiinformatica.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.ProfessorPostRequestDTO;
import br.com.cotiinformatica.dtos.ProfessorPutRequestDTO;
import br.com.cotiinformatica.entities.Professor;
import br.com.cotiinformatica.repositories.ProfessorRepository;

@RestController
@RequestMapping(value = "/api/professores")
public class ProfessorController {

	@PostMapping()
	public ResponseEntity<String> post(@RequestBody ProfessorPostRequestDTO dto) {

		try {

			// preenchendo os dados do professor
			Professor professor = new Professor();
			professor.setIdProfessor(UUID.randomUUID());
			professor.setNome(dto.getNome());
			professor.setTelefone(dto.getTelefone());

			// cadastrar o professor no banco de dados
			ProfessorRepository professorRepository = new ProfessorRepository();
			professorRepository.insert(professor);

			// HTTP 201 - CREATED
			return ResponseEntity.status(201).body("Professor cadastrado com sucesso.");
		} catch (Exception e) {

			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@PutMapping()
	public ResponseEntity<String> put(@RequestBody ProfessorPutRequestDTO dto) {

		try {

			// consultar o professor no banco de dados através do ID
			ProfessorRepository professorRepository = new ProfessorRepository();
			Professor professor = professorRepository.findById(dto.getIdProfessor());

			// verificar se o professor não foi encontrado
			if (professor == null)
				return ResponseEntity.status(400) // HTTP 400 - BAD REQUEST
						.body("Professor não encontrado. Verifique o ID informado.");

			// modificando os dados do professor
			professor.setNome(dto.getNome());
			professor.setTelefone(dto.getTelefone());

			// atualizar o cliente no banco de dados
			professorRepository.update(professor);

			// HTTP 200 - OK
			return ResponseEntity.status(200).body("Professor atualizado com sucesso.");
		} catch (Exception e) {

			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@DeleteMapping("{idProfessor}")
	public ResponseEntity<String> delete(@PathVariable("idProfessor") UUID idProfessor) {

		try {

			// consultar os dados do Professor através do ID
			ProfessorRepository professorRepository = new ProfessorRepository();
			Professor professor = professorRepository.findById(idProfessor);

			// verificando se o Professor não foi encontrado
			if (professor == null)
				// HTTP 400 - BAD REQUEST
				return ResponseEntity.status(400).body("Professor não encontrado. Verifique o ID informado.");

			// excluindo o Professor
			professorRepository.delete(professor);

			// HTTP 200 - OK
			return ResponseEntity.status(200).body("Professor excluído com sucesso.");
		} catch (Exception e) {
			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping()
	public ResponseEntity<List<Professor>> getAll() throws Exception {

		try {

			ProfessorRepository professorRepository = new ProfessorRepository();
			List<Professor> professores = professorRepository.findAll();

			if (professores.size() == 0) // se a lista está vazia
				// HTTP 204 - NO CONTENT
				return ResponseEntity.status(204).body(null);

			// HTTP 200 - OK
			return ResponseEntity.status(200).body(professores);

		} catch (Exception e) {
			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("{idProfessor}")
	public ResponseEntity<Professor> getById(@PathVariable("idProfessor") UUID idProfessor) throws Exception {

		try {

			ProfessorRepository professorRepository = new ProfessorRepository();
			Professor professor = professorRepository.findById(idProfessor);

			if (professor == null)
				// HTTP 204 - NO CONTENT
				return ResponseEntity.status(204).body(null);

			// HTTP 200 - OK
			return ResponseEntity.status(200).body(professor);
		} catch (Exception e) {
			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(null);
		}
	}

}
