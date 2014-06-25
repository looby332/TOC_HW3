/****************************************************************************

author: 賴冠如
date: 2014/6/24
student ID: H34001123
description: This homework is about parsing the house information by a URL 
to find the specify "road , address ,total price , sale date". And then print
the avg(total price) of this data.

****************************************************************************/
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.net.*;
import org.json.*;

public class TocHw3 {

	public static void main(String[] args) throws Exception {
		URL url = new URL(args[0]);
		URLConnection connect = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connect.getInputStream(), "UTF-8"));
		String inputLine;

		String town = null;
		String address = null;
		int time = 0;
		int price = 0;
		int totalprice = 0;
		int avg_price = 0;
		int count_effect = 0 ;
		int count = 0;

		while ((inputLine = in.readLine()) != null) { // System.out.println(inputLine);
			
			if (count != 0 && inputLine.charAt(0) == '{') {
				JSONTokener toke = new JSONTokener(inputLine);
				JSONObject houseOBJ;
				try {
					houseOBJ = (JSONObject) toke.nextValue();
					town = houseOBJ.getString("鄉鎮市區");
					address = houseOBJ.getString("土地區段位置或建物區門牌");
					time = houseOBJ.getInt("交易年月");
					price = houseOBJ.getInt("總價元");
					String patt_town=args[1];
					Pattern pattern_town = Pattern.compile(patt_town);
					Matcher match_town = pattern_town.matcher(town);
					while(match_town.find()){
						String patt_address =".*"+args[2]+".*";
						Pattern pattern_address = Pattern.compile(patt_address);
						Matcher match = pattern_address.matcher(address); 
						while(match.find()){
							if(time/100>=Integer.parseInt(args[3])) {
								totalprice+=price;
								count_effect++;
							}
								/*System.out.println(town + " " + address + " " + time + " "
									+ price);*/
						}
					}		
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} count++;
		} 
		avg_price = totalprice/count_effect;
		System.out.println(avg_price);

		/*
		 * String patt = ".*?"; //".*?n"; Pattern pattern =
		 * Pattern.compile(patt); Matcher match = pattern.matcher(inputLine);
		 * System.out.println(inputLine); while(match.find()){
		 * System.out.println("Match: "+match.group()); }
		 */
		in.close();
	}
}
