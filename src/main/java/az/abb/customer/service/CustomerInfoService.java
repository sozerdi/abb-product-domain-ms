package az.abb.customer.service;

import az.abb.customer.dto.CustomerDetailsDto;
import az.abb.customer.enums.CustomerFilterType;
import java.util.List;

public interface CustomerInfoService {
    List<CustomerDetailsDto> getCustomerDetails(CustomerFilterType filterType, String filterValue);

    CustomerDetailsDto getSingleCustomerDetail(CustomerFilterType filterType, String filterValue);
}
