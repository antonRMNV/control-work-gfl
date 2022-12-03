package org.example.serialization;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serialization<T> implements Serializable<T> {

    private String path;
    public Serialization(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public List<T> read() {
        List<T> list;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            list = ((ArrayList<T>)ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void write(List<T> objects) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(objects);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
