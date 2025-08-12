package com.sejin.clubmanager.crud;

import com.sejin.clubmanager.common.Api;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface CrudInterface<REQ,RES> {

    RES create(REQ dto);

    Optional<RES> read(Long id);

    RES update(Long id, REQ req);

    void delete(Long id);

    Api<List<RES>> list(Pageable pageable);
}
