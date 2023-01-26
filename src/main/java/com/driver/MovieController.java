package com.driver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class MovieController {
    Map<String, Movie> movies = new HashMap<>();
    Map<String, Director> directors = new HashMap<>();
    Map<Director, HashSet<Movie>> mvPair = new HashMap<>();

    @PostMapping("/movies/add-movie")
    public ResponseEntity addMovie(@RequestBody Movie movie) {
        String name = movie.getName();
        movies.put(name, movie);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/movies/add-director")
    public ResponseEntity addDirector(@RequestBody Director director) {
        String name = director.getName();
        directors.put(name, director);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/movies/add-movie-director-pair")
    public ResponseEntity addMovieDirectorPair(@RequestParam("q") String mname, String dname) {
        Movie movie = movies.get(mname);
        Director director = directors.get(dname);
        HashSet<Movie> set;
        if(!mvPair.containsKey(director)) {
            set = new HashSet<>();
            set.add(movie);
        }else {
            set = mvPair.get(director);
            set.add(movie);
        }

        mvPair.put(director, set);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/movies/get-movie-by-name/{name}")
    public ResponseEntity<Movie> getMovieByName(@PathVariable String name) {
        Movie movie = movies.get(name);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @GetMapping("/movies/get-director-by-name/{name}")
    public ResponseEntity<Director> getDirectorByName(@PathVariable String name) {
        Director director = directors.get(name);
        return new ResponseEntity<>(director, HttpStatus.OK);
    }

    @GetMapping(" /movies/get-movies-by-director-name/{director}")
    public ResponseEntity<List<String>> getMoviesByDirectorName(@PathVariable String name) {
        Director director = directors.get(name);
        HashSet<Movie> dirMovies = mvPair.get(director);

        List<String> list = new ArrayList<>();
        for(Movie movie: dirMovies) {
            list.add(movie.getName());
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/movies/get-all-movies")
    public ResponseEntity<List<String>> findAllMovies() {
        List<String> list = new ArrayList<>();
        for(String name: movies.keySet()) {
            list.add(name);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/movies/delete-director-by-name")
    public ResponseEntity deleteDirectorByName(@RequestParam String name) {
        Director director = directors.get(name);
        HashSet<Movie> dirMovies = mvPair.get(director);
        for (Movie movie: dirMovies) {
            movies.remove(movie.getName());
        }
        dirMovies.clear();
        mvPair.remove(director);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/movies/delete-all-directors")
    public ResponseEntity deleteAllDirectors() {
        for (Director director: mvPair.keySet()) {
            this.deleteDirectorByName(director.getName());
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
