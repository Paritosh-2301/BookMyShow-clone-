package com.example.InSeatCinema.Repository;

import com.example.InSeatCinema.Entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
}
