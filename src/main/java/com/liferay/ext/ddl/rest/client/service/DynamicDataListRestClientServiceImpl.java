package com.liferay.ext.ddl.rest.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

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

	@Reference
	private DDLRecordSetLocalService ddlRecordSetService;

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

	@Override
	public List<String> findDataByRecordSetName(String recordSetName, Long companyId, Long groupId) throws Exception {

		List<DDLRecordSet> sets = ddlRecordSetService.search(companyId, groupId, recordSetName, null,
				DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		List<String> results = new ArrayList<String>();

		if (!sets.isEmpty()) {

			for (DDLRecordSet set : sets) {
				
				Locale locale = LocaleThreadLocal.getDefaultLocale();
				
				if (set.getName(locale).equals(recordSetName)) {
					List<DDLRecord> records = ddlRecordService.getRecords(set.getRecordSetId());

					for (DDLRecord record : records) {
						long storageId = record.getDDMStorageId();
						DDMContent content = ddmContentLocalService.getContent(storageId);
						results.add(content.getData());
					}

					break;
				}
			}
		}
		return results;
	}

}
