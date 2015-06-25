package dedalus.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import dedalus.domain.Cliente;
import dedalus.domain.Prenotazione;
import dedalus.domain.Stanza;
import dedalus.domain.Tipologia;
import dedalus.service.ClienteService;
import dedalus.service.PrenotazioneService;
import dedalus.service.StanzaService;
import dedalus.service.TipologiaService;

@Controller
@RequestMapping(value = "prenotazionevmc", method = RequestMethod.POST)
public class PrenotazioneVMC {

	@Autowired
	private PrenotazioneService prenotazioneService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private StanzaService stanzaService;
	@Autowired
	private TipologiaService tipologiaService;
	
	@ResponseBody
	@RequestMapping("ListaClienti") //nome con cui il controller viene richiamato da booking.jsp
	public List<Cliente> listaClienti() throws Exception
	{
		List<Cliente> clienti = clienteService.findAll();
		return clienti;
	}
	
	@ResponseBody
	@RequestMapping("RilevaPrenotazioni")
	public List<Prenotazione> rilevaPrenotazioni(
			@RequestParam(value = "cliente", required = true) String clientejson) throws Exception
			{
			//conversione della della stringa Json in un oggetto cliente
			Cliente client = new ObjectMapper().readValue(clientejson, Cliente.class);
			List<Prenotazione> prenotazioniCliente = prenotazioneService.GetPrenotazioniCliente(client.getId());
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date dataDiOggi = new Date();
			System.out.println(dataDiOggi);
			//converto la data di oggi in ms
			long dataDiOggims = dataDiOggi.getTime();
			
			List<Prenotazione> prenotazioniAggiornate = new ArrayList();
			
			for (Prenotazione i : prenotazioniCliente){
				System.out.println(i.getFrom());
				if (i.getTo().getTime() < dataDiOggims){

					prenotazioneService.delete(i); //cancello la prenotazione se antecedente alla data di oggi

				}else{
					prenotazioniAggiornate.add(i);
				}
			}
			return prenotazioniAggiornate;
			}
	
//	@ResponseBody
//	@RequestMapping("ListaStanze") //nome con cui il controller viene richiamato da booking.jsp
//	public List<Stanza> listaStanze() throws Exception
//	{
//		List<Stanza> stanze = stanzaService.findAll();
//		return stanze;
//	}
	
			
	@ResponseBody
	@RequestMapping("StanzeLibere") //nome con cui il controller viene richiamato da miaprova.jsp
	public List<Stanza> StanzeLibere(
			@RequestParam(value = "da", required = true) String da,
			@RequestParam(value = "a", required = true) String a) throws Exception
			{	
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date from = format.parse(da);
		Date to = format.parse(a);
		long mFrom = from.getTime();
		long mTo = to.getTime();
		//costruisco una lista delle prenotazioni nelle date indicate
		List<Prenotazione> booking = prenotazioneService.getStanzeOccupate(mFrom, mTo);
		//prendo le stanze occupate da queste prenotazioni e ne costrisco una lista
//		List<Stanza> stanzeFull = new ArrayList();
//		for(Prenotazione i : booking){
//			stanzeFull.add(i.getStanza());
//		}
		//costruisco una lista di tutte le stanze disponibili
		List<Stanza> stanzeAll = stanzaService.findAll();
		//comparo le due liste e costriusco stanzeFree
		List<Stanza> stanzeFree = new ArrayList<Stanza>();
		
//		for(Stanza i : stanzeAll){
//			for(Stanza j : stanzeFull){
//				if(i.equals(j)){
//					stanzeFree.remove(i);
//				}
//			}
//		}
		
		for(Stanza stanza : stanzeAll) {
			boolean flag = true;
			for(Prenotazione p : booking){
				System.out.println(p.getId());
				System.out.println("stanza prenotata "+p.getStanza()  );
				System.out.println("stanza complessiva" + stanza);
				if(p.getStanza().equals(stanza))
					flag = false;
			}
			if(flag)
				stanzeFree.add(stanza);
		}
		
		//System.out.println(booking.size());
		
		//for(Stanza s: stanzeFree)
			//System.out.println(s);
		
		return stanzeFree;
		
	}
	
	
	@ResponseBody
	@RequestMapping("NuovaPrenotazione") //nome con cui il controller viene richiamato da miaprova.jsp
	public Prenotazione nuovaPrenotazione(
			@RequestParam(value = "da", required = true) String da,
			@RequestParam(value = "a", required = true) String a,
			@RequestParam(value = "cliente", required = true) String clientjson,
			@RequestParam(value = "stanza", required = true) String roomjson) throws Exception
	{
		//creo una nuova prenotazione
		Prenotazione prenotazione = new Prenotazione();
		prenotazione.setId(prenotazioneService.settingID());
		//manipolazione delle date di prenotazione
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date from = format.parse(da);
		Date to = format.parse(a);
		
		prenotazione.setFrom(from);
		prenotazione.setTo(to);
		
		long millisec = to.getTime() - from.getTime();
		int days = (int) (millisec / 86400000);
		System.out.println(days);
		List<String> response = new ArrayList();
		//HashMap<String,String> response = new HashMap<String, String>();
		if(days<0){
			String errore = "la data di fine è precedente a quella di inizio!!!";
			response.add(errore);
			
			//return response;
			
		}else{
		//conversione stringa JSON del cliente in un oggetto Cliente
		Cliente client = new ObjectMapper().readValue(clientjson, Cliente.class);
		prenotazione.setCliente(client);
		//stessa cosa per la stanza
		Stanza room = new ObjectMapper().readValue(roomjson, Stanza.class);
		prenotazione.setStanza(room);
		//il prezzo è quello relativo alla stanza selezionata
		Integer prezzo = room.getTipologia().getPrezzo();
		//calcolo del totale
		Integer totale = prezzo*days;
		System.out.println(totale);
		prenotazione.setTotale(totale);
		prenotazioneService.create(prenotazione);
		String ok = "prenotazione ok!!!";
		response.add(ok);
		//return response;
		}
		return prenotazione;
	}
			
			
	
}
