/*
 * (C) Copyright IBM Corp. 2019
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibm.fhir.persistence.jdbc.dao.api;

import java.util.Map;

import com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDBConnectException;
import com.ibm.fhir.persistence.jdbc.exception.FHIRPersistenceDataAccessException;

/**
 * This Data Access Object interface defines APIs specific to parameter_names table.
 * @author rarnold
 */
public interface CodeSystemDAO {
    
    /**
     * Reads all rows in the Parameter_Names table and returns the data as a Map
     * 
     * @return A map containing key=parameter-name, value=parameter-name-id
     * @throws FHIRPersistenceDBConnectException
     * @throws FHIRPersistenceDataAccessException
     */
    Map<String,Integer> readAllCodeSystems() throws FHIRPersistenceDBConnectException, FHIRPersistenceDataAccessException;
        
    /**
     * Reads the id associated with the name of the passed Parameter from the code_systems table. 
     * If the id for the passed name is not present in the database, an id is generated, persisted, 
     * and returned. This is done in a thread-safe way
     * 
     * @param codeSystem A valid FHIR search  parameter name.
     * @return the id associated with the name of the passed Parameter.
     * @throws FHIRPersistenceDBConnectException
     * @throws FHIRPersistenceDataAccessException
     */
    int readOrAddCodeSystem(String codeSystem) throws FHIRPersistenceDBConnectException, FHIRPersistenceDataAccessException;
    
    /**
     * Read the code_system_id for the given code system name
     * @param codeSystem
     * @return the parameter_name_id for for parameter, or null if it doesn't exist
     * @throws FHIRPersistenceDBConnectException
     * @throws FHIRPersistenceDataAccessException
     */
    Integer readCodeSystemId(String codeSystem) throws FHIRPersistenceDBConnectException, FHIRPersistenceDataAccessException;

}