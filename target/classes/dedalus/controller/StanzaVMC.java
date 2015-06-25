package dedalus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import dedalus.domain.Cliente;
import dedalus.domain.Piano;
import dedalus.domain.Stanza;
import dedalus.domain.Tipologia;
import dedalus.service.StanzaService;

@Controller
@RequestMapping(value = "stanzavmc", method = RequestMethod.POST)
public class StanzaVMC {
	
	@Autowired
	private StanzaService stanzaService;
	
	//response per la Stanza
			@ResponseBody
			@RequestMapping("nuovaStanza") //nome con cui il controller viene richiamato da miaprova.jsp
			public Stanza numeroPiano(
					@RequestParam(value = "numeroStanza", required = true) Integer numStanza, //equivalente di request.getParameter(...)
					@RequestParam(value = "numeroLetti", required = true) Integer numLetti, //true = è un parametro obbligatorio
					@RequestParam(value = "mq", required = true) Integer mq,
					@RequestParam(value = "piano", required = true) String pianojson,
					@RequestParam(value = "tipologia", required = true) String tipologiajson) throws Exception
			{
				System.out.println(numStanza);
				System.out.println(numLetti);
				System.out.println(mq);
				System.out.println(pianojson);
				System.out.println(tipologiajson);
				
				Stanza stanza = new Stanza();
				stanza.setNumero(numStanza);
				stanza.setLetti(numLetti);
				stanza.setMq(mq);
				
				//creo l'oggetto piano
				Piano piano = new ObjectMapper().readValue(pianojson, Piano.class); //il server riceve una stringa json che deve essere convertita nell'opportuno oggetto!!!
				stanza.setPiano(piano);
				//creo l'oggetto tipologia
				Tipologia tipologia = new ObjectMapper().readValue(tipologiajson, Tipologia.class);
				stanza.setTipologia(tipologia);
				
				stanza.setId(stanzaService.settingId());
				return stanza;
			}
	
}
