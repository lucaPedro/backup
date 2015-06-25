package dedalus.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import dedalus.base.AbstractDAO;
import dedalus.domain.Prenotazione;

@Repository
public class PrenotazioneDAO extends AbstractDAO<Prenotazione,Integer> {
		
		@SuppressWarnings("unchecked")
		public List<Prenotazione> getStanzeOccupate(long start, long end){ 
			//definisci un nuovo criterio
			Criteria criteria = createCriteria();
			//criteria.add(Restrictions.lt("to",  new Date(start)));
			criteria.add(Restrictions.and(Restrictions.gt("to",  new Date(start)), Restrictions.lt("from", new Date(end))));
			//criteria.add(Restrictions.gt("from", new Date(end)));
			return criteria.list();
		}
		
		@SuppressWarnings("unchecked")
		public List<Prenotazione> GetPrenotazioniCliente(Integer id){
			Criteria criteria = createCriteria();
			//per cercare nella tabella clienti devo creare un'alias per il criteria
			//L'estensione della classe astratta dice al DAO a quale tabella del DB fare riferimento
			criteria.createAlias("cliente", "cl"); //("oggetto Java", "nome per il path")
			criteria.add(Restrictions.eq("cl.id", id));
			return criteria.list();
		}
		
		
}
