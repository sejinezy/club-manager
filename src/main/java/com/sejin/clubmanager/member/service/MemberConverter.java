package com.sejin.clubmanager.member.service;

import com.sejin.clubmanager.club.db.ClubEntity;
import com.sejin.clubmanager.club.db.ClubRepository;
import com.sejin.clubmanager.crud.Converter;
import com.sejin.clubmanager.member.db.MemberEntity;
import com.sejin.clubmanager.member.model.MemberRequest;
import com.sejin.clubmanager.member.model.MemberResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberConverter implements Converter<MemberRequest, MemberResponse, MemberEntity> {

    private final ClubRepository clubRepository;


    @Override
    public MemberEntity toEntity(MemberRequest req) {
        ClubEntity club = clubRepository.findById(req.getClubId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 동아리입니다. id=" + req.getClubId()));

        return MemberEntity.builder()
                .name(req.getName())
                .email(req.getEmail())
                .club(club) // FK 매핑
                .build();

    }

    @Override
    public void updateEntityFromRequest(MemberRequest req, MemberEntity entity) {
        entity.setName(req.getName());
        entity.setEmail(req.getEmail());

        if (!entity.getClub().getId().equals(req.getClubId())) {
            ClubEntity club = clubRepository.findById(req.getClubId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 동아리입니다. id=" + req.getClubId()));
            entity.setClub(club);

        }

    }

    @Override
    public MemberResponse toResponse(MemberEntity entity) {
        return MemberResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .clubId(entity.getClub().getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
