package com.sejin.clubmanager.club.service;

import com.sejin.clubmanager.club.db.ClubEntity;
import com.sejin.clubmanager.club.model.ClubRequest;
import com.sejin.clubmanager.club.model.ClubResponse;
import com.sejin.clubmanager.crud.CrudAbstractService;
import com.sejin.clubmanager.member.db.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClubService extends CrudAbstractService<ClubRequest, ClubResponse, ClubEntity> {

    private final MemberRepository memberRepository;

    @Override
    protected void preDelete(Long id) {
        if (memberRepository.existsByClub_IdAndDeletedFalse(id)) {
            throw new IllegalStateException("해당 동아리에 회원이 있어 삭제할 수 없습니다.");
        }
    }

}
