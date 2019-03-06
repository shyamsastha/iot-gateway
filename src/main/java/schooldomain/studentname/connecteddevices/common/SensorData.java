package schooldomain.studentname.connecteddevices.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple SensorData class that is equivalent to the python SensorData class
 * @author: Shyama Sastha Krishnamoorthy Srinivasan
 */

public class SensorData {
	private Double curVal;
	private Double maxVal;
	private Double minVal;
	private Double totVal;
	private Double diffVal;
	private Double avgVal;
	private String timeStamp;
	private Integer sampleCount = 0;
	private String name;
	
	/*
	 * Constructor
	 */
	public SensorData(Double minVal, Double maxVal, String timeStamp, String name) {
		super();
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.timeStamp = timeStamp;
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
	public String getTime() {
		return timeStamp;
	}

	/*
	 * To set/update time
	 */
	public void updateTime() {
		this.timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm.ss").format(new Date());
	}
	
	/*
	 * To update value
	 */
	public void updateValue(float val) {
		this.timeStamp = getTime();
		++this.sampleCount;
		this.curVal = (double) val;
		this.totVal += (double) val;
		if (this.curVal < this.minVal) {
			this.minVal = this.curVal;
		}
		if (this.curVal > this.maxVal) {
			this.maxVal = this.curVal;
		}
		if (this.totVal != 0 && this.sampleCount > 0) {
			this.avgVal = this.totVal / this.sampleCount;
		}
	}
	
	/*
	 * To print all the sensor data from Json file
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Name: " + name + "\n"
				+ "Time: " + timeStamp + "n"
				+"\n" + "Current: " + curVal + "\n"
				+ "Average: " + avgVal + "n"
				+"\n" + "Samples: " + sampleCount + "\n"
				+ "Min: " + minVal + "\n" 
				+ "Max: " + maxVal + "\n";
	}
	
}