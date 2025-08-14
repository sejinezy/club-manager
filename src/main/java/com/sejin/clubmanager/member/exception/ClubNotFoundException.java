package com.sejin.clubmanager.member.exception;

public class ClubNotFoundException extends RuntimeException {

    public ClubNotFoundException(Long id) {
        super("존재하지 않는 클럽입니다. (clubId = " + id + ")");
    }
}
