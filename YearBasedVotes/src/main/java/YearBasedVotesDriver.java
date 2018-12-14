

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class YearBasedVotesDriver {

	public static void main(String[] args) throws Exception{
		Path inputPath = new Path(args[0]);
		Path outputPath = new Path(args[1]);
		
		Configuration conf = new Configuration();
		
		Job job = Job.getInstance(conf,"YearBasedVote");
		job.setJarByClass(YearBasedVotesDriver.class);
		
		job.setMapperClass(YearBasedVotesMapper.class);
		job.setReducerClass(YearBasedVotesReducer.class);
		
		job.setNumReduceTasks(1);
		
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, inputPath);
		job.setInputFormatClass(TextInputFormat.class);
		
		FileOutputFormat.setOutputPath(job, outputPath);
		
		FileSystem hdfs = FileSystem.get(conf);
		
		if(hdfs.exists(outputPath)) {
			hdfs.delete(outputPath,true);
		}
		int code = job.waitForCompletion(true)? 0 : 1;
		System.exit(code);
	}

}
