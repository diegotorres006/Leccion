package com.company.vehicles.controllers;

import com.company.vehicles.dto.OperationResponseDto;
import com.company.vehicles.dto.VehicleResponseDto;
import com.company.vehicles.dto.VehicleStockRequestDto;
import com.company.vehicles.services.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponseDto>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllActiveVehicles());
    }

    @GetMapping("/low-stock-expensive")
    public ResponseEntity<List<VehicleResponseDto>> getLowStockExpensive() {
        return ResponseEntity.ok(vehicleService.getLowStockExpensiveVehicles());
    }

    @PatchMapping("/delete/{model}")
    public ResponseEntity<OperationResponseDto> deleteByModel(@PathVariable String model) {
        return ResponseEntity.ok(vehicleService.deleteByModel(model));
    }

    @PatchMapping("/stock")
    public ResponseEntity<VehicleResponseDto> updateStock(@Valid @RequestBody VehicleStockRequestDto request) {
        return ResponseEntity.ok(vehicleService.updateStock(request));
    }
}