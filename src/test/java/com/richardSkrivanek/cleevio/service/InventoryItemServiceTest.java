package com.richardSkrivanek.cleevio.service;

import com.richardSkrivanek.cleevio.entity.InventoryItem;
import com.richardSkrivanek.cleevio.model.InventoryItemDto;
import com.richardSkrivanek.cleevio.repository.InventoryItemRepository;
import com.richardSkrivanek.cleevio.validation.Validation;
import exceptions.customExceptions.ProductNameAlreadyExistException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InventoryItemServiceTest {

    @Autowired
    private InventoryItemService inventoryItemService;

    @MockBean
    private InventoryItemRepository inventoryItemRepository;

    @MockBean
    private Validation inventoryItemValidation;

    @Test
    public void testSaveItem() {

        InventoryItemDto inputDto = new InventoryItemDto("Pie", "Yellow fruit", 23.34);

        when(inventoryItemValidation.validateInventoryItem(inputDto)).thenReturn(inputDto);

        when(inventoryItemRepository.save(Mockito.any(InventoryItem.class)))
                .thenAnswer(invocation -> {
                    InventoryItem savedItem = invocation.getArgument(0);
                    savedItem.setId(1L);
                    return savedItem;
                });

        InventoryItemDto resultDto = inventoryItemService.saveItem(inputDto);

        verify(inventoryItemRepository, times(1)).save(argThat(item -> item.getDateOfCreation() != null));

        assertEquals(1L, resultDto.getId());
    }

    @Test
    public void testSaveItemWithExistingName() {
        InventoryItemDto dto = new InventoryItemDto();
        dto.setName("ExistingName");
        dto.setDescription("s");
        dto.setPrice(22.2);
        dto.setDateOfCreation(LocalDate.now());

        when(inventoryItemRepository.findByName("ExistingName")).thenReturn(new InventoryItem());

        doThrow(new ConstraintViolationException("Name already exists", null, "name"))
                .when(inventoryItemRepository).save(any(InventoryItem.class));

        assertThrows(ProductNameAlreadyExistException.class, () -> inventoryItemService.saveItem(dto));

        verify(inventoryItemRepository, times(1)).save(any(InventoryItem.class));
    }

    @Test
    public void testGetAllEmptyList() {
        when(inventoryItemRepository.findAll()).thenReturn(Collections.emptyList());

        List<InventoryItemDto> result = inventoryItemService.geAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllNonEmptyList() {
        List<InventoryItem> mockItems = loadTestData();
        when(inventoryItemRepository.findAll()).thenReturn(mockItems);

        List<InventoryItemDto> result = inventoryItemService.geAll();

        assertNotNull(result);
        assertEquals(mockItems.size(), result.size());
    }

    @Test
    public void testMapping() {
        InventoryItem mockItem = new InventoryItem();
        when(inventoryItemRepository.findAll()).thenReturn(Collections.singletonList(mockItem));

        List<InventoryItemDto> result = inventoryItemService.geAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }


    private List<InventoryItem> loadTestData() {
        List<InventoryItem> inventoryItems = new ArrayList<>();

        inventoryItems.add(new InventoryItem(1, "Pie", "Yellow fruit", 23.34, LocalDate.parse("2023-11-09")));
        inventoryItems.add(new InventoryItem(2, "Cake", "Sweet delight", 15.99, LocalDate.parse("2023-11-09")));
        inventoryItems.add(new InventoryItem(3, "Cupcake", "Mini treat", 5.49, LocalDate.parse("2023-11-09")));
        inventoryItems.add(new InventoryItem(4, "Brownie", "Chocolate goodness", 12.75, LocalDate.parse("2023-11-09")));
        inventoryItems.add(new InventoryItem(5, "Cheesecake", "Creamy delight", 18.50, LocalDate.parse("2023-11-09")));
        inventoryItems.add(new InventoryItem(6, "Fruit Tart", "Colorful treat", 21.25, LocalDate.parse("2023-11-09")));
        inventoryItems.add(new InventoryItem(7, "Pudding", "Smooth dessert", 8.99, LocalDate.parse("2023-11-09")));
        inventoryItems.add(new InventoryItem(8, "Strudel", "Flaky pastry", 14.50, LocalDate.parse("2023-11-09")));
        inventoryItems.add(new InventoryItem(9, "Muffin", "Portable snack", 3.75, LocalDate.parse("2023-11-09")));
        inventoryItems.add(new InventoryItem(10, "Eclair", "French delight", 9.99, LocalDate.parse("2023-11-09")));

        return inventoryItems;
    }


}
