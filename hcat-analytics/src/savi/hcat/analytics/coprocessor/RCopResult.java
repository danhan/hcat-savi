package savi.hcat.analytics.coprocessor;

import java.io.Serializable;

public class RCopResult implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	
	long start = 0;
	long end = 0;	
	int rows = 0; // the number of row scanned
	int cells = 0;
	String parameter = null;
	int kvLength = 0;	
	String startRow = null;
	String endRow = null;
	

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public int getCells() {
		return cells;
	}

	public void setCells(int cells) {
		this.cells = cells;
	}

	public int getKvLength() {
		return kvLength;
	}

	public void setKvLength(int kvLength) {
		this.kvLength = kvLength;
	}

	public String getStartRow() {
		return startRow;
	}

	public void setStartRow(String startRow) {
		this.startRow = startRow;
	}

	public String getEndRow() {
		return endRow;
	}

	public void setEndRow(String endRow) {
		this.endRow = endRow;
	}


}
