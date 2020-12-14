package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.GeneralLedger;
import com.zainimtiaz.nagarro.model.PaymentType;
import com.zainimtiaz.nagarro.repository.GeneralLedgerRepository;
import com.zainimtiaz.nagarro.repository.PaymentTypeRepository;
import com.zainimtiaz.nagarro.wrapper.response.PaymentTypeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PaymentTypeService {

    @Autowired
    private PaymentTypeRepository paymentRepository;
    @Autowired
    GeneralLedgerRepository generalLedgerRepository;
    // Fetch All Record
    public List<PaymentTypeWrapper> getAllPaymentType() {
//        List<PaymentType> paymentType = paymentRepository.findAll();
        return paymentRepository.getAllWithWrapper();
    }

    // Fetch List Record
    public List<PaymentTypeWrapper> getListPaymentType() {
        return paymentRepository.getAllWithWrapper();
    }

    //Delete Record for Payment Delete
    @Transactional(rollbackOn = Throwable.class)
    public void deletePaymentType(Long id) {

        PaymentType type = this.paymentRepository.findOne(id);
        if (type != null) {
            this.paymentRepository.delete(type);

        }

    }

    //Save Record For Payment Type
    @Transactional(rollbackOn = Throwable.class)
    public PaymentType savePaymentAPI(PaymentTypeWrapper paymentTypeWrapper) {
        PaymentType paymentTypeObj=new PaymentType();
    //    paymentTypeObj.setBankGlCharges(paymentTypeWrapper.getBankGlCharges());
        paymentTypeObj.setPaymentGlAccount(paymentTypeWrapper.getPaymentGlAccount());
        paymentTypeObj.setActive(paymentTypeWrapper.getActive());
    //    paymentTypeObj.setMaxCardCharges(paymentTypeWrapper.getMaxCardCharges());
        paymentTypeObj.setPatient(paymentTypeWrapper.isPatient());
    //    paymentTypeObj.setPayCredit(paymentTypeWrapper.getPayCredit());
        if(paymentTypeWrapper.getPaymentMode().equalsIgnoreCase("Card")){
            if(paymentTypeWrapper.getStrServiceCharges()!=null){
                paymentTypeObj.setServiceCharges(Double.valueOf(paymentTypeWrapper.getStrServiceCharges()));
            }
            if(paymentTypeWrapper.getStrmaxCardCharges()!=null){
                paymentTypeObj.setMaxCardCharges(Double.valueOf(paymentTypeWrapper.getStrmaxCardCharges()));
            }
         //   paymentTypeObj.setMaxCardCharges(paymentTypeWrapper.getMaxCardCharges());
            paymentTypeObj.setBankGlCharges(paymentTypeWrapper.getBankGlCharges());
            paymentTypeObj.setPayCredit(paymentTypeWrapper.getPayCredit());
       //     paymentTypeObj.setServiceCharges(paymentTypeWrapper.getServiceCharges());
            paymentTypeObj.setPatient(paymentTypeWrapper.isPatient());
        }
        paymentTypeObj.setPaymentMode(paymentTypeWrapper.getPaymentMode());
        paymentTypeObj.setPaymentTitle(paymentTypeWrapper.getPaymentTitle());
     //   paymentTypeObj.setServiceCharges(paymentTypeWrapper.getServiceCharges());
        paymentTypeObj.setPaymentMode(paymentTypeWrapper.getPaymentMode());
        paymentTypeObj.setPaymentPurpose(paymentTypeWrapper.getPaymentPurpose());
      //  paymentTypeObj.
       // paymentTypeObj.
        /*if(paymentTypeWrapper.getPaymentMode().equalsIgnoreCase("Card")) {
            return paymentRepository.(paymentTypeObj);
        }*/
        return paymentRepository.save(paymentTypeObj);
    }

    // Update Record////////////////////////////
    @Transactional(rollbackOn = Throwable.class)
    public PaymentType updatePaymentType(PaymentTypeWrapper paymentType) {
        PaymentType paymentEntity = paymentRepository.findOne(Long.valueOf(paymentType.getId()));
        if(paymentType.getPaymentGlAccountId()>0){
        GeneralLedger gl= generalLedgerRepository.findOne(paymentType.getPaymentGlAccountId());
            paymentEntity.setPaymentGlAccount(gl);
        }else{
            paymentEntity.setPaymentGlAccount(paymentType.getPaymentGlAccount());
        }

        paymentEntity.setPaymentPurpose(paymentType.getPaymentPurpose());
        paymentEntity.setPaymentTitle(paymentType.getPaymentTitle());
        paymentEntity.setPaymentMode(paymentType.getPaymentMode());
        paymentEntity.setActive(paymentType.getActive());
        if(paymentType.getPaymentMode().equalsIgnoreCase("Card")){

            paymentEntity.setMaxCardCharges(Double.valueOf(paymentType.getStrmaxCardCharges()));
            paymentEntity.setBankGlCharges(paymentType.getBankGlCharges());
            paymentEntity.setPayCredit(paymentType.getPayCredit());
            paymentEntity.setServiceCharges(Double.valueOf(paymentType.getStrServiceCharges()));
            paymentEntity.setPatient(paymentType.isPatient());

        }
        return paymentRepository.save(paymentEntity);
    }

    public static  Double makeDecimalFormat(Double val) {
        return new BigDecimal(val.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
