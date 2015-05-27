package com.live.video.constans;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Response {

	public Recommendation recommendation;

	public Movie movie;

	public ArrayList<Recommendation> reList = new ArrayList<Recommendation>();

	public ArrayList<Movie> moList = new ArrayList<Movie>();

	public class Recommendation {
		public int id;
		public String title;
		public String imageUrl;
	}

	public class Movie {
		public int id;
		public String title;
		public String imageUrl;
	}

	public void parseJosn(String s) {
		try {
			JSONObject jo = new JSONObject(s);

			JSONArray recoArray = jo.getJSONArray("recommendation");
			for (int i = 0; i < recoArray.length(); i++) {

				JSONObject recoObj = (JSONObject) recoArray.get(i);
				recommendation = new Recommendation();

				recommendation.id = recoObj.getInt("id");
				recommendation.title = recoObj.getString("title");
				recommendation.imageUrl = recoObj.getString("imageUrl");
				reList.add(recommendation);
			}
			JSONArray movieArray = jo.getJSONArray("movie");
			for (int i = 0; i < movieArray.length(); i++) {

				JSONObject movieObj = (JSONObject) movieArray.get(i);
				movie = new Movie();

				movie.id = movieObj.getInt("id");
				movie.title = movieObj.getString("title");
				movie.imageUrl = movieObj.getString("imageUrl");
				moList.add(movie);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
