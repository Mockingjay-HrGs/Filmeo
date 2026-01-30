package com.example.filmeo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "film_cast")
@IdClass(FilmCastId.class)
public class FilmCast {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "is_main", nullable = false)
    private boolean main;

    @Column(name = "character_name", length = 120)
    private String characterName;

    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }

    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

    public boolean isMain() { return main; }
    public void setMain(boolean main) { this.main = main; }

    public String getCharacterName() { return characterName; }
    public void setCharacterName(String characterName) { this.characterName = characterName; }
}
