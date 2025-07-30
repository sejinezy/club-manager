package com.sejin.clubmanager.crud;

public interface Converter<DTO, ENTITY>{

    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);

    void updateEntityFromDto(DTO dto, ENTITY entity);
}
