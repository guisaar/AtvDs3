package com.dsactivies.activitiesDsTch.models;


import com.dsactivies.activitiesDsTch.repository.CodeActivitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="tb_counts")
public class Counts {
    @Id
    private long EventCount;

    public long getEventCount() {
        return EventCount;
    }

    public void setEventCount(long eventCount) {
        EventCount = eventCount;
    }


}
