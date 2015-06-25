package dedalus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stanza")
public class Stanza {
	
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "numero") //nome del campo nel DB
	private Integer numero;
	
	@Column(name = "num_posti_letto") 
	private Integer letti;
	
	@Column(name = "metri_quadri") 
	private Integer mq;
	
	@ManyToOne
	@JoinColumn(name = "piano_id")
	private Piano piano;
	
	@ManyToOne
	@JoinColumn(name="tipologia_id")
	private Tipologia tipologia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getLetti() {
		return letti;
	}

	public void setLetti(Integer letti) {
		this.letti = letti;
	}

	public Integer getMq() {
		return mq;
	}

	public void setMq(Integer mq) {
		this.mq = mq;
	}

	public Piano getPiano() {
		return piano;
	}

	public void setPiano(Piano piano) {
		this.piano = piano;
	}

	public Tipologia getTipologia() {
		return tipologia;
	}

	public void setTipologia(Tipologia tipologia) {
		this.tipologia = tipologia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stanza other = (Stanza) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Stanza [id=" + id + ", numero=" + numero + ", letti=" + letti
				+ ", mq=" + mq + ", piano=" + piano + ", tipologia="
				+ tipologia + "]";
	}
	
	
}
