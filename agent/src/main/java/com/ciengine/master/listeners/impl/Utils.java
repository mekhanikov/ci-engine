package com.ciengine.master.listeners.impl;

import com.ciengine.common.EnvironmentVariables;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by emekhanikov on 24.11.2016.
 */
public class Utils {
    public static EnvironmentVariables merge(EnvironmentVariables environmentVariablesFromEvent,
                                       EnvironmentVariables environmentVariables)
    {
        EnvironmentVariables environmentVariablesMerged = new EnvironmentVariables();
        if (environmentVariablesFromEvent != null && environmentVariablesFromEvent.getProperties() != null) {
            environmentVariablesMerged.addProperties(environmentVariablesFromEvent.getProperties());
        }
        if (environmentVariables != null && environmentVariables.getProperties() != null) {
            environmentVariablesMerged.addProperties(environmentVariables.getProperties());
        }

        return environmentVariablesMerged;
    }

    public static  String makeString(EnvironmentVariables merge)
    {
        StringBuilder stringBuilder = new StringBuilder();
        if (merge != null && merge.getProperties() != null) {
            for (Map.Entry<String, Object> kvEntry : merge.getProperties().entrySet()) {
                stringBuilder.append(kvEntry.getKey());
                stringBuilder.append("=");
                stringBuilder.append(kvEntry.getValue());
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public static  EnvironmentVariables getEnvironmentVariables(String inputParams) {
        EnvironmentVariables environmentVariables = new EnvironmentVariables();
        if (!StringUtils.isEmpty(inputParams)) {
            String[] lines = inputParams.split("\n");
            for (String line : lines) {
                if (!StringUtils.isEmpty(line)) {
                    String[] keyValue = line.split("=");
                    String key = keyValue.length > 0 ? keyValue[0] : "";
                    String value = keyValue.length > 1 ? keyValue[1] : "";
                    environmentVariables.addProperty(key, value);
                }
            }
        }
        return environmentVariables;
    }
}
