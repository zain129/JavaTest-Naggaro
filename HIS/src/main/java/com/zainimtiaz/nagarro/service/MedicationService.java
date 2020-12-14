package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.Appointment;
import com.zainimtiaz.nagarro.model.Medication;
import com.zainimtiaz.nagarro.model.Organization;
import com.zainimtiaz.nagarro.model.Patient;
import com.zainimtiaz.nagarro.repository.AppointmentRepository;
import com.zainimtiaz.nagarro.repository.MedicationRepository;
import com.zainimtiaz.nagarro.repository.OrganizationRepository;
import com.zainimtiaz.nagarro.repository.PatientRepository;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.MedicationWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;



/**
 * Created by jamal on 8/28/2018.
 */
@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationService organizationService;

    @Transactional
    public void saveMedication(MedicationWrapper medicationWrapper) throws Exception {

        Organization dbOrganization=organizationService.getAllOrgizationData();
        String systemDateFormat=dbOrganization.getDateFormat();
        String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
        Date dte=new Date();
        String utcDate = HISCoreUtil.convertDateToTimeZone(dte,systemDateFormat,Zone);
        String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
   //     System.out.println("Time"+currentTime);
        String readDate=HISCoreUtil.convertDateToTimeZone(dte,"YYYY-MM-dd hh:mm:ss",Zone);
      //  System.out.println("Read Date"+readDate);
        Date scheduledDate=HISCoreUtil.convertStringDateObject(readDate);
     //   Date scheduledDate1=HISCoreUtil.convertStringDateObject(readDate);
     //   Date scheduledDate1=HISCoreUtil.convertStringDateObject(readDate);
    //    Date scheduledDate=HISCoreUtil.convertToAPPDate(readDate);
       // System.out.println("Date with Time Zone"+scheduledDate);


        medicationWrapper.setDateStartedTakingDate(scheduledDate);
        medicationWrapper.setDateStoppedTakingDate(scheduledDate);
        medicationWrapper.setDatePrescribedDate(scheduledDate);

        Medication medication = new Medication(medicationWrapper);

        Patient patient = this.patientRepository.findOne(medicationWrapper.getPatientId());
        medication.setPatient(patient);

        Appointment appointment = this.appointmentRepository.findOne(medicationWrapper.getAppointmentId());
        medication.setAppointment(appointment);

        this.medicationRepository.save(medication);
    }

    @Transactional
    public boolean updateMedication(MedicationWrapper medicationWrapper) throws Exception {
        Medication medication = this.medicationRepository.findOne(medicationWrapper.getId());
        if (medication != null) {
            Patient patient = this.patientRepository.findOne(medicationWrapper.getPatientId());
            Organization dbOrganization=organizationService.getAllOrgizationData();
            String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
            Date dte=new Date();
            Date dteStarted=new Date();
            Date dteStoped=new Date();
            String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
            System.out.println("Time"+currentTime);

            // sample purpose
            dte =medicationWrapper.getDatePrescribedDate();
            String readDate=HISCoreUtil.convertDateToTimeZone(dte,"YYYY-MM-dd hh:mm:ss",Zone);
            //  System.out.println("Read Date"+readDate);
            Date scheduledDate=HISCoreUtil.convertStringDateObject(readDate);
            // System.out.println("Date with Time Zone"+scheduledDate);

            // Started Date Time
            dteStarted =medicationWrapper.getDateStartedTakingDate();
            String readDateStarted=HISCoreUtil.convertDateToTimeZone(dteStarted,"YYYY-MM-dd hh:mm:ss",Zone);
            //  System.out.println("Read Date"+readDate);
            Date scheduledDateStarted=HISCoreUtil.convertStringDateObject(readDateStarted);

            dteStoped =medicationWrapper.getDateStoppedTakingDate();
            String readDateStoped=HISCoreUtil.convertDateToTimeZone(dteStoped,"YYYY-MM-dd hh:mm:ss",Zone);
            //  System.out.println("Read Date"+readDate);
            Date scheduledDateStoped=HISCoreUtil.convertStringDateObject(readDateStoped);

            medicationWrapper.setDateStartedTakingDate(scheduledDateStarted);
            medicationWrapper.setDateStoppedTakingDate(scheduledDateStoped);
            medicationWrapper.setDatePrescribedDate(scheduledDate);
            new Medication(medication, medicationWrapper);
            medication.setPatient(patient);

            Appointment appointment = this.appointmentRepository.findOne(medicationWrapper.getAppointmentId());
            medication.setAppointment(appointment);

            this.medicationRepository.save(medication);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteMedicationById(long medicationId) {
        Medication medication = this.medicationRepository.findOne(medicationId);
        if (medication != null) {
            this.medicationRepository.delete(medication);
            return true;
        }
        return false;
    }

    public MedicationWrapper getMedication(long medicationId) {
        return this.medicationRepository.getMedicationById(medicationId);
    }

    public List<MedicationWrapper> getPaginatedMedications(Pageable pageable, Long patientId) {
        List<MedicationWrapper> objWrapperMedication= this.medicationRepository.getPaginatedMedications(pageable, patientId);
     //   Organization dbOrganization=organizationService.getAllOrgizationData();
     //   String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
       // String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
      //  String stdDateTime=dbOrganization.getDateFormat()+" "+dbOrganization.getTimeFormat();
        for(int i=0;i<objWrapperMedication.size();i++){
        Appointment appointment=appointmentRepository.findOne(objWrapperMedication.get(i).getAppointmentId());
          //  objWrapperMedication.get(i).setAppoint(appointment);

         //   String readDate=HISCoreUtil.convertDateToString(appointment.getSchdeulledDate(),stdDateTime);
         //   Date scheduledDate=HISCoreUtil.convertStringDateObject(readDate);objWrapperMedication.
        //    String prescribedDate=HISCoreUtil.convertStringDateObject(objWrapperMedication.get(i).getDatePrescribedString());

         //   objWrapperMedication.get(i).setAppointmentDate(readDate);
         //   objWrapperMedication.get(i).setDatePrescribedString(prescribedDate);
        }
        return objWrapperMedication;
    }

    public int countPaginatedMedications(Long patientId) {
        return this.medicationRepository.countPaginatedMedications(patientId).size();
    }


    public List<MedicationWrapper> getPaginatedMedicationsByStatusAndPatientId(Pageable pageable, String status, Long aLong) {
        return this.medicationRepository.getPaginatedMedicationsByStatusAndPatientId(pageable, status, aLong);
    }

    public int countPaginatedMedicationsByStatusAndPatientId(String status, Long aLong) {
        return this.medicationRepository.countPaginatedMedicationsByStatusAndPatientId(status, aLong).size();
    }

    public List<Medication> getPaginatedMedicationsData(Pageable pageable, Long patientId) {
        return this.medicationRepository.getPaginatedMedicationsAt(pageable, patientId);
    }

}
