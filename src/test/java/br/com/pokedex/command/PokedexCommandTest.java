package br.com.pokedex.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import br.com.pokedex.bean.PokedexBean;
import br.com.pokedex.dao.PokedexDAOJdbc;
import br.com.pokedex.dto.PokedexDto;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;

@QuarkusTest
public class PokedexCommandTest {
	
	
	@Inject
	PokedexCommand command;
	
	@InjectMock
	PokedexDAOJdbc dao;
	
	@BeforeAll
	public static void setUp() {
		PokedexDAOJdbc dao = mock(PokedexDAOJdbc.class);
		QuarkusMock.installMockForType(dao, PokedexDAOJdbc.class);
	}
	 
	@Nested
    @DisplayName("Testes do Create")
    class WhenCreate {
    	@Test
    	@DisplayName("Quando chamar o Create, deve retornar o último id criado.")
    	public void whenCallCreate_thenReturnLastId() throws Exception {
    		int expectedReturn = 1; 
    		PokedexBean bean = new PokedexBean(1, 10, "Bubassauro", 1, 14);
    		
    		when(dao.insert(any(Connection.class), any(PokedexBean.class)))
    		  .thenReturn(1);
    		
    		int actualReturn = command.create(bean);
    		
    		assertEquals(expectedReturn, actualReturn);
    	}

    	@Test
    	@DisplayName("Quando chamar o Create, deve lançar uma mensagem de erro.")
    	public void whenCallCreate_thenThrowErrorMessage() throws Exception {
    		PokedexBean bean = new PokedexBean(1, 10, "Bubassauro", 1, 14);
    		
    		when(dao.insert(any(Connection.class), any(PokedexBean.class)))
    		  .thenThrow(Exception.class);
    		    		
    		assertThrows(Exception.class, () -> command.create(bean));
    	}
    }
	
	@Nested
    @DisplayName("Testes do FindAll")
    class WhenFindAll {
    	@Test
    	@DisplayName("Quando chamar o FindAll, deve retornar todos os dtos.")
    	public void whenCallFindAll_thenReturnAllDtos() throws Exception {
    		PokedexDto dto = new PokedexDto(1, 10, "Bubassauro", 1, 14);
    		List<PokedexDto> expectedReturn = List.of(dto); 
    		
    		when(dao.findAll(any(Connection.class)))
    		  .thenReturn(expectedReturn);
    		
    		List<PokedexDto> actualReturn = command.findAll();
    		
    		assertEquals(expectedReturn, actualReturn);
    	}

    	@Test
    	@DisplayName("Quando chamar o findAll, deve lançar uma mensagem de erro.")
    	public void whenCallFindAll_thenThrowErrorMessage() throws Exception {
    		
    		when(dao.findAll(any(Connection.class)))
    		  .thenThrow(Exception.class);
    		    		
    		assertThrows(Exception.class, () -> command.findAll());
    	}
    }
	
	@Nested
    @DisplayName("Testes do FindById")
    class WhenFindById {
    	@Test
    	@DisplayName("Quando chamar o findById, deve retornar o dto do id requisitado.")
    	public void whenCallFindByid_thenReturnDtoOfId() throws Exception {
    		PokedexDto expectedReturn = new PokedexDto(1, 10, "Bubassauro", 1, 14);; 
    		
    		when(dao.findById(any(Connection.class), eq(1)))
    		  .thenReturn(expectedReturn);
    		
    		PokedexDto actualReturn = command.findById(1);
    		
    		assertEquals(expectedReturn, actualReturn);
    	}

    	@Test
    	@DisplayName("Quando chamar o FindById, deve lançar uma mensagem de erro.")
    	public void whenCallFindById_thenThrowErrorMessage() throws Exception {
    		when(dao.findById(any(Connection.class), eq(1)))
    		  .thenThrow(Exception.class);
    		    		
    		assertThrows(Exception.class, () -> command.findById(1));
    	}
    }
	
	@Nested
    @DisplayName("Testes do Update")
    class WhenUpdate {
    	@Test
    	@DisplayName("Quando chamar o Update, deve retornar o numero de linhas alteradas.")
    	public void whenCallUpdate_thenReturnLastId() throws Exception {
    		PokedexBean bean = new PokedexBean(1, 10, "Bubassauro", 1, 14);
    		int expectedReturn = 1;
    		
    		when(dao.update(any(Connection.class), any(PokedexBean.class)))
    		  .thenReturn(1);
    		
    		int actualReturn = command.update(bean);
    		
    		assertEquals(expectedReturn, actualReturn);
    	}

    	@Test
    	@DisplayName("Quando chamar o Update, deve lançar uma mensagem de erro.")
    	public void whenCallUpdate_thenThrowErrorMessage() throws Exception {
    		PokedexBean bean = new PokedexBean(1, 10, "Bubassauro", 1, 14);
    		
    		when(dao.update(any(Connection.class), any(PokedexBean.class)))
    		  .thenThrow(Exception.class);
    		    		
    		assertThrows(Exception.class, () -> command.update(bean));
    	}
    }
	
	@Nested
    @DisplayName("Testes do Delete")
    class WhenDelete {
    	@Test
    	@DisplayName("Quando chamar o Delete, deve retornar o número de linhas alteradas.")
    	public void whenCallUpdate_thenReturnLastId() throws Exception {
    		int expectedReturn = 1;
    		
    		when(dao.delete(any(Connection.class), eq(expectedReturn)))
    		  .thenReturn(1);
    		
    		int actualReturn = command.delete(1);
    		
    		assertEquals(expectedReturn, actualReturn);
    	}

    	@Test
    	@DisplayName("Quando chamar o Update, deve lançar uma mensagem de erro.")
    	public void whenCallUpdate_thenThrowErrorMessage() throws Exception {
    		
    		when(dao.delete(any(Connection.class), eq(1)))
    		  .thenThrow(Exception.class);
    		    		
    		assertThrows(Exception.class, () -> command.delete(1));
    	}
    }
}