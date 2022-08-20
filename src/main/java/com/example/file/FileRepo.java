package com.example.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Husan Narzullayev , чт 0:43. 18.08.2022
 */

@Repository
public interface FileRepo extends JpaRepository<File, Long> {

}
