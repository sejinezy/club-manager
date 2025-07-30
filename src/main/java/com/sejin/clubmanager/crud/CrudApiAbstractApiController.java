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

public class CrudApiAbstractApiController<DTO, ENTITY> implements CrudInterface<DTO> {

    @Autowired(required = false)
    private CrudAbstractService<DTO, ENTITY> crudAbstractService;


    @PostMapping
    @Override
    public DTO create(
            @Valid @RequestBody DTO dto) {
        return crudAbstractService.create(dto);
    }

    @GetMapping("/id/{id}")
    @Override
    public Optional<DTO> read(
            @PathVariable Long id) {
        return crudAbstractService.read(id);
    }

    @PutMapping
    @Override
    public DTO update(
            @Valid @RequestBody DTO dto) {
        return crudAbstractService.update(dto);
    }

    @DeleteMapping("/id/{id}")
    @Override
    public void delete(
            @PathVariable Long id) {
        crudAbstractService.delete(id);
    }

    @GetMapping("/all")
    @Override
    public Api<List<DTO>> list(Pageable pageable) {
        return crudAbstractService.list(pageable);
    }
}
