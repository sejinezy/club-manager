package com.sejin.clubmanager.crud;

import com.sejin.clubmanager.common.Api;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CrudInterface<REQ,RES> {

    Api<RES> create(REQ dto);

    Api<RES> read(Long id);

    Api<RES> update(Long id, REQ req);

    void delete(Long id);

    Api<List<RES>> list(Pageable pageable);
}
