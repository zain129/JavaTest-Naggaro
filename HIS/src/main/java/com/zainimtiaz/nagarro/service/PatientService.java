package com.zainimtiaz.nagarro.service;

import com.sd.his.enums.*;
import com.sd.his.model.*;
import com.zainimtiaz.nagarro.enums.GenderTypeEnum;
import com.zainimtiaz.nagarro.enums.MaritalStatusTypeEnum;
import com.zainimtiaz.nagarro.enums.ModuleEnum;
import com.zainimtiaz.nagarro.enums.PatientStatusTypeEnum;
import com.zainimtiaz.nagarro.model.*;
import com.sd.his.repository.*;
import com.zainimtiaz.nagarro.model.LabTest;
import com.zainimtiaz.nagarro.repository.*;
import com.zainimtiaz.nagarro.utill.DateTimeUtil;
import com.zainimtiaz.nagarro.utill.HISConstants;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.sd.his.wrapper.*;
import com.zainimtiaz.nagarro.wrapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private HISUtilService hisUtilService;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private LabOrderRepository labOrderRepository;
    @Autowired
    private LabTestRepository labTestRepository;
    @Autowired
    private FamilyHistoryRepository familyHistoryRepository;
    @Autowired
    private SmokingStatusRepository smokingStatusRepository;
    @Autowired
    private InsuranceService insuranceService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private PatientGroupRepository patientGroupRepository;
    @Autowired
    LabTestSpecimanRepository labTestSpecimanRepository;
    @Autowired
    private PatientService patientService;

    @Autowired
    private InsurancePlanRepository planRepository;

    @Autowired
    private InsuranceProfileRepository profileRepository;
    //response populate
    private void populatePatientWrapper(PatientWrapper patientWrapper, Patient patient) {
        patientWrapper.setId(patient.getId());//patient pk
        //patientWrapper.setPatientId(patient.getPatientId());//patient natural id
    //    this.firstName = patient.getFirstName()==null ? "-":patient.getFirstName();
     //   this.lastName = patient.getLastName()==null ? "-":patient.getLastName();
     //   this.email = patient.getEmail()==null ? "-":patient.getEmail();
        patientWrapper.setTitlePrefix(patient.getTitle());
        patientWrapper.setPatientSSN(patient.getPatientSSN());
        patientWrapper.setFirstName(patient.getFirstName()==null ? "-":patient.getFirstName());
        patientWrapper.setMiddleName(patient.getMiddleName());
        patientWrapper.setLastName(patient.getLastName()==null ? "-":patient.getLastName());
        patientWrapper.setForeignName(patient.getForeignName());
        if (patient.getDob() != null) {
            patientWrapper.setDob(patient.getDob() + "");
        }else{
            patientWrapper.setDob("-");
        }

        patientWrapper.setHomePhone(patient.getHomePhone());
        patientWrapper.setCellPhone(patient.getCellPhone());
        patientWrapper.setOfficePhone(patient.getOfficePhone());
        if (patient.getGender() != null && !patient.getGender().name().trim().equals(""))
            patientWrapper.setGender(patient.getGender().name());

        patientWrapper.setEmail(patient.getEmail()==null ? "-":patient.getEmail());
        if (patient.getMaritalStatus() != null && !patient.getMaritalStatus().name().trim().equals(""))
            patientWrapper.setMarital(patient.getMaritalStatus().name());
        patientWrapper.setStatus(patient.getStatus() == PatientStatusTypeEnum.ACTIVE);
        //patientWrapper.setProfileStatus( patient.getStatus().name().equalsIgnoreCase("ACTIVE") );
        patientWrapper.setDisableSMSTxt(patient.getDisableSMSText() == null ? false : patient.getDisableSMSText());
        patientWrapper.setPreferredCommunication(patient.getPreferredCommunication());
//        patientWrapper.setReminderLanguage(patient.getReminderLanguage());
        patientWrapper.setStreetAddress(patient.getStreetAddress()==null?"-":patient.getStreetAddress());

        if (patient.getCity() != null) {
            patientWrapper.setCity(patient.getCity().getName());
            patientWrapper.setCityId(patient.getCity().getId());
            patientWrapper.setState(patient.getCity().getState().getName());
            patientWrapper.setStateId(patient.getCity().getState().getId());
            patientWrapper.setCountry(patient.getCity().getCountry().getName());
            patientWrapper.setCountryId(patient.getCity().getCountry().getId());
        }

        patientWrapper.setEmergencyContactName(patient.getEmergencyContactName());
        patientWrapper.setProfileImgURL(patient.getProfileImgURL());

        if (patient.getEmergencyContactPhone() != null)
            patientWrapper.setEmergencyContactPhone(patient.getEmergencyContactPhone());
        if (patient.getEmergencyContactRelation() != null)
            patientWrapper.setEmergencyContactRelation(patient.getEmergencyContactRelation());
        // patientWrapper.setPatientId(hisUtilService.getPrefixId(ModuleEnum.PATIENT));
        patientWrapper.setPatientId(patient.getPatientId());


        if (patient.getAppointments() != null && patient.getAppointments().size() > 0) {
            patientWrapper.setHasChild(true);
        }

        if (patient.getPatientGroup() != null) {
            patientWrapper.setPatientGroup(patient.getPatientGroup().getName());
            patientWrapper.setPatientGroupId(patient.getPatientGroup().getId());
        }
        /*if (patient.getAllergies() != null && patient.getAllergies().size() > 0) {
            patientWrapper.setHasChild(true);
        }
        if (patient.getFamilyHistory() != null && patient.getFamilyHistory().size() > 0) {
            patientWrapper.setHasChild(true);
        }
        if (patient.getInvoices() != null && patient.getInvoices().size() > 0) {
            patientWrapper.setHasChild(true);
        }
        if (patient.getProblems() != null && patient.getProblems().size() > 0) {
            patientWrapper.setHasChild(true);
        }
        if (patient.getMedications() != null && patient.getMedications().size() > 0) {
            patientWrapper.setHasChild(true);
        }
        if (patient.getLabOrders() != null && patient.getLabOrders().size() > 0) {
            patientWrapper.setHasChild(true);
        }
        if (patient.getPatientInvoicePayments() != null && patient.getPatientInvoicePayments().size() > 0) {
            patientWrapper.setHasChild(true);
        }
        if (patient.getSmokingStatusList() != null && patient.getSmokingStatusList().size() > 0) {
            patientWrapper.setHasChild(true);
        }*/


    }

    //Request Populate
    private void populatePatient(Patient patient, PatientWrapper patientWrapper) throws ParseException {
        if (patientWrapper.getId() <= 0) { // it means new record
            patient.setPatientId(hisUtilService.getPrefixId(ModuleEnum.PATIENT));
        }
        patient.setTitle(patientWrapper.getTitlePrefix());
        patient.setPatientSSN(patientWrapper.getPatientSSN());
        patient.setFirstName(patientWrapper.getFirstName());
        patient.setMiddleName(patientWrapper.getMiddleName());
        patient.setLastName(patientWrapper.getLastName());
        if (!patientWrapper.getDob().isEmpty())
            patient.setDob(DateTimeUtil.getDateFromString(patientWrapper.getDob(), HISConstants.DATE_FORMATE_THREE));
        patient.setHomePhone(patientWrapper.getHomePhone());
        patient.setCellPhone(patientWrapper.getCellPhone());
        patient.setOfficePhone(patientWrapper.getOfficePhone());
        if (patientWrapper.getGender() != null && !patientWrapper.getGender().trim().equals(""))
            patient.setGender(GenderTypeEnum.valueOf(patientWrapper.getGender().toUpperCase()));

        //image profile
        if(patientWrapper.getEmail()!=null){
            patient.setEmail(patientWrapper.getEmail());
        }else{
            patient.setEmail("-");
        }

        if (patientWrapper.getMarital() != null && !patientWrapper.getMarital().trim().equals(""))
            patient.setMaritalStatus(MaritalStatusTypeEnum.valueOf(patientWrapper.getMarital().toUpperCase()));
        patient.setStatus(patientWrapper.getStatus() ? PatientStatusTypeEnum.ACTIVE : PatientStatusTypeEnum.INACTIVE);
//        patient.setStatus(patientWrapper.getStatus());
        patient.setDisableSMSText(patientWrapper.isDisableSMSTxt());
        patient.setPreferredCommunication(patientWrapper.getPreferredCommunication());
//        patient.setReminderLanguage(patientWrapper.getReminderLanguage());
        patient.setStreetAddress(patientWrapper.getStreetAddress());

        if (patientWrapper.getCityId() != null) {
            patient.setCity(cityRepository.findOne(patientWrapper.getCityId()));
            patient.setCountry(patient.getCity().getCountry().getName());
            patient.setState(patient.getCity().getState().getName());
        }

        patient.setEmergencyContactName(patientWrapper.getEmergencyContactName());
        patient.setEmergencyContactPhone(patientWrapper.getEmergencyContactPhone());
        patient.setEmergencyContactRelation(patientWrapper.getEmergencyContactRelation());
        if (patient.getId() == null)
            patient.setPatientId(hisUtilService.getPrefixId(ModuleEnum.PATIENT));

        if (patientWrapper.getPatientGroupId() != null) {
            patient.setPatientGroup(patientGroupRepository.findOne(patientWrapper.getPatientGroupId()));
        }
    }

    private void populateInsurance(Insurance insurance, PatientWrapper patientWrapper) throws ParseException {
        InsuranceProfile InsuranceProfile;
        InsurancePlan icd;
        if(patientWrapper.getCompany().length()>=4){

             InsuranceProfile = profileRepository.findByName(patientWrapper.getCompany());
        }else{
            InsuranceProfile = profileRepository.findOne(Long.valueOf(patientWrapper.getCompany()));
        }
       // InsuranceProfile InsuranceProfile = profileRepository.findOne(Long.valueOf(patientWrapper.getCompany()));
        insurance.setCompany(InsuranceProfile);
        insurance.setInsuranceIDNumber(patientWrapper.getInsuranceIdNumber());// not primary key
        insurance.setGroupNumber(patientWrapper.getGroupNumber());
        if(patientWrapper.getPlanName().length()>=4){
             icd = planRepository.findByName(patientWrapper.getPlanName());
        }else{
             icd = planRepository.findOne(Long.valueOf(patientWrapper.getPlanName()));
        }

        insurance.setPlanN(icd);
        insurance.setPlanType(patientWrapper.getPlanType());

        if (!patientWrapper.getCardIssuedDate().isEmpty())
            insurance.setCardIssuedDate(DateTimeUtil.getDateFromString(patientWrapper.getCardIssuedDate(), HISConstants.DATE_FORMATE_YYY_MM_dd));
        if (!patientWrapper.getCardExpiryDate().isEmpty())
            insurance.setCardExpiryDate(DateTimeUtil.getDateFromString(patientWrapper.getCardExpiryDate(), HISConstants.DATE_FORMATE_YYY_MM_dd));
        insurance.setPrimaryInsuranceNotes(patientWrapper.getPrimaryInsuranceNotes());
    }

    private void populateInsurance(PatientWrapper patientWrapper, Patient patient) {
        if (patient.getInsurance() != null) {
            patientWrapper.setInsuranceId(patient.getInsurance().getId());
            InsuranceProfile InsuranceProfile = profileRepository.findOne(Long.valueOf(patient.getInsurance().getCompany().getId()));
            patientWrapper.setCompany(InsuranceProfile.getName());
            patientWrapper.setInsuranceIdNumber(patient.getInsurance().getInsuranceIDNumber());
            patientWrapper.setGroupNumber(patient.getInsurance().getGroupNumber());
            InsurancePlan InsurancePlan = planRepository.findOne(Long.valueOf(patient.getInsurance().getCompany().getId()));
            patientWrapper.setPlanName(InsurancePlan.getName());
            patientWrapper.setPlanType(patient.getInsurance().getPlanType());
            if (patient.getInsurance().getCardIssuedDate() != null)
                patientWrapper.setCardIssuedDate(patient.getInsurance().getCardIssuedDate() + "");
            if (patient.getInsurance().getCardExpiryDate() != null)
                patientWrapper.setCardExpiryDate(patient.getInsurance().getCardExpiryDate() + "");
            patientWrapper.setPrimaryInsuranceNotes(patient.getInsurance().getPrimaryInsuranceNotes());
            patientWrapper.setPhotoFrontURL(patient.getInsurance() != null ? patient.getInsurance().getPhotoFrontURL() : null);
            patientWrapper.setPhotoBackURL(patient.getInsurance() != null ? patient.getInsurance().getPhotoBackURL() : null);
        }
    }

    public String savePatient(PatientWrapper patientWrapper) throws Exception {
        Patient patient = null;
        if (patientWrapper.getId() > 0) {
            patient = patientRepository.findOne(patientWrapper.getId());
            if (patient == null)
                patient = new Patient();
        } else {
            patient = new Patient();
            patient.setAdvanceBalance(0.0);
        }

        this.populatePatient(patient, patientWrapper);
        Insurance insurance = null;
        if (patientWrapper.getInsuranceId() != null && patientWrapper.getInsuranceId() > 0) {
            insurance = this.insuranceService.getInsuranceRepository().findOne(patientWrapper.getInsuranceId());
            if (insurance == null) {
                insurance = new Insurance();
            }
        } else {
            insurance = new Insurance();
        }

        if (!HISCoreUtil.isNull(patientWrapper.getCompany())) {
            this.populateInsurance(insurance, patientWrapper);
            this.insuranceService.getInsuranceRepository().save(insurance);
            patient.setInsurance(insurance);
            patientWrapper.setInsuranceId(insurance.getId());
        }
        Doctor doctor = doctorRepository.findOne(patientWrapper.getSelectedDoctor());
        patient.setPrimaryDoctor(doctor);
        patient.setForeignName(patientWrapper.getForeignName());
        patient = patientRepository.save(patient);
        //patientWrapper.

        /*
        Profile profile = new Profile(patientWrapper);
        UserRole userRole;
        User selectedDoctor = this.userRepository.findOne(patientWrapper.getSelectedDoctor());
        Insurance insurance = new Insurance(patientWrapper);
        User patient = new User(patientWrapper, UserTypeEnum.PATIENT.toString());
        patient.setPrimaryDoctor(selectedDoctor);


        this.profileRepository.save(profile);
        this.insuranceRepository.save(insurance);
        patient.setProfile(profile);
        patient.setInsurance(insurance);
        this.userRepository.save(patient);
        userRole = new UserRole(patient, roleRepo.findByName(UserTypeEnum.PATIENT.getValue()));
        userRoleRepository.save(userRole);

*/
        /// now saving images against user id

        ///profile photo save

        String url = null;
        if (patientWrapper.getProfileImg() != null) {
            url = userService.saveImage(patientWrapper.getProfileImg(),
                    HISConstants.S3_USER_PATIENT_PROFILE_DIRECTORY_PATH,
                    patient.getId()
                            + "_"
                            + patient.getId()
                            + "_"
                            + HISConstants.S3_USER_PROFILE_THUMBNAIL_GRAPHIC_NAME,
                    patient.getId()
                            + "_"
                            + patient.getId()
                            + "_"
                            + HISConstants.S3_USER_PROFILE_THUMBNAIL_GRAPHIC_NAME,
                    "/"
                            + HISConstants.S3_USER_PATIENT_PROFILE_DIRECTORY_PATH
                            + patient.getId()
                            + "_"
                            + patient.getId()
                            + "_"
                            + HISConstants.S3_USER_PROFILE_THUMBNAIL_GRAPHIC_NAME);
        }


        if (HISCoreUtil.isValidObject(url)) {
            patient.setProfileImgURL(url);
            this.patientRepository.save(patient);
            url = null;
        }
        ///profile photo save

        ///front photo save

        if (patientWrapper.getPhotoFront() != null) {
            url = userService.saveImage(patientWrapper.getPhotoFront(),
                    HISConstants.S3_USER_INSURANCE_DIRECTORY_PATH,
                    patient.getId()
                            + "_"
                            + patient.getInsurance().getId()
                            + "_"
                            + HISConstants.S3_USER_INSURANCE_FRONT_PHOTO_THUMBNAIL_GRAPHIC_NAME,
                    patient.getId()
                            + "_"
                            + patient.getInsurance().getId()
                            + "_"
                            + HISConstants.S3_USER_INSURANCE_FRONT_PHOTO_GRAPHIC_NAME,
                    "/"
                            + HISConstants.S3_USER_INSURANCE_DIRECTORY_PATH
                            + patient.getId()
                            + "_"
                            + patient.getInsurance().getId()
                            + "_"
                            + HISConstants.S3_USER_INSURANCE_FRONT_PHOTO_THUMBNAIL_GRAPHIC_NAME);

        }
        if (HISCoreUtil.isValidObject(url)) {
            patient.getInsurance().setPhotoFrontURL(url);
            this.insuranceService.getInsuranceRepository().save(patient.getInsurance());
            url = null;
        }
        ///back photo save
        if (patientWrapper.getPhotoBack() != null) {
            url = userService.saveImage(patientWrapper.getPhotoBack(),
                    HISConstants.S3_USER_INSURANCE_DIRECTORY_PATH,
                    patient.getId()
                            + "_"
                            + patient.getInsurance().getId()
                            + "_"
                            + HISConstants.S3_USER_INSURANCE_BACK_PHOTO_THUMBNAIL_GRAPHIC_NAME,
                    patient.getId()
                            + "_"
                            + patient.getInsurance().getId()
                            + "_"
                            + HISConstants.S3_USER_INSURANCE_BACK_PHOTO_GRAPHIC_NAME,
                    "/"
                            + HISConstants.S3_USER_INSURANCE_DIRECTORY_PATH
                            + patient.getId()
                            + "_"
                            + patient.getInsurance().getId()
                            + "_"
                            + HISConstants.S3_USER_INSURANCE_BACK_PHOTO_THUMBNAIL_GRAPHIC_NAME);
        }

        if (HISCoreUtil.isValidObject(url)) {
            patient.getInsurance().setPhotoBackURL(url);
            this.insuranceService.getInsuranceRepository().save(patient.getInsurance());
            url = null;
        }

        return patient.getId() + "";

    }

    public Patient findPatientByID(long id) {
        return patientRepository.findOne(id);
    }

    public void savePatientUpadtedImage(Patient patient) {
        patientRepository.save(patient);
    }

    public void saveUpdatePatientInsuranceImage(Insurance insurance) {
        this.insuranceService.getInsuranceRepository().save(insurance);
    }

    public boolean isEmailAlreadyExists(String email) {
        Patient patient = this.patientRepository.findAllByEmail(email);
        return patient != null;
    }

    public int countAllPaginatedPatients() {
        return this.patientRepository.findAll().size();
    }

    public List<PatientWrapper> findAllPaginatedPatients(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        List<Patient> patientList = patientRepository.getAllPaginatedPatients(pageable);
        return this.getPatientWrapperList(patientList);
        /*List<PatientWrapper> patientWrapperList = new ArrayList<>();
        for (patient p: patientList) {
            patientWrapper = new PatientWrapper();
            this.populatePatientWrapper(patientWrapper, p );
            patientWrapperList.add(patientWrapper);
        }
        return patientWrapperList;*/
    }

    public List<PatientWrapper> getAllPatientList() {
        List<Patient> patient = patientRepository.findAll();
        return this.getPatientWrapperList(patient);
    }

    public PatientWrapper getPatientById(long id) {
        Patient patient = patientRepository.findOne(id);
        PatientWrapper patientWrapper = new PatientWrapper(patient);
        if(patient.getDob()!= null){
            int age=HISCoreUtil.calculateAge(patient.getDob(),new Date());
            patientWrapper.setAge(age);
        }else{
            patientWrapper.setAge(Integer.parseInt("-"));
        }
        /*if (patient.getCity() != null) {
            patient.setState(patient.getCity().getState().getName());
            patient.setCountry(patient.getCity().getCountry().getName());
        }*/
     //  PatientWrapper patientWrapper = new PatientWrapper();
        this.populatePatientWrapper(patientWrapper, patient);
       if (patient.getPrimaryDoctor() != null) {
            patientWrapper.setSelectedDoctor(patient.getPrimaryDoctor().getId());

            if(patient.getPrimaryDoctor().getFirstName()!=null || patient.getPrimaryDoctor().getFirstName().equals("")){

                patientWrapper.setPrimaryDoctorFirstName(patient.getPrimaryDoctor().getFirstName());
            }else{
                patientWrapper.setPrimaryDoctorFirstName("-");
            }

            if(patient.getPrimaryDoctor().getLastName()!=null || patient.getPrimaryDoctor().getLastName().equals("")) {
                patientWrapper.setPrimaryDoctorLastName(patient.getPrimaryDoctor().getLastName());
            }else{
                patientWrapper.setPrimaryDoctorLastName("-");
            }

        }
        if(patient.getFormattedAddress() !=null){
            patientWrapper.setFormattedAddress(patient.getFormattedAddress());
        }else{
            patientWrapper.setFormattedAddress("-");
        }
     //   patientWrapper.setSmokingStatuses(patient.getSmokingStatusList() != null ? patient.getSmokingStatusList() : null);
     //     this.populateAppointments(patientWrapper, patient);
          this.populateInsurance(patientWrapper, patient);
        Organization dbOrganization=organizationService.getAllOrgizationData();
        String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
        String systemDateFormat=dbOrganization.getDateFormat();
        String systemTimeFormat=dbOrganization.getTimeFormat();
        String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
        String hoursFormat = dbOrganization.getHoursFormat();
       // dteFrom = DateTimeUtil.getDateFromString(patientWrapper., "yyyy-MM-dd hh:mm:ss");
        String readDateFrom = HISCoreUtil.convertDateToTimeZone(patient.getCreatedOn(), "yyyy-MM-dd hh:mm:ss", Zone);
        String lastAppointment;
        try {
            if(patientWrapper.getLastAppointment()!=null || !patientWrapper.getLastAppointment().equals("")) {
            Date fromLastDte = DateTimeUtil.getDateFromString(patientWrapper.getLastAppointment(), "yyyy-MM-dd hh:mm:ss");
            lastAppointment=HISCoreUtil.convertDateToTimeZone(fromLastDte,"yyyy-MM-dd hh:mm:ss", Zone);
            patientWrapper.setLastAppointment(lastAppointment);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date lastDateStr= null;
        Date fromDte=null;
        Date lastDate=null;
        Date nextDate=null;
      //  Date nextDate;
        try{
            if(patientWrapper.getLastAppointment()!=null || !patientWrapper.getLastAppointment().equals("")) {
             lastDateStr = DateTimeUtil.getDateFromString(patientWrapper.getLastAppointment(),"yyyy-MM-dd hh:mm:ss");
             fromDte = DateTimeUtil.getDateFromString(readDateFrom, "yyyy-MM-dd hh:mm:ss");
        }
            if(patientWrapper.getNextAppointment()!=null || !patientWrapper.getNextAppointment().equals("")) {
                lastDateStr = DateTimeUtil.getDateFromString(patientWrapper.getNextAppointment(),"yyyy-MM-dd hh:mm:ss");
                fromDte = DateTimeUtil.getDateFromString(readDateFrom, "yyyy-MM-dd hh:mm:ss");
            }
        }
        catch (ParseException e) {
        e.printStackTrace();
        }
        if (systemDateFormat != null || !systemDateFormat.equals("")) {

            String dtelFrom = HISCoreUtil.convertDateToStringWithDateDisplay(fromDte, systemDateFormat);
            String dob=HISCoreUtil.convertDateToStringWithDateDisplay(patient.getDob(),systemDateFormat);
            String lastAppointmentDate=HISCoreUtil.convertDateToStringWithDateDisplay(lastDateStr,systemDateFormat);
            if(lastAppointmentDate!=null){
                patientWrapper.setLastAppointment(lastAppointmentDate);
            }else{
                patientWrapper.setLastAppointment("-");
            }
            if(dob!=null){
            patientWrapper.setDobStr(dob);
            }else{
                patientWrapper.setDobStr("-");
            }
           // patientWrapper.setDob(dob);
            if(dtelFrom!=null){
            patientWrapper.setCreateDate(dtelFrom);
            }else{
                patientWrapper.setCreateDate("-");
            }
        }
        patientWrapper.setSmokingStatuses(patient.getSmokingStatusList());
     //   this.populateAppointments(patientWrapper, patient);
     //   this.populateInsurance(patientWrapper, patient);
        return patientWrapper;
    }

    public Patient getPatientByIdForHistory(Long id) {
        return patientRepository.findOne(id);
    }

    private void populateAppointments(PatientWrapper patientWrapper, Patient patient) {
        List<AppointmentWrapper> apptFutureWrapperList = new ArrayList<>();
        List<AppointmentWrapper> apptPastWrapperList = new ArrayList<>();
        List<AppointmentWrapper> listOfAppointments = appointmentRepository.findAllAppointmentsByPatient(patient.getId());
        Map<Boolean, List<AppointmentWrapper>> listOfApp = listOfAppointments.stream().sorted(Comparator.comparing(AppointmentWrapper::getLabel).reversed())
                .collect(Collectors.partitioningBy(x -> x.getCompareDate()
                        .toInstant().isAfter(Instant.now())));
        patientWrapper.setFutureAppointments(listOfApp.get(true));
        patientWrapper.setPastAppointments(listOfApp.get(false));
    }

    public boolean deletePatientById(long patientId) {
        Patient patient = this.patientRepository.findOne(patientId);
        if (patient != null) {
            patientRepository.delete(patientId);
            return true;
        }
        return false;
    }

    public List<PatientWrapper> searchAllPaginatedPatient(int offset, int limit, String searchString) {//searchString may contain patient name or cell number
        Pageable pageable = new PageRequest(offset, limit);
        List<Patient> patientList = patientRepository.searchPatientByNameOrCellNbr(pageable, searchString.toLowerCase());
        return this.getPatientWrapperList(patientList);
    }

    private List<PatientWrapper> getPatientWrapperList(List<Patient> patientList) {
        PatientWrapper patientWrapper = new PatientWrapper();
        List<PatientWrapper> patientWrapperList = new ArrayList<>();
        for (Patient p : patientList) {
            patientWrapper = new PatientWrapper();
            this.populatePatientWrapper(patientWrapper, p);
            patientWrapperList.add(patientWrapper);
        }
        return patientWrapperList;
    }

    public List<PatientWrapper> getAllPatient() {
        List<PatientWrapper> patientsList = patientRepository.getAll();

        return patientsList;
//        return patientRepository.getAllByStatusTrue();
    }

    //Lab Order work
    public LabOrderWrapper saveLabOrder(LabOrderWrapper labOrderWrapper) {
        LabOrder labOrder = new LabOrder();

        labOrder.setComments(labOrderWrapper.getComments());
        labOrder.setStatus(labOrderWrapper.getOrderStatus());
        // Time Zone Logic
        Organization dbOrganization = organizationService.getAllOrgizationData();
        String systemDateFormat = dbOrganization.getDateFormat();
        String Zone = dbOrganization.getZone().getName().replaceAll("\\s", "");
        Date dte = labOrderWrapper.getTestDate();
        String utcDate = HISCoreUtil.convertDateToTimeZone(dte, systemDateFormat, Zone);
        String currentTime = HISCoreUtil.getCurrentTimeByzone(Zone);
      //  System.out.println("Time" + currentTime);
        String readDate = HISCoreUtil.convertDateToTimeZone(dte, "YYYY-MM-dd hh:mm:ss", Zone);
        //  System.out.println("Read Date"+readDate);
        Date scheduledDate = HISCoreUtil.convertStringDateObject(readDate);
        labOrder.setDateTest(scheduledDate);
        Optional<Patient> patient = patientRepository.findById(labOrderWrapper.getPatientId());
        patient.ifPresent(labOrder::setPatient);
        Appointment appointment = appointmentRepository.findOne(labOrderWrapper.getAppointmentId());
        labOrder.setAppointment(appointment);
        List<com.zainimtiaz.nagarro.wrapper.LabTest> list = Arrays.stream(labOrderWrapper.getLabTest()).collect(Collectors.toList());
        labOrderRepository.save(labOrder);
        for (com.zainimtiaz.nagarro.wrapper.LabTest labOrder1 : list) {
            LabTest labTest = new LabTest();
            labTest.setDescription(labOrder1.getDescription());
            labTest.setNormalRange(labOrder1.getMinNormalRange());
            labTest.setUnits(labOrder1.getUnit());
            labTest.setResultValue(labOrder1.getResultValue());
            labTest.setLabOrder(labOrder);
            labTest.setLoincCode(labOrder1.getTestCode());
            labTestRepository.save(labTest);
        }
        return labOrderWrapper;
    }

    public List<LabOrderProjection> getAllLabOrders(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        return labOrderRepository.findAllProjectedBy(pageable);
    }

    public List<LabOrderProjection> getAllLabOrdersByPatient(int offset, int limit, Long patientId) {
        Patient patient = patientRepository.findOne(patientId);
        Pageable pageable = new PageRequest(offset, limit);
        return labOrderRepository.findAllByPatient(pageable, patient);
    }


    public int totaLabOrders() {
        return (int) labOrderRepository.count();
    }

    public LabOrderProjection getLabOrderById(long id) {
        return labOrderRepository.findById(id);
    }

    public LabOrder findById(long id) {
        return labOrderRepository.findOne(id);
    }

    public LabOrderWrapper updateLabOrder(LabOrderWrapper labOrderWrapper, LabOrder labOrder) {
        labOrder.setComments(labOrderWrapper.getComments());
        labOrder.setStatus(labOrderWrapper.getOrderStatus());
        Organization dbOrganization = organizationService.getAllOrgizationData();
        String systemDateFormat = dbOrganization.getDateFormat();
        String Zone = dbOrganization.getZone().getName().replaceAll("\\s", "");
        Date dte = labOrderWrapper.getTestDate();
        String utcDate = HISCoreUtil.convertDateToTimeZone(dte, systemDateFormat, Zone);
        String currentTime = HISCoreUtil.getCurrentTimeByzone(Zone);
    //    System.out.println("Time" + currentTime);
        String readDate = HISCoreUtil.convertDateToTimeZone(dte, "YYYY-MM-dd hh:mm:ss", Zone);
        //  System.out.println("Read Date"+readDate);
        Date scheduledDate = HISCoreUtil.convertStringDateObject(readDate);
        labOrder.setDateTest(scheduledDate);
        //  labOrder.setDateTest(HISCoreUtil.convertToDate(labOrderWrapper.getOrderTestDate()));
        Optional<Patient> patient = patientRepository.findById(labOrderWrapper.getPatientId());
        patient.ifPresent(labOrder::setPatient);
        Appointment appointment = appointmentRepository.findOne(labOrder.getAppointment().getId());
        labOrder.setAppointment(appointment);

        List<com.zainimtiaz.nagarro.wrapper.LabTest> list = Arrays.stream(labOrderWrapper.getLabTest()).collect(Collectors.toList());
        labOrderRepository.save(labOrder);
        List<String> alistCode = new ArrayList<>();
        for (int i = 0; i < alistCode.size(); i++) {
            alistCode.add(labOrder.getLabTests().get(i).getLoincCode());
        }

        List<LabTest> labTests = labTestRepository.findAllByLoincCodeIn(alistCode);
        if (!HISCoreUtil.isListEmpty(labTests))
            labTestRepository.delete(labTests);
        for (com.zainimtiaz.nagarro.wrapper.LabTest labOrder1 : list) {
            LabTest labTest = new LabTest();
            labTest.setDescription(labOrder1.getDescription());
            labTest = labTestRepository.findOne(Long.valueOf(labOrder1.getId()));
            //    labTest.setNormalRange(labOrder1.getMinNormalRange());
            //    labTest.setUnits(labOrder1.getUnit());
            labTest.setResultValue(labOrder1.getResultValue());
            //    labTest.setLabOrder(labOrder);
            //    labTest.setLoincCode(labOrder1.getTestCode());
            labTestRepository.save(labTest);
        }
        return labOrderWrapper;
    }

    @Transactional
    public boolean deleteByLabOrder(long id) {
        List<LabOrder> labOrder = labOrderRepository.findAllById(id);
        if (!HISCoreUtil.isValidObject(labOrder)) return false;
        List<LabTest> labTests = labTestRepository.findAllByLabOrderIn(labOrder);
        labTestRepository.deleteInBatch(labTests);
        int del = labOrderRepository.deleteById(id);
        if (del > 0) return true;
        return false;

    }

    //Family History

    public FamilyHistoryWrapper saveFamilyHistory(FamilyHistoryWrapper familyHistoryWrapper) {

        Patient patient = patientRepository.findOne(familyHistoryWrapper.getPatientId());
        FamilyHistory familyHistory = new FamilyHistory();
        familyHistory.setName(familyHistoryWrapper.getName());
        familyHistory.setEthnicGroup(familyHistoryWrapper.getEthnicGroup());
        familyHistory.setRelation(familyHistoryWrapper.getRelation());
        familyHistory.setStatus(familyHistoryWrapper.getStatus());
        familyHistory.setPatient(patient);
        familyHistoryRepository.save(familyHistory);

        return familyHistoryWrapper;
    }

    public List<FamilyHistoryWrapper> getAllFamilyHistoryByPatient(int offset, int limit, Long id) {
        Pageable pageable = new PageRequest(offset, limit);
        return familyHistoryRepository.findAllByPatient(id, pageable);
    }

    public List<FamilyHistoryWrapper> getAllFamilyHistory(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        return familyHistoryRepository.findAllByActive(pageable);
    }

    public List<FamilyHistoryWrapper> findAllFamilyHistory() {
        return familyHistoryRepository.findAllByActive();
    }

    public int familyHistoryCount() {
        return (int) familyHistoryRepository.count();
    }

    public FamilyHistory findFamilyHistoryById(Long id) {
        return familyHistoryRepository.findOne(id);
    }

    public FamilyHistoryWrapper updateFamilyHistory(FamilyHistoryWrapper familyHistoryWrapper, FamilyHistory familyHistory) {
        familyHistory.setName(familyHistoryWrapper.getName());
        familyHistory.setEthnicGroup(familyHistoryWrapper.getEthnicGroup());
        familyHistory.setRelation(familyHistoryWrapper.getRelation());
        familyHistory.setStatus(familyHistoryWrapper.getStatus());
        familyHistoryRepository.save(familyHistory);
        return familyHistoryWrapper;

    }


    public boolean deleteFamilyHistory(long id) {
        if (id != 0 || id > 0) {
            FamilyHistory familyHistory = familyHistoryRepository.findOne(id);
            if (HISCoreUtil.isValidObject(familyHistory)) {
                familyHistoryRepository.delete(familyHistory);
                return true;
            }
        }
        return false;

    }

    public void populateSmokeStatus(SmokingStatusWrapper smokeStatusWrapper, SmokingStatus smokeStatus) throws ParseException {
        if (!smokeStatusWrapper.getEndDate().isEmpty()) {
            smokeStatus.setEndDate(DateTimeUtil.getDateFromString(smokeStatusWrapper.getEndDate(), HISConstants.DATE_FORMATE_THREE));
        }
        if (!smokeStatusWrapper.getStartDate().isEmpty()) {
            smokeStatus.setStartDate(DateTimeUtil.getDateFromString(smokeStatusWrapper.getStartDate(), HISConstants.DATE_FORMATE_THREE));
        }
        if (!smokeStatusWrapper.getRecordedDate().isEmpty()) {
            smokeStatus.setRecordedDate(DateTimeUtil.getDateFromString(smokeStatusWrapper.getRecordedDate(), HISConstants.DATE_FORMATE_THREE));
        }
        smokeStatus.setSmokingStatus(smokeStatusWrapper.getSmokingStatus());
    }

    //smoke status
    public void savePatientSmokeStatus(SmokingStatus smokingStatus) {
        smokingStatusRepository.save(smokingStatus);
    }

    public void deleteSmokeStatusById(Long smokingId) {
        smokingStatusRepository.delete(smokingId);
    }

    public boolean patientHasChild(long patientId) {
        Patient patient = this.patientRepository.findOne(patientId);
        if (patient != null && patient.getAppointments() != null && patient.getAppointments().size() > 0) {
            return true;
        }
        return false;
    }

    public List<LabOrderWrapper> getAllLabOrdersList(String id) {
        Patient patient = patientRepository.findOne(Long.valueOf(id));
        return labOrderRepository.findAllByPatientById(patient);
    }

    public List<LabOrderWrapper> getAllLabOrdersByPatient(String patientId) {
        Patient patient = patientRepository.findOne(Long.valueOf(patientId));
        List<LabOrder> obj = labOrderRepository.findAllByPatient(patient);

        List<LabOrderWrapper> listofLab = new ArrayList<>();
        /*List<LabTest> labTests = labTestRepository.findAllByLabOrder(obj.get(i).getId());
        List<com.sd.his.wrapper.LabTest> returnListofLab = new ArrayList<>();
        for(int j=0;j<labTests.size();j++){
            com.sd.his.wrapper.LabTest testObj=new com.sd.his.wrapper.LabTest();
            if(labTests.get(j).getLoincCode()!=null){
                LabTestSpeciman labTestSpecimanObj = this.labTestSpecimanRepository.findTestEntry(labTests.get(j).getLoincCode());
                testObj.setTestName(labTestSpecimanObj.getTestName());
            }else{
                testObj.setTestName("");
            }
            testObj.setDescription(labTests.get(j).getDescription());
            testObj.setResultValue(labTests.get(j).getResultValue());
            testObj.setLoincCode(labTests.get(j).getLoincCode());
            testObj.setUnits(labTests.get(j).getUnits());
            testObj.setNormalRange(labTests.get(j).getNormalRange());
            testObj.setId(String.valueOf(labTests.get(j).getId()));
            returnListofLab.add(testObj);
        }*/
        for (int i = 0; i < obj.size(); i++) {
            List<LabTest> labTests = labTestRepository.findAllByLabOrder(obj.get(i).getId());
            boolean executeFlow=false;
            for(int j=0;j<labTests.size();j++){
                LabOrderWrapper  wrapObj = new LabOrderWrapper();
                if(obj.get(i).getId()==labTests.get(j).getLabOrder().getId()){
                    if(executeFlow==false) {

                        wrapObj.setTestDate(obj.get(i).getDateTest());
                        wrapObj.setComments(obj.get(i).getComments());
                        wrapObj.setAppointment(obj.get(i).getAppointment());
                        wrapObj.setPatient(obj.get(i).getPatient());
                        wrapObj.setId(obj.get(i).getId());
                        wrapObj.setOrderStatus(obj.get(i).getStatus());
                    }
                        if(labTests.get(j).getLoincCode()!=null){
                            LabTestSpeciman labTestSpecimanObj = this.labTestSpecimanRepository.findTestEntry(labTests.get(j).getLoincCode());
                            wrapObj.setTestName(labTestSpecimanObj.getTestName());
                        }else{
                            wrapObj.setTestName("");
                        }
                        wrapObj.setResultValue(labTests.get(j).getResultValue());
                        wrapObj.setLoincCode(labTests.get(j).getLoincCode());
                        wrapObj.setUnits(labTests.get(j).getUnits());
                        wrapObj.setNormalRange(labTests.get(j).getNormalRange());
                        wrapObj.setIdNew(String.valueOf(labTests.get(j).getId()));
                        listofLab.add(wrapObj);

                }
            }



        }
                return listofLab;
    }


    public List<com.zainimtiaz.nagarro.wrapper.LabTest> ListByLabOrder(long id) {

        List<LabTest> labTests = labTestRepository.findAllByLabOrder(id);
        List<com.zainimtiaz.nagarro.wrapper.LabTest> returnListofLab = new ArrayList<>();
        for(int j=0;j<labTests.size();j++){
            com.zainimtiaz.nagarro.wrapper.LabTest testObj=new com.zainimtiaz.nagarro.wrapper.LabTest();
            if(labTests.get(j).getLoincCode()!=null){
            LabTestSpeciman labTestSpecimanObj = this.labTestSpecimanRepository.findTestEntry(labTests.get(j).getLoincCode());
                testObj.setTestName(labTestSpecimanObj.getTestName());
            }else{
                testObj.setTestName("");
            }
                testObj.setDescription(labTests.get(j).getDescription());
                testObj.setResultValue(labTests.get(j).getResultValue());
                testObj.setLoincCode(labTests.get(j).getLoincCode());
                testObj.setUnits(labTests.get(j).getUnits());
                testObj.setNormalRange(labTests.get(j).getNormalRange());
                testObj.setId(String.valueOf(labTests.get(j).getId()));
                returnListofLab.add(testObj);
            }
        return returnListofLab;



    }


    public List<LabOrder> ListByLabOrderByOrderId(long id) {

        List<LabOrder> labOrder = labOrderRepository.findAllById(id);
        return labOrder;



    }


 // New Functionality
    public LabOrderUpdateWrapper updateLabOrderNew(LabOrderUpdateWrapper labOrderWrapper, LabOrder labOrder) {
        labOrder.setComments(labOrderWrapper.getComments());
        labOrder.setStatus(labOrderWrapper.getOrderStatus());
        Organization dbOrganization = organizationService.getAllOrgizationData();
        String systemDateFormat = dbOrganization.getDateFormat();
        String Zone = dbOrganization.getZone().getName().replaceAll("\\s", "");
        Date dte = labOrderWrapper.getTestDate();
        String utcDate = HISCoreUtil.convertDateToTimeZone(dte, systemDateFormat, Zone);
        String currentTime = HISCoreUtil.getCurrentTimeByzone(Zone);
      //  System.out.println("Time" + currentTime);
        String readDate = HISCoreUtil.convertDateToTimeZone(dte, "YYYY-MM-dd hh:mm:ss", Zone);
        //  System.out.println("Read Date"+readDate);
        Date scheduledDate = HISCoreUtil.convertStringDateObject(readDate);
        labOrder.setDateTest(scheduledDate);
        //  labOrder.setDateTest(HISCoreUtil.convertToDate(labOrderWrapper.getOrderTestDate()));
        Optional<Patient> patient = patientRepository.findById(labOrderWrapper.getPatientId());
        patient.ifPresent(labOrder::setPatient);
        Appointment appointment = appointmentRepository.findOne(labOrder.getAppointment().getId());
        labOrder.setAppointment(appointment);
        String dteFileUpload=HISCoreUtil.convertDateToStringUpload(new Date());
        String url = null;
        String imgURL = null;
        if (labOrderWrapper.getImage() != null) {
            try {
                imgURL = userService.saveImageMedical(labOrderWrapper.getImage(),
                        HISConstants.S3_USER_LABORDER_DIRECTORY_PATH, labOrderWrapper.getPatientId() + "_" + dteFileUpload
                                + "_"
                                + HISConstants.S3_USER_LABORDER_THUMBNAIL_GRAPHIC_NAME, labOrderWrapper.getPatientId()
                                + "_" + labOrderWrapper.getPatientId()
                                + "_"
                                + dteFileUpload
                                + "_"
                                + HISConstants.S3_USER_LABORDER_THUMBNAIL_GRAPHIC_NAME,
                        "/"
                                + HISConstants.S3_USER_LABORDER_DIRECTORY_PATH
                                + labOrderWrapper.getPatientId()
                                + "_"
                                + dteFileUpload
                                + "_"
                                + HISConstants.S3_USER_LABORDER_THUMBNAIL_GRAPHIC_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (HISCoreUtil.isValidObject(imgURL)) {
                labOrder.setImgURL(imgURL);

            }
        }
        labOrderRepository.save(labOrder);
      //  List<com.sd.his.wrapper.LabTest> list = Arrays.stream(labOrderWrapper.getLabTest()).collect(Collectors.toList());
        labOrderRepository.save(labOrder);
        List<String> alistCode = new ArrayList<>();
        for (int i = 0; i < alistCode.size(); i++) {
            alistCode.add(labOrder.getLabTests().get(i).getLoincCode());
        }

        List<LabTest> labTests = labTestRepository.findAllByLoincCodeIn(alistCode);
        if (!HISCoreUtil.isListEmpty(labTests))
            labTestRepository.delete(labTests);
        List<com.zainimtiaz.nagarro.wrapper.LabTest> list = labOrderWrapper.getLabTest();
        for (com.zainimtiaz.nagarro.wrapper.LabTest labOrder1 : list) {
            LabTest labTest = new LabTest();
            labTest.setDescription(labOrder1.getDescription());
            labTest = labTestRepository.findOne(Long.valueOf(labOrder1.getId()));
            //    labTest.setNormalRange(labOrder1.getMinNormalRange());
            //    labTest.setUnits(labOrder1.getUnit());
            labTest.setResultValue(labOrder1.getResultValue());
            //    labTest.setLabOrder(labOrder);
            //    labTest.setLoincCode(labOrder1.getTestCode());
            labTestRepository.save(labTest);
        }
        return labOrderWrapper;
    }




    public LabOrderUpdateWrapper saveLabOrderNew(LabOrderUpdateWrapper labOrderWrapper) {
        LabOrder labOrder = new LabOrder();

        labOrder.setComments(labOrderWrapper.getComments());
        labOrder.setStatus(labOrderWrapper.getOrderStatus());
        // Time Zone Logic
        Organization dbOrganization = organizationService.getAllOrgizationData();
        String systemDateFormat = dbOrganization.getDateFormat();
        String Zone = dbOrganization.getZone().getName().replaceAll("\\s", "");
        Date dte = labOrderWrapper.getTestDate();
        String utcDate = HISCoreUtil.convertDateToTimeZone(dte, systemDateFormat, Zone);
        String currentTime = HISCoreUtil.getCurrentTimeByzone(Zone);
        System.out.println("Time" + currentTime);
        String readDate = HISCoreUtil.convertDateToTimeZone(dte, "YYYY-MM-dd hh:mm:ss", Zone);
        //  System.out.println("Read Date"+readDate);
        Date scheduledDate = HISCoreUtil.convertStringDateObject(readDate);
        labOrder.setDateTest(scheduledDate);
        Optional<Patient> patient = patientRepository.findById(labOrderWrapper.getPatientId());
        patient.ifPresent(labOrder::setPatient);
        Appointment appointment = appointmentRepository.findOne(Long.valueOf(labOrderWrapper.getAppointmentId()));
        labOrder.setAppointment(appointment);
        List<com.zainimtiaz.nagarro.wrapper.LabTest> list =labOrderWrapper.getLabTest();
        String dteFileUpload=HISCoreUtil.convertDateToStringUpload(new Date());
        String url = null;
        String imgURL = null;
        if (labOrderWrapper.getImage() != null) {
            try {
                imgURL = userService.saveImageMedical(labOrderWrapper.getImage(),
                        HISConstants.S3_USER_LABORDER_DIRECTORY_PATH, labOrderWrapper.getPatientId() + "_" + dteFileUpload
                                + "_"
                                + HISConstants.S3_USER_LABORDER_THUMBNAIL_GRAPHIC_NAME, labOrderWrapper.getPatientId()
                                + "_" + labOrderWrapper.getPatientId()
                                + "_"
                                + dteFileUpload
                                + "_"
                                + HISConstants.S3_USER_LABORDER_THUMBNAIL_GRAPHIC_NAME,
                        "/"
                                + HISConstants.S3_USER_LABORDER_DIRECTORY_PATH
                                + labOrderWrapper.getPatientId()
                                + "_"
                                + dteFileUpload
                                + "_"
                                + HISConstants.S3_USER_LABORDER_THUMBNAIL_GRAPHIC_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (HISCoreUtil.isValidObject(imgURL)) {
                labOrder.setImgURL(imgURL);

            }
        }
        labOrderRepository.save(labOrder);
        for (com.zainimtiaz.nagarro.wrapper.LabTest labOrder1 : list) {
            LabTest labTest = new LabTest();
            labTest.setDescription(labOrder1.getDescription());
            labTest.setNormalRange(labOrder1.getMinNormalRange());
            labTest.setUnits(labOrder1.getUnit());
            labTest.setResultValue(labOrder1.getResultValue());
            labTest.setLabOrder(labOrder);
            labTest.setLoincCode(labOrder1.getTestCode());
            labTestRepository.save(labTest);
        }
        return labOrderWrapper;
    }
}
