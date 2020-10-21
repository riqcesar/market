package com.henrique.market;

import com.zaxxer.hikari.HikariDataSource;
import net.minidev.json.JSONValue;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Encoding;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Set;

import static org.dbunit.Assertion.assertEqualsByQuery;

public abstract class AbstractIntegrationMarketTests {

    @Autowired
    private HikariDataSource hikariDataSource;

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

    /**
     * Return a connection to be used at DBUnit
     *
     * @return instance of database connection
     * @throws SQLException          will be thrown if occurs some error perform sql command
     * @throws DatabaseUnitException will be thrown if occurs some error
     */
    protected IDatabaseConnection getIDatabaseConnection() throws SQLException, DatabaseUnitException {
        final DatabaseConnection dbUnitConnection = new DatabaseConnection(Objects.requireNonNull(hikariDataSource.getConnection()));
        final DatabaseConfig dbUnitConnectionConfig = dbUnitConnection.getConfig();
        dbUnitConnectionConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());

        return dbUnitConnection;
    }

    /**
     * Validate data from query database with xml document
     *
     * @param fileName       document with fixtures
     * @param tableName      table that will assert information
     * @param sqlStatement   run to result will be assert
     * @param ignoredColumns columns that will not be considered in the assertion
     * @throws Exception will be thron if occurs some error
     */
    protected void verifyDatasetForTable(final String fileName, final String tableName, final String sqlStatement,
        final String[] ignoredColumns) throws DataSetException, SQLException, DatabaseUnitException {
        final IDatabaseConnection iDatabaseConnection = getIDatabaseConnection();
        assertEqualsByQuery(getDataSet("/datasets/" + fileName), iDatabaseConnection, sqlStatement, tableName,
            ignoredColumns);
        iDatabaseConnection.close();
    }

    private IDataSet getDataSet(final String dataset) throws DataSetException {
        try {
            final InputSource source = new InputSource(getClass().getResourceAsStream(dataset));
            source.setEncoding("UTF-8");
            final FlatXmlProducer producer = new FlatXmlProducer(source, false, true);
            final ReplacementDataSet dataSet = new ReplacementDataSet(new FlatXmlDataSet(producer));
            dataSet.addReplacementObject("[NULL]", null);

            return dataSet;
        } catch (final DataSetException exception) {
            throw new DataSetException("Cannot read the dataset file " + dataset + "!", exception);
        }
    }

}

