package com.clean.arch.domain.repository;

import com.clean.arch.domain.event.IEvent;

public interface IEventRepository {
  void persist(IEvent data);
}
