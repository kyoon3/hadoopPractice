import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
<<<<<<< HEAD
import org.apache.hadoop.io.FloatWritable;
=======

>>>>>>> d631aff7f24656791c9d50f3f54ab845bb3ac07f
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
<<<<<<< HEAD
			String[] SinglePersonData = valueString.split(",");
			context.write(new Text(SinglePersonData[0]), new Text(SinglePersonData[2]+","+"1"));
			}
		}
	

	public static class MovieScoreReducer extends Reducer<Text,Text,Text,Text> {
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			String[] SinglePerson;
			Text result = new Text();
			Float total_value = new Float("0");
			Float num_items = new Float("0");
			for (Text val : values) {
				SinglePerson = val.toString().split(",");
				total_value += Float.valueOf(SinglePerson[0]);
				num_items += Float.valueOf(SinglePerson[1]);
			}
			total_value = total_value / num_items;
			result.set(new Text(total_value.toString()));
=======
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
>>>>>>> d631aff7f24656791c9d50f3f54ab845bb3ac07f
			context.write(key, result);
		}
	}

	public static void main(String[] args) throws Exception {
<<<<<<< HEAD
		Job job = new Job();

=======
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "MovieScore");
>>>>>>> d631aff7f24656791c9d50f3f54ab845bb3ac07f
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
