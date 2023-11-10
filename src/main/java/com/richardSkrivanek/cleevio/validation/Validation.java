package com.richardSkrivanek.cleevio.validation;

import com.richardSkrivanek.cleevio.model.InventoryItemDto;
import exceptions.customExceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Validation {

    public InventoryItemDto validateInventoryItem(InventoryItemDto inventoryItemDto) {

        if (inventoryItemDto == null) {
            log.error("Validation failed: Null inventory item provided.");
            throw new ValidationException("Pro přídání je potřeba produkt.");
        }

        if (inventoryItemDto.getName() == null || inventoryItemDto.getName().isEmpty()) {
            log.warn("Validation failed: Empty or null name in inventory item.");
            throw new ValidationException("Produkt musí obsahovat název.");
        }

        if (inventoryItemDto.getDescription() == null || inventoryItemDto.getDescription().isEmpty()) {
            log.warn("Validation failed: Empty or null description in inventory item.");
            throw new ValidationException("Produkt musí obsahovat popis.");
        }

        if (inventoryItemDto.getPrice() == null || Double.compare(inventoryItemDto.getPrice(), 0.0) == 0) {
            log.warn("Validation failed: Null or zero price in inventory item.");
            throw new ValidationException("Produkt musí obsahovat cenu.");
        }

        return inventoryItemDto;
    }
}
