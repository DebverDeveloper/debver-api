package com.donatoordep.debver.repositories;

import com.donatoordep.debver.entities.ProfileUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileUserRepository extends JpaRepository<ProfileUser, Long> {
}
