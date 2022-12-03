package org.example.serialization;

import java.util.List;

public interface Serializable<T> {
    List<T> read();
    void write(List<T> objects);
}
