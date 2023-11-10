package com.richardSkrivanek.cleevio.mapper;

import com.richardSkrivanek.cleevio.entity.InventoryItem;
import com.richardSkrivanek.cleevio.model.InventoryItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryItemMapper {

    InventoryItemDto toDto(InventoryItem inventoryItem);
}
