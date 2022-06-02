package com.oracle.apichallenge.controller;

import com.github.fge.jsonpatch.JsonPatchOperation;
import com.oracle.apichallenge.dto.ApiChallengeResponseDto;
import com.oracle.apichallenge.dto.CreateEmployeeRequestDto;
import com.oracle.apichallenge.enums.EmployeeRoleEnum;
import com.oracle.apichallenge.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class EmployeeRestController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{userId}")
    public ApiChallengeResponseDto getUserById(@PathVariable(name = "userId") String userId) {
        return employeeService.findByUserId(userId);
    }

    @GetMapping
    public ApiChallengeResponseDto getAllUsers() {
        return employeeService.findAll();
    }

    @GetMapping("/role/{roleId}")
    public ApiChallengeResponseDto getUserByRoleId(@PathVariable(name = "roleId") EmployeeRoleEnum roleId) {
        return employeeService.findByRoleId(roleId);
    }

    @PostMapping
    public ApiChallengeResponseDto createUser(@RequestBody CreateEmployeeRequestDto request) {
        return employeeService.create(request);
    }

    @PutMapping("/{userId}")
    public ApiChallengeResponseDto updateUser(@PathVariable String userId, @RequestBody CreateEmployeeRequestDto request) {
        return employeeService.update(userId, request);
    }

    @PatchMapping(path = "/{userId}", consumes = "application/json-patch+json")
    public ApiChallengeResponseDto patchUserFields(@PathVariable String userId, @RequestBody JsonPatchOperation jsonPatch) {
        return employeeService.patch(userId, jsonPatch);
    }

    @DeleteMapping("/{userId}")
    public ApiChallengeResponseDto deleteUser(@PathVariable String userId) {
        return employeeService.delete(userId);
    }
}
