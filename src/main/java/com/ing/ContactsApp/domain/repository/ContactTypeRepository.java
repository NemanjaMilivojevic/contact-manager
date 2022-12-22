package com.ing.ContactsApp.domain.repository;

import com.ing.ContactsApp.domain.entity.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {
    boolean existsByTypeIgnoreCase(String type);

    Optional<ContactType> findByTypeIgnoreCase(String contactType);




}
