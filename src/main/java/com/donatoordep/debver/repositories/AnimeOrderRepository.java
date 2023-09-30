package com.donatoordep.debver.repositories;

import com.donatoordep.debver.entities.AnimeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeOrderRepository extends JpaRepository<AnimeOrder, Long> {
}
