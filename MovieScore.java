import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MovieScore{

	public static class MovieScoreMapper extends Mapper<LongWritable, Text, Text, Text>{
		private Text word = new Text();
		private Text value = new Text();

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String valueString = value.toString();
			String[] SinglePersonData = valueString.split("\t");
			context.write(new Text(SinglePersonData[0]), new Text(SinglePersonData[2]+"\t"+"1"));
			}
		}
	}

	public static class MovieScoreReducer extends Reducer<Text,Text,Text,IntWritable> {
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			String[] SinglePersonData;
			String output = "";
			int total_value = 0;
			int num_items = 0;
			for (Text val : values) {
				SinglePersonData = val.toString().split("\t");
				total_value += Integer.parseInt(SinglePersonData[0]);
				num_items += Integer.parseInt(SinglePersonData[1]);
			}
			total_value = total_value / sum;
			result.set(total_value);
			context.write(key, result);
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "MovieScore");
		job.setJarByClass(MovieScore.class);
		job.setMapperClass(MovieScoreMapper.class);
		job.setCombinerClass(MovieScoreReducer.class);
		job.setReducerClass(MovieScoreReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
