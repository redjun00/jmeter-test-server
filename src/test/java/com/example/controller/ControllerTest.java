package com.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ControllerTest{

    @Autowired
    private WebApplicationContext context;

    protected MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void fileupload() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("file", "logo.png", MediaType.MULTIPART_FORM_DATA_VALUE, "logo".getBytes());

        // when
        ResultActions result = this.mockMvc.perform(
                fileUpload("/fileupload")
                        .file(multipartFile)
        ).andDo(print());

        // then
        result.andExpect(status().isOk());
    }

    @Test
    public void testRandomPathVariable() throws Exception {
        this.mockMvc
                .perform(
                        get("/random/{gender}", "male")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testRandomRequestParam() throws Exception {
        this.mockMvc
                .perform(
                        get("/random")
                                .param("gender", "female")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAsset() throws Exception{
        String numberToGet = "1";
        MvcResult result = this.mockMvc
                .perform(
                     get("/{id}", numberToGet)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.asset.id").value(numberToGet))
                .andReturn();
    }
    //TODO 참고해서 만들어보자. https://www.programcreek.com/java-api-examples/index.php?api=org.springframework.test.web.servlet.result.MockMvcResultMatchers

}
