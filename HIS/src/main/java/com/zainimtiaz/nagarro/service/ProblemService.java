package com.zainimtiaz.nagarro.service;

import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.zainimtiaz.nagarro.model.*;
import com.zainimtiaz.nagarro.repository.*;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.ProblemWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by jamal on 8/15/2018.
 */
@Service
@Transactional
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private ICDCodeRepository icdCodeRepository;
    @Autowired
    private ICDVersionRepository icdVersionRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private OrganizationService organizationService;

    public void savePatientProblem(ProblemWrapper problemWrapper) throws Exception {


        Organization dbOrganization=organizationService.getAllOrgizationData();
        String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
        String prescribedDate="";
        Date dte=new Date();
        String prescribedStartedDate="";
        String systemDateFormat=dbOrganization.getDateFormat();
        String systemtimeFormat=dbOrganization.getTimeFormat();
        String utcDate = HISCoreUtil.convertDateToTimeZone(dte,systemDateFormat,Zone);
        String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
        String standardFormatDateTime=systemDateFormat+" "+systemtimeFormat;
    //    System.out.println("Time"+currentTime);
        dte=problemWrapper.getDatePrescribedDate();
        String readDate=HISCoreUtil.convertDateToTimeZone(dte,"YYYY-MM-dd hh:mm:ss",Zone);
        Date scheduledDate=HISCoreUtil.convertStringDateObject(readDate);

        Problem problem = new Problem(problemWrapper);
        Appointment appointment = null;

        Patient patient = null;
        ICDVersion icdVersion = null;
        ICDCode icdCode = null;




        if (problemWrapper.getAppointmentId() > 0) {
            appointment = this.appointmentRepository.findOne(problemWrapper.getAppointmentId());
            if (appointment != null) {
                problem.setAppointment(appointment);
            }
        }

        if (problemWrapper.getPatientId() > 0) {
            patient = this.patientRepository.findOne(problemWrapper.getPatientId());
            if (patient != null) {
                problem.setPatient(patient);
            }
        }

        if (problemWrapper.getSelectedICDVersionId() > 0) {
            icdVersion = this.icdVersionRepository.findOne(problemWrapper.getSelectedICDVersionId());
            if (icdVersion != null) {
                problem.setIcdVersion(icdVersion);
            }
        }

        if (problemWrapper.getSelectedCodeId() > 0) {
            icdCode = this.icdCodeRepository.findOne(problemWrapper.getSelectedCodeId());
            if (icdCode != null) {
                problem.setIcdCode(icdCode);
            }
        }
        problem.setDateDiagnosis(scheduledDate);
        this.problemRepository.save(problem);
    }

    public void deleteProblemById(long problemId) {
        Problem problem = this.problemRepository.findOne(problemId);
        if (problem != null) {
            this.problemRepository.delete(problem);
        }
    }

    public void updatePatientProblem(ProblemWrapper problemWrapper) throws Exception {

        Problem problem = this.problemRepository.findOne(problemWrapper.getId());
        Organization dbOrganization=organizationService.getAllOrgizationData();
        String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
        String prescribedDate="";
        Date dte=new Date();
        String prescribedStartedDate="";
        String systemDateFormat=dbOrganization.getDateFormat();
        String systemtimeFormat=dbOrganization.getTimeFormat();
        String utcDate = HISCoreUtil.convertDateToTimeZone(dte,systemDateFormat,Zone);
        String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
        String standardFormatDateTime=systemDateFormat+" "+systemtimeFormat;
     //   System.out.println("Time"+currentTime);
        dte=problemWrapper.getDatePrescribedDate();
        String readDate=HISCoreUtil.convertDateToTimeZone(dte,"YYYY-MM-dd hh:mm:ss",Zone);
        Date scheduledDate=HISCoreUtil.convertStringDateObject(readDate);

     //   String readDate=HISCoreUtil.convertDateToTimeZone(dte,HISConstants.DATE_FORMAT_APP,Zone);

     //   Date scheduledDate=HISCoreUtil.convertToDateNew(readDate);

        if (problem != null) {

            new Problem(problem, problemWrapper);

            Appointment appointment = null;
            Patient patient = null;
            ICDVersion icdVersion = null;
            ICDCode icdCode = null;

            if (problemWrapper.getAppointmentId() > 0) {
                appointment = this.appointmentRepository.findOne(problemWrapper.getAppointmentId());
                if (appointment != null) {
                    problem.setAppointment(appointment);
                }
            }

            if (problemWrapper.getPatientId() > 0) {
                patient = this.patientRepository.findOne(problemWrapper.getPatientId());
                if (patient != null) {
                    problem.setPatient(patient);
                }
            }

            if (problemWrapper.getSelectedICDVersionId() > 0) {
                icdVersion = this.icdVersionRepository.findOne(problemWrapper.getSelectedICDVersionId());
                if (icdVersion != null) {
                    problem.setIcdVersion(icdVersion);
                }
            }

            if (problemWrapper.getSelectedCodeId() > 0) {
                icdCode = this.icdCodeRepository.findOne(problemWrapper.getSelectedCodeId());
                if (icdCode != null) {
                    problem.setIcdCode(icdCode);
                }
            }
            problem.setDateDiagnosis(scheduledDate);
            this.problemRepository.save(problem);

        }
    }

    public Page<ProblemWrapper> findPaginatedProblem(Pageable pageable, Long patientId) throws ParseException {
        return this.problemRepository.findAllByPatient_idOrderByCreatedOnDesc(pageable, patientId);
    }

    public ProblemWrapper getProblemById(long id) throws ParseException {
        return this.problemRepository.getProblemById(id);
    }

    public Page<ProblemWrapper> getProblemsByStatusAndPatientId(Pageable pageable, String status, Long selectedPatientId) throws ParseException {
        return this.problemRepository.findAllByStatusAndPatient_id(pageable, status, selectedPatientId);
    }

}
