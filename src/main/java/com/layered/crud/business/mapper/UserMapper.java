package com.layered.crud.business.mapper;

import com.layered.crud.business.dto.UserCreateRequest;
import com.layered.crud.business.dto.UserUpdateRequest;
import com.layered.crud.business.dto.UserResponse;
import com.layered.crud.persistence.entity.UserEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserResponse entityToResponse(UserEntity entity);

    UserEntity createRequestToEntity(UserCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(UserUpdateRequest request, @MappingTarget UserEntity entity);
}
