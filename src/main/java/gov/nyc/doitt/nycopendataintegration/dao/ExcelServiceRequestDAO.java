package gov.nyc.doitt.nycopendataintegration.dao;

import java.io.File;
import java.util.ArrayList;

import gov.nyc.doitt.nycopendataintegration.dto.ColumnDTO;
import gov.nyc.doitt.nycopendataintegration.dto.DataSetIndexDTO;

import gov.nyc.doitt.nycopendataintegration.util.PropertyLoaderUtil;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * ExcelServiceRequestDAO class is used mainly to read the dataset file and data
 * dictionary file to get the metadata required for creating a dataset.
 * 
 */
public class ExcelServiceRequestDAO {

	private static Logger logger = Logger
			.getLogger(ExcelServiceRequestDAO.class);

	/**
	 * reads the data dictionary file to get the column name, description and
	 * type index .
	 * 
	 * @param Sheet
	 *            the excel sheet to read
	 * 
	 * @param dataSetIndexDTO
	 *            to hold the index of the required column fields
	 * 
	 * @return dataSetIndexDTO
	 * 
	 * 
	 */
	private DataSetIndexDTO getDataSetColumnIndex(Sheet s,
			DataSetIndexDTO dataSetIndexDTO) throws Exception {

		int lastRowNum = s.getLastRowNum();
		for (int j = 0; j < lastRowNum; j++) {

			Row r1 = s.getRow(j);
			boolean datasetIdIndex = false;
			boolean categoryIndex = false;
			boolean typeIndex = false;
			for (int i = 0; i <= r1.getLastCellNum(); i++) {

				if (r1.getCell(i) != null
						&& !r1.getCell(i).toString().isEmpty()) {

					if (r1.getCell(i)
							.toString()
							.equalsIgnoreCase(
									PropertyLoaderUtil.COLUMN_DATASET_ID_HEADER)) {

						dataSetIndexDTO.setDataSetIdIndex(i);
						datasetIdIndex = true;
					}

					else if (r1
							.getCell(i)
							.toString()
							.equalsIgnoreCase(
									PropertyLoaderUtil.COLUMN_CATEGORY_HEADER)) {

						dataSetIndexDTO.setCategoryIndex(i);
						categoryIndex = true;
					}

					else if (r1
							.getCell(i)
							.toString()
							.equalsIgnoreCase(
									PropertyLoaderUtil.COLUMN_TYPE_HEADER)) {

						dataSetIndexDTO.setTypeIndex(i);
						typeIndex = true;
					}
				}
			}
			logger.info("columnDataSetIdIndex "
					+ dataSetIndexDTO.getDataSetIdIndex());
			logger.info("columnCategoryIndex "
					+ dataSetIndexDTO.getCategoryIndex());

			logger.info("columnTypeIndex " + dataSetIndexDTO.getTypeIndex());
			if (datasetIdIndex && categoryIndex && typeIndex) {

				dataSetIndexDTO.setDatasetValuesIndex(++j);

				break;

			}

		}
		
		logger.info("columnDataSetIdIndex "
				+ dataSetIndexDTO.getDataSetIdIndex());
		logger.info("columnCategoryIndex "
				+ dataSetIndexDTO.getCategoryIndex());

		logger.info("columnTypeIndex " + dataSetIndexDTO.getTypeIndex());

		return dataSetIndexDTO;
	}

	/**
	 * reads the data dictionary file to get the column name, description and
	 * type .
	 * 
	 * @param dataSetIndexDTO
	 *            to hold the index of the required column fields
	 * 
	 * @param dataDictionaryFile
	 *            the location of the data dictionary
	 * 
	 * @param listOfFiles
	 *            all the files in the folder where the dataset file is located
	 * 
	 * @return list of columns
	 * 
	 * 
	 */
	public ArrayList<ColumnDTO> getColumnsFromDictionary(String dataListFile)
			throws Exception {
		logger.info("dataDictionaryFile " + dataListFile);
		ArrayList<ColumnDTO> columnDTOList = new ArrayList<ColumnDTO>();

		Workbook workBook = WorkbookFactory.create(new File(dataListFile));

		int sheetIndex = workBook
				.getSheetIndex(PropertyLoaderUtil.DATASET_LIST_SHEET);
		Sheet s = workBook.getSheetAt(sheetIndex);

		DataSetIndexDTO dataSetIndexDTO = new DataSetIndexDTO();
		dataSetIndexDTO = getDataSetColumnIndex(s, dataSetIndexDTO);
		logger.info("dataSetIndexDTO.getDatasetValuesIndex() "
				+ dataSetIndexDTO.getDatasetValuesIndex());
		int count = 0;
		for (int i = dataSetIndexDTO.getDatasetValuesIndex(); i <= s
				.getLastRowNum(); i++) {
			Row r = s.getRow(i);
			ColumnDTO columnDTO = new ColumnDTO();

			if (r != null
					&& r.getCell(dataSetIndexDTO.getDataSetIdIndex()) != null
					&& !r.getCell(dataSetIndexDTO.getDataSetIdIndex())
							.toString().isEmpty()
					&& r.getCell(dataSetIndexDTO.getCategoryIndex()) != null
					&& !r.getCell(dataSetIndexDTO.getCategoryIndex())
							.toString().isEmpty()) {

				columnDTO.setDataSetId((r.getCell(dataSetIndexDTO
						.getDataSetIdIndex())).toString());

				columnDTO.setCategory((r.getCell(dataSetIndexDTO
						.getCategoryIndex())).toString());

				columnDTO.setType((r.getCell(dataSetIndexDTO.getTypeIndex()))
						.toString());

				columnDTOList.add(columnDTO);

				logger.info("DataSet Id is " + columnDTO.getDataSetId());

				logger.info("Category  is " + columnDTO.getCategory());
				logger.info("count " + count++);

			}

		}

		return columnDTOList;

	}

}
