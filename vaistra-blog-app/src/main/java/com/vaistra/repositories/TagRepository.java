package com.vaistra.repositories;

import com.vaistra.entities.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findByTagName(String name);

    @Query(value = "SELECT * from tag t WHERE t.is_deleted = 0 AND t.tag_id=:id", nativeQuery = true)
    Tag findByIdNotTrashed(Integer id);

    @Query(value = "SELECT * from tag t WHERE t.is_deleted = 1 AND t.tag_id=:id", nativeQuery = true)
    Tag findByIdTrashed(Integer id);

    @Query(value = "SELECT * from tag t WHERE t.is_active = 0 AND t.is_deleted = 0 AND t.tag_id=:id", nativeQuery = true)
    Tag findByIdInActive(Integer id);

    @Query(value = "SELECT * from tag t WHERE t.is_deleted = 1", nativeQuery = true)
    List<Tag> findAllTrashed();

    @Query(value = "SELECT * from tag t WHERE t.is_active = 0 AND t.is_deleted = 0", nativeQuery = true)
    List<Tag> findAllInActive();

    @Override
    @Query(value = "SELECT * from tag t WHERE t.is_deleted = 0", nativeQuery = true)
    List<Tag> findAll();

    @Override
    @Query(value = "SELECT * from tag t WHERE t.is_deleted = 0", nativeQuery = true)
    Page<Tag> findAll(Pageable pageable);

    Boolean existsByTagName(String name);

}
