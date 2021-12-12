package com.migros.services;

import com.migros.entities.Couriers;
import com.migros.entities.MovementLogs;
import com.migros.entities.Stores;
import com.migros.repositories.MovementLogsRepository;
import com.migros.utils.DistanceUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Service
@AllArgsConstructor
public class MovementLogsService {

    private DistanceUtility distanceUtility;
    private MovementLogsRepository movementLogsRepository;
    private CouriersService couriersService;

    public String addLog(MovementLogs info, ResourceBundle bundle) {
        String processResult;
        Couriers courier = couriersService.getCourierById(info.getCourierId());
        Stores enteredStore = distanceUtility.givenCoordinatesIsEntrance(info.getLocation());
        if (Objects.nonNull(enteredStore) && !movementLogsRepository.courierHasEnteredStoreArea(enteredStore.getId(), info.getLogDate(), info.getCourierId())) {
            info.setEnteredStoreId(enteredStore.getId());
            info.setIsEntrance((short) (Objects.isNull(enteredStore) ? 0 : 1));
            processResult = MessageFormat.format(bundle.getString("Log_inserted_with_entrance"), courier.getName(), courier.getId(), enteredStore.getName());
        } else {
            info.setEnteredStoreId(null);
            info.setIsEntrance((short) 0);
            processResult = MessageFormat.format(bundle.getString("Log_inserted_successfully"), courier.getName(), courier.getId());
        }
        movementLogsRepository.insertMovement(info);
        return processResult;

    }

    public String getTravelledDistanceOfCourier(Long courierId, ResourceBundle bundle) {
        Couriers courier = couriersService.getCourierById(courierId);
        List<MovementLogs> logs = movementLogsRepository.getLogsOfCourierById(courierId);
        return MessageFormat.format(bundle.getString("Travelled_distance_of_courier"), courier.getName(),
                courier.getId(), distanceUtility.iterateListForTotalDistance(logs).toString());
    }
}
