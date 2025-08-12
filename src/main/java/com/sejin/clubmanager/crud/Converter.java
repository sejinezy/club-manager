package com.sejin.clubmanager.crud;

public interface Converter<REQ, RES, ENTITY>{

    ENTITY toEntity(REQ req);

    void updateEntityFromRequest(REQ req, ENTITY entity);

    RES toResponse(ENTITY entity);

}
