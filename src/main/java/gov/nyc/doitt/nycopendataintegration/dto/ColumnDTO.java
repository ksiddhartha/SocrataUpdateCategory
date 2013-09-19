package gov.nyc.doitt.nycopendataintegration.dto;

/**
 * The ColumnDTO class holds all required fields for updating the dataset category
 */
public class ColumnDTO {
	private String dataSetId;
	private String category;
	private String type;

	public String getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(String dataSetId) {
		this.dataSetId = dataSetId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
