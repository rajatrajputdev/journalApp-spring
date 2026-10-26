package net.rajatrajputdev.journalApp.service;

import net.rajatrajputdev.journalApp.entity.UserEntity;
import net.rajatrajputdev.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(UserEntity UserEntity){
        userRepository.save(UserEntity);
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUser(ObjectId UserEntityId){
        return userRepository.findById(UserEntityId);
    }

    public void deleteUser(ObjectId UserEntityId){
        userRepository.deleteById(UserEntityId);
    }
    public UserEntity findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
