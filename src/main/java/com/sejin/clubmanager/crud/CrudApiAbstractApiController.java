package com.sejin.clubmanager.crud;

import com.sejin.clubmanager.common.Api;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class CrudApiAbstractApiController<REQ,RES extends Identifiable<Long>, ENTITY> implements CrudInterface<REQ,RES> {

    @Autowired(required = false)
    private CrudAbstractService<REQ, RES, ENTITY> crudAbstractService;


    @PostMapping
    @Override
    public RES create(
            @Valid @RequestBody REQ req) {
        return crudAbstractService.create(req);
    }

    @GetMapping("/id/{id}")
    @Override
    public Optional<RES> read(
            @PathVariable Long id) {
        return crudAbstractService.read(id);
    }

    @PutMapping("/id/{id}")
    @Override
    public RES update(
            @PathVariable Long id, @Valid @RequestBody REQ req) {
        return crudAbstractService.update(id,req);
    }

    @DeleteMapping("/id/{id}")
    @Override
    public void delete(
            @PathVariable Long id) {
        crudAbstractService.delete(id);
    }

    @GetMapping("/all")
    @Override
    public Api<List<RES>> list(Pageable pageable) {
        return crudAbstractService.list(pageable);
    }
}
