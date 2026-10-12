package com.example.lab12.Service;

import com.example.lab12.Api.ApiException;
import com.example.lab12.Model.Blog;
import com.example.lab12.Model.User;
import com.example.lab12.Repository.AuthRepository;
import com.example.lab12.Repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final AuthRepository authRepository;

    public List<Blog> getAllBlogs(Integer adminId){
        return blogRepository.findAll();
    }

    public void addBlog(Integer userId, Blog blog){
        User user = authRepository.findUserById(userId);
        if (user == null){
            throw new ApiException("User not allowed");
        }
        blog.setUser(user);
        blogRepository.save(blog);
    }

    public List<Blog> getBlogById(Integer userId){
        User user = authRepository.findUserById(userId);
        if (user == null){
            throw new ApiException("user not found");
        }
        return blogRepository.findAllByUser(user);
    }

    public void updateBlog(Integer userId, Integer blogId, Blog blog){
        Blog blog1 = blogRepository.findBlogById(blogId);
        User user = authRepository.findUserById(userId);

        if (blog1 == null){
            throw new ApiException("blog not found");
        }

        if (user == null){
            throw new ApiException("user not found");
        }
        if (blog1.getUser().getId()!=userId){
            throw new ApiException("can't update");
        }
        blog1.setTitle(blog.getTitle());
        blog1.setBody(blog.getBody());
        blogRepository.save(blog1);
    }

    public void deleteBlog(Integer userId, Integer blogId){
        Blog blog = blogRepository.findBlogById(blogId);
        if (blog == null){
            throw new ApiException("blog not found");
        }

        User user = authRepository.findUserById(userId);
        if (user == null){
            throw new ApiException("user not found");
        }
        if (blog.getUser().getId()!=userId){
            throw new ApiException("can't delete");
        }
        blogRepository.delete(blog);

    }
}
