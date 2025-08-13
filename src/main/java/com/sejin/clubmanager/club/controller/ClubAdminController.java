package com.sejin.clubmanager.club.controller;

import com.sejin.clubmanager.club.model.ClubResponse;
import com.sejin.clubmanager.club.service.ClubAdminService;
import com.sejin.clubmanager.common.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/clubs")
@RequiredArgsConstructor
public class ClubAdminController {

    private final ClubAdminService clubAdminService;

    @PostMapping("/{id}/restore")
    public void restore(@PathVariable Long id) {
        clubAdminService.restoreClub(id);
    }

    @GetMapping("/{id}")
    public ClubResponse getIncludingDeleted(@PathVariable Long id) {
        return clubAdminService.getClubIncludingDeleted(id);
    }

    @GetMapping
    public Api<List<ClubResponse>> listIncludingDeleted(Pageable pageable) {
        return clubAdminService.listClubIncludingDeleted(pageable);
    }
}
