package br.com.cotiinformatica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.entities.Professor;
import br.com.cotiinformatica.entities.Turma;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class TurmaRepository {

	public void insert(Turma turma) throws Exception {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = connection.prepareStatement(
				"insert into turma (idTurma, nome, dataInicio, dataTermino, professor_id) VALUES (?,?,?,?,?)");
		statement.setObject(1, turma.getIdTurma());
		statement.setString(2, turma.getNome());
		statement.setDate(3, new java.sql.Date(turma.getDataInicio().getTime()));
		statement.setDate(4, new java.sql.Date(turma.getDataTermino().getTime()));
		statement.setObject(5, turma.getProfessor().getIdProfessor());
		statement.execute();

		connection.close();
	}

	public void update(Turma turma) throws Exception {
		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = connection.prepareStatement(
				"update turma SET nome=?, dataInicio=?, dataTermino=?, professor_id=? WHERE idTurma=?");

		statement.setObject(1, turma.getNome());
		statement.setDate(2, new java.sql.Date(turma.getDataInicio().getTime()));
		statement.setDate(3, new java.sql.Date(turma.getDataTermino().getTime()));
		statement.setObject(4, turma.getProfessor().getIdProfessor());
		statement.setObject(5, turma.getIdTurma());
		statement.execute();

		connection.close();
	}

	public Turma findById(UUID idTurma) throws Exception {

		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(
				"select t.idTurma, t.nome as nomeTurma, t.dataInicio, t.dataTermino, p.idProfessor as IdProfessor, p.nome as nomeProfessor, p.telefone "
						+ "from turma t inner join professor p on p.idProfessor = t.professor_id " + "where idTurma=?");
		statement.setObject(1, idTurma);

		ResultSet resultSet = statement.executeQuery();

		Turma turma = null;

		if (resultSet.next()) {

			turma = new Turma();
			turma.setProfessor(new Professor());

			turma.setIdTurma(UUID.fromString(resultSet.getString("idTurma")));
			turma.setNome(resultSet.getString("nomeTurma"));
			turma.setDataInicio(new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("dataInicio")));
			turma.setDataTermino(new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("dataTermino")));
			turma.getProfessor().setIdProfessor(UUID.fromString(resultSet.getString("idProfessor")));
			turma.getProfessor().setNome(resultSet.getString("nomeProfessor"));
			turma.getProfessor().setTelefone(resultSet.getString("telefone"));

		}

		connection.close();
		return turma;
	}

	public void delete(Turma turma) throws Exception {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = connection.prepareStatement("delete from turma where idTurma=?");

		statement.setObject(1, turma.getIdTurma());
		statement.execute();

		connection.close();
	}

	public List<Turma> findAll() throws Exception {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = connection.prepareStatement(
				"select t.idTurma, t.nome as nomeTurma, t.dataInicio, t.dataTermino, p.idProfessor as IdProfessor, p.nome as nomeProfessor, p.telefone "
						+ "from turma t inner join professor p on p.idProfessor = t.professor_id " + "order by t.nome");

		ResultSet resultSet = statement.executeQuery();

		List<Turma> lista = new ArrayList<Turma>();

		while (resultSet.next()) {

			Turma turma = new Turma();
			turma.setProfessor(new Professor());

			turma.setIdTurma(UUID.fromString(resultSet.getString("idTurma")));
			turma.setNome(resultSet.getString("nomeTurma"));
			turma.setDataInicio(new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("dataInicio")));
			turma.setDataTermino(new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("dataTermino")));
			turma.getProfessor().setIdProfessor(UUID.fromString(resultSet.getString("idProfessor")));
			turma.getProfessor().setNome(resultSet.getString("nomeProfessor"));
			turma.getProfessor().setTelefone(resultSet.getString("telefone"));

			lista.add(turma);
		}

		connection.close();
		return lista;
	}

}
