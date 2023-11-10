package com.richardSkrivanek.cleevio.validation;

import com.richardSkrivanek.cleevio.model.InventoryItemDto;
import exceptions.customExceptions.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class Validation {

    public InventoryItemDto validateInventoryItem(InventoryItemDto inventoryItemDto) {

        if (inventoryItemDto == null) {
            throw new ValidationException("Pro přídání je potřeba produkt.");
        }

        if (inventoryItemDto.getName() == null || inventoryItemDto.getName().isEmpty()) {
            throw new ValidationException("Produkt musí obsahovat název.");
        }

        if (inventoryItemDto.getDescription() == null || inventoryItemDto.getDescription().isEmpty()) {
            throw new ValidationException("Produkt musí obsahovat popis.");
        }

        if (inventoryItemDto.getPrice() == null || Double.compare(inventoryItemDto.getPrice(), 0.0) == 0) {
            throw new ValidationException("Produkt musí obsahovat cenu.");
        }



        return inventoryItemDto;
    }
}
