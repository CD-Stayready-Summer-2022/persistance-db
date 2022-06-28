package com.codedifferentlty.users;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface Repository<T,I>{
    T save(T entity) throws IOException;
    Optional<T> findById(I id);
    List<T> findAll();
    void delete(I id);
}
