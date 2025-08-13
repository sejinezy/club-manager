package com.sejin.clubmanager.club.db;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends JpaRepository<ClubEntity, Long> {

    @Query(value = "SELECT * FROM club WHERE id = :id", nativeQuery = true)
    Optional<ClubEntity> findIncludingDeleted(@Param("id") Long id);

    @Query(
            value = "SELECT * FROM club ORDER BY id DESC",
            countQuery = "SELECT count(*) FROM club",
            nativeQuery = true
    )
    Page<ClubEntity> findAllIncludingDeleted(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE club SET is_deleted = 0 WHERE id = :id AND is_deleted = 1", nativeQuery = true)
    int restore(@Param("id") Long id);
}
