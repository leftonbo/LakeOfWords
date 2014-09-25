package models.apis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSRequestHolder;
import play.libs.ws.WSResponse;

import com.fasterxml.jackson.databind.JsonNode;

public class ApiColorColor {

	public String request;
	public int result;
	public List<Integer> colors;
	
	public ApiColorColor(String req) {
		request = req;
	}
	
	public int Send() {
		colors = new ArrayList<Integer>();
		
		WSRequestHolder holder = WS.url("http://synthsky.com/iroiro/q")
				.setQueryParameter("format", "json")
				.setQueryParameter("req", request);
		
		Promise<Integer> promRes = holder.get().map(
			    new Function<WSResponse, Integer>() {
			        public Integer apply(WSResponse response) {
			        	if (response.getStatus() != 200) {
			        		return -1;
			        	}
			            JsonNode json = response.asJson();
			            
			            if (json.get("returnCode").asInt(-1) != 0) {
			            	return -2;
			            }
			            
			            JsonNode elems = json.get("scheme").get("elements");
			            Iterator<JsonNode> it = elems.iterator();
			            while (it.hasNext()) {
			            	JsonNode node  = it.next();
			            	int r = node.get("r").asInt(0);
			            	int g = node.get("g").asInt(0);
			            	int b = node.get("b").asInt(0);
			            	int rgb = r * 0x10000 + g * 0x100 + b;
			            	colors.add( rgb );
			            	// play.Logger.debug(String.format("colorget:#%06X and %s", rgb, node.get("rgb").asText()));
			            }
			            
			            return 0;
			        }
			    }
			);
		
		result = promRes.get(10000);
		
		return result;
	}
	
}
