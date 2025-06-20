package org.auction.common.service;

public interface CommandService<T> {

    void create(T object);

}