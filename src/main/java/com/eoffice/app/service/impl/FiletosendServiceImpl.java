package com.eoffice.app.service.impl;

import com.eoffice.app.service.FiletosendService;
import com.eoffice.app.domain.Filetosend;
import com.eoffice.app.repository.FiletosendRepository;
import com.eoffice.app.service.dto.FiletosendDTO;
import com.eoffice.app.service.mapper.FiletosendMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Filetosend.
 */
@Service
public class FiletosendServiceImpl implements FiletosendService{

    private final Logger log = LoggerFactory.getLogger(FiletosendServiceImpl.class);
    
    private final FiletosendRepository filetosendRepository;

    private final FiletosendMapper filetosendMapper;

    public FiletosendServiceImpl(FiletosendRepository filetosendRepository, FiletosendMapper filetosendMapper) {
        this.filetosendRepository = filetosendRepository;
        this.filetosendMapper = filetosendMapper;
    }

    /**
     * Save a filetosend.
     *
     * @param filetosendDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FiletosendDTO save(FiletosendDTO filetosendDTO) {
        log.debug("Request to save Filetosend : {}", filetosendDTO);
        Filetosend filetosend = filetosendMapper.filetosendDTOToFiletosend(filetosendDTO);
        filetosend = filetosendRepository.save(filetosend);
        FiletosendDTO result = filetosendMapper.filetosendToFiletosendDTO(filetosend);
        return result;
    }

    /**
     *  Get all the filetosends.
     *  
     *  @return the list of entities
     */
    @Override
    public List<FiletosendDTO> findAll() {
        log.debug("Request to get all Filetosends");
        List<FiletosendDTO> result = filetosendRepository.findAll().stream()
            .map(filetosendMapper::filetosendToFiletosendDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one filetosend by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public FiletosendDTO findOne(String id) {
        log.debug("Request to get Filetosend : {}", id);
        Filetosend filetosend = filetosendRepository.findOne(UUID.fromString(id));
        FiletosendDTO filetosendDTO = filetosendMapper.filetosendToFiletosendDTO(filetosend);
        return filetosendDTO;
    }

    /**
     *  Delete the  filetosend by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Filetosend : {}", id);
        filetosendRepository.delete(UUID.fromString(id));
    }
}
