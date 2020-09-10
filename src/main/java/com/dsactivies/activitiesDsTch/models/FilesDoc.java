package com.dsactivies.activitiesDsTch.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="tb_files")
public class FilesDoc {

    @Id
    private String namId;

    private long size;

    private String date, name, location;


    @ManyToOne
    private Event event;

    public FilesDoc() {
    }

    public String getNamId() {
        return namId;
    }

    public void setNamId(String namId) {
        this.namId = namId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
