package com.dsactivies.activitiesDsTch.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="tb_inf")
public class InfoAdd {


    @Id
    private String authorId;
    @NotBlank
    private String author;

    @ManyToOne
    private Event event;

    @NotBlank
    private String supportLink;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSupportLink() {
        return supportLink;
    }

    public void setSupportLink(String supportLink) {
        this.supportLink = supportLink;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
