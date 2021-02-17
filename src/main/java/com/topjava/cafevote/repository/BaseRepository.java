package com.topjava.cafevote.repository;

import com.topjava.cafevote.util.ValidationUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

// https://stackoverflow.com/questions/42781264/multiple-base-repositories-in-spring-data-jpa
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Integer> {
    default T existsById(int id) {
        return ValidationUtil.checkNotFoundWithIdOptional(findById(id), id);
    }
}
