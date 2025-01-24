package com.atomika.gitByCity.dto.mapper;

import com.atomika.gitByCity.dto.Client;
import com.atomika.gitByCity.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "credential", ignore = true)
    ClientEntity dtoToEntity (Client client);

    @Mapping(target = "credential", ignore = true)
    Client entityToDto(ClientEntity clientEntity);

    List<Client> toList(List<ClientEntity> clientEntityList);
}
