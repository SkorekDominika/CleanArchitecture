package com.gra.recist.domain.repository;

public interface CrudRepository<ID, T> {

    T create(T data);

    T read(ID id);

    T update(T data);

    T delete(ID id);
}
