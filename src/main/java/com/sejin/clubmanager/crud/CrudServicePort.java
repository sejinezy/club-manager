package com.sejin.clubmanager.crud;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudServicePort<REQ, RES>{

    RES create(REQ req);

    Optional<RES> read(Long id);

    RES update(Long id, REQ req);

    void delete(Long id);

    Page<RES> list(Pageable pageable);

}
