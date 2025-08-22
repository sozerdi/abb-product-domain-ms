package az.abb.customer.mapper;

import az.abb.customer.dto.CustomerDetailsDto;
import az.abb.customer.dto.info.BaseDto;
import az.abb.customer.dto.info.CustomerDetailsIntegrationDto;
import az.abb.customer.dto.info.IdDto;
import az.abb.customer.dto.info.PersonalDto;
import az.abb.customer.integration.mapper.CustomerDetailsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDetailsMapperTest {

    private CustomerDetailsMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(CustomerDetailsMapper.class);
    }

    @Test
    void testToGlobal_MappingCorrectness() {
        // Arrange
        IdDto pin = IdDto.builder()
                .pin("ABC1234")
                .build();

        PersonalDto personal = PersonalDto.builder().build();
        personal.setId(pin);

        BaseDto base = BaseDto.builder().build();
        base.setName("John Doe");

        CustomerDetailsIntegrationDto source = new CustomerDetailsIntegrationDto();
        source.setBase(base);
        source.setPersonal(personal);

        // Act
        CustomerDetailsDto result = mapper.toGlobal(source);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getBase().getName());
        assertNotNull(result.getPersonal().getIdentification());
        assertEquals("ABC1234", result.getPersonal().getIdentification().getPin());
    }

    @Test
    void testToGlobal_NullSource() {
        // Act
        CustomerDetailsDto result = mapper.toGlobal(null);

        // Assert
        assertNull(result);
    }

    @Test
    void testToGlobal_NullNestedFields() {
        // Arrange
        CustomerDetailsIntegrationDto source = new CustomerDetailsIntegrationDto();
        source.setBase(null);
        source.setPersonal(null);

        // Act
        CustomerDetailsDto result = mapper.toGlobal(source);

        // Assert
        assertNotNull(result);
        assertNull(result.getBase());
        assertNull(result.getPersonal());
    }
}