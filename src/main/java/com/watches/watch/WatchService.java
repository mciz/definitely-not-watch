package com.watches.watch;

import com.watches.exception.TitleAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WatchService {

    private final WatchRepository watchRepository;

    @Transactional
    public Watch createIfNotExists(Watch watch) {
        if (watchRepository.existsByTitle(watch.getTitle())) {
            throw new TitleAlreadyExistsException("Watch with given title: " + watch.getTitle() + " already exists");
        }
        return watchRepository.save(watch);
    }

    public Optional<Watch> getWatch(long watchId) {
        return watchRepository.findById(watchId);
    }

    public List<Watch> getWatches() {
        return watchRepository.findAll();
    }
}
