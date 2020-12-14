package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.Currency;
import com.zainimtiaz.nagarro.repository.CurrencyRepository;
import com.zainimtiaz.nagarro.wrapper.CurrencyWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jamal on 10/29/2018.
 */
@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;


    @Transactional
    public String saveCurrency(CurrencyWrapper currencyWrapper) {
        Currency currency = new Currency(currencyWrapper);

        /**
         * at a time one default currency must be available in the system
         * below code is for default currency
         * and below code is not completed , its business logic is remaining
         * ***/
       /* if (currency.isSystemDefault()) {
            this.changeSystemDefaultCurrencyStatus();
        }*/
        this.currencyRepository.save(currency);
        return "";
    }

    public List<CurrencyWrapper> getPaginatedCurrencies(Pageable pageable) {
        return this.currencyRepository.getPaginatedCurrencies(pageable);
    }

    public int countPaginatedCurrencies() {
        return this.currencyRepository.findAll().size();
    }

    public CurrencyWrapper getCurrencyById(long currencyId) {
        return this.currencyRepository.getCurrencyById(currencyId);
    }

    @Transactional
    public String updateCurrency(CurrencyWrapper currencyWrapper) {
        Currency currency = this.currencyRepository.findOne(currencyWrapper.getId());
        if (currency != null) {
            new Currency(currency, currencyWrapper);
            /**
             * 1.0 at a time one default currency must be available in the system
             * 1.1 below code is for default currency
             * 1.2 and below code is not completed , its business logic is remaining
             * ***/
       /* if (currency.isSystemDefault()) {
            this.changeSystemDefaultCurrencyStatus();
        }*/

        } else {
            return "Currency Not Found";//means already deleted by other user
        }
        this.currencyRepository.save(currency);
        return "";
    }

    @Transactional
    public boolean deleteCurrency(long currencyId) {
        Currency currency = this.currencyRepository.findOne(currencyId);
        if (currency != null) {
            this.currencyRepository.delete(currency);
            return true;
        } else {
            return false;//means already deleted by other user
        }
    }

    private void changeSystemDefaultCurrencyStatus(long updateCurrencyId) {
        List<Currency> currencies = null;
        Currency updateCurrency = null;// a currency that is going to update by client
        if (updateCurrencyId <= 0) {
            currencies = this.currencyRepository.getCurrencyBySystemDefaultTrue();
        } else if (updateCurrencyId > 0) {
            updateCurrency = this.currencyRepository.findOne(updateCurrencyId);
        }

        if (currencies != null) {
            for (Currency c : currencies) {
                if (updateCurrency.getIos() == c.getIos()) continue;
                c.setSystemDefault(false);
                this.currencyRepository.save(c);
                this.currencyRepository.flush();
                break;
            }
        }
    }

    public List<CurrencyWrapper> getAllCurrenciesByStatus(boolean status) {
        return this.getAllCurrenciesByStatus(status);
    }

    public boolean getCurrencyByIOS(String ios) {
        return this.currencyRepository.getCurrencyByIos(ios);
    }

    public boolean getCurrencyByIOSAndIdNot(long currencyid, String ios) {
        return this.currencyRepository.getCurrencyByIosAndIdNot(currencyid, ios);
    }


    public boolean hasChild(long currencyId) {
        /// to do as requirement final
        return false;
    }

    public CurrencyWrapper getDefaultCurrencyWrapper() {
        return this.currencyRepository.getCurrencyBySystemDefault();
    }

    private List<Currency> getDefaultCurrency() {
        return this.currencyRepository.getCurrencyBySystemDefaultTrue();
    }
}
