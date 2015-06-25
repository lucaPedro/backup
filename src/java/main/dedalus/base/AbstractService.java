package dedalus.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
public abstract class AbstractService<T, ID extends Serializable, DAO extends AbstractDAO<T, ID>>
{

	@Autowired
	protected DAO dao;

	@Transactional(readOnly = true)
	public List<T> findAll()
	{
		return dao.findAll();
	}

	@Transactional(readOnly = true)
	public T findById(ID id, String... fetchProperties)
	{
		return dao.findById(id, fetchProperties);
	}

	@Transactional(readOnly = true)
	public List<T> findByName(String name, String... fetchProperties)
	{
		return dao.findByName(name, fetchProperties);
	}

	@Transactional(readOnly = true)
	public List<T> findByName(String name, MatchMode matchMode,
			String... fetchProperties)
	{
		return dao.findByName(name, matchMode, fetchProperties);
	}

	@Transactional(readOnly = false)
	public ID create(T entity)
	{
		return dao.create(entity);
	}

	@Transactional(readOnly = false)
	public T update(T entity)
	{
		return dao.update(entity);
	}

	@Transactional(readOnly = false)
	public void delete(T entity)
	{
		dao.delete(entity);
	}

	@Transactional(readOnly = false)
	public void deleteById(ID id)
	{
		dao.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public List<T> findActive()
	{
		return dao.findActive();
	}

}