package com.eoffice.app.service.mapper;

import com.eoffice.app.domain.*;
import com.eoffice.app.service.dto.FiletosendDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Filetosend and its DTO FiletosendDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FiletosendMapper {

    FiletosendDTO filetosendToFiletosendDTO(Filetosend filetosend);

    List<FiletosendDTO> filetosendsToFiletosendDTOs(List<Filetosend> filetosends);

    Filetosend filetosendDTOToFiletosend(FiletosendDTO filetosendDTO);

    List<Filetosend> filetosendDTOsToFiletosends(List<FiletosendDTO> filetosendDTOs);
}
