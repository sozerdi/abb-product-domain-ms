package az.abb.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import az.abb.customer.dto.CustomerDetailsDto;
import az.abb.customer.enums.CustomerFilterType;
import az.abb.customer.integration.client.FlexCustomerReaderFeignClient;
import az.abb.customer.dto.ResponseDto;
import az.abb.customer.dto.info.CustomerDetailsIntegrationDto;
import az.abb.customer.integration.mapper.CustomerDetailsMapper;
import az.abb.customer.integration.service.impl.CustomerInfoServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

public class CustomerInfoServiceImplTest {

    @Mock
    private FlexCustomerReaderFeignClient customerReaderFeignClient;

    @Mock
    private CustomerDetailsMapper customerDetailsMapper;

    @InjectMocks
    private CustomerInfoServiceImpl customerInfoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCustomerDetails_success() {
        // Arrange
        CustomerFilterType filterType = CustomerFilterType.CIF;
        String filterValue = "123";
        CustomerDetailsIntegrationDto customerDto = new CustomerDetailsIntegrationDto();
        List<CustomerDetailsIntegrationDto> customerList = List.of(customerDto);
        ResponseDto<List<CustomerDetailsIntegrationDto>> responseDto = new ResponseDto<>();
        responseDto.setData(customerList);

        ResponseEntity<ResponseDto<List<CustomerDetailsIntegrationDto>>> responseEntity =
                ResponseEntity.ok(responseDto);
        when(customerReaderFeignClient.getCustomerDetails(filterType, filterValue))
                .thenReturn(responseEntity);
        when(customerDetailsMapper.toGlobal(any(CustomerDetailsIntegrationDto.class)))
                .thenReturn(new CustomerDetailsDto());

        // Act
        List<CustomerDetailsDto> result = customerInfoService.getCustomerDetails(filterType, filterValue);

        // Assert
        verify(customerReaderFeignClient, times(1)).getCustomerDetails(filterType, filterValue);
        verify(customerDetailsMapper, times(1)).toGlobal(any());
    }

    @Test
    void testGetSingleCustomerDetail_success() {
        CustomerFilterType filterType = CustomerFilterType.CIF;
        String filterValue = "123";
        CustomerDetailsIntegrationDto customerDto = new CustomerDetailsIntegrationDto();
        List<CustomerDetailsIntegrationDto> customerList = List.of(customerDto);
        ResponseDto<List<CustomerDetailsIntegrationDto>> responseDto = new ResponseDto<>();
        responseDto.setData(customerList);

        ResponseEntity<ResponseDto<List<CustomerDetailsIntegrationDto>>> responseEntity =
                ResponseEntity.ok(responseDto);
        when(customerReaderFeignClient.getCustomerDetails(filterType, filterValue))
                .thenReturn(responseEntity);
        when(customerDetailsMapper.toGlobal(any(CustomerDetailsIntegrationDto.class)))
                .thenReturn(new CustomerDetailsDto());

        // Act
        CustomerDetailsDto result = customerInfoService.getSingleCustomerDetail(filterType, filterValue);

        // verify

        verify(customerReaderFeignClient).getCustomerDetails(filterType, filterValue);
    }

    @Test
    void testGetSingleCustomerDetail_notFound() {
        // Arrange
        CustomerFilterType filterType = CustomerFilterType.CIF;
        String filterValue = "123";
        List<CustomerDetailsIntegrationDto> customerList = new ArrayList<>();
        ResponseDto<List<CustomerDetailsIntegrationDto>> responseDto = new ResponseDto<>();
        responseDto.setData(customerList);

        ResponseEntity<ResponseDto<List<CustomerDetailsIntegrationDto>>> responseEntity =
                ResponseEntity.ok(responseDto);
        when(customerReaderFeignClient.getCustomerDetails(filterType, filterValue))
                .thenReturn(responseEntity);
        when(customerDetailsMapper.toGlobal(any(CustomerDetailsIntegrationDto.class)))
                .thenReturn(new CustomerDetailsDto());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                customerInfoService.getSingleCustomerDetail(filterType, filterValue));
        assertEquals("404 NOT_FOUND \"No customer found for filter value: 123\"", exception.getMessage());
    }

    @Test
    void testGetCustomerDetails_nullResponseBody() {
        // Arrange

        CustomerFilterType filterType = CustomerFilterType.CIF;
        String filterValue = "123";
        List<CustomerDetailsIntegrationDto> customerList = new ArrayList<>();
        ResponseDto<List<CustomerDetailsIntegrationDto>> responseDto = new ResponseDto<>();
        responseDto.setData(customerList);

        when(customerReaderFeignClient.getCustomerDetails(filterType, filterValue))
                .thenReturn(ResponseEntity.ok(null));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                customerInfoService.getCustomerDetails(filterType, filterValue));
        assertEquals("Customer info response body or data is null", exception.getMessage());
    }

}
