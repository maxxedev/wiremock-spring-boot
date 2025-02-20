package usecases;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

@SpringBootTest
@EnableWireMock(@ConfigureWireMock(name = "mywiremock", registerSpringBean = true))
class AutowireNamedWireMockServerTest {

  @Qualifier("mywiremock")
  @Autowired
  private WireMockServer wireMockServer;

  @Value("${wiremock.server.baseUrl}")
  private String wiremockUrl;

  @Test
  void returnsTodos() {
    this.wireMockServer.stubFor(get("/ping").willReturn(aResponse().withStatus(200)));

    RestAssured.when().get(this.wiremockUrl + "/ping").then().statusCode(200);
  }
}
