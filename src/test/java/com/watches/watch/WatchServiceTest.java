package com.watches.watch;

import com.watches.exception.TitleAlreadyExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class WatchServiceTest {

    @Mock
    private WatchRepository repository;
    @InjectMocks
    private WatchService service;

    private Watch mockWatch() {
        return Watch.builder()
                .title("unique title")
                .build();
    }

    @DisplayName("When a new watch is created then watch should be stored")
    @Test
    void storeNewWatch() {
        when(repository.existsByTitle(anyString())).thenReturn(false);
        when(repository.save(any(Watch.class))).thenReturn(mockWatch());

        Watch createdWatch = service.createIfNotExists(mockWatch());

        assertAll(
                () -> assertThat(createdWatch).isNotNull(),
                () -> assertThat(createdWatch.getTitle()).isEqualTo("unique title")
        );
    }

    @DisplayName("When a new watch is created and title is not unique then Exception is thrown")
    @Test
    void storeWatchAndThrowException() {
        when(repository.existsByTitle(anyString())).thenReturn(true);

        assertThrows(TitleAlreadyExistsException.class,
                () -> service.createIfNotExists(mockWatch()));
    }
}