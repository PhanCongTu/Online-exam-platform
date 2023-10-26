package com.example.springboot.repository;

import com.example.springboot.entity.ClassroomRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRegistrationRepository extends JpaRepository<ClassroomRegistration,Long> {
}
