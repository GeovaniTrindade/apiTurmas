package br.com.cotiinformatica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.entities.Professor;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class ProfessorRepository {

	public void insert(Professor professor) throws Exception {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = connection
				.prepareStatement("insert into professor(idProfessor, nome, telefone) values(?,?,?)");

		statement.setObject(1, professor.getIdProfessor());
		statement.setString(2, professor.getNome());
		statement.setString(3, professor.getTelefone());
		statement.execute();

		connection.close();
	}

	public void update(Professor professor) throws Exception {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = connection
				.prepareStatement("update professor set nome=?, telefone=? where idProfessor=?");

		statement.setString(1, professor.getNome());
		statement.setString(2, professor.getTelefone());
		statement.setObject(3, professor.getIdProfessor());
		statement.execute();

		connection.close();
	}

	public Professor findById(UUID idProfessor) throws Exception {

		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement("select * from professor where idProfessor = ?");
		statement.setObject(1, idProfessor);
		ResultSet resultSet = statement.executeQuery();

		Professor professor = null;

		if (resultSet.next()) {

			professor = new Professor();

			professor.setIdProfessor(UUID.fromString(resultSet.getString("idProfessor")));
			professor.setNome(resultSet.getString("nome"));
			professor.setTelefone(resultSet.getString("telefone"));

		}

		connection.close();
		return professor;
	}

	public void delete(Professor professor) throws Exception {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = connection.prepareStatement("delete from professor where idProfessor=?");

		statement.setObject(1, professor.getIdProfessor());
		statement.execute();

		connection.close();
	}

	public List<Professor> findAll() throws Exception {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = connection.prepareStatement("select * from professor");
		ResultSet resultSet = statement.executeQuery();

		List<Professor> lista = new ArrayList<Professor>();

		while (resultSet.next()) {

			Professor professor = new Professor();

			professor.setIdProfessor(UUID.fromString(resultSet.getString("idProfessor")));
			professor.setNome(resultSet.getString("nome"));
			professor.setTelefone(resultSet.getString("telefone"));

			lista.add(professor);
		}

		connection.close();
		return lista;
	}

}
