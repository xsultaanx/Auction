package org.auction.common.service;

import java.util.UUID;

public interface UserCommandService<T> {
    default T getById(UUID id) {
        return null;
    }

}
