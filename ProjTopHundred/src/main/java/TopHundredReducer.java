

import java.io.IOException;
import java.util.TreeMap;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
 
public class TopHundredReducer extends Reducer<NullWritable, Text, Text, Text> {

	private TreeMap<Integer, Text> recordsMap;
	
	@Override
	protected void setup(Reducer<NullWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		this.recordsMap = new TreeMap<Integer, Text>();
	}
	
	@Override
	protected void reduce(NullWritable arg0, Iterable<Text> arg1,
			Reducer<NullWritable, Text, Text, Text>.Context arg2) throws IOException, InterruptedException {
		
		for (Text t : arg1) {
			String values[] = t.toString().split("\t");
			//String product = values[0];
			String score = values[9];
			recordsMap.put(Integer.parseInt(score), new Text(t));
		}
		
		if (recordsMap.size() > 100) {
			recordsMap.remove(recordsMap.firstKey());
		}
	}
	
	@Override
	protected void cleanup(Reducer<NullWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		for (Text t : recordsMap.values()) {
			String values[] = t.toString().split("\t");
			context.write(new Text(values[3]), new Text(values[9]));
		}
	}
}