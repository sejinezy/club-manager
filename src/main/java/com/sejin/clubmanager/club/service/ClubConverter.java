package com.sejin.clubmanager.club.service;

import com.sejin.clubmanager.club.db.ClubEntity;
import com.sejin.clubmanager.club.model.ClubRequest;
import com.sejin.clubmanager.club.model.ClubResponse;
import com.sejin.clubmanager.crud.Converter;
import com.sejin.clubmanager.member.model.MemberResponse;
import com.sejin.clubmanager.member.service.MemberConverter;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClubConverter implements Converter<ClubRequest, ClubResponse, ClubEntity> {

    private final MemberConverter memberConverter;

    @Override
    public ClubEntity toEntity(ClubRequest req) {
        return ClubEntity.builder()
                .name(req.getName())
                .description(req.getDescription())
                .build();
    }

    @Override
    public void updateEntityFromRequest(ClubRequest req, ClubEntity entity) {
        entity.setName(req.getName());
        entity.setDescription(req.getDescription());
    }

    @Override
    public ClubResponse toResponse(ClubEntity entity) {

        List<MemberResponse> members = entity.getMemberEntities().stream()
                .map(memberConverter::toResponse)
                .toList();

        return ClubResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .members(members)
                .build();
    }
}
