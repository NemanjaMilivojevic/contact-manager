package com.ing.ContactsApp.service;

import com.ing.ContactsApp.domain.entity.Contact;
import com.ing.ContactsApp.domain.repository.ContactRepository;
import com.ing.ContactsApp.domain.repository.ContactTypeRepository;
import com.ing.ContactsApp.domain.repository.UserRepository;
import com.ing.ContactsApp.service.exception.AlreadyExistsException;
import com.ing.ContactsApp.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactTypeRepository contactTypeRepository;
    private final UserRepository userRepository;

    public ContactService(ContactRepository contactRepository, ContactTypeRepository contactTypeRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.contactTypeRepository = contactTypeRepository;
        this.userRepository = userRepository;
    }

    public void saveContact(Contact contact, Long userId, String contactType) {
        var user = userRepository.findById(userId).get();
        var type = contactTypeRepository.findByTypeIgnoreCase(contactType)
                .orElseThrow(()-> new NotFoundException("Contact type not found."));
        if (contactRepository.existsByPhoneNumberAndUserId(contact.getPhoneNumber(), user.getId())) {
            throw new AlreadyExistsException("Phone number exists");
        }
        contact.setContactType(type);
        contact.setUser(user);
        contactRepository.save(contact);
    }

    public void updateContact(Contact contact, Long userId, String contactType) {
        var user = userRepository.findById(userId).get();
        if (!contactRepository.existsByIdAndUserId(contact.getId(), userId)) {
            throw new NotFoundException("Contact for this user doesn`t exists");
        }
        if (nonNull(contactType) && !contactType.isEmpty()) {
            var type = contactTypeRepository.findByTypeIgnoreCase(contactType)
                    .orElseThrow(()-> new NotFoundException("Contact type not found."));
            contact.setContactType(type);
        }
        contact.setUser(user);
        contactRepository.save(contact);
    }

    public Page<Contact> findAllContacts(Long userId, Pageable pageable) {
        return contactRepository.findByUserId(userId, pageable);
    }

    public void deleteContact(Long contactId, Long securityUserId) {
        var contact = contactRepository.findById(contactId).orElseThrow(() -> new NotFoundException("Contact not found"));
        if (!contact.getUser().getId().equals(securityUserId)) {
            throw new NotFoundException("Contact for this user doesn`t exist"); // 401
        }
        contactRepository.delete(contact);
    }

    public void deleteAnyContact(Long contactId) {
        var contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new NotFoundException("Contact not found"));
        contactRepository.delete(contact);
    }

    public Contact findById(Long contactId) {
        return contactRepository.findById(contactId)
                .orElseThrow(() -> new NotFoundException("Contact not found"));
    }

    public Page<Contact> searchContact(Long userId, String firstName, String lastName, String address, String phoneNumber, String contactType, Pageable pageable) {
        return contactRepository.searchContacts(userId, firstName, lastName, address, phoneNumber, contactType, pageable);
    }

    public Page<Contact> search(Long id, String searchQuery, Pageable pageable) {
        return contactRepository.findByUser_IdIsAndFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrPhoneNumberContains(id, searchQuery, searchQuery, searchQuery, pageable);
    }
}
