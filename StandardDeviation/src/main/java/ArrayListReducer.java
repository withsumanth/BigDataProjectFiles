

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ArrayListReducer extends Reducer<Text,DoubleWritable,Text,MedStdDevWritable>{
	
	MedStdDevWritable result;
	ArrayList<Double> ratingList;

	@Override
	protected void reduce(Text arg0, Iterable<DoubleWritable> arg1,
			Reducer<Text, DoubleWritable, Text, MedStdDevWritable>.Context context)
			throws IOException, InterruptedException {
		
		ratingList = new ArrayList<Double>();
		int counter = 0;
		double sum = 0.0;
		for(DoubleWritable val: arg1) {
			ratingList.add(val.get());
			counter+=1;
			sum += val.get();
		}
		Collections.sort(ratingList);
		
		result = new MedStdDevWritable();
		
		//find median
		double median;
		if(counter % 2 == 0) {
			median = (ratingList.get(counter/2 - 1) + ratingList.get(counter/2))/2;
		}else {
			median = ratingList.get(counter/2);
		}
		result.setMedian(String.valueOf(median));
		
		double mean = sum/counter;
		double sumOfSquares = 0.0;
		for(double d: ratingList) {
			sumOfSquares += (d-mean) * (d-mean);
		}
		double stdDev = Math.sqrt(sumOfSquares/(counter-1));
		result.setStdDev(String.valueOf(stdDev));
		
		context.write(arg0,result);
	}

}
