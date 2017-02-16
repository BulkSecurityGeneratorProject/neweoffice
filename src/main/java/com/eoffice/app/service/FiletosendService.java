package com.eoffice.app.service;

import com.eoffice.app.service.dto.FiletosendDTO;
import java.util.List;

/**
 * Service Interface for managing Filetosend.
 */
public interface FiletosendService {

    /**
     * Save a filetosend.
     *
     * @param filetosendDTO the entity to save
     * @return the persisted entity
     */
    FiletosendDTO save(FiletosendDTO filetosendDTO);

    /**
     *  Get all the filetosends.
     *  
     *  @return the list of entities
     */
    List<FiletosendDTO> findAll();

    /**
     *  Get the "id" filetosend.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FiletosendDTO findOne(String id);

    /**
     *  Delete the "id" filetosend.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
