package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.enums.ModuleEnum;
import com.zainimtiaz.nagarro.model.Organization;
import com.zainimtiaz.nagarro.model.Tax;
import com.zainimtiaz.nagarro.repository.TaxRepository;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.TaxWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 14-May-18
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
 * @FileName  : TaxService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service
public class TaxService {

    @Autowired
    TaxRepository taxRepository;
    @Autowired
    HISUtilService hisUtilService;
    @Autowired
    private OrganizationService organizationService;

    public List<TaxWrapper> findAllActiveTax() {
        return taxRepository.findAllByActiveTrue(true);
    }

    public List<TaxWrapper> getAllTaxesForDataTable() {
        return taxRepository.findAllTaxesForDataTable();
    }
    public List<TaxWrapper> findAllPaginatedTax(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        return taxRepository.findAllByCreatedOnNotNull(pageable);
    }

    public int countAllTax() {
        return taxRepository.findAll().size();
    }

    @Transactional(rollbackOn = Throwable.class)
    public void deleteTax(long taxId) {
        taxRepository.delete(taxId);
    }

    public Tax findTaxById(long taxId) {
        return taxRepository.findOne(taxId);
    }

    @Transactional(rollbackOn = Throwable.class)
    public void saveTax(TaxWrapper taxWrapper) throws ParseException {
        Tax tax = new Tax(taxWrapper);
        tax.setTaxId(this.hisUtilService.generatePrefix(ModuleEnum.TAX));
        // Implement Time Zone
        Organization dbOrganization=organizationService.getAllOrgizationData();
        String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
      //  Date dteFrom=new Date();
      //  Date dteTo=new Date();
        String systemDateFormat=dbOrganization.getDateFormat();
        String systemTimeFormat=dbOrganization.getTimeFormat();
        String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
        String standardFormatDateTime=systemDateFormat+" "+systemTimeFormat;
    //    System.out.println("Time"+currentTime);
      //  dte=problemWrapper.getDatePrescribedDate();
     //   Date dteFrom=HISCoreUtil.convertStringDateObjectTax(taxWrapper.getFromDate());
     //   Date dteTo=HISCoreUtil.convertStringDateObjectTax(taxWrapper.getToDate());
        String readDateFrom=HISCoreUtil.convertDateToTimeZone(taxWrapper.getFromDate(),"yyyy-MM-dd",Zone);
        String readDateTo=HISCoreUtil.convertDateToTimeZone(taxWrapper.getToDate(),"yyyy-MM-dd",Zone);
        Date scheduledDateFrom=HISCoreUtil.convertStringDateObjectTax(readDateFrom);
        Date scheduledDateTo=HISCoreUtil.convertStringDateObjectTax(readDateTo);
        tax.setFromDate(scheduledDateFrom);
        tax.setToDate(scheduledDateTo);
        taxRepository.save(tax);
        hisUtilService.updatePrefix(ModuleEnum.TAX);
    }

    public boolean isAlreadyExist(TaxWrapper taxWrapper) {
        Boolean isAlreadyExist = false;

        if (taxWrapper.getId() > 0) {
            List<Tax> taxes = this.taxRepository.findAllByNameAndIdNot(taxWrapper.getName(), taxWrapper.getId());
            if (!HISCoreUtil.isListEmpty(taxes)) {
                isAlreadyExist = true;
            }
        } else {
            Tax tax = this.taxRepository.findByName(taxWrapper.getName());
            if (HISCoreUtil.isValidObject(tax)) {
                isAlreadyExist = true;
            }
        }
        return isAlreadyExist;
    }

    public void updateTaxService(TaxWrapper updateRequest) throws ParseException {
        Tax dbTax = this.taxRepository.findOne(updateRequest.getId());
        new Tax(dbTax, updateRequest);
        Organization dbOrganization=organizationService.getAllOrgizationData();
        String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
        String systemDateFormat=dbOrganization.getDateFormat();
        String systemTimeFormat=dbOrganization.getTimeFormat();
        String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
        String standardFormatDateTime=systemDateFormat+" "+systemTimeFormat;
    //    System.out.println("Time"+currentTime);
        Date dteFrom=HISCoreUtil.convertStringDateObjectTax(updateRequest.getStrfromDate());
        Date dteTo=HISCoreUtil.convertStringDateObjectTax(updateRequest.getStrtoDate());
        String readDateFrom=HISCoreUtil.convertDateToTimeZone(dteFrom,"yyyy-MM-dd",Zone);
        String readDateTo=HISCoreUtil.convertDateToTimeZone(updateRequest.getToDate(),"yyyy-MM-dd",Zone);
        Date scheduledDateFrom=HISCoreUtil.convertStringDateObjectTax(readDateFrom);
        Date scheduledDateTo=HISCoreUtil.convertStringDateObjectTax(readDateTo);
        dbTax.setFromDate(scheduledDateFrom);
        dbTax.setToDate(scheduledDateTo);
        this.taxRepository.save(dbTax);
    }

    public List<TaxWrapper> searchByTaxByName(String searchTaxName, int pageNo, int pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize);
        return taxRepository.findAllByNameContaining(searchTaxName, pageable);
    }

    public int countSearchByTaxByName(String searchTaxName) {
        return taxRepository.findAllByNameContaining(searchTaxName).size();
    }

    public boolean hasChild(long taxId) {
        Tax tax = this.taxRepository.findOne(taxId);
        if (tax != null && tax.getMedicalServices() != null && tax.getMedicalServices().size() > 0) {
            return true;
        }
        return false;
    }
}
