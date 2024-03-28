package com.example.InSeatCinema.Services;

import com.example.InSeatCinema.Entities.MovieEntity;
import com.example.InSeatCinema.EntryDtos.MovieEntryDto;
import com.example.InSeatCinema.Repository.MovieRepository;
import com.example.InSeatCinema.convertors.MovieConvertors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {


    @Autowired
    MovieRepository movieRepository;
    public String addMovie(MovieEntryDto movieEntryDto)throws Exception{


        MovieEntity movieEntity = MovieConvertors.convertEntryDtoToEntity(movieEntryDto);
//    movieRepository.save(movieEntity)

        movieRepository.save(movieEntity);

        return "Movie Added succesfully";
    }
}
