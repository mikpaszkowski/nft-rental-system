package com.rentalSystem.configuration.model;

import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class BaseMapperConfig {
}
