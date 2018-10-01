package com.liferay.ext.ddl.rest.client.service;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalService;

/**
 * @author André Fabbro
 *
 */
@Component(immediate = true, service = DynamicDataListRestClientService.class)
public class DynamicDataListRestClientServiceImpl implements DynamicDataListRestClientService {

	@Reference
	private DDLRecordLocalService ddlRecordService;

	@Reference
	private DDMContentLocalService ddmContentLocalService;

	@Override
	public List<String> findDataByRecordSetId(Long recordSetId) throws Exception {

		List<String> results = new ArrayList<String>();

		List<DDLRecord> records = ddlRecordService.getRecords(recordSetId);

		for (DDLRecord record : records) {
			long storageId = record.getDDMStorageId();
			DDMContent content = ddmContentLocalService.getContent(storageId);
			results.add(content.getData());
		}

		return results;
	}

}
