package com.bera.yilmaz.VetManagementSystem.Core.Config.ModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;

public interface IModelMapperService {
    ModelMapper forRequest();
    ModelMapper forResponse();
}
