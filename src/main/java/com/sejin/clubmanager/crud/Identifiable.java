package com.sejin.clubmanager.crud;

public interface Identifiable<ID extends Long> {
    ID getId();
}
