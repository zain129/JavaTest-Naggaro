package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.User;
import com.zainimtiaz.nagarro.service.StaffService;
import com.zainimtiaz.nagarro.service.UserService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.DepartmentWrapper;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.request.StaffRequestWrapper;
import com.zainimtiaz.nagarro.wrapper.response.AdminDashboardDataResponseWrapper;
import com.zainimtiaz.nagarro.wrapper.response.StaffResponseWrapper;
import com.zainimtiaz.nagarro.wrapper.response.StaffWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.IntStream;


@RestController
@RequestMapping("/user")
public class StaffAPI {

    //
    @Autowired
    private UserService userService;
    @Autowired
    private StaffService staffService;

    //
    private final Logger logger = LoggerFactory.getLogger(StaffAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");
    //    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");
//
//    @ApiOperation(httpMethod = "GET", value = "Admin LoggedIn",
//            notes = "This method will return logged in User",
//            produces = "application/json", nickname = "Logging In ",
//            response = GenericAPIResponse.class, protocols = "https")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Logged in Admin fetched", response = GenericAPIResponse.class),
//            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
//    @RequestMapping(value = "/loggedInUser", method = RequestMethod.GET)
//    public ResponseEntity<?> getLoggedInUser(HttpServletRequest request, Principal principal) {
//        logger.info("LoggedIn User API - getLoggedInUser API initiated.");
//        String name = principal.getName();
//
//        GenericAPIResponse response = new GenericAPIResponse();
//        response.setResponseMessage(messageBundle.getString("admin.loggedIn.fetched.error"));
//        response.setResponseCode(ResponseEnum.ADMIN_LOGGEDIN_FETCHED_FAILED.getValue());
//        response.setResponseStatus(ResponseEnum.ERROR.getValue());
//        response.setResponseData(null);
//
//        try {
//            if (HISCoreUtil.isValidObject(name)) {
//                logger.info("LoggedIn User API - fetching user from DB.");
//                User user = userService.findByUserName(name);
//                if (HISCoreUtil.isValidObject(user)) {
//                    logger.info("LoggedIn User API - user successfully fetched...");
//                    UserWrapper userWrapper = userService.buildLoggedInUserWrapper(user);
//
//                    response.setResponseMessage(messageBundle.getString("admin.loggedIn.fetched.success"));
//                    response.setResponseCode(ResponseEnum.ADMIN_LOGGEDIN_FETCHED_SUCCESS.getValue());
//                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
//                    response.setResponseData(userWrapper);
//
//                    return new ResponseEntity<>(response, HttpStatus.OK);
//                }
//            }
//        } catch (Exception ex) {
//            logger.error("LoggedIn User API - getLoggedInUser failed.", ex.fillInStackTrace());
//            response.setResponseStatus(ResponseEnum.ERROR.getValue());
//            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
//            response.setResponseMessage(messageBundle.getString("exception.occurs"));
//
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//
//
    @ApiOperation(httpMethod = "POST", value = "Create Staff",
            notes = "This method will Create Staff",
            produces = "application/json", nickname = "Create Staff",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Staff successfully created", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(HttpServletRequest request,
                                        @RequestBody StaffRequestWrapper createRequest) {

        long date = System.currentTimeMillis();
        logger.info("Create Staff API called..." + createRequest.getUserType());
        logger.info("Create Staff API called..." + createRequest.getUserName());
        createRequest.setCreatedOn(date);
        createRequest.setUpdatedOn(date);
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.add.error"));
        response.setResponseCode(ResponseEnum.USER_ADD_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (HISCoreUtil.isNull(createRequest.getUserType())) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);

                logger.error("Create User insufficient params");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (!HISCoreUtil.isNull(createRequest.getUserType())) {
                User alreadyExist = userService.findByUsername(createRequest.getUserName());

                if (HISCoreUtil.isValidObject(alreadyExist)) {
                    response.setResponseMessage(messageBundle.getString("user.add.already-found.error"));
                    response.setResponseCode(ResponseEnum.USER_ALREADY_EXIST_ERROR.getValue());
                    response.setResponseStatus(ResponseEnum.ERROR.getValue());
                    response.setResponseData(null);
                    logger.error("User already exist with the same name...");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                User savedUser = staffService.saveUser(createRequest);
                if (HISCoreUtil.isValidObject(savedUser)) {
                    response.setResponseData(savedUser);
                    response.setResponseMessage(messageBundle.getString("user.add.success"));
                    response.setResponseCode(ResponseEnum.USER_ADD_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("User created successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        } catch (Exception ex) {
            logger.error("Create User Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "Paginated Users",
            notes = "This method will return Paginated Users",
            produces = "application/json", nickname = "Get Paginated Users ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated Users fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
        logger.info("getAllUsers");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.not.found"));
        response.setResponseCode(ResponseEnum.USER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<User> allUsers = userService.getAllUsers();
            if (!HISCoreUtil.isListEmpty(allUsers)) {
                response.setResponseMessage(messageBundle.getString("user.fetched.success"));
                response.setResponseCode(ResponseEnum.USER_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(allUsers);
                logger.info("getAllUser Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get all User failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Paginated Users",
            notes = "This method will return Paginated Users",
            produces = "application/json", nickname = "Get Paginated Users ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated Users fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPaginatedUsers(HttpServletRequest request,
                                                  @PathVariable("page") int page,
                                                  @RequestParam(value = "pageSize",
                                                          required = false, defaultValue = "10") int pageSize) {
        logger.info("getAllUsers paginated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.not.found"));
        response.setResponseCode(ResponseEnum.USER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<StaffWrapper> userWrappers = staffService.findAllStaff(page, pageSize);
            int countUser = staffService.countAllStaff();
            if (!HISCoreUtil.isListEmpty(userWrappers)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (countUser > pageSize) {
                    int remainder = countUser % pageSize;
                    int totalPages = countUser / pageSize;
                    if (remainder > 0) {
                        totalPages = totalPages + 1;
                    }
                    pages = new int[totalPages];
                    pages = IntStream.range(0, totalPages).toArray();
                    currPage = page;
                    nextPage = (currPage + 1) != totalPages ? currPage + 1 : null;
                    prePage = currPage > 0 ? currPage : null;
                } else {
                    pages = new int[1];
                    pages[0] = 0;
                    currPage = 0;
                    nextPage = null;
                    prePage = null;
                }

                Map<String, Object> returnValues = new LinkedHashMap<>();
                returnValues.put("nextPage", nextPage);
                returnValues.put("prePage", prePage);
                returnValues.put("currPage", currPage);
                returnValues.put("pages", pages);
                returnValues.put("data", userWrappers);

                response.setResponseMessage(messageBundle.getString("user.fetched.success"));
                response.setResponseCode(ResponseEnum.USER_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("getAllPaginatedUser Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get all paginated User failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "PUT", value = "Update User ",
            notes = "This method will Update User",
            produces = "application/json", nickname = "Update User",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User successfully updated", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<?> updateUser(HttpServletRequest request,
                                        @PathVariable("id") Long id,
                                        @RequestBody StaffRequestWrapper createRequest) {


        long date = System.currentTimeMillis();
        logger.info("update User API called..." + createRequest.getUserType());
        logger.info("update User API called..." + createRequest.getUserName());
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.update.error"));
        response.setResponseCode(ResponseEnum.USER_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (!HISCoreUtil.isNull(createRequest.getUserType())) {
                User alreadyExistUser = staffService.findById(id);

                if (HISCoreUtil.isValidObject(alreadyExistUser)) {
                    logger.info("User founded...");
                    User userUpdated = staffService.updateStaffData(createRequest, alreadyExistUser);

                    if (HISCoreUtil.isValidObject(userUpdated)) {
                        logger.info("User saved...");
                        response.setResponseData(userUpdated);
                        response.setResponseMessage(messageBundle.getString("user.update.success"));
                        response.setResponseCode(ResponseEnum.USER_UPDATE_SUCCESS.getValue());
                        response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                        logger.info("User updated successfully...");

                        return new ResponseEntity<>(response, HttpStatus.OK);
                    }
                } else {
                    logger.info("User not found...");
                    response.setResponseMessage(messageBundle.getString("user.not.found"));
                    response.setResponseCode(ResponseEnum.USER_NOT_FOUND.getValue());
                    response.setResponseStatus(ResponseEnum.ERROR.getValue());
                    response.setResponseData(null);
                    logger.error("User not updated...");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            } else {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Update User insufficient params");
            }
        } catch (Exception ex) {
            logger.error("Update User Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "User",
            notes = "This method will return User on base of id",
            produces = "application/json", nickname = "Get Single User",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserById(HttpServletRequest request,
                                         @PathVariable("id") long id,
                                         @RequestParam(value = "userType", required = false) String userType)
    {


        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.not.found"));
        response.setResponseCode(ResponseEnum.USER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            StaffResponseWrapper user = this.staffService.findByIdAndResponse(id,userType);

            if (HISCoreUtil.isValidObject(user)) {
                response.setResponseData(user);
                response.setResponseMessage(messageBundle.getString("user.found"));
                response.setResponseCode(ResponseEnum.USER_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("User Found successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("user.not.found"));
                response.setResponseCode(ResponseEnum.USER_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                logger.info("User Not Found ...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("User Not Found", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete User",
            notes = "This method will Delete User on base of id",
            produces = "application/json", nickname = "Delete Single User",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User Deleted successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(HttpServletRequest request,
                                        @PathVariable("id") long id) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.delete.error"));
        response.setResponseCode(ResponseEnum.USER_NOT_DELETED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            User user = this.staffService.findById(id);
            if (HISCoreUtil.isValidObject(user)) {
                String result = staffService.deleteUser(user);
                if (result.equalsIgnoreCase("success")) {
                    response.setResponseData(user);
                    response.setResponseMessage(messageBundle.getString("user.delete.success"));
                    response.setResponseCode(ResponseEnum.USER_DELETED_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("User Deleted successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                else if(result.equalsIgnoreCase("hasChild")){
                    response.setResponseData(user);
                    response.setResponseMessage(messageBundle.getString("user.delete.failure"));
                    response.setResponseCode(ResponseEnum.USER_DELETE_FAILURE.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("User Deleted successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("user.not.found"));
                response.setResponseCode(ResponseEnum.USER_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                logger.info("User Not Found ...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("User Not Deleted", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Search User",
            notes = "This method will return User on base of search",
            produces = "application/json", nickname = "Search Users",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> searchUser(HttpServletRequest request,
                                        @PathVariable("page") int page,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                        @RequestParam(value = "name") String name,
                                        @RequestParam(value = "userType") String userType) {
        logger.info("search:" + userType);
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.not.found"));
        response.setResponseCode(ResponseEnum.USER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {
            //List<UserWrapper> userWrappers = userService.searchByNameOrEmailOrRole(name, email, role, page, pageSize);
            List<StaffWrapper> userWrappers = staffService.searchByNameOrRole(name.toLowerCase(), userType.toLowerCase(), page, pageSize);
            int countUser = staffService.countAllStaff();
            if (!HISCoreUtil.isListEmpty(userWrappers)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (countUser > pageSize) {
                    int remainder = countUser % pageSize;
                    int totalPages = countUser / pageSize;
                    if (remainder > 0) {
                        totalPages = totalPages + 1;
                    }
                    pages = new int[totalPages];
                    pages = IntStream.range(0, totalPages).toArray();
                    currPage = page;
                    nextPage = (currPage + 1) != totalPages ? currPage + 1 : null;
                    prePage = currPage > 0 ? currPage : null;
                } else {
                    pages = new int[1];
                    pages[0] = 0;
                    currPage = 0;
                    nextPage = null;
                    prePage = null;
                }

                Map<String, Object> returnValues = new LinkedHashMap<>();
                returnValues.put("nextPage", nextPage);
                returnValues.put("prePage", prePage);
                returnValues.put("currPage", currPage);
                returnValues.put("pages", pages);
                returnValues.put("data", userWrappers);

                response.setResponseMessage(messageBundle.getString("user.fetched.success"));
                response.setResponseCode(ResponseEnum.USER_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("searched User Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("searched User failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "User By Type",
            notes = "This method will return Users By Type",
            produces = "application/json", nickname = "Get Users By type ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Users fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public ResponseEntity<?> findUserByRole(HttpServletRequest request,
                                            @RequestParam(value = "name") String type) {

        logger.info("find User By Type..");
        logger.info("user type..." + type);
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.not.found"));
        response.setResponseCode(ResponseEnum.USER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (!HISCoreUtil.isNull(type)) {
                List<StaffResponseWrapper> staffResponseWrapper = staffService.findByRole(type);
                if (!HISCoreUtil.isListEmpty(staffResponseWrapper)) {
                    response.setResponseMessage(messageBundle.getString("user.fetched.success"));
                    response.setResponseCode(ResponseEnum.USER_FOUND.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    response.setResponseData(staffResponseWrapper);
                    logger.info("user on base of Type fetched successfully...");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Create User insufficient params");

            }
        } catch (Exception ex) {
            logger.error("user by role failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "SuperAdmin dashboard data",
            notes = "This method will return super admin dashboard data",
            produces = "application/json", nickname = "Dashboard Data",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SuperAdmin Dashboard Data fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ResponseEntity<?> getAdminDashboardData(HttpServletRequest request, Principal principal) {
        logger.info("Administrator Dashboard Data - getAdminDashboardData API initiated.");
        String name = principal.getName();

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("admin.dashboard.data.fetched.error"));
        response.setResponseCode(ResponseEnum.ADMIN_DASHBOARD_FETCHED_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (HISCoreUtil.isValidObject(name)) {
                logger.info("Administrator Dashboard Data - fetching user from DB.");
                User user = userService.findByUserName(name);
                if (HISCoreUtil.isValidObject(user)) {
                    logger.info("Administrator Dashboard Data - user successfully fetched...");
                    AdminDashboardDataResponseWrapper adminData = userService.buildAdminDashboardData();
                    if (HISCoreUtil.isValidObject(adminData)) {
                        response.setResponseMessage(messageBundle.getString("admin.dashboard.data.fetched.success"));
                        response.setResponseCode(ResponseEnum.ADMIN_DASHBOARD_FETCHED_SUCCESS.getValue());
                        response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                        response.setResponseData(adminData);

                        return new ResponseEntity<>(response, HttpStatus.OK);
                    } else {
                        response.setResponseMessage(messageBundle.getString("admin.dashboard.data.fetched.error"));
                        response.setResponseCode(ResponseEnum.ADMIN_DASHBOARD_FETCHED_FAILED.getValue());
                        response.setResponseStatus(ResponseEnum.ERROR.getValue());
                        response.setResponseData(adminData);

                        return new ResponseEntity<>(response, HttpStatus.OK);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Administrator Dashboard Data - getAdminDashboardData failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "All Staff",
            notes = "This method will return All Staff",
            produces = "application/json", nickname = "Get All Staff ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Staff fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllStaff(HttpServletRequest request) {
        logger.info("get All Satff ..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.not.found"));
        response.setResponseCode(ResponseEnum.USER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<StaffWrapper> userList = staffService.findAllStaffWithoutPagination();
            if(!HISCoreUtil.isListEmpty(userList)){
                response.setResponseMessage(messageBundle.getString("user.fetched.success"));
                response.setResponseCode(ResponseEnum.USER_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(userList);
                logger.info("getAllPaginatedUser Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get all paginated User failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Department On Base of Branch",
            notes = "This method will return All Department On Base of Branch",
            produces = "application/json", nickname = "Get All Department On Base of Branch ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Department On Base of Branch fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDepartment(HttpServletRequest request,  @PathVariable("id") long id) {
        logger.info("get Department On Base of Branch ..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.not.found"));
        response.setResponseCode(ResponseEnum.CLI_DPT_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<DepartmentWrapper> deptList = staffService.findDepartmentsByBranch(id);
            if(!HISCoreUtil.isListEmpty(deptList)){
                response.setResponseMessage(messageBundle.getString("cli.dpts.fetch.success"));
                response.setResponseCode(ResponseEnum.CLI_DPT_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(deptList);
                logger.info("get Department On Base of Branch...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get Department On Base of Branch.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

