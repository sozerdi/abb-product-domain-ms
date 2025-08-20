package az.abb.customer.integration.client;

import az.abb.customer.enums.CustomerFilterType;
import az.abb.customer.integration.dto.ResponseDto;
import az.abb.customer.integration.dto.info.CustomerDetailsIntegrationDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "flex-customer-reader", url = "${endpoints.flex-customer-reader}")
public interface FlexCustomerReaderFeignClient {

    @GetMapping(value = "/v1/customers/detail")
    ResponseEntity<ResponseDto<List<CustomerDetailsIntegrationDto>>> getCustomerDetails(
            @RequestParam(name = "filter-type") CustomerFilterType filterType,
            @RequestParam(name = "filter-value") String filterValue);
}
