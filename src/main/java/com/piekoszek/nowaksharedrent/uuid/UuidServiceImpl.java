package com.piekoszek.nowaksharedrent.uuid;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class UuidServiceImpl implements UuidService {

    @Override
    public String generateUuid() {
        return UUID.randomUUID().toString();
    }
}
