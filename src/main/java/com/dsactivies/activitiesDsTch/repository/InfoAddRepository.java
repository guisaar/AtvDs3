package com.dsactivies.activitiesDsTch.repository;


import com.dsactivies.activitiesDsTch.models.InfoAdd;
import com.dsactivies.activitiesDsTch.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface InfoAddRepository extends CrudRepository<InfoAdd, String> {
Iterable<InfoAdd> findByEvent(Event event);
InfoAdd findByAuthorId(String authorId);
}
