package com.migros.controllers;

import com.migros.entities.MovementLogs;
import com.migros.services.MovementLogsService;
import com.migros.utils.LoggerSupport;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractMap;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.migros.utils.Constants.Constant.LANG;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/movement")
public class MovementLogsController implements LoggerSupport {

    private MovementLogsService movementLogsService;

    /**
     * @param info movement information
     * @return processing result of given movement info
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AbstractMap.SimpleEntry> addMovementLog(@RequestBody MovementLogs info) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", new Locale(LANG));
        try {
            return ResponseEntity.ok().body(new AbstractMap.SimpleEntry<>("message", movementLogsService.addLog(info, bundle)));
        } catch (Exception e) {
            getLogger().error(e.getMessage());
            return ResponseEntity.internalServerError().body(new AbstractMap.SimpleEntry("error", bundle.getString("error_while_recording")));
        }
    }

    /**
     * @param id courierId
     * @return total travelled distance of courier who has given ID
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AbstractMap.SimpleEntry> getTotalDistanceOfCourier(@PathVariable("id") Long id) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", new Locale(LANG));
        try {
            return ResponseEntity.ok().body(new AbstractMap.SimpleEntry<>("message", movementLogsService.getTravelledDistanceOfCourier(id, bundle)));
        } catch (Exception e) {
            getLogger().error(e.getMessage());
            return ResponseEntity.internalServerError().body(new AbstractMap.SimpleEntry("error", bundle.getString("error_while_getting_total_dist")));
        }
    }
}
