package com.henrique.market;

import net.minidev.json.JSONValue;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public abstract class AbstractIntegrationMarketTests {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Load file from file system and return as {@link String}
     *
     * @param path of JSON file
     *
     * @return a JSON in String format
     */
    protected String getJsonFileAsString(final String path) {
        final InputStream resourceAsStream = this.getClass().getResourceAsStream(String.format("/%s", path));
        return JSONValue.parse(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8)).toString();
    }

    /**
     * Validate data from broker json message with a json document
     *
     * @param expectedJsonFile json document with expected results
     * @param jsonResult json string from broker message
     * @param ignoredNodes nodes to be ignores in the json
     */
    protected void verifyMessageJson(String expectedJsonFile, String jsonResult, final Set<String> ignoredNodes) {
        try {
            final Customization[] customizations = ignoredNodes.stream().map(node -> new Customization(node, (o1, o2) -> true)).toArray(
                Customization[]::new);
            JSONAssert.assertEquals(getJsonFileAsString(expectedJsonFile), jsonResult,
                new CustomComparator(JSONCompareMode.NON_EXTENSIBLE, customizations));
        } catch (JSONException e) {
            throw new RuntimeException("Error in JsonAssert", e);
        }
    }

}

