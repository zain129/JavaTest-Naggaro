package com.zainimtiaz.nagarro.wrapper.response;

import com.sd.his.model.*;
import com.zainimtiaz.nagarro.model.*;
import com.zainimtiaz.nagarro.service.OrganizationService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.MedicalServiceWrapper;
import com.zainimtiaz.nagarro.wrapper.ServiceComission;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/*
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
public class StaffResponseWrapper {

    private Long uid;
    private Long pId;
    private Long primaryBranchId;
    private String userType;
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String homePhone;
    private String cellPhone;
    private String expiryDate;
    private String primaryBranch;
    private DutyShift dutyShift;
    private String workingDaysOfDoctor;
    private List<Branch> visitBranches;
    private Boolean active;
    private Long checkUpInterval;
    private Boolean vacation;
    private String vacationFrom;
    private String vacationTo;
    private List<String> workingDays;
    private List<Long> permittedDoctorDashboard;
    private List<DutyShift> dutyShifts;
    private Boolean managePatientRecords;
    private Boolean managePatientInvoices;
    private String phoneNumber;
    private String address;
    private String city;
    private String country;
    private Boolean status;
    private String profileImg;
    private Long id;
    private String naturalId;
    private long value;
    private String label;
    private Long docDepartmentId;
    private List<Department> nurseDepartmentList;
    private List<Branch> staffBranches = new ArrayList();
    private List<Doctor> dutyWithDoctors;
    private boolean checkedDoc;
    private List<MedicalService> doctorMedicalSrvcList = new ArrayList();
    private List<MedicalServiceWrapper> doctorServiceComission = new ArrayList();
    private List<ServiceComission> comissionServices;
    private Double balance;
    private Boolean sendBillingReport;
    private Boolean useReceptDashboard;
    private Boolean otherDoctorDashBoard;
    private List<DutyShift> shift1;
    private List<DutyShift> shift2;
    private String formatedTime = "HH:mm:ss";
    private String formatedDate = "dd:MM:yyyy";
    private String zone = "Asia/Karachi";
    private Boolean receivePayment;
    private Double allowDiscount;
    private Boolean canAccessPatientRecord;
    private Boolean allowDiscountCheck;
    private Boolean hidePatientPhoneNumber;

    @Autowired
    private OrganizationService organizationService;

    public StaffResponseWrapper() {
       /*String timeFormate =  organizationService.getAllOrgizationData().getTimeFormat();
       if(timeFormate != null){
           this.formatedTime = timeFormate;
       }*/
    }

    //du.id,nr.id,du.userType,nr.firstName,nr.lastName,du.username,nr.email,br.name,nr.homePhone,nr.cellPhone,du.active,br.id,nr.accountExpiry
    //constructor for cashier
    public StaffResponseWrapper(Long uId, Long pId, String userType, String firstName, String lastName, String userName,
                                String email, String primaryBranch, String homePhone, String cellPhone, Boolean active, Long primaryId,
                                Date expiryDate, Boolean sendBillingReport, Boolean useReceptDashboard, Boolean otherDoctorDashBoard, Cashier cr

    ) {
        this.uid = uId;
        this.pId = pId;
        this.userType = userType;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.primaryBranchId = primaryId;
        //   this.visitBranches=branchList;
        this.active = active;
        this.expiryDate = HISCoreUtil.convertDateToStringWithZone(expiryDate);
        this.homePhone = homePhone;
        this.cellPhone = cellPhone;
        this.primaryBranch = primaryBranch;
        this.sendBillingReport = sendBillingReport;
        this.useReceptDashboard = useReceptDashboard;
        this.otherDoctorDashBoard = otherDoctorDashBoard;
        this.permittedDoctorDashboard = cr.getSelectedDoctorDashboard().keySet().stream().collect(Collectors.toList());
        this.allowDiscount = cr.getAllowDiscount();
        this.receivePayment = cr.getCanReceivePayment();
        this.canAccessPatientRecord = cr.getCanAccessPatientRecord();
        this.allowDiscountCheck = cr.getAllowDiscountCheck();
        this.hidePatientPhoneNumber =cr.getHidePatientPhoneNumber();
        this.allowDiscount =cr.getAllowDiscount();
        // this.visitBranches=branchCashiers;

    }
    //constructor for receptionist
    public StaffResponseWrapper(Long uId, Long pId, String userType, String firstName, String lastName, String userName,
                                String email, String primaryBranch, String homePhone, String cellPhone, Boolean active, Long primaryId,
                                Date expiryDate, Boolean sendBillingReport, Boolean useReceptDashboard, Boolean otherDoctorDashBoard,Receptionist rt

    ) {
        this.uid = uId;
        this.pId = pId;
        this.userType = userType;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.primaryBranchId = primaryId;
        //   this.visitBranches=branchList;
        this.active = active;
        this.expiryDate = HISCoreUtil.convertDateToStringWithZone(expiryDate);
        this.homePhone = homePhone;
        this.cellPhone = cellPhone;
        this.primaryBranch = primaryBranch;
        this.sendBillingReport = sendBillingReport;
        this.useReceptDashboard = useReceptDashboard;
        this.otherDoctorDashBoard = otherDoctorDashBoard;
        this.permittedDoctorDashboard = rt.getSelectedDoctorDashboard().keySet().stream().collect(Collectors.toList());
        this.receivePayment = rt.getCanReceivePayment();
        this.canAccessPatientRecord = rt.getCanAccessPatientRecord();
        this.allowDiscountCheck = rt.getAllowDiscountCheck();
        this.allowDiscount =rt.getAllowDiscount();
        this.hidePatientPhoneNumber =rt.getHidePatientPhoneNumber();
     //   this.canAccessPatientRecord = rt.;
        //   this.permittedDoctorDashboard = permitDocDashboard.keySet().stream().collect(Collectors.toList());

        // this.visitBranches=branchCashiers;

    }

    //constructor for generarl list
    public StaffResponseWrapper(Long uid, String pId, String userType, String email, String userName, String firstName, String lastName, String homePhone, String cellPhone, Long id) {
        this.uid = uid;
        this.naturalId = pId;
        this.userType = userType;
        this.email = email;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.homePhone = homePhone;
        this.cellPhone = cellPhone;
        this.id = id;
        this.label = firstName;
        this.value = id;

    }

    //constructor for doctor balance and generarl list
    public StaffResponseWrapper(Long uid, String pId, String email, String userName, String firstName, String lastName, Long id, Double balance) {
        this.uid = uid;
        this.naturalId = pId;
        this.email = email;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.label = firstName;
        this.value = id;
        this.balance = balance;

    }

    //constructor for doctor
    public StaffResponseWrapper(Long uId, Long pId, String userType, String firstName, String lastName, String userName,
                                String email, String primaryBranch, String homePhone, String cellPhone, Boolean active, Long primaryId,
                                Date expiryDate, Long checkUpInterval, Boolean vacation, Date vacationFrom, Date vacationTo,
                                Doctor doctor) {
        this.uid = uId;
        this.pId = pId;
        this.userType = userType;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.primaryBranchId = primaryId;
        //this.visitBranches=branchList;
        this.active = active;
        this.expiryDate = HISCoreUtil.convertDateToString(expiryDate);
        this.homePhone = homePhone;
        this.cellPhone = cellPhone;
        this.primaryBranch = primaryBranch;
        this.checkUpInterval = checkUpInterval;
        this.vacation = vacation;
        this.vacationFrom = HISCoreUtil.convertDateToString(vacationFrom);
        // this.vacationTo  =  HISCoreUtil.convertDateToTimeZone(vacationTo,formatedDate,zone);
        this.vacationTo = HISCoreUtil.convertDateToString(vacationTo);
        this.workingDays = doctor.getWorkingDays();
        this.permittedDoctorDashboard = doctor.getSelectedDoctorDashboard().keySet().stream().collect(Collectors.toList());
        // this.dutyShifts = doctor.getDutyShifts();
        this.shift1 = doctor.getDutyShifts().stream().filter(x -> x.getShiftName().name().equalsIgnoreCase("SHIFT1"))
                .map(x -> new DutyShift(x.getShiftName(), (HISCoreUtil.convertDateToTimeZone(x.getStartTime(), formatedTime, zone)), (HISCoreUtil.convertDateToTimeZone(x.getEndTime(), formatedTime, zone)))).collect(Collectors.toList());
        this.shift2 = doctor.getDutyShifts().stream().filter(Objects::nonNull).filter(x -> x.getShiftName().name().equalsIgnoreCase("SHIFT2"))
                .map(x -> new DutyShift(x.getShiftName(), (HISCoreUtil.convertDateToTimeZone(x.getStartTime(), formatedTime, zone)), (HISCoreUtil.convertDateToTimeZone(x.getEndTime(), formatedTime, zone)))).collect(Collectors.toList());
        this.sendBillingReport = doctor.getSendBillingReport();
        this.useReceptDashboard = doctor.getUseReceiptDashboard();
        this.otherDoctorDashBoard = doctor.getOtherDoctorDashboard();
        // this.visitBranches=branchCashiers;
        this.docDepartmentId = doctor.getDepartment() != null ? doctor.getDepartment().getId() : null;
        this.allowDiscount = doctor.getAllowDiscount();
        this.receivePayment = doctor.getCanReceivePayment();
        this.allowDiscountCheck = doctor.getAllowDiscountCheck();
        this.hidePatientPhoneNumber =doctor.getHidePatientPhoneNumber();
    }

  /*  public StaffResponseWrapper(Long uId,Long pId,String userType,String firstName,String lastName,String userName,
                                String email,String primaryBranch,String homePhone,String cellPhone,Boolean active,Long primaryId,
                                Date expiryDate

    ){
        this.uid = uId;
        this.pId=pId;
        this.userType = userType;
        this.userName = userName;
        this.firstName=firstName;
        this.lastName =lastName;
        this.email=email;
        this.primaryBranchId=primaryId;
        //   this.visitBranches=branchList;
        this.active=active;
        this.expiryDate= HISCoreUtil.convertDateToString(expiryDate);
        this.homePhone=homePhone;
        this.cellPhone=cellPhone;
        this.primaryBranch=primaryBranch;
        this.managePatientInvoices=managePatientInvoices;
        this.managePatientRecords=managePatientRecords;

        // this.visitBranches=branchCashiers;

    }*/

    //constructor for nurse

    public StaffResponseWrapper(Long uId, Long pId, String userType, String firstName, String lastName, String userName,
                                String email, String primaryBranch, String homePhone, String cellPhone, Boolean active, Long primaryId,
                                Date expiryDate, Boolean sendBillingReport, Boolean useReceptDashboard, Boolean otherDoctorDashBoard, Boolean managePatientRecords, Boolean managePatientInvoices,Nurse nurse

    ) {
        this.uid = uId;
        this.pId = pId;
        this.userType = userType;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.primaryBranchId = primaryId;
        //   this.visitBranches=branchList;
        this.active = active;
        this.expiryDate = HISCoreUtil.convertDateToStringWithZone(expiryDate);
        this.homePhone = homePhone;
        this.cellPhone = cellPhone;
        this.primaryBranch = primaryBranch;
        this.sendBillingReport = sendBillingReport;
        this.useReceptDashboard = useReceptDashboard;
        this.otherDoctorDashBoard = otherDoctorDashBoard;
        this.managePatientInvoices = managePatientInvoices;
        this.managePatientRecords = managePatientRecords;
        this.permittedDoctorDashboard = nurse.getSelectedDoctorDashboard().keySet().stream().collect(Collectors.toList());
        this.hidePatientPhoneNumber =nurse.getHidePatientPhoneNumber();
        // this.visitBranches=branchCashiers;

    }

    public StaffResponseWrapper(Nurse nurse) {
        this.uid = nurse.getUser().getId();
        this.pId = nurse.getId();
        this.userType = nurse.getUser().getUserType();
        this.userName = nurse.getUser().getUsername();
        this.firstName = nurse.getFirstName();
        this.lastName = nurse.getLastName();
        this.email = nurse.getEmail();
        OptionalLong pbId = nurse.getBranchNurses().stream().filter(b -> b.getPrimaryBranch() == true).mapToLong(pb -> pb.getId()).findFirst();
        this.primaryBranchId = pbId.getAsLong();
        //   this.visitBranches=branchList;
        this.active = nurse.getUser().getActive();
        this.expiryDate = HISCoreUtil.convertDateToString(nurse.getAccountExpiry());
        this.homePhone = nurse.getHomePhone();
        this.cellPhone = nurse.getCellPhone();
        this.primaryBranch = nurse.getBranchNurses().stream().filter(p -> p.getPrimaryBranch() == true).map(pb -> pb.getBranch().getName()).findAny().get();
        this.hidePatientPhoneNumber =nurse.getHidePatientPhoneNumber();
        //List<Branch> branches = nurse.getBranchNurses().stream().map(b->b.getBranch()).collect(Collectors.toList());
        //this.staffBranches = branches;
        //this.dutyWithDoctors = nurse.getNurseWithDoctorList().stream().map(d->d.getDoctor()).collect(Collectors.toList());
    }

    public StaffResponseWrapper(Object object) {
        Object obj = new Object();
        if (object instanceof Doctor) {
            Doctor doctor = (Doctor) object;
            this.id = doctor.getId();
            this.userType = doctor.getUser().getUserType();
            this.email = doctor.getEmail();
            this.userName = doctor.getUser().getUsername();
            this.firstName = doctor.getFirstName();
            this.lastName = doctor.getLastName();
            this.cellPhone = doctor.getCellPhone();
            this.address = doctor.getAddress();
            //this.gender = doctor.getGender().name();
            this.profileImg = doctor.getProfileImgURL();
            this.status = doctor.getStatus();
            this.docDepartmentId = doctor.getDepartment().getId();
        } else if (object instanceof Nurse) {
            Nurse nurse = (Nurse) object;
            this.email = nurse.getEmail();
            this.userName = nurse.getUser().getUsername();
            this.firstName = nurse.getFirstName();
            this.lastName = nurse.getLastName();
            this.cellPhone = nurse.getCellPhone();
            this.address = nurse.getAddress();
            //this.gender = nurse.getGender().name();
            this.profileImg = nurse.getProfileImgURL();
            this.status = nurse.getStatus();
        } else if (object instanceof Cashier) {
            Cashier cashier = (Cashier) object;
            this.email = cashier.getEmail();
            this.userName = cashier.getUser().getUsername();
            this.firstName = cashier.getFirstName();
            this.lastName = cashier.getLastName();
            this.cellPhone = cashier.getCellPhone();
            this.address = cashier.getAddress();
            //this.gender = cashier.getGender().name();
            this.profileImg = cashier.getProfileImgURL();
            this.status = cashier.getStatus();
        } else if (object instanceof Receptionist) {
            Receptionist receptionist = (Receptionist) object;
            this.email = receptionist.getEmail();
            this.userName = receptionist.getUser().getUsername();
            this.firstName = receptionist.getFirstName();
            this.lastName = receptionist.getLastName();
            this.cellPhone = receptionist.getCellPhone();
            this.address = receptionist.getAddress();
            //this.gender = receptionist.getGender().name();
            this.profileImg = receptionist.getProfileImgURL();
            this.status = receptionist.getStatus();
        }
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Boolean getHidePatientPhoneNumber() {
        return hidePatientPhoneNumber;
    }

    public void setHidePatientPhoneNumber(Boolean hidePatientPhoneNumber) {
        this.hidePatientPhoneNumber = hidePatientPhoneNumber;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Long getPrimaryBranchId() {
        return primaryBranchId;
    }

    public void setPrimaryBranchId(Long primaryBranchId) {
        this.primaryBranchId = primaryBranchId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPrimaryBranch() {
        return primaryBranch;
    }

    public void setPrimaryBranch(String primaryBranch) {
        this.primaryBranch = primaryBranch;
    }

    public DutyShift getDutyShift() {
        return dutyShift;
    }

    public void setDutyShift(DutyShift dutyShift) {
        this.dutyShift = dutyShift;
    }

    public String getWorkingDaysOfDoctor() {
        return workingDaysOfDoctor;
    }

    public void setWorkingDaysOfDoctor(String workingDaysOfDoctor) {
        this.workingDaysOfDoctor = workingDaysOfDoctor;
    }

    public List<Branch> getVisitBranches() {
        return visitBranches;
    }

    public void setVisitBranches(List<Branch> visitBranches) {
        this.visitBranches = visitBranches;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getCheckUpInterval() {
        return checkUpInterval;
    }

    public void setCheckUpInterval(Long checkUpInterval) {
        this.checkUpInterval = checkUpInterval;
    }

    public Boolean getVacation() {
        return vacation;
    }

    public void setVacation(Boolean vacation) {
        this.vacation = vacation;
    }

    public String getVacationFrom() {
        return vacationFrom;
    }

    public void setVacationFrom(String vacationFrom) {
        this.vacationFrom = vacationFrom;
    }

    public String getVacationTo() {
        return vacationTo;
    }

    public void setVacationTo(String vacationTo) {
        this.vacationTo = vacationTo;
    }

    public List<String> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<String> workingDays) {
        this.workingDays = workingDays;
    }

    public List<Long> getPermittedDoctorDashboard() {
        return permittedDoctorDashboard;
    }

    public void setPermittedDoctorDashboard(List<Long> permittedDoctorDashboard) {
        this.permittedDoctorDashboard = permittedDoctorDashboard;
    }

    public List<DutyShift> getDutyShifts() {
        return dutyShifts;
    }

    public void setDutyShifts(List<DutyShift> dutyShifts) {
        this.dutyShifts = dutyShifts;
    }

    public Boolean getManagePatientRecords() {
        return managePatientRecords;
    }

    public void setManagePatientRecords(Boolean managePatientRecords) {
        this.managePatientRecords = managePatientRecords;
    }

    public Boolean getManagePatientInvoices() {
        return managePatientInvoices;
    }

    public void setManagePatientInvoices(Boolean managePatientInvoices) {
        this.managePatientInvoices = managePatientInvoices;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaturalId() {
        return naturalId;
    }

    public void setNaturalId(String naturalId) {
        this.naturalId = naturalId;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getDocDepartmentId() {
        return docDepartmentId;
    }

    public void setDocDepartmentId(Long docDepartmentId) {
        this.docDepartmentId = docDepartmentId;
    }

    public List<Department> getNurseDepartmentList() {
        return nurseDepartmentList;
    }

    public void setNurseDepartmentList(List<Department> nurseDepartmentList) {
        this.nurseDepartmentList = nurseDepartmentList;
    }

    public List<Branch> getStaffBranches() {
        return staffBranches;
    }

    public void setStaffBranches(List<Branch> staffBranches) {
        this.staffBranches = staffBranches;
    }

    public List<Doctor> getDutyWithDoctors() {
        return dutyWithDoctors;
    }

    public void setDutyWithDoctors(List<Doctor> dutyWithDoctors) {
        this.dutyWithDoctors = dutyWithDoctors;
    }

    public boolean isCheckedDoc() {
        return checkedDoc;
    }

    public void setCheckedDoc(boolean checkedDoc) {
        this.checkedDoc = checkedDoc;
    }

    public List<MedicalService> getDoctorMedicalSrvcList() {
        return doctorMedicalSrvcList;
    }

    public void setDoctorMedicalSrvcList(List<MedicalService> doctorMedicalSrvcList) {
        this.doctorMedicalSrvcList = doctorMedicalSrvcList;
    }

    public List<MedicalServiceWrapper> getDoctorServiceComission() {
        return doctorServiceComission;
    }

    public void setDoctorServiceComission(List<MedicalServiceWrapper> doctorServiceComission) {
        this.doctorServiceComission = doctorServiceComission;
    }

    public List<ServiceComission> getComissionServices() {
        return comissionServices;
    }

    public void setComissionServices(List<ServiceComission> comissionServices) {
        this.comissionServices = comissionServices;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getSendBillingReport() {
        return sendBillingReport;
    }

    public void setSendBillingReport(Boolean sendBillingReport) {
        this.sendBillingReport = sendBillingReport;
    }

    public Boolean getUseReceptDashboard() {
        return useReceptDashboard;
    }

    public void setUseReceptDashboard(Boolean useReceptDashboard) {
        this.useReceptDashboard = useReceptDashboard;
    }

    public Boolean getOtherDoctorDashBoard() {
        return otherDoctorDashBoard;
    }

    public void setOtherDoctorDashBoard(Boolean otherDoctorDashBoard) {
        this.otherDoctorDashBoard = otherDoctorDashBoard;
    }

    public List<DutyShift> getShift1() {
        return shift1;
    }

    public void setShift1(List<DutyShift> shift1) {
        this.shift1 = shift1;
    }

    public List<DutyShift> getShift2() {
        return shift2;
    }

    public void setShift2(List<DutyShift> shift2) {
        this.shift2 = shift2;
    }

    public String getFormatedTime() {
        return formatedTime;
    }

    public void setFormatedTime(String formatedTime) {
        this.formatedTime = formatedTime;
    }

    public String getFormatedDate() {
        return formatedDate;
    }

    public void setFormatedDate(String formatedDate) {
        this.formatedDate = formatedDate;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Boolean getReceivePayment() {
        return receivePayment;
    }

    public void setReceivePayment(Boolean receivePayment) {
        this.receivePayment = receivePayment;
    }

    public Double getAllowDiscount() {
        return allowDiscount;
    }

    public void setAllowDiscount(Double allowDiscount) {
        this.allowDiscount = allowDiscount;
    }

    public Boolean getCanAccessPatientRecord() {
        return canAccessPatientRecord;
    }

    public void setCanAccessPatientRecord(Boolean canAccessPatientRecord) {
        this.canAccessPatientRecord = canAccessPatientRecord;
    }

    public Boolean getAllowDiscountCheck() {
        return allowDiscountCheck;
    }

    public void setAllowDiscountCheck(Boolean allowDiscountCheck) {
        this.allowDiscountCheck = allowDiscountCheck;
    }
}