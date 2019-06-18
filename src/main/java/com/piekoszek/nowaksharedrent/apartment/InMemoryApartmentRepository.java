package com.piekoszek.nowaksharedrent.apartment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryApartmentRepository implements ApartmentRepository {

    private Map<String, Apartment> map = new ConcurrentHashMap<>();

    @Override
    public void save(Apartment apartment) {
        map.put(apartment.getId(), apartment);
    }

    @Override
    public Apartment findById(String id) {
        return map.get(id);
    }
}
