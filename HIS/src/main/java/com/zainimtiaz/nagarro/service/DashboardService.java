package com.zainimtiaz.nagarro.service;


import com.zainimtiaz.nagarro.repository.AppointmentRepository;
import com.zainimtiaz.nagarro.repository.PatientRepository;
import com.zainimtiaz.nagarro.wrapper.response.DashboardResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<DashboardResponseWrapper> getDoctorDashboard(){
        //List<DashboardResponseWrapper> list = appointmentRepository.findAllAppointmentsByPatientAndDoctor();
        return  appointmentRepository.findAllAppointmentsByPatientAndDoctor();
    }
}
