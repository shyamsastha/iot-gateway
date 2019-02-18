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
	private Integer sampleCount = 1;
	private String name;
	
	/*
	 * Constructor
	 */
	public SensorData(Double minVal, Double maxVal, Date time, String name) {
		super();
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.setTime(time);
		this.name = name;
	}
	
	/*
	 * To get sample count
	 */
	public Integer getSampleCount() {
		return sampleCount;
	}
	
	/*
	 * To set sample count 
	 */
	public void setSampleCount(Integer sampleCount) {
		this.sampleCount = sampleCount;
	}

	/*
	 * To get average value
	 */
	public Double getAvgValue() {
		return avgVal;
	}

	/*
	 * To set average value
	 */
	public void setAvgValue(Double avgVal) {
		this.avgVal = avgVal;
	}
	
	/*
	 * To get name
	 */
	public String getName() {
		return name;
	}

	/*
	 * To set name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * To get current value
	 */
	public Double getCurValue() {
		return curVal;
	}
	
	/*
	 * To set current value
	 */
	public void setCurValue(Double curVal) {
		this.curVal = curVal;
	}
	
	/*
	 * To get max value
	 */
	public Double getMaxValue() {
		return maxVal;
	}
	
	/*
	 * To set max value
	 */
	public void setMaxValue(Double maxVal) {
		this.maxVal = maxVal;
	}
	
	/*
	 * To get min value
	 */
	public Double getMinValue() {
		return minVal;
	}
	
	/*
	 * To set min value
	 */
	public void setMinValue(Double minVal) {
		this.minVal = minVal;
	}
	
	/*
	 * To get total value
	 */
	public Double getTotValue() {
		return totVal;
	}
	
	/*
	 * To set total value
	 */
	public void setTotValue(Double totVal) {
		this.totVal = totVal;
	}
	
	/*
	 * To get difference in value
	 */
	public Double getDiffValue() {
		return diffVal;
	}
	
	/*
	 * To set difference in value
	 */
	public void setDiffValue(Double diffVal) {
		this.diffVal = diffVal;
	}

	/*
	 * To get time
	 */
	public Date getTime() {
		return time;
	}

	/*
	 * To set time
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	
	/*
	 * To print all the sensor data from Json file
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Name: " + name + "\n"
				+ "Time: " + time + "n"
				+"\n" + "Current: " + curVal + "\n"
				+ "Average: " + avgVal + "n"
				+"\n" + "Samples: " + sampleCount + "\n"
				+ "Min: " + minVal + "\n" 
				+ "Max: " + maxVal + "\n";
	}
	
}