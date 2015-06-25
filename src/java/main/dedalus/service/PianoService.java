package dedalus.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dedalus.base.AbstractService;
import dedalus.dao.PianoDAO;
import dedalus.domain.Piano;

@Service
public class PianoService extends AbstractService<Piano,Integer, PianoDAO>
{
	@Transactional(readOnly = true)
	public Integer settingID(){
		List<Piano> pianiPresenti = dao.findAll();
		List<Integer> idPiani = new ArrayList<Integer>();
		for(Piano i : pianiPresenti){
			idPiani.add(i.getId());
		}
			//Collections.sort(idPiani); // Sort the arraylist
			//return idPiani.size(); 
			return (Collections.max(idPiani)+1); //gets the last item, largest for an ascending sort
	}
	
	@Transactional(readOnly = false)
	public Integer creazionePiano(Piano p){
		dao.create(p);
		return p.getId();
	}
	
}
