package com.edu.mapper;

import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class RandomDataMapper extends Mapper<Object,Text,NullWritable,Text>{
	
	private Double perc;
	private Random rand = new Random();
	@Override
	protected void setup(Mapper<Object, Text, NullWritable, Text>.Context context)
			throws IOException, InterruptedException {
		perc = Double.parseDouble("50")/100;
		
	}

	@Override
	protected void map(Object key, Text value, Mapper<Object, Text, NullWritable, Text>.Context context)
			throws IOException, InterruptedException {
		if(rand.nextDouble()<perc) {
			context.write(NullWritable.get(), value);
		}
	}

}
