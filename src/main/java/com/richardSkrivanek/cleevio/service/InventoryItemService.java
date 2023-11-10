package com.richardSkrivanek.cleevio.service;

import com.richardSkrivanek.cleevio.model.InventoryItemDto;

import java.util.List;

public interface InventoryItemService {

    InventoryItemDto saveItem(InventoryItemDto inventoryItemDto);
    List<InventoryItemDto> geAll();
    InventoryItemDto getByName(String name);
}
