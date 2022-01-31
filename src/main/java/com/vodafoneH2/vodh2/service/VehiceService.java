package com.vodafoneH2.vodh2.service;

import com.vodafoneH2.vodh2.model.ParkModel;

import java.util.List;

public interface VehiceService {

    String parkVehice(ParkModel parkModel);

    void leaveById(Integer id);

    List<String> getStatus();
}
