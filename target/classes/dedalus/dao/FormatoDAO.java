package dedalus.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import dedalus.base.AbstractDAO;
import dedalus.domain.Formato;

@Repository
public class FormatoDAO extends AbstractDAO<Formato,Integer> {
	
	public List<Formato> readId(String f){
		List<Formato> list = findByCriteria(Restrictions.eq("descrizione", f));
		return list;
	}
}
