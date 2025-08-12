package com.sejin.clubmanager.member.controller;

import com.sejin.clubmanager.crud.CrudApiAbstractApiController;
import com.sejin.clubmanager.member.db.MemberEntity;
import com.sejin.clubmanager.member.model.MemberRequest;
import com.sejin.clubmanager.member.model.MemberResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberApiController extends CrudApiAbstractApiController<MemberRequest, MemberResponse, MemberEntity> {
}
