package com.shopme.admin.user;

import com.shopme.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("select u from User u where u.email = :email")
    public User getUserByEmail(@Param("email") String email);
    public Long countById(Integer id);
    @Query("UPDATE  User u set u.enabled = ?2 where u.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id,boolean enabled);
}
