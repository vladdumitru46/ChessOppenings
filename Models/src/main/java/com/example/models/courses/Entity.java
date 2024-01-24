package com.example.models.courses;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public abstract class Entity<ID> implements Serializable {

    @Id
    @Column(name = "id")
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}