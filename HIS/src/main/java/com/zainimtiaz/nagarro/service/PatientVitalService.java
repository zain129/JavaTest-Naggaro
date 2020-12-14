package com.zainimtiaz.nagarro.service;



import com.sd.his.model.*;

import com.zainimtiaz.nagarro.model.Appointment;
import com.zainimtiaz.nagarro.model.Organization;
import com.zainimtiaz.nagarro.model.Patient;
import com.zainimtiaz.nagarro.model.PatientVital;
import com.zainimtiaz.nagarro.repository.AppointmentRepository;
import com.zainimtiaz.nagarro.repository.PatientRepository;
import com.zainimtiaz.nagarro.repository.PatientVitalRepository;

import com.zainimtiaz.nagarro.utill.DateTimeUtil;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.sd.his.wrapper.*;
import com.zainimtiaz.nagarro.wrapper.AppointmentWrapper;
import com.zainimtiaz.nagarro.wrapper.PatientVitalWrapper;
import com.zainimtiaz.nagarro.wrapper.VitaPatientWrapper;
import com.zainimtiaz.nagarro.wrapper.VitalWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.text.ParseException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PatientVitalService{

    @Autowired
    PatientVitalRepository patientVitalRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<PatientVitalWrapper> getAll(){
        return patientVitalRepository.getAll();
    }

    public void save(PatientVitalWrapper vital){
        PatientVital patientVital=new PatientVital();
        //  patientVital.setId(vital.);
        patientVital.setName(vital.getName());
        patientVital.setUnit(vital.getUnit());
        patientVital.setStandardValue(vital.getStandardValue());
        patientVital.setStatus(vital.getStatus());
        patientVital.setCurrentValue(vital.getCurrentValue());
        patientVital.setUpdatedOn(HISCoreUtil.convertStringDateObject(vital.getUpdatedOn()) );
        Optional<Patient> patient=patientRepository.findById(Long.valueOf(vital.getPatientId()));
        patientVital.setPatient(patient.get());
        patientVitalRepository.save(patientVital);
    }


    @Transactional(rollbackOn = Throwable.class)
    public PatientVital update(VitaPatientWrapper wrapper) {
        PatientVital wrapperObj = patientVitalRepository.findOne(Long.valueOf(wrapper.getId()));
        wrapperObj.setName(wrapperObj.getName());
        wrapperObj.setUnit(wrapperObj.getUnit());
        wrapperObj.setStandardValue(wrapperObj.getStandardValue());
        wrapperObj.setStatus(wrapperObj.getStatus());
        wrapperObj.setCurrentValue(wrapper.getCurrentValue());
        Optional<Patient> patient=patientRepository.findById(Long.valueOf(wrapperObj.getPatient().getId()));
        wrapperObj.setPatient(patient.get());
        wrapperObj.setUpdatedOn(new Date());

        return patientVitalRepository.save(wrapperObj);
    }


    @Transactional(rollbackOn = Throwable.class)
    public boolean deleteVital(long id) {

        patientVitalRepository.delete(id);
        return true;
    }

    public PatientVital getById(long Id) {
        return patientVitalRepository.findOne(Id);

    }


    public List<PatientVitalWrapper> getPaginatedOrder(Pageable pageable, Long patientId) {
        List<PatientVitalWrapper> patientVitals=this.patientVitalRepository.getPaginatedOrder(pageable,patientId);
        List<PatientVitalWrapper> patientVitalsSort=this.patientVitalRepository.getPaginatedOrderSort(pageable,patientId);
     //   invoice.getPatientRefunds().stream().filter(i ->i.getRefundType().equalsIgnoreCase("Invoice")).mapToDouble(i -> i.getRefundAmount()).sum()
        /*List<AppointmentWrapper> listOfAppointments = appointmentRepository.findAllAppointmentsByPatient(patientId);*/
      /*  Map<Boolean, List<AppointmentWrapper>> listOfApp = listOfAppointments.stream().sorted(Comparator.comparing(AppointmentWrapper::getLabel).reversed())
                .collect(Collectors.partitioningBy(x -> x.getCompareDate()
                        .toInstant().isAfter(Instant.now())));*/
         /*List<AppointmentWrapper> listOfApp = listOfAppointments.stream().sorted(Comparator.comparing(AppointmentWrapper::getLabel).reversed())
                .collect(Collectors.partitioningBy(x -> x.getCompareDate()
                        .toInstant().isAfter(Instant.now())));*/

        Organization dbOrganization=organizationService.getAllOrgizationData();
        String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
        String systemDateFormat=dbOrganization.getDateFormat();
        String systemTimeFormat=dbOrganization.getTimeFormat();
        String hoursFormat = dbOrganization.getHoursFormat();
        if(hoursFormat.equals("12")){
            if(systemTimeFormat.equals("hh:mm")){
                systemTimeFormat="hh:mm";
            }else{
                systemTimeFormat="hh:mm:ss";
            }
        }else{
            if(systemTimeFormat.equals("hh:mm")){
                systemTimeFormat="HH:mm";
            }else{
                systemTimeFormat="HH:mm:ss";
            }
        }



        if(patientVitalsSort.size()>0){
            boolean isFound=false;
            for(int j=0;j<patientVitalsSort.size();j++){

                long appointment=Long.valueOf(patientVitalsSort.get(0).getAppointmentId());
                if(isFound==false){
                patientVitals=patientVitals.stream()
                        .filter(x -> x.getAppointmentId()==appointment).collect(Collectors.toList());
                if(patientVitals.size()>0){
                    isFound=true;
                    }
                }
            }



            for(int i=0;i<patientVitals.size();i++){

                if(patientVitals.get(i).getVitalStrDate() != null){


                        String standardFormatDateTime=systemDateFormat+" "+systemTimeFormat;
                        Date dteFrom=HISCoreUtil.convertStringDateObjectOrder(patientVitals.get(i).getVitalStrDate());
                        String readDateFrom=HISCoreUtil.convertDateToTimeZone(dteFrom,standardFormatDateTime,Zone);
                        Date fromDte= null;
                        try {
                            fromDte = DateTimeUtil.getDateFromString(readDateFrom,standardFormatDateTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(systemDateFormat!=null || !systemDateFormat.equals("")){
                            String  dtelFrom=HISCoreUtil.convertDateToStringWithDateDisplay(fromDte,standardFormatDateTime);

                            patientVitals.get(i).setVitalStrDate(dtelFrom);

                        }





                }else{
                    patientVitals.get(i).setVitalStrDate("");
                }

            }


        }

        return patientVitals;
        }


    public int countPaginatedDocuments() {
        return this.patientVitalRepository.findAll().size();
    }


    public void saveListVital(List<VitalWrapper>  vital, String  patientId){
        for(int i=0;i<vital.size();i++){
            PatientVital patientVital=new PatientVital();

            patientVital.setName(vital.get(i).getName());
            patientVital.setUnit(vital.get(i).getUnit());
            patientVital.setStandardValue(vital.get(i).getStandardValue());
            List<AppointmentWrapper> listOfAppointments = appointmentRepository.findAllAppointmentsByPatient(Long.valueOf(patientId));
            Map<Boolean, List<AppointmentWrapper>> listOfApp = listOfAppointments.stream().sorted(Comparator.comparing(AppointmentWrapper::getLabel).reversed())
                .collect(Collectors.partitioningBy(x -> x.getCompareDate()
                        .toInstant().isAfter(Instant.now())));
            if(listOfApp.get(false).get(0).getAppointmentId()!=null){
            Appointment app=appointmentService.findById(listOfApp.get(false).get(0).getId());
                patientVital.setAppointment(app);
            }

            patientVital.setChiefComplaint(vital.get(i).getChiefComplaint());
           // patientVital.setStatus(vital.get(i).get);
            patientVital.setCurrentValue(vital.get(i).getCurrentValue());
            Optional<Patient> patient=patientRepository.findById(Long.valueOf(patientId));
            patientVital.setPatient(patient.get());
            Organization dbOrganization = organizationService.getAllOrgizationData();
            String systemDateFormat = dbOrganization.getDateFormat();
            String Zone = dbOrganization.getZone().getName().replaceAll("\\s", "");
            String readDate = HISCoreUtil.convertDateToTimeZone(new Date(), "YYYY-MM-dd hh:mm:ss", Zone);
            //  System.out.println("Read Date"+readDate);
            Date scheduledDate = HISCoreUtil.convertStringDateObject(readDate);
            patientVital.setDateVital(scheduledDate);
            patientVitalRepository.save(patientVital);
        }

    }
}
