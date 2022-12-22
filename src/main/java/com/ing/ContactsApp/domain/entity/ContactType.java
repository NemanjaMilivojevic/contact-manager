package com.ing.ContactsApp.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "contact_type")
public class ContactType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

    public ContactType() {
    }

    public ContactType(String type) {
        this.type = type;
    }

    public Long getId() {return id;}

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
