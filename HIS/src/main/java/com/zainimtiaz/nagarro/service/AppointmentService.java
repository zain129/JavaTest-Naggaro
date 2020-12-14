package com.zainimtiaz.nagarro.service;

import com.google.gson.Gson;
import com.sd.his.enums.*;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.zainimtiaz.nagarro.enums.*;
import com.zainimtiaz.nagarro.model.*;
import com.zainimtiaz.nagarro.repository.*;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.AppointmentWrapper;
import com.zainimtiaz.nagarro.wrapper.response.MedicalServicesDoctorWrapper;
import com.sendgrid.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * @author    : waqas kamran
 * @Date      : 08-Jun-18
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
 * @Package   : com.sd.his.service
 * @FileName  : AppointmentService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    BranchRepository branchRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    HISUtilService hisUtilService;
    @Autowired
    private MedicalServiceRepository medicalServiceRepository;
    @Autowired
    private DoctorMedicalServiceRepository doctorMedicalServiceRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private OrganizationService organizationService;
   /* @Autowired
    private SendGrid sendGrid;*/
    @Autowired
    private DutyShiftRepository dutyShiftRepository;


    Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    public List<AppointmentWrapper> findAllPaginatedAppointments(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        //    List<AppointmentWrapper> list = appointmentRepository.findAllPaginatedAppointments(pageable);
        //  return appointmentRepository.findAllPaginatedAppointments(pageable);
        return appointmentRepository.findAllAppointments();
    }

    public List<AppointmentWrapper> findAllAppointments() {
     /* appointmentRepository.findAll().stream()
              .map(x -> new AppointmentWrapper(x.getId(), x.getAppointmentId(), x.getName(), x.getNotes(), x.getStatus().getName(), x.getColor(), x.getStatus().getId(), x.getReason(), x.getColor()
                      , x.getType(), x.getDuration(), x.getFollowUpReminder(), x.getFollowUpReasonReminder(), x.getSchdeulledDate(), x.getStartedOn(), x.getEndedOn(),
                      x.getRecurring(), x.getFirstAppointmentOn(), x.getLastAppointmentOn(), x.getDoctor().getFirstName(), x.getDoctor().getLastName(), x.getPatient().getProfileImgURL()
                      , x.getPatient().getId(), x.getBranch().getId(), x.getBranch().getName(), x.getRoom().getId(), x.getDoctor().getFirstName(), x.getDoctor().getLastName(), x.getDoctor().getId(), x.getFollowUpDate(), x.getMedicalService().getId(), x.getMedicalService().getName()))
              .collect(Collectors.toList());*/

        return appointmentRepository.findAllAppointments();


    }

    public AppointmentWrapper getSingleAppointment(long id) {
        return appointmentRepository.findAllAppointmentById(id);
    }

    public Appointment findById(long id) {
        return appointmentRepository.findOne(id);
    }

    public Appointment findByNaturalId(String id) {
        return appointmentRepository.findByAppointmentId(id);
    }


    public AppointmentWrapper findAppointmentById(long id) {
        return appointmentRepository.findAppointmentById(id);
    }

    public int countAllAppointments() {
        return appointmentRepository.findAll().size();
    }

    @Transactional
    public String saveAppointment(AppointmentWrapper appointmentWrapper) {
        Appointment appointment = new Appointment();
        Appointment appointmentObj;
        Appointment appointmentObj2;
        String result = "success";
        Patient patient = null;
        boolean checkDoctorExist = false;
        Organization dbOrganization = organizationService.getAllOrgizationData();
        String zone = dbOrganization.getZone().getName().replaceAll("\\s", "");

        Doctor doctor = doctorRepository.findOne(appointmentWrapper.getDoctorId());
        if (HISCoreUtil.isValidObject(doctor)) {
            checkDoctorExist = true;
        }
        if (checkDoctorExist && (appointmentWrapper.getDateSchedule() != null)) {
            if(doctor.getVacationFrom() != null && doctor.getVacationTO() != null){
                String zonedDateFrom = HISCoreUtil.convertDateToTimeZone(doctor.getVacationFrom(), "YYYY-MM-dd", zone);
                String zonedDateTo = HISCoreUtil.convertDateToTimeZone(doctor.getVacationTO(), "YYYY-MM-dd", zone);
                String dateScheduled = HISCoreUtil.convertDateToTimeZone(appointmentWrapper.getDateSchedule(), "YYYY-MM-dd", zone);
                boolean isDrOnVacation = isDateBetweenDRVacation(HISCoreUtil.convertToDateOnly(zonedDateFrom),
                        HISCoreUtil.convertToDateOnly(zonedDateTo), HISCoreUtil.convertToDateOnly(dateScheduled));
                if (isDrOnVacation)
                    return result = "onVacation";
            }
        }
        if(checkDoctorExist && (appointmentWrapper.getDateSchedule() != null )){
            if(!HISCoreUtil.isListEmpty(doctor.getWorkingDays())){
                String zonedDate =  HISCoreUtil.convertDateToTimeZone(appointmentWrapper.getDateSchedule(),"YYYY-MM-dd hh:mm:ss",zone);
                String day = HISCoreUtil.getDayFromDate(zonedDate);
                long dayExist = doctor.getWorkingDays().stream().filter(x->x.equalsIgnoreCase(day)).count();
                if(dayExist == 0){
                    return result ="dayConflict";
                }
            }
        }
        if (checkDoctorExist) {
            DutyShift dutyShift = dutyShiftRepository.findByDoctorAndShiftName(doctor, DutyShiftEnum.SHIFT1);
            String currentTime = HISCoreUtil.convertDateToTimeZone(appointmentWrapper.getDateSchedule(), "HH:mm:", zone);
            boolean valid = checkApptTimeLiesBetweenDoctorShifts(dutyShift, currentTime, zone);
            if (valid) {
                appointment = this.buildAppointment(appointmentWrapper, appointment, zone);
            } else {
                DutyShift dutyShift2 = dutyShiftRepository.findByDoctorAndShiftName(doctor, DutyShiftEnum.SHIFT2);
                if (HISCoreUtil.isValidObject(dutyShift2)) {
                    boolean shift2Validator = checkApptTimeLiesBetweenDoctorShifts(dutyShift2, currentTime, zone);
                    if (shift2Validator) {
                        appointment = this.buildAppointment(appointmentWrapper, appointment, zone);

                    } else {
                        return result = "doctorShift";
                    }
                } else {
                    return result = "doctorShift";
                }

            }

        }

        if (appointmentWrapper.getStateOfPatientBox()) {
            patient = this.buildPatient(appointmentWrapper);
            appointment.setPatient(patient);
        }

        if (appointmentWrapper.isRecurringAppointment()) {
            LocalDate start = HISCoreUtil.convertDateToLocalDate(appointmentWrapper.getFirstAppointment());
            LocalDate end = HISCoreUtil.convertDateToLocalDate(appointmentWrapper.getLastAppointment());
            List<LocalDate> listOfDates = new ArrayList<>();
            /*List<LocalDate> dates = Stream.iterate(start, date -> date.plusDays(1))
                    .limit(ChronoUnit.DAYS.between(start, end))
                    .collect(Collectors.toList());*/
            boolean checkDate =true;
            LocalDate week = start.plus(appointmentWrapper.getRecurseEvery() != null ?appointmentWrapper.getRecurseEvery():0 , ChronoUnit.WEEKS);
            List<LocalDate> finalLocalDates = new ArrayList<>();

            while (checkDate){
                if(week.isBefore(end)){
                    LocalDate addWeekRecurs = week;
                    LocalDate addWeek = week.plusDays(7);
                    List<LocalDate> dates = Stream.iterate(week, date -> date.plusDays(1))
                            .limit(ChronoUnit.DAYS.between(week, addWeek))
                            .collect(Collectors.toList());
                    finalLocalDates.addAll(dates);
                    week = addWeekRecurs;
                    week = week.plus(2,ChronoUnit.WEEKS);

                }else
                    checkDate =false;
            }

            for (LocalDate locald : finalLocalDates) {
                for (String s : appointmentWrapper.getSelectedRecurringDays()) {
                    if (locald.getDayOfWeek().toString().equalsIgnoreCase(s)) {
                        listOfDates.add(locald);
                    }
                }
            }

            if (!HISCoreUtil.isListEmpty(listOfDates)) {
                for (LocalDate localDate1 : listOfDates) {
                    String readDate = HISCoreUtil.convertDateToTimeZone(HISCoreUtil.convertLocalDateToDate(localDate1), "YYYY-MM-dd hh:mm:ss", zone);
                    Date zoneScheduleDate = HISCoreUtil.convertStringDateObject(readDate);
                    boolean isAlreadyExist = this.checkTimeAndDateAlreadyExist(zoneScheduleDate, appointment.getStartedOn(), appointment.getEndedOn(), appointmentWrapper.getDoctorId());
                    if (!isAlreadyExist) {
                        appointmentObj = new Appointment();
                        appointmentObj2 = this.buildAppointment(appointmentWrapper, appointmentObj, zone);
                        appointmentObj2.setSchdeulledDate(HISCoreUtil.convertLocalDateToDate(localDate1));
                        appointmentRepository.save(appointmentObj2);
                    }
                }
            }
        }
        //check date time exist already
        if (!checkTimeAndDateAlreadyExist(appointment.getSchdeulledDate(), appointment.getStartedOn(), appointment.getEndedOn(), appointmentWrapper.getDoctorId())) {
            appointmentRepository.save(appointment);
            hisUtilService.updatePrefix(ModuleEnum.APPOINTMENT);
            //sendMail();
            //    emailServiceImp.sendSimpleMessage("waqasrana11@gmail.com","Test","Hey Buddy");
            //   EmailService emailService = EmailService.getInstance(true);
            //   boolean testSent   = SMTPUtil.sendTestEmail("AKIAJITH6V4G622BCQ6A","ssl://email-smtp.us-east-1.amazonaws.com","465","waqas@solutiondots.net","AnwMXw05Y7JstZEKPtNM9M023fpm5om2DljVqYIGNin5");
            /*try {
              boolean testSent =  AmazonSESUtil.sendTestEmail("noreply@solutiondots.com","ag@solutiondots.com","Test","Hello Body","AKIAICPTDCC6INFXRWGA","KxC1ZF5FJCdD6x43RPg+51ccITDWV++UF6fdmnXA");
              logger.info("email has been"+ testSent);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
           /* boolean  emailSent= emailService.sendEmail(new EmailWrapper("waqasrana1111@yahoo.com","Tes","Hey buddy "));
            logger.info("email sending..."+emailSent );*/
        } else {
            result = "already";
        }

        return result;
    }

    private boolean checkApptTimeLiesBetweenDoctorShifts(DutyShift dutyShift, String currentTime, String zone) {
        boolean valid = false;
        Date startTime = dutyShift.getStartTime();
        Date endTime = dutyShift.getEndTime();
        String zonedStartTime = HISCoreUtil.convertDateToTimeZone(startTime, "HH:mm", zone);
        String zonedEndTime = HISCoreUtil.convertDateToTimeZone(endTime, "HH:mm", zone);
        try {
            valid = HISCoreUtil.isTimeBetweenTwoTime(zonedStartTime, zonedEndTime, currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return valid;
    }

    private Patient buildPatient(AppointmentWrapper appointmentWrapper) {
        Patient patient = new Patient();
        patient.setPatientId(hisUtilService.getPrefixId(ModuleEnum.PATIENT));
        patient.setFirstName(appointmentWrapper.getNewPatient());
        patient.setGender(GenderTypeEnum.MALE);
        patient.setMaritalStatus(MaritalStatusTypeEnum.MARRIED);
        patient.setStatus(PatientStatusTypeEnum.ACTIVE);
        /*if (appointmentWrapper.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findOne(appointmentWrapper.getDoctorId());
            appointment.setDoctor(doctor);
        } else
            patient.setPrimaryDoctor(doctorRepository.findOne(1L));*/
        patient.setLastName(appointmentWrapper.getNewPatient());
        patient.setMiddleName(appointmentWrapper.getNewPatient());
        patient.setCellPhone(appointmentWrapper.getCellPhone());
        patientRepository.save(patient);
        return patient;
    }

    private Appointment buildAppointment(AppointmentWrapper appointmentWrapper, Appointment appointment, String zone) {
        Patient patient = new Patient();
        Branch branch = branchRepository.findOne(appointmentWrapper.getBranchId());
        // Date scheduleDate = appointmentWrapper.getDateSchedule();
        // appointment.setSchdeulledDate(scheduleDate); // old working sc date without zone
        String readDate = HISCoreUtil.convertDateToTimeZone(appointmentWrapper.getDateSchedule(), "YYYY-MM-dd hh:mm:ss", zone);
        Date zoneScheduleDate = HISCoreUtil.convertStringDateObject(readDate);

        //Date date2 = Date.from(Instant.parse(appointmentWrapper.getScheduleDateAndTime()));
        LocalDateTime date2 = HISCoreUtil.convertToLocalDateTimeViaSqlTimestamp(zoneScheduleDate);

        appointment.setSchdeulledDate(zoneScheduleDate);
        logger.info("zone date .." + zoneScheduleDate);
        appointment.setStartedOn(HISCoreUtil.convertToDateViaLocalDateTime(date2));
        Date ended = HISCoreUtil.addTimetoDate(appointment.getSchdeulledDate(), appointmentWrapper.getDuration());
        appointment.setEndedOn(ended);
        appointment.setReason(appointmentWrapper.getReason());
        appointment.setNotes(appointmentWrapper.getNotes());
        appointment.setColor(appointmentWrapper.getColor());
        appointment.setActive(true);
       // appointment.setType(new Gson().toJson(appointmentWrapper.getAppointmentType()));
        appointment.setType(appointmentWrapper.getApptType());
        appointment.setDuration(appointmentWrapper.getDuration());
        // appointment.setStatus(AppointmentStatusTypeEnum.valueOf(appointmentWrapper.getStatus()));
        if (appointmentWrapper.getFollowUpReminder() == true) {
            // appointment.setFollowUpDate(HISCoreUtil.convertToDate(appointmentWrapper.getFollowUpDate()));
            appointment.setFollowUpDate(appointmentWrapper.getFollowUpDate());
            appointment.setFollowUpReasonReminder(appointmentWrapper.getFollowUpReason());
            appointment.setFollowUpReminder(appointmentWrapper.getFollowUpReminder());
        }
        appointment.setAppointmentId(hisUtilService.getPrefixId(ModuleEnum.APPOINTMENT));
        appointment.setBranch(branch);
        Room room = findExamRoomById(appointmentWrapper.getRoomId());
        if (HISCoreUtil.isValidObject(room)) {
            appointment.setRoom(room);
        }
        Doctor doctor = doctorRepository.findOne(appointmentWrapper.getDoctorId());
        appointment.setDoctor(doctor);
        MedicalService medicalService = medicalServiceRepository.findOne(appointmentWrapper.getServiceId());
        appointment.setMedicalService(medicalService);
        if (appointmentWrapper.getPatientId() != null) {
            patient = patientRepository.findOne(appointmentWrapper.getPatientId());
            appointment.setPatient(patient);
        }
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        logger.info("creating appoint" + auth.getName());
        if(auth.getName() != null){
           User user = userRepository.findByUsernameAndActiveTrue(auth.getName());
           appointment.setCreator(user);
        }

        if (appointmentWrapper.getStatusId() != null) {
            Status apptStatus = statusRepository.findOne(appointmentWrapper.getStatusId());
            appointment.setStatus(apptStatus);
        }
        return appointment;
    }

    @Transactional
    public String updateAppointment(AppointmentWrapper appointmentWrapper, Appointment alreadyExistAppointment) {
        String result = "success";
        boolean checkDoctorExist = false;
        Organization dbOrganization = organizationService.getAllOrgizationData();
        String zone = dbOrganization.getZone().getName().replaceAll("\\s", "");
        Branch branch = branchRepository.findOne(appointmentWrapper.getBranchId());
        String readDate = HISCoreUtil.convertDateToTimeZone(appointmentWrapper.getDateSchedule(), "YYYY-MM-dd hh:mm:ss", zone);
        Date zoneScheduleDate = HISCoreUtil.convertStringDateObject(readDate);

        //isDateBetweenDRVacation();
        //Date scheduleDate = HISCoreUtil.convertToDate(appointmentWrapper.getScheduleDate());
        /*alreadyExistAppointment.setSchdeulledDate(zoneScheduleDate);
        Date date2 = Date.from(Instant.parse(appointmentWrapper.getScheduleDate()));
        alreadyExistAppointment.setStartedOn(date2);
        Date ended = HISCoreUtil.addTimetoDate(scheduleDate, appointmentWrapper.getDuration());
        alreadyExistAppointment.setEndedOn(ended);*/

        if(appointmentWrapper.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findOne(appointmentWrapper.getDoctorId());
            if (HISCoreUtil.isValidObject(doctor)) {
                checkDoctorExist = true;
            }

            if (checkDoctorExist && (appointmentWrapper.getDateSchedule() != null)) {
                String zonedDateFrom = HISCoreUtil.convertDateToTimeZone(doctor.getVacationFrom(), "YYYY-MM-dd", zone);
                String zonedDateTo = HISCoreUtil.convertDateToTimeZone(doctor.getVacationTO(), "YYYY-MM-dd", zone);
                String dateScheduled = HISCoreUtil.convertDateToTimeZone(appointmentWrapper.getDateSchedule(), "YYYY-MM-dd", zone);
                boolean isDrOnVacation = isDateBetweenDRVacation(HISCoreUtil.convertToDateOnly(zonedDateFrom),
                        HISCoreUtil.convertToDateOnly(zonedDateTo), HISCoreUtil.convertToDateOnly(dateScheduled));
                if (isDrOnVacation)
                    return result = "onVacation";
            }
            if (checkDoctorExist) {
                DutyShift dutyShift = dutyShiftRepository.findByDoctorAndShiftName(doctor, DutyShiftEnum.SHIFT1);
                String currentTime = HISCoreUtil.convertDateToTimeZone(appointmentWrapper.getDateSchedule(), "HH:mm:", zone);
                boolean valid = checkApptTimeLiesBetweenDoctorShifts(dutyShift, currentTime, zone);
                if (valid) {
                    alreadyExistAppointment = this.updateBuildAppointment(appointmentWrapper, alreadyExistAppointment, zone);
                } else {
                    DutyShift dutyShift2 = dutyShiftRepository.findByDoctorAndShiftName(doctor, DutyShiftEnum.SHIFT2);
                    if (HISCoreUtil.isValidObject(dutyShift2)) {
                        boolean shift2Validator = checkApptTimeLiesBetweenDoctorShifts(dutyShift2, currentTime, zone);
                        if (shift2Validator) {
                            alreadyExistAppointment = this.updateBuildAppointment(appointmentWrapper, alreadyExistAppointment, zone);

                        } else {
                            return result = "doctorShift";
                        }
                    } else {
                        return result = "doctorShift";
                    }

                }

            }
        }else {alreadyExistAppointment = this.updateBuildAppointment(appointmentWrapper, alreadyExistAppointment, zone);}

      /*  LocalDateTime date2 = HISCoreUtil.convertToLocalDateTimeViaSqlTimestamp(zoneScheduleDate);

        alreadyExistAppointment.setSchdeulledDate(zoneScheduleDate);
        logger.info("zone date .." + zoneScheduleDate);
        alreadyExistAppointment.setStartedOn(HISCoreUtil.convertToDateViaLocalDateTime(date2));
        Date ended = HISCoreUtil.addTimetoDate(alreadyExistAppointment.getSchdeulledDate(), appointmentWrapper.getDuration());
        alreadyExistAppointment.setEndedOn(ended);

        alreadyExistAppointment.setReason(appointmentWrapper.getReason());
        alreadyExistAppointment.setNotes(appointmentWrapper.getNotes());
        alreadyExistAppointment.setColor(appointmentWrapper.getColor());
        alreadyExistAppointment.setType(new Gson().toJson(appointmentWrapper.getAppointmentType()));
        alreadyExistAppointment.setDuration(appointmentWrapper.getDuration());
        alreadyExistAppointment.setBranch(branch);

        Room room = findExamRoomById(appointmentWrapper.getRoomId());
        if (HISCoreUtil.isValidObject(room)) {
            alreadyExistAppointment.setRoom(room);
        }
        Doctor doctor = doctorRepository.findOne(appointmentWrapper.getDoctorId());
        alreadyExistAppointment.setDoctor(doctor);
          MedicalService medicalService = medicalServiceRepository.findOne(appointmentWrapper.getServiceId());
        alreadyExistAppointment.setMedicalService(medicalService);

       Patient patient = null;
        if (appointmentWrapper.getPatientId() != null) {
            patient = patientRepository.findOne(appointmentWrapper.getPatientId());
            alreadyExistAppointment.setPatient(patient);
        }
        if(appointmentWrapper.getStatusId() != null) {
            Status apptStatus = statusRepository.findOne(appointmentWrapper.getStatusId());
            alreadyExistAppointment.setStatus(apptStatus);
        }
        */


        if (!checkTimeAndDateAlreadyExistForUpdate(alreadyExistAppointment.getSchdeulledDate(), alreadyExistAppointment.getStartedOn(), alreadyExistAppointment.getEndedOn(), appointmentWrapper.getDoctorId(), appointmentWrapper.getAppointmentId())) {
            appointmentRepository.save(alreadyExistAppointment);

        } else {
            result = "already";
        }

        return result;
    }

    private Appointment updateBuildAppointment(AppointmentWrapper appointmentWrapper, Appointment appointment, String zone) {
        Patient patient = new Patient();
        if(appointmentWrapper.getBranchId() !=null){
            Branch branch = branchRepository.findOne(appointmentWrapper.getBranchId());
            appointment.setBranch(branch);
        }
        String readDate = HISCoreUtil.convertDateToTimeZone(appointmentWrapper.getDateSchedule(), "YYYY-MM-dd hh:mm:ss", zone);
        Date zoneScheduleDate = HISCoreUtil.convertStringDateObject(readDate);

        //Date date2 = Date.from(Instant.parse(appointmentWrapper.getScheduleDateAndTime()));
        LocalDateTime date2 = HISCoreUtil.convertToLocalDateTimeViaSqlTimestamp(zoneScheduleDate);
        appointment.setSchdeulledDate(zoneScheduleDate);
        logger.info("zone date .." + zoneScheduleDate);
        appointment.setStartedOn(HISCoreUtil.convertToDateViaLocalDateTime(date2));

        Date ended = HISCoreUtil.addTimetoDate(appointment.getSchdeulledDate(), appointmentWrapper.getDuration());
        appointment.setEndedOn(ended);
        appointment.setReason(appointmentWrapper.getReason());
        appointment.setNotes(appointmentWrapper.getNotes());
        appointment.setColor(appointmentWrapper.getColor());
        appointment.setType(new Gson().toJson(appointmentWrapper.getAppointmentType()));
        appointment.setDuration(appointmentWrapper.getDuration());
        // appointment.setStatus(AppointmentStatusTypeEnum.valueOf(appointmentWrapper.getStatus()));
        if (appointmentWrapper.getFollowUpReminder() != null) {
            // appointment.setFollowUpDate(HISCoreUtil.convertToDate(appointmentWrapper.getFollowUpDate()));
            appointment.setFollowUpDate(appointmentWrapper.getFollowUpDate());
            appointment.setFollowUpReasonReminder(appointmentWrapper.getFollowUpReason());
            appointment.setFollowUpReminder(appointmentWrapper.getFollowUpReminder());
        }

        Room room = findExamRoomById(appointmentWrapper.getRoomId());
        if (HISCoreUtil.isValidObject(room)) {
            appointment.setRoom(room);
        }
        if(appointmentWrapper.getDoctorId() !=null){
            Doctor doctor = doctorRepository.findOne(appointmentWrapper.getDoctorId());
            appointment.setDoctor(doctor);}
        if(appointmentWrapper.getServiceId() != null){
            MedicalService medicalService = medicalServiceRepository.findOne(appointmentWrapper.getServiceId());
            appointment.setMedicalService(medicalService);
        }

        if (appointmentWrapper.getPatientId() != null) {
            patient = patientRepository.findOne(appointmentWrapper.getPatientId());
            appointment.setPatient(patient);
        }
        if (appointmentWrapper.getStatusId() != null) {
            Status apptStatus = statusRepository.findOne(appointmentWrapper.getStatusId());
            appointment.setStatus(apptStatus);
        }
        return appointment;
    }


    public List<AppointmentWrapper> getPageableSearchedAppointments(Long doctorId, Long branchId) {
        return appointmentRepository.findAllAppointmentsByDoctor(doctorId, branchId);
    }

    public List<AppointmentWrapper> searchAppointmentByPatients(String patientName, int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        return appointmentRepository.searchAllAppointmentsByPatients(patientName, pageable);
    }

    public int countSearchedAppointments(Long doctorId, Long branchId) {
        Doctor doctor = doctorRepository.findOne(doctorId);
        Branch branch = branchRepository.findOne(branchId);
        return appointmentRepository.findByDoctorAndBranch(doctor, branch).size();
    }

    public int countSearchedAppointmentsByPatient(String patientName) {
        return appointmentRepository.countAllByPatientFirstName(patientName);
    }


    /*
        public Appointment updateAppointment(AppointmentWrapper appointmentWrapper,Appointment appointment) {
            Branch branch = branchRepository.getOne(appointmentWrapper.getBranchId());

            appointment.setRecurringDays(new Gson().toJson(appointmentWrapper.getSelectedRecurringDays()));
            long startTime= HISCoreUtil.convertDateToMilliSeconds(appointmentWrapper.getStart());
            appointment.setStartedOn(startTime);
            appointment.setEndedOn(startTime + appointmentWrapper.getDuration()*60*1000);
            appointment.setUpdatedOn(System.currentTimeMillis());
            appointment.setDeleted(false);
            appointment.setActive(true);
            appointment.setRecurring(appointmentWrapper.isRecurringAppointment());
            appointment.setDuration(appointmentWrapper.getDuration());
    *//*
       if(HISCoreUtil.isValidObject((HISCoreUtil.convertDateToMilliSeconds(appointmentWrapper.getFirstAppointment())))){
           appointment.setFirstAppointmentOn(HISCoreUtil.convertDateToMilliSeconds(appointmentWrapper.getFirstAppointment()));
        }
        if(HISCoreUtil.isValidObject((HISCoreUtil.convertDateToMilliSeconds(appointmentWrapper.getLastAppointment())))){
            appointment.setFirstAppointmentOn(HISCoreUtil.convertDateToMilliSeconds(appointmentWrapper.getLastAppointment()));
        }
*//*
        appointment.setNotes(appointmentWrapper.getNotes());
        appointment.setColor(appointmentWrapper.getColor());
        appointment.setReason(appointmentWrapper.getReason());
        appointment.setType(new Gson().toJson(appointmentWrapper.getAppointmentType()));
        appointment.setStatus(appointmentWrapper.getStatus());
        appointment.setFollowUpReasonReminder(appointmentWrapper.getFollowUpReason());
        appointment.setFollowUpReminder(appointmentWrapper.getFollowUpReminder());
     // appointment.setFollowUpDate(HISCoreUtil.convertDateToMilliSeconds(appointmentWrapper.getFollowUpDate()));
        appointment.setFollowUpReasonReminder(appointmentWrapper.getReason());
        appointment.setName(appointmentWrapper.getProblem());
        appointment.setBranch(branch);
        roomRepository.getOne(appointmentWrapper.getRoomId());
        if(appointmentWrapper.getAppointmentType().contains(AppointmentTypeEnum.NEW_PATIENT.getValue())) {
            User user = userRepository.findByUsernameOrEmail(appointmentWrapper.getPatient(), appointmentWrapper.getEmail());
            Profile profile = user.getProfile();
            user.setUsername(appointmentWrapper.getPatient());
            user.setUserType(String.valueOf(UserTypeEnum.PATIENT));
            user.setDeleted(false);
            user.setActive(true);
            appointment.setAge(Long.valueOf(appointmentWrapper.getAge()).longValue());

            profile.setType(String.valueOf(UserTypeEnum.PATIENT));
            profile.setFirstName(appointmentWrapper.getProblem());
            profile.setLastName(appointmentWrapper.getProblem());
            profile.setDeleted(false);
            profile.setActive(true);
            profile.setHomePhone(appointmentWrapper.getCellPhone());
            profile.setCellPhone(appointmentWrapper.getCellPhone());
            profile.setUpdatedOn(System.currentTimeMillis());
            profile.setGender(appointmentWrapper.getGender());
            user.setProfile(profile);
            userRepository.save(user);
            appointment.setPatient(user);
            appointmentRepository.save(appointment);
        }else {
            appointmentRepository.save(appointment);}
            return appointment;
    }
*/
    //getAllLabOrdersById
    public Room findExamRoomById(Long id) {
        if (id != null) {
            Room room = roomRepository.findOne(id);
            return room;
        }
        return null;
    }

    public boolean changeStatus(Long currentStatus, Appointment alreadyExistAppointment) {
        boolean statusChanged = false;
        Status status = statusRepository.findOne(currentStatus);
        alreadyExistAppointment.setStatus(status);
        Appointment appt = appointmentRepository.save(alreadyExistAppointment);
        if (HISCoreUtil.isValidObject(appt)) {
            statusChanged = true;
        }
        return statusChanged;
    }

    private boolean isDateBetweenDRVacation(Date from, Date To, Date currentDate) {
        boolean valid = true;
        if (currentDate.before(from) || currentDate.after(To)) {
            valid = false;
        }
        return valid;
    }

    private boolean checkTimeAndDateAlreadyExist(Date date1, Date startedOn, Date endedOn, Long drId) {
        boolean isExist = false;
        int appointments = appointmentRepository.findAppointmentClash(date1, startedOn, endedOn, drId);
        if (appointments > 1)
            isExist = true;
        return isExist;
    }

    private boolean checkTimeAndDateAlreadyExistForUpdate(Date date1, Date startedOn, Date endedOn, Long drId, String aptId) {
        boolean isExist = false;
        List<Appointment> appointments = appointmentRepository.findAppointmentClashForUpdate(date1, startedOn, endedOn, drId);
        for (Appointment apt : appointments) {
            if (!(apt.getAppointmentId().equalsIgnoreCase(aptId)))
                isExist = true;
        }
        return isExist;
    }

    public String updateAppointmentRoom(String roomId, Appointment alreadyAppointment) {
        String apt = "";
        long rId = Long.valueOf(roomId);
        if (rId != 0) {
            Room roomObj = roomRepository.findOne(rId);
            alreadyAppointment.setRoom(roomObj);
            appointmentRepository.save(alreadyAppointment);
            return "success";
        }
        return apt;
    }


    public List<MedicalServicesDoctorWrapper> getMedicalServicesAgainstDoctors() {
        return doctorMedicalServiceRepository.findAllByDoctorAndServices();
    }

    /*void sendMail() {
        Email from = new Email("waqasrana11@gmail.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("waqasrana11@gmail.com");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            try {
                request.setBody(mail.build());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Response response = this.sendGrid.api(request);
            sendGrid.api(request);

            // ...
        } catch (IOException ex) {
            // ...
        }
    }*/

    public void deleteAppointment(Appointment appointment) {
        appointment.setActive(false);
        appointmentRepository.save(appointment);
    }

}
