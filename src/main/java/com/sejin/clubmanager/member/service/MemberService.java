package com.sejin.clubmanager.member.service;

import com.sejin.clubmanager.crud.CrudAbstractService;
import com.sejin.clubmanager.member.db.MemberEntity;
import com.sejin.clubmanager.member.model.MemberDto;
import org.springframework.stereotype.Service;

@Service
public class MemberService extends CrudAbstractService<MemberDto, MemberEntity> {
}
