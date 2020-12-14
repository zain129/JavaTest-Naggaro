package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.enums.DutyShiftEnum;
import com.zainimtiaz.nagarro.enums.ModuleEnum;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.zainimtiaz.nagarro.model.*;
import com.zainimtiaz.nagarro.repository.*;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.DepartmentWrapper;
import com.zainimtiaz.nagarro.wrapper.ServiceComission;
import com.zainimtiaz.nagarro.wrapper.request.DoctorPaymentRequestWrapper;
import com.zainimtiaz.nagarro.wrapper.request.StaffRequestWrapper;
import com.zainimtiaz.nagarro.wrapper.response.DoctorPaymentResponseWrapper;
import com.zainimtiaz.nagarro.wrapper.response.StaffResponseWrapper;
import com.zainimtiaz.nagarro.wrapper.response.StaffWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.zainimtiaz.nagarro.enums.UserTypeEnum.*;

@Service
public class StaffService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    BranchRepository branchRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    BranchCashierRepository branchCashierRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    CashierRepository cashierRepository;
    @Autowired
    NurseRepository nurseRepository;
    @Autowired
    HISUtilService hisUtilService;
    @Autowired
    ReceptionistRepository receptionistRepository;
    @Autowired
    BranchReceptionistRepository branchReceptionistRepository;
    @Autowired
    BranchNurseRepository branchNurseRepository;
    @Autowired
    NurseWithDoctorRepository nurseWithDoctorRepository;
    @Autowired
    DutyShiftRepository dutyShiftRepository;
    @Autowired
    BranchDoctorRepository branchDoctorRepository;
    @Autowired
    BranchService branchService;
    @Autowired
    StaffService staffService;
    @Autowired
    BranchDepartmentRepository branchDepartmentRepository;
    @Autowired
    MedicalServiceRepository medicalServiceRepository;
    @Autowired
    DoctorMedicalServiceRepository doctorMedicalServiceRepository;
    @Autowired
    NurseDepartmentRepository nurseDepartmentRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    StaffPaymentRepository staffPaymentRepository;
    @Autowired
    PaymentTypeRepository paymentTypeRepository;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private AppointmentRepository appointmentRepository;



    List<StaffWrapper> finalStaffList = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(StaffService.class);

    @Transactional
    public User saveUser(StaffRequestWrapper createRequest) {
        String usertype = createRequest.getUserType();
        Branch branch = branchRepository.findOne(createRequest.getPrimaryBranch());
        //  UserDutyShift userDutyShift = new UserDutyShift();
        UserRole userRole = null;//new UserRole();
        // String brName = branch.getName();
        User user = null;
        String prefID = hisUtilService.generatePrefix(ModuleEnum.PROFILE);

        if (usertype.equalsIgnoreCase("CASHIER")) {
            user = new User();
            user.setActive(true);
            user.setPassword(new BCryptPasswordEncoder().encode(createRequest.getPassword()));
            user.setUsername(createRequest.getUserName());
            user.setUserType("CASHIER");
            userRepository.save(user);

            Role role = roleRepository.findByName(createRequest.getUserType().toUpperCase());
            /*List<UserRole> recAssignedRole = new ArrayList<>();
            if (!HISCoreUtil.isListEmpty(allRoles)) {*/
            if (role != null) {
                //for (Role userAllowRole : allRoles) {
                userRole = new UserRole();
                    /*userRole1.setRole(userAllowRole);
                    userRole1.setUser(user);
                    recAssignedRole.add(userRole1);*/
                userRole.setRole(role);
                userRole.setUser(user);
                //}
                userRoleRepository.save(userRole);//recAssignedRole);
            }

            /*List<Role> allRoles = roleRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedRoles()));
            List<UserRole> cashAssignedRole = new ArrayList<>();
            if (!HISCoreUtil.isListEmpty(allRoles)) {
                for (Role userAllowRole : allRoles) {
                    UserRole userRole1 = new UserRole();
                    userRole1.setRole(userAllowRole);
                    userRole1.setUser(user);
                    cashAssignedRole.add(userRole1);
                }
                userRoleRepository.save(cashAssignedRole);
            }*/

            Cashier cashier = new Cashier();
            cashier.setProfileId(hisUtilService.getPrefixId(ModuleEnum.PROFILE));
            cashier.setCellPhone(createRequest.getCellPhone());
            cashier.setHomePhone(createRequest.getHomePhone());
            cashier.setEmail(createRequest.getEmail());
            cashier.setFirstName(createRequest.getFirstName());
            cashier.setLastName(createRequest.getLastName());
            cashier.setAllowDiscountCheck(createRequest.isAllowDiscountCheck());
            cashier.setCanAccessPatientRecord(createRequest.isCanAccessPatientRecord());
            cashier.setHidePatientPhoneNumber(createRequest.isHidePatientPhoneNumber());
            cashier.setProfileId(prefID);
            cashier.setUser(user);

            cashier.setActive(createRequest.isActive());
            cashier.setSendBillingReport(createRequest.isSendBillingReport());
            cashier.setUseReceiptDashboard(createRequest.isUseReceptDashboard());
            cashier.setOtherDoctorDashboard(createRequest.isOtherDoctorDashBoard());
            cashier.setAllowDiscount(createRequest.getAllowDiscount());
            cashier.setAccountExpiry(HISCoreUtil.convertToDate(createRequest.getAccountExpiry()));
            cashier.setCanReceivePayment(createRequest.isReceivePayment());

            if (createRequest.getSelectedDoctorDashboard() != null) {
                List<Long> listOfDrDashboards = Arrays.asList(createRequest.getSelectedDoctorDashboard());
                List<Doctor> selectedDashboardDoctor = doctorRepository.findAllByIdIn(listOfDrDashboards);
                Map<Long, String> dashMap = new HashMap<>();
                selectedDashboardDoctor.stream()
                        .filter(x -> x.getId() != null)
                        .forEach(x -> dashMap.put(x.getId(), x.getEmail()));
                cashier.setSelectedDoctorDashboard(dashMap);
            }

            List<Branch> allowBranches = branchRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedVisitBranches()));
            List<BranchCashier> cashierVisitBranchesData = new ArrayList<>();
            cashierRepository.save(cashier);
            if (!HISCoreUtil.isListEmpty(allowBranches)) {
                for (Branch userVisitBr : allowBranches) {
                    if (userVisitBr.getId() == branch.getId())
                        continue;
                    BranchCashier userVisitBranches = new BranchCashier();
                    userVisitBranches.setBranch(userVisitBr);
                    userVisitBranches.setCashier(cashier);
                    userVisitBranches.setPrimaryBranch(false);
                    cashierVisitBranchesData.add(userVisitBranches);
                }
                branchCashierRepository.save(cashierVisitBranchesData);
            }

            BranchCashier branchCashier = new BranchCashier();
            branchCashier.setBranch(branch);
            branchCashier.setPrimaryBranch(true);
            branchCashier.setCashier(cashier);
            branchCashierRepository.save(branchCashier);

            // cashier.setBranchCashiers(cashierVisitBranchesData);
            hisUtilService.updatePrefix(ModuleEnum.PROFILE);
            return user;
        }

        if (usertype.equalsIgnoreCase(RECEPTIONIST.name())) {
            user = new User();
            user.setActive(true);
            user.setPassword(new BCryptPasswordEncoder().encode(createRequest.getPassword()));
            user.setUsername(createRequest.getUserName());
            user.setUserType("RECEPTIONIST");
            userRepository.save(user);

            Role role = roleRepository.findByName(createRequest.getUserType().toUpperCase());
            /*List<UserRole> recAssignedRole = new ArrayList<>();
            if (!HISCoreUtil.isListEmpty(allRoles)) {*/
            if (role != null) {
                //for (Role userAllowRole : allRoles) {
                userRole = new UserRole();
                    /*userRole1.setRole(userAllowRole);
                    userRole1.setUser(user);
                    recAssignedRole.add(userRole1);*/
                userRole.setRole(role);
                userRole.setUser(user);
                //}
                userRoleRepository.save(userRole);//recAssignedRole);
            }

            /*List<Role> allRoles = roleRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedRoles()));
            List<UserRole> recAssignedRole = new ArrayList<>();
            if (!HISCoreUtil.isListEmpty(allRoles)) {
                for (Role userAllowRole : allRoles) {
                    UserRole userRole1 = new UserRole();
                    userRole1.setRole(userAllowRole);
                    userRole1.setUser(user);
                    recAssignedRole.add(userRole1);
                }
                userRoleRepository.save(recAssignedRole);
            }*/

            Receptionist receptionist = new Receptionist();
            receptionist.setProfileId(prefID);
            receptionist.setCellPhone(createRequest.getCellPhone());
            receptionist.setHomePhone(createRequest.getHomePhone());
            receptionist.setEmail(createRequest.getEmail());
            receptionist.setStatus(createRequest.isActive());
            receptionist.setFirstName(createRequest.getFirstName());
            receptionist.setLastName(createRequest.getLastName());
            receptionist.setCanAccessPatientRecord(createRequest.isCanAccessPatientRecord());
            receptionist.setAllowDiscountCheck(createRequest.isAllowDiscountCheck());
            receptionist.setHidePatientPhoneNumber(createRequest.isHidePatientPhoneNumber());
            receptionist.setUser(user);

            receptionist.setActive(createRequest.isActive());
            receptionist.setSendBillingReport(createRequest.isSendBillingReport());
            receptionist.setUseReceiptDashboard(createRequest.isUseReceptDashboard());
            receptionist.setOtherDoctorDashboard(createRequest.isOtherDoctorDashBoard());
            receptionist.setAllowDiscount(createRequest.getAllowDiscount());
            receptionist.setCanReceivePayment(createRequest.isReceivePayment());
            if (createRequest.getSelectedDoctorDashboard() != null) {
                List<Long> listOfDrDashboards = Arrays.asList(createRequest.getSelectedDoctorDashboard());
                List<Doctor> selectedDashboardDoctor = doctorRepository.findAllByIdIn(listOfDrDashboards);
                Map<Long, String> dashMap = new HashMap<>();
                selectedDashboardDoctor.stream()
                        .filter(x -> x.getId() != null)
                        .forEach(x -> dashMap.put(x.getId(), x.getEmail()));
                receptionist.setSelectedDoctorDashboard(dashMap);
            }

            receptionist.setAccountExpiry(HISCoreUtil.convertToDate(createRequest.getAccountExpiry()));
            List<Long> listbr = Arrays.asList(createRequest.getSelectedVisitBranches());
            List<Branch> allowBranches = branchRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedVisitBranches()));
            List<BranchReceptionist> receptionistVisitBranchesData = new ArrayList<>();
            receptionistRepository.save(receptionist);
            if (!HISCoreUtil.isListEmpty(allowBranches)) {
                for (Branch userVisitBr : allowBranches) {
                    BranchReceptionist userVisitBranches = new BranchReceptionist();
                    userVisitBranches.setBranch(userVisitBr);
                    userVisitBranches.setReceptionist(receptionist);
                    userVisitBranches.setPrimaryBranch(false);
                    receptionistVisitBranchesData.add(userVisitBranches);
                }
                branchReceptionistRepository.save(receptionistVisitBranchesData);
            }


            BranchReceptionist branchReceptionist = new BranchReceptionist();
            branchReceptionist.setBranch(branch);
            branchReceptionist.setPrimaryBranch(true);
            branchReceptionist.setReceptionist(receptionist);
            branchReceptionistRepository.save(branchReceptionist);
            hisUtilService.updatePrefix(ModuleEnum.PROFILE);
            // cashier.setBranchCashiers(cashierVisitBranchesData);
            return user;
        }

        if (usertype.equalsIgnoreCase(NURSE.name())) {
            user = new User();
            user.setActive(true);
            user.setPassword(new BCryptPasswordEncoder().encode(createRequest.getPassword()));
            user.setUsername(createRequest.getUserName());
            user.setUserType("NURSE");
            userRepository.save(user);
            /*List<Role> allRoles = roleRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedRoles()));
            List<UserRole> recAssignedRole = new ArrayList<>();*/
            Role role = roleRepository.findByName(createRequest.getUserType().toUpperCase());
            /*List<UserRole> recAssignedRole = new ArrayList<>();
            if (!HISCoreUtil.isListEmpty(allRoles)) {*/
            if (role != null) {
                //for (Role userAllowRole : allRoles) {
                userRole = new UserRole();
                    /*userRole1.setRole(userAllowRole);
                    userRole1.setUser(user);
                    recAssignedRole.add(userRole1);*/
                userRole.setRole(role);
                userRole.setUser(user);
                //}
                userRoleRepository.save(userRole);//recAssignedRole);
            }
            /*if (role!=null) {//!HISCoreUtil.isListEmpty(allRoles)) {
                //for (Role userAllowRole : allRoles) {
                    UserRole userRole1 = new UserRole();
                    userRole1.setRole(userAllowRole);
                    userRole1.setUser(user);
                    recAssignedRole.add(userRole1);
                //}
                userRoleRepository.save(recAssignedRole);
            }*/
            Nurse nurse = new Nurse();
            nurse.setUser(user);
            nurse.setProfileId(prefID);
            nurse.setProfileId(hisUtilService.getPrefixId(ModuleEnum.PROFILE));
            nurse.setCellPhone(createRequest.getCellPhone());
            nurse.setHomePhone(createRequest.getHomePhone());
            nurse.setEmail(createRequest.getEmail());
            nurse.setStatus(createRequest.isActive());
            nurse.setFirstName(createRequest.getFirstName());
            nurse.setLastName(createRequest.getLastName());
            nurse.setUser(user);
            nurse.setAccountExpiry(HISCoreUtil.convertToDate(createRequest.getAccountExpiry()));
            nurse.setHidePatientPhoneNumber(createRequest.isHidePatientPhoneNumber());
            nurse.setActive(createRequest.isActive());
            nurse.setSendBillingReport(createRequest.isSendBillingReport());
            nurse.setUseReceiptDashboard(createRequest.isUseReceptDashboard());
            nurse.setOtherDoctorDashboard(createRequest.isOtherDoctorDashBoard());
            nurse.setManagePatientRecords(createRequest.isManagePatientRecords());
            nurse.setManagePatientRecords(createRequest.isManagePatientInvoices());
            //     nurse.setManagePatientInvoices(createRequest.isManagePatientInvoices());
            //      nurse.setManagePatientRecords(createRequest.isManagePatientRecords());

            /*List<ClinicalDepartment> clinicalDepartments = clinicalDepartmentRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedDepartment()));
            List<DepartmentUser> departmentUserListData = new ArrayList<>();
            for (ClinicalDepartment clinicalDepartment : clinicalDepartments) {
                DepartmentUser departmentUser = new DepartmentUser();
                departmentUser.setClinicalDepartment(clinicalDepartment);
                departmentUser.setUser(user);
                departmentUser.setCreatedOn(System.currentTimeMillis());
                departmentUser.setUpdatedOn(System.currentTimeMillis());
                departmentUser.setDeleted(false);
                departmentUserListData.add(departmentUser);
            }
            departmentUserRepository.save(departmentUserListData);
*/
            List<Branch> allowBranches = branchRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedVisitBranches()));
            List<BranchNurse> nurseVisitBranchesData = new ArrayList<>();
            if (createRequest.getSelectedDoctorDashboard() != null) {
                List<Long> listOfDrDashboards = Arrays.asList(createRequest.getSelectedDoctorDashboard());
                List<Doctor> selectedDashboardDoctor = doctorRepository.findAllByIdIn(listOfDrDashboards);
                Map<Long, String> dashMap = new HashMap<>();
                selectedDashboardDoctor.stream()
                        .filter(x -> x.getId() != null)
                        .forEach(x -> dashMap.put(x.getId(), x.getEmail()));
                nurse.setSelectedDoctorDashboard(dashMap);
            }

            nurseRepository.save(nurse);

            //nurse departments portion
            List<Department> selectedDept = departmentRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedDepartment()));
            List<NurseDepartment> nurseDepartmentList = new ArrayList<>();
            if (!HISCoreUtil.isListEmpty(selectedDept)) {
                for (Department dept : selectedDept) {
                    NurseDepartment nurseDept = new NurseDepartment();
                    nurseDept.setDepartment(dept);
                    nurseDept.setNurse(nurse);
                    nurseDepartmentList.add(nurseDept);
                }

            }
            nurseDepartmentRepository.save(nurseDepartmentList);
            //branch nurse portion
            BranchNurse branchNurse = new BranchNurse();
            branchNurse.setBranch(branch);
            branchNurse.setPrimaryBranch(true);
            branchNurse.setNurse(nurse);
            branchNurseRepository.save(branchNurse);
            if (!HISCoreUtil.isListEmpty(allowBranches)) {
                for (Branch userVisitBr : allowBranches) {
                    if (userVisitBr.getId() == branch.getId())
                        continue;
                    BranchNurse userVisitBranches = new BranchNurse();
                    userVisitBranches.setBranch(userVisitBr);
                    userVisitBranches.setNurse(nurse);
                    userVisitBranches.setPrimaryBranch(false);
                    nurseVisitBranchesData.add(userVisitBranches);
                }
                branchNurseRepository.save(nurseVisitBranchesData);
            }
            List<User> doctorsList = userRepository.findAllByIdIn(Arrays.asList(createRequest.getDutyWithDoctors()));
            List<Doctor> doctors = doctorRepository.findAllByUserIn(doctorsList);
            List<NurseWithDoctor> dutyWithDoctorsData = new ArrayList<>();
            for (Doctor docUser : doctors) {
                NurseWithDoctor dutyWithDoctor1 = new NurseWithDoctor();
                dutyWithDoctor1.setNurse(nurse);
                dutyWithDoctor1.setDoctor(docUser);
                dutyWithDoctorsData.add(dutyWithDoctor1);
            }
            nurseWithDoctorRepository.save(dutyWithDoctorsData);
            hisUtilService.updatePrefix(ModuleEnum.PROFILE);
            return user;
        }

        if (usertype.equalsIgnoreCase(DOCTOR.name())) {
            Organization dbOrganization = organizationService.getAllOrgizationData();
            String zone = dbOrganization.getZone().getName().replaceAll("\\s", "");
            String shift1From = HISCoreUtil.convertTimeTozone(createRequest.getFirstShiftFromTime(), zone);
            String shift1To = HISCoreUtil.convertTimeTozone(createRequest.getFirstShiftToTime(), zone);

            user = new User();
            user.setActive(true);
            user.setPassword(new BCryptPasswordEncoder().encode(createRequest.getPassword()));
            user.setUsername(createRequest.getUserName());
            user.setUserType("DOCTOR");
            userRepository.save(user);
            //commenting becz roles are removed from staff creation page
            //List<Role> allRoles = roleRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedRoles()));
            Role role = roleRepository.findByName(createRequest.getUserType().toUpperCase());
            /*List<UserRole> recAssignedRole = new ArrayList<>();
            if (!HISCoreUtil.isListEmpty(allRoles)) {*/
            if (role != null) {
                //for (Role userAllowRole : allRoles) {
                userRole = new UserRole();
                    /*userRole1.setRole(userAllowRole);
                    userRole1.setUser(user);
                    recAssignedRole.add(userRole1);*/
                userRole.setRole(role);
                userRole.setUser(user);
                //}
                userRoleRepository.save(userRole);//recAssignedRole);
            }
            Doctor doctor = new Doctor();
            doctor.setUser(user);
            doctor.setFirstName(createRequest.getFirstName());
            doctor.setLastName(createRequest.getLastName());
            doctor.setProfileId(prefID);
            doctor.setProfileId(hisUtilService.getPrefixId(ModuleEnum.PROFILE));
            doctor.setCellPhone(createRequest.getCellPhone());
            doctor.setHomePhone(createRequest.getHomePhone());
            doctor.setCheckUpInterval(createRequest.getInterval());
            doctor.setEmail(createRequest.getEmail());
            doctor.setCanAccessPatientRecord(createRequest.isCanAccessPatientRecord());
            doctor.setAllowDiscountCheck(createRequest.isAllowDiscountCheck());
            doctor.setStatus(createRequest.isActive());
            if (!HISCoreUtil.isNull(createRequest.getAccountExpiry()))
                doctor.setAccountExpiry(HISCoreUtil.convertToDate(createRequest.getAccountExpiry()));
            if (!HISCoreUtil.isNull(createRequest.getDateFrom()) && !HISCoreUtil.isNull(createRequest.getDateTo())) {
                doctor.setVacation(createRequest.isVacation());
                String zonedFrom = HISCoreUtil.convertDateToTimeZone(HISCoreUtil.convertToDate(createRequest.getDateFrom()), "YYYY-MM-dd", zone);
                String zonedTo = HISCoreUtil.convertDateToTimeZone(HISCoreUtil.convertToDate(createRequest.getDateTo()), "YYYY-MM-dd", zone);
                doctor.setVacationFrom(HISCoreUtil.convertToDateOnly(zonedFrom)); //write date format only
                doctor.setVacationTO(HISCoreUtil.convertToDateOnly(zonedTo));
            }

            doctor.setActive(createRequest.isActive());
            doctor.setSendBillingReport(createRequest.isSendBillingReport());
            doctor.setUseReceiptDashboard(createRequest.isUseReceptDashboard());
            doctor.setOtherDoctorDashboard(createRequest.isOtherDoctorDashBoard());
            doctor.setAllowDiscount(createRequest.getAllowDiscount());
            doctor.setCanReceivePayment(createRequest.isReceivePayment());
            doctor.setHidePatientPhoneNumber(createRequest.isHidePatientPhoneNumber());
            List<String> daysList = Arrays.asList(createRequest.getSelectedWorkingDays());
            doctor.setWorkingDays(daysList);

            //doctor department portion
            Department selectedDocDept = null;
            if (createRequest.getSelectedDepartment().length > 0) {
                selectedDocDept = departmentRepository.findOne((createRequest.getSelectedDepartment())[0]);
            }
            if (createRequest.getSelectedDoctorDashboard() != null) {
                List<Long> listOfDrDashboards = Arrays.asList(createRequest.getSelectedDoctorDashboard());
                List<Doctor> selectedDashboardDoctor = doctorRepository.findAllByIdIn(listOfDrDashboards);
                Map<Long, String> dashMap = new HashMap<>();
                selectedDashboardDoctor.stream()
                        .filter(x -> x.getId() != null)
                        .forEach(x -> dashMap.put(x.getId(), x.getEmail()));

                doctor.setSelectedDoctorDashboard(dashMap);
            }
            doctor.setDepartment(selectedDocDept);
            doctorRepository.save(doctor);
//            profile.setWorkingDays(daysList);
            //duty shift portion
            DutyShift dutyShift = new DutyShift();
            dutyShift.setShiftName(DutyShiftEnum.SHIFT1);
            //  dutyShift.setStartTime(HISCoreUtil.convertToTime(createRequest.getFirstShiftFromTime()));
            //  dutyShift.setEndTime(HISCoreUtil.convertToTime(createRequest.getFirstShiftToTime()));
            dutyShift.setStartTime(HISCoreUtil.convertToTime(shift1From));
            dutyShift.setEndTime(HISCoreUtil.convertToTime(shift1To));
            dutyShift.setDoctor(doctor);
            dutyShiftRepository.save(dutyShift);

            if (!HISCoreUtil.isNull(createRequest.getSecondShiftFromTime())) {
                String shift2From = HISCoreUtil.convertTimeTozone(createRequest.getSecondShiftFromTime(), zone);
                String shift2To = HISCoreUtil.convertTimeTozone(createRequest.getSecondShiftToTime(), zone);
                DutyShift dutyShift2 = new DutyShift();
                dutyShift2.setShiftName(DutyShiftEnum.SHIFT2);
                dutyShift2.setStartTime(HISCoreUtil.convertToTime(shift2From));
                dutyShift2.setEndTime(HISCoreUtil.convertToTime(shift2To));
                dutyShift2.setDoctor(doctor);
                dutyShiftRepository.save(dutyShift2);
            }

            List<Branch> allowBranches = branchRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedVisitBranches()));
            List<BranchDoctor> nurseVisitBranchesData = new ArrayList<>();
            if (!HISCoreUtil.isListEmpty(allowBranches)) {
                for (Branch userVisitBr : allowBranches) {
                    if (userVisitBr.getId() == branch.getId())
                        continue;
                    BranchDoctor userVisitBranches = new BranchDoctor();
                    userVisitBranches.setBranch(userVisitBr);
                    userVisitBranches.setDoctor(doctor);
                    userVisitBranches.setPrimaryBranch(false);
                    nurseVisitBranchesData.add(userVisitBranches);
                }
                branchDoctorRepository.save(nurseVisitBranchesData);
            }

            //doctor services portion
            List<Long> selectedServices = createRequest.getServiceComission().stream().map(x -> x.getId()).collect(Collectors.toList());
            List<MedicalService> medicalServicesList = medicalServiceRepository.findAllByIdIn(selectedServices);
            List<DoctorMedicalService> doctorMedicalServiceList = new ArrayList<>();
            Optional<Double> serviceCom = null;
            if (!HISCoreUtil.isListEmpty(medicalServicesList)) {
                for (MedicalService ms : medicalServicesList) {
                    DoctorMedicalService dms = new DoctorMedicalService();
                    dms.setDoctor(doctor);
                    dms.setMedicalService(ms);
                    serviceCom = this.getComissionOfServices(createRequest.getServiceComission(), ms.getId());
                    if (serviceCom.isPresent())
                        dms.setComission(serviceCom.get());
                    doctorMedicalServiceList.add(dms);
                }
            }
            doctorMedicalServiceRepository.save(doctorMedicalServiceList);

            //branch doctor portion
            BranchDoctor branchDoctor = new BranchDoctor();
            branchDoctor.setBranch(branch);
            branchDoctor.setPrimaryBranch(true);
            branchDoctor.setDoctor(doctor);
            branchDoctorRepository.save(branchDoctor);
            /*List<MedicalService> medicalServiceslist = medicalServicesRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedServices()));
            List<UserMedicalService> userDetailsServicesData = new ArrayList<>();
            for (MedicalService mdService : medicalServiceslist) {
                UserMedicalService userMedicalService = new UserMedicalService();
                userMedicalService.setMedicalService(mdService);
                userMedicalService.setUser(user);
                userDetailsServicesData.add(userMedicalService);
            }
            userMedicalServiceRepository.save(userDetailsServicesData);
                */

            /*List<ClinicalDepartment> clinicalDepartments = clinicalDepartmentRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedDepartment()));
            List<DepartmentUser> docDepartmentUser = new ArrayList<>();
            for (ClinicalDepartment clinicalDepartment : clinicalDepartments) {
                DepartmentUser departmentUser = new DepartmentUser();
                departmentUser.setClinicalDepartment(clinicalDepartment);
                departmentUser.setUser(user);
                departmentUser.setCreatedOn(System.currentTimeMillis());
                departmentUser.setUpdatedOn(System.currentTimeMillis());
                departmentUser.setDeleted(false);
                docDepartmentUser.add(departmentUser);
            }
            departmentUserRepository.save(docDepartmentUser);
*/
            hisUtilService.updatePrefix(ModuleEnum.PROFILE);
            return user;
        }
        return null;
    }

    private Optional<Double> getComissionOfServices(List<ServiceComission> list, long id) {
        return Optional.ofNullable(list).orElseGet(Collections::emptyList).stream().filter(x -> x.getComission() != null).filter(x -> x.getId() == id).map(x -> x.getComission()).findFirst();
    }

    public User findById(long id) {
        return userRepository.findOne(id);
    }


    public StaffResponseWrapper findByIdAndResponse(long id, String userType) {
        StaffResponseWrapper staffResponseWrapper = null;
        if (userType.equalsIgnoreCase("CASHIER")) {
            staffResponseWrapper = cashierRepository.findAllByIdAndStatusActive(id);
            staffResponseWrapper.setStaffBranches(branchCashierRepository.getCashierBranches(id));
            return staffResponseWrapper;//cashierRepository.findAllByIdAndStatusActive(id);
        }
        if (userType.equalsIgnoreCase("RECEPTIONIST")) {
            //return cashierRepository.findAllByIdAndStatusActive(id);
            staffResponseWrapper = receptionistRepository.findAllByIdAndStatusActive(id);
            staffResponseWrapper.setStaffBranches(branchReceptionistRepository.getReceptionistBranches(id));
            return staffResponseWrapper;//cashierRepository.findAllByIdAndStatusActive(id);
        }
        if (userType.equalsIgnoreCase("DOCTOR")) {
            staffResponseWrapper = doctorRepository.findAllByIdAndStatusActive(id);
            staffResponseWrapper.setStaffBranches(branchDoctorRepository.getDoctorBranches(id));
            staffResponseWrapper.setDoctorServiceComission(doctorMedicalServiceRepository.getDocServicesAndComissions(id));
            return staffResponseWrapper;
        }
        if (userType.equalsIgnoreCase("NURSE")) {
            staffResponseWrapper = nurseRepository.findAllByIdAndStatusActive(id);
            Nurse nurse = nurseRepository.findOne(id);
            //branchService.getAllActiveBranches();
            staffResponseWrapper.setStaffBranches(branchNurseRepository.getNurseBranches(id));
            staffResponseWrapper.setNurseDepartmentList(nurseDepartmentRepository.getNurseDepartments(id));
            //staffService.findByRole("DOCTOR");
            staffResponseWrapper.setDutyWithDoctors(nurseWithDoctorRepository.findNurseWithDoctors(id));

            return staffResponseWrapper;
        }

      /* User user = userRepository.findAllById(id);
        String userType = user.getUserType();
        UserResponseWrapper userResponseWrapper = new UserResponseWrapper(user);

        userResponseWrapper.setProfile(user.getProfile());
        BranchUser branchUser = branchUserRepository.findByUser(user);
       //  if (branchUser.isPrimaryBranch()) {
        BranchWrapper branchWrapper = new BranchWrapper(branchUser);
        userResponseWrapper.setBranch(branchWrapper);
        //}
        List<Branch> visitBranchesList = new ArrayList<>();
        for (UserVisitBranches userVisitBranches : user.getUserVisitBranches()) {
            visitBranchesList.add(userVisitBranches.getBranch());
            userResponseWrapper.setVisitBranches(visitBranchesList);
        }
        if (userType.equalsIgnoreCase("CASHIER")) {

        }

        *//*if (userType.equalsIgnoreCase("doctor")) {
            UserDutyShift userDutyShift = userDutyShiftRepository.findByUser(user);
            Vacation vacation = vacationRepository.findByUser(user);
            userResponseWrapper.setDutyShift(userDutyShift.getDutyShift());
            userResponseWrapper.setVacation(vacation);
            List<ClinicalDepartment> clinicalDepartmentList = new ArrayList<>();
            for (DepartmentUser departmentUser : user.getDepartmentsActive()) {
                clinicalDepartmentList.add(departmentUser.getClinicalDepartment());
               userResponseWrapper.setClinicalDepartments(clinicalDepartmentList);
           }

        }
        if (userType.equalsIgnoreCase("nurse")) {
            List<ClinicalDepartment> nurseDepartList = new ArrayList<>();
            List<DutyWithDoctor> nurseDutyWithDoctorsList = new ArrayList<>();
            for (DepartmentUser nurseDeptUser : user.getDepartmentsActive()) {
                nurseDepartList.add(nurseDeptUser.getClinicalDepartment());
                userResponseWrapper.setClinicalDepartments(nurseDepartList);
            }
            for (DutyWithDoctor nurseDutyWithDoctor : user.getDoctor()) {
                nurseDutyWithDoctorsList.add(nurseDutyWithDoctor);
                userResponseWrapper.setDutyWithDoctors(nurseDutyWithDoctorsList);
            }
        }
*//*
        return userResponseWrapper;*/
        return null;
    }

    public List<Role> getAllActiveRoles() {
        return roleRepository.findAllByActiveTrue();
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public int totalUser() {
        return userRepository.countAllByActiveTrue();
    }

    /* public List<StaffResponseWrapper> findAllStaff(int offset, int limit) {

         Pageable pageable = new PageRequest(offset, limit);
         List<StaffResponseWrapper> users = new ArrayList<>();
         //  BranchWrapper branches = null;
         List<User> allUser = userRepository.findAllByActiveTrue(pageable);
         for (User user : allUser) {
             StaffResponseWrapper userWrapper = new StaffResponseWrapper(user);
             *//*for (BranchUser branchUser : user.getBranches()) {
                if (branchUser.isPrimaryBranch()) {
                    BranchWrapper branchWrapper = new BranchWrapper(branchUser);
                    branches = branchWrapper;
                }
            }
            userWrapper.setBranch(branches);
*//*            users.add(userWrapper);
        }
        return users;
    }*/
    public List<StaffWrapper> findAllStaff(int offset, int limit) {

        Pageable pageable = new PageRequest(offset, limit);
        List<StaffWrapper> drStaffList = doctorRepository.findAllByActive(pageable);
        List<StaffWrapper> crStaffList = cashierRepository.findAllByActive(pageable);
        List<StaffWrapper> rtStaffList = receptionistRepository.findAllByActive(pageable);
        List<StaffWrapper> nrStaffList = nurseRepository.findAllByActive(pageable);
        finalStaffList = Stream.of(drStaffList, crStaffList, rtStaffList, nrStaffList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return finalStaffList;
    }

    public List<StaffWrapper> findAllStaffWithoutPagination() {
        List<StaffWrapper> drStaffList = doctorRepository.findAllByActive();
        List<StaffWrapper> crStaffList = cashierRepository.findAllByActive();
        List<StaffWrapper> rtStaffList = receptionistRepository.findAllByActive();
        List<StaffWrapper> nrStaffList = nurseRepository.findAllByActive();
        finalStaffList = Stream.of(drStaffList, crStaffList, rtStaffList, nrStaffList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return finalStaffList;
    }

    public int countAllStaff() {
        return finalStaffList.size();
    }

    @Transactional
    public User updateStaffData(StaffRequestWrapper createRequest, User alreadyExistsUser) {
        switch (alreadyExistsUser.getUserType().toUpperCase()) {
            case "DOCTOR":
                Organization dbOrganization = organizationService.getAllOrgizationData();
                String zone = dbOrganization.getZone().getName().replaceAll("\\s", "");
                String shift1From = HISCoreUtil.convertTimeTozone(createRequest.getFirstShiftFromTime(), zone);
                String shift1To = HISCoreUtil.convertTimeTozone(createRequest.getFirstShiftToTime(), zone);

                Doctor doctor = doctorRepository.findByUser(alreadyExistsUser);
                alreadyExistsUser.setActive(createRequest.isActive());
                userRepository.save(alreadyExistsUser);
                doctor.getDoctorMedicalServices().clear();
                // doctor.setEmail(createRequest.getEmail());
                doctor.setHomePhone(createRequest.getHomePhone());
                doctor.setCellPhone(createRequest.getCellPhone());
                doctor.setAccountExpiry(HISCoreUtil.convertToDate(createRequest.getAccountExpiry()));
                doctor.setLastName(createRequest.getLastName());
                doctor.setFirstName(createRequest.getFirstName());
                doctor.setVacation(createRequest.isVacation());
                doctor.setStatus(createRequest.isActive());
                doctor.setVacationFrom(HISCoreUtil.convertToDate(createRequest.getDateFrom()));
                doctor.setVacationTO(HISCoreUtil.convertToDate(createRequest.getDateTo()));
                doctor.setCheckUpInterval(createRequest.getInterval());
                doctor.setCanReceivePayment(createRequest.isReceivePayment());
                doctor.setCanAccessPatientRecord(createRequest.isCanAccessPatientRecord());
                doctor.setAllowDiscountCheck(createRequest.isAllowDiscountCheck());
                doctor.setAllowDiscount(createRequest.getAllowDiscount());
                doctor.setOtherDoctorDashboard(createRequest.isOtherDoctorDashBoard());
                doctor.setUseReceiptDashboard(createRequest.isUseReceptDashboard());
                doctor.setHidePatientPhoneNumber(createRequest.isHidePatientPhoneNumber());
                //doctor working days
                List<String> daysList = new LinkedList<String>(Arrays.asList(createRequest.getSelectedWorkingDays()));
                if (!HISCoreUtil.isListEmpty(daysList)) {
                    doctor.setWorkingDays(daysList);
                }

                //doctor department portion
                Department selectedDocDept = null;
                if (createRequest.getSelectedDepartment().length > 0) {
                    //departmentRepository.findOne( (createRequest.getSelectedDepartment())[0] );
                    selectedDocDept = departmentRepository.findOne((createRequest.getSelectedDepartment())[0]);
                    doctor.setDepartment(selectedDocDept);
                }
                if (createRequest.getSelectedDoctorDashboard() != null) {
                    List<Long> listOfDrDashboards = Arrays.asList(createRequest.getSelectedDoctorDashboard());
                    List<Doctor> selectedDashboardDoctor = doctorRepository.findAllByIdIn(listOfDrDashboards);
                    Map<Long, String> dashMap = new HashMap<>();
                    selectedDashboardDoctor.stream()
                            .filter(x -> x.getId() != null)
                            .forEach(x -> dashMap.put(x.getId(), x.getEmail()));
                    doctor.setSelectedDoctorDashboard(dashMap);
                }

                doctorRepository.save(doctor);
                //doctor medical services

                List<Long> selectedServces = createRequest.getServiceComission().stream().map(x -> x.getId()).collect(Collectors.toList());
                List<MedicalService> medicalServiceList = medicalServiceRepository.findAllByIdIn(selectedServces);
                List<DoctorMedicalService> doctorMedicalServiceList = new ArrayList<>();
                if (!HISCoreUtil.isListEmpty(medicalServiceList)) {
                    doctorMedicalServiceRepository.deleteDoctorMedicalServiceByDoctor_Id(doctor.getId());
                    Optional<Double> serviceCom = null;
                    for (MedicalService ms : medicalServiceList) {
                        DoctorMedicalService dms = new DoctorMedicalService();
                        dms.setDoctor(doctor);
                        dms.setMedicalService(ms);
                        serviceCom = this.getComissionOfServices(createRequest.getServiceComission(), ms.getId());
                        if (serviceCom.isPresent())
                            dms.setComission(serviceCom.get());
                        doctorMedicalServiceList.add(dms);
                    }
                    doctorMedicalServiceRepository.save(doctorMedicalServiceList);
                }

                Branch branchDoc = branchRepository.findOne(createRequest.getPrimaryBranch());
                BranchDoctor branchDoctor = branchDoctorRepository.findByDoctorAndPrimaryBranchTrue(doctor);
                branchDoctor.setBranch(branchDoc);
                branchDoctorRepository.save(branchDoctor);
                List<Branch> allowBranchesDoc = branchRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedVisitBranches()));
                List<BranchDoctor> nurseVisitBranchesData = new ArrayList<>();
                if (!HISCoreUtil.isListEmpty(allowBranchesDoc)) {
                    branchDoctorRepository.deleteAllByDoctorAndPrimaryBranchFalse(doctor);
                    for (Branch userVisitBr : allowBranchesDoc) {
                        if (userVisitBr.getId() == branchDoc.getId())
                            continue;
                        BranchDoctor userVisitBranches = new BranchDoctor();
                        userVisitBranches.setBranch(userVisitBr);
                        userVisitBranches.setDoctor(doctor);
                        userVisitBranches.setPrimaryBranch(false);
                        nurseVisitBranchesData.add(userVisitBranches);
                    }
                    branchDoctorRepository.save(nurseVisitBranchesData);
                }

                dutyShiftRepository.deleteAllByDoctor(doctor);

                if (!HISCoreUtil.isNull(createRequest.getFirstShiftFromTime())) {
                    DutyShift dutyShift = new DutyShift();//dutyShiftRepository.findByDoctorAndShiftName(doctor, DutyShiftEnum.SHIFT1);
                    dutyShift.setShiftName(DutyShiftEnum.SHIFT1);
                    dutyShift.setStartTime(HISCoreUtil.convertToTime(shift1From));
                    dutyShift.setEndTime(HISCoreUtil.convertToTime(shift1To));
                    dutyShift.setDoctor(doctor);
                    dutyShiftRepository.save(dutyShift);
                }

                if (!HISCoreUtil.isNull(createRequest.getSecondShiftFromTime())) {
                    String shift2From = HISCoreUtil.convertTimeTozone(createRequest.getSecondShiftFromTime(), zone);
                    String shift2To = HISCoreUtil.convertTimeTozone(createRequest.getSecondShiftToTime(), zone);
                    DutyShift dutyShift2 = new DutyShift();
                    dutyShift2.setShiftName(DutyShiftEnum.SHIFT2);
                    dutyShift2.setStartTime(HISCoreUtil.convertToTime(shift2From));
                    dutyShift2.setEndTime(HISCoreUtil.convertToTime(shift2To));
                    dutyShift2.setDoctor(doctor);
                    dutyShiftRepository.save(dutyShift2);
                }

                break;
            case "RECEPTIONIST":
                Receptionist receptionist = receptionistRepository.findByUser(alreadyExistsUser);
                alreadyExistsUser.setActive(createRequest.isActive());
                userRepository.save(alreadyExistsUser);
                receptionist.setEmail(createRequest.getEmail());
                receptionist.setHomePhone(createRequest.getHomePhone());
                receptionist.setCellPhone(createRequest.getCellPhone());
                receptionist.setAccountExpiry(HISCoreUtil.convertToDate(createRequest.getAccountExpiry()));
                receptionist.setLastName(createRequest.getLastName());
                receptionist.setFirstName(createRequest.getFirstName());
                receptionist.setCanReceivePayment(createRequest.isReceivePayment());
                receptionist.setAllowDiscount(createRequest.getAllowDiscount());
                receptionist.setCanAccessPatientRecord(createRequest.isCanAccessPatientRecord());
                receptionist.setAllowDiscountCheck(createRequest.isAllowDiscountCheck());
                receptionist.setOtherDoctorDashboard(createRequest.isOtherDoctorDashBoard());
                receptionist.setUseReceiptDashboard(createRequest.isUseReceptDashboard());
                receptionist.setHidePatientPhoneNumber(createRequest.isHidePatientPhoneNumber());

                if (createRequest.getSelectedDoctorDashboard() != null) {
                    List<Long> listOfDrDashboards = Arrays.asList(createRequest.getSelectedDoctorDashboard());
                    List<Doctor> selectedDashboardDoctor = doctorRepository.findAllByIdIn(listOfDrDashboards);
                    Map<Long, String> dashMap = new HashMap<>();
                    selectedDashboardDoctor.stream()
                            .filter(x -> x.getId() != null)
                            .forEach(x -> dashMap.put(x.getId(), x.getEmail()));
                    receptionist.setSelectedDoctorDashboard(dashMap);
                }
                receptionistRepository.save(receptionist);
                Branch primaryBranchReceptionist = branchRepository.findOne(createRequest.getPrimaryBranch());
                BranchReceptionist branchReceptionist = branchReceptionistRepository.findByReceptionistAndPrimaryBranchTrue(receptionist);
                branchReceptionist.setBranch(primaryBranchReceptionist);
                branchReceptionistRepository.save(branchReceptionist);

                List<Branch> allowBranches = branchRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedVisitBranches()));
                List<BranchReceptionist> receptionistVisitBranchesData = new ArrayList<>();
                if (!HISCoreUtil.isListEmpty(allowBranches)) {
                    branchReceptionistRepository.deleteAllByReceptionistAndPrimaryBranchFalse(receptionist);
                }
                if (!HISCoreUtil.isListEmpty(allowBranches)) {
                    for (Branch userVisitBr : allowBranches) {
                        if (userVisitBr.getId() == primaryBranchReceptionist.getId())
                            continue;
                        BranchReceptionist userVisitBranches = new BranchReceptionist();
                        userVisitBranches.setBranch(userVisitBr);
                        userVisitBranches.setReceptionist(receptionist);
                        userVisitBranches.setPrimaryBranch(false);
                        receptionistVisitBranchesData.add(userVisitBranches);
                    }
                    branchReceptionistRepository.save(receptionistVisitBranchesData);
                }

                break;
            case "CASHIER":
                Cashier cashier = cashierRepository.findByUser(alreadyExistsUser);
                alreadyExistsUser.setActive(createRequest.isActive());
                userRepository.save(alreadyExistsUser);
                cashier.setEmail(createRequest.getEmail());
                cashier.setHomePhone(createRequest.getHomePhone());
                cashier.setCellPhone(createRequest.getCellPhone());
                cashier.setAccountExpiry(HISCoreUtil.convertToDate(createRequest.getAccountExpiry()));
                cashier.setLastName(createRequest.getLastName());
                cashier.setFirstName(createRequest.getFirstName());
                cashier.setCanReceivePayment(createRequest.isReceivePayment());
                cashier.setAllowDiscount(createRequest.getAllowDiscount());
                cashier.setCanAccessPatientRecord(createRequest.isCanAccessPatientRecord());
                cashier.setAllowDiscountCheck(createRequest.isAllowDiscountCheck());
                cashier.setOtherDoctorDashboard(createRequest.isOtherDoctorDashBoard());
                cashier.setUseReceiptDashboard(createRequest.isUseReceptDashboard());
                cashier.setHidePatientPhoneNumber(createRequest.isHidePatientPhoneNumber());
                if (createRequest.getSelectedDoctorDashboard() != null) {
                    List<Long> listOfDrDashboards = Arrays.asList(createRequest.getSelectedDoctorDashboard());
                    List<Doctor> selectedDashboardDoctor = doctorRepository.findAllByIdIn(listOfDrDashboards);
                    Map<Long, String> dashMap = new HashMap<>();
                    selectedDashboardDoctor.stream()
                            .filter(x -> x.getId() != null)
                            .forEach(x -> dashMap.put(x.getId(), x.getEmail()));
                    cashier.setSelectedDoctorDashboard(dashMap);
                }
                cashierRepository.save(cashier);

                Branch primaryBranchCashier = branchRepository.findOne(createRequest.getPrimaryBranch());
                BranchCashier branchCashier = branchCashierRepository.findByCashierAndPrimaryBranchTrue(cashier);
                branchCashier.setBranch(primaryBranchCashier);
                branchCashierRepository.save(branchCashier);

                List<Branch> cashierAllowBranches = branchRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedVisitBranches()));
                List<BranchCashier> cashierVisitBranchesData = new ArrayList<>();
                if (!HISCoreUtil.isListEmpty(cashierAllowBranches)) {
                    branchCashierRepository.deleteAllByCashierAndPrimaryBranchFalse(cashier);
                }

                if (!HISCoreUtil.isListEmpty(cashierAllowBranches)) {
                    for (Branch userVisitBr : cashierAllowBranches) {
                        if(branchCashier.getBranch() != null){
                        if (userVisitBr.getId() == branchCashier.getBranch().getId())
                            continue;}
                        BranchCashier userVisitBranches = new BranchCashier();
                        userVisitBranches.setBranch(userVisitBr);
                        userVisitBranches.setCashier(cashier);
                        userVisitBranches.setPrimaryBranch(false);
                        cashierVisitBranchesData.add(userVisitBranches);
                    }
                    branchCashierRepository.save(cashierVisitBranchesData);
                }
                break;
            case "NURSE":
                Nurse nurse = nurseRepository.findByUser(alreadyExistsUser);
                alreadyExistsUser.setActive(createRequest.isActive());
                userRepository.save(alreadyExistsUser);
                nurse.setEmail(createRequest.getEmail());
                nurse.setHomePhone(createRequest.getHomePhone());
                nurse.setAccountExpiry(HISCoreUtil.convertToDate(createRequest.getAccountExpiry()));
                nurse.setCellPhone(createRequest.getCellPhone());
                nurse.setAccountExpiry(HISCoreUtil.convertToDate(createRequest.getAccountExpiry()));
                nurse.setLastName(createRequest.getLastName());
                nurse.setFirstName(createRequest.getFirstName());
                nurse.setOtherDoctorDashboard(createRequest.isOtherDoctorDashBoard());
                nurse.setUseReceiptDashboard(createRequest.isUseReceptDashboard());
                nurse.setHidePatientPhoneNumber(createRequest.isHidePatientPhoneNumber());
                nurse.setManagePatientRecords(createRequest.isManagePatientRecords());
                nurse.setManagePatientInvoices(createRequest.isManagePatientInvoices());
                if (createRequest.getSelectedDoctorDashboard() != null) {
                    List<Long> listOfDrDashboards = Arrays.asList(createRequest.getSelectedDoctorDashboard());
                    List<Doctor> selectedDashboardDoctor = doctorRepository.findAllByIdIn(listOfDrDashboards);
                    Map<Long, String> dashMap = new HashMap<>();
                    selectedDashboardDoctor.stream()
                            .filter(x -> x.getId() != null)
                            .forEach(x -> dashMap.put(x.getId(), x.getEmail()));
                    nurse.setSelectedDoctorDashboard(dashMap);
                }
                nurseRepository.save(nurse);

                //nurse department portion
                List<Department> selectedDept = departmentRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedDepartment()));
                List<NurseDepartment> nurseDepartmentList = new ArrayList<>();
                if (!HISCoreUtil.isListEmpty(selectedDept)) {
                    nurseDepartmentRepository.deleteAllByNurse_Id(nurse.getId());
                    for (Department dept : selectedDept) {
                        NurseDepartment nurseDept = new NurseDepartment();
                        nurseDept.setDepartment(dept);
                        nurseDept.setNurse(nurse);
                        nurseDepartmentList.add(nurseDept);
                    }

                }

                nurseDepartmentRepository.save(nurseDepartmentList);

                //nurse branch portion
                Branch primaryBranchNurse = branchRepository.findOne(createRequest.getPrimaryBranch());
                BranchNurse branchNurse = branchNurseRepository.findByNurseAndPrimaryBranchTrue(nurse);
                branchNurse.setBranch(primaryBranchNurse);
                branchNurseRepository.save(branchNurse);

                List<Branch> nurseAllowBranches = branchRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedVisitBranches()));
                List<BranchNurse> nurseAllowBranchesData = new ArrayList<>();
                if (!HISCoreUtil.isListEmpty(nurseAllowBranches)) {
                    branchNurseRepository.deleteAllByNurseAndPrimaryBranchFalse(nurse);
                }
                if (!HISCoreUtil.isListEmpty(nurseAllowBranches)) {
                    for (Branch userVisitBr : nurseAllowBranches) {
                        if (userVisitBr.getId() == branchNurse.getBranch().getId())
                            continue;
                        BranchNurse userVisitBranches = new BranchNurse();
                        userVisitBranches.setBranch(userVisitBr);
                        userVisitBranches.setNurse(nurse);
                        userVisitBranches.setPrimaryBranch(false);
                        nurseAllowBranchesData.add(userVisitBranches);
                    }
                    branchNurseRepository.save(nurseAllowBranchesData);
                }

                //nurse with doctor logic
                //List<User> doctorsList = userRepository.findAllByIdIn(Arrays.asList(createRequest.getDutyWithDoctors()));

                List<Doctor> doctorList = doctorRepository.findAllByIdIn(Arrays.asList(createRequest.getDutyWithDoctors()));
                List<NurseWithDoctor> dutyWithDoctorsData = new ArrayList<>();
                if (!HISCoreUtil.isListEmpty(doctorList)) {
                    nurseWithDoctorRepository.deleteAllByNurse_Id(nurse.getId());
                    for (Doctor docUser : doctorList) {
                        NurseWithDoctor dutyWithDoctor1 = new NurseWithDoctor();
                        dutyWithDoctor1.setNurse(nurse);
                        dutyWithDoctor1.setDoctor(docUser);
                        dutyWithDoctorsData.add(dutyWithDoctor1);
                    }
                }
                nurseWithDoctorRepository.save(dutyWithDoctorsData);

                break;

        }

        return alreadyExistsUser;
    }

    @Transactional
    public String deleteUser(User user) {
        //  user.setDeleted(true);

        String userType = user.getUserType();
        if (userType.equalsIgnoreCase("CASHIER")) {
            Cashier cashier = cashierRepository.findByUser(user);
            userRepository.delete(user);
            branchCashierRepository.deleteAllByCashier(cashier);
            cashierRepository.delete(cashier);
            return "success";
        }
        if (userType.equalsIgnoreCase("RECEPTIONIST")) {
            Receptionist receptionist = receptionistRepository.findByUser(user);
            branchReceptionistRepository.deleteAllByReceptionist(receptionist);
            userRepository.delete(user);
            receptionistRepository.delete(receptionist);
            return "success";
        }
        if (userType.equalsIgnoreCase("NURSE")) {
            Nurse nurse = nurseRepository.findByUser(user);
            branchNurseRepository.deleteAllByNurse(nurse);
            userRepository.delete(user);
            nurseWithDoctorRepository.deleteAllByNurse(nurse);
            nurseRepository.delete(nurse);
            //    userRoleRepository.deleteAllByUser(user);
            ;
            return "success";
        }
        if (userType.equalsIgnoreCase("DOCTOR")) {
            Doctor doctor = doctorRepository.findByUser(user);
            long apptCount = appointmentRepository.countByDoctor(doctor);
            if (apptCount != 0) {
                return "hasChild";
            }
            branchDoctorRepository.deleteAllByDoctor(doctor);
            //    userRoleRepository.deleteAllByUser(user);
            dutyShiftRepository.deleteAllByDoctor(doctor);
            nurseWithDoctorRepository.deleteAllByDoctor(doctor);
            userRepository.delete(user);
            doctorRepository.delete(doctor);

            return "success";
        }
        //userRepository.delete(user);
        return null;
    }

    //Long uid, Long pId, Long primaryBranchId, String userType, String email, String userName, String firstName, String lastName, String homePhone, String cellPhone, Long id, Long docDepartmentId
    public List<StaffResponseWrapper> findByRole(String role) {
        List<StaffResponseWrapper> staffRespWrapper = new ArrayList<>();
        List<User> userList = userRepository.findAllByUserRoles_role_name(role);

        switch (role) {
            case "DOCTOR":
                List<Doctor> doctorList = doctorRepository.findAllByUserIn(userList);
                for (Doctor doctor : doctorList) {
                    StaffResponseWrapper staffRespWrapper1 = new StaffResponseWrapper(doctor.getUser().getId(), doctor.getProfileId(), doctor.getUser().getUserType(),
                            doctor.getEmail(), doctor.getUser().getUsername(), doctor.getFirstName(), doctor.getLastName(), doctor.getHomePhone(), doctor.getCellPhone(), doctor.getId());
                    staffRespWrapper.add(staffRespWrapper1);
                }
                break;
            default:

        }
        return staffRespWrapper;
    }

    // Added By : Naeem Saeed
    public List<StaffResponseWrapper> findAllByRole(String role) {
        List<StaffResponseWrapper> staffRespWrapper = new ArrayList<>();
        List<User> userList = userRepository.findAllByUserRoles_role_name(role);
        switch (role) {
            case "DOCTOR":
                List<Doctor> doctorList = doctorRepository.findAllByUserIn(userList);
                for (Doctor doctor : doctorList) {
                    StaffResponseWrapper staffRespWrapper1 = new StaffResponseWrapper(doctor.getUser().getId(), doctor.getProfileId(),
                            doctor.getEmail(), doctor.getUser().getUsername(), doctor.getFirstName(), doctor.getLastName(), doctor.getId(), doctor.getBalance());
                    staffRespWrapper.add(staffRespWrapper1);
                }
                break;
            default:
        }
        return staffRespWrapper;
    }

    public List<DoctorPaymentResponseWrapper> findDoctorListWithCommission() {
        return doctorRepository.getAllDoctorWithCommission();
    }

    public List<StaffWrapper> searchByNameOrRole(String name, String userType, int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        if (userType != null && !userType.isEmpty())
            userType = userType.toLowerCase();
        List<StaffWrapper> drStaffList = doctorRepository.findAllBySearchCriteria(name, userType, pageable);
        List<StaffWrapper> crStaffList = cashierRepository.findAllBySearchCriteria(name, userType, pageable);
        List<StaffWrapper> rtStaffList = receptionistRepository.findAllBySearchCriteria(name, userType, pageable);
        List<StaffWrapper> nrStaffList = nurseRepository.findAllBySearchCriteria(name, userType, pageable);

        finalStaffList = Stream.of(drStaffList, crStaffList, rtStaffList, nrStaffList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());


        return finalStaffList;
    }

    public List<DepartmentWrapper> findDepartmentsByBranch(long branchId) {
        List<Department> department = branchDepartmentRepository.findByBranch(branchId);
        return department.stream()
                .map(x -> new DepartmentWrapper(x.getId(), x.getName(), x.getDescription()))
                .collect(Collectors.toList());

    }


    // Save Doctor Payment
    // Added By : Naeem Saeed
    @Transactional
    public void saveDoctorPayment(DoctorPaymentRequestWrapper paymentRequestWrapper) {
        double remainingBalance = 0.00;
        Doctor doctor = doctorRepository.findOne(paymentRequestWrapper.getDoctorId());
        if (doctor != null) {
            if (doctor.getBalance() != null)
                remainingBalance = doctor.getBalance() - paymentRequestWrapper.getAmount();
            else
                remainingBalance = paymentRequestWrapper.getAmount();
            doctor.setBalance(remainingBalance);
            doctorRepository.save(doctor);

            StaffPayment staffPayment = new StaffPayment();
            staffPayment.setCreatedOn(new Date());
            staffPayment.setUpdatedOn(new Date());
            staffPayment.setPaymentId(paymentRequestWrapper.getPaymentId());
            //    staffPayment.setPaymentId(hisUtilService.getPrefixId(ModuleEnum.PAYMENT));
            staffPayment.setPaymentId(paymentRequestWrapper.getPaymentId());
            staffPayment.setPaymentAmount(paymentRequestWrapper.getAmount());
            staffPayment.setDoctor(doctor);
            staffPayment.setPaymentType(paymentTypeRepository.findOne(paymentRequestWrapper.getPaymentTypeId()));
            staffPaymentRepository.save(staffPayment);
        }
    }

    // Get Doctor ALL Payment List
    // Added By : Naeem Saeed
    public List<DoctorPaymentRequestWrapper> getDocPaymentList() {
        return staffPaymentRepository.findAllList();
    }

}
