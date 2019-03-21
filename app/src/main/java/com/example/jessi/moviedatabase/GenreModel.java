package com.example.jessi.moviedatabase;

public class GenreModel {
    private Genre[] genres;

    public Genre[] getGenres() { return genres; }
}

class Genre {
    private int id;
    private String name;

    public int getId() { return id; }
    public String getName() { return name; }
}
