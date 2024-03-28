package com.example.InSeatCinema.Services;

import com.example.InSeatCinema.Entities.*;
import com.example.InSeatCinema.EntryDtos.ShowEntryDto;
import com.example.InSeatCinema.Genres.SeatType;
import com.example.InSeatCinema.Repository.MovieRepository;
import com.example.InSeatCinema.Repository.TheaterRepository;
import com.example.InSeatCinema.convertors.ShowConvertors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {

    @Autowired
    MovieRepository movieRepository;

  @Autowired
    TheaterRepository theaterRepository;
    public String addShow(ShowEntryDto showEntryDto){

    // 1. Create a show entity

        ShowEntity showEntity= ShowConvertors.convertEntryToEntity(showEntryDto);

        int movieId = showEntryDto.getMovieId();
        int theatreId = showEntryDto.getTheaterId();

        MovieEntity movieEntity = movieRepository.findById(movieId).get();

        TheaterEntity theaterEntity = theaterRepository.findById(theatreId).get();


   // Setting the attribute of foreign key
        showEntity.setMovieEntity(movieEntity);
        showEntity.setTheaterEntity(theaterEntity);


        List<ShowSeatEntity> seatEntityList = createShowSeatEntity(showEntryDto, showEntity);

        showEntity.setListOfShowSeats(seatEntityList);

         List<ShowEntity> showEntityList = movieEntity.getShowEntityList();
         showEntityList.add(showEntity);
         movieEntity.setShowEntityList(showEntityList);

         movieRepository.save(movieEntity);

         List<ShowEntity> showEntityList1 = theaterEntity.getShowEntityList();

         showEntityList1.add(showEntity);

         theaterEntity.setShowEntityList(showEntityList);



         movieRepository.save(movieEntity);

         theaterRepository.save(theaterEntity);

         return "The show has been added succesfully";
    }

    private List<ShowSeatEntity> createShowSeatEntity(ShowEntryDto showEntryDto, ShowEntity showEntity){


        TheaterEntity theaterEntity = showEntity.getTheaterEntity();

        List<TheaterSeatEntity> theaterSeatEntityList = theaterEntity.getTheaterSeatEntityList();

        List<ShowSeatEntity> seatEntityList = new ArrayList<>();

        for(TheaterSeatEntity theaterSeatEntity: theaterSeatEntityList){
            ShowSeatEntity showSeatEntity = new ShowSeatEntity();

            showSeatEntity.setSeatNo(theaterSeatEntity.getSeatNo());
            showSeatEntity.setSeatType(theaterSeatEntity.getSeatType());

         if(theaterSeatEntity.getSeatType().equals(SeatType.CLASSIC))
             showSeatEntity.setPrice(showEntryDto.getClassicSeatPrice());

         else
             showSeatEntity.setPrice(showEntryDto.getPremiumSeatPrice());

         showSeatEntity.setBooked(false);
         showSeatEntity.setShowEntity(showEntity); //parent : foreign key for the shows

            seatEntityList.add(showSeatEntity); //Adding it to the list
        }

    }
}
