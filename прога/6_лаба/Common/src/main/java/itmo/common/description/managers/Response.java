package itmo.common.description.managers;


import itmo.common.description.Movie;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

public class Response implements Serializable {
    private String response = "";
    private Collection<Movie> collection;


    public Response(String message, String result) {
    }


    public Response() {

    }

    public Response(Collection<Movie> collection) {
        this.collection = collection;
    }
    public Response( String response, Collection<Movie> collection) {
        this.response = response.trim();
        this.collection = collection.stream()
                .sorted(Comparator.comparing(Movie::getId))
                .toList();
    }

    public Response(String response) {
        this.response = response;
    }


    public Collection<Movie> getCollection() {
        return collection;
    }

    public void setCollection(Collection<Movie> collection) {
        this.collection = collection;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Response{" +
                "response='" + response + '\'' +
                ", collection=" + collection +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response1 = (Response) o;
        return Objects.equals(response, response1.response) && Objects.equals(collection, response1.collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(response, collection);
    }
}
