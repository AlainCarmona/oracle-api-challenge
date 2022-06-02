package com.oracle.apichallenge.mapper;

import com.oracle.apichallenge.dto.CreateEmployeeRequestDto;
import com.oracle.apichallenge.entity.Employee;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {
    @Mappings({
        @Mapping(target = "firstName", source =  "requestDto.firstName"),
        @Mapping(target = "lastName", source = "requestDto.lastName"),
        @Mapping(target = "role", source = "requestDto.role"),
        @Mapping(target = "address.street", source = "requestDto.street"),
        @Mapping(target = "address.county", source = "requestDto.county"),
        @Mapping(target = "address.city", source = "requestDto.city"),
        @Mapping(target = "address.state", source = "requestDto.state"),
        @Mapping(target = "address.postalCode", source = "requestDto.postalCode"),
        @Mapping(target = "address.country", source = "requestDto.country"),
        @Mapping(target = "address.phoneDetails.countryCode", source = "requestDto.phoneCountryCode"),
        @Mapping(target = "address.phoneDetails.phone", source = "requestDto.phoneNumber")
    })
    Employee mapFromCreateDto(CreateEmployeeRequestDto requestDto);
}
