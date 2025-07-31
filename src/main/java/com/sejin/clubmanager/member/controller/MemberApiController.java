package com.sejin.clubmanager.member.controller;

import com.sejin.clubmanager.crud.CrudApiAbstractApiController;
import com.sejin.clubmanager.member.db.MemberEntity;
import com.sejin.clubmanager.member.model.MemberDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberApiController extends CrudApiAbstractApiController<MemberDto, MemberEntity> {
}
