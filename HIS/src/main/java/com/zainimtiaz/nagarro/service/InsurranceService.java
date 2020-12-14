package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.*;
import com.sd.his.repository.*;
import com.zainimtiaz.nagarro.repository.*;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.sd.his.wrapper.*;
import com.zainimtiaz.nagarro.wrapper.ICDCodeVersionWrapper;
import com.zainimtiaz.nagarro.wrapper.ICDCodeWrapper;
import com.zainimtiaz.nagarro.wrapper.ICDVersionWrapper;
import com.zainimtiaz.nagarro.wrapper.InsuranceProfileWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


import com.sd.his.model.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

@Service
@Transactional
public class InsurranceService {

    @Autowired
    private ICDCodeRepository codeRepository;
    @Autowired
    private ICDVersionRepository versionRepository;
    @Autowired
    private ICDCodeVersionRepository codeVersionRepository;

    @Autowired
    private InsuranceProfileRepository profileRepository;

    @Autowired
    private InsurancePlanRepository planRepository;

    @Autowired
    private InsuranceRepository insuranceRep;
    public List<InsuranceProfileWrapper> getPlans() {
        return this.planRepository.findAllByCreatedOnNotNull();
    }

    public List<ICDVersionWrapper> versiosForDataTable() {
        return this.versionRepository.findAllVersionsForDataTable();
    }

    public List<InsuranceProfileWrapper> getProfiles() {
        List<InsuranceProfile> lstProfile= this.profileRepository.findAllByCreatedOnNotNull();
        List<InsuranceProfileWrapper> lstWrapper=new ArrayList<InsuranceProfileWrapper>();
        if(lstProfile.size()>0){
            for(int i=0;i<lstProfile.size();i++){
                InsuranceProfileWrapper obj=new InsuranceProfileWrapper();
                obj.setId(lstProfile.get(i).getId());
                obj.setName(lstProfile.get(i).getName());
                obj.setDescription(lstProfile.get(i).getDescription());
                obj.setStatus(lstProfile.get(i).isStatus());
                lstWrapper.add(obj);
            }
        }
        return lstWrapper;
    }

    public List<ICDCodeWrapper> codesForDataTable() {
        return this.codeRepository.findAllCodesForDataTable();
    }


    public String saveplan(InsuranceProfileWrapper createRequest) {
        InsurancePlan plan=new InsurancePlan(createRequest);
        planRepository.save(plan);
        return "" ;
    }


    public String saveProfile(InsuranceProfileWrapper createRequest) {
        InsuranceProfile plan=new InsuranceProfile(createRequest);
        profileRepository.save(plan);
        return "" ;
    }

    public boolean isAlreadyExistAgainstId( long id) {
        InsuranceProfile icd = profileRepository.findOne(id);
        if (HISCoreUtil.isValidObject(icd)) {
            return true;
        }
        return false;
    }


    public boolean isPlanAlreadyExistAgainstId(long id) {
        InsurancePlan icd = planRepository.findOne(id);
        if (HISCoreUtil.isValidObject(icd)) {
            return true;
        }
        return false;
    }





    public boolean isNameAlreadyExist(String name) {
        InsurancePlan icd = planRepository.findByName(name);
        if (HISCoreUtil.isValidObject(icd)) {
            return true;
        }
        return false;
    }


    public boolean isProfileNameAlreadyExist(String name) {
        InsuranceProfile icd = profileRepository.findByName(name);
        if (HISCoreUtil.isValidObject(icd)) {
            return true;
        }
        return false;
    }



    public List<ICDVersionWrapper> findVersions(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        List<ICDVersionWrapper> list = versionRepository.findAllByCreatedOnNotNull(pageable);
        if (list != null) {
            for (ICDVersionWrapper versionWrapper : list) {
                if (this.codeVersionRepository.isVersionAssociated(versionWrapper.getId())) {
                    versionWrapper.setHasChild(true);
                }
            }
        }
        return list;
    }

    public int countVersion() {
        return versionRepository.findAllByCreatedOnNotNull().size();
    }

    public List<ICDCodeWrapper> searchCodes(String code, int offset, int limit) {
        Pageable page = new PageRequest(offset, limit);
        List<ICDCodeWrapper> list = codeRepository.findAllByCodeContaining(code, page);
        if (list != null) {
            for (ICDCodeWrapper codeWrapper : list) {
                if (this.codeVersionRepository.isCodeAssociated(codeWrapper.getId())) {
                    codeWrapper.setHasChild(true);
                }
            }
        }
        return list;
    }

    public int countSearchCodes(String code) {
        return codeRepository.findAllByCodeContaining(code).size();
    }

    public List<ICDCodeVersionWrapper> searchCodeVersionByCodeAndVersionName(Pageable pageable, String versionName, String code) {
        return this.codeVersionRepository.findAllCodeVersionByVersion_NameAndIcd_Code(versionName, code, pageable);
    }

    public int countSearchCodeVersionByCodeAndVersionName(String versionName, String code) {
        return this.codeVersionRepository.countFindAllCodeVersionByVersion_NameAndIcd_Code(versionName, code).size();
    }

    public List<ICDCodeVersionWrapper> searchCodeVersionByVersionName(Pageable pageable, String versionName) {
        return this.codeVersionRepository.findAllCodeVersionByVersion_Name(versionName, pageable);
    }

    public int countSearchCodeVersionByVersionName(String versionName) {
        return codeVersionRepository.countFindAllCodeVersionByVersion_Name(versionName).size();
    }

    public List<ICDCodeVersionWrapper> searchCodeVersionByCode(Pageable pageable, String code) {
        return this.codeVersionRepository.findAllCodeVersionByIcd_Code(code, pageable);
    }

    public int countSearchCodeVersionByCode(String code) {
        return codeVersionRepository.countFindAllCodeVersionByIcd_Code(code).size();
    }

    public List<ICDVersionWrapper> searchByVersion(String name, int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);

        List<ICDVersionWrapper> list = versionRepository.findAllByNameContaining(name, pageable);
        if (list != null) {
            for (ICDVersionWrapper versionWrapper : list) {
                if (this.codeVersionRepository.isCodeAssociated(versionWrapper.getId())) {
                    versionWrapper.setHasChild(true);
                }
            }
        }
        return list;
    }

    public int countSearchByVersion(String name) {
        return versionRepository.findAllByNameContaining(name).size();
    }

    @Transactional(rollbackOn = Throwable.class)
    public boolean deletedPlan(Long icdId) {
        InsurancePlan icd = planRepository.findOne(icdId);
        if (HISCoreUtil.isValidObject(icd)) {
            this.planRepository.delete(icd);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(rollbackOn = Throwable.class)
    public boolean deletedProfile(long icdId) {
        InsuranceProfile icdVersion = profileRepository.findOne(icdId);
        if (HISCoreUtil.isValidObject(icdVersion)) {
            profileRepository.delete(icdVersion);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(rollbackOn = Throwable.class)
    public boolean deletedAssociateICDCV(long icdId) {
        if (icdId > 0) {
            ICDCodeVersion codeVersion = this.codeVersionRepository.findOne(icdId);
            if (codeVersion == null) {
                return false;
            }
            codeVersionRepository.delete(codeVersion);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(rollbackOn = Throwable.class)
    public String updatePlan(InsuranceProfileWrapper createRequest) {
        InsurancePlan icdCode = this.planRepository.findOne(createRequest.getId());
        if (HISCoreUtil.isValidObject(icdCode)) {
            icdCode.setName(createRequest.getName());
            icdCode.setDescription(createRequest.getDescription());
            icdCode.setStatus(createRequest.isStatus());


        }
        this.planRepository.save(icdCode);
   //     this.associateICDCODEBySelectedVersion(icdCode, createRequest);
        return "";
    }


    @Transactional(rollbackOn = Throwable.class)
    public String updateProfile(InsuranceProfileWrapper createRequest) {
        InsuranceProfile icdCode = this.profileRepository.findOne(createRequest.getId());
        if (HISCoreUtil.isValidObject(icdCode)) {
            icdCode.setName(createRequest.getName());
            icdCode.setDescription(createRequest.getDescription());
            icdCode.setStatus(createRequest.isStatus());
        }
        this.profileRepository.save(icdCode);
        return "";
    }








    @Transactional(rollbackOn = Throwable.class)
    public ICDVersion saveICDVersion(ICDVersionWrapper createRequest) {
        ICDVersion icdVersion = new ICDVersion(createRequest);
        return versionRepository.save(icdVersion);
    }

    public boolean isICDVersionNameAlreadyExistAgainstICDVersionNameId(String name, long id) {
        ICDVersion icdVersion = versionRepository.findByNameAndIdNot(name, id);
        if (HISCoreUtil.isValidObject(icdVersion)) {
            return true;
        }
        return false;
    }

    @Transactional(rollbackOn = Throwable.class)
    public ICDVersion updateICDVersion(ICDVersionWrapper request) {
        ICDVersion icdVersion = versionRepository.findOne(request.getId());
        if (HISCoreUtil.isValidObject(icdVersion)) {
            icdVersion.setName(request.getName());
            icdVersion.setDescription(request.getDescription());
            icdVersion.setStatus(request.isStatus());
        }
        return icdVersion;
    }

    public List<ICDCodeVersionWrapper> codeVersions(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
//        List<ICDCodeVersionWrapper> cvs = new ArrayList<>();
    /*    List<ICDCodeVersion> codeVersions = this.codeVersionRepository.findAllByOrderByVersion_name(pageable);
        if (codeVersions != null) {
            cvs = APIUtil.buildICDCodeVersionWrapper(codeVersions);
        }*/
        return this.codeVersionRepository.findAllByOrderByVersion_name(pageable);
    }

    public int countCodeVersions() {
        return codeVersionRepository.findAllByOrderByVersion_name().size();
    }

    @Transactional(rollbackOn = Throwable.class)
    public List<ICDCodeVersion> saveAssociateICDCVs(ICDCodeVersionWrapper createRequest) {
        ICDCodeVersion associateICDCVs = null;
        List<ICDCodeVersion> codeVersions = new ArrayList<>();

        ICDVersion icdVersion = this.versionRepository.findOne(Long.parseLong(createRequest.getSelectedICDVersionId()));

        this.codeVersionRepository.deleteAllByVersion_id(icdVersion.getId());
        this.codeVersionRepository.flush();

        for (ICDCodeWrapper codeWrapper : createRequest.getiCDCodes()) {
            if (codeWrapper.isCheckedCode()) {
                associateICDCVs = new ICDCodeVersion();
                associateICDCVs.setVersion(icdVersion);
                associateICDCVs.setDescription(createRequest.getDescription());
                associateICDCVs.setIcd(this.codeRepository.findOne(codeWrapper.getId()));
                codeVersions.add(associateICDCVs);
            }
        }

        return codeVersionRepository.save(codeVersions);
    }

    @Transactional(rollbackOn = Throwable.class)
    public List<ICDCodeVersion> updateCVs(ICDCodeVersionWrapper createRequest) {
        ICDCodeVersion associateICDCVs = null;
        List<ICDCodeVersion> icdCodeVersions = new ArrayList<>();

        ICDVersion icdVersion = this.versionRepository.findOne(Long.parseLong(createRequest.getSelectedICDVersionId()));

        this.codeVersionRepository.deleteAllByVersion_id(icdVersion.getId());
        this.codeVersionRepository.flush();

        for (ICDCodeWrapper codeWrapper : createRequest.getSelectedICDCodes()) {
            associateICDCVs = new ICDCodeVersion();
            associateICDCVs.setVersion(icdVersion);
            associateICDCVs.setIcd(this.codeRepository.findOne(codeWrapper.getId()));

            icdCodeVersions.add(associateICDCVs);
        }

        return codeVersionRepository.save(icdCodeVersions);
    }

    public List<ICDCodeWrapper> getAssociatedICDCVByVId(long versionId) {
        if (versionId > 0) {
            return codeVersionRepository.findAllByVersion_id(versionId);
        }
        return null;
    }

    public List<ICDVersionWrapper> getAssociatedICDVByCId(long codeId) {
        return codeVersionRepository.findAllVersionsByCode_id(codeId);
    }

    public boolean isCodeAssociated(long codeId) {
        InsurancePlan icdCode = this.planRepository.findOne(codeId);
        boolean returnStatus;
        if (icdCode != null) {
            {
                return returnStatus=this.insuranceRep.isCodeAssociated(codeId);

            }

         }


        return false;
    }


    public boolean isCodeAssociatedProfile(long codeId) {
        InsuranceProfile icdCode = this.profileRepository.findOne(codeId);
        boolean returnStatus;
        if (icdCode != null) {
            {
                return returnStatus=this.insuranceRep.isCodeAssociatedProfile(codeId);

            }

        }


        return false;
    }


    public boolean isVersionAssociated(long versionId) {
        return this.codeVersionRepository.isVersionAssociated(versionId);
    }

    public ICDCodeWrapper getCodeById(long codeId) {
        ICDCode icdCode = this.codeRepository.findOne(codeId);
        if (icdCode != null) {
            ICDCodeWrapper codeWrapper = new ICDCodeWrapper(icdCode);
            codeWrapper.setSelectedVersions(new ArrayList<>());
            for (ICDCodeVersion codeVersion : icdCode.getVersions()) {
                codeWrapper.getSelectedVersions().add(new ICDVersionWrapper(codeVersion.getVersion()));
            }
            return codeWrapper;
        }
        return null;
    }


}
