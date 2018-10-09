package com.liferay.ext.ddl.rest.client.service;

import java.util.List;

/**
 * @author André Fabbro
 *
 */
public interface DynamicDataListRestClientService {

	/**
	 * Resturn the data of a Dynamic Data List records by the recordset id
	 * 
	 * @param Long:
	 *            recordSetId
	 * @return List of data results from the recordset
	 * @throws Exception
	 */
	List<String> findDataByRecordSetId(Long recordSetId) throws Exception;
	
	List<String> findDataByRecordSetName(String recordSetName, Long companyId, Long groupId) throws Exception;

}
