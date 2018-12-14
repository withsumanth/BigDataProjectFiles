package com.me.reducer;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.me.writable.PercentageWritable;

public class PercentageReducer extends Reducer<Text,Text,Text,PercentageWritable>{
	

	private double verifiedPerc = 0.0;
	private double nonVerifiedPerc = 0.0;
	private PercentageWritable pwrit;
	
	@Override
	protected void reduce(Text keyIn, Iterable<Text> valueIn,
			Reducer< Text,Text,Text,PercentageWritable>.Context context)
			throws IOException, InterruptedException {
		double verifiedCount = 0.0;
		pwrit = new PercentageWritable();
		double nonVerifiedCount = 0.0;
		double count = 0.0;
		for(Text d:valueIn) {
			count++;
			if(d.toString().equals("Y")) {
				verifiedCount++;
			}else {
				nonVerifiedCount++;
			}
		}
		verifiedPerc = (Double)verifiedCount/count * 100;
		nonVerifiedPerc = (Double)nonVerifiedCount/count * 100;
		pwrit.setNonVerifiedPerc(nonVerifiedPerc);	
		pwrit.setVerifiedPerc(verifiedPerc);;	
		context.write(keyIn, pwrit);
	}
}
