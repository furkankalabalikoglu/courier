package com.migros.utils;

import com.migros.entities.Location;
import com.migros.entities.MovementLogs;
import com.migros.entities.Stores;
import com.migros.services.StoresService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class DistanceUtility {

    private StoresService storesService;

    /**
     * This method has used for calculating distance between two points
     *
     * @return distance (km)
     */
    public double getDistance(Location location1, Location location2) {
        double theta = location1.getLongitude() - location2.getLongitude();
        double dist = Math.sin(deg2rad(location1.getLatitude())) * Math.sin(deg2rad(location2.getLatitude())) +
                Math.cos(deg2rad(location1.getLatitude())) * Math.cos(deg2rad(location2.getLatitude())) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        dist = (Objects.equals(dist, Double.NaN) ? 0 : dist);
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    /**
     * This method iterates all stores and compare courier location with store location
     *
     * @param courierLocation location information of courier
     * @return store entity if his/her location is near of store
     */
    public Stores givenCoordinatesIsEntrance(Location courierLocation) {
        List<Stores> stores = storesService.getAllStores();
        for (Stores store : stores) {
            if (isInside(store.getLocation(), courierLocation)) {
                return store;
            }
        }
        return null;
    }

    /**
     * @param storeLocation   latitude and longitude of store
     * @param courierLocation latitude and longitude of courier
     * @return true if courier is on the area of store otherwise false
     */
    public boolean isInside(Location storeLocation, Location courierLocation) {
        if ((courierLocation.getLatitude() - storeLocation.getLatitude()) * (courierLocation.getLatitude() - storeLocation.getLatitude()) +
                (courierLocation.getLongitude() - storeLocation.getLongitude()) * (courierLocation.getLongitude() - storeLocation.getLongitude()) <= 0.1 * 0.1)
            return true;
        else
            return false;
    }

    /**
     * @param logs set of courier's movements
     * @return total travelled distance of courier in kilometer(s)
     */
    public Double iterateListForTotalDistance(List<MovementLogs> logs) {
        Double totalDistance = 0.0d;
        if (logs.size() < 2) {
            return 0.0d;
        }
        for (int i = 0; i < logs.size() - 1; i++) {
            totalDistance += getDistance(logs.get(i).getLocation(), logs.get(i + 1).getLocation());
        }
        return totalDistance;
    }
}
