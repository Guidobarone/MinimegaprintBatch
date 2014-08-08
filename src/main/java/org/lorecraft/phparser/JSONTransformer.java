package org.lorecraft.phparser;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONTransformer {

	public static Object toJSON(Object o) throws JSONException {
		if (o instanceof Map) {
			return arrayToJSON((Map)o);
		} else if (o instanceof SerializedPhpParser.PhpObject) {
			return mapToJSON(((SerializedPhpParser.PhpObject)o).attributes);
		} else if (o == SerializedPhpParser.NULL) {
			return null;
		}
		return o;
	}

	private static JSONArray arrayToJSON(Map o) throws JSONException {
		JSONArray a = new JSONArray();
		for (Object obj : o.values()) {
			((Collection) a).add(toJSON(obj));
		}
		return a;
	}

	private static JSONObject mapToJSON(Map o) throws JSONException {
		JSONObject obj = new JSONObject();
		Map map = (Map)o;
		Iterator<Map.Entry> i = map.entrySet().iterator();
		while (i.hasNext()) {
			Entry next = i.next();
			obj.put((String) next.getKey(), toJSON(next.getValue()));
		}
		return obj;
	}
}
