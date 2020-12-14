package com.zainimtiaz.nagarro.service;

import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.zainimtiaz.nagarro.model.*;
import com.zainimtiaz.nagarro.repository.*;
import com.zainimtiaz.nagarro.utill.HISConstants;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.BranchWrapperPart;
import com.zainimtiaz.nagarro.wrapper.DepartmentWrapper;
import com.zainimtiaz.nagarro.wrapper.MedicalServiceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
 * @author    : Qari Muhammad Jamal
 * @Date      : 30-july-2018
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer    Date       Version  Operation  Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.service
 * @FileName  : MedicalServicesService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service
public class MedicalServicesService {

    @Autowired
    private MedicalServiceRepository medicalServiceRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TaxRepository taxRepository;
    @Autowired
    private BranchMedicalServiceRepository branchMedicalServiceRepository;
    @Autowired
    private DepartmentMedicalServiceRepository departmentMedicalServiceRepository;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UserService userService;

    public List<MedicalServiceWrapper> findAllPaginatedMedicalServices(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        Organization dbOrganization=organizationService.getAllOrgizationData();
        String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
        String systemCurrency=dbOrganization.getCurrencyFormat();
        String hoursFormat=dbOrganization.getHoursFormat();
        String dateFormat=dbOrganization.getDateFormat();
        String timeFormat=dbOrganization.getTimeFormat();

        List<MedicalService> mSList = medicalServiceRepository.findAllByCreatedOnNotNull(pageable);
        List<MedicalServiceWrapper> mSWList = null;
        if (mSList != null) {
            mSWList = new ArrayList<>();
            for (MedicalService ms : mSList) {
                if(systemCurrency!=null || (!systemCurrency.equals(""))){
                ms.setFee(Double.valueOf(formatCurrency((ms.getFee()),systemCurrency)));
                ms.setCost(Double.valueOf(formatCurrency((ms.getCost()),systemCurrency)));
                }
                MedicalServiceWrapper mSW = new MedicalServiceWrapper(ms);

                mSWList.add(mSW);
            }
        }
        return mSWList;
    }

    public List<MedicalServiceWrapper> findAllMedicalServices() {
        return medicalServiceRepository.findAllMedicalServiceWrappers();
    }

    public List<MedicalServiceWrapper> findAllMedicalServicesForDataTable() {
       /* List<MedicalServiceWrapper> mSWList = null;
        List<MedicalServiceWrapper> msLstW = new ArrayList<MedicalServiceWrapper>();*/

        return  medicalServiceRepository.findAllMedicalServiceWrappersForDataTable();
       /* for(int i=0;i<mSWList.size();i++){
            MedicalServiceWrapper ms=new MedicalServiceWrapper(mSWList.get(i));
            if (systemCurrency != null || (!systemCurrency.equals(""))) {
                mSWList.get(i).setFee(Double.valueOf(formatCurrency(String.valueOf(mSWList.get(i).getFee()), systemCurrency)));
                mSWList.get(i).setCost(Double.valueOf(formatCurrency(String.valueOf(mSWList.get(i).getCost()), systemCurrency)));
                msLstW.add(ms);
            }
        }*/
        /*for (MedicalServiceWrapper ms : mSWList) {
            if (systemCurrency != null || (!systemCurrency.equals(""))) {
                ms.setFee(Double.valueOf(formatCurrency(String.valueOf(ms.getFee()), systemCurrency)));
                ms.setCost(Double.valueOf(formatCurrency(String.valueOf(ms.getCost()), systemCurrency)));
                mSWList.add(ms);
            }


        }*/
      //  return msLstW;
    }
    public List<MedicalServiceWrapper> getAllMedicalServicesForAppointment() {
        return medicalServiceRepository.findAllMedicalServicesForAppointment();
    }

    public int countAllMedicalServices() {
        return medicalServiceRepository.findAll().size();
    }

    public List<MedicalServiceWrapper> getMedicalServicesByDeptId(Long deptId){
      //  List<MedicalServiceWrapper> list = medicalServiceRepository.findMedicalServicesByDepartmentId(deptId);
        return medicalServiceRepository.findMedicalServicesByDepartmentId(deptId);
    }

    public MedicalService findByTitleAndDeletedFalse(String name) {
        return medicalServiceRepository.findByName(name);
    }

    public boolean findByNameAgainstId(long id, String name) {
        return medicalServiceRepository.findByIdNotAndName(id, name) == null ? false : true;
    }

    public MedicalServiceWrapper findByTitleAndBranchAndDptDeletedFalse(String title, long branchId, long dptId) {
        return null; //medicalServiceRepository.findOneByNameAndDptAndBranch(title, branchId, dptId);
    }

    @Transactional(rollbackOn = Throwable.class)
    public String saveMedicalService(MedicalServiceWrapper createRequest) {
        Tax tax = taxRepository.findOne(createRequest.getTax().getId());
        MedicalService medicalService = new MedicalService(createRequest);
        if (tax != null) {
            medicalService.setTax(tax);
        }

    //    medicalService.setFee(Double.parseDouble(createRequest.getStrFee()));
    //    medicalService.setCost(Double.parseDouble(createRequest.getStrCost()));
        String dteFileUpload= HISCoreUtil.convertDateToStringUpload(new Date());
        String url = null;
        String imgURL = null;
        if (createRequest.getImage() != null) {
            try {
                imgURL = userService.saveImageMedical(createRequest.getImage(),
                        HISConstants.S3_USER_MEDICAL_DIRECTORY_PATH, createRequest.getCode() + "_" + dteFileUpload
                                + "_"
                                + HISConstants.S3_USER_MEDICAL_THUMBNAIL_GRAPHIC_NAME, createRequest.getCode()
                                + "_" + createRequest.getCode()
                                + "_"
                                + dteFileUpload
                                + "_"
                                + HISConstants.S3_USER_MEDICAL_THUMBNAIL_GRAPHIC_NAME,
                        "/"
                                + HISConstants.S3_USER_MEDICAL_DIRECTORY_PATH
                                + createRequest.getCode()
                                + "_"
                                + dteFileUpload
                                + "_"
                                + HISConstants.S3_USER_MEDICAL_THUMBNAIL_GRAPHIC_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*userService.saveImage(createRequest.getImage(), HISConstants.S3_USER_MEDICAL_DIRECTORY_PATH, createRequest.getCode() + "_" + dteFileUpload
                            + "_"
                            + HISConstants.S3_USER_ORGANIZATION_THUMBNAIL_GRAPHIC_NAME, +createRequest.getId()
                            + "_" + createRequest.getCode()
                            + "_"
                            + dteFileUpload
                            + "_"
                            + HISConstants.S3_USER_MEDICAL_THUMBNAIL_GRAPHIC_NAME,
                    "/"
                            + HISConstants.S3_USER_MEDICAL_DIRECTORY_PATH
                            + createRequest.getCode()
                            + "_"
                            + dteFileUpload
                            + "_"
                            + HISConstants.S3_USER_MEDICAL_THUMBNAIL_GRAPHIC_NAME,fileName);*/
            if (HISCoreUtil.isValidObject(imgURL)) {
                medicalService.setUrl(imgURL);


            }
        }
        medicalServiceRepository.save(medicalService);
        if (HISCoreUtil.isListValid(createRequest.getBranches())) {
            List<BranchMedicalService> list = new ArrayList<>();
            for (Long  branchWrapper : createRequest.getSelectedBranchesMS()) {
                    Branch branch = this.branchRepository.findOne(branchWrapper);
                    BranchMedicalService branchMedicalService = new BranchMedicalService(branch, medicalService);
                    list.add(branchMedicalService);

            }
            if (HISCoreUtil.isListValid(list)) {
                branchMedicalServiceRepository.save(list);
            }
        }
        if (HISCoreUtil.isListValid(createRequest.getDepartments())) {
            List<DepartmentMedicalService> list = new ArrayList<>();
            for (DepartmentWrapper departmentWrapper : createRequest.getDepartments()) {
           //     if (departmentWrapper.isCheckedDepartment()) {
                    Department department = this.departmentRepository.findOne(departmentWrapper.getId());
                    DepartmentMedicalService departmentMedicalService = new DepartmentMedicalService(department, medicalService);
                    list.add(departmentMedicalService);
            //    }
            }


            if (HISCoreUtil.isListValid(list)) {
                departmentMedicalServiceRepository.save(list);
            }
        }
        return "";
    }

    @Transactional(rollbackOn = Throwable.class)
    public String updateMedicalService(MedicalServiceWrapper createRequest) {

        MedicalService medicalService = this.medicalServiceRepository.findOne(createRequest.getId());
        new MedicalService(medicalService, createRequest);
        if (createRequest.getTax() != null) {
            Tax tax = taxRepository.findOne(createRequest.getTax().getId());
            if (tax != null) {
                medicalService.setTax(tax);
            } else {
                medicalService.setTax(null);
            }
        }
       /* DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        df.setDecimalFormatSymbols(symbols);
            = df.parse(createRequest.getStrFee();*/
       /* medicalService.setFee(Double.parseDouble(createRequest.getStrFee()));
        medicalService.setCost(Double.parseDouble(createRequest.getStrCost().replace(',','.')));*/
        /*if(systemCurrency!=null || (!systemCurrency.equals(""))){
            medicalService.setFee(Double.valueOf(formatCurrencyDisplay((createRequest.getFee()),systemCurrency)));
            medicalService.setCost(Double.valueOf(formatCurrencyDisplay((createRequest.getCost()),systemCurrency)));
        }*/
        String dteFileUpload=HISCoreUtil.convertDateToStringUpload(new Date());
        String url = null;
        String imgURL = null;
        if (createRequest.getImage() != null) {
            try {
                imgURL = userService.saveImageMedical(createRequest.getImage(),
                        HISConstants.S3_USER_MEDICAL_DIRECTORY_PATH, createRequest.getCode() + "_" + dteFileUpload
                                + "_"
                                + HISConstants.S3_USER_MEDICAL_THUMBNAIL_GRAPHIC_NAME, createRequest.getCode()
                                + "_" + createRequest.getCode()
                                + "_"
                                + dteFileUpload
                                + "_"
                                + HISConstants.S3_USER_MEDICAL_THUMBNAIL_GRAPHIC_NAME,
                        "/"
                                + HISConstants.S3_USER_MEDICAL_DIRECTORY_PATH
                                + createRequest.getCode()
                                + "_"
                                + dteFileUpload
                                + "_"
                                + HISConstants.S3_USER_MEDICAL_THUMBNAIL_GRAPHIC_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*userService.saveImage(createRequest.getImage(), HISConstants.S3_USER_MEDICAL_DIRECTORY_PATH, createRequest.getCode() + "_" + dteFileUpload
                            + "_"
                            + HISConstants.S3_USER_ORGANIZATION_THUMBNAIL_GRAPHIC_NAME, +createRequest.getId()
                            + "_" + createRequest.getCode()
                            + "_"
                            + dteFileUpload
                            + "_"
                            + HISConstants.S3_USER_MEDICAL_THUMBNAIL_GRAPHIC_NAME,
                    "/"
                            + HISConstants.S3_USER_MEDICAL_DIRECTORY_PATH
                            + createRequest.getCode()
                            + "_"
                            + dteFileUpload
                            + "_"
                            + HISConstants.S3_USER_MEDICAL_THUMBNAIL_GRAPHIC_NAME,fileName);*/
            if (HISCoreUtil.isValidObject(imgURL)) {
                medicalService.setUrl(imgURL);


            }
        }
        medicalServiceRepository.save(medicalService);
        if (HISCoreUtil.isListValid(createRequest.getBranches())) {
            List<BranchMedicalService> list = new ArrayList<>();
            this.branchMedicalServiceRepository.deleteByMedicalService_id(medicalService.getId());
            for (BranchWrapperPart branchWrapper : createRequest.getBranches()) {
                if (branchWrapper.isCheckedBranch()) {
                    Branch branch = this.branchRepository.findOne(branchWrapper.getId());
                    BranchMedicalService branchMedicalService = new BranchMedicalService(branch, medicalService);
                    list.add(branchMedicalService);
                }
            }
            if (HISCoreUtil.isListValid(list)) {
                branchMedicalServiceRepository.save(list);
            }
        }
        if (HISCoreUtil.isListValid(createRequest.getDepartments())) {
            List<DepartmentMedicalService> list = new ArrayList<>();
            this.departmentMedicalServiceRepository.deleteByMedicalService_id(medicalService.getId());
            for (DepartmentWrapper departmentWrapper : createRequest.getDepartments()) {
             //   if (departmentWrapper.isCheckedDepartment()) {
                    Department department = this.departmentRepository.findOne(departmentWrapper.getId());
                    DepartmentMedicalService departmentMedicalService = new DepartmentMedicalService(department, medicalService);
                    list.add(departmentMedicalService);
          //     }
            }
            if (HISCoreUtil.isListValid(list)) {
                departmentMedicalServiceRepository.save(list);
            }
        }
        return "";
    }

    @Transactional(rollbackOn = Throwable.class)
    public boolean deleteMedicalService(long msId) {
        MedicalService deleteMedicalService = medicalServiceRepository.findOne(msId);
        if (HISCoreUtil.isValidObject(deleteMedicalService)) {
            medicalServiceRepository.delete(deleteMedicalService);
            return true;
        }
        return false;
    }

    public List<MedicalServiceWrapper> searchMedicalServiceByParam(String serviceName,
                                                                   String searchCode,
                                                                   Long branchId,
                                                                   Long departmentId,
                                                                   Double serviceFee,
                                                                   int pageNo,
                                                                   int pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize);
        return medicalServiceRepository.findAllByParam(serviceName,searchCode,branchId, departmentId, serviceFee, pageable);
    }

    public int countSearchMedicalServiceByParam(String serviceName,
                                                String searchCode,
                                                Long branchId,
                                                Long departmentId,
                                                Double serviceFee) {
        return medicalServiceRepository.countAllByParam(serviceName, searchCode, branchId, departmentId, serviceFee).size();
    }

    public List<BranchWrapperPart> getCheckedBranchesByMedicalServiceId(long msId) {
        List<BranchWrapperPart> branchWrappers = new ArrayList<>();
        branchWrappers.addAll(this.findMedicalServicesDetailsById(msId).getCheckedBranches());
        return branchWrappers;
    }

    public List<DepartmentWrapper> getCheckedDepartsByMedicalServiceId(long msId) {
        List<DepartmentWrapper> branchWrappers = new ArrayList<>();
        branchWrappers.addAll(this.findMedicalServicesDetailsById(msId).getCheckedDepartments());
        return branchWrappers;
    }

    public MedicalServiceWrapper findMedicalServicesDetailsById(Long msId) {
        MedicalService mS = medicalServiceRepository.findOne(msId);
        MedicalServiceWrapper mSW = null;
        if (mS != null) {
            mSW = new MedicalServiceWrapper(mS);
            if (mS.getDepartmentMedicalServices() != null) {
                mSW.setCheckedDepartments(new ArrayList<>());
                for (DepartmentMedicalService dMS : mS.getDepartmentMedicalServices()) {
                    if (dMS.getDepartment() != null) {
                        mSW.getCheckedDepartments().add(new DepartmentWrapper(dMS.getDepartment()));
                    }
                }
            }
            if (mS.getBranchMedicalServices() != null) {
                mSW.setCheckedBranches(new ArrayList<>());
                for (BranchMedicalService branchMedicalService : mS.getBranchMedicalServices()) {
                    if (branchMedicalService.getBranch() != null) {
                        mSW.getCheckedBranches().add(new BranchWrapperPart(branchMedicalService.getBranch()));
                    }
                }
            }
            mSW.setImgUrl(mS.getUrl());
        }
        return mSW;
    }

    public static String formatCurrency(double amount,String format) {
        String returnFormat="";
        if(format.equals("123,456")){
            String doubleValue=String.valueOf(amount);
            if(doubleValue.length()==3){
                DecimalFormat formatter = new DecimalFormat("###,000");
                amount=Double.valueOf(doubleValue);
                returnFormat= formatter.format(new BigDecimal(amount));
            }
             if(doubleValue.length()==2){
                DecimalFormat formatter = new DecimalFormat("0##,000");
                 amount=Double.valueOf(doubleValue);
                returnFormat= formatter.format(new BigDecimal(amount));
             }
            if(doubleValue.length()==1){
                DecimalFormat formatter = new DecimalFormat("#00,000");
                amount=Double.valueOf(doubleValue);
                returnFormat= formatter.format(new BigDecimal(amount));
            } if(doubleValue.length()==4){
                DecimalFormat formatter = new DecimalFormat("###,#00");
                amount=Double.valueOf(doubleValue);
                returnFormat= formatter.format(new BigDecimal(amount));
            }if(doubleValue.length()==5){
                DecimalFormat formatter = new DecimalFormat("###,##0");
                amount=Double.valueOf(doubleValue);
                returnFormat= formatter.format(new BigDecimal(amount));
            }else{
                DecimalFormat formatter = new DecimalFormat("###,000");
                amount=Double.valueOf(doubleValue);
                returnFormat= formatter.format(new BigDecimal(amount));
            }

        }else if(format.equals("123,456.00")){
            String doubleValue=String.valueOf(amount);
            if(doubleValue.length()==3){
                DecimalFormat formatter = new DecimalFormat("###,000");
                amount=Double.valueOf(doubleValue);
                returnFormat= formatter.format(new BigDecimal(amount));
            }
            if(doubleValue.length()==2){
                DecimalFormat formatter = new DecimalFormat("0##,000");
                amount=Double.valueOf(doubleValue);
                returnFormat= formatter.format(new BigDecimal(amount));
            }
            if(doubleValue.length()==1){
                DecimalFormat formatter = new DecimalFormat("#00,000");
                amount=Double.valueOf(doubleValue);
                returnFormat= formatter.format(new BigDecimal(amount));
            } if(doubleValue.length()==4){
                DecimalFormat formatter = new DecimalFormat("###,#00");
                amount=Double.valueOf(doubleValue);
                returnFormat= formatter.format(new BigDecimal(amount));
            }if(doubleValue.length()==5){
                DecimalFormat formatter = new DecimalFormat("###,##0");
                amount=Double.valueOf(doubleValue);
                returnFormat= formatter.format(new BigDecimal(amount));
            }
            else{
            DecimalFormat formatter = new DecimalFormat("###,000.00");
            returnFormat=formatter.format((amount));}
        }else if(format.equals("123456")){
            DecimalFormat formatter = new DecimalFormat("000000");
            returnFormat = formatter.format((amount));
        }else if(format.equals("123456.00")){
            DecimalFormat formatter = new DecimalFormat("000000.00");
            returnFormat= formatter.format((amount));
        }
        return returnFormat;
    }


//Latest Function
    public static String formatCurrencyDisplay(double amount, String format) {
        String returnFormat = "";
        if (format.equals("123,456")) {

            String pattern = "###,###";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);

            returnFormat= decimalFormat.format(amount);
      //      System.out.println("Currency Format"+returnFormat);
        //    DecimalFormat formatter = new DecimalFormat("###,000");
        //    returnFormat = formatter.format(Double.parseDouble(amount));
            // "###,###,##0.00"
        } else if (format.equals("123,456.00")) {

            String pattern = "###,###.00";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);

            returnFormat = decimalFormat.format(amount);
      //      System.out.println("Currency Format"+returnFormat);

            /*DecimalFormat formatter = new DecimalFormat("###,###.00");
            returnFormat = formatter.format(Double.parseDouble(amount));*/
        } else if (format.equals("123456")) {

            returnFormat = new DecimalFormat("###").format(amount);
        //    System.out.println(returnFormat);
            return returnFormat;
      //      DecimalFormat formatter = new DecimalFormat("######");
      //      returnFormat = formatter.format(Double.parseDouble(amount));
        } else if (format.equals("123456.00")) {

            returnFormat = new DecimalFormat("###.00").format(amount);
        //    System.out.println(returnFormat);
            return returnFormat;
       //     DecimalFormat formatter = new DecimalFormat("###.00");
        //    returnFormat = formatter.format(Double.parseDouble(amount));
        }
        return returnFormat;
    }


    public static  double parseDecimal(String input) throws ParseException {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        ParsePosition parsePosition = new ParsePosition(0);
        Number number = numberFormat.parse(input, parsePosition);

        if(parsePosition.getIndex() != input.length()){
            throw new ParseException("Invalid input", parsePosition.getIndex());
        }

        return number.doubleValue();
    }
}