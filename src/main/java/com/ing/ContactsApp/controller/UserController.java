package com.ing.ContactsApp.controller;

import com.ing.ContactsApp.controller.dto.ContactRequestDto;
import com.ing.ContactsApp.controller.dto.ContactResponseDto;
import com.ing.ContactsApp.controller.dto.CustomPageDto;
import com.ing.ContactsApp.controller.mapper.ContactMapper;
import com.ing.ContactsApp.domain.SecurityUser;
import com.ing.ContactsApp.service.ContactService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final ContactMapper contactMapper;
    private final ContactService contactService;

    public UserController(ContactMapper contactMapper, ContactService contactService) {
        this.contactMapper = contactMapper;
        this.contactService = contactService;
    }

    @PostMapping("/contact")
    public ResponseEntity<ContactRequestDto> saveContact(@Valid @RequestBody ContactRequestDto contactDto, @AuthenticationPrincipal SecurityUser securityUser) {
        var contact = contactMapper.mapToEntity(contactDto);
        contactService.saveContact(contact, securityUser.getId(), contactDto.getContactType());
        return ResponseEntity.status(HttpStatus.CREATED).body(contactDto);
    }

    @PutMapping("contact/{id}")
    public ResponseEntity<ContactRequestDto> updateContact(@PathVariable(name = "id") Long id, @RequestBody ContactRequestDto contactDto, @AuthenticationPrincipal SecurityUser securityUser) {
        var oldContact = contactService.findById(id);
        var newContact = contactMapper.editContact(oldContact, contactDto);
        contactService.updateContact(newContact, securityUser.getId(), contactDto.getContactType());
        return ResponseEntity.status(HttpStatus.OK).body(contactDto);
    }

    @GetMapping("/contact")
    public ResponseEntity<CustomPageDto<ContactResponseDto>> findContacts(Pageable pageable, @AuthenticationPrincipal SecurityUser securityUser) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("lastName").ascending().and(Sort.by("firstName")));
        var result = contactService.findAllContacts(securityUser.getId(), pageable).map(ContactMapper::mapToDto);
        var customPageDto = new CustomPageDto<>(result.getContent(), result.getNumber(), result.getSize(), result.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(customPageDto);
    }

    @DeleteMapping("contact/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable(name = "id") Long contactId, @AuthenticationPrincipal SecurityUser securityUser) {
        contactService.deleteContact(contactId, securityUser.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("contact/v1/search")
    public ResponseEntity<CustomPageDto> searchContact(@AuthenticationPrincipal SecurityUser securityUser,
                                           @RequestParam(name = "firstName") @Nullable String firstName,
                                           @RequestParam(name = "lastName") @Nullable String lastName,
                                           @RequestParam(name = "address") @Nullable String address,
                                           @RequestParam(name = "phoneNumber") @Nullable String phoneNumber,
                                           @RequestParam(name = "contactType") @Nullable String contactType,
                                           Pageable pageable){
        var result = contactService.searchContact(securityUser.getId(), firstName,lastName,address,phoneNumber, contactType,pageable).map(ContactMapper::mapToDto);
        CustomPageDto customPageDto = new CustomPageDto<>(result.getContent(), result.getNumber(), result.getSize(), result.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(customPageDto);
    }

    @GetMapping("contact/search")
    public ResponseEntity<CustomPageDto> search(@AuthenticationPrincipal SecurityUser securityUser,
                                    @RequestParam(name = "search",required = false) String search,
                                    Pageable pageable){
        var result = contactService.search(securityUser.getId(), search,pageable);
        var customPageDto = new CustomPageDto<>(result.getContent(), result.getNumber(), result.getSize(), result.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(customPageDto);
    }

}
