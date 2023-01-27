package com.driver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Repository
public class MovieRepository {
    Map<String, Movie> movies = new HashMap<>();
    Map<String, Director> directors = new HashMap<>();
    Map<Director, HashSet<Movie>> mvPair = new HashMap<>();

    public void addMovie(Movie movie) {
        String name = movie.getName();
        movies.put(name, movie);
    }

    public void addDirector(Director director) {
        String name = director.getName();
        directors.put(name, director);
    }

    public void addMovieDirectorPair(String movieName, String directorName) {
        Movie movie = movies.get(movieName);
        Director director = directors.get(directorName);
        HashSet<Movie> set;
        if(!mvPair.containsKey(director)) {
            set = new HashSet<>();
        }else {
            set = mvPair.get(director);
        }
        set.add(movie);
        mvPair.put(director, set);
    }

    public Movie getMovieByName(String name) {
        Movie movie = movies.get(name);
        return movie;
    }

    public Director getDirectorByName(String name) {
        Director director = directors.get(name);
        return director;
    }

    public List<String> getMoviesByDirectorName(String name) {
        Director director = directors.get(name);
        HashSet<Movie> dirMovies = mvPair.get(director);

        List<String> list = new ArrayList<>();
        for(Movie movie: dirMovies) {
            list.add(movie.getName());
        }

        return list;
    }

    public List<String> findAllMovies() {
        List<String> list = new ArrayList<>();
        for(String name: movies.keySet()) {
            list.add(name);
        }

        return list;
    }

    public void deleteDirectorByName(String name) {
        Director director = directors.get(name);
        HashSet<Movie> dirMovies = mvPair.get(director);
        for (Movie movie: dirMovies) {
            movies.remove(movie.getName());
        }
        dirMovies.clear();
        mvPair.remove(director);
        directors.remove(name);
    }

    public void deleteAllDirectors() {
        //Deleting all directors having movies
        for (Director director: mvPair.keySet()) {
            this.deleteDirectorByName(director.getName());
        }

        //Now deleting all directors
        directors.clear();
    }
}
