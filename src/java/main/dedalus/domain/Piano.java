package dedalus.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "piano")
public class Piano
{
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "num_piano")
	private Integer numPiano;
	
	@Column(name = "num_stanze")
	private Integer numStanze;
	
	
//	@OneToMany(mappedBy = "piano_id", fetch = FetchType.LAZY)
//	private List<Stanza> list;
	
	public Piano()
	{
		
	}

	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * @return the numPiano
	 */
	public Integer getNumPiano()
	{
		return numPiano;
	}

	/**
	 * @param numPiano the numPiano to set
	 */
	public void setNumPiano(Integer numPiano)
	{
		this.numPiano = numPiano;
	}

	/**
	 * @return the numStanze
	 */
	public Integer getNumStanze()
	{
		return numStanze;
	}

	/**
	 * @param numStanze the numStanze to set
	 */
	public void setNumStanze(Integer numStanze)
	{
		this.numStanze = numStanze;
	}
}
