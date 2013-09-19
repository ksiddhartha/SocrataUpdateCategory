package gov.nyc.doitt.nycopendataintegration.main;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.socrata.api.HttpLowLevel;
import com.socrata.api.SodaImporter;

import com.socrata.model.importer.DatasetInfo;

import gov.nyc.doitt.nycopendataintegration.dao.ExcelServiceRequestDAO;
import gov.nyc.doitt.nycopendataintegration.dto.ColumnDTO;
import gov.nyc.doitt.nycopendataintegration.util.PropertyLoaderUtil;

public class UpdateDataSetCategory {

	/**
	 * @param args
	 */

	private static Logger logger = Logger
			.getLogger(UpdateDataSetCategory.class);

	public static void main(String[] args) {

		if (PropertyLoaderUtil.PROXY_ENABLED) {
			// Proxy Settings
			Properties sysProperties = System.getProperties();
			logger.info("Proxy is enabled");
			sysProperties.put("https.proxyHost", PropertyLoaderUtil.PROXY_HOST);
			sysProperties.put("https.proxyPort", PropertyLoaderUtil.PROXY_PORT);

			sysProperties.put("proxySet", "true");

		}
		final HttpLowLevel httpLowLevel = HttpLowLevel.instantiateBasic(
				PropertyLoaderUtil.DOMAIN, PropertyLoaderUtil.USER,
				PropertyLoaderUtil.PASSWORD, PropertyLoaderUtil.APPTOKEN);

		ExcelServiceRequestDAO excelServiceRequestDAO = new ExcelServiceRequestDAO();
		ArrayList<ColumnDTO> columnsListDto = new ArrayList<ColumnDTO>();


		try {
			columnsListDto = excelServiceRequestDAO
					.getColumnsFromDictionary(PropertyLoaderUtil.DATASET_FILE);
			int counter = 0;
			for (ColumnDTO columnDTO : columnsListDto) {
				logger.info("Processing for DataSetId  " + counter++ + " "
						+ columnDTO.getDataSetId());
				try {
					SodaImporter sodaImporter = new SodaImporter(httpLowLevel);

					if (columnDTO.getType().equalsIgnoreCase("tabular")) {
						
						logger.info("Processing Tabular  ");
						DatasetInfo datasetInfo = sodaImporter
								.createWorkingCopy(columnDTO.getDataSetId());
						DatasetInfo draftCopy = DatasetInfo.copy(datasetInfo);
						draftCopy.setCategory(columnDTO.getCategory());
						datasetInfo = sodaImporter
								.updateDatasetInfo(draftCopy);
						sodaImporter.publish(datasetInfo.getId());
					} else {
						
						logger.info("Processing blob or external or chart or filter  " );
						logger.info("columnDTO.getDataSetId() "+columnDTO.getDataSetId());
						DatasetInfo datasetInfo = sodaImporter
								.loadDatasetInfo(columnDTO.getDataSetId());
						datasetInfo.setCategory(columnDTO.getCategory());

						datasetInfo = sodaImporter
								.updateDatasetInfo(datasetInfo);
					}

				} catch (Exception ex) {
					logger.error(
							"Error while changing category for dataset id "
									+ columnDTO.getDataSetId(), ex);
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
