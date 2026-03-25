package com.estoque.gerenciamentoestoque.controller;

import com.estoque.gerenciamentoestoque.dto.category.CategoryRequestDTO;
import com.estoque.gerenciamentoestoque.dto.category.CategoryResponseDTO;
import com.estoque.gerenciamentoestoque.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoryService categoryService;

    @Test
    @DisplayName("Deve criar uma nova categoria com sucesso")
    void deveCriarCategoria() throws Exception {

        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName("Mouse");

        CategoryResponseDTO response = new CategoryResponseDTO();
        response.setId(1L);
        response.setName("Mouse");

        when(categoryService.save(any(CategoryRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/categories")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Mouse"));


    }

    @Test
    @DisplayName("Deve listar todas as categorias")
    void deveListarCategorias() throws Exception {

        CategoryRequestDTO requestOne = new CategoryRequestDTO();
        requestOne.setName("Mouse");

        CategoryRequestDTO requestTwo = new CategoryRequestDTO();
        requestTwo.setName("Teclado");

        CategoryResponseDTO responseOne = new CategoryResponseDTO();
        responseOne.setId(1L);
        responseOne.setName("Mouse");

        CategoryResponseDTO responseTwo = new CategoryResponseDTO();
        responseTwo.setId(2L);
        responseTwo.setName("Teclado");

        when(categoryService.findAll()).thenReturn(List.of(responseOne, responseTwo));

         mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Mouse"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Teclado"));
    }

    @Test
    @DisplayName("Deve retornar os detalhes de uma categoria específica pelo ID")
    void deveRetornarCategoriaPeloId() throws Exception {

        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName("Mouse");

        CategoryResponseDTO response = new CategoryResponseDTO();
        response.setId(1L);
        response.setName("Mouse");

        when(categoryService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Mouse"));

    }

    @Test
    @DisplayName("Deve deletar uma categoria pelo ID")
    void deveDeletarCategoriaPeloId() throws Exception {

        doNothing().when(categoryService).delete(1L);

        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isNoContent());

        verify(categoryService).delete(1L);

    }
}