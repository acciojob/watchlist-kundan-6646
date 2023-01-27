package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public ResponseEntity addMovie(Movie movie) {
        movieRepository.addMovie(movie);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    public ResponseEntity addDirector(Director director) {
        movieRepository.addDirector(director);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    public ResponseEntity addMovieDirectorPair(String movie, String director) {
        movieRepository.addMovieDirectorPair(movie, director);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    public ResponseEntity<Movie> getMovieByName(String name) {
        Movie movie = movieRepository.getMovieByName(name);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    public ResponseEntity<Director> getDirectorByName(String name) {
        Director director = movieRepository.getDirectorByName(name);
        return new ResponseEntity<>(director, HttpStatus.OK);
    }

    public ResponseEntity<List<String>> getMoviesByDirectorName(String name) {
        List<String> movies = movieRepository.getMoviesByDirectorName(name);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    public ResponseEntity<List<String>> findAllMovies() {
        List<String> list = movieRepository.findAllMovies();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    public ResponseEntity deleteDirectorByName(String name) {
        movieRepository.deleteDirectorByName(name);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    public ResponseEntity deleteAllDirectors() {
        movieRepository.deleteAllDirectors();
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
