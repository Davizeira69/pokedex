package br.com.pokedex.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.pokedex.business.PokedexBusiness;
import br.com.pokedex.dto.PokedexDto;
import br.com.pokedex.dto.ResponseDto;
import br.com.pokedex.exceptions.ConflictException;
import br.com.pokedex.exceptions.NotFoundException;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/pokedex")
@Tag(name = "Pokedex", description = "Catálogo dos pokemon.")
public class PokedexController {

	@Inject
	PokedexBusiness business;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(PokedexDto dto) {
		int id = 0;
		Response res;
		try {
			id = business.create(dto);
			res = Response.status(Status.CREATED).entity(new ResponseDto<Integer>(id, null)).build();
		} catch (ConflictException e) {
			res = Response.status(Status.CONFLICT).entity(new ResponseDto<>(null, e.getMessage())).build();
		} catch(Exception e) {
			res = Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ResponseDto<>(null, e.getMessage())).build();
		}
		return res;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		List<PokedexDto> dtos;
		Response res;

		try {
			dtos = business.findAll();
			res = Response.ok(new ResponseDto<List<PokedexDto>>(dtos, null)).build();
		} catch(NotFoundException e) {
			res = Response.status(Status.NOT_FOUND).entity(new ResponseDto<>(null, e.getMessage())).build();
		} catch(Exception e) {
			res = Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ResponseDto<>(null, e.getMessage())).build();
		} 
		return res;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") int id) {
		PokedexDto dto;
		Response res;

		try {
			dto = business.findById(id);
			res = Response.ok(new ResponseDto<PokedexDto>(dto, null)).build();
		} catch(NotFoundException e) {
			res = Response.status(Status.NOT_FOUND).entity(new ResponseDto<>(null, e.getMessage())).build();
		} catch(Exception e) {
			res = Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ResponseDto<>(null, e.getMessage())).build();
		}
		return res;
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(PokedexDto dto, @PathParam("id") int id) {
		String msg;
		Response res;
		
		try {
			msg = business.update(dto, id);
			res = Response.ok(new ResponseDto<String>(msg, null)).build();
		} catch (ConflictException e) {
			res = Response.status(Status.CONFLICT).entity(new ResponseDto<>(null, e.getMessage())).build();
		} catch(NotFoundException e) {
			res = Response.status(Status.NOT_FOUND).entity(new ResponseDto<>(null, e.getMessage())).build();
		} catch(Exception e) {
			res = Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ResponseDto<>(null, e.getMessage())).build();
		}
		return res;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id) {
		String msg;
		Response res;
		
		try {
			msg = business.delete(id);
			res = Response.ok(new ResponseDto<String>(msg, null)).build();
		} catch(NotFoundException e) {
			res = Response.status(Status.NOT_FOUND).entity(new ResponseDto<>(null, e.getMessage())).build();
		} catch(Exception e) {
			res = Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ResponseDto<>(null, e.getMessage())).build();
		}
		return res;
	}
}
