package br.com.pokedex.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import br.com.pokedex.bean.PokedexBean;
import br.com.pokedex.command.PokedexCommand;
import br.com.pokedex.dto.PokedexDto;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;

@QuarkusTest
public class PokedexBusinessTest {
	@Inject
	PokedexBusiness business;
	
	@InjectMock
	PokedexCommand command;
	
	@BeforeAll
	public static void setUp() {
		PokedexCommand command = mock(PokedexCommand.class);
		QuarkusMock.installMockForType(command, PokedexCommand.class);
	}
	
	@Nested
    @DisplayName("Testes do Create")
    class WhenCreate {
    	@Test
    	@DisplayName("Quando chamar o Create, deve retornar o último id criado.")
    	public void whenCallCreate_thenReturnLastId() throws Exception {
    		int expectedReturn = 1; 
    		PokedexDto dto = new PokedexDto(1, 10, "Bubassauro", 1, 14);
    		
    		when(command.create(any(PokedexBean.class)))
    		  .thenReturn(1);
    		
    		int actualReturn = business.create(dto);
    		
    		assertEquals(expectedReturn, actualReturn);
    	}

    	@Test
    	@DisplayName("Quando chamar o Create, deve lançar uma mensagem de erro.")
    	public void whenCallCreate_thenThrowErrorMessage() throws Exception {
    		PokedexDto dto = new PokedexDto(1, 10, "Bubassauro", 1, 14);
    		
    		when(command.create(any(PokedexBean.class)))
    		  .thenThrow(Exception.class);
    		    		
    		assertThrows(Exception.class, () -> business.create(dto));
    	}
    }
	
	@Nested
    @DisplayName("Testes do FindAll")
    class WhenFindAll {
    	@Test
    	@DisplayName("Quando chamar o FindAll, deve retornar uma lista de Dtos")
    	public void whenCallFindAll_thenReturnDtoList() throws Exception {
    		PokedexDto dto = new PokedexDto(1, 10, "Bubassauro", 1, 14);
    		List<PokedexDto> expectedReturn = List.of(dto);
    		
    		when(command.findAll())
    		  .thenReturn(expectedReturn);
    		
    		List<PokedexDto> actualReturn = business.findAll();
    		
    		assertEquals(expectedReturn, actualReturn);
    		verify(command, times(1))
    		  .findAll();
    	}
    
	
	@Test
	@DisplayName("Quando chamar o FindAll, deve lançar uma mensagem de erro.")
	public void whenCallFindAll_thenThrowErrorMessage() throws Exception {
		
		when(command.findAll())
		  .thenThrow(Exception.class);
				
		assertThrows(Exception.class, () -> business.findAll());
		verify(command, times(1))
		  .findAll();
		}
	}
	
	@Nested
    @DisplayName("Testes do FindById")
    class WhenFindById {
    	@Test
    	@DisplayName("Quando chamar o FindById, deve retornar um dto pelo seu id.")
    	public void whenCallFindById_thenReturnDtoById() throws Exception {
    		PokedexDto expectedReturn = new PokedexDto(1, 10, "Bubassauro", 1, 14);
    		int id = 1;
    		
    		when(command.findById(eq(id)))
    		  .thenReturn(expectedReturn);
    		
    		PokedexDto actualReturn = business.findById(id);
    		
    		assertEquals(expectedReturn, actualReturn);
    		verify(command, times(1))
    		  .findById(id);
    	}
    	
    	@Test
    	@DisplayName("Quando chamar o FindById, deve lançar uma mensagem de erro.")
    	public void whenCallFindById_thenThrowErrorMessage() throws Exception {
    		Class<Exception> expectedReturn = Exception.class; 
    		int id = 1;
    		
    		when(command.findById(eq(id)))
    		  .thenThrow(expectedReturn);
    		    		
    		assertThrows(expectedReturn, () -> business.findById(id));
    		verify(command, times(1))
    		  .findById(id);
    	}
    }
	
	@Nested
    @DisplayName("Testes do Update")
    class WhenUpdate {
    	@Test
    	@DisplayName("Quando chamar o update, deve retornar uma mensagem de sucesso")
    	public void whenCallUpdate_thenReturnOkMessage() throws Exception {
    		PokedexDto dto = new PokedexDto(1, 10, "Bubassauro", 1, 14);
    		String expectedReturn = "Alterações realizadas com êxito.";
    		int affectedRows = 1;
    		
    		when(command.update(any(PokedexBean.class)))
    		  .thenReturn(affectedRows);
    		
    		String actualReturn = business.update(dto, 1);
    		
    		assertEquals(expectedReturn, actualReturn);
    	}
    	
    	@Test
    	@DisplayName("Quando chamar o Update, deve lançar uma mensagem de erro.")
    	public void whenCallUpdate_thenThrowErrorMessage() throws Exception {
    		PokedexDto dto = new PokedexDto(1, 10, "Bubassauro", 1, 14);
    		int id = 1;
    		
    		when(command.update(any(PokedexBean.class)))
    		  .thenThrow(Exception.class);
    		    		
    		assertThrows(Exception.class, () -> business.update(dto, id));
    	}
    }
	
	@Nested
    @DisplayName("Testes do Delete")
    class WhenDelete {
    	@Test
    	@DisplayName("Quando chamar o Delete, deve retornar uma mensagem de sucesso")
    	public void whenCallDelete_thenReturnOkMessage() throws Exception {
    		int id = 1;
    		String expectedReturn = "Dados excluídos com êxito.";
    		
    		when(command.delete(eq(id)))
    		  .thenReturn(1);
    		
    		String actualReturn = business.delete(id);
    		
    		assertEquals(expectedReturn, actualReturn);
    		verify(command, times(1))
    		  .delete(id);
    	}
    	
    	@Test
    	@DisplayName("Quando chamar o Delete, deve lançar uma mensagem de erro.")
    	public void whenCallDelete_thenThrowErrorMessage() throws Exception {
    		int id = 1;
    		
    		when(command.delete(id))
    		  .thenThrow(Exception.class);
    		
    		
    		assertThrows(Exception.class, () -> business.delete(id));
    		verify(command, times(1))
    		  .delete(id);
    	}
    }
}