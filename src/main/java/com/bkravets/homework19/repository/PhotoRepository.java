package com.bkravets.homework19.repository;

import com.bkravets.homework19.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
  List<Photo> findPhotoByDescription(String description);
}

