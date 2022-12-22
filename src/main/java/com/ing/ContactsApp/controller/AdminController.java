package com.ing.ContactsApp.controller;

import com.ing.ContactsApp.controller.dto.ContactTypeDto;
import com.ing.ContactsApp.controller.dto.CustomPageDto;
import com.ing.ContactsApp.controller.dto.UserRequestDto;
import com.ing.ContactsApp.controller.mapper.UserMapper;
import com.ing.ContactsApp.domain.entity.ContactType;
import com.ing.ContactsApp.service.ContactService;
import com.ing.ContactsApp.service.ContactTypeService;
import com.ing.ContactsApp.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ContactTypeService contactTypeService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ContactService contactService;

    public AdminController(ContactTypeService contactTypeService, UserService userService, UserMapper userMapper, ContactService contactService) {
        this.contactTypeService = contactTypeService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.contactService = contactService;
    }

    @PostMapping("/contact-type")
    public ResponseEntity<ContactTypeDto> createContactType(@Valid @RequestBody ContactTypeDto contactTypeDto) {
        var type = new ContactType(contactTypeDto.getContactType());
        contactTypeService.saveContactType(type);
        return ResponseEntity.status(HttpStatus.CREATED).body(contactTypeDto);

    }

    @PostMapping("/user")
    public ResponseEntity<UserRequestDto> createNewUser(@Valid @RequestBody UserRequestDto userDto) {
        var user = userMapper.mapToEntity(userDto);
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PutMapping("/contact-type/{id}")
    public ResponseEntity<ContactTypeDto> updateContactType(@PathVariable(name = "id") Long id, @RequestBody ContactTypeDto contactTypeDto) {
        var oldContactType = contactTypeService.findById(id);
        oldContactType.setType(contactTypeDto.getContactType());
        contactTypeService.saveContactType(oldContactType);
        return ResponseEntity.status(HttpStatus.CREATED).body(contactTypeDto);
    }

    @DeleteMapping("/contact-type/{contactTypeId}")
    public ResponseEntity<String> deleteContactType(@PathVariable(name = "id") Long contactTypeId) {

        contactTypeService.deleteContactType(contactTypeId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable(name = "id") Long id) {
        contactService.deleteAnyContact(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserRequestDto> updateUser(@PathVariable(name = "id") Long id,@Valid @RequestBody UserRequestDto userDto) {
        var oldUser = userService.findById(id);
        var oldEmail = oldUser.getEmail();
        var updatedUser = userMapper.editUser(oldUser, userDto);
        userService.updateUser(updatedUser,oldEmail);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("/user")
    public ResponseEntity<CustomPageDto> findUsers(Pageable pageable){
        var result = userService.findAllUsers(pageable).map(UserMapper::mapToDto);
        var customPageDto = new CustomPageDto<>(result.getContent(), result.getNumber(), result.getTotalPages(), result.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(customPageDto);
    }
}
