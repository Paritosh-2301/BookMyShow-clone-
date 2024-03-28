package com.example.InSeatCinema.convertors;

import com.example.InSeatCinema.Entities.ShowEntity;
import com.example.InSeatCinema.EntryDtos.ShowEntryDto;

public class ShowConvertors {

    public static ShowEntity convertEntryToEntity(ShowEntryDto showEntryDto){

        ShowEntity showEntity = ShowEntity.builder()
                .showDate(showEntryDto.getLocalDate())
                .showTime(showEntryDto.getLocalTime())
                .showType(showEntryDto.getShowType())
                .build();

        return showEntity;
    }
}
