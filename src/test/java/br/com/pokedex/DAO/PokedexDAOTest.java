package br.com.pokedex.DAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import br.com.pokedex.bean.PokedexBean;
import br.com.pokedex.dao.test.IPokedexDAO;
import br.com.pokedex.dto.PokedexDto;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class PokedexDAOTest {

	@Inject
	IPokedexDAO dao;
	
	static PreparedStatement pst;
	
	static ResultSet rs;
	
	@BeforeAll
	public static void setUp() {
		pst = mock(PreparedStatement.class);
		
		rs = mock(ResultSet.class);
	}
	
	@Nested
    @DisplayName("Testes do Create")
    class WhenCreate {
    	@Test
    	@DisplayName("Quando chamar o Create, deve retornar o último id criado.")
    	public void whenCallCreate_thenReturnLastId() throws Exception {
    		int expectedReturn = 1; 
    		PokedexBean bean = new PokedexBean(1, 10, "Bubassauro", 1, 14);
    		
    		when(pst.execute()).thenReturn(true);
    		when(rs.getInt(1)).thenReturn(1);
    		
    		int actualReturn = dao.insert(any(Connection.class), bean);
    		
    		assertEquals(expectedReturn, actualReturn);
    	}

    	@Test
    	@DisplayName("Quando chamar o Create, deve lançar uma mensagem de erro.")
    	public void whenCallCreate_thenThrowErrorMessage() throws Exception {
    		PokedexBean bean = new PokedexBean(1, 10, "Bubassauro", 1, 14);
    		
    		when(pst.execute())
    		  .thenThrow(Exception.class);
    		when(rs.getInt(1))
    		  .thenThrow(Exception.class);
    		    		
    		assertThrows(Exception.class, () -> dao.insert(any(Connection.class), bean));
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
    		
    		when(rs.next()).thenReturn(true);
    		
    		List<PokedexDto> actualReturn = dao.findAll(any(Connection.class));
    		
    		assertEquals(expectedReturn, actualReturn);
    	}

    	@Test
    	@DisplayName("Quando chamar o findAll, deve lançar uma mensagem de erro.")
    	public void whenCallFindAll_thenThrowErrorMessage() throws Exception {
    		
    		when(rs.next()).thenThrow(Exception.class);
    		    		
    		assertThrows(Exception.class, () -> dao.findAll(any(Connection.class)));
    	}
    }
	
	@Nested
    @DisplayName("Testes do FindById")
    class WhenFindById {
    	@Test
    	@DisplayName("Quando chamar o findById, deve retornar o dto do id requisitado.")
    	public void whenCallFindByid_thenReturnDtoOfId() throws Exception {
    		PokedexDto expectedReturn = new PokedexDto(1, 10, "Bubassauro", 1, 14);
    		
    		when(pst.executeQuery()).thenReturn(rs);
    		when(rs.next()).thenReturn(true);
    		
    		PokedexDto actualReturn = dao.findById(any(Connection.class), 1);
    		
    		assertEquals(expectedReturn, actualReturn);
    	}

    	@Test
    	@DisplayName("Quando chamar o FindById, deve lançar uma mensagem de erro.")
    	public void whenCallFindById_thenThrowErrorMessage() throws Exception {
    		
    		when(pst.executeQuery()).thenThrow(Exception.class);
    		when(rs.next()).thenThrow(Exception.class);
    		    		
    		assertThrows(Exception.class, () -> dao.findById(any(Connection.class), 1));
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
    		
    		when(pst.executeUpdate()).thenReturn(1);
    		
    		int actualReturn = dao.update(any(Connection.class), bean);
    		
    		assertEquals(expectedReturn, actualReturn);
    	}

    	@Test
    	@DisplayName("Quando chamar o Update, deve lançar uma mensagem de erro.")
    	public void whenCallUpdate_thenThrowErrorMessage() throws Exception {
    		PokedexBean bean = new PokedexBean(1, 10, "Bubassauro", 1, 14);
    		
    		when(pst.executeUpdate()).thenThrow(Exception.class);
    		    		
    		assertThrows(Exception.class, () -> dao.update(any(Connection.class), bean));
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
    		
    		int actualReturn = dao.delete(any(Connection.class), 1);
    		
    		assertEquals(expectedReturn, actualReturn);
    	}

    	@Test
    	@DisplayName("Quando chamar o Update, deve lançar uma mensagem de erro.")
    	public void whenCallUpdate_thenThrowErrorMessage() throws Exception {
    		
    		when(pst.executeUpdate()).thenReturn(1);
    		    		
    		assertThrows(Exception.class, () -> dao.delete(any(Connection.class), 1));
    	}
    }

}
