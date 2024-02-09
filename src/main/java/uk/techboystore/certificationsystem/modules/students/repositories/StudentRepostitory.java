package uk.techboystore.certificationsystem.modules.students.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.techboystore.certificationsystem.modules.students.entities.StudentEntity;

public interface StudentRepostitory extends JpaRepository<StudentEntity, UUID>{
    public Optional<StudentEntity> findByEmail(String email);
}
