package models.apis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSAuthScheme;
import play.libs.ws.WSRequestHolder;
import play.libs.ws.WSResponse;

public class ApiWord {
	
	public String request;
	public int result;
	public List<String> sen;
	
	public ApiWord(String req) {
		request = req;
	}
	
	public int Send() {
		sen = new ArrayList<String>();
		
		WSRequestHolder holder = WS.url("https://lr.capio.jp/webapis/iminos/synana/1_1/")
				.setAuth("6C0apQAo", "x9SXQFbk", WSAuthScheme.BASIC)
				.setQueryParameter("sent", request);
		
		Promise<Integer> promRes = holder.get().map(
			    new Function<WSResponse, Integer>() {
			        public Integer apply(WSResponse response) {
			        	if (response.getStatus() != 200) {
			        		return -1;
			        	}
			            JsonNode json = response.asJson();
			            
			            if (json.get("apierr").asInt(-1) != 0) {
			            	return -2;
			            }
			            // play.Logger.debug(json.toString());
			            
			            JsonNode elems = json.get("results").get(0).get("sensibilities");
			            Iterator<JsonNode> it = elems.iterator();
			            while (it.hasNext()) {
			            	JsonNode node  = it.next();
			            	sen.add( node.asText() );
			            	// play.Logger.debug(String.format("eee %s", node.asText()));
			            }
			            
			            return 0;
			        }
			    }
			);
		
		result = promRes.get(10000);
		
		return result;
	}

}
