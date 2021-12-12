package com.migros.services;

import com.migros.entities.Stores;
import com.migros.repositories.StoresRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StoresService {
    private StoresRepository storesRepository;

    /**
     * Used 24 hour cache for this method.
     *
     * @return all stores
     */
    @Cacheable(value = "AllStores", cacheManager = "Guava24HourCacheManager")
    public List<Stores> getAllStores() {
        return storesRepository.getAllStores();
    }
}
