package kr.co.polycube.backendtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import kr.co.polycube.backendtest.batch.LottoBatchConfiguration;
import kr.co.polycube.backendtest.filter.SpecialCharacterFilter;
import kr.co.polycube.backendtest.model.Winner;
import kr.co.polycube.backendtest.service.WinnerService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class BackendTestApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(BackendTestApplicationTests.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private JobLauncher jobLauncher;
    
    @Autowired
    private WinnerService winnerService;

    @Autowired
    private LottoBatchConfiguration lottoBatchConfiguration;


    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                                      .addFilters(new SpecialCharacterFilter())  // 필터 추가
                                      .build();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }
    
    @Test
    @Order(1)
    void testInvalidRequest() throws Exception {
        // 잘못된 요청 테스트
        String invalidUserJson = "{\"id\": 1}";
        MvcResult result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent", "MockMvcTestAgent")
                .content(invalidUserJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("HTTP Status : 400 " + content);
        logger.info("testInvalidRequest passed");
    }

    @Test
    @Order(2)
    void testInvalidEndpoint() throws Exception {
        // 존재하지 않는 API 호출 테스트
        MvcResult result = mockMvc.perform(get("/non-existing-endpoint")
                .header("User-Agent", "MockMvcTestAgent"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.reason").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("HTTP Status : 404 " + content);
        logger.info("testInvalidEndpoint passed");
    }

    @Test
    @Order(3)
    void testRegisterUser() throws Exception {
        // 유저 등록 테스트
        String userJson = "{\"id\": 1, \"name\": \"Lee Kang Yong\"}";
        MvcResult result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent", "MockMvcTestAgent")
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Object json = objectMapper.readValue(content, Object.class);
        String formattedContent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        System.out.println(formattedContent);  // User JSON response
        logger.info("testRegisterUser passed");
    }

    @Test
    @Order(4)
    void testGetUser() throws Exception {
        // 유저 조회 테스트
        MvcResult result = mockMvc.perform(get("/users/1")
                .header("User-Agent", "MockMvcTestAgent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Lee Kang Yong"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Object json = objectMapper.readValue(content, Object.class);
        String formattedContent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        System.out.println(formattedContent);  // User JSON response
        logger.info("testGetUser passed");
    }

    @Test
    @Order(5)
    void testUpdateUser() throws Exception {
        // 유저 업데이트 테스트
        String updateJson = "{\"name\": \"Kim Min Soo\"}";
        MvcResult result = mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent", "MockMvcTestAgent")
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Kim Min Soo"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Object json = objectMapper.readValue(content, Object.class);
        String formattedContent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        System.out.println(formattedContent);  // Updated JSON response
        logger.info("testUpdateUser passed");
    }

    @Test
    @Order(6)
    void testFilterSpecialCharacters() throws Exception {
        // 특수 문자 필터 테스트
        MvcResult result = mockMvc.perform(get("/users/1?name=test!!")
                .header("User-Agent", "MockMvcTestAgent"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason").value("Invalid characters in query string"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("HTTP Status : 400 " + content);
        logger.info("testFilterSpecialCharacters passed");
    }
    
    @Test
    @Order(7)
    void testGenerateLottoNumbers() throws Exception {
        MvcResult result = mockMvc.perform(post("/lottos")
                .contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent", "MockMvcTestAgent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numbers").isArray())
                .andExpect(jsonPath("$.numbers.length()").value(6))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Object json = objectMapper.readValue(content, Object.class);
        String formattedContent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        System.out.println(formattedContent);  
        logger.info("testGenerateLottoNumbers passed");
    }
    
    @Test
    @Order(8)
    @DirtiesContext
    void testEvaluateAndBatchJob() throws Exception {
        // 초기 우승자 평가 및 검증
        winnerService.evaluateWinners();
        List<Winner> initialWinners = winnerService.getAllWinners();

        assertNotNull(initialWinners);
        assertTrue(initialWinners.size() > 0, "There should be at least one winner");

        // 배치 작업 실행
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(lottoBatchConfiguration.lottoJob(), jobParameters);
        assertNotNull(jobExecution);

        // 배치 작업 결과 검증
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus(), "Batch job should complete successfully");

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            LocalDateTime startTime = stepExecution.getStartTime();
            LocalDateTime endTime = stepExecution.getEndTime();
            assertNotNull(startTime, "Step start time should not be null");
            assertNotNull(endTime, "Step end time should not be null");
        }

        // 배치 작업 후 우승자 검증
        List<Winner> winners = winnerService.getAllWinners();
        assertNotNull(winners, "Winners list should not be null");
        assertTrue(winners.size() > 0, "Winners list should not be empty");
    }
    
}