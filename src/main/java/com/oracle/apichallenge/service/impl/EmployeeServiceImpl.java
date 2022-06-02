package com.oracle.apichallenge.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.oracle.apichallenge.dto.ApiChallengeResponseDto;
import com.oracle.apichallenge.dto.CreateEmployeeRequestDto;
import com.oracle.apichallenge.entity.Address;
import com.oracle.apichallenge.entity.Employee;
import com.oracle.apichallenge.entity.PhoneDetails;
import com.oracle.apichallenge.enums.EmployeeRoleEnum;
import com.oracle.apichallenge.mapper.EmployeeMapper;
import com.oracle.apichallenge.repository.EmployeeRepository;
import com.oracle.apichallenge.service.EmployeeService;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ApiChallengeResponseDto findByUserId(String userId) {
        try {
            Optional<Employee> employee = employeeRepository.findById(userId);

            if (employee.isEmpty()) {
                return new ApiChallengeResponseDto(HttpStatus.OK.value(), "User Not found", null);
            } else {
                return new ApiChallengeResponseDto(HttpStatus.OK.value(), "OK", employee.get());
            }
        } catch(Exception ex) {
            StringBuilder errorMessage = new StringBuilder().append("Error finding user with ID ").append(userId);
            log.error(errorMessage.toString(), ex.getMessage());
            return new ApiChallengeResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage.toString(), null);
        }
    }

    @Override
    public ApiChallengeResponseDto findAll() {
        try {
            List<Employee> employees = employeeRepository.findAll();

            return new ApiChallengeResponseDto(HttpStatus.OK.value(), "OK", employees);
        } catch(Exception ex) {
            StringBuilder errorMessage = new StringBuilder().append("Error finding all users. ");
            log.error(errorMessage + ex.getMessage());
            return new ApiChallengeResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage.toString(), null);
        }
    }

    @Override
    public ApiChallengeResponseDto findByRoleId(EmployeeRoleEnum roleId) {
        try {
            if (!Arrays.asList(EmployeeRoleEnum.values()).contains(roleId)) {
                return new ApiChallengeResponseDto(HttpStatus.NOT_ACCEPTABLE.value(), "Invalid Role ID", null);
            }

            List<Employee> employees = employeeRepository.findEmployeesByRole(roleId.toString());

            return new ApiChallengeResponseDto(HttpStatus.OK.value(), "OK", employees);
        } catch(Exception ex) {
            StringBuilder errorMessage =
                    new StringBuilder()
                            .append("Error finding users by role")
                            .append(roleId);
            log.error(errorMessage + ex.getMessage());
            return new ApiChallengeResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage.toString(), null);
        }
    }

    @Override
    public ApiChallengeResponseDto create(CreateEmployeeRequestDto requestDto) {
        try {
            if (!Arrays.asList(EmployeeRoleEnum.values()).contains(requestDto.getRole())) {
                return new ApiChallengeResponseDto(HttpStatus.NOT_ACCEPTABLE.value(), "Invalid Role ID", null);
            }

            PhoneDetails phoneDetails = new PhoneDetails();
            phoneDetails.setCountryCode(requestDto.getPhoneCountryCode());
            phoneDetails.setPhone(requestDto.getPhoneNumber());

            Address address = new Address();
            address.setStreet(requestDto.getStreet());
            address.setCounty(requestDto.getCounty());
            address.setCity(requestDto.getCity());
            address.setState(requestDto.getState());
            address.setPostalCode(requestDto.getPostalCode());
            address.setCountry(requestDto.getCountry());
            address.setPhoneDetails(phoneDetails);

            Employee newEmployee = new Employee();
            newEmployee.setFirstName(requestDto.getFirstName());
            newEmployee.setLastName(requestDto.getLastName());
            newEmployee.setRole(requestDto.getRole());
            newEmployee.setAddress(address);

            employeeRepository.save(newEmployee);

            return new ApiChallengeResponseDto(HttpStatus.CREATED.value(), "OK", newEmployee);
        } catch(Exception ex) {
            StringBuilder errorMessage =
                    new StringBuilder()
                            .append("Error creating user ")
                            .append(requestDto.getFirstName());
            log.error(errorMessage + ex.getMessage());
            return new ApiChallengeResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage.toString(), null);
        }
    }

    @Override
    public ApiChallengeResponseDto update(String userId, CreateEmployeeRequestDto requestDto) {
        try {
            Employee employee = employeeRepository.findById(userId).orElseThrow(ChangeSetPersister.NotFoundException::new);
            Employee updatedEmployee = employeeMapper.mapFromCreateDto(requestDto);
            updatedEmployee.setUserId(userId);

            employeeRepository.save(updatedEmployee);

            return new ApiChallengeResponseDto(HttpStatus.CREATED.value(), "OK", updatedEmployee);
        } catch(ChangeSetPersister.NotFoundException ex) {
            StringBuilder errorMessage =
                    new StringBuilder()
                            .append("User not found ")
                            .append(userId);
            log.error(errorMessage + ex.getMessage());
            return new ApiChallengeResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage.toString(), null);
        }
    }

    @Override
    public ApiChallengeResponseDto patch(String userId, JsonPatchOperation jsonPatch) {
        try {
            Employee employee = employeeRepository.findById(userId).orElseThrow(ChangeSetPersister.NotFoundException::new);
            Employee patchedEmployee = applyPatchToEmployee(jsonPatch, employee);

            employeeRepository.save(patchedEmployee);

            return new ApiChallengeResponseDto(HttpStatus.OK.value(), "OK", patchedEmployee);
        } catch(JsonPatchException | JsonProcessingException ex) {
            StringBuilder errorMessage =
                    new StringBuilder()
                            .append("Error patching user ")
                            .append(userId);
            log.error(errorMessage + ex.getMessage());
            return new ApiChallengeResponseDto(HttpStatus.NOT_ACCEPTABLE.value(), errorMessage.toString(), null);
        } catch(ChangeSetPersister.NotFoundException ex) {
            StringBuilder errorMessage =
                    new StringBuilder()
                            .append("User not found ")
                            .append(userId);
            log.error(errorMessage + ex.getMessage());
            return new ApiChallengeResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage.toString(), null);
        }
    }

    @Override
    public ApiChallengeResponseDto delete(String userId) {
        try {
            Employee employee = employeeRepository.findById(userId).orElseThrow(ChangeSetPersister.NotFoundException::new);

            employeeRepository.delete(employee);

            return new ApiChallengeResponseDto(HttpStatus.OK.value(), "User deleted Successfully", null);
        } catch(ChangeSetPersister.NotFoundException ex) {
            StringBuilder errorMessage =
                    new StringBuilder()
                            .append("User not found ")
                            .append(userId);
            log.error(errorMessage + ex.getMessage());
            return new ApiChallengeResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage.toString(), null);
        }
    }

    private Employee applyPatchToEmployee(JsonPatchOperation patch, Employee target)
            throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(target, JsonNode.class));

        return objectMapper.treeToValue(patched, Employee.class);
    }
}
