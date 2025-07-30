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

public abstract class CrudAbstractService<DTO extends Identifiable<Long>, ENTITY> implements CrudInterface<DTO> {

    @Autowired(required = false)
    private Converter<DTO, ENTITY> converter;

    @Autowired
    private JpaRepository<ENTITY, Long> jpaRepository;

    @Override
    public DTO create(DTO dto) {
        ENTITY entity = converter.toEntity(dto);
        jpaRepository.save(entity);
        return converter.toDto(entity);
    }

    @Override
    public Optional<DTO> read(Long id) {
        Optional<ENTITY> optionalEntity = jpaRepository.findById(id);

        DTO dto = optionalEntity.map(
                it -> converter.toDto(it)
        ).orElse(null);

        return Optional.ofNullable(dto);
    }

    @Transactional
    @Override
    public DTO update(DTO dto) {
        ENTITY entity = jpaRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 데이터가 없습니다."));

        converter.updateEntityFromDto(dto, entity);

        jpaRepository.flush();

        return converter.toDto(entity);

    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);

    }

    @Override
    public Api<List<DTO>> list(Pageable pageable) {
        Page<ENTITY> list = jpaRepository.findAll(pageable);

        Pagination pagination = Pagination.builder()
                .page(list.getNumber())
                .size(list.getSize())
                .currentElements(list.getNumberOfElements())
                .totalElements(list.getTotalElements())
                .totalPage(list.getTotalPages())
                .build();

        List<DTO> dtoList = list.stream()
                .map(it -> {
                    return converter.toDto(it);
                })
                .toList();

        Api<List<DTO>> response = Api.<List<DTO>>builder()
                .body(dtoList)
                .pagination(pagination)
                .build();

        return response;
    }
}
