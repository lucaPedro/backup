package dedalus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dedalus.base.AbstractService;
import dedalus.dao.FormatoDAO;
import dedalus.domain.Formato;

@Service
public class FormatoService extends AbstractService<Formato, Integer, FormatoDAO>{

	@Transactional(readOnly = true)
	public List<Formato> readId(String f){
		List<Formato> formati = dao.readId(f);
		return formati;
	}
	
}
