package com.sejin.clubmanager.crud;

import com.sejin.clubmanager.common.Api;
import com.sejin.clubmanager.common.Pagination;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class CrudAbstractService<REQ,RES extends Identifiable<Long>, ENTITY> implements CrudInterface<REQ,RES> {

    @Autowired(required = false)
    private Converter<REQ, RES, ENTITY> converter;

    @Autowired
    private JpaRepository<ENTITY, Long> jpaRepository;

    @Override
    public RES create(REQ req) {
        ENTITY entity = converter.toEntity(req);
        jpaRepository.save(entity);
        return converter.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RES> read(Long id) {
        return jpaRepository.findById(id)
                .map(converter::toResponse);
    }

    @Override
    public RES update(Long id, REQ req) {
        ENTITY entity = jpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 데이터가 없습니다."));

        converter.updateEntityFromRequest(req, entity);

        jpaRepository.flush();

        return converter.toResponse(entity);

    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Api<List<RES>> list(Pageable pageable) {
        Page<ENTITY> page = jpaRepository.findAll(pageable);

        List<RES> body = page.stream()
                .map(converter::toResponse)
                .toList();

        Pagination pagination = Pagination.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .currentElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();

        return Api.<List<RES>>builder()
                .body(body)
                .pagination(pagination)
                .build();
    }
}
