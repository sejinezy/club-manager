package com.sejin.clubmanager.club.service;

import com.sejin.clubmanager.club.db.ClubEntity;
import com.sejin.clubmanager.club.model.ClubRequest;
import com.sejin.clubmanager.club.model.ClubResponse;
import com.sejin.clubmanager.crud.CrudAbstractService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClubService extends CrudAbstractService<ClubRequest, ClubResponse, ClubEntity> {


}
