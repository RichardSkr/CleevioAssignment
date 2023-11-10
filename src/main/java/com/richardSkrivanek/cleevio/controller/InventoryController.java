package com.richardSkrivanek.cleevio.controller;

import com.richardSkrivanek.cleevio.api.ApiApi;
import com.richardSkrivanek.cleevio.model.InventoryItemDto;
import com.richardSkrivanek.cleevio.service.implementation.InventoryItemServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InventoryController implements ApiApi {

    @NonNull
    private final InventoryItemServiceImpl inventoryItemService;

    @Override
    public ResponseEntity<InventoryItemDto> addInventory(InventoryItemDto inventoryItem) {
            return new ResponseEntity<>(inventoryItemService.saveItem(inventoryItem), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<InventoryItemDto> searchInventory(String searchText) {
        return new ResponseEntity<>(inventoryItemService.getByName(searchText),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<InventoryItemDto>> getAll() {
        return new ResponseEntity<>(inventoryItemService.geAll(),
                HttpStatus.OK);
    }
}
