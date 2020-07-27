package com.watches.watch.mapper;

import com.watches.watch.Watch;
import com.watches.watch.WatchDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(WatchMapperDecorator.class)
public interface WatchMapper {

    Watch toEntity(WatchDto dto);

    WatchDto toDto(Watch entity);
}
