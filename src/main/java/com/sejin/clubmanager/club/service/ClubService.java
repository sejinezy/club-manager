package com.sejin.clubmanager.club.service;

import com.sejin.clubmanager.club.db.ClubEntity;
import com.sejin.clubmanager.club.db.ClubRepository;
import com.sejin.clubmanager.club.model.ClubRequest;
import com.sejin.clubmanager.club.model.ClubResponse;
import com.sejin.clubmanager.crud.Converter;
import com.sejin.clubmanager.crud.CrudAbstractService;
import com.sejin.clubmanager.member.db.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubService extends CrudAbstractService<ClubRequest, ClubResponse, ClubEntity> {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubConverter clubConverter;

    @Override
    protected Converter<ClubRequest, ClubResponse, ClubEntity> converter() {
        return clubConverter;
    }

    @Override
    protected JpaRepository<ClubEntity, Long> jpaRepository() {
        return clubRepository;
    }

    @Override
    protected void preDelete(Long id) {

        if (!clubRepository.existsByIdAndDeletedFalse(id)) {
            throw new EntityNotFoundException("이미 삭제되었거나 존재하지 않는 클럽입니다.");

        }

        if (memberRepository.existsByClub_IdAndDeletedFalse(id)) {
                throw new IllegalStateException("해당 동아리에 회원이 있어 삭제할 수 없습니다.");
            }
        }

    }


