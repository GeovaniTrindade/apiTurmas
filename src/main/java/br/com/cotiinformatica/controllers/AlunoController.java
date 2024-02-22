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

import br.com.cotiinformatica.dtos.AlunoPostRequestDTO;
import br.com.cotiinformatica.dtos.AlunoPutRequestDTO;
import br.com.cotiinformatica.entities.Aluno;
import br.com.cotiinformatica.repositories.AlunoRepository;

@RestController
@RequestMapping(value = "/api/alunos")
public class AlunoController {

	@PostMapping
	public ResponseEntity<String> post(@RequestBody AlunoPostRequestDTO dto) {

		try {

			// dados do aluno
			Aluno aluno = new Aluno();

			aluno.setIdAluno(UUID.randomUUID());
			aluno.setNome(dto.getNome());
			aluno.setMatricula(dto.getMatricula());
			aluno.setCpf(dto.getCpf());

			// cadastrar o aluno no banco de dados
			AlunoRepository alunoRepository = new AlunoRepository();
			alunoRepository.insert(aluno);

			// HTTP 201 - CREATED
			return ResponseEntity.status(201).body("Aluno cadastrado com sucesso.");
		} catch (Exception e) {

			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@PutMapping()
	public ResponseEntity<String> put(@RequestBody AlunoPutRequestDTO dto) {

		try {

			// consultar o aluno no banco de dados através do ID
			AlunoRepository alunoRepository = new AlunoRepository();
			Aluno aluno = alunoRepository.findById(dto.getIdAluno());

			// verificar se o aluno não foi encontrado
			if (aluno == null)
				return ResponseEntity.status(400) // HTTP 400 - BAD REQUEST
						.body("Aluno não encontrado. Verifique o ID informado.");

			// modificando os dados do aluno
			aluno.setNome(dto.getNome());
			aluno.setMatricula(dto.getMatricula());
			aluno.setCpf(dto.getCpf());

			// atualizar o aluno no banco de dados
			alunoRepository.update(aluno);

			// HTTP 200 - OK
			return ResponseEntity.status(200).body("Aluno atualizado com sucesso.");
		} catch (Exception e) {

			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@DeleteMapping("{idAluno}")
	public ResponseEntity<String> delete(@PathVariable("idAluno") UUID idAluno) {

		try {

			// consultar os dados do Aluno através do ID
			AlunoRepository alunoRepository = new AlunoRepository();
			Aluno aluno = alunoRepository.findById(idAluno);

			// verificando se o Aluno não foi encontrado
			if (aluno == null)
				// HTTP 400 - BAD REQUEST
				return ResponseEntity.status(400).body("Aluno não encontrado. Verifique o ID informado.");

			// excluindo o cliente
			alunoRepository.delete(aluno);

			// HTTP 200 - OK
			return ResponseEntity.status(200).body("Aluno excluído com sucesso.");
		} catch (Exception e) {
			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping()
	public ResponseEntity<List<Aluno>> getAll() throws Exception {

		try {

			AlunoRepository alunoRepository = new AlunoRepository();
			List<Aluno> alunos = alunoRepository.findAll();

			if (alunos.size() == 0) // se a lista está vazia
				// HTTP 204 - NO CONTENT
				return ResponseEntity.status(204).body(null);

			// HTTP 200 - OK
			return ResponseEntity.status(200).body(alunos);
		} catch (Exception e) {
			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("{idAluno}")
	public ResponseEntity<Aluno> getById(@PathVariable("idAluno") UUID idAluno) throws Exception {

		try {

			AlunoRepository alunoRepository = new AlunoRepository();
			Aluno aluno = alunoRepository.findById(idAluno);

			if (aluno == null)
				// HTTP 204 - NO CONTENT
				return ResponseEntity.status(204).body(null);

			// HTTP 200 - OK
			return ResponseEntity.status(200).body(aluno);
		} catch (Exception e) {
			// HTTP 500 - INTERNAL SERVER ERROR
			return ResponseEntity.status(500).body(null);
		}
	}

}
