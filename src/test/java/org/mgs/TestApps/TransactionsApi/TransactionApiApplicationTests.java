package org.mgs.TestApps.TransactionsApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransactionApiApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private TransactionFixture fixtures;
    static private ObjectMapper objectMapper = new ObjectMapper();
    private final String SORT_API_PATH = "/api/transactions/sort";

    @Before
    public void init() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.fixtures = new TransactionFixture();
    }

    @Test
    public void testSortHappyPath() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .request(HttpMethod.POST, SORT_API_PATH)
            .contentType(MediaTypes.HAL_JSON)
            .content(objectMapper.writeValueAsString(fixtures.jumbledList)))
            .andDo(print()).andReturn();

        assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());

        List<Transaction> sorted = jsonToListTransactions(result.getResponse().getContentAsString());
        assertEquals(fixtures.masssiveT.getValue(), sorted.get(0).getValue());
        assertEquals(fixtures.smallT.getValue(), sorted.get(4).getValue());
    }

    @Test
    public void testDateParseError() throws Exception {
        String invalidDate = "[{\"date\":\"30-02-2018\",\"value\":10.00,\"currency\":\"GBP\",\"sourceAccount\":\"x\",\"destAccount\":\"y\"}]";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .request(HttpMethod.POST, SORT_API_PATH)
            .contentType(MediaTypes.HAL_JSON)
            .content(invalidDate)).andDo(print()).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResolvedException().toString().contains("Invalid date format supplied. Supported date format DD-MM-YYYY"));
    }

    @Test
    public void testNegativeAmountError() throws Exception {
        String invalidAmount = "[{\"date\":\"21-02-2018\",\"value\":-10.00,\"currency\":\"GBP\",\"sourceAccount\":\"x\",\"destAccount\":\"y\"}]";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .request(HttpMethod.POST, SORT_API_PATH)
            .contentType(MediaTypes.HAL_JSON)
            .content(invalidAmount)).andDo(print()).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResolvedException().toString().contains("Transaction amount must be positive"));
    }

    private static List<Transaction> jsonToListTransactions(String response)
        throws HttpMessageNotReadableException, IOException {
        return objectMapper.readValue(response.getBytes(), new TypeReference<List<Transaction>>(){});
    }

}
