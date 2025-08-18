package az.abb.customer.activity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import az.abb.customer.activity.impl.CustomerInfoActivityImpl;
import az.abb.customer.dto.CustomerDetailsDto;
import az.abb.customer.enums.CustomerFilterType;
import az.abb.customer.service.CustomerInfoService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CustomerInfoActivityImplTest {

    @Mock
    private CustomerInfoService customerInfoService;

    @InjectMocks
    private CustomerInfoActivityImpl activity;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCustomerDetails() {
        // Arrange
        CustomerFilterType filterType = CustomerFilterType.CIF;
        String filterValue = "123";
        List<CustomerDetailsDto> mockResult = List.of(new CustomerDetailsDto());
        when(customerInfoService.getCustomerDetails(filterType, filterValue)).thenReturn(mockResult);

        // Act
        List<CustomerDetailsDto> result = activity.getCustomerDetails(filterType, filterValue);


        verify(customerInfoService, times(1)).getCustomerDetails(filterType, filterValue);
    }

    @Test
    void testGetSingleCustomerDetail() {
        // Arrange
        CustomerFilterType filterType = CustomerFilterType.CIF;
        String filterValue = "123";
        CustomerDetailsDto mockDto = new CustomerDetailsDto();
        when(customerInfoService.getSingleCustomerDetail(filterType, filterValue)).thenReturn(mockDto);

        // Act
        CustomerDetailsDto result = activity.getSingleCustomerDetail(filterType, filterValue);

        // Assert
        verify(customerInfoService, times(1)).getSingleCustomerDetail(filterType, filterValue);
    }

}
