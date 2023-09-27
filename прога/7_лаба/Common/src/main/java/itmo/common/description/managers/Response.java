package itmo.common.description.managers;


import itmo.common.description.Movie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

public class Response implements Serializable {
    private ResponseStatus responseStatus;
    private String response = "";
    private ArrayList<Movie> movie;

    public Response() {
    }

    public Response(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Response(ResponseStatus responseStatus, String response) {
        this.responseStatus = responseStatus;
        this.response = response.trim() + "\n";
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public String getResponse() {
        return response;
    }

    public ArrayList<Movie> getMovie() {
        return movie;
    }

    public void setMovie(ArrayList<Movie> movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "Response{" +
                "response='" + response + '\'' +
                ", collection=" + movie +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response1 = (Response) o;
        return Objects.equals(response, response1.response) && Objects.equals(movie, response1.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(response, movie);
    }
}
