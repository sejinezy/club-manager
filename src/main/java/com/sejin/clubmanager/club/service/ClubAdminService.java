package com.sejin.clubmanager.club.service;

import com.sejin.clubmanager.club.db.ClubEntity;
import com.sejin.clubmanager.club.db.ClubRepository;
import com.sejin.clubmanager.club.model.ClubResponse;
import com.sejin.clubmanager.common.Api;
import com.sejin.clubmanager.common.Pagination;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubAdminService {

    private final ClubRepository clubRepository;
    private final ClubConverter clubConverter;

    @Transactional
    public void restoreClub(Long id) {
        int updated = clubRepository.restore(id);
        if (updated == 0) {
            throw new EntityNotFoundException("삭제 상태의 동아리만 복구할 수 있습니다.");
        }
    }

    public ClubResponse getClubIncludingDeleted(Long id) {
        ClubEntity e = clubRepository.findIncludingDeleted(id)
                .orElseThrow(() -> new EntityNotFoundException("클럽을 찾을 수 없습니다."));
        return clubConverter.toResponse(e);
    }

    public Api<List<ClubResponse>> listClubIncludingDeleted(Pageable pageable) {
        Page<ClubEntity> page = clubRepository.findAllIncludingDeleted(pageable);

        List<ClubResponse> body = page.stream()
                .map(clubConverter::toResponse)
                .toList();

        Pagination p = Pagination.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .currentElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();

        return Api.<List<ClubResponse>>builder()
                .body(body)
                .pagination(p)
                .build();
    }
}
