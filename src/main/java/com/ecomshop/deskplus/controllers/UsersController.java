package com.ecomshop.deskplus.controllers;

import com.ecomshop.deskplus.services.UserRoles;
import com.ecomshop.deskplus.services.UserServices;
import com.ecomshop.deskplus.web.rest.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * Author: Sheik Syed Ali
 * Date: 24 Sep 2021
 */
@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    private final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserServices userServices;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public Response authenticate(@RequestBody LoginRequest loginRequest) throws Exception {
        return userServices.authenticate(loginRequest);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @RolesAllowed({UserRoles.ROLE_ADMIN})
    public Response createUser(@RequestBody CreateUserRequest createUserRequest){
        return userServices.createUser(createUserRequest);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RolesAllowed({UserRoles.ROLE_ADMIN})
    public Response updateUser(@RequestBody CreateUserRequest createUserRequest){
        return null;
    }



    @RequestMapping(value = "/teams/create", method = RequestMethod.POST)
    @RolesAllowed({UserRoles.ROLE_ADMIN})
    public Response createTeam(@RequestBody CreateTemRequest createTemRequest){
        return userServices.createTeam(createTemRequest);
    }

    @RequestMapping(value = "/teams/update", method = RequestMethod.POST)
    @RolesAllowed({UserRoles.ROLE_ADMIN})
    public Response updateTeam(@RequestBody CreateTemRequest updateTeamRequest){
        return userServices.updateTeam(updateTeamRequest);
    }

    @RequestMapping(value = "/teams/delete", method = RequestMethod.POST)
    @RolesAllowed({UserRoles.ROLE_ADMIN})
    public Response deleteTeam(@RequestBody DeleteTeamRequest deleteTeamRequest){
        return userServices.deleteTeam(deleteTeamRequest);
    }







    @GetMapping
    public String testMe(){
        logger.info("Hellow Sheik test logging...");
        return "Hello Sheik";
    }


    @GetMapping
    @RequestMapping("/role")
    public String role() throws Exception{


//        mailService.sendAccountActivationMail("Sheik Syed", "sheiksyeda55@gmail.com");


        return "Role Test";
    }

    @GetMapping
    @RequestMapping("/admin")
    public String admin(){
        return "Only Admin";
    }

}
