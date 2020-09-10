package com.dsactivies.activitiesDsTch.repository;


import com.dsactivies.activitiesDsTch.models.FilesDoc;
import com.dsactivies.activitiesDsTch.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface FilesRepository extends CrudRepository<FilesDoc, String>{
    Iterable<FilesDoc> findByEvent(Event event);
    FilesDoc findByName(String name);
    FilesDoc findByNamId(String namId);
}
