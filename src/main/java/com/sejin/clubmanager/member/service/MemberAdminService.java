package com.sejin.clubmanager.member.service;

import com.sejin.clubmanager.common.Api;
import com.sejin.clubmanager.common.Pagination;
import com.sejin.clubmanager.member.db.MemberEntity;
import com.sejin.clubmanager.member.db.MemberRepository;
import com.sejin.clubmanager.member.model.MemberResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberAdminService {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;

    @Transactional
    public void restoreMember(Long id) {
        int updated = memberRepository.restore(id);
        if (updated == 0) {
            throw new EntityNotFoundException("삭제 상태의 회원만 복구할 수 있습니다.");
        }
    }

    public MemberResponse getMemberIncludingDeleted(Long id) {
        MemberEntity e = memberRepository.findIncludingDeleted(id)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        return memberConverter.toResponse(e);
    }

    public Api<List<MemberResponse>> listMembersIncludingDeleted(Pageable pageable) {
        Page<MemberEntity> page = memberRepository.findAllIncludingDeleted(pageable);

        List<MemberResponse> body = page.stream()
                .map(memberConverter::toResponse)
                .toList();

        Pagination p = Pagination.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .currentElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();

        return Api.<List<MemberResponse>>builder()
                .body(body)
                .pagination(p)
                .build();
    }
}
