package com.vodafoneH2.vodh2.service;

import com.vodafoneH2.vodh2.convertUtil.ConvertUtil;
import com.vodafoneH2.vodh2.entity.Vehice;
import com.vodafoneH2.vodh2.model.ParkModel;
import com.vodafoneH2.vodh2.repository.VehiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class VehiceServiceImpl implements VehiceService {

    @Autowired
    private VehiceRepository vehiceRepository;

    private static HashMap<Integer, String> parkSlotList = new HashMap<>();

    //-----------------PARK SERVICE START-----------------------
    @Override
    public String parkVehice(ParkModel parkModel) {
        setHashMapToDataList();
        int vehiceSlotResult = slotCount(parkModel.getType());
        Boolean avaliableResult = isAvaliableParkForVehice(vehiceSlotResult, parkSlotList);

        Integer parkResponse = 1;
        String returnMessage = "";
        if (avaliableResult) {
            List<Integer> availableSlots = getAvailableSlots(vehiceSlotResult, parkSlotList);
            parkResponse = setVehiceToAvailableSlots(parkModel, availableSlots);
            returnMessage = "Allocated " + parkResponse + " slot.";
        } else {
            returnMessage = "Garage is full.";
        }

        return returnMessage;
    }

    private void setHashMapToDataList() {
        List<Vehice> vehiceList = vehiceRepository.findAll();

        for (Vehice vehice : vehiceList) {
            String valueOfSlot = "park " + vehice.getPlate() + " " + vehice.getColor() + " " + vehice.getType();
            parkSlotList.put(Integer.valueOf(vehice.getSlot()), valueOfSlot);
        }
    }

    private Integer setVehiceToAvailableSlots(ParkModel model, List<Integer> availableSlots) {
        //dynamic set operation
        for (int i = 0; i < availableSlots.size(); i++) {
            Vehice vehice = new Vehice();
            vehice.setPark("park");
            vehice.setSlot(availableSlots.get(i).toString());
            vehice.setColor(model.getColor());
            vehice.setPlate(model.getPlate());
            vehice.setType(model.getType());
            vehiceRepository.save(vehice);
        }
        return availableSlots.size();
    }

    private int slotCount(String type) {
        //get slot count by vehice
        int slot = 0;
        if (type.equals("car")) {
            slot = 1;
            return slot;
        } else if (type.equals("jeep")) {
            slot = 2;
            return slot;
        } else if (type.equals("truck")) {
            slot = 4;
            return slot;
        }
        return slot;
    }

    private Boolean isAvaliableParkForVehice(int vehiceSlotResult, HashMap<Integer, String> parkSlotMap) {
        Boolean isAvailable = true;
        if (parkSlotMap.size() + vehiceSlotResult > 9) {
            isAvailable = false;
        }
        return isAvailable;
    }

    private List<Integer> getAvailableSlots(int vehiceSlotResult, HashMap<Integer, String> parkSlotList) {
        //avaliable slot list for vehice
        List<Integer> nextParkArea = new ArrayList<>();
        if (vehiceSlotResult == 1) {
            for (int i = 0; i < 10; i++) {
                String currentSlotResult = parkSlotList.get(i);
                if (currentSlotResult == null) {
                    nextParkArea.add(i);
                    break;
                }
            }

        } else if (vehiceSlotResult == 2) {
            for (int i = 0; i < 10; i++) {
                String currentSlotResult = parkSlotList.get(i);
                String nextSlotResult = parkSlotList.get(i + 1);
                if (currentSlotResult == null && nextSlotResult == null) {
                    nextParkArea.add(i);
                    nextParkArea.add(i + 1);
                    break;
                }
            }

        } else if (vehiceSlotResult == 4) {
            for (int i = 0; i < 10; i++) {
                String currentSlotResult = parkSlotList.get(i);
                String next1SlotResult = parkSlotList.get(i + 1);
                String next2SlotResult = parkSlotList.get(i + 1);
                String next3SlotResult = parkSlotList.get(i + 1);
                if (currentSlotResult == null && next1SlotResult == null && next2SlotResult == null && next3SlotResult == null) {
                    nextParkArea.add(i);
                    nextParkArea.add(i + 1);
                    nextParkArea.add(i + 2);
                    nextParkArea.add(i + 3);
                    break;
                }
            }
        }
        return nextParkArea;
    }

    //-----------------LEAVE SERVICE START-------------------------

    @Override
    public void leaveById(Integer id) {

        setHashMapToDataList();
        Integer exitCarSlot = Integer.valueOf(id);
        int parkSlotListItem = 0;
        int vehiceNumber = 1;

        for (int leaveNumber = 1; leaveNumber <= exitCarSlot; leaveNumber++) {
            String slotValue = parkSlotList.get(parkSlotListItem);

            if (slotValue == null) {
                for (int i = 0; i < 10; i++) {
                    parkSlotListItem++;
                    String checknext = parkSlotList.get(parkSlotListItem);
                    if (checknext != null) {
                        slotValue = checknext;
                        break;
                    }
                }
            }

            if (slotValue == null)
                break;

            List<String> splittedList = ConvertUtil.convertEnterenceSplit(slotValue);
            String vehiceType = splittedList.get(3);

            if (vehiceType.equals("car")) {
                if (vehiceNumber == exitCarSlot) {
                    parkSlotList.remove(parkSlotListItem);
                    vehiceRepository.deleteBySlot(String.valueOf(parkSlotListItem));
                    break;
                }
                parkSlotListItem++;
                vehiceNumber++;
            } else if (vehiceType.equals("jeep")) {
                if (vehiceNumber == exitCarSlot) {
                    for (int k = parkSlotListItem; k < parkSlotListItem + 2; k++) {
                        parkSlotList.remove(k);
                        vehiceRepository.deleteBySlot(String.valueOf(k));
                    }
                    break;
                }
                parkSlotListItem = parkSlotListItem + 2;
                vehiceNumber++;
            } else if (vehiceType.equals("truck")) {
                if (vehiceNumber == exitCarSlot) {
                    for (int k = parkSlotListItem; k < parkSlotListItem + 4; k++) {
                        parkSlotList.remove(k);
                        vehiceRepository.deleteBySlot(String.valueOf(k));
                    }
                    break;
                }
                parkSlotListItem = parkSlotListItem + 4;
                vehiceNumber++;
            }
        }
    }

    //--------------STATUS SERVICE START------------

    @Override
    public List<String> getStatus() {
        setHashMapToDataList();
        List<String> responseList = new ArrayList<>();
        for (int k = 0; k < 10; k++) {
            String slotValue = parkSlotList.get(k);
            if (slotValue != null) {
                List<String> statusResult = ConvertUtil.convertEnterenceSplit(slotValue);
                if (statusResult.get(3).equals("car")) {
                    int car1 = k + 1;
                    System.out.println(statusResult.get(1) + " " + statusResult.get(2) + " [" + car1 + "]");
                    responseList.add(statusResult.get(1) + " " + statusResult.get(2) + " [" + car1 + "]");
                } else if (statusResult.get(3).equals("jeep")) {
                    int jeep1 = k + 1;
                    int jeep2 = k + 2;
                    System.out.println(statusResult.get(1) + " " + statusResult.get(2) + " [" + jeep1 + "]" + " [" + jeep2 + "]");
                    responseList.add(statusResult.get(1) + " " + statusResult.get(2) + " [" + jeep1 + "]" + " [" + jeep2 + "]");
                    k = k + 1;
                } else if (statusResult.get(3).equals("truck")) {
                    int truck1 = k + 1;
                    int truck2 = k + 2;
                    int truck3 = k + 3;
                    int truck4 = k + 4;
                    System.out.println(statusResult.get(1) + " " + statusResult.get(2) + " [" + truck1 + "]" + " [" + truck2 + "]" + " [" + truck3 + "]" + " [" + truck4 + "]");
                    responseList.add(statusResult.get(1) + " " + statusResult.get(2) + " [" + truck1 + "]" + " [" + truck2 + "]" + " [" + truck3 + "]" + " [" + truck4 + "]");
                    k = k + 3;
                }
                if (k == 9) {
                    break;
                }
            }
        }
        return responseList;
    }

}

