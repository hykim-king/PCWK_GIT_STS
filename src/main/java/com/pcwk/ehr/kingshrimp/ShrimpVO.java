package com.pcwk.ehr.kingshrimp;

public class ShrimpVO extends DTO {
	private String name;
	private int length;

	public ShrimpVO() {
		// TODO Auto-generated constructor stub
	}

	public ShrimpVO(String name, int length) {
		this.name = name;
		this.length = length;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "ShrimpVO [name=" + name + ", length=" + length + "]";
	}

}
