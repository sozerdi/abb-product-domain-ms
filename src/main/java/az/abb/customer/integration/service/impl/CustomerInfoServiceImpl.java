package az.abb.customer.integration.service.impl;

import az.abb.customer.dto.CustomerDetailsDto;
import az.abb.customer.enums.CustomerFilterType;
import az.abb.customer.integration.client.FlexCustomerReaderFeignClient;
import az.abb.customer.integration.mapper.CustomerDetailsMapper;
import az.abb.customer.service.CustomerInfoService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerInfoServiceImpl implements CustomerInfoService {

    private final FlexCustomerReaderFeignClient customerReaderFeignClient;
    private final CustomerDetailsMapper customerDetailsMapper;

    @Override
    public List<CustomerDetailsDto> getCustomerDetails(CustomerFilterType filterType, String filterValue) {
        return fetchAndMapCustomerDetails(filterType, filterValue);
    }

    @Override
    public CustomerDetailsDto getSingleCustomerDetail(CustomerFilterType filterType, String filterValue) {
        List<CustomerDetailsDto> list = fetchAndMapCustomerDetails(filterType, filterValue);

        if (list.isEmpty()) {
            log.error("No customer found for filter value: {}", filterValue);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer found for filter value: "
                    + filterValue);
        }

        return list.get(0);
    }

    private List<CustomerDetailsDto> fetchAndMapCustomerDetails(CustomerFilterType filterType, String filterValue) {
        Objects.requireNonNull(filterValue, "Filter value must not be null");
        log.debug("Fetching customer info for filter type {} and filter value {}", filterType, filterValue);

        var responseEntity = customerReaderFeignClient.getCustomerDetails(filterType, filterValue);

        var data = Optional.ofNullable(responseEntity.getBody())
                .map(body -> {
                    log.debug("Received response with {} customer(s)",
                            body.getData() != null ? body.getData().size() : 0);
                    return body.getData();
                })
                .orElseThrow(() -> {
                    log.error("Customer info response body or data is null for filter value: {}", filterValue);
                    return new IllegalStateException("Customer info response body or data is null");
                });

        return data.stream()
                .map(customerDetailsMapper::toGlobal)
                .toList();
    }
}
