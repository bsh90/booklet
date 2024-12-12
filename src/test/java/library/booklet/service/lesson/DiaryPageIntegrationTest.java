package library.booklet.service.lesson;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.entity.DiaryPageEntity;
import library.booklet.repository.DiaryPageRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiaryPageIntegrationTest {

    @Autowired
    DiaryPageRepository diaryPageRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    DiaryPageEntity diaryPage;

    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest");

    @BeforeAll
    static void beforeAll() {
            mysql.start();
        }

    @AfterAll
    static void afterAll() {
            mysql.stop();
        }

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto",() -> "create");
    }

    @BeforeEach
    void setUp() {
        DiaryPageEntity diaryPageEntity = new DiaryPageEntity();
        diaryPageEntity.setWrittenDate(LocalDate.of(2024, 12, 10));
        diaryPageEntity.setEntry("entry");
        diaryPage = diaryPageRepository.saveAndFlush(diaryPageEntity);
    }

    @AfterEach
    public void afterEach(){
        diaryPageRepository.deleteAll();
        }

    @Test
    void happyPath_createDiaryPage() {
        DiaryPageDTO diaryPageDTO = new DiaryPageDTO(LocalDate.of(2024, 12, 8), "entry1");
        ResponseEntity<DiaryPageEntity> response = testRestTemplate.postForEntity("/createDiaryPage", diaryPageDTO,
                DiaryPageEntity.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getWrittenDate()).isEqualTo(diaryPageDTO.getWrittenDate());
        assertThat(response.getBody().getEntry()).isEqualTo(diaryPageDTO.getEntry());
    }

    @Test
    void happyPath_deleteDiaryPage() {
        ResponseEntity<?> response = testRestTemplate.exchange("/deleteDiaryPages"+"?id="+diaryPage.getId(),
                HttpMethod.DELETE, getHttpEntity(), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @NotNull
    private static HttpEntity<String> getHttpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        return requestEntity;
    }


    @Test
    void notFound_deleteDiaryPage() {
        ResponseEntity<?> response = testRestTemplate.exchange("/deleteDiaryPages"+"?id=451351",
                HttpMethod.DELETE, getHttpEntity(), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("DiaryPage id not found - 451351");
    }

    @Test
    void happyPath_getDiaryPage() {
        DiaryPageDTO diaryPageDTO = new DiaryPageDTO(LocalDate.of(2024, 12, 10), "entry");

        ResponseEntity<?> response = testRestTemplate.exchange("/getDiaryPage"+"?id="+diaryPage.getId(),
                HttpMethod.GET, getHttpEntity(), DiaryPageDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(diaryPageDTO);
    }

    @Test
    void notFound_getDiaryPage() {
        ResponseEntity<?> response = testRestTemplate.exchange("/getDiaryPage"+"?id=451352",
                HttpMethod.GET, getHttpEntity(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("DiaryPage id not found - 451352");
    }

    @Test
    void happyPath_getDiaryOfDate() {
        DiaryPageDTO diaryPageDTO = new DiaryPageDTO(LocalDate.of(2024, 12, 10), "entry");
        ResponseEntity<List<DiaryPageDTO>> response = testRestTemplate.exchange("/getDiaryOfDate"+"?writtenDate=2024-12-10",
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<DiaryPageDTO>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(List.of(diaryPageDTO));
    }

    @Test
    void emptyList_getDiaryOfDate() {
        ResponseEntity<List<DiaryPageDTO>> response = testRestTemplate.exchange("/getDiaryOfDate"+"?writtenDate=2024-12-11",
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<DiaryPageDTO>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(Collections.emptyList());
    }

    @Test
    void happyPath_getAllDiaryPages() {
        DiaryPageDTO diaryPageDTO = new DiaryPageDTO(LocalDate.of(2024, 12, 10), "entry");
        ResponseEntity<List<DiaryPageDTO>> response = testRestTemplate.exchange("/getAllDiaryPages",
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<DiaryPageDTO>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(List.of(diaryPageDTO));
    }

    @Test
    void emptyList_getAllDiaryPages() {
        testRestTemplate.exchange("/deleteDiaryPages"+"?id="+diaryPage.getId(),
                HttpMethod.DELETE, getHttpEntity(), String.class);
        ResponseEntity<List<DiaryPageDTO>> response = testRestTemplate.exchange("/getAllDiaryPages",
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<DiaryPageDTO>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(Collections.emptyList());
    }

    @Test
    void happyPath_getAllDiaryPagesWithId() {
        ResponseEntity<List<DiaryPageEntity>> response = testRestTemplate.exchange("/getAllDiaryPagesWithId",
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<DiaryPageEntity>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(List.of(diaryPage));
    }

    @Test
    void emptyList_getAllDiaryPagesWithId() {
        testRestTemplate.exchange("/deleteDiaryPages"+"?id="+diaryPage.getId(),
                HttpMethod.DELETE, getHttpEntity(), String.class);
        ResponseEntity<List<DiaryPageEntity>> response = testRestTemplate.exchange("/getAllDiaryPagesWithId",
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<DiaryPageEntity>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(Collections.emptyList());
    }

    @Test
    void happyPath_sortACSDiaryPageBasedOnWrittenDate() {
        DiaryPageDTO diaryPageDTO1 = new DiaryPageDTO(LocalDate.of(2024, 12, 8), "entry1");
        ResponseEntity<DiaryPageEntity> response1 = testRestTemplate.postForEntity("/createDiaryPage", diaryPageDTO1,
                DiaryPageEntity.class);
        DiaryPageDTO diaryPageDTO2 = new DiaryPageDTO(LocalDate.of(2024, 12, 9), "entry2");
        ResponseEntity<DiaryPageEntity> response2 = testRestTemplate.postForEntity("/createDiaryPage", diaryPageDTO2,
                DiaryPageEntity.class);
        List<DiaryPageEntity> diaryPageEntityList = List.of(response1.getBody(), response2.getBody(), diaryPage);

        ResponseEntity<List<DiaryPageEntity>> response = testRestTemplate.exchange("/sortACSDiaryPageBasedOnWrittenDate",
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<DiaryPageEntity>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(diaryPageEntityList);
    }

    @Test
    void happyPath_requestDiaryPagesSortByWrittenDate() {
        DiaryPageDTO diaryPageDTO1 = new DiaryPageDTO(LocalDate.of(2024, 12, 8), "entry1");
        testRestTemplate.postForEntity("/createDiaryPage", diaryPageDTO1, DiaryPageEntity.class);
        DiaryPageDTO diaryPageDTO2 = new DiaryPageDTO(LocalDate.of(2024, 12, 9), "entry2");
        testRestTemplate.postForEntity("/createDiaryPage", diaryPageDTO2, DiaryPageEntity.class);
        List<DiaryPageEntity> diaryPageEntityList = List.of(diaryPage);

        ResponseEntity<List<DiaryPageEntity>> response = testRestTemplate.exchange(
                "/requestDiaryPages"+"?pageNumber=1"+"&pageSize=2",
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<DiaryPageEntity>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(diaryPageEntityList);
    }
}
