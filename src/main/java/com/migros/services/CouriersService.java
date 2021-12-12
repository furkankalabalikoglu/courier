package com.migros.services;

import com.migros.entities.Couriers;
import com.migros.repositories.CouriersRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CouriersService {

    private CouriersRepository couriersRepository;

    /**
     * Used 24 hour cache for this method.
     *
     * @param id courier ID
     * @return information about courier who has given ID
     */
    @Cacheable(value = "CourierById", cacheManager = "Guava24HourCacheManager")
    public Couriers getCourierById(Long id) {
        return couriersRepository.getCourierById(id);
    }
}
