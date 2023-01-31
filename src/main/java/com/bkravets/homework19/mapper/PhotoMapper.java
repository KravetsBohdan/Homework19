package com.bkravets.homework19.mapper;

import com.bkravets.homework19.dto.PhotoDTO;
import com.bkravets.homework19.entity.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PhotoMapper {
    PhotoMapper INSTANCE = Mappers.getMapper(PhotoMapper.class);


    PhotoDTO toPhotoDTO(Photo photo);

    Photo toPhoto(PhotoDTO photoDTO);

}
