package schooldomain.studentname.connecteddevices.common;

/**
 * Simple SensorData class that is equivalent to the python SensorData class
 * @author: Shyama Sastha Krishnamoorthy Srinivasan
 */

import java.util.Date;

public class SensorData {
	private Double curVal;
	private Double maxVal;
	private Double minVal;
	private Double totVal;
	private Double diffVal;
	private Double avgVal;
	private Date time;
	private Integer sampleCount = 0;
	private String name;
	
	public SensorData(Double minVal, Double maxVal, Date time, String name) {
		super();
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.time = time;
		this.name = name;
	}
	
	public Integer getSampleCount() {
		return sampleCount;
	}

	public void setSampleCount(Integer sampleCount) {
		this.sampleCount = sampleCount;
	}

	public Double getAvgValue() {
		return avgVal;
	}

	public void setAvgValue(Double avgVal) {
		this.avgVal = avgVal;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCurValue() {
		return curVal;
	}
	
	public void setCurValue(Double curVal) {
		this.curVal = curVal;
	}
	
	public Double getMaxValue() {
		return maxVal;
	}
	
	public void setMaxValue(Double maxVal) {
		this.maxVal = maxVal;
	}
	
	public Double getMinValue() {
		return minVal;
	}
	
	public void setMinValue(Double minVal) {
		this.minVal = minVal;
	}
	
	public Double getTotValue() {
		return totVal;
	}
	
	public void setTotValue(Double totVal) {
		this.totVal = totVal;
	}
	
	public Double getDiffValue() {
		return diffVal;
	}
	
	public void setDiffValue(Double diffVal) {
		this.diffVal = diffVal;
	}
	
}