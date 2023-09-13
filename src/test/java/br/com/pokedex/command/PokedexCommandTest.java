package br.com.pokedex.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import br.com.pokedex.bean.PokedexBean;
import br.com.pokedex.config.MySQLConfig;
import br.com.pokedex.dao.IPokedexDAO;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;

@QuarkusTest
public class PokedexCommandTest {
	
	private static MockedStatic<MySQLConfig> configMock = Mockito.mockStatic(MySQLConfig.class);
	
	@Inject
	PokedexCommand command;
	
	@InjectMock
	IPokedexDAO dao;
	
	@BeforeAll
	public static void setUp() {
		IPokedexDAO dao = mock(IPokedexDAO.class);
		QuarkusMock.installMockForType(dao, IPokedexDAO.class);
	}
	
	@Nested
    @DisplayName("Testes do Create")
    class WhenCreate {
    	@Test
    	@DisplayName("Quando chamar o Create, deve retornar o último id criado.")
    	public void whenCallCreate_thenReturnLastId() throws Exception {
    		int expectedReturn = 1; 
    		PokedexBean bean = new PokedexBean(1, 10, "Bubassauro", 1, 14);
    		
    		when(dao.insert(any(Connection.class), eq(bean)))
    		  .thenReturn(1);
    		
    		int actualReturn = command.create(bean);
    		
    		assertEquals(expectedReturn, actualReturn);
    		verify(dao, times(1))
    		  .insert(any(Connection.class) ,bean);
    	}

    	@Test
    	@DisplayName("Quando chamar o Create, deve lançar uma mensagem de erro.")
    	public void whenCallCreate_thenThrowErrorMessage() throws Exception {
    		Class<Exception> expectedReturn = Exception.class; 
    		PokedexBean bean = new PokedexBean(1, 10, "Bubassauro", 1, 14);
    		
    		when(dao.insert(any(Connection.class), eq(bean)))
    		  .thenThrow(expectedReturn);
    		    		
    		assertThrows(expectedReturn, () -> command.create(bean));
    		verify(dao, times(1))
    		  .insert(any(Connection.class), bean);
    	}
    }

}
