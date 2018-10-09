package com.liferay.ext.ddl.rest.client.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.ext.ddl.rest.client.service.DynamicDataListRestClientService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author André Fabbro
 */
@ApplicationPath("/ddlclient")
@Component(immediate = true, service = Application.class)
public class DDLRestClientApplication extends Application {

	private Log _log = LogFactoryUtil.getLog(DDLRestClientApplication.class.getName());

	@Reference
	private DynamicDataListRestClientService service;

	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}

	/**
	 * Gets the data by the recordset id, in json format, for example, to retrieve a
	 * list of values in english of a ddl with a field named MyFieldName:
	 * <code>$..fieldValues[?(@.name == 'MyFieldName')].value.en_US</code>
	 * 
	 * @param recordSetId
	 * @return
	 */
	@GET
	@Path("/get-data-by-record-set-id/{recordSetId}")
	@Produces("application/json")
	public String getRecordSetContentsById(@PathParam("recordSetId") String recordSetId) {

		List<String> results = new ArrayList<String>();

		try {
			results = service.findDataByRecordSetId(Long.parseLong(recordSetId));
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}

		StringBuffer res = new StringBuffer("[");
		res.append(results.stream().collect(Collectors.joining(",")));
		res.append("]");

		return res.toString();
	}

	@GET
	@Path("/get-data-by-record-set-name/{recordSetName}")
	@Produces("application/json")
	public String getRecordSetContentsByName(@PathParam("recordSetName") String recordSetName) {

		List<String> results = new ArrayList<String>();

		try {
			results = service.findDataByRecordSetName(recordSetName, PortalUtil.getDefaultCompanyId(),
					GroupThreadLocal.getGroupId());
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}

		StringBuffer res = new StringBuffer("[");
		res.append(results.stream().collect(Collectors.joining(",")));
		res.append("]");

		return res.toString();
	}

}