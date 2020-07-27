package com.watches.watch;

import com.watches.watch.mapper.WatchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.watches.utils.Endpoint.WATCHES;

@RestController
@RequestMapping(WATCHES + "/")
@RequiredArgsConstructor
public class WatchController {

    private final WatchService watchService;
    private final WatchMapper watchMapper;


    @PostMapping
    public ResponseEntity<Object> createWatch(@Valid @RequestBody WatchDto watchDto, UriComponentsBuilder uriBuilder) {
        Watch watch = watchService.createIfNotExists(watchMapper.toEntity(watchDto));
        URI watchUri = uriBuilder.path(watchMapper.toDto(watch) + "/{id}").buildAndExpand(watch.getId()).toUri();
        return ResponseEntity.created(watchUri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<WatchDto> getWatch(@PathVariable Long id) {
        return watchService.getWatch(id)
                .map(watch -> ResponseEntity.of(Optional.of(watchMapper.toDto(watch))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<WatchDto>> getWatches() {
        return ResponseEntity.ok(
                watchService.getWatches().stream()
                        .map(watchMapper::toDto)
                        .collect(Collectors.toList())
        );
    }
}
