package com.ing.ContactsApp.controller.dto;

import javax.validation.constraints.NotBlank;

public class ContactTypeDto {
    private Long id;
    @NotBlank
    private String contactType;

    public ContactTypeDto() {
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

}
