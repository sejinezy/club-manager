package com.sejin.clubmanager.member.controller;

import com.sejin.clubmanager.common.Api;
import com.sejin.clubmanager.member.model.MemberResponse;
import com.sejin.clubmanager.member.service.MemberAdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/members")
@RequiredArgsConstructor
public class MemberAdminController {

    private final MemberAdminService memberAdminService;

    @PostMapping("/{id}/restore")
    public void restore(@PathVariable Long id) {
        memberAdminService.restoreMember(id);
    }

    @GetMapping("/{id}")
    public Api<MemberResponse> getIncludingDeleted(@PathVariable Long id) {
        MemberResponse memberIncludingDeleted = memberAdminService.getMemberIncludingDeleted(id);

        return Api.<MemberResponse>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.name())
                .body(memberIncludingDeleted)
                .build();
    }

    @GetMapping
    public Api<List<MemberResponse>> listIncludingDeleted(Pageable pageable) {
        Api<List<MemberResponse>> listApi = memberAdminService.listMembersIncludingDeleted(pageable);
        listApi.setResultCode(String.valueOf(HttpStatus.OK.value()));
        listApi.setResultMessage(HttpStatus.OK.name());

        return listApi;
    }
}
