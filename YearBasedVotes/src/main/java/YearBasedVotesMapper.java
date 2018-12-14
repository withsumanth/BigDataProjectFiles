
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class YearBasedVotesMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable> {
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, IntWritable, IntWritable>.Context context)
			throws IOException, InterruptedException {

		String[] values = value.toString().split("\t");
		int votes = 0;
		int year = 0;
		try {
			votes = Integer.parseInt(values[9]);
			String dateString = values[14];
			DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
			Date dateOp = null;
			try {
				dateOp = df.parse(dateString);
				year = dateOp.getYear() + 1900;
				context.write(new IntWritable(year), new IntWritable(votes));
			} catch (ParseException e) {
				System.out.println("Error while converting Date " + e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
