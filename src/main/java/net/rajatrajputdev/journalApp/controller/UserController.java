package net.rajatrajputdev.journalApp.controller;
import net.rajatrajputdev.journalApp.entity.JournalEntry;
import net.rajatrajputdev.journalApp.entity.UserEntity;
import net.rajatrajputdev.journalApp.service.JournalEntryService;
import net.rajatrajputdev.journalApp.service.UserService;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-users")
    public List<UserEntity> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody UserEntity userEntity){
        userService.createUser(userEntity);
    }

    @PutMapping("/update-user/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity, @PathVariable String userName){
        UserEntity userInDB = userService.findByUserName(userName);
        if(userInDB != null){
            userInDB.setUserName(userEntity.getUserName());
            userInDB.setPassword(userEntity.getPassword());
            userService.createUser(userInDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
