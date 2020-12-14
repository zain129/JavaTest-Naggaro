package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.enums.BalanceTypeEnum;
import com.zainimtiaz.nagarro.enums.ModuleEnum;
import com.zainimtiaz.nagarro.model.AccountConfig;
import com.zainimtiaz.nagarro.model.GeneralLedger;
import com.zainimtiaz.nagarro.model.Organization;
import com.zainimtiaz.nagarro.repository.AccountConfigRepository;
import com.zainimtiaz.nagarro.repository.GeneralLedgerRepository;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.GeneralLedgerWrapper;
import com.zainimtiaz.nagarro.wrapper.request.AccountConfigRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneralLedgerService {

    @Autowired
    GeneralLedgerRepository generalLedgerRepository;
    @Autowired
    AccountConfigRepository accountConfigRepository;
    @Autowired
    private HISUtilService hisUtilService;
    @Autowired
    private OrganizationService organizationService;

    public GeneralLedger getById(long id){
        return generalLedgerRepository.findOne(id);
    }

    public List<GeneralLedgerWrapper> getAll(){
        List<GeneralLedger> list =  generalLedgerRepository.findAll();//generalLedgerRepository.getAll();
        List<GeneralLedgerWrapper> dataList = new ArrayList<>();
        double drBalance = 0;
        double crBalance = 0;
        for (GeneralLedger gl : list) {
            drBalance = gl.getGeneralLedgerTransactions().stream().filter(x -> x.getTransactionType().equals(BalanceTypeEnum.DR.name())).mapToDouble(x -> x.getAmount()).sum();
            crBalance = gl.getGeneralLedgerTransactions().stream().filter(x -> x.getTransactionType().equals(BalanceTypeEnum.CR.name())).mapToDouble(x -> x.getAmount()).sum();
            Organization dbOrganization = organizationService.getAllOrgizationData();
            String systemCurrency = dbOrganization.getCurrencyFormat();
            if (gl.getBalanceType().equals(BalanceTypeEnum.DR.name())) {
                dataList.add(new GeneralLedgerWrapper(gl, HISCoreUtil.formatCurrencyDisplay(drBalance - crBalance, systemCurrency)));
            } else {
                dataList.add(new GeneralLedgerWrapper(gl, HISCoreUtil.formatCurrencyDisplay(crBalance - drBalance, systemCurrency)));
            }
        }

        return dataList;
    }

    public void saveConfiguration(GeneralLedger generalLedger){

        switch (generalLedger.getParentType()) {
            case "Assets":
            case "Cost of Goods Sold":
            case "Expense":
                generalLedger.setBalanceType(BalanceTypeEnum.DR.name());
                break;
            case "Revenue":
            case "Liabilities":
                generalLedger.setBalanceType(BalanceTypeEnum.CR.name());
                break;
        }
        generalLedgerRepository.save(generalLedger);

    }


    public AccountConfig getAccountConfig(){
//        List<AccountConfig> result = accountConfigRepository.findAll();
//        return result.size() > 0 ? result.get(0) : null;
        return accountConfigRepository.findOne(1L);
    }

    public void saveAssetsConfiguration(AccountConfigRequestWrapper accountConfigRequest){
        AccountConfig accountConfig=accountConfigRepository.findAll().size()>0 ? accountConfigRepository.findAll().get(0) : null;

        if(accountConfig ==null){
            accountConfig = new AccountConfig();
        }
        accountConfig.setCash(generalLedgerRepository.findOne(accountConfigRequest.getCash()));
        accountConfig.setBank(generalLedgerRepository.findOne(accountConfigRequest.getBank()));
        accountConfig.setInventory(generalLedgerRepository.findOne(accountConfigRequest.getInventory()));
        accountConfig.setAccountReceivable(generalLedgerRepository.findOne(accountConfigRequest.getAccountReceivable()));
        accountConfig.setPalntEquipment(generalLedgerRepository.findOne(accountConfigRequest.getPalntEquipment()));
        accountConfig.setFurnitureFixture(generalLedgerRepository.findOne(accountConfigRequest.getFurnitureFixture()));
        accountConfigRepository.save(accountConfig);
    }

    public void saveLiabilityConfiguration(AccountConfigRequestWrapper accountConfigRequest){
        AccountConfig accountConfig=accountConfigRepository.findAll().size()>0 ? accountConfigRepository.findAll().get(0) : null;

        if(accountConfig ==null){
            accountConfig = new AccountConfig();
        }
        accountConfig.setTaxPayable(generalLedgerRepository.findOne(accountConfigRequest.getTaxPayable()));
        accountConfig.setAccountPayable(generalLedgerRepository.findOne(accountConfigRequest.getAccountPayable()));

        accountConfig.setAccuredSalary(generalLedgerRepository.findOne(accountConfigRequest.getAccuredSalary()));
        accountConfig.setLoan(generalLedgerRepository.findOne(accountConfigRequest.getLoan()));
        accountConfig.setOtherPayable(generalLedgerRepository.findOne(accountConfigRequest.getOtherPayable()));
        accountConfigRepository.save(accountConfig);
    }

    public void saveRevenueConfiguration(AccountConfigRequestWrapper accountConfigRequest){
        AccountConfig accountConfig=accountConfigRepository.findAll().size()>0 ? accountConfigRepository.findAll().get(0) : null;

        if(accountConfig ==null){
            accountConfig = new AccountConfig();
        }
        accountConfig.setIncome(generalLedgerRepository.findOne(accountConfigRequest.getIncome()));
        accountConfig.setOtherIncome(generalLedgerRepository.findOne(accountConfigRequest.getOtherIncome()));
        accountConfigRepository.save(accountConfig);
    }

    public void saveCOGSConfiguration(AccountConfigRequestWrapper accountConfigRequest){
        AccountConfig accountConfig=accountConfigRepository.findAll().size()>0 ? accountConfigRepository.findAll().get(0) : null;

        if(accountConfig ==null){
            accountConfig = new AccountConfig();
        }
        accountConfig.setCostOfSale(generalLedgerRepository.findOne(accountConfigRequest.getCostOfSale()));
        accountConfigRepository.save(accountConfig);
    }

    public void saveExpenseConfiguration(AccountConfigRequestWrapper accountConfigRequest){
        AccountConfig accountConfig=accountConfigRepository.findAll().size()>0 ? accountConfigRepository.findAll().get(0) : null;

        if(accountConfig ==null){
            accountConfig = new AccountConfig();
        }
        accountConfig.setGeneralExpense(generalLedgerRepository.findOne(accountConfigRequest.getGeneralExpense()));
        accountConfig.setDoctorExpense(generalLedgerRepository.findOne(accountConfigRequest.getDoctorExpense()));
        accountConfigRepository.save(accountConfig);
    }

    public String getAccountCode(){
        return this.hisUtilService.generatePrefix(ModuleEnum.GENERAL_LEDGER);
    }

    public void updateAccountCode(){
        this.hisUtilService.updatePrefix(ModuleEnum.GENERAL_LEDGER);
    }

    public void deleteLedger(Long generalLedgerId) {
        this.generalLedgerRepository.delete(generalLedgerId);
    }

    public void deleteLedger(GeneralLedger generalLedger) {
        this.generalLedgerRepository.delete(generalLedger);
    }
}
