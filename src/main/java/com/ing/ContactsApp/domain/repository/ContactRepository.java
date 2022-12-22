package com.ing.ContactsApp.domain.repository;

import com.ing.ContactsApp.domain.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Page<Contact> findByUserId(Long userId, Pageable pageable);


    boolean existsByIdAndUserId(Long contactId, Long userId);

    boolean existsByPhoneNumberAndUserId(String phoneNumber, Long userId);

    @Query("""
            select c from Contact c
            where c.user.id = ?1 and upper(c.firstName) like upper(concat('%', ?2, '%')) and upper(c.lastName) like upper(concat('%', ?3, '%')) and upper(c.address) like upper(concat('%', ?4, '%')) and upper(c.phoneNumber) like upper(concat('%', ?5, '%')) and upper(c.contactType.type) like upper(concat('%', ?6, '%'))""")
    Page<Contact> searchContacts(Long id, @Nullable String firstName, @Nullable String lastName, @Nullable String address, @Nullable String phoneNumber, @Nullable String type, Pageable pageable);

    Page<Contact> findByUser_IdIsAndFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrPhoneNumberContains(Long id, String firstName, String lastName, String phoneNumber, Pageable pageable);


}
