package com.example.FestivalDashboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

@Entity
@Data
@Table(name = "djs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DJ implements Serializable {
    @Id
    @Getter
    @GeneratedValue
    private Long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String genre;
    @Getter
    @Setter
    private int age;

    @Setter
    @Getter
    @OneToMany(mappedBy = "dj", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"dj"})
    private List<Performance> performances = new ArrayList<>();

    public DJ() {
    }

    public DJ(String name, String genre, int age) {
        this.name = name;
        this.genre = genre;
        this.age = age;
    }

}
