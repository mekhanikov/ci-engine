package com.ciengine.common;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class EnvironmentVariables
{
	private Map<String, Object> map = new HashMap();
	public void addProperty(String key, String value)
	{
		map.put(key, value);
	}

	public String getProperty(String key)
	{
		return (String) map.get(key);
	}

	public void addProperties(Map<String, Object> source)
	{
		map.putAll(source);
	}

	public Map<String, Object> getProperties() {
		return new HashMap<>(map);
	}

	@Override
	public String toString() {
		return "EnvironmentVariables{" +
				"map=" + map +
				'}';
	}
}
