package com.me.writable;

public class PercentageWritable {
	double verifiedPerc;
	double nonVerifiedPerc;

	public PercentageWritable() {
		super();
	}

	public PercentageWritable(double verifiedPerc, double nonVerifiedPerc) {
		super();
		this.verifiedPerc = verifiedPerc;
		this.nonVerifiedPerc = nonVerifiedPerc;
	}

	@Override
	public String toString() {
		return verifiedPerc + "," + nonVerifiedPerc;
	}

	public double getVerifiedPerc() {
		return verifiedPerc;
	}

	public void setVerifiedPerc(double verifiedPerc) {
		this.verifiedPerc = verifiedPerc;
	}

	public double getNonVerifiedPerc() {
		return nonVerifiedPerc;
	}

	public void setNonVerifiedPerc(double nonVerifiedPerc) {
		this.nonVerifiedPerc = nonVerifiedPerc;
	}
}
