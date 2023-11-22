package com.os.services.contract;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContractServiceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContractServiceApplicationTests
{

    @Test
    public void contextLoads()
    {
        
    }

}
