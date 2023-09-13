package br.com.pokedex.business;

import java.util.List;

import org.jboss.logging.Logger;

import br.com.pokedex.adapter.PokedexAdapter;
import br.com.pokedex.bean.PokedexBean;
import br.com.pokedex.command.PokedexCommand;
import br.com.pokedex.dto.PokedexDto;
import br.com.pokedex.exceptions.NotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PokedexBusiness {

	@Inject
	PokedexCommand command;
	
	@Inject
	PokedexAdapter adapter;
	
	static Logger log = Logger.getLogger(PokedexBusiness.class);
	
	public int create(PokedexDto dto) throws Exception {
		int id = 0;
		try {
			PokedexBean bean = adapter.adapterDtoToBean(dto, 0);
			id = command.create(bean);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return id;
	}
	
	public List<PokedexDto> findAll() throws Exception {
		List<PokedexDto> pokedexDtos = null;
		
		try {
			pokedexDtos = command.findAll();
			notFoundChecker(pokedexDtos.size());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return pokedexDtos;
	}
	
	public PokedexDto findById(int id) throws Exception {
		PokedexDto dto;
		
		try {
			dto = command.findById(id);
			notFoundChecker(dto.getId());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return dto;
	}
	
	public String update(PokedexDto dto, int id) throws Exception {
		int affectedRows = 0;
		try {
			PokedexBean bean = adapter.adapterDtoToBean(dto, id);
			affectedRows = command.update(bean);
			notFoundChecker(affectedRows);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return "Alterações realizadas com êxito.";
	}
	
	public String delete(int id) throws Exception {
		int affectedRows = 0;
		
		try {
			affectedRows = command.delete(id);
			notFoundChecker(affectedRows);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return "Dados excluídos com êxito.";
	}
	
	private static void notFoundChecker(int paramForCheck) throws NotFoundException{
		if (paramForCheck == 0) {
			throw new NotFoundException("Nenhum registro encontrado.");
		}
	}
}