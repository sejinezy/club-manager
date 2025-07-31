package com.sejin.clubmanager.member.service;

import com.sejin.clubmanager.club.db.ClubEntity;
import com.sejin.clubmanager.club.db.ClubRepository;
import com.sejin.clubmanager.crud.Converter;
import com.sejin.clubmanager.member.db.MemberEntity;
import com.sejin.clubmanager.member.model.MemberDto;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberConverter implements Converter<MemberDto, MemberEntity> {

    private final ClubRepository clubRepository;

    @Override
    public MemberDto toDto(MemberEntity memberEntity) {
        return MemberDto.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .email(memberEntity.getEmail())
                .clubId(memberEntity.getClub().getId())
                .createdAt(memberEntity.getCreatedAt())
                .updatedAt(memberEntity.getUpdatedAt())
                .build();

    }

    @Override
    public MemberEntity toEntity(MemberDto memberDto) {

        Optional<ClubEntity> clubEntity = clubRepository.findById(memberDto.getClubId());

        return MemberEntity.builder()
                .id(memberDto.getId())
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .club(clubEntity.orElse(null)) // null일 경우 예외 발생
                .build();
    }

    @Override
    public void updateEntityFromDto(MemberDto memberDto, MemberEntity memberEntity) {

        Optional<ClubEntity> clubEntity = clubRepository.findById(memberDto.getClubId());

        memberEntity.setName(memberDto.getName());
        memberEntity.setEmail(memberDto.getEmail());
        memberEntity.setClub(clubEntity.orElse(null));

    }
}
