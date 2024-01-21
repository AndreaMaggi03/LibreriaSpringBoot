package io.example.library.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Libri")
public class Libro implements Serializable {

    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Questo campo non puo essere nullo")
    String autore, annoPubblicazione,titolo ;
    @NotNull(message = "Questo campo non puo essere nullo")
    Double price;

    @Transient
    @OneToMany(mappedBy = "libro", fetch = FetchType.EAGER)
    public Set<UserLibro> userLibros = new HashSet<>();

    public Libro(){}

    public Libro(String autore, String titolo, String publicationYear, Double price) {
        this.autore = autore;
        this.titolo = titolo;
        this.annoPubblicazione = publicationYear;
        this.price = price;
    }

    public String getAutore() {
        return autore;
    }
    public String getTitolo() {
        return titolo;
    }
    public void setAutore(String autore) {
        this.autore = autore;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    public String getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public void setAnnoPubblicazione(String annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "autore='" + autore + '\'' +
                ", titolo='" + titolo + '\'' +
                ", annoPubblicazione='" + annoPubblicazione + '\'' +
                ", prezzo=" + price +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
