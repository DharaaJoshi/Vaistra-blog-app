package com.vaistra.utils;

import com.vaistra.dto.*;
import com.vaistra.entities.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AppUtils {

    private final ModelMapper modelMapper;

    @Autowired
    public AppUtils(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    //____________________________________________________________________Comment Utility Methods Start_____________________________________________________

    // Comment to CommentDTO
    public CommentDTO commentToDto(Comment comment) {

        return this.modelMapper.map(comment,CommentDTO.class);
    }

    //CommentDTO to Comment
    public Comment dtoToComment(CommentDTO commentDTO) {

        return this.modelMapper.map(commentDTO,Comment.class);
    }

    public  List<CommentDTO> commentsToDtos(List<Comment> comments) {

        java.lang.reflect.Type targetListType = new TypeToken<List<CommentDTO>>() {}.getType();
        return modelMapper.map(comments, targetListType);
    }

    public List<Comment> dtosToComments(List<CommentDTO> dtos) {

        java.lang.reflect.Type targetListType = new TypeToken<List<Comment>>() {}.getType();
        return modelMapper.map(dtos, targetListType);

    }

    //____________________________________________________________________Comment Utility Methods Over_____________________________________________________

    //____________________________________________________________________Category Utility Methods Start_____________________________________________________

    // Category DTO methods
    public CategoryDTO categoryToDto(Category category) {

        return this.modelMapper.map(category, CategoryDTO.class);
    }

    public Category dtoToCategory(CategoryDTO dto) {

        return this.modelMapper.map(dto, Category.class);
    }

    public List<CategoryDTO> categoriesToDtos(List<Category> categories) {
        java.lang.reflect.Type targetListType = new TypeToken<List<CategoryDTO>>() {}.getType();

        return modelMapper.map(categories, targetListType);

    }

    public List<Category> dtosToCategories(List<CategoryDTO> dtos) {
        java.lang.reflect.Type targetListType = new TypeToken<List<Category>>() {}.getType();

        return modelMapper.map(dtos, targetListType);
    }

    //____________________________________________________________________Category Utility Methods Over_____________________________________________________


    //____________________________________________________________________Tag Utility Methods Start_____________________________________________________

    // Tag DTO methods

    public TagDTO tagToDto(Tag tag) {
        return this.modelMapper.map(tag,TagDTO.class);
    }

    public Tag dtoToTag(TagDTO tagDTO) {
        return this.modelMapper.map(tagDTO,Tag.class);
    }

    public List<TagDTO> tagsToDtos(List<Tag> tags) {
        java.lang.reflect.Type targetListType = new TypeToken<List<TagDTO>>() {}.getType();

        return modelMapper.map(tags, targetListType);
    }

    public List<Tag> dtosToTags(List<TagDTO> dtos) {
        java.lang.reflect.Type targetListType = new TypeToken<List<Tag>>() {}.getType();

        return modelMapper.map(dtos, targetListType);
    }

    //____________________________________________________________________Tag Utility Methods Over_____________________________________________________

    //____________________________________________________________________Post Utility Methods Start_____________________________________________________

    public PostDTO postToDto(Post post) {
        return this.modelMapper.map(post,PostDTO.class);
    }

    public Post dtoToPost(PostDTO postDTO) {
        return this.modelMapper.map(postDTO,Post.class);
    }

    public List<PostDTO> postsToDtos(List<Post> posts) {
        java.lang.reflect.Type targetListType = new TypeToken<List<PostDTO>>() {}.getType();

        return modelMapper.map(posts, targetListType);
    }

    public List<Post> dtosToPosts(List<PostDTO> dtos) {
        java.lang.reflect.Type targetListType = new TypeToken<List<Post>>() {}.getType();

        return modelMapper.map(dtos, targetListType);
    }

    //____________________________________________________________________Post Utility Methods Over_____________________________________________________

    //____________________________________________________________________User Utility Methods Start_____________________________________________________

    private static long otp = 0;

    public String getEmailMessage(String name, String host)
    {
        Random random = new Random();

        otp = 100_000 + random.nextInt(900_000);

        return "Hello, "+name+",\n\n Your Request for Forget Password is Accepted. Please Enter this OTP to your API for further Process. \n\n"
                + otp
                +"\n\n Team VaistraTech.";
    }

    public boolean verify(long token){
        if(otp == token){
            return true;
        }
        else {
            return false;
        }
    }



    public UserDTO userToDto(User user) {
        return this.modelMapper.map(user, UserDTO.class);
    }

    public User dtoToUser(UserDTO userDTO) {
        return this.modelMapper.map(userDTO,User.class);
    }


    public List<UserDTO> usersToDtos(List<User> users) {
        java.lang.reflect.Type targetListType = new TypeToken<List<UserDTO>>() {}.getType();

        return modelMapper.map(users, targetListType);
    }

    //____________________________________________________________________User Utility Methods Over_____________________________________________________


}