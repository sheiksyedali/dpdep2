package com.ecomshop.deskplus.services;


import com.ecomshop.deskplus.models.*;
import com.ecomshop.deskplus.repositories.*;
import com.ecomshop.deskplus.security.JWTUtil;
import com.ecomshop.deskplus.services.utils.DateTimeUtil;
import com.ecomshop.deskplus.web.rest.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Author: Sheik Syed Ali
 * Date: 26 Sep 2021
 */
@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private RegistrationsRepository registrationsRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TeamsRepository teamsRepository;

    @Autowired
    private TeamMembersRepository teamMembersRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthUserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private MailService mailService;

    @Override
    public UsersEntity createUser(String firstName, String lastName, String email, String password, List<RolesEntity> roles, boolean isAccountOwner, RegistrationsEntity registrationsEntity, UsersEntity createdBy) {
        UsersEntity userEntity = new UsersEntity();
        userEntity.setFirst_name(firstName);
        userEntity.setLast_name(lastName);
        userEntity.setEmail(email);
        userEntity.setPassword(password);
        userEntity.setRoles(roles);
        userEntity.setIs_account_owner(isAccountOwner);
        userEntity.setRegistration(registrationsEntity);
        if(createdBy != null){
            userEntity.setCreated_by(createdBy.getUser_id());
        }
        userEntity.setCreated_time(new Date());
        userEntity = usersRepository.saveAndFlush(userEntity);
        return userEntity;
    }

    @Override
    public Map<String, Object> getRegistrationDetails(SubscriptionsEntity subscriptionsEntity) {
        Map<String, Object> subscription = null;
        if(subscriptionsEntity != null){
            subscription = new HashMap<>();
            Date trialStartDate = subscriptionsEntity.getTrail_start_date();
            Date trialEndDate = subscriptionsEntity.getTrail_end_date();
            Date subsStartDate = subscriptionsEntity.getSub_start_date();
            Date subEndDate = subscriptionsEntity.getSub_end_date();
            subscription.put("is-in-trial", subscriptionsEntity.isIs_in_trail());
            subscription.put("trial-start", trialStartDate);
            subscription.put("trial-end", trialEndDate);
            int trialDays = (trialEndDate == null) ? -1 : DateTimeUtil.daysBetweenDates(trialStartDate, trialEndDate);
            subscription.put("trial-days", trialDays);
            int currentTrailDay = (trialStartDate == null) ? -1 : DateTimeUtil.daysBetweenDates(trialStartDate, new Date());
            subscription.put("trial-current-day", (currentTrailDay == 0) ? 1 : currentTrailDay);
            subscription.put("stripe-sub-id", subscriptionsEntity.getStripe_subscription_id());
            subscription.put("sub-start-date", subsStartDate);
            subscription.put("sub-end-date", subEndDate);
            int curSubDay = DateTimeUtil.daysBetweenDates(subsStartDate, new Date());
            subscription.put("sub-current-day", (curSubDay == 0) ? 1 : curSubDay);
            int subDays = (subsStartDate == null || subEndDate == null) ? -1 : DateTimeUtil.daysBetweenDates(subsStartDate, subEndDate);
            subscription.put("sub-total-days", subDays);

            PlansEntity planEntity = subscriptionsEntity.getPlan();
            Map<String, Object> planDetails = new HashMap<>();
            planDetails.put("plan-name", planEntity.getPlan_name());
            planDetails.put("stripe-plan-id", planEntity.getPlan_stripe_id());
            ProductsEntity productEntity = planEntity.getProduct();
            planDetails.put("product", productEntity.getProduct_name());

            subscription.put("plan", planDetails);
            RegistrationsEntity registrationsEntity = subscriptionsEntity.getRegistration();
            subscription.put("reg-id", registrationsEntity.getReg_unique_id());
        }
        return subscription;
    }

    @Override
    public Response createUser(CreateUserRequest createUserRequest){
        Long createdById = createUserRequest.getCreatedBy();
        String regId = createUserRequest.getRegId();
        String firstName = createUserRequest.getFirstName();
        String lastName = createUserRequest.getLastName();
        String email = createUserRequest.getEmail();
        String password = createUserRequest.getPassword();
        UsersEntity createdBy = usersRepository.getUser(createdById, regId);
        if(createdBy == null){
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_INVALID_CREATED_BY_USER, ResponseConstants.ERR_INVALID_CREATED_BY_USER_MSG);
        }

        UsersEntity usersEntity = usersRepository.findByEmail(email);
        if(usersEntity != null){
            return new FailureResponse(ResponseConstants.ERR_USER_EXIST, ResponseConstants.ERR_USER_EXIST_MSG);
        }

        RegistrationsEntity registrationsEntity = registrationsRepository.getRegistrationByRegUniqueId(regId);
        RolesEntity rolesEntity = rolesRepository.findByRole(createUserRequest.getRole());
        if(rolesEntity == null){
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_INVALID_ROLE, ResponseConstants.ERR_INVALID_ROLE_MSG);
        }
        List<RolesEntity> roles = new ArrayList<>();
        roles.add(rolesEntity);

        UsersEntity user = createUser(firstName, lastName, email, password, roles, false, registrationsEntity, createdBy);
        Map<String, Object> data = new HashMap<>();
        data.put("user-id", user.getUser_id());
        data.put("email", user.getEmail());
        mailService.sendUserCreatedMail(firstName, email, password);
        return ResponseManager.getInstance().createSuccessDetailedResponse(data);
    }

    @Override
    public Response createTeam(CreateTemRequest createTemRequest) {
        Long createdById = createTemRequest.getCreatedBy();
        String regId = createTemRequest.getRegId();
        List<Long> members = createTemRequest.getMembers();
        String teamName = createTemRequest.getTeamName();
        UsersEntity createdBy = usersRepository.getUser(createdById, regId);
        if(createdBy == null){
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_INVALID_CREATED_BY_USER, ResponseConstants.ERR_INVALID_CREATED_BY_USER_MSG);
        }

        List<UsersEntity> membersEntity = usersRepository.findAllById(members);
        if(membersEntity.size() != members.size()){
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_INVALID_TEAM_MEMBERS, ResponseConstants.ERR_INVALID_TEAM_MEMBERS_MSG);
        }

        TeamsEntity existTeam = teamsRepository.findByTeamName(teamName, regId);
        if(existTeam != null){
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_TEAM_ALREADY_EXIST, ResponseConstants.ERR_TEAM_ALREADY_EXIST_MSG);
        }

        TeamsEntity teamEntity = new TeamsEntity();
        teamEntity.setTeam_name(teamName);
        teamEntity.setTeam_description(createTemRequest.getTeamDescription());
        Date createdTime = new Date();
        teamEntity.setTeam_creation_time(createdTime);
        teamEntity.setTeam_last_updated_time(createdTime);
        teamEntity.setCreatedUser(createdBy);
        teamEntity.setUpdatedUser(createdBy);

        teamEntity = teamsRepository.saveAndFlush(teamEntity);

        for(UsersEntity member : membersEntity){
            TeamMembersEntity teamMembersEntity = new TeamMembersEntity();
            teamMembersEntity.setTeam(teamEntity);
            teamMembersEntity.setUser(member);
            teamMembersRepository.saveAndFlush(teamMembersEntity);
        }
        return ResponseManager.getInstance().createSuccessResponse(ResponseConstants.TEAM_CREATED_SUCCESSFULLY);
    }

    @Override
    public Response updateTeam(CreateTemRequest updateTeamRequest) {
        Long createdById = updateTeamRequest.getCreatedBy();
        String regId = updateTeamRequest.getRegId();
        Long teamId = updateTeamRequest.getTeamId();
        List<Long> members = updateTeamRequest.getMembers();

        UsersEntity updatedBy = usersRepository.getUser(createdById, regId);
        if(updatedBy == null) {
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_INVALID_CREATED_BY_USER, ResponseConstants.ERR_INVALID_CREATED_BY_USER_MSG);
        }

        Optional<TeamsEntity> teamsEntityOpt = teamsRepository.findById(teamId);
        if(teamsEntityOpt.isEmpty()){
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_TEAM_NOT_FOUND, ResponseConstants.ERR_TEAM_NOT_FOUND_MSG);
        }

        TeamsEntity teamEntity = teamsEntityOpt.get();
        teamEntity.setTeam_last_updated_time(new Date());
        teamEntity.setUpdatedUser(updatedBy);
        teamEntity = teamsRepository.saveAndFlush(teamEntity);

        teamMembersRepository.deleteTeamMembers(teamId);

        List<UsersEntity> membersEntity = usersRepository.findAllById(members);
        for(UsersEntity member : membersEntity) {
            TeamMembersEntity teamMembersEntity = new TeamMembersEntity();
            teamMembersEntity.setTeam(teamEntity);
            teamMembersEntity.setUser(member);
            teamMembersRepository.saveAndFlush(teamMembersEntity);
        }
        return ResponseManager.getInstance().createSuccessResponse(ResponseConstants.TEAM_UPDATED_SUCCESSFULLY);
    }

    @Override
    public Response deleteTeam(DeleteTeamRequest deleteTeamRequest) {
        Long deletedByUserId = deleteTeamRequest.getDeletedBy();
        Long teamId = deleteTeamRequest.getTeamId();
        String regId = deleteTeamRequest.getRegId();

        UsersEntity deletedBy = usersRepository.getUser(deletedByUserId, regId);
        if(deletedBy == null) {
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_INVALID_CREATED_BY_USER, ResponseConstants.ERR_INVALID_CREATED_BY_USER_MSG);
        }

        Optional<TeamsEntity> teamsEntityOpt = teamsRepository.findById(teamId);
        if(teamsEntityOpt.isEmpty()){
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_TEAM_NOT_FOUND, ResponseConstants.ERR_TEAM_NOT_FOUND_MSG);
        }

        teamMembersRepository.deleteTeamMembers(teamId);
        teamsRepository.deleteById(teamId);

        return ResponseManager.getInstance().createSuccessResponse(ResponseConstants.TEAM_DELETED_SUCCESSFULLY);
    }


    public Response signup(SignupRequest signupRequest) {
        String firstName = signupRequest.getFirstName();
        String email = signupRequest.getEmail();
        String password = signupRequest.getPassword();

        String jwt = null;
        try{
//            UsersEntity usersEntity = usersRepository.findByEmail(email);
//            if(usersEntity != null){
//                return new FailureResponse(ResponseConstants.ERR_USER_EXIST, ResponseConstants.ERR_USER_EXIST_MSG);
//            }
//            OrganizationsEntity organizationsEntity = new OrganizationsEntity();
//            organizationsEntity.setOrg_user_email(email);
//            organizationsEntity = organizationsRepository.saveAndFlush(organizationsEntity);
//
//            RolesEntity adminRoleEntity = rolesRepository.findByRole(UserRoles.ROLE_ADMIN);
//            List<RolesEntity> roles = new ArrayList<>();
//            roles.add(adminRoleEntity);
//
//            UsersEntity userEntity = new UsersEntity();
//            userEntity.setFirst_name(firstName);
//            userEntity.setLast_name(signupRequest.getLastName());
//            userEntity.setEmail(email);
//            userEntity.setPassword(password);
//            userEntity.setRoles(roles);
//            userEntity.setOrganization(organizationsEntity);
//            userEntity = usersRepository.saveAndFlush(userEntity);
//
//            jwt = authenticate(email, password);

        } catch (Exception ex){
            ex.printStackTrace();
            return new FailureResponse(ResponseConstants.ERR_SIGNUP_FAILED, ResponseConstants.ERR_SIGNUP_FAILED_MSG);
        }

//        if(jwt == null){
//            return new FailureResponse(ResponseConstants.ERR_SIGNUP_AUTH_FAILED, ResponseConstants.ERR_SIGNUP_AUTH_FAILED_MSG);
//        }

//        Map<String, Object> data = new HashMap<>();
//        data.put("jwt", jwt);
//        SuccessDetailResponse successResponse = new SuccessDetailResponse();
//        successResponse.setData(data);

//        mailService.sendAccountActivationMail(firstName, email);

//        return successResponse;
        return  null;
    }

    @Override
    public Response authenticate(LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        UsersEntity usersEntity = usersRepository.findByEmail(email);
        if(usersEntity == null){
            return new FailureResponse(ResponseConstants.ERR_LOGIN_USER_NOT_FOUND, ResponseConstants.ERR_LOGIN_USER_NOT_FOUND_MSG);
        }

        String jwt = null;
        try{
            jwt = authenticate(email, password);
        } catch (Exception ex){
            ex.printStackTrace();
            return new FailureResponse(ResponseConstants.ERR_LOGIN_FAILED, ResponseConstants.ERR_LOGIN_FAILED_MSG);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("jwt", jwt);
        SuccessDetailResponse successResponse = new SuccessDetailResponse(data);
        successResponse.setData(data);

        return successResponse;
    }

    @Override
    public String authenticate(String email, String password) throws Exception{
        String jwt = null;
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }catch (Exception ex){
            ex.printStackTrace();
            throw new Exception("Invalid Credentials");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        jwt = jwtUtil.generateToken(userDetails);
        return jwt;
    }




}
