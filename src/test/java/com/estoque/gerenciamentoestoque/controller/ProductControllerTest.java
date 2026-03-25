package com.estoque.gerenciamentoestoque.controller;

import com.estoque.gerenciamentoestoque.dto.category.CategoryResponseDTO;
import com.estoque.gerenciamentoestoque.dto.product.ProductRequestDTO;
import com.estoque.gerenciamentoestoque.dto.product.ProductResponseDTO;
import com.estoque.gerenciamentoestoque.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Test
    @DisplayName("Deve criar um novo produto com sucesso")
    void deveCriarProduto() throws Exception {

        CategoryResponseDTO categoryResponse = new CategoryResponseDTO(1L, "Mouse");

        ProductRequestDTO productRequest = new ProductRequestDTO(
                "Attack Shark X11",
                1L,
                "Mouse sem fio",
                new BigDecimal("179.90"),
                10);

        ProductResponseDTO productResponse = new ProductResponseDTO(
                1L,
                productRequest.getName(),
                categoryResponse,
                productRequest.getDescription(),
                productRequest.getPrice(),
                productRequest.getAmount(),
                LocalDateTime.now()
        );

        when(productService.save(any(ProductRequestDTO.class))).thenReturn(productResponse);

        mockMvc.perform(post("/products")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Attack Shark X11"))
                .andExpect(jsonPath("$.category.id").value(1L))
                .andExpect(jsonPath("$.category.name").value("Mouse"))
                .andExpect(jsonPath("$.description").value("Mouse sem fio"))
                .andExpect(jsonPath("$.price").value(179.90))
                .andExpect(jsonPath("$.amount").value(10));


    }

    @Test
    @DisplayName("Deve listar todos os produtos")
    void deveListarProdutos() throws Exception {

        CategoryResponseDTO categoryResponse = new CategoryResponseDTO(1L, "Mouse");

        ProductRequestDTO productRequestOne = new ProductRequestDTO(
                "Attack Shark X11",
                1L,
                "Mouse sem fio",
                new BigDecimal("179.90"),
                10);

        ProductRequestDTO productRequestTwo = new ProductRequestDTO(
                "Attack Shark R1",
                1L,
                "Mouse com fio",
                new BigDecimal("159.90"),
                10);

        ProductResponseDTO productResponseOne = new ProductResponseDTO(
                1L,
                productRequestOne.getName(),
                categoryResponse,
                productRequestOne.getDescription(),
                productRequestOne.getPrice(),
                productRequestOne.getAmount(),
                LocalDateTime.now()
        );

        ProductResponseDTO productResponseTwo = new ProductResponseDTO(
                2L,
                productRequestTwo.getName(),
                categoryResponse,
                productRequestTwo.getDescription(),
                productRequestTwo.getPrice(),
                productRequestTwo.getAmount(),
                LocalDateTime.now()
        );

        when(productService.findAll()).thenReturn(List.of(productResponseOne, productResponseTwo));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Attack Shark X11"))
                .andExpect(jsonPath("$[0].category.id").value(1L))
                .andExpect(jsonPath("$[0].category.name").value("Mouse"))
                .andExpect(jsonPath("$[0].description").value("Mouse sem fio"))
                .andExpect(jsonPath("$[0].price").value(179.90))
                .andExpect(jsonPath("$[0].amount").value(10))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Attack Shark R1"))
                .andExpect(jsonPath("$[1].category.id").value(1L))
                .andExpect(jsonPath("$[1].category.name").value("Mouse"))
                .andExpect(jsonPath("$[1].description").value("Mouse com fio"))
                .andExpect(jsonPath("$[1].price").value(159.90))
                .andExpect(jsonPath("$[1].amount").value(10));

    }

    @Test
    @DisplayName("Deve retornar os detalhes de um produto específico pelo ID")
    void deveRetornarProdutoPeloId() throws Exception {

        CategoryResponseDTO categoryResponse = new CategoryResponseDTO(1L, "Mouse");

        ProductRequestDTO productRequest = new ProductRequestDTO(
                "Attack Shark X11",
                1L,
                "Mouse sem fio",
                new BigDecimal("179.90"),
                10);

        ProductResponseDTO productResponse = new ProductResponseDTO(
                1L,
                productRequest.getName(),
                categoryResponse,
                productRequest.getDescription(),
                productRequest.getPrice(),
                productRequest.getAmount(),
                LocalDateTime.now()
        );

        when(productService.findById(1L)).thenReturn(productResponse);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Attack Shark X11"))
                .andExpect(jsonPath("$.category.id").value(1L))
                .andExpect(jsonPath("$.category.name").value("Mouse"))
                .andExpect(jsonPath("$.description").value("Mouse sem fio"))
                .andExpect(jsonPath("$.price").value(179.90))
                .andExpect(jsonPath("$.amount").value(10));

    }

    @Test
    @DisplayName("Deve retornar os produtos vinculados a uma categoria específica pelo ID da categoria")
    void deveRetornarProdutosPeloIdDaCategoria() throws Exception {

        CategoryResponseDTO categoryResponse = new CategoryResponseDTO(1L, "Mouse");

        ProductRequestDTO productRequestOne = new ProductRequestDTO(
                "Attack Shark X11",
                1L,
                "Mouse sem fio",
                new BigDecimal("179.90"),
                10);

        ProductRequestDTO productRequestTwo = new ProductRequestDTO(
                "Attack Shark R1",
                1L,
                "Mouse com fio",
                new BigDecimal("159.90"),
                10);

        ProductResponseDTO productResponseOne = new ProductResponseDTO(
                1L,
                productRequestOne.getName(),
                categoryResponse,
                productRequestOne.getDescription(),
                productRequestOne.getPrice(),
                productRequestOne.getAmount(),
                LocalDateTime.now()
        );

        ProductResponseDTO productResponseTwo = new ProductResponseDTO(
                2L,
                productRequestTwo.getName(),
                categoryResponse,
                productRequestTwo.getDescription(),
                productRequestTwo.getPrice(),
                productRequestTwo.getAmount(),
                LocalDateTime.now()
        );

        when(productService.findByCategory(1L)).thenReturn(List.of(productResponseOne, productResponseTwo));

        mockMvc.perform(get("/products/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Attack Shark X11"))
                .andExpect(jsonPath("$[0].category.id").value(1L))
                .andExpect(jsonPath("$[0].category.name").value("Mouse"))
                .andExpect(jsonPath("$[0].description").value("Mouse sem fio"))
                .andExpect(jsonPath("$[0].price").value(179.90))
                .andExpect(jsonPath("$[0].amount").value(10))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Attack Shark R1"))
                .andExpect(jsonPath("$[1].category.id").value(1L))
                .andExpect(jsonPath("$[1].category.name").value("Mouse"))
                .andExpect(jsonPath("$[1].description").value("Mouse com fio"))
                .andExpect(jsonPath("$[1].price").value(159.90))
                .andExpect(jsonPath("$[1].amount").value(10));

    }

    @Test
    @DisplayName("Deve atualizar os dados de um produto existente pelo ID")
    void deveAtualizarProdutoPeloId() throws Exception {

        CategoryResponseDTO categoryResponse = new CategoryResponseDTO(1L, "Mouse");

        ProductRequestDTO productUpdateRequest = new ProductRequestDTO(
                "Attack Shark X11",
                1L,
                "Mouse sem fio",
                new BigDecimal("179.90"),
                10);

        ProductResponseDTO productUpdateResponse = new ProductResponseDTO(
                1L,
                productUpdateRequest.getName(),
                categoryResponse,
                productUpdateRequest.getDescription(),
                productUpdateRequest.getPrice(),
                productUpdateRequest.getAmount(),
                LocalDateTime.now()
        );

        when(productService.update(any(Long.class), any(ProductRequestDTO.class))).thenReturn(productUpdateResponse);

        mockMvc.perform(put("/products/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(productUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Attack Shark X11"))
                .andExpect(jsonPath("$.category.id").value(1L))
                .andExpect(jsonPath("$.category.name").value("Mouse"))
                .andExpect(jsonPath("$.description").value("Mouse sem fio"))
                .andExpect(jsonPath("$.price").value(179.90))
                .andExpect(jsonPath("$.amount").value(10));

    }

    @Test
    @DisplayName("Deve deletar um produto pelo ID")
    void deveDeletarProdutoPeloId() throws Exception {

        doNothing().when(productService).delete(1L);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).delete(1L);


    }
}