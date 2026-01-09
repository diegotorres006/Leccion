package com.company.vehicles.service;

import com.company.vehicles.dto.OperationResponseDto;
import com.company.vehicles.dto.VehicleResponseDto;
import com.company.vehicles.dto.VehicleStockRequestDto;
import com.company.vehicles.entity.Vehicle;
import com.company.vehicles.exception.ConflictException;
import com.company.vehicles.exception.ResourceNotFoundException;
import com.company.vehicles.mapper.VehicleMapper;
import com.company.vehicles.repository.VehicleRepository;
import com.company.vehicles.service.VehicleService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleResponseDto> getAllActiveVehicles() {
        return vehicleRepository.findByDeleted("N").stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleResponseDto> getLowStockExpensiveVehicles() {
        return vehicleRepository.findByDeletedAndPriceGreaterThanAndStockLessThan("N", 20000.0, 10).stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OperationResponseDto deleteByModel(String model) {
        Vehicle vehicle = vehicleRepository.findByModel(model)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado"));

        if ("S".equals(vehicle.getDeleted())) {
            throw new ConflictException("Vehículo ya eliminado");
        }

        vehicle.setDeleted("S");
        vehicleRepository.save(vehicle);

        return new OperationResponseDto("Vehículo eliminado exitosamente");
    }

    @Override
    @Transactional
    public VehicleResponseDto updateStock(VehicleStockRequestDto request) {
        Vehicle vehicle = vehicleRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado"));

        vehicle.setStock(request.getStock());
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.toDto(updatedVehicle);
    }
}