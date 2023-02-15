package com.bkravets.homework19.mapper;

import com.bkravets.homework19.dto.PhotoDto;
import com.bkravets.homework19.entity.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PhotoMapper {
    PhotoMapper INSTANCE = Mappers.getMapper(PhotoMapper.class);


    PhotoDto toPhotoDTO(Photo photo);

    Photo toPhoto(PhotoDto photoDTO);

}
