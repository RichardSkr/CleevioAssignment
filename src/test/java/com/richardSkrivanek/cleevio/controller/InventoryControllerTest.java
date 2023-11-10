package com.richardSkrivanek.cleevio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.richardSkrivanek.cleevio.model.InventoryItemDto;
import com.richardSkrivanek.cleevio.service.implementation.InventoryItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryItemServiceImpl inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddInventory() throws Exception {
        InputStream productRequest = new ClassPathResource("common/productOk.json").getInputStream();
        InventoryItemDto itemRequest = objectMapper.readValue(productRequest, InventoryItemDto.class);

        InputStream productResponse = new ClassPathResource("common/productOkResponse.json").getInputStream();
            InventoryItemDto itemResponse = objectMapper.readValue(productResponse, InventoryItemDto.class);

            when(inventoryService.saveItem(Mockito.any(InventoryItemDto.class)))
                    .thenReturn(itemResponse);

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/save/item")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemRequest)))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("name").value("Pie"))
                    .andExpect(MockMvcResultMatchers.jsonPath("description").value("Yellow fruit"))
                    .andExpect(MockMvcResultMatchers.jsonPath("price").value(23.34))
                    .andExpect(MockMvcResultMatchers.jsonPath("id").value(1));

    }

    @Test
    public void testSearchInventory() throws Exception {
        String searchText = "Cake";
        InventoryItemDto itemDto = new InventoryItemDto("Cake", "Sweet delight", 15.99);

        when(inventoryService.getByName(searchText)).thenReturn(itemDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/inventory/{searchText}", searchText)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Cake"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Sweet delight"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(15.99));
    }


    @Test
    void testGetAllInventoryItems() throws Exception {
        InventoryItemDto Banana = new InventoryItemDto("Banana", "Yellow fruit.", 10.0);
        InventoryItemDto Apple = new InventoryItemDto("Apple", "Juicy.", 15.0);
        List<InventoryItemDto> itemList = Arrays.asList(Banana, Apple);
        
        when(inventoryService.geAll()).thenReturn(itemList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/inventory")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Banana"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Yellow fruit."))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(10.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Apple"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("Juicy."))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value(15.0));
    }
}
