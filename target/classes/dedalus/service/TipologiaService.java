package dedalus.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dedalus.base.AbstractService;
import dedalus.dao.TipologiaDAO;
import dedalus.domain.Tipologia;

@Service
public class TipologiaService extends AbstractService<Tipologia, Integer, TipologiaDAO> {	
	
	@Transactional(readOnly = true)
		public Integer settingID(){
			List<Tipologia> tipologie = dao.findAll();
			List<Integer> idTipologie = new ArrayList<Integer>();
			for(Tipologia i : tipologie){
				idTipologie.add(i.getId());
			}
			if(idTipologie.size() == 0){
				return 0;
			}else{
				//Collections.sort(idPiani); // Sort the arraylist
				return (Collections.max(idTipologie)+1);
			}
		}
	

}
