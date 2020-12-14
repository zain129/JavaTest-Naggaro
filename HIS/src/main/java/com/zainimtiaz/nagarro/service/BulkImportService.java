package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.enums.GenderTypeEnum;
import com.zainimtiaz.nagarro.enums.ModuleEnum;
import com.zainimtiaz.nagarro.enums.PatientStatusTypeEnum;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.zainimtiaz.nagarro.model.*;
import com.zainimtiaz.nagarro.repository.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Zain on 12/10/2018.
 */
@Service
public class BulkImportService {

    @Autowired
    private DrugRepository drugRepository;
    @Autowired
    private LabTestSpecimanRepository labTestSpecimanRepository;
    @Autowired
    private ICDCodeRepository codeRepository;
    @Autowired
    private ICDVersionRepository versionRepository;
    @Autowired
    private ICDCodeVersionRepository codeVersionRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HISUtilService hisUtilService;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private DrugManufacturerRepository drugManufacturerRepository;

    @Value("${spring.http.multipart.location}")
    private String tmpFilePath;

    public int importDrugRecords(String fileName) throws IllegalStateException, InvalidFormatException, IOException {
        File file = new File(this.tmpFilePath + fileName);
        AtomicInteger records = new AtomicInteger(0);
        Workbook workBook = WorkbookFactory.create(file);
        Sheet excelSheet = workBook.getSheetAt(0);
        Drug drug = null;

        for (Row row : excelSheet) {
            if (row != null && row.getRowNum() > 0 && row.getCell(0) != null) {
                if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null || row.getCell(3) == null
                        || row.getCell(4) == null || row.getCell(5) == null || row.getCell(6) == null)
                    continue;
                Drug oldDrug = drugRepository.findDrugByBrandName(row.getCell(0).getStringCellValue() + "");
                if (oldDrug != null)
                    continue;
                drug = new Drug();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    switch (j) {
                        case 0:
                            drug.setDrugName(row.getCell(j).getStringCellValue());
                            break;
                        case 1:
                            drug.setGenericName(row.getCell(j).getStringCellValue());
                            break;
                        case 2:
                            drug.setCompanyName(row.getCell(j).getStringCellValue());
                            break;
                        case 3:
                            drug.setRoute(row.getCell(j).getStringCellValue());
                            break;
                        case 4:
                            String[] arrSplit = row.getCell(j).getStringCellValue().split("[,]");
                            drug.setStrengths(Arrays.asList(arrSplit));
                            break;
                        case 5:
                            drug.setuOM(row.getCell(j).getStringCellValue());
                            break;
                        case 6:
                            drug.setDrugManufacturer(drugManufacturerRepository.findOne((long) row.getCell(j).getNumericCellValue()));
                            break;
                    }
                }
                drug.setActive(true);
                drugRepository.save(drug);
                records.getAndIncrement();
            }
        }
        workBook.close();
        this.deleteFile(file);
        return records.get();
    }

    public int importLabTestRecords(String fileName) throws IllegalStateException, InvalidFormatException, IOException {
        File file = new File(this.tmpFilePath + fileName);
        AtomicInteger records = new AtomicInteger(0);
        Workbook workBook = WorkbookFactory.create(file);

        //Read sheet inside the workbook by its Index
        Sheet excelSheet = workBook.getSheetAt(0);
        LabTestSpeciman labTestSpeciman = null;

        for (Row row : excelSheet) {
            if (row != null && row.getRowNum() > 0 && row.getCell(0) != null) {
                if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null || row.getCell(3) == null)
                    continue;
                LabTestSpeciman oldlabTestSpeciman = labTestSpecimanRepository.findDuplicateTestEntry(row.getCell(0).getStringCellValue() + "",
                        row.getCell(1).getStringCellValue() + "", row.getCell(2).getNumericCellValue() + "",
                        row.getCell(3).getNumericCellValue() + "");
                if (oldlabTestSpeciman != null)
                    continue;
                labTestSpeciman = new LabTestSpeciman();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    switch (j) {
                        case 0:
                            labTestSpeciman.setTestCode(row.getCell(j).getStringCellValue());
                            break;
                        case 1:
                            labTestSpeciman.setTestName(row.getCell(j).getStringCellValue());
                            break;
                        case 2:
                            labTestSpeciman.setMinNormalRange(row.getCell(j).getNumericCellValue() + "");
                            break;
                        case 3:
                            labTestSpeciman.setMaxNormalRange(row.getCell(j).getNumericCellValue() + "");
                            break;
                        case 4:
                            labTestSpeciman.setUnit(row.getCell(j).getStringCellValue());
                            break;
                    }
                }
                labTestSpeciman.setSpecimanId(hisUtilService.getPrefixId(ModuleEnum.LAB_TEST));
                labTestSpecimanRepository.save(labTestSpeciman);
                records.getAndIncrement();
            }
        }
        workBook.close();
        this.deleteFile(file);
        return records.get();
    }

    @Transactional(rollbackOn = Throwable.class)
    public int importIcdCodeRecords(String fileName) throws IllegalStateException, InvalidFormatException, IOException, ParseException {
        File file = new File(this.tmpFilePath + fileName);
        AtomicInteger records = new AtomicInteger(0);
        Workbook workBook = WorkbookFactory.create(file);

        //Read sheet inside the workbook by its Index
        Sheet excelSheet = workBook.getSheetAt(0);
        ICDCode icdCode = null;
        ICDVersion icdVersion = null;
        ICDCodeVersion icdCodeVersion = null;

        for (Row row : excelSheet) {
            if (row != null && row.getRowNum() > 0 && row.getCell(0) != null) {
                if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null)
                    continue;

                List<ICDVersion> oldVersions = new ArrayList<>();
                List<ICDVersion> newVersions = new ArrayList<>();

                ICDCode oldIcdCode = codeRepository.findByCode(row.getCell(1).getStringCellValue() + "");
                ICDVersion oldIcdVersion = null;
                String[] arrSplit = row.getCell(0).getStringCellValue().split("[,]");

                for (int a = 0; a < arrSplit.length; a++) {
                    String versionName = arrSplit[a].trim();
                    oldIcdVersion = versionRepository.findByName(versionName);
                    if (oldIcdVersion != null) {
                        oldVersions.add(oldIcdVersion);
                    } else {
                        icdVersion = new ICDVersion();
                        icdVersion.setName(versionName);
                        icdVersion.setStatus(true);
                        versionRepository.save(icdVersion);
                        newVersions.add(icdVersion);
                    }
                }

                if (oldIcdCode != null && oldVersions.size() == arrSplit.length && newVersions.size() < 1) {
                    continue;
                }
                icdCode = new ICDCode();
                for (int j = 1; j < row.getLastCellNum(); j++) {
                    switch (j) {
                        case 1:
                            icdCode.setCode(row.getCell(j).getStringCellValue());
                            break;
                        case 2:
                            icdCode.setProblem(row.getCell(j).getStringCellValue());
                            break;
                    }
                }

                icdCode.setStatus(true);
                codeRepository.save(icdCode);

                List<ICDVersion> allVersions = new ArrayList<ICDVersion>();
                allVersions.addAll(oldVersions);
                allVersions.addAll(newVersions);

                List<ICDCodeVersion> codeVersionsList = new ArrayList<>();

                for (int y = 0; y < allVersions.size(); y++) {
                    icdCodeVersion = new ICDCodeVersion();
                    icdCodeVersion.setIcd(icdCode);
                    icdCodeVersion.setVersion(allVersions.get(y));
                    codeVersionRepository.save(icdCodeVersion);
                    codeVersionsList.add(icdCodeVersion);
                }
                icdCode.setVersions(codeVersionsList);
                codeRepository.save(icdCode);
                records.getAndIncrement();
            }
        }
        workBook.close();
        this.deleteFile(file);
        return records.get();
    }



    public List<String> patientFileFields(String fileName) throws IllegalStateException, InvalidFormatException, IOException, ParseException {
        // Creating a Workbook from an Excel file (.xls or .xlsx)
        File file = new File(fileName);
        Workbook workBook = WorkbookFactory.create(file);
        Sheet excelSheet = workBook.getSheetAt(0);
        List<String> fileFields = new ArrayList<>();

        Row row = excelSheet.getRow(0);
        if (row != null && row.getRowNum() == 0){
            if (row.getCell(0) != null && row.getCell(1) != null && row.getCell(2) != null
                    && row.getCell(3) != null && row.getCell(4) != null) {
                for (int i = 0; i < 5; i++){
                    fileFields.add(row.getCell(i).getStringCellValue());
                }
            }
        }
        // Closing the workbook
        workBook.close();
        return fileFields;
    }

    public int importPatientRecords(String fileName) throws IllegalStateException, InvalidFormatException, IOException, ParseException {
        // Creating a Workbook from an Excel file (.xls or .xlsx)
        File file = new File(this.tmpFilePath + fileName);
        AtomicInteger records = new AtomicInteger(0);
        Workbook workBook = WorkbookFactory.create(file);
        Sheet excelSheet = workBook.getSheetAt(0);

        Patient patient = null;
        Doctor doctor = null;
        Country country = null;
        for (Row row : excelSheet) {
            if (row != null && row.getRowNum() > 0 && row.getCell(0) != null) {
                if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null
                        || row.getCell(3) == null || row.getCell(4) == null || row.getCell(5) == null
                        || row.getCell(6) == null || row.getCell(7) == null)
                    continue;
                Patient oldPatient = patientRepository.findDuplicatePatientForBulkImport(row.getCell(2).getStringCellValue() + "",
                        row.getCell(3).getStringCellValue() + "", row.getCell(4).getStringCellValue() + "", row.getCell(5).getDateCellValue());
                if (oldPatient != null)
                    continue;

                doctor = doctorRepository.findOne((long) row.getCell(0).getNumericCellValue());
                if (doctor == null)
                    continue;

                country = countryRepository.findOne((long) row.getCell(7).getNumericCellValue());
                if (country == null) {
                    continue;
                }
                patient = new Patient();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    switch (j) {
                        case 0:
                            patient.setPrimaryDoctor(doctor);
                            break;
                        case 1:
                            patient.setTitle(row.getCell(j).getStringCellValue());
                            break;
                        case 2:
                            patient.setFirstName(row.getCell(j).getStringCellValue());
                            break;
                        case 3:
                            patient.setLastName(row.getCell(j).getStringCellValue());
                            break;
                        case 4:
                            patient.setCellPhone(row.getCell(j).getStringCellValue());
                            break;
                        case 5:
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String date = dateFormat.format(row.getCell(j).getDateCellValue());
                            patient.setDob(dateFormat.parse(date));
                            break;
                        case 6:
                            if (row.getCell(j).getStringCellValue().trim().toUpperCase().equals(GenderTypeEnum.MALE.name())) {
                                patient.setGender(GenderTypeEnum.MALE);
                            } else {
                                patient.setGender(GenderTypeEnum.FEMALE);
                            }
                            break;
                        case 7:
                            patient.setCountry(country.getName());
                            break;
                    }
                }
                patient.setStatus(PatientStatusTypeEnum.ACTIVE);
                patient.setPatientId(hisUtilService.getPrefixId(ModuleEnum.PATIENT));
                patientRepository.save(patient);
                records.incrementAndGet();
            }
        }
        // Closing the workbook
        workBook.close();
        this.deleteFile(file);
        return records.get();
    }

    public int importPatientRecords(File file) throws IllegalStateException, InvalidFormatException, IOException, ParseException {
        // Creating a Workbook from an Excel file (.xls or .xlsx)
        AtomicInteger records = new AtomicInteger(0);
        Workbook workBook = WorkbookFactory.create(file);
        Sheet excelSheet = workBook.getSheetAt(0);

        Patient patient = null;
        for (Row row : excelSheet) {
            if (row != null && row.getRowNum() > 0 && row.getCell(0) != null) {
                if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null
                        || row.getCell(3) == null || row.getCell(4) == null)
                    continue;
                Patient oldPatient = patientRepository.findDuplicatePatientForBulkImport(row.getCell(0).getStringCellValue() + "",
                        row.getCell(1).getStringCellValue() + "", row.getCell(2).getStringCellValue() + "", row.getCell(3).getDateCellValue());
                if (oldPatient != null)
                    continue;

                patient = new Patient();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    switch (j) {
                        case 0:
                            patient.setFirstName(row.getCell(j).getStringCellValue());
                            break;
                        case 1:
                            patient.setLastName(row.getCell(j).getStringCellValue());
                            break;
                        case 2:
                            patient.setCellPhone(row.getCell(j).getStringCellValue());
                            break;
                        case 3:
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String date = dateFormat.format(row.getCell(j).getDateCellValue());
                            patient.setDob(dateFormat.parse(date));
                            break;
                        case 4:
                            if (row.getCell(j).getStringCellValue().trim().toUpperCase().equals(GenderTypeEnum.MALE.name())) {
                                patient.setGender(GenderTypeEnum.MALE);
                            } else {
                                patient.setGender(GenderTypeEnum.FEMALE);
                            }
                            break;
                    }
                }
                patient.setStatus(PatientStatusTypeEnum.ACTIVE);
                patient.setPatientId(hisUtilService.getPrefixId(ModuleEnum.PATIENT));
                patientRepository.save(patient);
                records.incrementAndGet();
            }
        }
        // Closing the workbook
        workBook.close();
        this.deleteFile(file);
        return records.get();
    }

    public List<PatientImportRecord> importPatientRecordsMapFields(List<String> fileMappedList, String fileName) throws IllegalStateException, InvalidFormatException, IOException, ParseException {
        List systemFieldsList = Arrays.asList("First Name~Last Name~Cell Phone~Date of Birth (yyyy-MM-dd)~Gender (Male, Female, Other)".split("~"));
        File file = new File(fileName);
        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workBook = WorkbookFactory.create(file);
        Sheet excelSheet = workBook.getSheetAt(0);
        List<PatientImportRecord> listOfLists = new ArrayList<>();

        if (fileMappedList.size() == systemFieldsList.size()) {
            for (Row row : excelSheet) {
//                List<Object> data = new ArrayList<>();
                PatientImportRecord patientImportRecord = new PatientImportRecord();
                StringBuilder sb = new StringBuilder();
                if (row != null && row.getRowNum() > 0 && row.getCell(0) != null) {
                    if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null
                            || row.getCell(3) == null || row.getCell(4) == null) {
                        patientImportRecord.setStatus(false);
                    } else {
                        patientImportRecord.setStatus(true);
                    }
                    for (int j = 0; j < fileMappedList.size(); j++) {
                    String mappedField = fileMappedList.get(j);
//                    for (String mappedField: fileMappedList) {
                        if ("First Name".equals(mappedField)) {
                            sb = new StringBuilder(row.getCell(0) == null ? "" : row.getCell(0).getStringCellValue());
                        } else if ("Last Name".equals(mappedField)) {
                            sb = new StringBuilder(row.getCell(1) == null ? "" : row.getCell(1).getStringCellValue());
                        } else if ("Cell Phone".equals(mappedField)) {
                            sb = new StringBuilder(row.getCell(2) == null ? "" : row.getCell(2).getStringCellValue());
                        } else if ("Date of Birth (yyyy-MM-dd)".equals(mappedField)) {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            sb = new StringBuilder(row.getCell(3) == null ? "" : dateFormat.format(row.getCell(3).getDateCellValue()));
                        } else if ("Gender (Male, Female, Other)".equals(mappedField)) {
                            if (row.getCell(4) == null) {
                                sb = new StringBuilder(GenderTypeEnum.OTHER.name());
                            } else {
                                if (row.getCell(4).getStringCellValue().trim().equalsIgnoreCase(GenderTypeEnum.MALE.name())) {
                                    sb = new StringBuilder(GenderTypeEnum.MALE.name());
                                } else if (row.getCell(4).getStringCellValue().trim().equalsIgnoreCase(GenderTypeEnum.FEMALE.name())) {
                                    sb = new StringBuilder(GenderTypeEnum.FEMALE.name());
                                } else {
                                    sb = new StringBuilder(GenderTypeEnum.OTHER.name());
                                }
                            }
                        }

                        switch (j) {
                            case 0:
                                patientImportRecord.setFirstName(sb.toString());
                                break;
                            case 1:
                                patientImportRecord.setLastName(sb.toString());
                                break;
                            case 2:
                                patientImportRecord.setCellPhone(sb.toString());
                                break;
                            case 3:
                                patientImportRecord.setDob(sb.toString());
                                break;
                            case 4:
                                patientImportRecord.setGender(sb.toString());
                                break;
                        }
                    }
                    listOfLists.add(patientImportRecord);
                }
            }
        }
//      Closing the workbook
        workBook.close();
        return listOfLists;
    }

    public int importAppointmentRecords(String fileName) throws IllegalStateException, InvalidFormatException, IOException, ParseException {
        File file = new File(this.tmpFilePath + fileName);
        AtomicInteger records = new AtomicInteger(0);
        Workbook workBook = WorkbookFactory.create(file);

        //Read sheet inside the workbook by its Index
        Sheet excelSheet = workBook.getSheetAt(0);
        Appointment appointment = null;

        for (Row row : excelSheet) {
            if (row != null && row.getRowNum() > 0 && row.getCell(0) != null) {
                if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null || row.getCell(3) == null || row.getCell(4) == null || row.getCell(5) == null)
                    continue;
                String time = new SimpleDateFormat("HH:mm:ss").format(row.getCell(4).getDateCellValue());
                Appointment oldAppointment = appointmentRepository.findConflictInAppointment(row.getCell(1).getStringCellValue() + "",
                        row.getCell(2).getStringCellValue() + "", row.getCell(3).getDateCellValue(), time);
                if (oldAppointment != null)
                    continue;
                appointment = new Appointment();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    switch (j) {
                        case 0:
                            Patient patient = patientRepository.getByEMR(row.getCell(j).getStringCellValue() + "");
                            if (patient == null) {
                                j = row.getLastCellNum() + 1;
                            } else {
                                appointment.setPatient(patient);
                            }
                            break;
                        case 1:
                            Doctor doctor = doctorRepository.getByProfileId(row.getCell(j).getStringCellValue() + "");
                            if (doctor == null) {
                                j = row.getLastCellNum() + 1;
                            } else {
                                appointment.setDoctor(doctor);
                            }
                            break;
                        case 2:
                            Branch branch = branchRepository.getByBranchId(row.getCell(j).getStringCellValue() + "");
                            if (branch == null) {
                                j = row.getLastCellNum() + 1;
                            } else {
                                appointment.setBranch(branch);
                            }
                            break;
                        case 3:
                            String date = new SimpleDateFormat("yyyy-MM-dd").format(row.getCell(j).getDateCellValue());
                            appointment.setSchdeulledDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
                            break;
                        case 4:
                            appointment.setStartedOn(new SimpleDateFormat("HH:mm:ss").parse(time));
                            break;
                        case 5:
                            long endTimeInMillis = (long) (new SimpleDateFormat("HH:mm:ss").parse(time).getTime() + (row.getCell(j).getNumericCellValue() * 60000));           // 1 MINUTE = 60000 MiliSeconds
                            appointment.setEndedOn(new Date(endTimeInMillis));
                            appointment.setDuration((int) row.getCell(j).getNumericCellValue());
                            break;
                        case 6:
                            appointment.setType(row.getCell(j).getStringCellValue());
                            break;
                    }
                }
                if (appointment.getPatient() == null || appointment.getDoctor() == null || appointment.getBranch() == null) {
                    continue;
                } else {
                    appointment.setAppointmentId(hisUtilService.getPrefixId(ModuleEnum.APPOINTMENT));
                    appointmentRepository.save(appointment);
                }
                records.getAndIncrement();
            }
        }
        workBook.close();
        this.deleteFile(file);
        return records.get();
    }

    private boolean deleteFile(File file) {
        return (file.exists() && file.delete());
    }

    public int savePatientData(ArrayList<PatientImportRecord> allData, String duplicateRecordOperation) {
        AtomicInteger records = new AtomicInteger(0);
        int skipDupRecOps = duplicateRecordOperation.equalsIgnoreCase("Skip") ? 1 : 0;
        Patient patient = null;
        for (PatientImportRecord patientImportRecord : allData) {
            if (patientImportRecord.getFirstName().trim().equalsIgnoreCase("") || patientImportRecord.getLastName().trim().equalsIgnoreCase("")
                    || patientImportRecord.getCellPhone().trim().equalsIgnoreCase("") || patientImportRecord.getDob().trim().equalsIgnoreCase("")
                    || patientImportRecord.getGender().trim().equalsIgnoreCase(""))
                continue;
            else {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                String date = dateFormat.format(patientImportRecord.getDob().trim());
                Date dob = null;
                try {
                    dob = dateFormat.parse(patientImportRecord.getDob().trim());
                    GenderTypeEnum genderTypeEnum = patientImportRecord.getGender().trim().equalsIgnoreCase(GenderTypeEnum.MALE.name()) ? GenderTypeEnum.MALE :
                            (patientImportRecord.getGender().trim().equalsIgnoreCase(GenderTypeEnum.FEMALE.name()) ? GenderTypeEnum.FEMALE :
                                    GenderTypeEnum.OTHER);
                    patient = patientRepository.findDuplicatePatientForBulkImport(patientImportRecord.getFirstName().trim() + "", patientImportRecord.getLastName().trim()+"",
                            patientImportRecord.getCellPhone().trim() + "", dob);
                    if (patient == null) {
                        patient = new Patient();
                        patient.setFirstName(patientImportRecord.getFirstName().trim());
                        patient.setLastName(patientImportRecord.getLastName().trim());
                        patient.setCellPhone(patientImportRecord.getCellPhone().trim());
                        patient.setDob(dob);
                        patient.setGender(genderTypeEnum);
                        patient.setPatientId(hisUtilService.getPrefixId(ModuleEnum.PATIENT));
                        patient.setStatus(PatientStatusTypeEnum.ACTIVE);
                        patientRepository.save(patient);
                        records.incrementAndGet();
                    } else if (skipDupRecOps == 0) {              //Mean overwrite
                        patient.setFirstName(patientImportRecord.getFirstName().trim());
                        patient.setLastName(patientImportRecord.getLastName().trim());
                        patient.setCellPhone(patientImportRecord.getCellPhone().trim());
                        patient.setDob(dob);
                        patient.setGender(genderTypeEnum);
                        patient.setStatus(PatientStatusTypeEnum.ACTIVE);
                        patientRepository.save(patient);
                        records.incrementAndGet();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return records.get();
    }
}
