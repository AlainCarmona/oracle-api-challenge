package com.oracle.apichallenge.service;

import com.github.fge.jsonpatch.JsonPatchOperation;
import com.oracle.apichallenge.dto.ApiChallengeResponseDto;
import com.oracle.apichallenge.dto.CreateEmployeeRequestDto;
import com.oracle.apichallenge.enums.EmployeeRoleEnum;

public interface EmployeeService {
    ApiChallengeResponseDto findByUserId(String userId);
    ApiChallengeResponseDto findAll();
    ApiChallengeResponseDto findByRoleId(EmployeeRoleEnum roleId);
    ApiChallengeResponseDto create(CreateEmployeeRequestDto requestDto);
    ApiChallengeResponseDto update(String userId, CreateEmployeeRequestDto requestDto);
    ApiChallengeResponseDto patch(String userId, JsonPatchOperation jsonPatch);
    ApiChallengeResponseDto delete(String userId);
}
