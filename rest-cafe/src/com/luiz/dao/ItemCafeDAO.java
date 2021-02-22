package com.luiz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.luiz.config.ConectaBanco;
import com.luiz.model.Colaborador;
import com.luiz.model.ItemCafe;

public class ItemCafeDAO {

	
	//select com inner join das chaves primarias e secundarias para chamar os items que serão levados e dizer qual colaborador o vai levar
	public List<ItemCafe> listarItemCafeComColab() throws Exception {
		List<ItemCafe> lista = new ArrayList<>();

		Connection conexao = ConectaBanco.getConnection();

		String sql = "SELECT *  FROM tb_colaborador as c INNER JOIN  item_cafe as i ON c.cpf_colab = i.cpf_colab;";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Colaborador colab = new Colaborador();
			ItemCafe cafe = new ItemCafe();
			colab.setCpf_colab(rs.getInt("cpf_colab"));
			colab.setNome_colab(rs.getString("nome_colab"));
			cafe.setId_item(rs.getInt("id_item"));
			cafe.setCpf_colaborador(rs.getInt("cpf_colaborador"));
			cafe.setNome_item(rs.getString("nome_item"));
			

			lista.add(cafe);
		}

		return lista;
	}
	
	
	//insert do item café
	
	public int addItemCafe(ItemCafe cafe) throws Exception {
		int idGerado = 0;
		Connection conexao = ConectaBanco.getConnection();

		String sql = "INSERT INTO tb_item_cafe(cpf_colaborador, nome_item) VALUES(?, ?)";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, cafe.getCpf_colaborador());
		statement.setString(2, cafe.getNome_item());
		statement.execute();
		
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idGerado = rs.getInt(1);
		}
		
		return idGerado;
	}
	
	//select
	public List<ItemCafe> listarItemCafe() throws Exception {
		List<ItemCafe> lista = new ArrayList<>();

		Connection conexao = ConectaBanco.getConnection();

		String sql = "SELECT * FROM tb_item_café";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			ItemCafe cafe = new ItemCafe();
			cafe.setId_item(rs.getInt("id_item"));
			cafe.setCpf_colaborador(rs.getInt("cpf_colaborador"));
			cafe.setNome_item(rs.getString("nome_item"));
			

			lista.add(cafe);
		}

		return lista;
	}
	//select com where do cpf de dado coaborador
	public ItemCafe buscarItemPorCpfColab(int cpf_colaborador) throws Exception {
		ItemCafe cafe = null;

		Connection conexao = ConectaBanco.getConnection();

		String sql = "SELECT * FROM tb_item_cafe WHERE cpf_colaborador = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, cpf_colaborador);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			cafe = new ItemCafe();
			cafe.setId_item(rs.getInt("id_item"));
			cafe.setCpf_colaborador(rs.getInt("cpf_colaborador"));
			cafe.setNome_item(rs.getString("nome_item"));
		}

		return cafe;
	}
	//update
	public void editarItemCafe(ItemCafe itemCafe, int id_item) throws Exception {
		Connection conexao = ConectaBanco.getConnection();

		String sql = "UPDATE tb_item_cafe SET nome_item = ? WHERE id_item = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, itemCafe.getNome_item());
		statement.setInt(2, itemCafe.getCpf_colaborador());
		statement.execute();
	}
	
	//remove
	
	public void removerItemCafe(int id_item) throws Exception {
		Connection conexao = ConectaBanco.getConnection();

		String sql = "DELETE FROM tb_item_cafe WHERE id_item = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, id_item);
		statement.execute();
	}
	
	
	
}
