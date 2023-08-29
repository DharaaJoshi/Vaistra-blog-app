package com.vaistra.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vaistra.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

    //public List<UserDTO> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

    public UserDTO addUser(String userDtoStr, MultipartFile file) throws IOException;

    public  String eml(int id);

    public UserDTO verification(String newpwd, long token, int id);

    public UserDTO getById(int id);

    public UserDTO inAct(int id);

    public byte[] img(Integer id) throws IOException;

    public UserDTO updt(MultipartFile file, String userDtoStr, int id) throws IOException;

    public String delete(int id);

    public UserDTO restore(int id);

    public String forceDelete(int id);
}
