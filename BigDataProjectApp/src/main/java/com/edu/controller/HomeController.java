package com.edu.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.edu.pojo.Percentage;
import com.edu.pojo.Ratings;
import com.edu.pojo.StandardDeviation;
import com.edu.pojo.TopHundred;
import com.edu.pojo.TotalVotes;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/analytics", method = RequestMethod.POST)
	public ModelAndView analytics(HttpServletRequest request, Locale locale, Model model) throws ParseException {

		String type = request.getParameter("analytics");
		if (type.equals("avg")) {
			MongoClient mongoClient = null;
			MongoDatabase db;
			try {
				mongoClient = new MongoClient();
				db = mongoClient.getDatabase("projectdb");
				MongoCollection<Document> dbcol = db.getCollection("average");
				MongoCursor<Document> cursor = dbcol.find().iterator();
				ArrayList<Ratings> data = new ArrayList<Ratings>();
				Ratings ratings;
				while (cursor.hasNext()) {
					JSONParser parser = new JSONParser();
					JSONObject json = (JSONObject) parser.parse(cursor.next().toJson());
					JSONObject map = (JSONObject) json.get("value");
						ratings = new Ratings();
						ratings.setProductName((String) json.get("_id"));
						ratings.setProductRating((Double) map.get("avg"));
						data.add(ratings);
				}
				return new ModelAndView("analytics", "ratings", data);
			} catch (Exception e) {
				System.out.println("Error in mongo connection " + e);
			}finally{
				mongoClient.close();
			}
		} else if (type.equals("per")) {
			// Percentage calculation
			return new ModelAndView("percentage");
		} else if (type.equals("std")) {
			// Standard deviation

			Configuration configuration = new Configuration();
			configuration.set("fs.defaultFS", "hdfs://localhost:8020");
			FileSystem fs;
			try {
				fs = FileSystem.get(configuration);
				Path filePath = new Path("hdfs://localhost:8020/ProjectOutputs/StandardDev/part-r-00000");
				FSDataInputStream fsDataInputStream = fs.open(filePath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fsDataInputStream));
				String currentLine = null;
				String[] inputArray;
				ArrayList<StandardDeviation> data = new ArrayList<StandardDeviation>();
				StandardDeviation s;
				while ((currentLine = reader.readLine()) != null) {
					inputArray = currentLine.split("[\\s,]+");
					try {
						if (Double.isNaN(Double.parseDouble(inputArray[2])) != true) {
							s = new StandardDeviation();
							s.setProdName(inputArray[0]);
							s.setMedian(Double.parseDouble(inputArray[1]));
							s.setStdDev(Double.parseDouble(inputArray[2]));
							data.add(s);
						}
					} catch (Exception e) {
						System.out.println("Error in Parsing double " + e);
					}
				}
				return new ModelAndView("stddeviation", "stdOutput", data);
			} catch (IOException e) {

				System.out.println("Error in reading file " + e);
			}
		} else if (type.equals("top")) {
			// To find top 100
			String path = request.getParameter("path");
			Configuration configuration = new Configuration();
			configuration.set("fs.defaultFS", "hdfs://localhost:8020");
			FileSystem fs;
			try {
				fs = FileSystem.get(configuration);
				Path filePath = new Path("hdfs://localhost:8020/ProjectOutputs/TopHundred/"+path);
				FSDataInputStream fsDataInputStream = fs.open(filePath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fsDataInputStream));
				String currentLine = null;
				String[] inputArray;
				ArrayList<TopHundred> data = new ArrayList<TopHundred>();
				TopHundred s;
				while ((currentLine = reader.readLine()) != null) {
					inputArray = currentLine.split("[\\s,]+");
					try {
						s = new TopHundred();
						s.setProdId(inputArray[0]);
						s.setTopVotes(inputArray[1]);
						data.add(s);
					} catch (Exception e) {
						System.out.println("Error in Parsing double " + e);
					}
				}
				return new ModelAndView("tophundred", "topHundred", data);
			} catch (IOException e) {

				System.out.println("Error in reading file " + e);
			}
		}else if(type.equals("total")) {
			Configuration configuration = new Configuration();
			configuration.set("fs.defaultFS", "hdfs://localhost:8020");
			FileSystem fs;
			try {
				fs = FileSystem.get(configuration);
				Path filePath = new Path("hdfs://localhost:8020/ProjectOutputs/TotalVotes/part-r-00000");
				FSDataInputStream fsDataInputStream = fs.open(filePath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fsDataInputStream));
				String currentLine = null;
				String[] inputArray;
				ArrayList<TotalVotes> data = new ArrayList<TotalVotes>();
				TotalVotes s;
				while ((currentLine = reader.readLine()) != null) {
					inputArray = currentLine.split("[\\s,]+");
					try {
						s = new TotalVotes();
						s.setYear(Integer.parseInt(inputArray[0]));
						s.setVotes(Integer.parseInt(inputArray[1]));
						data.add(s);
					} catch (Exception e) {
						System.out.println("Error in Parsing integer " + e);
					}
				}
				return new ModelAndView("totalvotes", "totalvotes", data);
			} catch (IOException e) {

				System.out.println("Error in reading file " + e);
			}
		}
		return new ModelAndView("percentagenodata");
	}

	@RequestMapping(value = "/percentage", method = RequestMethod.POST)
	public ModelAndView percentage(HttpServletRequest request, Locale locale, Model model) throws ParseException {
		// HDFS Percentage

		Configuration configuration = new Configuration();
		configuration.set("fs.defaultFS", "hdfs://localhost:8020");
		FileSystem fs;
		try {
			fs = FileSystem.get(configuration);
			Path filePath = new Path("hdfs://localhost:8020/ProjectOutputs/Percentage/part-r-00000");
			HttpSession session = request.getSession();
			FSDataInputStream fsDataInputStream = fs.open(filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fsDataInputStream));
			String currentLine = null;
			String[] inputArray;
			String text = request.getParameter("product");
			Percentage p = new Percentage();
			while ((currentLine = reader.readLine()) != null) {
				inputArray = currentLine.split("[\\s,]+");
				if (inputArray[0].toLowerCase().equals(text.toLowerCase())) {
					session.setAttribute("productName", inputArray[0]);
					p.setVerifiedPerc(Double.parseDouble(inputArray[1]));
					p.setNonVerifiedPerc(Double.parseDouble(inputArray[2]));
					return new ModelAndView("percentageanalytics", "percentageData", p);
				}
			}
		} catch (IOException e) {

			System.out.println("Error in reading HDFS File " + e);
		}
		return new ModelAndView("percentagenodata");
	}

}
