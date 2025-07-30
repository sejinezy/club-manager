package com.sejin.clubmanager.crud;

import com.sejin.clubmanager.common.Api;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface CrudInterface<DTO> {

    DTO create(DTO dto);

    Optional<DTO> read(Long id);

    DTO update(DTO dto);

    void delete(Long id);

    Api<List<DTO>> list(Pageable pageable);
}
