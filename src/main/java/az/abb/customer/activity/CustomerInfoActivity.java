package az.abb.customer.activity;

import az.abb.customer.dto.CustomerDetailsDto;
import az.abb.customer.enums.CustomerFilterType;
import io.temporal.activity.ActivityInterface;
import java.util.List;

@ActivityInterface
public interface CustomerInfoActivity {

    /**
     * Retrieves a list of customers by filter.
     *
     * @param filterType   Filter type (TRANSIT_ACCOUNT_NO, CIF, etc.)
     * @param filterValue  Filter value.
     * @return             List of customers.
     */
    List<CustomerDetailsDto> getCustomerDetails(CustomerFilterType filterType, String filterValue);

    CustomerDetailsDto getSingleCustomerDetail(CustomerFilterType filterType, String filterValue);
}
