package com.migros.repositories;

import com.migros.entities.Location;
import com.migros.entities.Stores;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class StoresRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * @return all stores
     */
    public List<Stores> getAllStores() {
        return jdbcTemplate.query("SELECT * FROM STORES", new MapSqlParameterSource(), (resultSet, i) -> Stores.builder()
                .id(resultSet.getLong("ID"))
                .name(resultSet.getString("NAME"))
                .location(Location.builder()
                        .latitude(resultSet.getDouble("LATITUDE"))
                        .longitude(resultSet.getDouble("LONGITUDE"))
                        .build())
                .build());

    }
}
