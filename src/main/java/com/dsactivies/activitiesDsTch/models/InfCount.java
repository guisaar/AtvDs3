package com.dsactivies.activitiesDsTch.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_infcounts")
public class InfCount {
    @Id
    private long InfCount;

    public long getInfCount() {
        return InfCount;
    }

    public void setInfCount(long infCount) {
        InfCount = infCount;
    }
}
