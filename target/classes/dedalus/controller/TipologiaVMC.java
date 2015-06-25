package dedalus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import dedalus.domain.Piano;
import dedalus.domain.Tipologia;
import dedalus.service.FormatoService;
import dedalus.service.TipologiaService;

@Controller
@RequestMapping(value = "tipologiavmc", method = RequestMethod.POST)
public class TipologiaVMC {
	
	@Autowired
	TipologiaService tipologiaservice;
	@Autowired
	FormatoService formatoservice;
	
	@ResponseBody
	@RequestMapping("nuovaTipologia") //nome con cui il controller viene richiamato da miaprova.jsp
	public Tipologia nuovaTipologia(
			@RequestParam(value = "prezzo", required = true) String price, //equivalente di request.getParameter(...)
			@RequestParam(value = "piano", required = true) String floorjson,
			@RequestParam(value = "formatoId", required = true) Integer form) throws Exception
	{
		Piano floor = new ObjectMapper().readValue(floorjson, Piano.class); //il server riceve una stringa json che deve essere convertita nell'opportuno oggetto!!!
		System.out.println(floor);
		Tipologia tipologia = new Tipologia(); //creazione di una nuova tipologia
		System.out.println(price);
		tipologia.setPrezzo(Integer.parseInt(price));
		tipologia.setId(tipologiaservice.settingID());
		tipologia.setPiano(floor);
		tipologia.setFormato(formatoservice.findById(form));
		tipologiaservice.create(tipologia);
		return tipologia;
	}

}
