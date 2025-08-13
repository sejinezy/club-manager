package com.sejin.clubmanager.club.controller;

import com.sejin.clubmanager.club.model.ClubResponse;
import com.sejin.clubmanager.club.service.ClubAdminService;
import com.sejin.clubmanager.common.Api;
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
@RequestMapping("/api/admin/clubs")
@RequiredArgsConstructor
public class ClubAdminController {

    private final ClubAdminService clubAdminService;

    @PostMapping("/{id}/restore")
    public void restore(@PathVariable Long id) {
        clubAdminService.restoreClub(id);
    }

    @GetMapping("/{id}")
    public Api<ClubResponse> getIncludingDeleted(@PathVariable Long id) {
        ClubResponse clubIncludingDeleted = clubAdminService.getClubIncludingDeleted(id);

        return Api.<ClubResponse>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.name())
                .body(clubIncludingDeleted)
                .build();
    }

    @GetMapping
    public Api<List<ClubResponse>> listIncludingDeleted(Pageable pageable) {
        Api<List<ClubResponse>> listApi = clubAdminService.listClubIncludingDeleted(pageable);
        listApi.setResultCode(String.valueOf(HttpStatus.OK.value()));
        listApi.setResultMessage(HttpStatus.OK.name());

        return listApi;
    }
}
