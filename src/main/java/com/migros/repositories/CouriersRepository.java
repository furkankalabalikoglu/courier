package com.migros.repositories;

import com.migros.entities.Couriers;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CouriersRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * @param id courier ID
     * @return information about courier who has given ID
     */
    public Couriers getCourierById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.queryForObject("SELECT * FROM COURIERS WHERE ID=:id", params, (resultSet, i) -> Couriers.builder()
                .id(resultSet.getLong("ID"))
                .name(resultSet.getString("NAME"))
                .build());
    }
}
