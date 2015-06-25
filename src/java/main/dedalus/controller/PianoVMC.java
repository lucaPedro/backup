package dedalus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dedalus.domain.Piano;
import dedalus.service.PianoService;


@Controller
@RequestMapping(value = "miaprovavmc", method = RequestMethod.POST)
public class PianoVMC
{
	@Autowired
	private PianoService pianoService;
	
	@ResponseBody
	@RequestMapping("cambio") //nome con cui il controller viene richiamato da miaprova.jsp
	public HashMap<String, Object> cambio(
			@RequestParam(value = "pinco", required = false)String pinco) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		System.out.println(pinco);
//		map.put("val", pianoService.findAll() );
		return map;
	}
	
	//response for il Piano
	@ResponseBody
	@RequestMapping("nuovoPiano") //nome con cui il controller viene richiamato da miaprova.jsp
	public Integer numeroPiano(
			@RequestParam(value = "numeroPiano", required = false) String numPiano, //equivalente di request.getParameter(...)
			@RequestParam(value = "numeroStanze", required = false) String numStanze) throws Exception
	{
		System.out.println(numPiano);
		System.out.println(numStanze);
		Piano piano = new Piano(); //creazione di un nuovo piano
		piano.setNumPiano(Integer.parseInt(numPiano));
		piano.setNumStanze(Integer.parseInt(numStanze));
		piano.setId(pianoService.settingID()); 
		Integer idpiano = pianoService.creazionePiano(piano);
		return idpiano;
		//return new HashMap<String, Object>();
	}
	
	@SuppressWarnings("null")
	@ResponseBody
	@RequestMapping("listaPiani") //nome con cui il controller viene richiamato da stanza.jsp
	//passo una lista di oggetti
	public List<Piano> listaPiani() throws Exception
	{
		List<Piano>	pianidisp = pianoService.findAll();	 //ritorna la lista di tutti i piani registrati
//		Piano[] pianiArray = pianidisp.toArray(new Piano[pianidisp.size()]); //conversione list->array
		return pianidisp;
//		List<String> piani = new ArrayList<String>();;
//		for(Piano i : pianiArray){
//			piani.add("id: " + Integer.toString(i.getId()) + " numero piano: " + Integer.toString(i.getNumPiano()) + " numero letti: " + Integer.toString(i.getNumStanze()));
//		}
//		String[] pianiStr = piani.toArray(new String[piani.size()]);
//		return pianiStr;
	}
	
}



