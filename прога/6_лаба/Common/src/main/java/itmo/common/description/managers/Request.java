package itmo.common.description.managers;

import itmo.common.description.Movie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Request implements Serializable {
    private Movie movie;
    private ArrayList<Movie> movies;;
    private String commandName;
    private String args = "";

    public Request (String commandName){
        this.commandName = commandName;
    }


    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Movie getObject() {
        return movie;

    }

    public Movie getMovie() {
        return movie;
    }

    public Request(String commandName, String args) {
        this.commandName = commandName;
        this.args = args;
    }
    public Request(String commandName, String args, Movie movie, ArrayList<Movie> movies) {
        this.commandName = commandName;
        this.args = args;
        this.movie = movie;
        this.movies = movies;
    }
    public Request(String commandName, String args, Movie movie) {
        this.commandName = commandName;
        this.args = args;
        this.movie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(commandName, request.commandName) && Objects.equals(args, request.args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandName, args);
    }
    public String toString() {
        return "Request{" +
                "commandName='" + commandName + '\'' +
                ", args='" + args + '\'' +
                ", object=" + movie +
                '}';
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
