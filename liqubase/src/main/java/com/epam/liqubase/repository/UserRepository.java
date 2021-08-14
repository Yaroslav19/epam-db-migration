package com.epam.liqubase.repository;

import com.epam.liqubase.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmployee_Fio(String fio);
}
