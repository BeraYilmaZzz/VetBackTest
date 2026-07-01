package com.bera.yilmaz.VetManagementSystem.Api;

import com.bera.yilmaz.VetManagementSystem.Business.Abs.ICustomerService;
import com.bera.yilmaz.VetManagementSystem.Core.Config.ModelMapper.IModelMapperService;
import com.bera.yilmaz.VetManagementSystem.Core.Result.ResultData;
import com.bera.yilmaz.VetManagementSystem.Core.Utilies.ResultHelper;
import com.bera.yilmaz.VetManagementSystem.Dto.Request.Customer.CustomerSaveRequest;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.CustomerResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Customer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {
    private final ICustomerService customerService;
    private final IModelMapperService modelMapper;

    public CustomerController(ICustomerService customerService, IModelMapperService modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<Customer> save(@Valid @RequestBody CustomerSaveRequest customerSaveRequest) {
        String customerMail = customerSaveRequest.getMail();
        // E-posta adresinin daha önce kullanılıp kullanılmadığını kontrol et
        Customer saveCustomer = null;
        if (customerService.existsByMail(customerMail)) {
            return ResultHelper.failWithData(null);
        }
        saveCustomer = this.modelMapper.forRequest().map(customerSaveRequest, Customer.class);
        this.customerService.save(saveCustomer);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveCustomer, Customer.class));
    }
    @GetMapping("/{name}") // Hayvan sahipleri isme göre filtrelenecek şekilde end point oluşturmak.
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> get(@PathVariable("name") String name){
        Customer customer = this.customerService.get(name);
        return ResultHelper.success(this.modelMapper.forResponse().map(customer,CustomerResponse.class));
    }
   @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> update(@PathVariable("id") long id, @RequestBody @Valid CustomerSaveRequest customerSaveRequest) {
        Customer updatedCustomer = this.customerService.update(id, customerSaveRequest);
        CustomerResponse customerResponse = null;
        if (updatedCustomer != null) {
            customerResponse = this.modelMapper.forResponse().map(updatedCustomer, CustomerResponse.class);
            return ResultHelper.success(customerResponse);
        } else {
            return ResultHelper.notFound(customerResponse);
        }
    }


    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> delete(@PathVariable("id") long id) {
        Customer deleteCustomer = this.customerService.deleteById(id);
        CustomerResponse customerResponse = null;
        if (deleteCustomer != null) {
            customerResponse = this.modelMapper.forResponse().map(deleteCustomer, CustomerResponse.class);
            return ResultHelper.success(customerResponse);
        } else {
            return ResultHelper.notFound(customerResponse);
        }
    }
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<CustomerResponse>> findAll() {
        List<Customer> custoemrs = customerService.findAll();
        List<CustomerResponse> customerResponses = custoemrs.stream()
                .map(customer -> modelMapper.forResponse().map(customer, CustomerResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(customerResponses);
    }
}
