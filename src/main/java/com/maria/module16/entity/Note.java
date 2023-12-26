package com.maria.module16.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="username", nullable=false)
    private User user;

    @Column
    @Size(max = 50)
    private String title;

    @Column
    @Size(max = 300)
    private String content;
}
