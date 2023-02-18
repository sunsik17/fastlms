package com.zerobase.fastlms.admin.repository;

import com.zerobase.fastlms.admin.entity.LmsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginLmsUsersRepository extends JpaRepository<LmsUser, Long> {
    List<LmsUser> findByUsername(String username);
}
