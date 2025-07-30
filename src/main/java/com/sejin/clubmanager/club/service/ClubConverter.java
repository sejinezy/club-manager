package com.sejin.clubmanager.club.service;

import com.sejin.clubmanager.club.db.ClubEntity;
import com.sejin.clubmanager.club.model.ClubDto;
import com.sejin.clubmanager.crud.Converter;
import com.sejin.clubmanager.member.db.MemberEntity;
import com.sejin.clubmanager.member.model.MemberDto;
import com.sejin.clubmanager.member.service.MemberConverter;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClubConverter implements Converter<ClubDto, ClubEntity> {

    private final MemberConverter memberConverter;

    @Override
    public ClubDto toDto(ClubEntity clubEntity) {

        List<MemberDto> memberDtos = clubEntity.getMemberEntities().stream()
                .map(memberConverter::toDto).toList();

        return ClubDto.builder()
                .id(clubEntity.getId())
                .name(clubEntity.getName())
                .description(clubEntity.getDescription())
                .createdAt(clubEntity.getCreatedAt())
                .updatedAt(clubEntity.getUpdatedAt())
                .memberEntities(memberDtos)
                .build();


    }

    @Override
    public ClubEntity toEntity(ClubDto clubDto) {

        List<MemberEntity> memberEntities = clubDto.getMemberEntities().stream()
                .map(memberConverter::toEntity).toList();

        return ClubEntity.builder()
                .id(clubDto.getId())
                .name(clubDto.getName())
                .description(clubDto.getDescription())
                .memberEntities(memberEntities)
                .build();
    }

    @Override
    public void updateEntityFromDto(ClubDto clubDto, ClubEntity clubEntity) {
        List<MemberEntity> memberEntities = clubDto.getMemberEntities().stream()
                .map(memberConverter::toEntity).toList();

        clubEntity.setName(clubDto.getName());
        clubEntity.setDescription(clubDto.getDescription());
        clubEntity.setMemberEntities(memberEntities);


    }
}
