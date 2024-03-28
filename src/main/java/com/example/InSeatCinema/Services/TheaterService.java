package com.example.InSeatCinema.Services;

import com.example.InSeatCinema.Entities.TheaterEntity;
import com.example.InSeatCinema.Entities.TheaterSeatEntity;
import com.example.InSeatCinema.EntryDtos.TheaterEntryDto;
import com.example.InSeatCinema.Genres.SeatType;
import com.example.InSeatCinema.Repository.TheaterRepository;
import com.example.InSeatCinema.Repository.TheaterSeatRepository;
import com.example.InSeatCinema.convertors.TheaterConvertors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheaterService {

    @Autowired
    TheaterSeatRepository theaterSeatRepository;
   @Autowired
    TheaterRepository theaterRepository;

    public String addTheater(TheaterEntryDto theaterEntryDto) throws Exception{

       //Do some validation
        if(theaterEntryDto.getName()==null || theaterEntryDto.getLocation()==null){
            throw new Exception("name and location shoul valid");
        }
        /*
        1. Create theaterSeats
        2. I need to save theater: I need theaterEntity
        3. Always set the attribute before saving.

         */
        TheaterEntity theaterEntity = TheaterConvertors.convertDtoToEntity(theaterEntryDto);


        List<TheaterSeatEntity> theaterSeatEntityList = createTheaterSeats(theaterEntryDto, theaterEntity);


        theaterEntity.setTheaterSeatEntityList(theaterSeatEntityList);

        theaterRepository.save(theaterEntity);
        return "Theater added succesfully";
    }

    private List<TheaterSeatEntity> createTheaterSeats(TheaterEntryDto theaterEntryDto, TheaterEntity theaterEntity){

        int noClassicSeats = theaterEntryDto.getClassicSeatsCount();
        int noPremiumSeats = theaterEntryDto.getPremiumSeatsCount();

        List<TheaterSeatEntity> theaterSeatEntityList = new ArrayList<>();

        //Created the classic Seats
        for(int count = 1;count<=noClassicSeats;count++){

            //We need to make a new TheaterSeatEntity
            TheaterSeatEntity theaterSeatEntity = TheaterSeatEntity.builder()
                    .seatType(SeatType.CLASSIC).seatNo(count+"C")
                    .theaterEntity(theaterEntity).build();

            theaterSeatEntityList.add(theaterSeatEntity);
        }

        //Create the premium Seats
        for(int count = 1;count<=noPremiumSeats;count++){

            TheaterSeatEntity theaterSeatEntity = TheaterSeatEntity.builder().seatType(SeatType.PREMIUM).seatNo(count +"P").theaterEntity(theaterEntity).build();


         theaterSeatEntityList.add(theaterSeatEntity);
        }


        return theaterSeatEntityList;
    }

}
