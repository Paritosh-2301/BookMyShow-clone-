package com.example.InSeatCinema.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theaters")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TheaterEntity {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

   private int id;
   private String name;

   private String location;

   @OneToMany(mappedBy = "theaterEntity",cascade = CascadeType.ALL)

    private List<TheaterSeatEntity> theaterSeatEntityList = new ArrayList<>();

   @OneToMany(mappedBy = "theaterEntity", cascade = CascadeType.ALL)
    private List<ShowEntity> showEntityList = new ArrayList<>();
}
