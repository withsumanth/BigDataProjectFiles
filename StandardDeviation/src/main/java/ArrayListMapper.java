

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class ArrayListMapper extends Mapper<LongWritable,Text, Text,DoubleWritable>{

	Text brand = new Text();
	DoubleWritable rating = new DoubleWritable();

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, DoubleWritable>.Context context)
			throws IOException, InterruptedException {
		String values[] = value.toString().split("\t");
		try {
			brand.set(values[6]);
			rating.set(Double.parseDouble(values[7]));
		}catch(Exception e) {
			
		}
		context.write(brand, rating);
	}
}
