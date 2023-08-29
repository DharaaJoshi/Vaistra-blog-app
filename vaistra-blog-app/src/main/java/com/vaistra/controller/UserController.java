package com.vaistra.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vaistra.dto.UserDTO;
import com.vaistra.entities.User;
import com.vaistra.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("show")
    public ResponseEntity<List<UserDTO>> allUser(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
                                                 @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                 @RequestParam(value = "sortBy", defaultValue = "userId", required = false) String sortBy,
                                                 @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)   {
        return new ResponseEntity<>(userService.getAllUsers(pageNumber,pageSize,sortBy,sortDirection), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<UserDTO> add(
            @RequestParam("image") MultipartFile file,
            @RequestParam("userData") @Valid String userDtoStr
    ) throws IOException {
        return new ResponseEntity<>(userService.addUser(userDtoStr,file),HttpStatus.CREATED);
    }

    @PostMapping("forgetpwd/{id}")
    private ResponseEntity<String> forget(@PathVariable int id){
        return new ResponseEntity<>(userService.eml(id),HttpStatus.OK);
    }

    @PostMapping("verify/{token}/{id}")
    public ResponseEntity<UserDTO> verify(@RequestBody String newpwd,@PathVariable long token,@PathVariable int id){
        return new ResponseEntity<>(userService.verification(newpwd,token,id),HttpStatus.OK);
    }

    @GetMapping("test")
    public String check(){
        return "Secured API";
    }

    @GetMapping("get/{id}")
    public ResponseEntity<UserDTO> byId(@PathVariable int id){
        return new ResponseEntity<>(userService.getById(id),HttpStatus.OK);
    }

    @GetMapping("showimg/{id}")
    public ResponseEntity<?> showimg(@PathVariable Integer id) throws IOException {
        byte[] b = userService.img(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(b);
    }

    @GetMapping("inactive/{id}")
    public ResponseEntity<UserDTO> inActiveUser(@PathVariable int id){
        return new ResponseEntity<>(userService.inAct(id),HttpStatus.ACCEPTED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<UserDTO> update(
            @RequestParam("image") MultipartFile file,
            @RequestParam("userData") @Valid String userDtoStr,
            @PathVariable int id
    ) throws IOException {
        return new ResponseEntity<>(userService.updt(file,userDtoStr,id),HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> remove(@PathVariable int id){
        return new ResponseEntity<>(userService.delete(id),HttpStatus.OK);
    }

    @GetMapping("restore/{id}")
    public ResponseEntity<UserDTO> restore(@PathVariable int id){
        return new ResponseEntity<>(userService.restore(id),HttpStatus.OK);
    }

    @DeleteMapping("forceDelete/{id}")
    public ResponseEntity<String> forceDelete(@PathVariable int id){
        return new ResponseEntity<>(userService.forceDelete(id),HttpStatus.OK);
    }
}