package com.ing.ContactsApp.domain.repository;

import com.ing.ContactsApp.domain.entity.User;
import com.ing.ContactsApp.service.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String userEmail);

    default User getUserById(Long userId){
      return this.findById(userId).orElseThrow(()-> new NotFoundException("Not found"));
    }
    boolean existsByEmail(String userEmail);



}
