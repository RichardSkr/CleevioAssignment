package com.richardSkrivanek.cleevio.service.implementation;

import com.richardSkrivanek.cleevio.entity.InventoryItem;
import com.richardSkrivanek.cleevio.mapper.InventoryItemMapper;
import com.richardSkrivanek.cleevio.model.InventoryItemDto;
import com.richardSkrivanek.cleevio.repository.InventoryItemRepository;
import com.richardSkrivanek.cleevio.service.InventoryItemService;
import com.richardSkrivanek.cleevio.validation.Validation;
import exceptions.customExceptions.ProductNameAlreadyExistException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryItemServiceImpl implements InventoryItemService {

    @NonNull
    private final InventoryItemRepository inventoryItemRepository;

    @NonNull
    private final Validation inventoryItemValidation;

    @NonNull
    private final InventoryItemMapper mapper;


    @Override
    public InventoryItemDto saveItem(InventoryItemDto dto) {
        inventoryItemValidation.validateInventoryItem(dto);

        try {
            InventoryItem inventoryItem = InventoryItem.builder()
                    .dateOfCreation(LocalDate.now())
                    .description(dto.getDescription())
                    .name(dto.getName())
                    .price(dto.getPrice())
                    .build();
            InventoryItem newItem = inventoryItemRepository.save(inventoryItem);
            return mapper.toDto(newItem);

        } catch (ConstraintViolationException ex) {
            throw new ProductNameAlreadyExistException(dto.getName());
        }
    }

    @Override
    public List<InventoryItemDto> geAll() {
        return inventoryItemRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public InventoryItemDto getByName(String name) {
        return mapper.toDto(inventoryItemRepository.findByName(name));
    }
}
