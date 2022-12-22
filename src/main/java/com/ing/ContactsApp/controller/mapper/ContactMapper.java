package com.ing.ContactsApp.controller.mapper;

import com.ing.ContactsApp.controller.dto.ContactRequestDto;
import com.ing.ContactsApp.controller.dto.ContactResponseDto;
import com.ing.ContactsApp.domain.entity.Contact;
import org.springframework.stereotype.Component;
import static java.util.Objects.nonNull;

@Component
public class ContactMapper {

    public Contact mapToEntity(ContactRequestDto contactDto) {
        var contact = new Contact();
        contact.setFirstName(contactDto.getFirstName());
        contact.setLastName(contactDto.getLastName());
        contact.setAddress(contactDto.getAddress());
        contact.setPhoneNumber(contactDto.getPhoneNumber());
        return contact;
    }

    public Contact editContact(Contact contact, ContactRequestDto contactDto) {
        if (nonNull(contactDto.getFirstName()) && !contactDto.getFirstName().isEmpty()) {
            contact.setFirstName(contactDto.getFirstName());
        }
        if (nonNull(contactDto.getLastName()) && !contactDto.getLastName().isEmpty()) {
            contact.setLastName(contactDto.getLastName());
        }
        if (nonNull(contactDto.getAddress()) && !contactDto.getAddress().isEmpty()) {
            contact.setAddress(contactDto.getAddress());
        }
        if (nonNull(contactDto.getPhoneNumber()) && !contactDto.getPhoneNumber().isEmpty()) {
            contact.setPhoneNumber(contactDto.getPhoneNumber());
        }
        return contact;
    }

    public static ContactResponseDto mapToDto(Contact contact) {
        ContactResponseDto contactResponseDto = new ContactResponseDto();
        contactResponseDto.setFirstName(contact.getFirstName());
        contactResponseDto.setLastName(contact.getLastName());
        contactResponseDto.setAddress(contact.getAddress());
        contactResponseDto.setPhoneNumber(contact.getPhoneNumber());
        contactResponseDto.setContactType(contact.getContactType().getType());
        return contactResponseDto;
    }
}
