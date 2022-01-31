package com.vodafoneH2.vodh2.controller;

import javax.validation.Valid;

import com.vodafoneH2.vodh2.model.LeaveModel;
import com.vodafoneH2.vodh2.model.ParkModel;
import com.vodafoneH2.vodh2.service.VehiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkController {

    @Autowired private VehiceService vehiceService;

    @PostMapping("/park-vehice")
    public String parkVehice(@Valid @RequestBody ParkModel park)
    {
        return vehiceService.parkVehice(park);
    }

    @DeleteMapping("/leave-vehice")
    public void leaveVehice(@Valid @RequestBody LeaveModel leave)
    {
        vehiceService.leaveById(leave.getId());
    }

    @GetMapping("/status")
    public List<String> leaveVehice()
    {
        return vehiceService.getStatus();
    }

}
