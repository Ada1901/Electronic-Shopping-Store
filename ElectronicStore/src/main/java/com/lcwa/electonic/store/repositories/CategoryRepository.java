package com.lcwa.electonic.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lcwa.electonic.store.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {

}
