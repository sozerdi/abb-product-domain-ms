package az.abb.customer.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import az.abb.customer.activity.impl.CustomerInfoActivityImpl;
import az.abb.customer.dto.CustomerDetailsDto;
import az.abb.customer.enums.CustomerFilterType;
import az.abb.customer.service.CustomerInfoService;
import io.temporal.testing.TestActivityEnvironment;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CustomerInfoActivityImplTest {

    @Mock
    private CustomerInfoService customerInfoService;

    private TestActivityEnvironment testEnvironment;
    private CustomerInfoActivityImpl activity;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        testEnvironment = TestActivityEnvironment.newInstance();
        activity = new CustomerInfoActivityImpl(customerInfoService);
        testEnvironment.registerActivitiesImplementations(activity);
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

        // Assert
        assertEquals(mockResult, result);
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
        assertEquals(mockDto, result);
    }
}