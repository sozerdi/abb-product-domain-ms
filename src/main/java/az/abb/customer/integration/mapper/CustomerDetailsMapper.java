package az.abb.customer.integration.mapper;

import az.abb.customer.dto.CustomerDetailsDto;
import az.abb.customer.integration.dto.info.CustomerDetailsIntegrationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerDetailsMapper {
    CustomerDetailsDto toGlobal(CustomerDetailsIntegrationDto source);
}

