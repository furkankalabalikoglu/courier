package com.migros.repositories;

import com.migros.entities.Location;
import com.migros.entities.MovementLogs;
import com.migros.utils.LoggerSupport;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class MovementLogsRepository implements LoggerSupport {
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * @param info movement information
     */
    public void insertMovement(MovementLogs info) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("courierId", info.getCourierId());
        params.addValue("latitude", info.getLocation().getLatitude());
        params.addValue("longitude", info.getLocation().getLongitude());
        params.addValue("logDate", info.getLogDate());
        params.addValue("isEntrance", info.getIsEntrance());
        params.addValue("storeId", info.getEnteredStoreId());
        jdbcTemplate.update("INSERT INTO MOVEMENTLOGS(COURIERID,LATITUDE,LONGITUDE,LOGDATE,ISENTRANCE,ENTEREDSTOREID) " +
                "VALUES (:courierId,:latitude,:longitude,:logDate,:isEntrance,:storeId)", params);
    }

    /**
     * @param courierId courier ID
     * @return movement set of courier who has given ID
     */
    public List<MovementLogs> getLogsOfCourierById(Long courierId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("courierId", courierId);
        return jdbcTemplate.query("SELECT * FROM MOVEMENTLOGS WHERE COURIERID=:courierId", params, (resultSet, rowNum) -> MovementLogs.builder()
                .id(resultSet.getLong("ID"))
                .courierId(resultSet.getLong("COURIERID"))
                .logDate(resultSet.getString("LOGDATE"))
                .isEntrance(resultSet.getShort("ISENTRANCE"))
                .enteredStoreId(resultSet.getLong("ENTEREDSTOREID"))
                .location(Location.builder()
                        .latitude(resultSet.getDouble("LATITUDE"))
                        .longitude(resultSet.getDouble("LONGITUDE"))
                        .build())
                .build());
    }

    /**
     * @param enteredStoreId store ID
     * @param logDate        date time
     * @param courierId      courier ID
     * @return true if given courier enters given store on last 1 minute, otherwise returns false
     */
    public boolean courierHasEnteredStoreArea(Long enteredStoreId, String logDate, Long courierId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("courierId", courierId);
        params.addValue("logDate", logDate);
        params.addValue("enteredStoreId", enteredStoreId);
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM MOVEMENTLOGS WHERE ISENTRANCE='1' AND " +
                "ENTEREDSTOREID=:enteredStoreId AND LOGDATE> DATEADD(MINUTE,-1,:logDate) AND COURIERID=:courierId", params, Integer.class) > 0;
    }
}
