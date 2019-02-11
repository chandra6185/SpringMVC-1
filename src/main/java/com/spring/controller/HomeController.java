package com.spring.controller;

import com.spring.forms.User;
import com.spring.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
public class HomeController {

    private UserServiceImpl userService;

    @Autowired(required=true)
    @Qualifier(value="userService")
    public void setUserService(UserServiceImpl userService1){
        this.userService = userService1;
    }

    @RequestMapping(value = "/users/{userid}/delete", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("userid") String userid) {
        userService.deleteUser(userid);
        return "redirect:/users";

    }

    @RequestMapping(value = "/users/{userid}/update", method = RequestMethod.GET)
    public String updateUser(@PathVariable("userid") String userid,Model model) {
       User user = userService.updateUser(userid);
        model.addAttribute("userForm",user);
        populateDefaultModel(model);
        return "userform";

    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String showAllUsers(Model model) {
       List<User> userList =  userService.retrieveUsers();
        System.out.println("sizeeeeeeeeeeeeeeeeeeeeee.................."+userList.size());
        model.addAttribute("users",userList);
        return "list";

    }
//    @RequestMapping(value = "/users", method = RequestMethod.POST)
//    public String UpdateUser(@ModelAttribute("userForm") User user,Model model) {
//        model.addAttribute("message",userService.updateUser(user));
//        return "redirect:/users";
//    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String saveOrUpdateUser(@ModelAttribute("userForm") User user,Model model) {
       model.addAttribute("message",userService.save(user));
        return "redirect:/users";
    }


    @RequestMapping(value = "",method = RequestMethod.GET)
    public String sayHello(Model map){
        User user = new User();
        user.setName("mkyong123");
        user.setEmail("test@gmail.com");
        user.setAddress("abc 88");
        user.setNewsletter(true);
        user.setSex("M");
        user.setFramework(new ArrayList<String>(Arrays.asList("Spring MVC", "GWT")));
        user.setSkill(new ArrayList<String>(Arrays.asList("Spring", "Grails", "Groovy")));
        user.setCountry("SG");
        user.setNumber(2);
        map.addAttribute("userForm", user);

        populateDefaultModel(map);
       return "userform";
    }
    private void populateDefaultModel(Model model) {

        List<String> frameworksList = new ArrayList<String>();
        frameworksList.add("Spring MVC");
        frameworksList.add("Struts 2");
        frameworksList.add("JSF 2");
        frameworksList.add("GWT");
        frameworksList.add("Play");
        frameworksList.add("Apache Wicket");
        model.addAttribute("frameworkList", frameworksList);

        Map<String, String> skill = new LinkedHashMap<String, String>();
        skill.put("Hibernate", "Hibernate");
        skill.put("Spring", "Spring");
        skill.put("Struts", "Struts");
        skill.put("Groovy", "Groovy");
        skill.put("Grails", "Grails");
        model.addAttribute("javaSkillList", skill);

        List<Integer> numbers = new ArrayList<Integer>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        model.addAttribute("numberList", numbers);

        Map<String, String> country = new LinkedHashMap<String, String>();
        country.put("US", "United Stated");
        country.put("CN", "China");
        country.put("SG", "Singapore");
        country.put("MY", "Malaysia");
        model.addAttribute("countryList", country);

    }
}
