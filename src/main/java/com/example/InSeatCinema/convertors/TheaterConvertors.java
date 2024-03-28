package com.example.InSeatCinema.convertors;

import com.example.InSeatCinema.Entities.TheaterEntity;
import com.example.InSeatCinema.EntryDtos.TheaterEntryDto;

public class TheaterConvertors {
    public static TheaterEntity convertDtoToEntity(TheaterEntryDto theaterEntryDto) {
        return TheaterEntity.builder().location(theaterEntryDto.getLocation()).name(theaterEntryDto.getName()).build();



    }
}