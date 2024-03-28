package com.example.InSeatCinema.Repository;

import com.example.InSeatCinema.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
