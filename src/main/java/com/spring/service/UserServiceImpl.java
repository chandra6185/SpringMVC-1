package com.spring.service;

import com.spring.dao.UserDAOImpl;
import com.spring.forms.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl {

    UserDAOImpl userDAO;

    @Autowired(required=true)
    @Qualifier(value="userDAO")
    public void setUserDAO(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }

    public String save(User user){
       return userDAO.save(user);
    }
    public User retrieve(Integer id){
        return this.userDAO.retrieve(id);
    }
    public List<User> retrieveUsers(){
        return userDAO.retrieveUsers();
    }
     public void deleteUser(String userid){
         userDAO.deleteUser(userid);
     }
     public User updateUser(String userid){
       return userDAO.updateUser(userid);
     }

}
