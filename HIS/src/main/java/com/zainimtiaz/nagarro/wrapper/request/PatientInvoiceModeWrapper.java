package com.zainimtiaz.nagarro.wrapper.request;

public class PatientInvoiceModeWrapper {

        Long id;
        String description;
        String invoiceId ;
        String patientId;



        String appointmentId;
        String paymentMode;
        String paymentId;



    String paymentAmount;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }


        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }


        public String getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(String invoiceId) {
            this.invoiceId = invoiceId;
        }


        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public String getAppointmentId() {
            return appointmentId;
        }

        public void setAppointmentId(String appointmentId) {
            this.appointmentId = appointmentId;
        }


    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }


    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    }


