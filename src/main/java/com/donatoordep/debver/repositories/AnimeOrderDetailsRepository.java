package com.donatoordep.debver.repositories;

import com.donatoordep.debver.entities.AnimeOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeOrderDetailsRepository extends JpaRepository<AnimeOrderDetails, Long> {
}
