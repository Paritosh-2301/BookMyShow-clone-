package com.example.InSeatCinema.Services;

import com.example.InSeatCinema.Entities.ShowEntity;
import com.example.InSeatCinema.Entities.ShowSeatEntity;
import com.example.InSeatCinema.Entities.TicketEntity;
import com.example.InSeatCinema.Entities.UserEntity;
import com.example.InSeatCinema.EntryDtos.TicketEntryDto;
import com.example.InSeatCinema.Repository.ShowRepository;
import com.example.InSeatCinema.Repository.TicketRepository;
import com.example.InSeatCinema.Repository.UserRepository;
import com.example.InSeatCinema.convertors.TicketConvertors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

   @Autowired
   TicketRepository ticketRepository;

   @Autowired
   ShowRepository showRepository;
   @Autowired
   UserRepository userRepository;
   private Object totalAmount;

   @Autowired
   JavaMailSender javaMailSender;

   public String addTicket(TicketEntryDto ticketEntryDto) throws Exception{


      //Create ticketEntity from entryDto : convertDto --> Entity

      TicketEntity ticketEntity = TicketConvertors.convertEntryToENtity(ticketEntryDto);

//      int showId = ticketEntryDto.getShowId();

      // validation check if the requested seats are avaliable or not

      boolean isValidRequest = checkvalidityofRequestedSeats(ticketEntryDto);

      if(isValidRequest==false){

         throw new Exception("Requested seats are not available");
      }

     // assume requested seats are valid

      //calculate total amount

      ShowEntity showEntity = showRepository.findById(ticketEntryDto.getShowId()).get();

      List<ShowSeatEntity> seatEntityList = showEntity.getListOfShowSeats();

      List<String> requestedSeats = ticketEntryDto.getRequestedSeats();

      int totalAmount = 0;
      for(ShowSeatEntity showSeatEntity:seatEntityList){
         if(requestedSeats.contains(showSeatEntity.getSeatNo())){

            totalAmount  = totalAmount + showSeatEntity.getPrice();
            showSeatEntity.setBooked(true);
            showSeatEntity.setBooked(new Date());
         }
      }
      ticketEntity.setTotalAmount(totalAmount);

      //Setting the other attributes for the ticketEntity

      ticketEntity.setMovieName(showEntity.getMovieEntity().getMovieName());
      ticketEntity.setShowDate(showEntity.getShowDate());
      ticketEntity.setShowTime(showEntity.getShowTime());
      ticketEntity.setTheatername(showEntity.getMovieEntity().getName());
    //We need to set that string that talked about requested seats

      String allotedSeats = getAllotedSeatsFromShowSeats(requestedSeats);

      //Setting the foreign key attributes
      UserEntity userEntity = userRepository.findById(ticketEntryDto.getUserId()).get();

      ticketEntity.setUserEntity(userEntity);
      ticketEntity.setShowEntity(showEntity);

      //save the parent
      List<TicketEntity> ticketEntityList = showEntity.getListOfBookedTickets();
      ticketEntityList.add(ticketEntity);
      showEntity.setListOfBookedTickets(ticketEntityList);

      showRepository.save((showEntity);


      List<TicketEntity> ticketEntityList1 = userEntity.getBookedTickets();
      ticketEntityList1.add(ticketEntity);
      userEntity.setBookedTickets(ticketEntityList1);

      userRepository.save(userEntity);

      String body = "Hi this is to confirm your booking for seat No "+allotedSeats +"for the movie : " + ticketEntity.getMovieName();


      MimeMessage mimeMessage=javaMailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
      mimeMessageHelper.setFrom("mayank041297@gmail.com");
      mimeMessageHelper.setTo(userEntity.getEmail());
      mimeMessageHelper.setText(body);
      mimeMessageHelper.setSubject("Confirming your booked Ticket");

      javaMailSender.send(mimeMessage);

      return "Ticket has been added succesfully";


   }

   private String getAllotedSeatsFromShowSeats(List<String> requestedSeats){

      String result = "";
      for(String seat:requestedSeats){
         result = result + seat+ ", ";
      }

     return result;
   }
   private boolean checkvalidityofRequestedSeats(TicketEntryDto ticketEntryDto){
      int showid = ticketEntryDto.getShowId();

      List<String> requestSeats = ticketEntryDto.getRequestedSeats();

      ShowEntity showEntity = showRepository.findById(showid).get();

      List<ShowSeatEntity> ListOfSeats = showEntity.getListOfShowSeats();

      //iterating over the list of seats for that particular show
      for(ShowSeatEntity showSeatEntity : ListOfSeats){

         String seatNo = showSeatEntity.getSeatNo();

         if(requestedSeats.contains(seatNo)){

            if(showSeatEntity.isBooked()==true){
               return false;
            }
         }


      }
// All the seats requested were available
      return true;
   }
}
