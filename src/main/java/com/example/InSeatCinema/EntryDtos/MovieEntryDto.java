package com.example.InSeatCinema.EntryDtos;

import com.example.InSeatCinema.Genres.Genre;
import com.example.InSeatCinema.Genres.Language;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data

public class MovieEntryDto {


    private String movieName;

    private double ratings;

    private int duration;


    private Language language;


    private Genre genre;
}
