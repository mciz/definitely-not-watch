package com.watches.watch.mapper;


import com.watches.decoder.Base64Decoder;
import com.watches.decoder.MimeType;
import com.watches.watch.Watch;
import com.watches.watch.WatchDto;
import org.springframework.beans.factory.annotation.Autowired;

abstract class WatchMapperDecorator implements WatchMapper {

    @Autowired
    private WatchMapper watchMapper;

    @Override
    public Watch toEntity(WatchDto dto) {
        Watch entity =  watchMapper.toEntity(dto);
        entity.setMimeType(MimeType.fromMimeType(Base64Decoder.decodeMimeType(dto.getFountain())));
        return entity;
    }

    @Override
    public WatchDto toDto(Watch entity) {
        return watchMapper.toDto(entity);
    }
}
