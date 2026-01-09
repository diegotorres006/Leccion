package com.company.vehicles.services;

import java.util.List;

import com.company.vehicles.dto.OperationResponseDto;
import com.company.vehicles.dto.VehicleResponseDto;
import com.company.vehicles.dto.VehicleStockRequestDto;

public interface VehicleService {
    List<VehicleResponseDto> getAllActiveVehicles();
    List<VehicleResponseDto> getLowStockExpensiveVehicles();
    OperationResponseDto deleteByModel(String model);
    VehicleResponseDto updateStock(VehicleStockRequestDto request);
}