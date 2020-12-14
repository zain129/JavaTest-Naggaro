package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.CustomerEntity;
import com.zainimtiaz.nagarro.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;




    // Fetch All Record
    public List<CustomerEntity> getAllCustomer() {
        List<CustomerEntity> customer = customerRepository.findAll();
        return customer;
    }

    //Delete Record for Customer
    @Transactional(rollbackOn = Throwable.class)
    public boolean deleteCustomer(long id) {

        customerRepository.delete(id);
        return true;
    }

    //Save Record For Customer
    @Transactional(rollbackOn = Throwable.class)
    public CustomerEntity saveCustomer(CustomerEntity createCustomer) {
       return customerRepository.save(createCustomer);
    }

    // Update Record////////////////////////////
    @Transactional(rollbackOn = Throwable.class)
    public CustomerEntity updateCustomer(CustomerEntity updateCustomerRequest) {
        CustomerEntity customerEntity = customerRepository.findOne(Long.valueOf(updateCustomerRequest.getCustomerId()));
        customerEntity.setFirstName(updateCustomerRequest.getFirstName());
        customerEntity.setLastName(updateCustomerRequest.getLastName());
        customerEntity.setPassword(updateCustomerRequest.getPassword());
        customerEntity.setCustomer_language(updateCustomerRequest.getCustomer_language());
        return customerRepository.save(customerEntity);
    }

    public boolean isNameAlreadyExists(String fName,String lName) {
        CustomerEntity customerEntity = this.customerRepository.findAllByFirstNameAndLastName(fName,lName);
        return customerEntity != null;
    }


}
