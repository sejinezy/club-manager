package com.sejin.clubmanager.member.db;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    @Query(value = "SELECT * FROM member WHERE id = :id", nativeQuery = true)
    Optional<MemberEntity> findIncludingDeleted(@Param("id") Long id);

    @Query(
            value = "SELECT * FROM member ORDER BY id DESC",
            countQuery = "SELECT count(*) FROM member",
            nativeQuery = true
    )
    Page<MemberEntity> findAllIncludingDeleted(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE member SET is_deleted = 0 WHERE id= :id AND is_deleted = 1",
            nativeQuery = true)
    int restore(@Param("id") Long id);

    boolean existsByClub_IdAndDeletedFalse(Long clubId);

}
