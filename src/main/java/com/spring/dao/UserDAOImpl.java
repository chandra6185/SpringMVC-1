package com.spring.dao;

import com.spring.forms.User;
import com.spring.pojo.UserPojo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import javax.jws.soap.SOAPBinding;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Transactional
public class UserDAOImpl {

    @Autowired
    private SessionFactory sessionFactory;


    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    public String save(User user){
        Session session = this.sessionFactory.openSession();
        try {
            session.getTransaction().begin();
            UserPojo userPojo = new UserPojo();
            userPojo.setName(user.getName());
            userPojo.setPassword(user.getPassword());
            userPojo.setAddress(user.getAddress());
            userPojo.setCountry(user.getCountry());
            userPojo.setEmail(user.getEmail());
            userPojo.setNumber(user.getNumber());
            userPojo.setSex(user.getSex());
            if(user.getId() != null){
                userPojo.setId(user.getId());
                session.update(userPojo);
            }
            else{
                session.persist(userPojo);
            }
            session.getTransaction().commit();
        }
        catch (Exception exp) {
            System.out.println(exp);
            return "Failure";
        }
        finally {
            session.close();
        }
        return "Success";
    }
    public User retrieve(Integer id){
        Session session = this.sessionFactory.openSession();
        User u1 = null;
        try{
            UserPojo usr1 =(UserPojo) session.load(UserPojo.class,new Integer(id));
            u1 = new User();
            u1.setName(usr1.getName());
            u1.setAddress(usr1.getAddress());
            u1.setCountry(usr1.getCountry());
            u1.setEmail(usr1.getEmail());
            u1.setSex(usr1.getSex());
        }
        catch (Exception exp) {
            System.out.println(exp);

        }
        finally{
            session.close();
        }
        return u1;
    }

    public List<User> retrieveUsers(){
        Session session = this.sessionFactory.openSession();
        List<User> userList = new ArrayList<User>();
        List<UserPojo> userPojos = null;

        try{
            userPojos = session.createCriteria(UserPojo.class).list();
              for (UserPojo u1:userPojos) {
                  User usr =new User();
                     usr.setId(u1.getId());
                    usr.setName(u1.getName());
                    usr.setSex(u1.getSex());
                    usr.setEmail(u1.getEmail());
                    usr.setAddress(u1.getAddress());
                    usr.setCountry(u1.getCountry());
                    usr.setFramework(populateDefaultModel());
                  userList.add(usr);
            }
        }
        catch(Exception exp){
            System.out.println(exp);

        }

        return userList;
    }

    private List<String> populateDefaultModel() {
        List<String> frameworksList = new ArrayList<String>();
        frameworksList.add("Spring MVC");
        frameworksList.add("Struts 2");
        frameworksList.add("JSF 2");
        frameworksList.add("GWT");
        frameworksList.add("Play");
        frameworksList.add("Apache Wicket");
        return frameworksList;
    }

    public void deleteUser(String userid){
        {
            Session session = this.sessionFactory.openSession();
            User u1 = null;
            try{
                session.getTransaction().begin();
                UserPojo usr1 =(UserPojo) session.load(UserPojo.class,new Integer(Integer.parseInt(userid)));
                session.delete(usr1);
                session.getTransaction().commit();
            }
            catch (Exception exp) {
                System.out.println(exp);

            }
            finally{
                session.close();
            }
        }
    }
    public User updateUser(String userid){
        Session session = this.sessionFactory.openSession();
        User user1 = new User();
        try{
            UserPojo user =(UserPojo) session.load(UserPojo.class,new Integer(Integer.parseInt(userid)));
            user1.setId(user.getId());
            user1.setName(user.getName());
            user1.setPassword(user.getPassword());
            user1.setAddress(user.getAddress());
            user1.setCountry(user.getCountry());
            user1.setEmail(user.getEmail());
            user1.setNumber(user.getNumber());
            user1.setSex(user.getSex());
            user1.setFramework(populateDefaultModel());
        }
        catch (Exception exp) {
            System.out.println(exp);

        }
        finally{
            session.close();
        }
        return user1;
    }
}
