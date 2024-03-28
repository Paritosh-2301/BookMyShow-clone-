package com.example.InSeatCinema.convertors;

import com.example.InSeatCinema.Entities.MovieEntity;
import com.example.InSeatCinema.EntryDtos.MovieEntryDto;

public class MovieConvertors {

    public static MovieEntity convertEntryDtoToEntity(MovieEntryDto movieEntryDto){

        MovieEntity movieEntity = MovieEntity.builder().movieName(movieEntryDto.getMovieName())
                .duration(movieEntryDto.getDuration()).genre(movieEntryDto.getGenre()).language(movieEntryDto.getLanguage()).ratings(movieEntryDto.getRatings()).build();

        return movieEntity;
    }
}
