package larp_italia;
/**
 *
 * @author giokk
 */
public class User {
    public enum types{
        GIOCATORE,
        DIPENDENTE
    }
    
    private String cf;
    private String email;
    private String cellulare;
    private String nome;
    private String cognome;
    private int stipendio;
    private types type;
    
    public User(){}
    
    public String getCf() {
       return cf;
    }

    public String getEmail() {
        return email;
    }

    public String getCellulare() {
        return cellulare;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }


    public types getType() {
        return type;
    }
    
    // Setters

    public void setCf(String cf) {
        this.cf = cf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setType(types type) {
        this.type = type;
    }

	public int getStipendio() {
		return stipendio;
	}

	public void setStipendio(int stipendio) {
		this.stipendio = stipendio;
	}

}
