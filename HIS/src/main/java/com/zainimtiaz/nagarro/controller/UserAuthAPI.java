package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.Permission;
import com.zainimtiaz.nagarro.model.Role;
import com.zainimtiaz.nagarro.model.User;
import com.zainimtiaz.nagarro.service.UserService;
import com.zainimtiaz.nagarro.utill.APIUtil;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.PermissionWrapper;
import com.zainimtiaz.nagarro.wrapper.RoleWrapper;
import com.zainimtiaz.nagarro.wrapper.UserWrapper;
import com.zainimtiaz.nagarro.wrapper.request.AssignAuthoritiesRequestWrapper;
import com.zainimtiaz.nagarro.wrapper.request.UserPermissionRequestWrapper;
import com.zainimtiaz.nagarro.wrapper.request.UserRequestWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/*
 * @author    : Irfan Nasim
 * @Date      : 16-Apr-18
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.controller
 * @FileName  : UserAuthAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@RequestMapping("/user/auth")
@RestController
public class UserAuthAPI {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenStore tokenStore;


    private final Logger logger = LoggerFactory.getLogger(UserAuthAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    /**
     * @return Response with Admin detail.
     * @author Irfan Nasim
     * @description API will return admin detail.
     * @since 16-04-2018
     */
    @ApiOperation(httpMethod = "POST", value = "Admin LoggedIn",
            notes = "This method will return logged in Admin",
            produces = "application/json", nickname = "Logging In Admin",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Logged in Admin fetched", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public ResponseEntity<?> signIn(HttpServletRequest request,
                                    @RequestBody UserRequestWrapper loginReq) {

        String loggedInUser = request.getRemoteUser().toString();
        logger.info("Sign in up Admin requested by User Name: " + loggedInUser);
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("admin.login.error"));
        response.setResponseCode(ResponseEnum.ADMIN_LOGGEDIN_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            // get requested user
            User dbAdmin = userService.findByUsername(loggedInUser);

            if (!HISCoreUtil.isValidObject(dbAdmin)) {

                response.setResponseMessage(messageBundle.getString("admin.not.found"));
                response.setResponseCode(ResponseEnum.ADMIN_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("The Admin is not found...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            //if (HISCoreUtil.isValidObject(dbAdmin)) {
            if (BCrypt.checkpw(loginReq.getPassword(), dbAdmin.getPassword())) {
                UserWrapper userWrapper = userService.buildUserWrapper(dbAdmin);
                response.setResponseData(userWrapper);
                response.setResponseMessage(messageBundle.getString("admin.login.success"));
                response.setResponseCode(ResponseEnum.ADMIN_ACCESS_GRANTED.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Admin Logged in successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            //}
        } catch (Exception ex) {
            logger.error("Admin Logged In failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

   /* *//**
     * @return Response with System Authorities.
     * @author Irfan Nasim
     * @description API will return All Authorities like Roles and Permissions.
     * @since 20-04-2018
     */
    @ApiOperation(httpMethod = "GET", value = "All Systems Authorities",
            notes = "This API will return All Authorities like Roles and Permissions",
            produces = "application/json", nickname = "Authorities",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Authorities fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/authorities", method = RequestMethod.GET)
    public ResponseEntity<?> getRolesAndPermissions(HttpServletRequest request) {

        logger.info("Get Roles and Permissions api called...");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("role.permissions.error"));
        response.setResponseCode(ResponseEnum.ROLE_PERMISSION_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<Role> dbRoles = userService.getAllActiveRoles();
            List<Permission> dbPermissions = userService.getAllActivePermissionsByIndicator('M');//getAllActivePermissions();
            Map<String, Object> returnValues = new LinkedHashMap<>();
            if (HISCoreUtil.isListEmpty(dbRoles)) {
                response.setResponseMessage(messageBundle.getString("role.permissions.not.found.error"));
                response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("Role and Permission not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            List<RoleWrapper> allRolesAndPermissions = APIUtil.buildRoleWrapper(dbRoles);
            List<PermissionWrapper> allPermissions = APIUtil.buildPermissionWrapper(dbPermissions);
            if (!HISCoreUtil.isListEmpty(allRolesAndPermissions)) {
                returnValues.put("allRoleAndPermissions", allRolesAndPermissions);
                returnValues.put("allPermissions", allPermissions);
                response.setResponseMessage(messageBundle.getString("role.permissions.success"));
                response.setResponseCode(ResponseEnum.ROLE_PERMISSION_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("All roles and permission fetched successfully");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("Get Roles and Permissions failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "All and User's all Permissions",
            notes = "This API will return All Authorities like Roles and Permissions",
            produces = "application/json", nickname = "UserPermissions",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Permissions fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/userDBPermissions", method = RequestMethod.GET)
    public ResponseEntity<?> getUserDashBoardPermissions(HttpServletRequest request) {
        logger.info("Get User's and all Permissions api called...");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.permissions.error"));
        response.setResponseCode(ResponseEnum.ROLE_PERMISSION_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {
            //below permissions are related to dashboard user's related
            List<Permission> userDBPermissions = userService.getAllActivePermissionsByIndicator('D');
            if (HISCoreUtil.isListEmpty(userDBPermissions)) {
                response.setResponseMessage(messageBundle.getString("user.permissions.not.found.error"));
                response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("User's Permissions not found.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else{
                response.setResponseMessage(messageBundle.getString("role.permissions.success"));
                response.setResponseCode(ResponseEnum.USER_PERMISSION_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(userDBPermissions);
                logger.info("All Permission fetched successfully");
            }

            /*List<RoleWrapper> allRolesAndPermissions = APIUtil.buildRoleWrapper(dbRoles);
            List<PermissionWrapper> allPermissions = APIUtil.buildPermissionWrapper(dbPermissions);
            if (!HISCoreUtil.isListEmpty(allRolesAndPermissions)) {
                returnValues.put("allRoleAndPermissions", allRolesAndPermissions);
                returnValues.put("allPermissions", allPermissions);
                response.setResponseMessage(messageBundle.getString("role.permissions.success"));
                response.setResponseCode(ResponseEnum.ROLE_PERMISSION_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("All roles and permission fetched successfully");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }*/
        } catch (Exception ex) {
            logger.error("Get Permissions failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation(httpMethod = "GET", value = "User's granted Permissions",
            notes = "This API will return user's All Granted Permissions",
            produces = "application/json", nickname = "UserPermissions",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Permissions fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/userPermissions/{selectedUserId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserPermissions(@PathVariable("selectedUserId") long selectedUserId) {
        logger.info("Get User's granted api called...");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.permissions.error"));
        response.setResponseCode(ResponseEnum.USER_PERMISSION_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {
            //below permissions are related to dashboard user's related
            List<PermissionWrapper> userPermissions = userService.getUserGrantedPermissions(selectedUserId);
            if (HISCoreUtil.isListEmpty(userPermissions)) {
                response.setResponseMessage(messageBundle.getString("user.permissions.not.found.error"));
                response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("User's Permissions not found.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else{
                response.setResponseMessage(messageBundle.getString("user.permissions.success"));
                response.setResponseCode(ResponseEnum.USER_PERMISSION_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(userPermissions);
                logger.info("User's Permissions fetched successfully");
            }

            /*List<RoleWrapper> allRolesAndPermissions = APIUtil.buildRoleWrapper(dbRoles);
            List<PermissionWrapper> allPermissions = APIUtil.buildPermissionWrapper(dbPermissions);
            if (!HISCoreUtil.isListEmpty(allRolesAndPermissions)) {
                returnValues.put("allRoleAndPermissions", allRolesAndPermissions);
                returnValues.put("allPermissions", allPermissions);
                response.setResponseMessage(messageBundle.getString("role.permissions.success"));
                response.setResponseCode(ResponseEnum.ROLE_PERMISSION_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("All roles and permission fetched successfully");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }*/
        } catch (Exception ex) {
            logger.error("Get Roles and Permissions failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     @ApiOperation(httpMethod = "GET", value = "Admin LoggedIn",
            notes = "This method will return logged in User",
            produces = "application/json", nickname = "Logging In ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Logged in Admin fetched", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/loggedInUser", method = RequestMethod.GET)
    public ResponseEntity<?> getLoggedInUser(HttpServletRequest request, Principal principal) {
        logger.info("LoggedIn User API - getLoggedInUser API initiated.");
        String name = principal.getName();

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("admin.loggedIn.fetched.error"));
        response.setResponseCode(ResponseEnum.ADMIN_LOGGEDIN_FETCHED_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (HISCoreUtil.isValidObject(name)) {
                logger.info("LoggedIn User API - fetching user from DB.");
                User user = userService.findByUsername(name);
                if (HISCoreUtil.isValidObject(user)) {
                    logger.info("LoggedIn User API - user successfully fetched...");
                    UserWrapper userWrapper = userService.buildLoggedInUserWrapper(user);

                    response.setResponseMessage(messageBundle.getString("admin.loggedIn.fetched.success"));
                    response.setResponseCode(ResponseEnum.ADMIN_LOGGEDIN_FETCHED_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    response.setResponseData(userWrapper);

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        } catch (Exception ex) {
            logger.error("LoggedIn User API - getLoggedInUser failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "Admin Logged out ",
            notes = "This method will Log out the User",
            produces = "application/json", nickname = "Logging Out ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User Logout success", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logOutUser(HttpServletRequest request) {
        logger.info("LogoutUser API initiated...");

        String authHeader = request.getHeader("Authorization");
        logger.info("Checking Request Header...:" + authHeader);

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.logout.error"));
        response.setResponseCode(ResponseEnum.USER_LOGGED_OUT_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(authHeader);

        try {
            if (!HISCoreUtil.isNull(authHeader)) {
                String tokenValue = authHeader.replace("Bearer", "").trim();
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                tokenStore.removeAccessToken(accessToken);

                response.setResponseMessage(messageBundle.getString("user.logout.success"));
                response.setResponseCode(ResponseEnum.USER_LOGGED_OUT_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);

                logger.info("User logging out ...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("logOutUser failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @return Response with status of Role & Permission.
     * @author Irfan Nasim
     * @description API will return status of Role & Permission creation.
     * @since 27-04-2018
     */
    @ApiOperation(httpMethod = "POST", value = "Create Role & Permission",
            notes = "This method will Create Role & Permission",
            produces = "application/json", nickname = "Create Role & Permission",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Role & Permission successfully created", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public ResponseEntity<?> addRole(HttpServletRequest request,
                                                  @RequestBody RoleWrapper createRequest) {

        logger.info("addRole API called...");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("role.add.error"));
        response.setResponseCode(ResponseEnum.ROLE_ADD_SUCCESS.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (HISCoreUtil.isNull(createRequest.getName())) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Create Role insufficient params");
            }


                Role alreadyExist = userService.getRoleByName(createRequest.getName());
                if (HISCoreUtil.isValidObject(alreadyExist)) {
                    response.setResponseMessage(messageBundle.getString("role.add.already-found.error"));
                    response.setResponseCode(ResponseEnum.ROLE_ALREADY_EXIST_ERROR.getValue());
                    response.setResponseStatus(ResponseEnum.ERROR.getValue());
                    response.setResponseData(null);
                    logger.error("Role already exist with the same name...");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                Role savedRole = userService.saveRole(createRequest);
                if (HISCoreUtil.isValidObject(savedRole)) {
                    response.setResponseData(null);
                    response.setResponseMessage(messageBundle.getString("role.add.success"));
                    response.setResponseCode(ResponseEnum.ROLE_ADD_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("Role created successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

        } catch (Exception ex) {
            logger.error("addRole failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    /**
//     * @return Response with status of  Role & Permission update.
//     * @author Irfan Nasim
//     * @description API will return status of Role & Permission update.
//     * @since 27-04-2018
//     */
//    @ApiOperation(httpMethod = "POST", value = "Update Role & Permission",
//            notes = "This method will Update Role & Permission",
//            produces = "application/json", nickname = "Update Role & Permission",
//            response = GenericAPIResponse.class, protocols = "https")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Role & Permission successfully updated", response = GenericAPIResponse.class),
//            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public ResponseEntity<?> updateRoleAndPermission(HttpServletRequest request,
//                                                     @RequestBody RoleAndPermissionUpdateRequest updateRequest) {
//
//        logger.info("updateRoleAndPermission API called...");
//        GenericAPIResponse response = new GenericAPIResponse();
//        response.setResponseMessage(messageBundle.getString("role.add.error"));
//        response.setResponseCode(ResponseEnum.ROLE_ADD_SUCCESS.getValue());
//        response.setResponseStatus(ResponseEnum.ERROR.getValue());
//        response.setResponseData(null);
//
//        try {
//
//        } catch (Exception ex) {
//            logger.error("updateRoleAndPermission failed.", ex.fillInStackTrace());
//            response.setResponseStatus(ResponseEnum.ERROR.getValue());
//            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
//            response.setResponseMessage(messageBundle.getString("exception.occurs"));
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
    @ApiOperation(httpMethod = "GET", value = "All Systems Authorities",
            notes = "This API will return All Authorities like Roles and Permissions",
            produces = "application/json", nickname = "Authorities",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Authorities fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public ResponseEntity<?> getPermissionByRole(HttpServletRequest request,
                                                    @RequestParam("name") String name) {

        logger.info("Get Permissions by Role api called...");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("permission.error"));
        response.setResponseCode(ResponseEnum.ROLE_PERMISSION_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            Role role = userService.getRoleByName(name);

            if (!HISCoreUtil.isValidObject(role)) {
                response.setResponseMessage(messageBundle.getString("role.not.found.error"));
                response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("Role not found ");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            List<Permission> permissions = userService.getPermissionByRole(role.getId());

            if (HISCoreUtil.isListEmpty(permissions)) {
                response.setResponseMessage(messageBundle.getString("permission.not.found.error"));
                response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("Permission not found against" + role.getName());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            List<PermissionWrapper> allPermissions = APIUtil.buildPermissionWrapper(permissions);
            if (!HISCoreUtil.isListEmpty(allPermissions)) {

                response.setResponseMessage(messageBundle.getString("permission.success"));
                response.setResponseCode(ResponseEnum.ROLE_PERMISSION_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(allPermissions);
                logger.info("permission fetched successfully");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("Get Permissions failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @return Response with Save Authorities.
     * @author Irfan Nasim
     * @description API will return response Authorities assigned or not.
     * @since 20-04-2018
     */
    @ApiOperation(httpMethod = "POST", value = "Assigned Authorities to Roles",
            notes = "This API will return Response Authorities are assigned or not",
            produces = "application/json", nickname = "Assignment of Authorities to Roles",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Authorities Assigned to roles successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/assignAuthorities", method = RequestMethod.POST)
    public ResponseEntity<?> assignAuthoritiesToRoles(HttpServletRequest request,
                                                      @RequestBody AssignAuthoritiesRequestWrapper authRequest) {

        logger.info("Assign Authorities to Roles api called...");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("role.permission.assign.error"));
        response.setResponseCode(ResponseEnum.ROLE_PERMISSION_ASSIGN_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (HISCoreUtil.isNull(authRequest.getSelectedRole())) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("assignAuthoritiesToRoles insufficient params");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Boolean isRolePermissionAssigned = userService.assignPermissionsToRole(authRequest);
            if (isRolePermissionAssigned) {
                response.setResponseMessage(messageBundle.getString("role.permission.assigned.success"));
                response.setResponseCode(ResponseEnum.ROLE_PERMISSION_ASSIGN_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);
                logger.info("assignAuthoritiesToRoles assigned successfully");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("Assign Authorities to Roles api failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation(httpMethod = "POST", value = "Assignment of Permissions to User",
            notes = "This API will return Response Permissions are assigned or not",
            produces = "application/json", nickname = "Assignment of Permissions to User",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Permissions Assigned to user successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/assignUserPermissions", method = RequestMethod.POST)
    public ResponseEntity<?> assignPermissionsToUser(@RequestBody UserPermissionRequestWrapper userPermRequest) {

        logger.info("Assign Authorities to Users api called...");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.permission.assign.error"));
        response.setResponseCode(ResponseEnum.USER_PERMISSION_ASSIGN_SUCCESS.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (userPermRequest.getUserId()==null) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("assignPermissionsToUser insufficient params");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Boolean isUserPermissionAssigned = userService.assignPermissionsToUser(userPermRequest);
            if (isUserPermissionAssigned) {
                response.setResponseMessage(messageBundle.getString("user.permission.assigned.success"));
                response.setResponseCode(ResponseEnum.USER_PERMISSION_ASSIGN_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);
                logger.info("assignPermissionsToUser assigned successfully");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("Assign Permissions to User api failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
