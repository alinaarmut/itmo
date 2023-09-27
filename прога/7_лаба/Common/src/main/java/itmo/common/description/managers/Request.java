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
    private User user;

    public String getCommandName() {
        return commandName;
    }

    public String getArgs() {
        return args;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Request(ResponseStatus OK, String commandName, Movie help) {
        this.commandName = commandName.trim();
    }
    public Request(String commandName, String args, User user, Movie movie) {
        this.commandName = commandName.trim();
        this.args = args.trim();
        this.movie = movie;
        this.user = user;
    }

    public Request(String commandName, String args) {
        this.commandName = commandName.trim();
        this.args = args;
    }

    public Request(String commandName, Movie movie) {
        this.commandName = commandName.trim();
        this.movie = movie;
    }

    public Request(String commandName, String args, Movie movie) {
        this.commandName = commandName.trim();
        this.args = args.trim();
        this.movie = movie;
    }
    public Request(String commandName, String args, User object) {
        this.commandName = commandName.trim();
        this.args = args.trim();
        this.user = object;
    }

    public boolean isEmpty() {
        return commandName.isEmpty() && args.isEmpty() && movie == null;
    }
    public User getUser() {
        return user;
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


}

