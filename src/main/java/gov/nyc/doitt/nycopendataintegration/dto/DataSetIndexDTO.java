package gov.nyc.doitt.nycopendataintegration.dto;

/**
 * The DataSetIndexDTO class holds all the indexes of required fileds in the
 * dataset excel file
 */
public class DataSetIndexDTO {
	private int dataSetIdIndex = -1;

	private int categoryIndex = -1;
	
	private int typeIndex = -1;
	
	private int datasetValuesIndex = -1;
	

	

	
	public int getCategoryIndex() {
		return categoryIndex;
	}

	public void setCategoryIndex(int categoryIndex) {
		this.categoryIndex = categoryIndex;
	}

	public int getDataSetIdIndex() {
		return dataSetIdIndex;
	}

	public void setDataSetIdIndex(int dataSetIdIndex) {
		this.dataSetIdIndex = dataSetIdIndex;
	}

	public int getTypeIndex() {
		return typeIndex;
	}

	public void setTypeIndex(int typeIndex) {
		this.typeIndex = typeIndex;
	}

	public int getDatasetValuesIndex() {
		return datasetValuesIndex;
	}

	public void setDatasetValuesIndex(int datasetValuesIndex) {
		this.datasetValuesIndex = datasetValuesIndex;
	}

	
}
