package dedalus.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dedalus.base.AbstractService;
import dedalus.dao.ClienteDAO;
import dedalus.domain.Cliente;

@Service
public class ClienteService extends AbstractService<Cliente, Integer, ClienteDAO>{
		
	@Transactional(readOnly = true)
	public Integer settingID(){
		List<Cliente> clientiPresenti = dao.findAll();
		List<Integer> idClienti = new ArrayList<Integer>();
		for(Cliente i : clientiPresenti){
			idClienti.add(i.getId());
		}
			//Collections.sort(idPiani); // Sort the arraylist
			return (Collections.max(idClienti)+1);
	}
	
	@Transactional(readOnly = false)
	public Integer creazioneCliente(Cliente e){
		dao.create(e);
		return e.getId();
	}

}
