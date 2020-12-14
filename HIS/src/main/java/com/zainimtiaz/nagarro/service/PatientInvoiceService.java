package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.enums.InvoiceStatusEnum;
import com.zainimtiaz.nagarro.enums.ModuleEnum;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.sd.his.wrapper.request.*;
import com.zainimtiaz.nagarro.model.*;
import com.zainimtiaz.nagarro.repository.*;
import com.zainimtiaz.nagarro.wrapper.response.InvoiceItemsResponseWrapper;
import com.zainimtiaz.nagarro.wrapper.response.InvoiceResponseWrapper;
import com.zainimtiaz.nagarro.wrapper.response.ReceiptListResponseWrapper;
import com.zainimtiaz.nagarro.wrapper.response.RefundListResponseWrapper;
import com.zainimtiaz.nagarro.wrapper.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientInvoiceService {

    @Autowired
    private PatientInvoiceRepository patientInvoiceRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    HISUtilService hisUtilService;
    @Autowired
    private InvoiceItemsRepository invoiceItemsRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PatientInvoicePaymentRepository patientInvoicePaymentRepository;

    @Autowired
    private MedicalServiceRepository medicalServiceRepository;

    @Autowired
    private PatientRefundRepository patientRefundRepository;

    @Autowired
    PaymentTypeRepository  paymentTypeRepository;


    @Autowired
    ReceiptPaymentTypeRepository receiptPaymentTypeRepository;

    @Autowired
    DoctorMedicalServiceRepository doctorMedicalServiceRepository;


    @Autowired
    PatientInvoiceModeRepository patientInvoiceModeRepository;



    public Invoice getInvoiceById(Long id){
       return patientInvoiceRepository.findOne(id);
    }

    @Transactional(rollbackOn = Throwable.class)
    public void saveInvoice(GenerateInvoiceRequestWrapper createInvoiceRequest)
    {
        Appointment appointment =appointmentRepository.findOne(Long.parseLong(createInvoiceRequest.getInvoiceRequestWrapper().get(0).getAppointmentId()));

        Invoice invoice = patientInvoiceRepository.findByAppointmentId(appointment.getId());
        invoice.setCompleted(createInvoiceRequest.getCompleted());

        PatientInvoiceModePayment patientInvoiceModePayment=new PatientInvoiceModePayment();

        if(invoice == null)
        {
            invoice = new Invoice();
            invoice.setAppointment(appointment);
            invoice.setPatient(patientRepository.findOne(Long.parseLong(createInvoiceRequest.getInvoiceRequestWrapper().get(0).getPatientId())));
            invoice.setInvoiceId(hisUtilService.getPrefixId(ModuleEnum.INVOICE));
            invoice.setCreatedOn(new Date());
            invoice.setUpdatedOn(new Date());
            invoice.setPaidAmount(0.0);
            invoice.setStatus(InvoiceStatusEnum.PENDING.toString());

            patientInvoiceRepository.save(invoice);
        }

        List<Long> ids = createInvoiceRequest.getInvoiceRequestWrapper().stream().filter(x->x.getId()!=null).map(PatientInvoiceRequestWrapper::getId).collect(Collectors.toList());
    //    invoiceItemsRepository.deleteInvoiceItem(ids);

        if(ids.size()>0){
            deleteRemoveInviceItems(ids,invoice.getId());
        }


        double amount =0.00;
        double taxAmount =0.00;
        double discountAmount = 0.00;
        double amountAfterDics = 0.0;
        double commission = 0.0;

        double ivoiceTotal =0.00;
        double discountTotal =0.00;
        double taxTotal =0.00;
        double totalAmount =0.00;
        double totalAmountAfterDics = 0.0;
        double totalCommission =0.00;
        for(PatientInvoiceRequestWrapper pInvc : createInvoiceRequest.getInvoiceRequestWrapper())
        {
            InvoiceItems invItems ;
            if(pInvc.getId()==null){
                invItems = new InvoiceItems();
            }else{
                invItems =invoiceItemsRepository.findOne(pInvc.getId());
            }

            DoctorMedicalService doctorMedicalService = doctorMedicalServiceRepository.getByDoctor_IdAndMedicalService_Code(appointment.getDoctor().getId(),pInvc.getCode());
            double commissionRate = 0.0;
            if(doctorMedicalService != null) {
                commissionRate = doctorMedicalService.getComission();
            }
            invItems.setCode(pInvc.getCode());
            invItems.setDescription(pInvc.getDescription());
            invItems.setDiscountRate(pInvc.getDiscountRate());
            invItems.setTaxRate(pInvc.getTaxRate());
            invItems.setQuantity(pInvc.getQuantity());
            invItems.setServiceName(pInvc.getServiceName());
            invItems.setUnitFee(pInvc.getUnitFee());
            invItems.setCreatedOn(new Date());
            invItems.setUpdatedOn(new Date());
            invItems.setInvoice(invoice);
            invItems.setCommissionRate(commissionRate);
            invoiceItemsRepository.save(invItems);



            amount = invItems.getQuantity() * invItems.getUnitFee();
            discountAmount =(amount * invItems.getDiscountRate())/100;
            amountAfterDics = amount-discountAmount;
            commission = (amountAfterDics * invItems.getCommissionRate())/100;
            taxAmount = (amountAfterDics * invItems.getTaxRate())/100;


            discountTotal += discountAmount;
            taxTotal += taxAmount;
            totalAmountAfterDics+= amountAfterDics;
            totalCommission += commission;
            totalAmount += amount;
        }

        ivoiceTotal += (totalAmountAfterDics+taxTotal);
        invoice.setDoctorCommission(totalCommission);
        invoice.setDiscountAmount(discountTotal);
        invoice.setTaxAmount(taxTotal);
        invoice.setInvoiceAmount(totalAmount);
        invoice.setTotalInvoiceAmount(ivoiceTotal);
        patientInvoiceRepository.save(invoice);





        if(invoice.getCompleted()) {
            Doctor dr= appointment.getDoctor();
            double balance = dr.getBalance()== null? 0.0:dr.getBalance();
            dr.setBalance(balance+totalCommission);
        }
    }


// Save Payment
    @Transactional(rollbackOn = Throwable.class)
    public void savePayment(PaymentRequestWrapper paymentRequest)
    {
        Invoice invoice = patientInvoiceRepository.findOne(paymentRequest.getId());
        PatientInvoiceModeWrapper patientInvoiceModeWrapper=new PatientInvoiceModeWrapper();
     //   patientInvoiceModeWrapper.setPaymentAmount(paymentRequest.getPatientInvoiceModeWrapperList());

        if(invoice != null)
        {
            double advanceCredit = 0.00;
            if(paymentRequest.getUseAdvancedBal()){
                advanceCredit = paymentRequest.getUsedAdvanceDeposit() == null ? 0D : paymentRequest.getUsedAdvanceDeposit();
            }
            double receivedAmount = (invoice.getPaidAmount()== null? 0.00 : invoice.getPaidAmount()) + paymentRequest.getPaidAmount() + advanceCredit + paymentRequest.getDiscountAmount();

            Patient patient = patientRepository.findOne(invoice.getPatient().getId());
            double advanceConsumed = (patient.getAdvanceBalance() == null ? 0D : patient.getAdvanceBalance()) - advanceCredit;
            if(receivedAmount >= invoice.getTotalInvoiceAmount())
            {
                invoice.setStatus(InvoiceStatusEnum.CLOSE.toString());
            //    patient.setAdvanceBalance(advanceDeposit);
                patient.setAdvanceBalance(advanceConsumed);
                patientRepository.save(patient);
                receivedAmount = invoice.getTotalInvoiceAmount();
            }else if(receivedAmount < invoice.getTotalInvoiceAmount()){
                    double due=patient.getReceive_due();
                    if(due > 0){

                        double total=invoice.getTotalInvoiceAmount()-receivedAmount;
                        due=due+total;
                        patient.setReceive_due(due);
                    }else{

                        double total=invoice.getTotalInvoiceAmount()-receivedAmount;
                        patient.setReceive_due(total);
                    }
                    if(paymentRequest.getUsedReceiveDeposit()>0) {
                        double updatedReceived = patient.getReceive_due() - paymentRequest.getUsedReceiveDeposit();
                        patient.setReceive_due(updatedReceived);
                    }
                    patient=patientRepository.save(patient);
                    patientRepository.flush();


            }else if(paymentRequest.getUseAdvancedBal() && paymentRequest.getUsedAdvanceDeposit() > 0){
                patient.setAdvanceBalance(advanceConsumed);
                patientRepository.save(patient);
            }

            invoice.setPaidAmount(receivedAmount);
            invoice.setUpdatedOn(new Date());
            if(paymentRequest.isCompleted()==true){
                invoice.setCompleted(true);
            }else{
                invoice.setCompleted(false);
            }
            invoice = patientInvoiceRepository.save(invoice);
            patientInvoiceRepository.flush();

            Payment payment = new Payment();
            payment.setCreatedOn(new Date());
            payment.setUpdatedOn(new Date());
            payment.setPaymentId(hisUtilService.getPrefixId(ModuleEnum.PAYMENT));
            payment.setPaymentAmount(paymentRequest.getPaidAmount());
            payment.setTransactionType("Invoice");
    //        payment.setPaymentAmount((paymentRequest.getPaidAmount()+ advanceCredit));
            paymentRepository.save(payment);

            ReceiptPaymentType receiptPaymentType = new ReceiptPaymentType();
            receiptPaymentType.setPayment(payment);
            receiptPaymentType.setPaymentType(paymentTypeRepository.findOne(paymentRequest.getPaymentTypeId()));
            receiptPaymentType.setPaymentAmount(paymentRequest.getPaidAmount());
            receiptPaymentTypeRepository.save(receiptPaymentType);


            PatientInvoicePayment patientInvoicePayment = new PatientInvoicePayment();
            patientInvoicePayment.setCreatedOn(new Date());
            patientInvoicePayment.setUpdatedOn(new Date());
            patientInvoicePayment.setPaymentAmount(paymentRequest.getPaidAmount());
    //        patientInvoicePayment.setPaymentAmount((paymentRequest.getPaidAmount()+ advanceCredit) >= invoice.getInvoiceAmount()? invoice.getInvoiceAmount() : paymentRequest.getPaidAmount()+advanceCredit);
            patientInvoicePayment.setInvoice(invoice);
            patientInvoicePayment.setPayment(payment);
            patientInvoicePayment.setPatient(invoice.getPatient());

            patientInvoicePayment.setDiscountAmount(paymentRequest.getDiscountAmount());
            patientInvoicePayment.setAdvanceAmount(paymentRequest.getUsedAdvanceDeposit());

            patientInvoicePaymentRepository.save(patientInvoicePayment);


            for(int i=0;i<paymentRequest.getPatientInvoiceModeWrapperList().size();i++){
                PatientInvoiceModePayment patientInvoiceModePayment=new PatientInvoiceModePayment();
                patientInvoiceModePayment.setInvoice(invoice);
                patientInvoiceModePayment.setPatient(patient);
                patientInvoiceModePayment.setPaymentAmount(Double.valueOf(paymentRequest.getPatientInvoiceModeWrapperList().get(i).getPaymentAmount()));
                String paymentid=paymentRequest.getPatientInvoiceModeWrapperList().get(i).getPaymentId();
                PaymentType paymentObject=paymentTypeRepository.findOne(Long.valueOf(paymentid));
                patientInvoiceModePayment.setPayment(paymentObject);
                patientInvoiceModeRepository.save(patientInvoiceModePayment);
              //  patientInvoiceModeWrapper.setAppointmentId(invoice.getAppointment().getAppointmentId());


            }
        }
    }



    // Save Advance Payment
    // Added By : Naeem Saeed
    @Transactional
    public void saveAdvancePayment(AdvancePaymentRequestWrapper advancePaymentRequestWrapper)
    {
        double advanceDeposit = 0.00;

        Patient patient = patientRepository.findOne(advancePaymentRequestWrapper.getPatientId());
        if(patient.getAdvanceBalance() != null) {
            advanceDeposit = patient.getAdvanceBalance() + advancePaymentRequestWrapper.getAmount();
        }
        else {
            advanceDeposit = advancePaymentRequestWrapper.getAmount();
        }
        patient.setAdvanceBalance(advanceDeposit);
        patientRepository.save(patient);

        Payment payment = new Payment();
        payment.setCreatedOn(new Date());
        payment.setUpdatedOn(new Date());
        payment.setPaymentId(advancePaymentRequestWrapper.getPaymentId());
        payment.setPaymentAmount(advancePaymentRequestWrapper.getAmount());
        payment.setTransactionType("Advance");
        paymentRepository.save(payment);


        ReceiptPaymentType receiptPaymentType = new ReceiptPaymentType();
        receiptPaymentType.setPayment(payment);
        receiptPaymentType.setPaymentType(paymentTypeRepository.findOne(advancePaymentRequestWrapper.getPaymentTypeId()));
        receiptPaymentType.setPaymentAmount(advancePaymentRequestWrapper.getAmount());
        receiptPaymentTypeRepository.save(receiptPaymentType);

        PatientInvoicePayment patientInvoicePayment = new PatientInvoicePayment();
        patientInvoicePayment.setCreatedOn(new Date());
        patientInvoicePayment.setUpdatedOn(new Date());
        patientInvoicePayment.setPaymentAmount(advancePaymentRequestWrapper.getAmount()) ;
        patientInvoicePayment.setPayment(payment);
        patientInvoicePayment.setPatient(patient);
        patientInvoicePayment.setAdvanceAmount(0.00);
        patientInvoicePayment.setDiscountAmount(0.00);
        patientInvoicePaymentRepository.save(patientInvoicePayment);

    }

    public String getPaymentPrefixId(){
        return hisUtilService.getPrefixId(ModuleEnum.PAYMENT);
    }


    public String getRefundPrefixId(){
        return hisUtilService.getPrefixId(ModuleEnum.REFUND);
    }

    public void refundPayment(RefundPaymentRequestWrapper refundPaymentRequestWrapper)
    {
        double advanceDeposit = 0.00;

        PatientRefund patientRefund = new PatientRefund();
        Patient patient = patientRepository.findOne(refundPaymentRequestWrapper.getPatientId());
        if(refundPaymentRequestWrapper.getRefundType().equalsIgnoreCase("Advance")){
            advanceDeposit = patient.getAdvanceBalance() - refundPaymentRequestWrapper.getRefundAmount();
            patient.setAdvanceBalance(advanceDeposit);
            patientRepository.save(patient);
        }else{
            Invoice invoice = patientInvoiceRepository.findByInvoiceId(refundPaymentRequestWrapper.getInvoiceId());
//            invoice.setPaidAmount(invoice.getPaidAmount() - refundPaymentRequestWrapper.getRefundAmount());

            if((invoice.getPaidAmount()-refundPaymentRequestWrapper.getRefundAmount())<1){
                invoice.setStatus(InvoiceStatusEnum.REFUND.toString());
            }
            patientInvoiceRepository.save(invoice);
            patientRefund.setInvoice(invoice);
        }


        patientRefund.setRefundId(refundPaymentRequestWrapper.getRefundId());
        patientRefund.setCreatedOn(new Date());
        patientRefund.setUpdatedOn(new Date());
        patientRefund.setPatient(patient);
        patientRefund.setRefundType(refundPaymentRequestWrapper.getRefundType());
        patientRefund.setPaymentType(paymentTypeRepository.findOne(Long.parseLong(refundPaymentRequestWrapper.getPaymentTypeId())));

        patientRefund.setDescription(refundPaymentRequestWrapper.getDescription());
        patientRefund.setRefundAmount(refundPaymentRequestWrapper.getRefundAmount());
        patientRefundRepository.save(patientRefund);

    }

    // Save Bulk Payment
    @Transactional
    public void saveBulkPayment(BulkReceitRequestWrapper bulkReceitRequestWrapper)
    {
        double appliedAmount = bulkReceitRequestWrapper.getPaymentAmount();
        double advanceCredit = bulkReceitRequestWrapper.getUseAdvanceTotal();

        if(appliedAmount > 0 || advanceCredit > 0)
        {
            if(bulkReceitRequestWrapper.isUseAdvance() && advanceCredit > 0)
            {
                Patient patient = patientRepository.findOne(bulkReceitRequestWrapper.getPatientId());
                double advanceDeposit = patient.getAdvanceBalance() - advanceCredit;
                patient.setAdvanceBalance(advanceDeposit);
                patientRepository.save(patient);
            }

            Payment payment = new Payment();
            payment.setCreatedOn(new Date());
            payment.setUpdatedOn(new Date());
            payment.setPaymentId(hisUtilService.getPrefixId(ModuleEnum.PAYMENT));
            payment.setPaymentAmount(bulkReceitRequestWrapper.getPaymentAmount());
            payment.setTransactionType("Invoice");
            paymentRepository.save(payment);


            ReceiptPaymentType receiptPaymentType = new ReceiptPaymentType();
            receiptPaymentType.setPayment(payment);
            receiptPaymentType.setPaymentType(paymentTypeRepository.findOne(bulkReceitRequestWrapper.getPaymentTypeId()));
            receiptPaymentType.setPaymentAmount(bulkReceitRequestWrapper.getPaymentAmount());
            receiptPaymentTypeRepository.save(receiptPaymentType);

            for(InvoiceResponseWrapper iRW:bulkReceitRequestWrapper.getInvoiceListPaymentRequest())
            {
                Invoice invoice = patientInvoiceRepository.findOne(iRW.getId());
                if(invoice != null && iRW.isSelected())
                {
                    invoice.setPaidAmount(invoice.getPaidAmount() + iRW.getAdvanceBalance() + iRW.getAppliedAmount());

                    if(invoice.getPaidAmount()>= invoice.getTotalInvoiceAmount())
                    {
                        invoice.setStatus(InvoiceStatusEnum.CLOSE.toString());
                    }
                    invoice.setUpdatedOn(new Date());
                    patientInvoiceRepository.save(invoice);

                    PatientInvoicePayment patientInvoicePayment = new PatientInvoicePayment();
                    patientInvoicePayment.setCreatedOn(new Date());
                    patientInvoicePayment.setUpdatedOn(new Date());
            //        patientInvoicePayment.setPaymentAmount(iRW.getAdvanceBalance() + iRW.getAppliedAmount());
                    patientInvoicePayment.setPaymentAmount(iRW.getAppliedAmount());
                    patientInvoicePayment.setInvoice(invoice);
                    patientInvoicePayment.setPayment(payment);
                    patientInvoicePayment.setPatient(invoice.getPatient());

                    patientInvoicePayment.setDiscountAmount(iRW.getDiscountOnPayment());
                    patientInvoicePayment.setAdvanceAmount(iRW.getAdvanceBalance());
                    patientInvoicePaymentRepository.save(patientInvoicePayment);
                }
            }
        }
    }


    @Transactional
    public void deleteRemoveInviceItems(List<Long> ids,Long invID) {
        invoiceItemsRepository.deleteRemoveInviceItems(ids,invID);
    }

    public List<InvoiceItemsResponseWrapper> getInvoiceItemsById(long id){
        return invoiceItemsRepository.findAllByInvoiceId(patientInvoiceRepository.findByAppointmentId(id)!=null ? patientInvoiceRepository.findByAppointmentId(id).getId() : null);
    }




    // Get Patient ALl Invoice List
    // Added By : Naeem Saeed
    public List<InvoiceResponseWrapper> getInvoiceListByPatientId(long id){
        return patientInvoiceRepository.findAllByPatientId(id);
    }


    // Get Patient ALl Invoice List
    // Added By : Naeem Saeed
    public List<ReceiptListResponseWrapper> getReceiptList(){
        return paymentRepository.findAllReceipt();
    }


    public List<RefundListResponseWrapper> getRefundList(){
        return patientRefundRepository.findAllRefund();
    }


    @Transactional(rollbackOn = Throwable.class)
    public void generateInvoiceOnCheckIn(long id)
    {
        Appointment appointment = appointmentRepository.findOne(id);
        Invoice invoice = patientInvoiceRepository.findByAppointmentId(appointment.getId());
        if(invoice == null)
        {
            invoice = new Invoice();
            invoice.setAppointment(appointment);
            invoice.setPatient(appointment.getPatient());
            invoice.setInvoiceId(hisUtilService.getPrefixId(ModuleEnum.INVOICE));
            invoice.setCreatedOn(new Date());
            invoice.setUpdatedOn(new Date());
            invoice.setPaidAmount(0.0);
            invoice.setDiscountAmount(0.0);
            invoice.setTaxAmount(0.00);
            invoice.setTotalInvoiceAmount(0.00);
            invoice.setStatus(InvoiceStatusEnum.PENDING.toString());
            invoice.setCompleted(false);

            patientInvoiceRepository.save(invoice);

            double amount =0.00;
            double taxAmount =0.00;
            double discountAmount = 0.00;
            double amountAfterDics = 0.00;
            double ivoiceTotal =0.00;
            double commission = 0.00;

            MedicalService medicalService =appointment.getMedicalService();
            DoctorMedicalService doctorMedicalService = doctorMedicalServiceRepository.getByDoctor_IdAndMedicalService_Id(appointment.getDoctor().getId(),medicalService.getId());
            double commissionRate = 0.0;
            if(doctorMedicalService != null) {
                commissionRate = doctorMedicalService.getComission();
            }
            if(medicalService!=null){
                InvoiceItems invItems = new InvoiceItems();
                invItems.setCode(medicalService.getCode());
                invItems.setDescription(medicalService.getDescription());
                invItems.setDiscountRate(0.0);
                invItems.setTaxRate(medicalService.getTax().getRate());
                invItems.setQuantity(1);  // TO DO
                invItems.setServiceName(medicalService.getName());
                invItems.setUnitFee(medicalService.getFee());
                invItems.setCreatedOn(new Date());
                invItems.setUpdatedOn(new Date());
                invItems.setInvoice(invoice);
                invItems.setCommissionRate(commissionRate);
                invoiceItemsRepository.save(invItems);


                amount = invItems.getQuantity() * invItems.getUnitFee();
                discountAmount =(amount * invItems.getDiscountRate())/100;
                amountAfterDics = amount-discountAmount;
                commission = (amountAfterDics * invItems.getCommissionRate())/100;
                taxAmount = (amountAfterDics * invItems.getTaxRate())/100;

                ivoiceTotal += amountAfterDics+taxAmount;
                invoice.setTaxAmount(taxAmount);
                invoice.setDoctorCommission(commission);
                invoice.setInvoiceAmount(amount);
                invoice.setTotalInvoiceAmount(ivoiceTotal);
                patientInvoiceRepository.save(invoice);
            }
        }
    }

    public List<InvoiceResponseWrapper> getAllInvoice(){
        return patientInvoiceRepository.findAllInvoices();
    }
    public List<InvoiceResponseWrapper> getInvoiceListByStatus(String status){
        return patientInvoiceRepository.getInvoiceListByStatus(status);
    }

    public InvoiceResponseWrapper getPatientInvoicesBalance(Long patientId){
        return patientInvoiceRepository.getPatientInvoicesBalance(patientId);
    }
}
