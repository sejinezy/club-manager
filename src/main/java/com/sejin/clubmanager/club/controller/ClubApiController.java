package com.sejin.clubmanager.club.controller;

import com.sejin.clubmanager.club.db.ClubEntity;
import com.sejin.clubmanager.club.model.ClubDto;
import com.sejin.clubmanager.crud.CrudApiAbstractApiController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/club")
public class ClubApiController extends CrudApiAbstractApiController<ClubDto, ClubEntity> {
}
