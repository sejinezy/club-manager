package com.sejin.clubmanager.member.service;

import com.sejin.clubmanager.crud.Converter;
import com.sejin.clubmanager.crud.CrudAbstractService;
import com.sejin.clubmanager.member.db.MemberEntity;
import com.sejin.clubmanager.member.db.MemberRepository;
import com.sejin.clubmanager.member.exception.DuplicateEmailException;
import com.sejin.clubmanager.member.model.MemberRequest;
import com.sejin.clubmanager.member.model.MemberResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService extends CrudAbstractService<MemberRequest, MemberResponse, MemberEntity> {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;


    @Override
    protected Converter<MemberRequest, MemberResponse, MemberEntity> converter() {
        return memberConverter;
    }

    @Override
    protected JpaRepository<MemberEntity, Long> jpaRepository() {
        return memberRepository;
    }

    @Transactional
    @Override
    public MemberResponse create(MemberRequest req) {
        if (memberRepository.existsByEmailAndDeletedFalse(req.getEmail())) {
            throw new DuplicateEmailException(req.getEmail());
        }

        return super.create(req);
    }

    @Transactional
    @Override
    public MemberResponse update(Long id, MemberRequest req){
        MemberEntity existing = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다. id=" + id));

        if (!existing.getEmail().equals(req.getEmail())
                && memberRepository.existsByEmailAndDeletedFalseAndIdNot(req.getEmail(),id)) {
            throw new DuplicateEmailException(req.getEmail());
        }

        return super.update(id, req);

    }


}
