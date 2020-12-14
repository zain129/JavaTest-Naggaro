package com.zainimtiaz.nagarro.wrapper;


import com.amazonaws.util.DateUtils;
import com.zainimtiaz.nagarro.model.Appointment;
import com.zainimtiaz.nagarro.utill.DateTimeUtil;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;

import java.util.Date;
import java.util.List;

/*
 * @author    : Irfan Nasim
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
 * @Package   : com.sd.his.wrapper
 * @FileName  : AppointmentWrapper
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class AppointmentWrapper implements Comparable<AppointmentWrapper> {

    private Long id;
    private String appointmentId;
    private String title;
    private String appointmentStartedOn;
    private String appointmentEndedOn;
    private Boolean draggable;
    private String patient;
    private String notes;
    private String reason;
    private String color;
    private String colorHash;
    private String status;//AppointmentStatusTypeEnum
    private Integer duration; //minutes
    private boolean followUpReminder;
    private String followUpReason;
    private Long startedOn;
    private Long ended;
    private Long createdOn;
    private Long updatedOn;
    private boolean recurringAppointment;
    private Long recurringPeriod;
    private String gender;
    private String age;
    private String cellPhone;
    private Date firstAppointment;
    private Date lastAppointment;
    private Date followUpDate;
    private String followUpDateResponse;
    private Long patientId;
    private String patientFirstName;
    private String patientLastName;
    private Long roomId;
    private Long doctorId;
    private String email;
    private List<String> selectedRecurringDays;
    private List<String> appointmentType;
    private String examName;
    private String branchName;
    private String type;
    private Long firstAppointmentOn;
    private Long lastAppointmentOn;
    private Long branchId;
    private String scheduleDate;
    private String scheduleDateAndTime;
    // private Date scheduleDateAndTime2;
    private String docFirstName;
    private String docLastName;
    private int appointmentConvertedTime;
    private int appointmentEndedConvertedTime;
    private Date compareDate;
    private String newPatient;
    private Date dateOfBirth;
    private Long serviceId;
    private String serviceName;
    private Double receivedAmount;
    private Double patientAdvanceDeposit;
    private Boolean stateOfPatientBox;
    private String invoicePrefix;
    private boolean completed;
    private String label;
    private Long value;
    private Date dateSchedule;
    private Long statusId;
    private String zonedDate;
    private String formatedDate ="YYYY-MM-dd hh:mm:ss";
    private String zone = "Asia/Karachi";
    private Double refundAmount;
    private String hashColor;
    private String profileImgURL;
    private String base_S3_URL ="https://s3.amazonaws.com/hisdev/users/patient/history/order/";
    private String default_photo ="/public/images/patient-photo.jpg";
    private Long creatorId;
    private String creatorName;
    private String checkInTime;
    private boolean pastAppt;
    private String apptType;
    private Long recurseEvery;


    private double receive_Patient;




 //   private BufferedImage imgBarcode;



    private byte[] img;
    public AppointmentWrapper() {
    }
    public AppointmentWrapper(Appointment appointment){

        this.appointmentId = appointment.getAppointmentId();
        this.id = appointment.getId();
        this.branchId = appointment.getBranch().getId();
        this.branchName =appointment.getBranch().getName();

        if(appointment.getStatus() != null){this.hashColor =appointment.getStatus().getHashColor();
            this.statusId =appointment.getStatus().getId();
            this.status =appointment.getStatus().getName();}


        this.serviceName =appointment.getMedicalService().getName();
        this.serviceId =appointment.getMedicalService().getId();


        this.notes =appointment.getNotes();
        //     this.recurringAppointment =appointment.getRecurring();

        this.profileImgURL=appointment.getPatient().getProfileImgURL();
        this.patientFirstName =appointment.getPatient().getFirstName();
        this.patientLastName =appointment.getPatient().getLastName();
        this.patient = this.getPatientFirstName() + this.getPatientLastName();
        this.patientId = appointment.getPatient().getId();

        this.docFirstName =appointment.getDoctor().getFirstName();
        this.docLastName =appointment.getDoctor().getLastName();
        if(HISCoreUtil.isValidObject(appointment.getRoom())){
            this.examName = appointment.getRoom().getRoomName();
            this.roomId =  appointment.getRoom().getId();
        }
        this.zonedDate = HISCoreUtil.convertDateToTimeZone(appointment.getSchdeulledDate(),this.formatedDate,zone);
        this.label = appointmentId+","+HISCoreUtil.convertDateAndTimeToStringWithPMAndAM(appointment.getSchdeulledDate());
        this.profileImgURL = profileImgURL != null ? this.base_S3_URL+profileImgURL : this.default_photo ;
        this.value = id;
        this.scheduleDate = this.zonedDate;
        this.pastAppt = appointment.getSchdeulledDate().before(new Date());
        this.appointmentConvertedTime = convertAppointmentTime(appointment.getStartedOn());
//        this.appointmentEndedConvertedTime = convertAppointmentTime(appointment.getStartedOn()) + duration;
        this.appointmentEndedOn = HISCoreUtil.convertTimeToString(appointment.getEndedOn());
        this.reason =appointment.getReason();
        this.followUpReason =appointment.getFollowUpReasonReminder();
        this.duration = appointment.getDuration();
        this.compareDate = appointment.getSchdeulledDate();
       // this.appointmentType = JSONUtil.convertJsonToList(appointment.getType());
        this.apptType = appointment.getType();
        this.creatorId = appointment.getCreator().getId();
        this.creatorName = appointment.getCreator().getUsername();
        this.checkInTime = HISCoreUtil.convertDateToTimeZone(appointment.getCheckIn(),this.formatedDate,zone);
    }

    public AppointmentWrapper(Long id, String name, String notes) {
    }

    public AppointmentWrapper(Long id, String appointmentId, String title, String notes, String statusName, String hashColor, Long statusId, String reason, String color, String appointmentType, Integer duration,
                              Boolean followUpReminder, String followUpReasonReminder, Date scheduleDate, Date startedOn, Date endedOn,
                              Boolean recurring, Date firstAppointmentOn, Date lastAppointmentOn, String firstName, String lastName, String profileImgURL, Long patientId,
                              Long branchId, String branchName,Long roomId, String docFirstName, String docLastName, Long docId, Date followUpDate, Long serviceId, String serviceName
    ) {


        this.id = id;
        this.appointmentId=appointmentId;
        this.zonedDate = HISCoreUtil.convertDateToTimeZone(scheduleDate,this.formatedDate,zone);
        this.appointmentConvertedTime = convertAppointmentTime(startedOn);
        this.appointmentEndedConvertedTime = convertAppointmentTime(startedOn) + duration;
        this.appointmentEndedOn = HISCoreUtil.convertTimeToString(endedOn);
        //  this.scheduleDate = HISCoreUtil.convertDateToString(scheduleDate);
        this.scheduleDate = this.zonedDate;
        this.draggable = draggable;
        this.patient = firstName + " " + lastName;
        this.notes = notes;
        this.reason = reason;
        this.color = color;
        this.docFirstName = docFirstName;
        this.docLastName = docLastName;
        this.doctorId = docId;
        this.status = statusName;
        this.statusId =statusId;
     //   this.followUpReminder = followUpReminder;
        this.duration = duration;
        this.compareDate = scheduleDate;
        this.apptType = appointmentType;
       // this.appointmentType = JSONUtil.convertJsonToList(appointmentType);
        this.followUpReason=followUpReasonReminder;
        this.recurringPeriod = recurringPeriod;
       /* this.firstAppointmentOn = firstAppointmentOn;
        this.lastAppointmentOn = lastAppointmentOn;*/
        this.patientId = patientId;
        this.patientFirstName = firstName;
        this.patientLastName = lastName;
        this.branchId = branchId;
        this.roomId = roomId;
        //     this.examName = room.getRoomName();
        this.branchName = branchName;
        this.scheduleDateAndTime = HISCoreUtil.convertDateAndTimeToStringWithPMAndAM(scheduleDate);
        this.followUpDateResponse = HISCoreUtil.convertDateToString(followUpDate);
        this.serviceId=serviceId;
        this.serviceName=serviceName;
        this.label = appointmentId+","+HISCoreUtil.convertDateAndTimeToStringWithPMAndAM(scheduleDate);
        this.profileImgURL = profileImgURL != null ? this.base_S3_URL+profileImgURL : this.default_photo ;
        this.value = id;
        this.hashColor =hashColor;


    }
    public AppointmentWrapper(Long id,String appointmentId,
                              String title,Date scheduleDate,
                              String firstName, String lastName,
                              String docFirstName,
                              String docLastName,String profileImgURL, Long patientId,
                              String invPrefix, boolean completed)
    {
        //Long patientId,Long branchId, String branchName, Long roomId,
        this.id = id;
        this.appointmentId=appointmentId;
        this.title = title;
        this.scheduleDate = HISCoreUtil.convertDateToString(scheduleDate);
        this.draggable = draggable;
        this.patient = firstName + " " + lastName;
        this.notes = notes;
        this.reason = reason;
        this.color = color;
        this.docFirstName = docFirstName;
        this.docLastName = docLastName;

        // this.status = status.name();
        this.followUpReminder = followUpReminder;
        this.duration = duration;
        this.compareDate = scheduleDate;

        this.recurringPeriod = recurringPeriod;
        this.patientId = patientId;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.branchId = branchId;
//        this.roomId = roomId;

        this.branchName = branchName;
        this.scheduleDateAndTime = HISCoreUtil.convertDateAndTimeToStringNew(scheduleDate);
        this.invoicePrefix = invPrefix;
        this.completed = completed;
        this.label = HISCoreUtil.convertDateAndTimeToStringWithPMAndAM(scheduleDate);
        this.value = id;
        this.profileImgURL =this.base_S3_URL+profileImgURL;
    }

    @Override
    public String toString() {
        return "AppointmentWrapper{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", notes='" + notes + '\'' +
                ", reason='" + reason + '\'' +
                ", type='" + type + '\'' +
                ", duration=" + duration +
                '}';
    }

    public Long getRecurseEvery() {
        return recurseEvery;
    }

    public void setRecurseEvery(Long recurseEvery) {
        this.recurseEvery = recurseEvery;
    }

    public String getApptType() {
        return apptType;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    public boolean isPastAppt() {
        return pastAppt;
    }

    public void setPastAppt(boolean pastAppt) {
        this.pastAppt = pastAppt;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getProfileImgURL() {
        return profileImgURL;
    }

    public void setProfileImgURL(String profileImgURL) {
        this.profileImgURL = profileImgURL;
    }

    public String getHashColor() {
        return hashColor;
    }

    public void setHashColor(String hashColor) {
        this.hashColor = hashColor;
    }

    public String getFollowUpDateResponse() {
        return followUpDateResponse;
    }

    public void setFollowUpDateResponse(String followUpDateResponse) {
        this.followUpDateResponse = followUpDateResponse;
    }

    public Date getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(Date followUpDate) {
        this.followUpDate = followUpDate;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Date getDateSchedule() {
        return dateSchedule;
    }

    public void setDateSchedule(Date dateSchedule) {
        this.dateSchedule = dateSchedule;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Boolean getStateOfPatientBox() {
        return stateOfPatientBox;
    }

    public void setStateOfPatientBox(Boolean stateOfPatientBox) {
        this.stateOfPatientBox = stateOfPatientBox;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNewPatient() {
        return newPatient;
    }

    public void setNewPatient(String newPatient) {
        this.newPatient = newPatient;
    }

    public Date getCompareDate() {
        return compareDate;
    }

    public void setCompareDate(Date compareDate) {
        this.compareDate = compareDate;
    }

    public int getAppointmentEndedConvertedTime() {
        return appointmentEndedConvertedTime;
    }

    public void setAppointmentEndedConvertedTime(int appointmentEndedConvertedTime) {
        this.appointmentEndedConvertedTime = appointmentEndedConvertedTime;
    }

    static int convertAppointmentTime(Date startedOn) {
        return DateTimeUtil.convertAppointmentTime(HISCoreUtil.convertTimeToString(startedOn));
    }

    public String getDocFirstName() {
        return docFirstName;
    }

    public String getColorHash() {
        return colorHash;
    }

    public int getAppointmentConvertedTime() {
        return appointmentConvertedTime;
    }

    public void setAppointmentConvertedTime(int appointmentConvertedTime) {
        this.appointmentConvertedTime = appointmentConvertedTime;
    }

    public void setColorHash(String colorHash) {
        this.colorHash = colorHash;
    }

    public void setDocFirstName(String docFirstName) {
        this.docFirstName = docFirstName;
    }

    public String getDocLastName() {
        return docLastName;
    }

    public void setDocLastName(String docLastName) {
        this.docLastName = docLastName;
    }

    public String getScheduleDateAndTime() {
        return scheduleDateAndTime;
    }

    public void setScheduleDateAndTime(String scheduleDateAndTime) {
        this.scheduleDateAndTime = scheduleDateAndTime;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDuration() {
        return duration;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getFirstAppointmentOn() {
        return firstAppointmentOn;
    }

    public void setFirstAppointmentOn(Long firstAppointmentOn) {
        this.firstAppointmentOn = firstAppointmentOn;
    }

    public Long getLastAppointmentOn() {
        return lastAppointmentOn;
    }

    public void setLastAppointmentOn(Long lastAppointmentOn) {
        this.lastAppointmentOn = lastAppointmentOn;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAppointmentStartedOn() {
        return appointmentStartedOn;
    }

    public void setAppointmentStartedOn(String appointmentStartedOn) {
        this.appointmentStartedOn = appointmentStartedOn;
    }

    public String getAppointmentEndedOn() {
        return appointmentEndedOn;
    }

    public void setAppointmentEndedOn(String appointmentEndedOn) {
        this.appointmentEndedOn = appointmentEndedOn;
    }

    public Boolean getDraggable() {
        return draggable;
    }

    public void setDraggable(Boolean draggable) {
        this.draggable = draggable;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public Boolean getFollowUpReminder() {
        return followUpReminder;
    }

    public void setFollowUpReminder(Boolean followUpReminder) {
        this.followUpReminder = followUpReminder;
    }

    public String getFollowUpReason() {
        return followUpReason;
    }

    public void setFollowUpReason(String followUpReason) {
        this.followUpReason = followUpReason;
    }

    public Long getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(Long startedOn) {
        this.startedOn = startedOn;
    }

    public Long getEnded() {
        return ended;
    }

    public void setEnded(Long ended) {
        this.ended = ended;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public boolean isRecurringAppointment() {
        return recurringAppointment;
    }

    public void setRecurringAppointment(boolean recurringAppointment) {
        this.recurringAppointment = recurringAppointment;
    }

    public Long getRecurringPeriod() {
        return recurringPeriod;
    }

    public void setRecurringPeriod(Long recurringPeriod) {
        this.recurringPeriod = recurringPeriod;
    }

    public Date getFirstAppointment() {
        return firstAppointment;
    }

    public void setFirstAppointment(Date firstAppointment) {
        this.firstAppointment = firstAppointment;
    }

    public Date getLastAppointment() {
        return lastAppointment;
    }

    public void setLastAppointment(Date lastAppointment) {
        this.lastAppointment = lastAppointment;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public List<String> getSelectedRecurringDays() {
        return selectedRecurringDays;
    }

    public void setSelectedRecurringDays(List<String> selectedRecurringDays) {
        this.selectedRecurringDays = selectedRecurringDays;
    }

    public List<String> getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(List<String> appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Double getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(Double receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public Double getPatientAdvanceDeposit() {
        return patientAdvanceDeposit;
    }

    public void setPatientAdvanceDeposit(Double patientAdvanceDeposit) {
        this.patientAdvanceDeposit = patientAdvanceDeposit;
    }

    public String getInvoicePrefix() {
        return invoicePrefix;
    }

    public void setInvoicePrefix(String invoicePrefix) {
        this.invoicePrefix = invoicePrefix;
    }


    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    @Override
    public int compareTo(AppointmentWrapper o) {
        return getCompareDate().compareTo(o.getCompareDate());
    }

    /*public BufferedImage getImgBarcode() {
        return imgBarcode;
    }*/

  /*  public void setImgBarcode(BufferedImage imgBarcode) {
        this.imgBarcode = imgBarcode;
    }
*/
    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public double getReceive_Patient() {
        return receive_Patient;
    }

    public void setReceive_Patient(double receive_Patient) {
        this.receive_Patient = receive_Patient;
    }


}
