package com.sejin.clubmanager.crud;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudServicePort<REQ, RES>{

    RES create(REQ req);

    RES read(Long id);

    RES update(Long id, REQ req);

    void delete(Long id);

    Page<RES> list(Pageable pageable);

}
