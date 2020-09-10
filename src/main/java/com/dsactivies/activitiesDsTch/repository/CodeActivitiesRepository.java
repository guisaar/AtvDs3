package com.dsactivies.activitiesDsTch.repository;
import com.dsactivies.activitiesDsTch.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CodeActivitiesRepository extends JpaRepository<Event, Long> {
Event findByActId(long actId);
Event findByName(String name);

@Query("select count(name) from Event")
    public int CountEvents();

@Query("select count(namId) from FilesDoc")
    public int CountFiles();

    @Query("select count(authorId) from InfoAdd")
    public int CountInf();

}
