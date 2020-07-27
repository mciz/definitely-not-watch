package com.watches.watch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watches.exception.TitleAlreadyExistsException;
import com.watches.watch.mapper.WatchMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.watches.utils.Endpoint.WATCHES;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WatchController.class)
class WatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WatchMapper mapper;

    @MockBean
    private WatchService service;

    private WatchDto mockWatchDtoWithId() {
        return WatchDto.builder()
                .id(2L)
                .title("unique title")
                .fountain("R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=")
                .description("description")
                .price(14)
                .build();
    }

    private WatchDto mockWatchDtoWithoutId() {
        return WatchDto.builder()
                .title("unique title")
                .fountain("R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=")
                .description("description")
                .price(14)
                .build();
    }


    private Watch mockWatchWithId() {
        return Watch.builder()
                .id(2L)
                .title("unique title")
                .fountain("R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=")
                .description("description")
                .price(14)
                .build();
    }

    private Watch mockWatchWithoutId() {
        return Watch.builder()
                .title("unique title")
                .fountain("R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=")
                .description("description")
                .price(14)
                .build();
    }

    @Nested
    @DisplayName("GET")
    class Get {

        @DisplayName("when GET watch with ID in path then watch with given id should be returned")
        @Test
        void getWatchWithId() throws Exception {
            when(service.getWatch(anyLong())).thenReturn(Optional.of(mockWatchWithId()));
            when(mapper.toDto(any(Watch.class))).thenReturn(mockWatchDtoWithId());

            mockMvc.perform(
                    get("/" + WATCHES + "/{id}", 2L))
                    .andExpect(status().isOk())
                    .andExpect(matchAll(
                            jsonPath("$.id").value(2),
                            jsonPath("$.title").value("unique title"),
                            jsonPath("$.fountain").exists(),
                            jsonPath("$.description").exists(),
                            jsonPath("$.price").value(14)
                    ));
        }
    }

    @Nested
    @DisplayName("POST")
    class Post {

        @DisplayName("when Post JSON watch then watch is created")
        @Test
        void createWatchJson() throws Exception {
            when(service.createIfNotExists(any(Watch.class))).thenReturn(mockWatchWithoutId());
            when(mapper.toEntity(any(WatchDto.class))).thenReturn(mockWatchWithoutId());
            String body = objectMapper.writeValueAsString(mockWatchDtoWithoutId());

            mockMvc.perform(
                    post("/" + WATCHES + "/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(status().isCreated());
        }

        @DisplayName("when Post JSON watch then Exception is thrown")
        @Test
        void createWatchThrowExceptionJson() throws Exception {
            when(service.createIfNotExists(any(Watch.class))).thenReturn(mockWatchWithoutId());
            when(mapper.toEntity(any(WatchDto.class))).thenThrow(new TitleAlreadyExistsException());
            String body = objectMapper.writeValueAsString(mockWatchDtoWithoutId());

            mockMvc.perform(
                    post("/" + WATCHES + "/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(status().isConflict());
        }

        @DisplayName("when Post XML watch then watch is created")
        @Test
        void createWatchXml() throws Exception {
            when(service.createIfNotExists(any(Watch.class))).thenReturn(mockWatchWithoutId());
            when(mapper.toEntity(any(WatchDto.class))).thenReturn(mockWatchWithoutId());
            String body = "<WatchDto><title>watch xml title</title><price>8</price>\n" +
                    "    <description>XML description</description>\n" +
                    "    <fountain>R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=</fountain></WatchDto>";

            mockMvc.perform(
                    post("/" + WATCHES + "/")
                            .contentType(MediaType.APPLICATION_XML)
                            .content(body))
                    .andExpect(status().isCreated());
        }

        @DisplayName("when Post XML watch then Exception is thrown")
        @Test
        void createWatchThrowExceptionXML() throws Exception {
            when(service.createIfNotExists(any(Watch.class))).thenReturn(mockWatchWithoutId());
            when(mapper.toEntity(any(WatchDto.class))).thenThrow(new TitleAlreadyExistsException());
            String body = "<WatchDto><title>watch xml title</title><price>8</price>\n" +
                    "    <description>XML description</description>\n" +
                    "    <fountain>R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=</fountain></WatchDto>";

            mockMvc.perform(
                    post("/" + WATCHES + "/")
                            .contentType(MediaType.APPLICATION_XML)
                            .content(body))
                    .andExpect(status().isConflict());
        }
    }
}