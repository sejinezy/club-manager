package com.sejin.clubmanager.member.service;

import com.sejin.clubmanager.crud.CrudAbstractService;
import com.sejin.clubmanager.member.db.MemberEntity;
import com.sejin.clubmanager.member.model.MemberRequest;
import com.sejin.clubmanager.member.model.MemberResponse;
import org.springframework.stereotype.Service;

@Service
public class MemberService extends CrudAbstractService<MemberRequest, MemberResponse, MemberEntity> {
}
