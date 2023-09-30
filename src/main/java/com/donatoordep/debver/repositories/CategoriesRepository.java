package com.donatoordep.debver.repositories;

import com.donatoordep.debver.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    @Query("SELECT u FROM Categories u WHERE UPPER(u.category) LIKE(UPPER(:title))")
    Categories findByName(String title);
}
