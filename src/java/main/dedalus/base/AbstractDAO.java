package dedalus.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public abstract class AbstractDAO<T, ID extends Serializable>
{

	private Class<T> clazz;

	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	protected AbstractDAO()
	{
		this.clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public List<T> findAll()
	{
		return findByCriteria();
	}

	public T findById(ID id, String... fetchProperties)
	{
		return findById(id, false, fetchProperties);
	}

	@SuppressWarnings("unchecked")
	public T findById(ID id, boolean exclusiveLock, String... fetchProperties)
	{
		if (exclusiveLock || fetchProperties != null)
		{
			Criteria criteria = createCriteria(Restrictions.eq("id", id));
			if (exclusiveLock)
			{
				criteria.setLockMode(LockMode.PESSIMISTIC_WRITE);
			}
			for (String property : fetchProperties)
			{
				criteria.setFetchMode(property, FetchMode.JOIN);
			}
			return (T) criteria.uniqueResult();
		}
		else
		{
			return (T) getCurrentSession().get(clazz, id);
		}
	}

	public List<T> findByName(String name, String... fetchProperties)
	{
		return findByName(name, MatchMode.START, fetchProperties);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByName(String name, MatchMode matchMode,
			String... fetchProperties)
	{
		Criteria criteria = createCriteria(Restrictions.ilike("name", name,
				matchMode));
		criteria.addOrder(Order.asc("name"));
		if (fetchProperties != null)
		{
			for (String property : fetchProperties)
			{
				criteria.setFetchMode(property, FetchMode.JOIN);
			}
		}
		return (List<T>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public ID create(T entity)
	{
		return (ID) getCurrentSession().save(entity);
	}

	@SuppressWarnings("unchecked")
	public T update(T entity)
	{
		return (T) getCurrentSession().merge(entity);
	}

	public void delete(T entity)
	{
		getCurrentSession().delete(entity);
	}

	public void deleteById(ID id)
	{
		T entity = findById(id);
		delete(entity);
	}
	
	public List<T> findActive()
	{
		Boolean flag = false;
		for(Field f : this.clazz.getDeclaredFields())
		{
			if(f.getName().equals("active"))
			{
				flag = true;
				break;
			}
		}
		
		if(flag)
			return findByCriteria(Restrictions.eq("active", true));
		return findAll();
	}
	
	/**
	 * Funzione di utilità per le sottoclassi
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterions)
	{
		return createCriteria(criterions).list();
	}

	/**
	 * Funzione di utilità per le sottoclassi
	 */
	protected Criteria createCriteria(Criterion... criterions)
	{
		Criteria criteria = getCurrentSession().createCriteria(this.clazz);
		for (Criterion criterion : criterions)
		{
			criteria.add(criterion);
		}
		return criteria;
	}

	/**
	 * Funzione di utilità per le sottoclassi
	 */
	protected final Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}

}