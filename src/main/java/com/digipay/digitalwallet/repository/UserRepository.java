package com.digipay.digitalwallet.repository;

import com.digipay.digitalwallet.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUserId(String userId);
    boolean existsUserByUserId(String userId);

}
