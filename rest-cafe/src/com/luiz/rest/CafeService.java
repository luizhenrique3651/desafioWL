package com.luiz.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.luiz.dao.ItemCafeDAO;
import com.luiz.model.ItemCafe;


@Path("/ItemCafe")
public class CafeService {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";

	
	private ItemCafeDAO cafeDAO;
	
	@PostConstruct
	private void init() {
		cafeDAO = new ItemCafeDAO();
		
	}
	
	

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemCafe> listarItemCafe() {
		List<ItemCafe> lista = null;
		try {
			lista = cafeDAO.listarItemCafe();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String addColab(ItemCafe cafe) {
		String msg = "";

		System.out.println(cafe.getNome_item());

		try {
			int idGerado = cafeDAO.addItemCafe(cafe);

			msg = String.valueOf(idGerado);
		} catch (Exception e) {
			msg = "Erro ao add um colaborador!";
			e.printStackTrace();
		}

		return msg;
	}
	
	
	

}
