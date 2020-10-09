package com.henrique.market;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {ProductTests.TestcontainersConfiguration.class})
class ProductTests extends AbstractIntegrationMarketTests {

    @Container
    private static final MySQLContainer MY_SQL_CONTAINER = (MySQLContainer) new MySQLContainer("mysql:8")
        .withUsername("admin")
        .withPassword("admin")
        .withDatabaseName("market")
        .withExposedPorts(3306);
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(scripts = {"classpath:sqls/clear"}, executionPhase = BEFORE_TEST_METHOD)
    void givenProduct_whenReceived_shouldSaveAndReturnProductSaved() throws Exception {
        final String payload = getJsonFileAsString("expected/product/add_product");

        final MvcResult mvcResult = mockMvc.perform(post("/product/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isOk()).andReturn();

        verifyMessageJson("mock/product/add_product", mvcResult.getResponse().getContentAsString(), Set.of("id"));
    }

    @Test
    void givenProductWithoutName_WhenReceived_shouldReturnError400() throws Exception {
        final String payload = getJsonFileAsString("expected/product/add_product_without_name");

        mockMvc.perform(post("/product/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isBadRequest());
    }

    @Test
    void givenProductWithoutBrand_WhenReceived_shouldReturnError400() throws Exception {
        final String payload = getJsonFileAsString("expected/product/add_product_without_brand");

        mockMvc.perform(post("/product/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isBadRequest());
    }

    @Test
    void givenProductWithoutQuantity_WhenReceived_shouldReturnError400() throws Exception {
        final String payload = getJsonFileAsString("expected/product/add_product_without_quantity");

        mockMvc.perform(post("/product/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isBadRequest());
    }

    @Test
    void givenProductWithoutPrice_WhenReceived_shouldReturnError400() throws Exception {
        final String payload = getJsonFileAsString("expected/product/add_product_without_price");

        mockMvc.perform(post("/product/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isBadRequest());
    }

    @Test
    void givenProductPriceWithLetters_WhenReceived_shouldReturnError400() throws Exception {
        final String payload = getJsonFileAsString("expected/product/add_product_price_with_letters");

        mockMvc.perform(post("/product/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({@Sql(scripts = {"classpath:sqls/clear"}, executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = {"classpath:sqls/data"}, executionPhase = BEFORE_TEST_METHOD)})
    void givenGetProducts_WhenReceived_ShouldReturnListOfProducts() throws Exception {

        final MvcResult mvcResult = mockMvc.perform(get("/product/filter"))
            .andExpect(status().isOk()).andReturn();

        verifyMessageJson("mock/product/get_all_products", mvcResult.getResponse().getContentAsString(), Set.of());
    }

    @Test
    @SqlGroup({@Sql(scripts = {"classpath:sqls/clear"}, executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = {"classpath:sqls/data"}, executionPhase = BEFORE_TEST_METHOD)})
    void givenProductAlreadyExisted_whenReceived_shouldIncreaseQuantityAndReturnProductSaved() throws Exception {
        final String payload = getJsonFileAsString("expected/product/add_product_already_existed");

        final MvcResult mvcResult = mockMvc.perform(post("/product/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isOk()).andReturn();

        verifyMessageJson("mock/product/add_product_already_existed", mvcResult.getResponse().getContentAsString(), Set.of("id"));
    }

    @Test
    @SqlGroup({@Sql(scripts = {"classpath:sqls/clear"}, executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = {"classpath:sqls/data"}, executionPhase = BEFORE_TEST_METHOD)})
    void givenGetProductsByPriceMinAndPriceMax_WhenReceived_ShouldReturnListOfProductsFilteredByPriceRange() throws Exception {

        final MvcResult mvcResult = mockMvc.perform(get("/product/filter")
            .queryParam("priceMin", "10")
            .queryParam("priceMax", "13"))
            .andExpect(status().isOk()).andReturn();

        verifyMessageJson("mock/product/get_products_between_priceMin_PriceMax", mvcResult.getResponse().getContentAsString(), Set.of());
    }

    @Test
    @SqlGroup({@Sql(scripts = {"classpath:sqls/clear"}, executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = {"classpath:sqls/data"}, executionPhase = BEFORE_TEST_METHOD)})
    void givenGetProductsFilteredByNameLike_WhenReceived_ShouldReturnListOfProductsFilteredByNameLike() throws Exception {

        final MvcResult mvcResult = mockMvc.perform(get("/product/filter")
            .queryParam("name", "rro"))
            .andExpect(status().isOk()).andReturn();

        verifyMessageJson("mock/product/get_products_name_like", mvcResult.getResponse().getContentAsString(), Set.of());
    }

    @Test
    @SqlGroup({@Sql(scripts = {"classpath:sqls/clear"}, executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = {"classpath:sqls/data"}, executionPhase = BEFORE_TEST_METHOD)})
    void givenGetProductsFilteredByQuantityGreaterThan_WhenReceived_ShouldReturnListOfProductsFilteredByQuantity() throws Exception {

        final MvcResult mvcResult = mockMvc.perform(get("/product/filter")
            .queryParam("quantity", "35"))
            .andExpect(status().isOk()).andReturn();

        verifyMessageJson("mock/product/get_all_products_quantity_greater_than", mvcResult.getResponse().getContentAsString(), Set.of());
    }

    @Test
    @SqlGroup({@Sql(scripts = {"classpath:sqls/clear"}, executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = {"classpath:sqls/data"}, executionPhase = BEFORE_TEST_METHOD)})
    void givenGetProductsFilteredByBrandLike_WhenReceived_ShouldReturnListOfProductsFilteredByBrand() throws Exception {

        final MvcResult mvcResult = mockMvc.perform(get("/product/filter")
            .queryParam("brand", "ami"))
            .andExpect(status().isOk()).andReturn();

        verifyMessageJson("mock/product/get_products_brand_like", mvcResult.getResponse().getContentAsString(), Set.of());
    }

    @Test
    @SqlGroup({@Sql(scripts = {"classpath:sqls/clear"}, executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = {"classpath:sqls/data"}, executionPhase = BEFORE_TEST_METHOD)})
    void givenGetProductsFilteredByProductTypeThatNotExists_WhenReceived_ShouldReturnError400() throws Exception {

        final MvcResult mvcResult = mockMvc.perform(get("/product/filter")
            .queryParam("productType", "TEST"))
            .andExpect(status().isBadRequest()).andReturn();
    }

    static class TestcontainersConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(final ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                String.format("spring.datasource.username=%s", MY_SQL_CONTAINER.getUsername()),
                String.format("spring.datasource.password=%s", MY_SQL_CONTAINER.getPassword()),
                String.format("spring.datasource.url=%s", MY_SQL_CONTAINER.getJdbcUrl())
                                 ).applyTo(configurableApplicationContext.getEnvironment());
        }

    }

}
