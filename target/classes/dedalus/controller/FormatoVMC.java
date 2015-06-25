package dedalus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;





import dedalus.domain.Formato;
import dedalus.service.ClienteService;
import dedalus.service.FormatoService;

@Controller
@RequestMapping(value = "formatovmc", method = RequestMethod.POST)
public class FormatoVMC {
	
	@Autowired //autoistanziazione dell'oggetto FormatoService
	FormatoService formatoService; //ogni controller ha il suo service e il suo DAO
	
	@ResponseBody
	@RequestMapping("nuovoFormato") //nome con cui il controller viene richiamato da miaprova.jsp
	public Formato numeroFormato(
			@RequestParam(value = "formato", required = true) Integer formId //equivalente di request.getParameter(...)
			) throws Exception
	{
		Formato formato = new Formato(); //creazione di un nuovo piano
		//formato.setId(formId);
		formato = formatoService.findById(formId);
//		formato.setDescrizione(form);
//		List<Formato> a = formatoService.readId(form);
//		formato.setId(a.get(0).getId());
		return formato;
	}


}
