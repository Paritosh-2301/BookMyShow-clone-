package com.example.InSeatCinema.Controller;

import com.example.InSeatCinema.EntryDtos.TicketEntryDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketController {

   @PostMapping("/book")
    public String bookTicket(@RequestBody TicketEntryDto ticketEntryDto){

       try{
           String result = ticketServcie.addTicket(ticketEntryDto);
           return result;
       }catch(Exception e){
           return "";

       }


   }


}
