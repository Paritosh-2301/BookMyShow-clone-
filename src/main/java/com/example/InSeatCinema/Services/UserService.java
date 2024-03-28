package com.example.InSeatCinema.Services;

import com.example.InSeatCinema.Entities.UserEntity;
import com.example.InSeatCinema.EntryDtos.UserEntryDto;
import com.example.InSeatCinema.Repository.UserRepository;
import com.example.InSeatCinema.convertors.UserConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public String addUser(UserEntryDto userEntryDto)throws Exception{

      UserEntity userEntity = UserConvertor.convertDtoToEntity(userEntryDto);

      userRepository.save(userEntity);
      
      return "";
    }

}
