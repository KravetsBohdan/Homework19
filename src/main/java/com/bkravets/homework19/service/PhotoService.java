package com.bkravets.homework19.service;

import com.bkravets.homework19.entity.Photo;
import com.bkravets.homework19.entity.Student;
import com.bkravets.homework19.repository.PhotoRepository;
import com.bkravets.homework19.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public void addPhotoToStudent(long studentId, String description, String url) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Photo photo = new Photo();
        photo.setDescription(description);
        photo.setUrl(url);
        photo.setStudent(student);
        photoRepository.save(photo);
    }

    public List<Photo> getPhotoByDescription(String description) {
       return photoRepository.findPhotoByDescription(description);
    }
}
