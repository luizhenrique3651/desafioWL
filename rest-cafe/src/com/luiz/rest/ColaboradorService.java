package com.luiz.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.luiz.dao.ColaboradorDAO;
import com.luiz.model.Colaborador;





//path indica o caminho da url em que essa classe se encontra

@Path("/colaboradores")
public class ColaboradorService {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	
	private ColaboradorDAO colabDAO;
	
	//post construct é parte do framework jersey, que monta automaticamente um construtor pro objeto instanciado
	@PostConstruct
	private void init() {
		colabDAO = new ColaboradorDAO();
	}
	
	//parametro para restringir o verbo http usado para chamar esse metodo
	@GET
	//caminho da url para o metodo
	@Path("/list")
	//Produces indica que após recebidos os dados, os mesmos serão transcritos num mediatype, que defini como um JSON
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	//lista da entidade colaborador que chama o metodo listarColaboradores() da classe DAO.
	public List<Colaborador> listarColaboradores() {
		List<Colaborador> lista = null;
		try {
			lista = colabDAO.listarColaboradores();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	//os outros metodos abaixo seguem o mesmo padrao, com a diferenciação no path, no verbo http recebido e
	//na definição se ele vai produzir ou consumir um certo dado.
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String addColab(Colaborador colab) {
		String msg = "";

		System.out.println(colab.getNome_colab());

		try {
			int idGerado = colabDAO.addColab(colab);

			msg = String.valueOf(idGerado);
		} catch (Exception e) {
			msg = "Erro ao add um colaborador!";
			e.printStackTrace();
		}

		return msg;
	}
	
	
	@PUT
	@Path("/edit/{id}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarNota(Colaborador colab, @PathParam("id") int cpf_colab) {
		String msg = "";
		
		System.out.println(colab.getNome_colab());
		
		try {
			colabDAO.editarColaborador(colab, cpf_colab);;
			
			msg = "Nota editada com sucesso!";
		} catch (Exception e) {
			msg = "Erro ao editar a nota!";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	
	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String removerColaborador(@PathParam("id") int cpf_colab) {
		String msg = "";
		
		try {
			colabDAO.removerColaborador(cpf_colab);
			
			msg = "COlaborador removido com sucesso!";
		} catch (Exception e) {
			msg = "Erro ao remover colab!";
			e.printStackTrace();
		}
		
		return msg;
	}

}
