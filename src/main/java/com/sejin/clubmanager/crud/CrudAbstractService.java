package com.sejin.clubmanager.crud;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public abstract class CrudAbstractService<REQ,RES extends Identifiable<Long>, ENTITY> implements CrudServicePort<REQ,RES> {


    protected abstract Converter<REQ, RES, ENTITY> converter();
    protected abstract JpaRepository<ENTITY, Long> jpaRepository();

    @Transactional
    @Override
    public RES create(REQ req) {
        ENTITY entity = converter().toEntity(req);
        jpaRepository().save(entity);
        return converter().toResponse(entity);
    }

    @Override
    public RES read(Long id) {
        return jpaRepository().findById(id)
                .map(converter()::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 데이터가 없습니다. ID=" + id));
    }

    @Transactional
    @Override
    public RES update(Long id, REQ req) {
        ENTITY entity = jpaRepository().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 데이터가 없습니다."));

        converter().updateEntityFromRequest(req, entity);

        jpaRepository().flush();

        return converter().toResponse(entity);

    }

    @Transactional
    @Override
    public void delete(Long id) {
        preDelete(id);
        jpaRepository().deleteById(id);
        postDelete(id);
    }

    @Override
    public Page<RES> list(Pageable pageable) {

        return jpaRepository().findAll(pageable)
                .map(converter()::toResponse);
    }

    protected void preDelete(Long id) {}

    protected void postDelete(Long id){}

}
