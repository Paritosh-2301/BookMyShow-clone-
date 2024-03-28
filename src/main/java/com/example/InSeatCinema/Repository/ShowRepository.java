package com.example.InSeatCinema.Repository;

import com.example.InSeatCinema.Entities.ShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<ShowEntity, Integer> {


}
