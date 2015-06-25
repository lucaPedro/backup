package dedalus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import dedalus.domain.Cliente;
import dedalus.service.ClienteService;


@Controller
@RequestMapping(value = "clientevmc", method = RequestMethod.POST)
public class ClienteVMC {
	
	@Autowired //autoistanziazione dell'oggetto PianoService
	ClienteService clienteService; //ogni controller ha il suo service e il suo DAO
	
	//response for il Cliente
		@ResponseBody
		@RequestMapping("nuovoCliente") //nome con cui il controller viene richiamato da miaprova.jsp
		public Cliente numeroPiano(
				@RequestParam(value = "nomeCliente", required = true) String nome, //equivalente di request.getParameter(...)
				@RequestParam(value = "cognomeCliente", required = true) String cognome, //true = è un parametro obbligatorio
				@RequestParam(value = "ID", required = true) String id,
				@RequestParam(value = "codiceFiscale", required = true) String codFiscale) throws Exception
		{
			Cliente cliente = new Cliente(); //creazione di un nuovo piano
			cliente.setNome(nome);
			cliente.setCognome(cognome);
			cliente.setCartaIdentita(id);
			cliente.setCodiceFiscale(codFiscale);
			
			cliente.setId(clienteService.settingID()); 
			clienteService.creazioneCliente(cliente);	
			return cliente;
		}
		
	//lista di tutti i clienti
		@ResponseBody
		@RequestMapping("tuttiClienti")
		public List<Cliente> tuttiIClienti() throws Exception{
			List<Cliente> AllClients = clienteService.findAll();
//			for(Cliente c : AllClients){
//			System.out.println(c);
//			}
			return AllClients;
		}
		
	//elimina il cliente selezionato
//TODO: problema per consistenza dati nel DB -> clienti con prenotazioni in corso!!!
		@ResponseBody
		@RequestMapping("eliminaCliente")
		public List<String> EliminaCliente(
				@RequestParam(value="cliente", required=true) String clientejson) throws Exception{
			
			System.out.println(clientejson);
			Cliente client = new ObjectMapper().readValue(clientejson, Cliente.class);
			List<String> message = new ArrayList<>();
			try{
				clienteService.delete(client);
				message.add("il cliente " + client.getNome() + " " + client.getCognome() + " è stato eliminato dal DB");
				return message;
			} catch (Exception e) {
				message.add("Il cliente ha prenotazioni in corso");
				return message;
			}
		}
		
		@ResponseBody
		@RequestMapping("Prova")
		public Integer Proviamoci(){
			System.out.println("siamo arrivati qui!!!");
			return 0;
		}
		
//		@RequestMapping("prova2")
//		public void prova2(HttpServletResponse response){
//			response.setStatus(200);
//			System.out.println("jflajflajfal");
//			return;
//		}
	
}
