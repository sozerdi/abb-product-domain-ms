package az.abb.customer.integration.mapper;

import az.abb.customer.dto.CustomerDetailsDto;
import az.abb.customer.integration.dto.info.CustomerDetailsIntegrationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerDetailsMapper {
    @Mapping(target = "base.name", source = "base.name")
    @Mapping(target = "personal.identification", source = "personal.id")
    @Mapping(target = "personal.identification.pin", source = "personal.id.pinCode")
    CustomerDetailsDto toGlobal(CustomerDetailsIntegrationDto source);
}

