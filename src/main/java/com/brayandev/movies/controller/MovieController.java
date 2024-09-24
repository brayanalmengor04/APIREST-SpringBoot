package com.brayandev.movies.controller;

import com.brayandev.movies.models.Movie;
import com.brayandev.movies.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Esto hara que hara lo de peticiones apirest / ruta a que manera
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    // Esto inyecta la dependencia y evita la creaccion de objetos
    @Autowired
    private MovieRepository movieRepository;

    // Generar unas acciones -----
    /**
     * 1. Listar todas las peliculas
     * 2. Buscar por id
     */
    // Optimizar que parte se va hacer segun la ruta @GetMapping("/all")

    @CrossOrigin
    @GetMapping
    public List<Movie> getAllMovies() {return movieRepository.findAll();}

    // Esto enviamos una respuesta si no encuentra nada

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
            return movie.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    // Post en apirest es para guardar datos (los datos estan en el cuerpo de la peticion no en el path
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        // Esto si no me se el numero de estado Httpsstatus (201)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (!movieRepository.existsById(id)) return ResponseEntity.notFound().build();

        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    // Actualizar pelicula
    @CrossOrigin
    @PutMapping("/{id}")
    public  ResponseEntity<Movie> updateMovie(@PathVariable Long id,@RequestBody Movie movie) {
        if (!movieRepository.existsById(id)) return ResponseEntity.notFound().build();
        // No trae el id cuando lo recuperas
        movie.setId(id);
        Movie updatedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(updatedMovie);
    }

    @CrossOrigin
    @GetMapping("/vote/{id}/{rating}")
    public ResponseEntity<Movie> voteMovie(@PathVariable Long id,@PathVariable double rating ) {
        if (!movieRepository.existsById(id)) return ResponseEntity.notFound().build();

        Optional<Movie> optional = movieRepository.findById(id);
        Movie movie = optional.get();
        // Movie.rating
        // Movie.votes calcular la media

        double newRating = ((movie.getVotes() * movie.getRating()) + rating) / (movie.getVotes()+1);
        movie.setVotes(movie.getVotes()+1);
        movie.setRating(newRating);
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(savedMovie);
    }
}
