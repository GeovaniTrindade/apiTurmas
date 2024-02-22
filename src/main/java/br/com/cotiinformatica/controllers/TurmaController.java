package br.com.cotiinformatica.controllers;

import java.text.SimpleDateFormat;
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

import br.com.cotiinformatica.dtos.TurmaPostRequestDTO;
import br.com.cotiinformatica.dtos.TurmaPutRequestDTO;
import br.com.cotiinformatica.entities.Professor;
import br.com.cotiinformatica.entities.Turma;
import br.com.cotiinformatica.repositories.ProfessorRepository;
import br.com.cotiinformatica.repositories.TurmaRepository;

@RestController
@RequestMapping(value = "/api/turmas")
public class TurmaController {

	@PostMapping()
	public ResponseEntity<String> post(@RequestBody TurmaPostRequestDTO dto) {

		try {

			// preenchendo os dados da turma
			Turma turma = new Turma();

			turma.setIdTurma(UUID.randomUUID());
			turma.setNome(dto.getNome());
			turma.setDataInicio(new SimpleDateFormat("dd/MM/yyyy").parse(dto.getDataInicio()));
			turma.setDataTermino(new SimpleDateFormat("dd/MM/yyyy").parse(dto.getDataTermino()));

			// consultar o Professor no banco de dados através do ID
			ProfessorRepository professorRepository = new ProfessorRepository();
			Professor professor = professorRepository.findById(dto.getIdProfessor());

			// verificando se o professor não foi encontrado
			if (professor == null)
				// HTTP 400 - BAD REQUEST
				return ResponseEntity.status(400).body("Professor não encontrado. Verifique o ID informado.");

			// associar o professor a turma
			turma.setProfessor(professor);

			// cadastrar a turma no banco de dados
			TurmaRepository turmaRepository = new TurmaRepository();
			turmaRepository.insert(turma);

			// HTTP 201 - CREATED
			return ResponseEntity.status(201).body("Turma cadastrada com sucesso.");
		} catch (Exception e) {

			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@PutMapping()
	public ResponseEntity<String> put(@RequestBody TurmaPutRequestDTO dto) {

		try {

			// consultar Turma no banco de dados através do ID
			TurmaRepository turmaRepository = new TurmaRepository();
			Turma turma = turmaRepository.findById(dto.getIdTurma());

			// verificar se o cliente não foi encontrado
			if (turma == null)
				return ResponseEntity.status(400) // HTTP 400 - BAD REQUEST
						.body("Turma não encontrada. Verifique o ID informado.");

			// consultar o Professor no banco de dados através do ID
			ProfessorRepository professorRepository = new ProfessorRepository();
			Professor professor = professorRepository.findById(dto.getProfessor_id());

			// verificando se o professor não foi encontrado
			if (professor == null)
				// HTTP 400 - BAD REQUEST
				return ResponseEntity.status(400).body("Professor não encontrado. Verifique o ID informado.");

			// modificando os dados da turma
			turma.setNome(dto.getNome());
			turma.setDataInicio(new SimpleDateFormat("dd/MM/yyyy").parse(dto.getDataInicio()));
			turma.setDataTermino(new SimpleDateFormat("dd/MM/yyyy").parse(dto.getDataTermino()));
			turma.setProfessor(professor);

			// atualizar a turma no banco de dados
			turmaRepository.update(turma);

			// HTTP 200 - OK
			return ResponseEntity.status(200).body("Turma atualizada com sucesso.");
		} catch (Exception e) {

			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable("id") UUID idTurma) {

		try {

			// consultar os dados da turma através do ID
			TurmaRepository turmaRepository = new TurmaRepository();
			Turma turma = turmaRepository.findById(idTurma);

			// verificando se a turma não foi encontrada
			if (turma == null)
				// HTTP 400 - BAD REQUEST
				return ResponseEntity.status(400).body("Turma não encontrada. Verifique o ID informado.");

			// excluindo a turma
			turmaRepository.delete(turma);

			// HTTP 200 - OK
			return ResponseEntity.status(200).body("Turma excluída com sucesso.");
		} catch (Exception e) {
			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping()
	public ResponseEntity<List<Turma>> getAll() throws Exception {

		try {

			TurmaRepository turmaRepository = new TurmaRepository();
			List<Turma> turmas = turmaRepository.findAll();

			if (turmas.size() == 0) // se a lista está vazia
				// HTTP 204 - NO CONTENT
				return ResponseEntity.status(204).body(null);

			// HTTP 200 - OK
			return ResponseEntity.status(200).body(turmas);
		} catch (Exception e) {
			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<Turma> getById(@PathVariable("id") UUID idTurma) throws Exception {

		try {
			// consultar os dados da turma através do ID
			TurmaRepository turmaRepository = new TurmaRepository();
			Turma turma = turmaRepository.findById(idTurma);

			if (turma == null)
				// HTTP 204 - NO CONTENT
				return ResponseEntity.status(204).body(null);

			// HTTP 200 - OK
			return ResponseEntity.status(200).body(turma);
		} catch (Exception e) {
			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(null);
		}
	}

}
