package com.example.mercadofrescos.integrados;

import com.example.mercadofrescos.repository.IInboundOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class InboundOrderControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IInboundOrderRepo repoOrder;

    void saveOrder_retunsOrder_whenSuccess() throws Exception {

    }
}
