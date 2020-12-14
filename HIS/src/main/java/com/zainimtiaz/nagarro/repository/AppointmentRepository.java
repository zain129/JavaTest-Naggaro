package com.zainimtiaz.nagarro.repository;

import com.amazonaws.services.opsworks.model.App;
import com.zainimtiaz.nagarro.model.Appointment;
import com.zainimtiaz.nagarro.model.Branch;
import com.zainimtiaz.nagarro.model.Doctor;
import com.zainimtiaz.nagarro.wrapper.AppointmentWrapper;
import com.zainimtiaz.nagarro.wrapper.response.DashboardResponseWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT NEW  com.sd.his.wrapper.AppointmentWrapper(a.id,a.appointmentId, a.name, a.notes,a.status.name,a.status.hashColor,a.status.id ,a.reason, a.color, a.type," +
            " a.duration, a.followUpReminder, a.followUpReasonReminder,a.schdeulledDate, a.startedOn, a.endedOn," +
            "a.recurring, a.firstAppointmentOn, a.lastAppointmentOn, a.patient.firstName,a.patient.lastName,a.patient.profileImgURL,a.patient.id,a.branch.id, a.branch.name,a.room.id,a.doctor.firstName,a.doctor.lastName,a.doctor.id ,a.followUpDate,a.medicalService.id,a.medicalService.name) " +
            "FROM Appointment a")
    List<AppointmentWrapper>  findAllPaginatedAppointments(Pageable pageable);


    long countByDoctor(Doctor doctor);
    /*@Query("SELECT NEW  com.sd.his.wrapper.AppointmentWrapper(a.id,a.appointmentId, a.name, a.notes,a.status.name,a.status.hashColor,a.status.id,a.reason, a.color, a.type," +
            " a.duration, a.followUpReminder, a.followUpReasonReminder,a.schdeulledDate, a.startedOn, a.endedOn, " +
            "a.recurring, a.firstAppointmentOn, a.lastAppointmentOn, a.patient.firstName,a.patient.lastName,a.patient.profileImgURL,a.patient.id,a.branch.id, a.branch.name,a.room,a.doctor.firstName,a.doctor.lastName,a.doctor.id ,a.followUpDate,a.medicalService.id,a.medicalService.name) " +
            "FROM Appointment a")
    List<AppointmentWrapper> findAllAppointments();*/

    @Query("SELECT new com.sd.his.wrapper.AppointmentWrapper(apt) FROM Appointment apt where apt.active = true ")
    List<AppointmentWrapper> findAllAppointments();



    @Query("SELECT NEW  com.sd.his.wrapper.AppointmentWrapper(a.id,a.appointmentId ,a.name, a.notes,a.status.name,a.status.hashColor,a.status.id, a.reason, a.color, a.type," +
            " a.duration, a.followUpReminder, a.followUpReasonReminder,a.schdeulledDate, a.startedOn, a.endedOn, " +
            "a.recurring, a.firstAppointmentOn, a.lastAppointmentOn, a.patient.firstName,a.patient.lastName,a.patient.profileImgURL,a.patient.id,a.branch.id, a.branch.name,a.room.id,a.doctor.firstName,a.doctor.lastName,a.doctor.id,a.followUpDate,a.medicalService.id,a.medicalService.name) " +
            "FROM Appointment a WHERE (a.doctor.id =?1 or a.branch.id =?2) AND a.active = true ")
    List<AppointmentWrapper> findAllAppointmentsByDoctor(Long doctorId, Long branchId);

    @Query("SELECT NEW  com.sd.his.wrapper.AppointmentWrapper(a.id,a.appointmentId ,a.name, a.notes,a.status.name,a.status.hashColor,a.status.id, a.reason, a.color, a.type," +
            " a.duration, a.followUpReminder, a.followUpReasonReminder,a.schdeulledDate, a.startedOn, a.endedOn, " +
            "a.recurring, a.firstAppointmentOn, a.lastAppointmentOn, a.patient.firstName,a.patient.lastName,a.patient.profileImgURL,a.patient.id,a.branch.id, a.branch.name,a.room.id,a.doctor.firstName,a.doctor.lastName,a.doctor.id,a.followUpDate,a.medicalService.id,a.medicalService.name) " +
            "FROM Appointment a WHERE a.patient.firstName =?1 or a.patient.lastName =?1")
    List<AppointmentWrapper> searchAllAppointmentsByPatients(String patientName,Pageable pageable);

    @Query("SELECT NEW  com.sd.his.wrapper.AppointmentWrapper(a.id,a.appointmentId ,a.name, a.notes,a.status.name,a.status.hashColor,a.status.id, a.reason, a.color, a.type," +
            " a.duration, a.followUpReminder, a.followUpReasonReminder,a.schdeulledDate, a.startedOn, a.endedOn, " +
            "a.recurring, a.firstAppointmentOn, a.lastAppointmentOn, a.patient.firstName,a.patient.lastName,a.patient.profileImgURL,a.patient.id,a.branch.id, a.branch.name,a.room.id,a.doctor.firstName,a.doctor.lastName,a.doctor.id,a.followUpDate,a.medicalService.id,a.medicalService.name) " +
            "FROM Appointment a WHERE a.patient.id =?1 ")
    List<AppointmentWrapper> findAllAppointmentsByPatient(Long patientId);

    @Query("SELECT NEW  com.sd.his.wrapper.response.DashboardResponseWrapper(a.id,a.appointmentId,a.patient.id,a.patient.firstName,a.patient.lastName,a.status.name,a.status.hashColor,a.status.id,a.schdeulledDate ,a.doctor.firstName,a.doctor.lastName,a.branch.name,a.reason,a.schdeulledDate,a.room.roomName, a.branch.id,a.doctor.id,a.room.id )" +
            "FROM Appointment a")
    List<DashboardResponseWrapper> findAllAppointmentsByPatientAndDoctor();

    @Query("SELECT NEW  com.sd.his.wrapper.AppointmentWrapper(a.id,a.appointmentId, a.name, a.notes,a.status.name,a.status.hashColor,a.status.id,a.reason, a.color, a.type," +
            " a.duration, a.followUpReminder, a.followUpReasonReminder,a.schdeulledDate, a.startedOn, a.endedOn, " +
            "a.recurring, a.firstAppointmentOn, a.lastAppointmentOn, a.patient.firstName,a.patient.lastName,a.patient.profileImgURL,a.patient.id,a.branch.id, a.branch.name,a.room.id,a.doctor.firstName,a.doctor.lastName,a.doctor.id,a.followUpDate ,a.medicalService.id,a.medicalService.name) " +
            "FROM Appointment a WHERE a.id =?1 ")
    AppointmentWrapper findAllAppointmentById(Long apptId);

    @Query("SELECT NEW com.sd.his.wrapper.AppointmentWrapper(a.id,a.appointmentId, a.name, " +
            " a.schdeulledDate," +
            " a.patient.firstName,a.patient.lastName,a.doctor.firstName,a.doctor.lastName,a.patient.profileImgURL, a.patient.id, invc.invoiceId, invc.completed) " +
            "FROM  Invoice invc inner join invc.appointment a  WHERE a.id =:apptId  AND  invc.appointment.id=:apptId")
    AppointmentWrapper findAppointmentById(@Param("apptId") long apptId);


/*    @Query("SELECT apt from Appointment apt where apt.schdeulledDate between  apt.schdeulledDate =?1 AND apt.endedOn=?2")*/
    /*List<Appointment>  findBySchdeulledDateEqualsAndStartedOnBetweenEndedOn( Timestamp date1,Timestamp date2);*/

    @Query("select count(apt) from Appointment  apt where DATE(apt.schdeulledDate) = DATE_FORMAT(:date, '%Y-%m-%d') and ( :end between apt.startedOn  and apt.endedOn OR :start  between apt.startedOn  and apt.endedOn ) and apt.doctor.id =:doctorId " )
    int findAppointmentClash(@Param("date") Date date,@Param("start") Date start,@Param("end") Date end,@Param("doctorId") Long doctorId);

    @Query("select apt from Appointment  apt where DATE(apt.schdeulledDate) = DATE_FORMAT(:date, '%Y-%m-%d') and ( :end between apt.startedOn  and apt.endedOn OR :start  between apt.startedOn  and apt.endedOn ) and apt.doctor.id =:doctorId " )
    List<Appointment> findAppointmentClashForUpdate(@Param("date") Date date,@Param("start") Date start,@Param("end") Date end,@Param("doctorId") Long doctorId);

    Appointment findByAppointmentId(String id);
    List<Appointment> findByDoctorAndBranch(Doctor doctor, Branch branch);
    int countAllByPatientFirstName(String patientName);

    @Query(" SELECT apt FROM Appointment apt WHERE DATE(apt.schdeulledDate) = DATE_FORMAT(:date, '%Y-%m-%d') " +
            " AND apt.doctor.profileId =:doctorId AND ( DATE_FORMAT(:time, '%H:%m:%s') between apt.startedOn  and apt.endedOn ) AND apt.branch.branchId = :branchId ")
    Appointment findConflictInAppointment(@Param("doctorId") String doctorId, @Param("branchId") String branchId, @Param("date") Date date, @Param("time") String time);
}

