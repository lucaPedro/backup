package dedalus.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dedalus.base.AbstractService;
import dedalus.dao.StanzaDAO;
import dedalus.domain.Cliente;
import dedalus.domain.Stanza;


@Service
public class StanzaService extends AbstractService<Stanza, Integer, StanzaDAO>{
	
	@Transactional(readOnly = true)
	public Integer settingId() {

		List<Stanza> stanze = dao.findAll();
		List<Integer> idStanze = new ArrayList<Integer>();
		for(Stanza i : stanze){
			idStanze.add(i.getId());
		}
		if(idStanze.size() == 0){
			return 1;
		}else{
			//Collections.sort(idPiani); // Sort the arraylist
			return (Collections.max(idStanze)+1);
		}
	}

}
