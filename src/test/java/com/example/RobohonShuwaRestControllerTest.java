package com.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import org.junit.Test;
import com.example.domain.ShuwaApiResult;


@RunWith(MockitoJUnitRunner.class)
public class RobohonShuwaRestControllerTest{
    @Test
    public void Test(){
        RestTemplate restTemplate = new RestTemplate();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(requestTo("http://localhost:1598/api/customers/test"))
            .andRespond(withSuccess("{\"label\":\"1\", \"prob\":\"2\"}", MediaType.APPLICATION_JSON_UTF8));

        ShuwaApiResult shuwaApiResult = restTemplate.getForObject("http://localhost:1598/api/customers/test", ShuwaApiResult.class);

        assertThat(shuwaApiResult.getLabel(), is(1));
        assertThat(shuwaApiResult.getProb(), is(2));
    }
}
