package dedalus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tipologia")
public class Tipologia {
	
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "prezzo") //nome del campo nel DB
	private Integer prezzo;
	
	@ManyToOne
	@JoinColumn(name = "formato_id")
	private Formato formato;
	
	@ManyToOne
	@JoinColumn(name="piano_id")
	private Piano piano;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Integer prezzo) {
		this.prezzo = prezzo;
	}

	public Formato getFormato() {
		return formato;
	}

	public void setFormato(Formato formato) {
		this.formato = formato;
	}

	public Piano getPiano() {
		return piano;
	}

	public void setPiano(Piano piano) {
		this.piano = piano;
	}
	
	

}
