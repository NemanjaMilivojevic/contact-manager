package com.ing.ContactsApp.service;

import com.ing.ContactsApp.domain.entity.ContactType;
import com.ing.ContactsApp.domain.repository.ContactTypeRepository;
import com.ing.ContactsApp.service.exception.AlreadyExistsException;
import com.ing.ContactsApp.service.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ContactTypeService {
    private final ContactTypeRepository contactTypeRepository;


    public ContactTypeService(ContactTypeRepository contactTypeRepository) {
        this.contactTypeRepository = contactTypeRepository;
    }

    public void saveContactType(ContactType contactType) {
        if (contactTypeRepository.existsByTypeIgnoreCase(contactType.getType())) {
            throw new AlreadyExistsException("Contact type already exists.");
        }
        contactTypeRepository.save(contactType);
    }

    public ContactType findById(Long contactTypeId) {
        return contactTypeRepository.findById(contactTypeId)
                .orElseThrow(() -> new NotFoundException("Contact type does not exists"));
    }

    public ContactType findByType(String contactType){
        return contactTypeRepository.findByTypeIgnoreCase(contactType)
                .orElseThrow(()-> new NotFoundException("Contact type not found"));
    }

    public void deleteContactType(Long contactTypeId){
        var contactType = contactTypeRepository.findById(contactTypeId)
                .orElseThrow(()-> new NotFoundException("Contact type not found"));
        contactTypeRepository.delete(contactType);
    }
}
