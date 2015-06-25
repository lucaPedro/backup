package dedalus.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dedalus.base.AbstractService;
import dedalus.dao.PrenotazioneDAO;
import dedalus.domain.Prenotazione;


@Service
public class PrenotazioneService extends AbstractService<Prenotazione, Integer, PrenotazioneDAO>{

	@Transactional(readOnly = true)
	public Integer settingID(){
		List<Prenotazione> prenotazioni = dao.findAll();
		List<Integer> idPrenotazioni = new ArrayList<Integer>();
		for(Prenotazione i : prenotazioni){
			idPrenotazioni.add(i.getId());
		}
		if(idPrenotazioni.size() == 0){
			return 0;
		}else{
			//Collections.sort(idPiani); // Sort the arraylist
			return (Collections.max(idPrenotazioni)+1);
		}
	}
	
	@Transactional(readOnly = true)
	public List<Prenotazione> getStanzeOccupate(long da, long a){
		List<Prenotazione> freeRooms = dao.getStanzeOccupate(da, a);
		return freeRooms;	
	}
	
	@Transactional(readOnly = true)
	public List<Prenotazione> GetPrenotazioniCliente(Integer id){
		List<Prenotazione> prenotazioniCliente = dao.GetPrenotazioniCliente(id);
		return prenotazioniCliente;
	}
	
}
