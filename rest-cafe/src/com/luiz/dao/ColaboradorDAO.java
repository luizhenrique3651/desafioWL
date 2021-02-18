package com.luiz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.luiz.config.ConectaBanco;
import com.luiz.model.Colaborador;






//DAO é o data access object, nele a gente coloca todos os métodos para realizar o CRUD no banco, insert, select, update e remove

public class ColaboradorDAO {
	
	
	public int addColab(Colaborador colab) throws Exception {
		//id gerado nas operações http, quis fazer um sistema de registro de ids mas não consegui implementar completamenter
		int idGerado = 0;
		//conexao com o banco instanciada
		Connection conexao = ConectaBanco.getConnection();
		// instrução SQL salva em uma string, onde as interrogações são os parametros que serão recebidos
		String sql = "INSERT INTO tb_colaborador(cpf_colab, nome_colab) VALUES(?, ?)";
		//prepared statement que realiza a conexão com o banco e monta a query SQL com os parametros recebidos
		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		//a preparação dos parametros é feita pelos getters da entidade.
		statement.setInt(1, colab.getCpf_colab());
		statement.setString(2, colab.getNome_colab());
		//query executada
		statement.execute();
		//gerada a chave de id
		ResultSet rs = statement.getGeneratedKeys();
		//condição para caso a instrução sql seja bem executada, um id será gerado para essa mesma instrução
		if (rs.next()) {
			idGerado = rs.getInt(1);
		}
		
		return idGerado;
	}
	
	//os outros metodos abaixo seguem o mesmo parametro, com prepared statement e result set.
	
	public List<Colaborador> listarColaboradores() throws Exception {
		List<Colaborador> lista = new ArrayList<>();

		Connection conexao = ConectaBanco.getConnection();

		String sql = "SELECT * FROM tb_colaborador";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Colaborador colab = new Colaborador();
			colab.setCpf_colab(rs.getInt("cpf_colab"));
			colab.setNome_colab(rs.getString("nome_colab"));
			

			lista.add(colab);
		}

		return lista;
	}
	
	
	public Colaborador buscarColabPorCpf(int cpf_colab) throws Exception {
		Colaborador colab = null;

		Connection conexao = ConectaBanco.getConnection();

		String sql = "SELECT * FROM tb_colaborador WHERE cpf_colab = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, cpf_colab);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			colab = new Colaborador();
			colab.setCpf_colab(rs.getInt("cpf_colab"));
			colab.setNome_colab(rs.getString("nome_colab"));
		}

		return colab;
	}
	
	public void editarColaborador(Colaborador colaborador, int cpf_colab) throws Exception {
		Connection conexao = ConectaBanco.getConnection();

		String sql = "UPDATE tb_colaborador SET nome_colab = ? WHERE cpf_colab = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, colaborador.getNome_colab());
		statement.setInt(2, colaborador.getCpf_colab());
		statement.execute();
	}
	
	
	public void removerColaborador(int cpf_colab) throws Exception {
		Connection conexao = ConectaBanco.getConnection();

		String sql = "DELETE FROM tb_colaborador WHERE cpf_colab = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, cpf_colab);
		statement.execute();
	}

}



