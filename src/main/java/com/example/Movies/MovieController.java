package com.example.Movies;

import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Controller
public class MovieController {


    private static final String API = "https://api.themoviedb.org/3/movie/now_playing?api_key=e02372b05f7b71693db90ff6957a605c";

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home (Model model) {
        return "home";
    }

    @RequestMapping(path = "/now-playing", method = RequestMethod.GET)
    public String nowPlaying (Model model) {
        List<Movie> nowPlaying = getMovies();
        model.addAttribute("nowPlaying", nowPlaying );

        return "now-playing";
    }

    @RequestMapping(path = "/medium-popular-long-name", method = RequestMethod.GET)
    public String mPLN (Model model) {
        model.addAttribute("movies", getMovies());
        List<Movie> movie = getMovies();
        movie.stream()
                .filter(e -> e.getPopularity() >= 30 && e.getPopularity() <= 80)
                .filter(e -> e.getTitle().length() >= 10)
                .collect(Collectors.toList());
        return "medium-popular-long-name";
    }

    public static List<Movie> getMovies() {
        RestTemplate restTemplate = new RestTemplate();
        List<Movie> movies = restTemplate.getForObject(API, ResultsPage.class).getResults();
        System.out.println(movies);
        return movies;

    }

}
