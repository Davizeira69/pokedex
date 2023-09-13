package br.com.pokedex.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.pokedex.business.PokedexBusiness;
import br.com.pokedex.dto.PokedexDto;
import br.com.pokedex.exceptions.ConflictException;
import br.com.pokedex.exceptions.InternalServerErrorException;
import br.com.pokedex.exceptions.NotFoundException;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class PokedexControllerTest {
    
	@Inject
	PokedexBusiness business;
	
	@BeforeEach
	public void setUp(){
		business = Mockito.mock(PokedexBusiness.class);
		QuarkusMock.installMockForType(business, PokedexBusiness.class);
	}
	
	
    @Nested
    @DisplayName("Testes do Create")
    class WhenCreate {
    	@Test
    	@DisplayName("Quando chamar o Create, deve retornar OK")
    	public void whenCallCreate_thenReturnOk() throws Exception {
    		PokedexDto requestBody = new PokedexDto(1, 10, "Bubassauro", 1, 14);
    		
    		when(business.create(any(PokedexDto.class)))
    			.thenReturn(1);
    		
    		given()
    		  .when()
    		  	.header("Content-Type", "application/json")
    			.body(requestBody)
    			.post("/pokedex")
    		  .then()
    			.statusCode(201)
    			.body("data",is(1))
    			.body("errors", nullValue());
    	}
    	@Test
    	@DisplayName("Quando chamar o Create, deve lançar ConflictException")
    	public void whenCallCreate_thenThrowConflictException() throws Exception{
    		PokedexDto requestBody = new PokedexDto(1, 10, "Bubassauro", 1, 14);
    		
    		when(business.create(any(PokedexDto.class)))
    			.thenThrow(new ConflictException("Pokemon já cadastrado."));
    		
    		given()
    		  .when()
    		    .header("Content-type", "application/json")
    		    .body(requestBody)
    		    .post("/pokedex")
      		  .then()
      			.statusCode(409)
      			.body("data",nullValue())
      			.body("errors", is("Pokemon já cadastrado."));
    	}
    	@Test
    	@DisplayName("Quando chamar o Create, deve lançar InternalServerErrorException")
    	public void whenCallCreate_thenThrowInternalServerError() throws Exception{
    		PokedexDto requestBody = new PokedexDto(1, 10, "Bubassauro", 1, 14);
    		
    		when(business.create(any(PokedexDto.class)))
    		    .thenThrow(new InternalServerErrorException("Erro interno do servidor."));
    		
    		given()
    		  .when()
    		  	.header("Content-type", "application/json")
    		  	.body(requestBody)
    		  	.post("/pokedex")
    		  .then()
  				.statusCode(500)
  				.body("data",nullValue())
  				.body("errors", is("Erro interno do servidor."));
    	}
    }
    @Nested
    @DisplayName("Testes do Find All")
    class WhenFindAll {
    	@Test
    	@DisplayName("Quando chamar o Find All, deve retornar OK")
    	public void whenCallFindall_thenReturnOk() throws Exception{
    		PokedexDto firstPokemon = new PokedexDto(1, 10, "Bubassauro", 1, 14);
    		PokedexDto secondPokemon = new PokedexDto(2, 11, "ivyssauro", 1, 14);
    		List<PokedexDto> dtos = List.of(firstPokemon, secondPokemon);
    		
    		when(business.findAll())
    			.thenReturn(dtos);
    		
    		given()
  		  	  .when()
  		  		.get("/pokedex")
  		  	  .then()
  		  		.statusCode(200)
  		  		.body("data[0].id", is(1))
  		  		.body("data[0].dexNumber", is(10))
  		  		.body("data[0].name", is("Bubassauro"))
  		  		.body("data[0].typeId", is(1))
  		  		.body("data[0].secondTypeId", is(14))
  		  		.body("data[1].id", is(2))
		  		.body("data[1].dexNumber", is(11))
		  		.body("data[1].name", is("ivyssauro"))
		  		.body("data[1].typeId", is(1))
		  		.body("data[1].secondTypeId", is(14))
  		  		.body("errors", nullValue());
    	}
    	@Test
    	@DisplayName("Quando chamar o FindAll, deve lançar NotFoundException")
    	public void whenCallFindAll_thenThrowNotFoundException() throws Exception{    		
    		when(business.findAll())
			  .thenThrow(new NotFoundException("Nenhum registro encontrado."));
		
    		given()
		  	  .when()
		  		.get("/pokedex")
		  	  .then()
		  		.statusCode(404)
		  		.body("data", nullValue())
		  		.body("errors", is("Nenhum registro encontrado."));
    	}
    	@Test
    	@DisplayName("Quando chamar o FindAll, deve lançar InternalServerErrorException")
    	public void whenCallFindAll_thenThrowInternalServerError() throws Exception{
    		when(business.findAll())
			  .thenThrow(new InternalServerErrorException("Erro interno do servidor."));
		
    		given()
		  	  .when()
		  		.get("/pokedex")
		  	  .then()
		  		.statusCode(500)
		  		.body("data", nullValue())
		  		.body("errors", is("Erro interno do servidor."));
    	}
    }
    
    @Nested
    @DisplayName("Teste do Find By Id")
    class WhenFindById {
    	@Test
    	@DisplayName("Quando chamar o Find By Id deve retornar OK")
    	public void whencallFindById_thenReturnOk() throws Exception{
    		PokedexDto dto = new PokedexDto(1, 10, "Bubassauro", 1, 14);
    		    		
    		when(business.findById(anyInt()))
    			.thenReturn(dto);
    		
    		given()
    		  .when()
    		    .pathParam("id", 1)
    			.get("/pokedex/{id}")
    		  .then()
    			.statusCode(200)
    			.body("data.id", is(1))
    			.body("data.name", is("Bubassauro"))
    			.body("errors", nullValue());
    	}
    	@Test
    	@DisplayName("Quando chamar o FindById, deve lançar NotFoundException")
    	public void whenCallFindById_thenThrowNotFoundException() throws Exception{    		
    		when(business.findById(anyInt()))
			  .thenThrow(new NotFoundException("Nenhum registro encontrado."));
		
    		given()
		  	  .when()
		  	  	.pathParam("id", 1)
		  		.get("/pokedex/{id}")
		  	  .then()
		  		.statusCode(404)
		  		.body("data", nullValue())
		  		.body("errors", is("Nenhum registro encontrado."));
    	}
    	@Test
    	@DisplayName("Quando chamar o FindById, deve lançar InternalServerErrorException")
    	public void whenCallFindById_thenThrowInternalServerError() throws Exception{
    		when(business.findById(anyInt()))
			  .thenThrow(new InternalServerErrorException("Erro interno do servidor."));
		
    		given()
		  	  .when()
		  	  	.pathParam("id", 1)
		  		.get("/pokedex/{id}")
		  	  .then()
		  		.statusCode(500)
		  		.body("data", nullValue())
		  		.body("errors", is("Erro interno do servidor."));
    	}
    }
    
    @Nested
    @DisplayName("Teste do Update")
    class WhenUpdate {
    	@Test
    	@DisplayName("Quando chamar o Update deve retornar OK")
    	public void whenCallUpdate_thenReturnOk() throws Exception{
    		PokedexDto requestBody = new PokedexDto(1, 01, "Bubassaur", 1, 14);
    		
    		when(business.update(any(PokedexDto.class), anyInt()))
    			.thenReturn("Alteração realizada com êxito.");
    		
    		given()
  		  	  .when()
  		  	  	.header("Content-Type", "application/json")
  		  		.pathParam("id", 1)
  		  		.body(requestBody)
  		  		.put("/pokedex/{id}")
  		  	  .then()
  		  		.statusCode(200)
  		  		.body("data", is("Alteração realizada com êxito."))
  		  		.body("errors", nullValue());
    	}
    	@Test
    	@DisplayName("Quando chamar o Update, deve lançar ConflictException")
    	public void whenCallUpdate_thenThrowConflictException() throws Exception{
    		PokedexDto requestBody = new PokedexDto(1, 10, "Bubassauro", 1, 14);
    		
    		when(business.update(any(PokedexDto.class), anyInt()))
    			.thenThrow(new ConflictException("Pokemon já cadastrado."));
    		
    		given()
    		  .when()
    		    .header("Content-type", "application/json")
    		    .pathParam("id", 1)
    		    .body(requestBody)
    		    .put("/pokedex/{id}")
      		  .then()
      			.statusCode(409)
      			.body("data",nullValue())
      			.body("errors", is("Pokemon já cadastrado."));
    	}
    	@Test
    	@DisplayName("Quando chamar o Update, deve lançar NotFoundException")
    	public void whenCallUpdate_thenThrowNotFoundException() throws Exception{    		
    		when(business.update(any(PokedexDto.class), anyInt()))
			  .thenThrow(new NotFoundException("Nenhum registro encontrado."));
		
    		given()
    		.when()
		    .header("Content-type", "application/json")
		    .pathParam("id", 0)
		    .body(new PokedexDto())
		    .put("/pokedex/{id}")
  		  .then()
  			.statusCode(404)
  			.body("data", nullValue())
  			.body("errors", is("Nenhum registro encontrado."));
    	}
    	@Test
    	@DisplayName("Quando chamar o update, deve lançar InternalServerErrorException")
    	public void whenCallUpdate_thenThrowInternalServerError() throws Exception{
    		when(business.update(any(PokedexDto.class), anyInt()))
			  .thenThrow(new InternalServerErrorException("Erro interno do servidor."));
		
    		given()
    		  .when()
    		  	.header("Content-type", "application/json")
    		  	.pathParam("id", 1)
    		  	.body(new PokedexDto())
    		  	.put("/pokedex/{id}")
    		  .then()
  				.statusCode(500)
  				.body("data",nullValue())
  				.body("errors", is("Erro interno do servidor."));
    	}
    }
    
    @Nested
    @DisplayName("Teste do Delete")
    class WhenDelete {
    	@Test
    	@DisplayName("Quando chamar o Delete deve retornar OK")
    	public void whenCallDelete_thenReturnOk() throws Exception{    		
    		
    		when(business.delete(anyInt()))
    			.thenReturn("Dados excluídos com êxito.");
    		
    		given()
    		  .when()
    		    .pathParam("id", 1)
    			.delete("/pokedex/{id}")
    		  .then()
    			.statusCode(200)
    			.body("data" ,is("Dados excluídos com êxito."))
    			.body("errors", nullValue());
    	}
    	@Test
    	@DisplayName("Quando chamar o delete, deve lançar NotFoundException")
    	public void whenCallDelete_thenThrowNotFoundException() throws Exception{    		
    		when(business.delete(anyInt()))
			  .thenThrow(new NotFoundException("Nenhum registro encontrado."));
		
    		given()
    		 .when()
    		 	.pathParam("id", 0)
    		 	.delete("/pokedex/{id}")
		     .then()
		     	.statusCode(404)
		     	.body("data" , nullValue())
		     	.body("errors", is("Nenhum registro encontrado."));
    	}
    	@Test
    	@DisplayName("Quando chamar o delete, deve lançar InternalServerErrorException")
    	public void whenCallDelete_thenThrowInternalServerError() throws Exception{
    		when(business.delete(anyInt()))
			  .thenThrow(new InternalServerErrorException("Erro interno do servidor."));
		
    		given()
    		  .when()
    		  	.pathParam("id", 0)
    		  	.delete("/pokedex/{id}")
	          .then()
	          	.statusCode(500)
	          	.body("data" , nullValue())
	          	.body("errors", is("Erro interno do servidor."));
    	}
    }
}