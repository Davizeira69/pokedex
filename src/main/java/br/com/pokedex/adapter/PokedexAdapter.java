package br.com.pokedex.adapter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import br.com.pokedex.bean.PokedexBean;
import br.com.pokedex.dto.PokedexDto;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PokedexAdapter {

	public PokedexBean adapterDtoToBean(PokedexDto dto, int id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Timestamp(System.currentTimeMillis()));
		PokedexBean pokedexBean = new PokedexBean();
		
		pokedexBean.setId(id);
		pokedexBean.setDexNumber(dto.getDexNumber());
		pokedexBean.setName(dto.getName());
		pokedexBean.setTypeId(dto.getTypeId());
		pokedexBean.setSecondTypeId(dto.getSecondTypeId());
		pokedexBean.setDthrCreate(date);
		pokedexBean.setDthrUpdate(date);
		
		return pokedexBean;
	}
}
