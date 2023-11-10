package com.richardSkrivanek.cleevio.validation;

import com.richardSkrivanek.cleevio.model.InventoryItemDto;
import exceptions.customExceptions.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ValidationTest {

    @Autowired
    private Validation inventoryItemValidation;

    @Test
    public void testValidationOk() {
        InventoryItemDto inventoryItem = new InventoryItemDto(
                "Banana",
                "Yellow fruit",
                23.49);

        assertEquals(inventoryItem, inventoryItemValidation.validateInventoryItem(inventoryItem));
    }

    @Test
    public void testValidationNok() {
        ValidationException exception = throwException(null);

        assertEquals("Pro přídání je potřeba produkt.", exception.getMessage());
    }

    @Test
    public void testValidationPriceEx() {
        InventoryItemDto inventoryItemDto = new InventoryItemDto(
                "Banana",
                "Yellow fruit",
                null);

        ValidationException exception = throwException(inventoryItemDto);

        assertEquals("Produkt musí obsahovat cenu.", exception.getMessage());
    }

    @Test
    public void testValidationNameEx() {
        InventoryItemDto inventoryItemDto = new InventoryItemDto(
                null,
                "Yellow fruit",
                23.49);

        ValidationException exception = throwException(inventoryItemDto);

        assertEquals("Produkt musí obsahovat název.", exception.getMessage());
    }

    @Test
    public void testValidationDescriptionEx() {
        InventoryItemDto inventoryItemDto = new InventoryItemDto(
                "Banana",
                null,
                23.49);

        ValidationException exception = throwException(inventoryItemDto);

        assertEquals("Produkt musí obsahovat popis.", exception.getMessage());
    }


    private ValidationException throwException(InventoryItemDto inventoryItemDto) {
        return assertThrows(ValidationException.class,
                () -> inventoryItemValidation.validateInventoryItem(inventoryItemDto));
    }
}
