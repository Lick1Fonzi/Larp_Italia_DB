/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    String cf;
    String email;
    String cellulare;
    String nome;
    String cognome;
    types type = types.GIOCATORE;
    int id;
    
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

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }
    
}