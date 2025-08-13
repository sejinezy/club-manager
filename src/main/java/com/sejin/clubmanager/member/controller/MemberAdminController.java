package com.sejin.clubmanager.member.controller;

import com.sejin.clubmanager.common.Api;
import com.sejin.clubmanager.member.model.MemberResponse;
import com.sejin.clubmanager.member.service.MemberAdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public MemberResponse getIncludingDeleted(@PathVariable Long id) {
        return memberAdminService.getMemberIncludingDeleted(id);
    }

    @GetMapping
    public Api<List<MemberResponse>> listIncludingDeleted(Pageable pageable) {
        return memberAdminService.listMembersIncludingDeleted(pageable);
    }
}
