package com.zainimtiaz.nagarro.service;/*
 * @author    : waqas kamran
 * @Date      : 17-Apr-18
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
 * @Package   : com.sd.his.*
 * @FileName  : UserAuthAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */


import com.zainimtiaz.nagarro.enums.OrganizationFormTypeEnum;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.zainimtiaz.nagarro.model.*;
import com.zainimtiaz.nagarro.repository.*;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.TimezoneWrapper;
import com.zainimtiaz.nagarro.wrapper.request.OrganizationRequestWrapper;
import com.zainimtiaz.nagarro.wrapper.response.OrganizationResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TimezoneRepository timezoneRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

  /*  @Autowired
    private BranchRepository branchRepository;*/
   /* @Autowired
    private BranchUserRepository branchUserRepository;*/


    public List<TimezoneWrapper> getAllTimeZone() {
        return timezoneRepository.findAllByCountryCode();
    }


    public List<Zone> getAllZone() {
        List<Zone> lst=zoneRepository.findAll();
        return zoneRepository.findAll();
    }

/*    public List<OrganizationResponseWrapper> getAllActiveOrganizations() {
        List<OrganizationResponseWrapper> organizationResponseWrappers = new ArrayList<>();
        for (Organization organization : organizationRepository.findAllByActiveTrueAndDeletedFalse()) {
            OrganizationResponseWrapper organizationResponseWrapper = new OrganizationResponseWrapper(organization);
            organizationResponseWrappers.add(organizationResponseWrapper);
        }
        return organizationResponseWrappers;
    }*/

    public List<OrganizationResponseWrapper> findAllPaginatedOrganization(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        return organizationRepository.findAllByName(pageable);
    }

    public int totalOrganizations() {
        return (int) organizationRepository.count();
    }

    public OrganizationResponseWrapper getOrganizationByIdWithResponse(long id) {

        return organizationRepository.findById(id);

    }


    public Organization getOrganizationByIdWithResponseAdditionalInfo(long id) {
        return organizationRepository.findByIdContains(id);

    }
   /*public Organization getOrganizationByIdWithResponse(long id) {
        return organizationRepository.findById(id);
    }*/

    public Organization getByID(long id) {
        return organizationRepository.getOne(id);
    }

    public OrganizationRequestWrapper updateOrganization(OrganizationRequestWrapper organizationRequestWrapper, Organization organization) {

        if (organizationRequestWrapper.getFormName().equalsIgnoreCase(OrganizationFormTypeEnum.PROFILE.name())) {

            organization.setCompanyName(organizationRequestWrapper.getCompanyName());
            organization.setWebsite(organizationRequestWrapper.getWebsite());
            organization.setOfficePhone(organizationRequestWrapper.getOfficePhone());
            organization.setFax(organizationRequestWrapper.getFax());
            organization.setAddress(organizationRequestWrapper.getAddress());
            organization.setSpecialty(organizationRequestWrapper.getSpecialty());
            organization.setEmail(organizationRequestWrapper.getCompanyEmail());
            boolean chkStatusCity=false;
            boolean chkStatusState=false;
            if(organizationRequestWrapper.getSelectedCity()!=null && (!organizationRequestWrapper.getSelectedCity().equals(""))){
             chkStatusCity = containsDigit(organizationRequestWrapper.getSelectedCity());

            }else{

            }
            if(organizationRequestWrapper.getSelectedState()!=null && (!organizationRequestWrapper.getSelectedState().equals(""))){
                chkStatusState = containsDigit(organizationRequestWrapper.getSelectedState());

            }else{

            }

            boolean chkStatusCountry = containsDigit(organizationRequestWrapper.getSelectedCountry());
            Long num;
            Long numState;
            Long numCountry;

            if (chkStatusCity) {
                num = Long.parseLong(organizationRequestWrapper.getSelectedCity());
            } else {
                if(organizationRequestWrapper.getSelectedCity()!=null && (!organizationRequestWrapper.getSelectedCity().equals(""))){
                City cityObj = cityRepository.findTitleById(organizationRequestWrapper.getSelectedCity());
                num = cityObj != null ? cityObj.getId() : null;
                }else{
                    num=null;
                }
            }
            if (chkStatusState) {
                numState = Long.parseLong(organizationRequestWrapper.getSelectedState());
            } else {
                if(organizationRequestWrapper.getSelectedState() != null && (!organizationRequestWrapper.getSelectedState().equals("")))
                {
                    State stateObj = stateRepository.findTitleById(organizationRequestWrapper.getSelectedState());
                    numState = stateObj != null ? stateObj.getId() : null;
                }else{
                    numState=null;
                }
            }
            if (chkStatusCountry) {
                numCountry = Long.parseLong(organizationRequestWrapper.getSelectedCountry());
            } else {
//                Country countryObj = countryRepository.findTitleById(organizationRequestWrapper.getSelectedCountry());
                Country countryObj = countryRepository.findByName(organizationRequestWrapper.getSelectedCountry());
                numCountry = countryObj != null ? countryObj.getId() : null;
            }

            if (num != null) {
                organization.setCity(cityRepository.findOne(num));
            }else{
                organization.setCity(null);
            }
            if (numState != null) {
                organization.setState(stateRepository.findOne(numState));
            }else{
                organization.setState(null);
            }

            if (numCountry != null) {
                organization.setCountry(countryRepository.findOne(numCountry));
            }
            /*String url = null;
            if (organizationRequestWrapper.getImage() != null) {
                try {
                    url = userService.saveImage(organizationRequestWrapper.getImage(),
                            HISConstants.S3_USER_ORGANIZATION_DIRECTORY_PATH,
                            organizationRequestWrapper.getUserId()
                                    + "_"
                                    + organizationRequestWrapper.getUserId()
                                    + "_"
                                    + HISConstants.S3_USER_ORGANIZATION_THUMBNAIL_GRAPHIC_NAME,
                            organizationRequestWrapper.getUserId()
                                    + "_"
                                    + organizationRequestWrapper.getUserId()
                                    + "_"
                                    + HISConstants.S3_USER_ORGANIZATION_GRAPHIC_NAME,
                            "/"
                                    + HISConstants.S3_USER_ORGANIZATION_DIRECTORY_PATH
                                    + organizationRequestWrapper.getUserId()
                                    + "_"
                                    + organizationRequestWrapper.getUserId()
                                    + "_"
                                    + HISConstants.S3_USER_ORGANIZATION_THUMBNAIL_GRAPHIC_NAME);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/


               /* if (HISCoreUtil.isValidObject(url)) {
                    organization.setUrl(url);
                    this.organizationRepository.save(organization);
                    return organizationRequestWrapper;
                    //           url = null;
                }*/
            organizationRepository.save(organization);
            return organizationRequestWrapper;
        }
        if (organizationRequestWrapper.getFormName().equalsIgnoreCase(OrganizationFormTypeEnum.GENERAL.name())) {
        //    String branchName;
          //  Branch branchFound=new Branch();
            Branch branch1=new Branch();
            Branch branch = branchRepository.findBySystemBranchTrue();
            branch.setSystemBranch(false);
            if(organizationRequestWrapper.getDefaultBranch()!=null && organizationRequestWrapper.getDefaultBranch().length()>4){
                 branch1=branchRepository.findByName(organizationRequestWrapper.getDefaultBranch());
            }else{
                 branch1 = branchRepository.findOne(Long.valueOf(organizationRequestWrapper.getDefaultBranch()));
            }

            branch1.setSystemBranch(true);
            branchRepository.save(branch1);
       //     organization.setDurationOFExam(organizationRequestWrapper.getDurationOfExam());
          //  organization.setZone(zoneRepository.findOne(Long.valueOf(organizationRequestWrapper.getTimezoneList()));
//            long id=Long.valueOf(organizationRequestWrapper.getSelectedTimeZoneFormat());
//            Zone zoneId=zoneRepository.findOne(id);
            Long numZone;
            Zone zoneObj=new Zone();
            String zone="";
            String result="";
         //   boolean chkStatusZone;
            if(organizationRequestWrapper.getSelectedTimeZoneFormat().length()>3){
                 zone=organizationRequestWrapper.getSelectedTimeZoneFormat().replaceAll("\\s","");

                if(zone.contains("(")){
                    result = zone.substring(0, zone.indexOf("("));

                }else{
                    result=zone;
                }
               zoneObj=zoneRepository.findZoneByName(result);
            }else{
                result=organizationRequestWrapper.getSelectedTimeZoneFormat();
                numZone = Long.parseLong(result);
                zoneObj=zoneRepository.findOne(numZone);
            }


            if (zoneObj != null) {
                organization.setZone(zoneObj);
            }




       //     long numZone;
           /* if (chkStatusZone) {
               numZone = Long.parseLong(result);
               zoneObj=zoneRepository.findOne(numZone);
            } else {

                    if(zone.contains("(")  ){
                        result=zone.substring(0, zone.indexOf("("));
                    }

                zoneObj = zoneRepository.findZoneByName(result);

            }*/
            if (zoneObj != null) {
                organization.setZone(zoneObj);
            }
        //    organization.setZone(organizationRequestWrapper.getZoneFormat().replaceAll("\\s",""));
            organization.setDateFormat(organizationRequestWrapper.getDateFormat().trim());
            organization.setTimeFormat(organizationRequestWrapper.getTimeFormat().trim());
            organization.setCurrencyFormat(organizationRequestWrapper.getCurrencyFormat());
            organization.setHoursFormat(organizationRequestWrapper.getHoursFormat());


           organizationRepository.save(organization);
            return organizationRequestWrapper;
        }

        if (organizationRequestWrapper.getFormName().equalsIgnoreCase(OrganizationFormTypeEnum.ACCOUNT.name())) {

            User user = userRepository.findOne(organizationRequestWrapper.getUserId());
            if(organizationRequestWrapper.getPassword()!=null){
            user.setPassword(new BCryptPasswordEncoder().encode(organizationRequestWrapper.getPassword()));
            }
            userRepository.save(user);
            Manager manager = managerRepository.findByUser(user);
            manager.setCellPhone(organizationRequestWrapper.getCellPhone());
            manager.setHomePhone(organizationRequestWrapper.getHomePhone());
            manager.setAddress(organizationRequestWrapper.getUserAddress());
            manager.setFirstName(organizationRequestWrapper.getFirstName());
            manager.setLastName(organizationRequestWrapper.getLastName());
            manager.setEmail(organizationRequestWrapper.getUserEmail());

            managerRepository.save(manager);
            //   user.setFi(organizationRequestWrapper.get);
            //  organizationRepository.save(organization);
            return organizationRequestWrapper;
        }
        return organizationRequestWrapper;

    }



    public Organization findOrgnazatinoByCompanyName(String companyName) {
        return organizationRepository.findByCompanyName(companyName);
    }

    public OrganizationResponseWrapper getOrganizationManagerAccountData() {
        User user = userRepository.findByUsernameAndActiveTrue("admin");
        Manager manager = managerRepository.findByUser(user);
        Branch branch = branchRepository.findBySystemBranchTrue();
        OrganizationResponseWrapper organizationResponseWrapper = new OrganizationResponseWrapper(user.getId(), user.getUserType().toString()
                , user.getUsername(), manager.getEmail(), manager.getFirstName(), manager.getLastName(), manager.getCellPhone(), manager.getHomePhone(), manager.getAddress()
        ,branch.getName(),branch.getId(),manager.getProfileImgURL());
        return organizationResponseWrapper;
    }

    public Organization getAllOrgizationData() {
        List<Organization> organization = organizationRepository.findAll();
        if(!HISCoreUtil.isListEmpty(organization)){
            Organization organizationData = (Organization)getFirstIndexofList(organization);
            return organizationData;
        }
        return null;
    }

    private   Organization  getFirstIndexofList(List<Organization> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return null;
        }
        Organization OrganizationObj= (Organization) dataList.get(0);
        return OrganizationObj;
    }

    public void saveOrganizationUpadtedImage(Organization organization) {
        organizationRepository.save(organization);
    }

    public final boolean containsDigit(String s) {
        boolean containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        return containsDigit;
    }

    public void saveProfileUpadtedImage(String url) {
        User user = userRepository.findByUsernameAndActiveTrue("admin");
        Manager manager = managerRepository.findByUser(user);
        manager.setProfileImgURL(url);
        managerRepository.save(manager);

    }
}