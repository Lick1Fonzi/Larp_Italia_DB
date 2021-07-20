package larp_italia;

import java.util.Date;

public class Evento {

	private String titolo;
	private String nome_campagna;
	private Date inizio;
	private Date fine;
	private int limite_iscrizione;
	private int costo;
	private String indirizzo;
	
	public Evento() {
		
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getNome_campagna() {
		return nome_campagna;
	}

	public void setNome_campagna(String nome_campagna) {
		this.nome_campagna = nome_campagna;
	}

	public Date getInizio() {
		return inizio;
	}

	public void setInizio(Date inizio) {
		this.inizio = inizio;
	}

	public Date getFine() {
		return fine;
	}

	public void setFine(Date fine) {
		this.fine = fine;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public int getLimite_iscrizione() {
		return limite_iscrizione;
	}

	public void setLimite_iscrizione(int limite_iscrizione) {
		this.limite_iscrizione = limite_iscrizione;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

}
