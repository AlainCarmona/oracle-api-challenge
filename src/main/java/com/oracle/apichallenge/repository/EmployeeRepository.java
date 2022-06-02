package com.oracle.apichallenge.repository;

import com.oracle.apichallenge.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findEmployeesByRole(String roleId);
}
