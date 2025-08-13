package com.sejin.clubmanager.crud;

import com.sejin.clubmanager.common.Api;
import com.sejin.clubmanager.common.Pagination;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class CrudApiAbstractApiController<REQ,RES extends Identifiable<Long>, ENTITY> implements CrudInterface<REQ,RES> {

    @Autowired(required = false)
    private CrudServicePort<REQ,RES> crudAbstractService;


    @PostMapping
    @Override
    public Api<RES> create(
            @Valid @RequestBody REQ req) {
        RES res = crudAbstractService.create(req);
        return Api.<RES>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.name())
                .body(res)
                .build();
    }

    @GetMapping("/id/{id}")
    @Override
    public Api<Optional<RES>> read(
            @PathVariable Long id) {
        Optional<RES> res = crudAbstractService.read(id);

        return Api.<Optional<RES>>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.name())
                .body(res)
                .build();

    }

    @PutMapping("/id/{id}")
    @Override
    public Api<RES> update(
            @PathVariable Long id, @Valid @RequestBody REQ req) {
        RES res = crudAbstractService.update(id, req);
        return Api.<RES>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.name())
                .body(res)
                .build();

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

        Page<RES> page = crudAbstractService.list(pageable);

        Pagination pagination = Pagination.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .currentElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();

        return Api.<List<RES>>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.name())
                .body(page.getContent())
                .pagination(pagination)
                .build();

    }
}
