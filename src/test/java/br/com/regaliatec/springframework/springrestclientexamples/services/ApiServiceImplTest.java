package br.com.regaliatec.springframework.springrestclientexamples.services;

import br.com.regaliatec.springframework.api.domain.Data;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiServiceImplTest extends TestCase {

    @Autowired
    ApiService apiService;

    @Test
    public void testGetData() throws Exception {

        Data data = apiService.getData(81);

        assertEquals("PE", data.getState());
        assertEquals(98,data.getCities().size());
    }
}