package com.example.file;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Husan Narzullayev , чт 0:10. 18.08.2022
 */
@Entity
@Getter
@Setter
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private long size;

    @Column
    private String contentType;

    @Column
    private String imageUrl;

    @Column
    private String originalName;

}
