package com.donatoordep.debver.repositories;

import com.donatoordep.debver.entities.AccountStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStatsRepository extends JpaRepository<AccountStats, Long> {
}
