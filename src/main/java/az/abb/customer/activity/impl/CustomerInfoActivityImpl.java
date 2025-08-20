package az.abb.customer.activity.impl;

import az.abb.customer.activity.CustomerInfoActivity;
import az.abb.customer.dto.CustomerDetailsDto;
import az.abb.customer.enums.CustomerFilterType;
import az.abb.customer.service.CustomerInfoService;
import io.temporal.spring.boot.ActivityImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(workers = "customer-info-worker")
@RequiredArgsConstructor
public class CustomerInfoActivityImpl implements CustomerInfoActivity {
    private final CustomerInfoService customerInfoService;


    @Override
    public List<CustomerDetailsDto> getCustomerDetails(CustomerFilterType filterType, String filterValue) {
        return customerInfoService.getCustomerDetails(filterType, filterValue);
    }

    @Override
    public CustomerDetailsDto getSingleCustomerDetail(CustomerFilterType filterType, String filterValue) {
        return customerInfoService.getSingleCustomerDetail(filterType, filterValue);
    }
}
