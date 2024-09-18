package com.gra.recist.domain.repository;

import com.gra.recist.domain.event.IEvent;

import java.util.List;

public interface IEventRepository {
    void persist(IEvent data);
}
