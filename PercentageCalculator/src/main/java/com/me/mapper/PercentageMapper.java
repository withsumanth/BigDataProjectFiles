package com.me.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PercentageMapper extends Mapper<LongWritable, Text, Text, Text> {

	Text prod = new Text();
	Text verified = new Text();

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		String values[] = value.toString().split("\t");
		try {
			prod.set(values[6]);
			verified.set(values[11]);
			context.write(prod, verified);
		} catch (Exception e) {
			System.out.println("Exception in ");
		}
	}

}
