package org.example.model;

import java.io.Serializable;
import java.util.Objects;

public class Manufacturer implements Serializable {

    static final long serialVersionUID = 2537L;
    private int id;
    private String name;
    private String country;

    public Manufacturer(int id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "\t#" + id + ". Manufacturer «" + name + "»"
                + ". Country: " + country;
    }

}
