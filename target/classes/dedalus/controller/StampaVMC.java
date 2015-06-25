
package dedalus.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.engine.data.JRXlsDataSource;
import net.sf.jasperreports.engine.data.XlsDataSource;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dedalus.domain.Cliente;
import dedalus.domain.Prenotazione;

@Controller
@RequestMapping(value = "stampavmc", method = RequestMethod.GET)
public class StampaVMC {
	
	@Autowired
	private ServletContext ctx;
	
	@Autowired
	private DataSource dataSource; //fornisce al file .jasper la connessione al DB che deve utilizzare per ottenere i dati
	
	//metodo per la stampa!!! non c'e @ResponseBody poiché è un metodo void
	@RequestMapping("StampaPrenotazione")
	public void StampaDoc(HttpServletRequest request, HttpServletResponse response
			//@RequestParam(value = "da", required = true) String from,
			//@RequestParam(value = "a", required = true) String to
			//@RequestParam(value = "booking", required = true) String prenotazionejson
			) throws Exception {
		
		Cliente client = new ObjectMapper().readValue(request.getParameter("cliente"), Cliente.class);
//		String a = request.getParameter("bho");

//		System.out.println(prenotazionejson);
		Prenotazione prenot = new ObjectMapper().readValue(request.getParameter("booking"), Prenotazione.class);
		
//		String totale = request.getParameter("prezzo");
		Integer totale = prenot.getTotale();

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		String from = format.format(prenot.getFrom());
		String to = format.format(prenot.getTo());

		System.out.println(prenot.getId());
		System.out.println(totale);
		//seleziona il tipo di output => pdf
		response.setContentType("application/pdf");
//	    response.setHeader("Content-Disposition", "attachment;"); //fa il download della stampa
	    
//	    la ctx corrisponde a request.getSession().getServletContext()
//		String JasperPath = "C:\\workspaceLuna\\dedalusAlbergo\\dedAlbergo.jasper"; 
		String JasperPath = "C:\\workspaceLuna\\dedalusAlbergo\\Prova1.jasper"; 

//	    String JasperPath = ctx.getRealPath("") + "\\dedAlbergo.jasper";
		
//	    if(new File(JasperPath).exists()){
//	    	System.out.println("esisto!!");
//	    }
	    System.out.println(JasperPath);
		
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("cliente", client.getNome() + " " + client.getCognome());
	    parameters.put("ClienteID", client.getId());
	    parameters.put("prenotazioneId", prenot.getId());
	    parameters.put("prezzo", totale);
	    //parameters.put("cliente_id", client.getId());
	    parameters.put("da", from);
	    parameters.put("a", to);
	    
	    System.out.println(parameters.get("cliente"));
	    System.out.println(parameters.get("ClienteID"));
	    
	    byte[] bytes = JasperRunManager.runReportToPdf(JasperPath, parameters, dataSource.getConnection());
	    
	    ServletOutputStream output = response.getOutputStream();
        output.write(bytes);
	    //out.writeTo(output);
        output.flush();
        output.close();
        
	}
	
	@RequestMapping("StampaTutteLePrenotazioni")
	public void StampaTutteLePrenotazioni(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		System.out.println("I am here!!");
		//seleziona il tipo di output => pdf
		response.setContentType("application/pdf");
		
		String JasperPath2 = "C:\\workspaceLuna\\dedalusAlbergo\\TutteLePrenotazioni.jasper";
//	    String JasperPath2 = ctx.getRealPath("") + "\\TutteLePrenotazioni.jasper";
	    System.out.println(JasperPath2);
	    //inizializzazione di una mappa vuota che viene riempieta con eventuali paratri da passare al file .jasper
	    Map<String, Object> parameters2 = new HashMap<>();
	    
	    byte[] bytes = JasperRunManager.runReportToPdf(JasperPath2, parameters2, dataSource.getConnection());
	    
	    //setting dell'output
	    //invio di dati binari al cliente
	    ServletOutputStream output = response.getOutputStream();
	    output.write(bytes);
	    output.flush();
	    output.close();
	}

//funzione per conversione da json string to arrayList	
	ArrayList<String> jsonStringToArray(String jsonString) throws JSONException {
		
		ArrayList<String> stringArray = new ArrayList<String>();
		
		JSONArray jsonArray = new JSONArray(jsonString);
		System.out.println(jsonArray);
		for (int i = 0; i < jsonArray.length(); i++) {
			stringArray.add(jsonArray.getString(i));
		}
		
		return stringArray;
	}
	
	@RequestMapping("StampaOptionals")
	public void StampaDoc(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="optionals", required=false) String optionaljson
			) throws Exception {
	
		System.out.println("urca...ci sono arrivato!!");
		System.out.println(optionaljson);
		
//		ArrayList<String> optionals = jsonStringToArray(optionaljson);
		
		JSONArray optionals = new JSONArray(optionaljson);
//		System.out.println(optionals);
//		System.out.println(optionals.getJSONObject(0).get("unicity"));
		
		
		//Creo il file excel chiamato output.xls
		WritableWorkbook workbook = Workbook.createWorkbook(new File("output.csv"));
		//Creo il foglio, che chiamo “primo foglio” nel quale scrivere le celle ed il loro contenuto (il primo foglio ha indice 0)
		WritableSheet sheet = workbook.createSheet("Primo foglio", 0);
		
		//campi della tabella
		Label campo1 = new Label(0, 0, "optionals");
		sheet.addCell(campo1);
		Label campo2 = new Label(1, 0, "prezzo");
		sheet.addCell(campo2);
		
		//ORA CREO LE CELLE	-> riempio con i record 
		for (int i=0; i < optionals.length(); i++){
			//Creo una celle con i nomi degli optionals sulla prima riga
			Label label = new Label(0, i+1, (String) optionals.getJSONObject(i).get("productName"));
			sheet.addCell(label);
		 
			//Creo una celle con i prezzi dei prodotti sulla seconda riga
			Number number = new Number(1, i+1, (Integer) optionals.getJSONObject(i).get("productPrice"));
			sheet.addCell(number); 
		 
		}
		//Scrivo effettivamente tutte le celle ed i dati aggiunti
		workbook.write();
		 
		//Chiudo il foglio excel
		workbook.close();
		System.out.println("ho creato il file excell!!!");
		
		
		
		
		Map<String, Object> listOptionals = new HashMap<>();
//		List<String> prodotti = new ArrayList();
//		List<Integer> prezzo = new ArrayList();
//		
//		for (int i=0; i < optionals.length(); i++){
//			prodotti.add((String) optionals.getJSONObject(i).get("productName"));
//			prezzo.add((Integer) optionals.getJSONObject(i).get("productPrice"));
//		}
//		
//		listOptionals.put("productName", prodotti);
//		listOptionals.put("productPrice", prezzo);
//		System.out.println(listOptionals);
//
		String JasperPath3 = "C:\\workspaceLuna\\dedalusAlbergo\\Optionals.jasper";

		JRCsvDataSource dataSource = new JRCsvDataSource(new File("C:\\eclipse\\datasource.csv"));
		dataSource.setRecordDelimiter("\t");
		dataSource.setUseFirstRowAsHeader(true);
		dataSource.setFieldDelimiter(';');
		byte[] bytes = JasperRunManager.runReportToPdf(JasperPath3, listOptionals, dataSource );
	    
	    //setting dell'output
	    //invio di dati binari al cliente
	    ServletOutputStream output = response.getOutputStream();
	    output.write(bytes);
	    output.flush();
	    output.close();	
	}
	
}
