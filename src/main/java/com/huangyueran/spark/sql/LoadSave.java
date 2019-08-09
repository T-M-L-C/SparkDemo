package com.huangyueran.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;

/**
 * @category LoadSave
 * @author huangyueran
 * @time 2019-7-24 13:58:59
 */
public class LoadSave {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("LoadSave").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);

		// 创建DataFrame 读取json
		SQLContext sqlContext = new SQLContext(sc);

		DataFrameReader dataFrameReader = sqlContext.read();

		// parquet 是本地数据存储的格式
		Dataset<Row> dataset = dataFrameReader.load("/data/resources/users.parquet");

		dataset.printSchema();
		dataset.show();

		// 通过关writer写入并保存save
		DataFrameWriter write = dataset.select("name", "favorite_color").write();
		write.save("namesAndColors.parquet");

		// DataFrame df1 = dataFrameReader
		// .load("namesAndColors.parquet/part-r-00000-7de940b5-233f-43b5-8e4f-1ef80390f28b.gz.parquet");
		// df1.show();

		sc.close();
	}
}
