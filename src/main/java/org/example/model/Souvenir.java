package org.example.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Souvenir implements Serializable {
    static final long serialVersionUID = 2538L;
    private int id;
    private String name;
    private int manufacturerId;
    private LocalDate date;
    private int price;

    public Souvenir(int id, String name, int manufacturerId, LocalDate date, int price) {
        this.id = id;
        this.name = name;
        this.manufacturerId = manufacturerId;
        this.date = date;
        this.price = price;
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

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "\t#" + id + ". Souvenir «" + name + "»" +
                ", manufacturer's id: " + manufacturerId +
                ", date: " + date +
                ", price: " + price + "$";
    }
}
