package com.sparqr.cx.iam.web.controllers;

import static capital.scalable.restdocs.AutoDocumentation.description;
import static capital.scalable.restdocs.AutoDocumentation.embedded;
import static capital.scalable.restdocs.AutoDocumentation.links;
import static capital.scalable.restdocs.AutoDocumentation.methodAndPath;
import static capital.scalable.restdocs.AutoDocumentation.pathParameters;
import static capital.scalable.restdocs.AutoDocumentation.requestFields;
import static capital.scalable.restdocs.AutoDocumentation.requestParameters;
import static capital.scalable.restdocs.AutoDocumentation.responseFields;
import static capital.scalable.restdocs.AutoDocumentation.sectionBuilder;
import static org.springframework.restdocs.cli.CliDocumentation.curlRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import capital.scalable.restdocs.SnippetRegistry;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparqr.cx.iam.Application;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

@ActiveProfiles("test")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = {Application.class})
@Tag("functional")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class FunctionalRestTest {

  private static final String DB_USERNAME = "db_user";
  private static final String DB_PASSWORD = "db_user_1234";

  private static final PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER =
      new PostgreSQLContainer<>("postgres:14.1")
          .withDatabaseName("sparqr_middleware")
          .withUsername(DB_USERNAME)
          .withPassword(DB_PASSWORD);

  static {
    POSTGRES_SQL_CONTAINER.start();
  }

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected WebApplicationContext webApplicationContext;

  protected MockMvc mockMvc;

  @BeforeAll
  static void setProperties() {
    System.setProperty("DB_URL", POSTGRES_SQL_CONTAINER.getJdbcUrl());
    System.setProperty("DB_USERNAME", DB_USERNAME);
    System.setProperty("DB_PASSWORD", DB_PASSWORD);
  }

  /**
   * @param restDocumentation rest documentation provider
   * @see <a href="https://scacap.github.io/spring-auto-restdocs/">https://scacap.github.io/spring-auto-restdocs/</a>
   */
  @BeforeEach
  void setup(RestDocumentationContextProvider restDocumentation) {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
            .apply(
                MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                    .uris()
                    .withScheme("http")
                    .withHost("localhost")
                    .withPort(8080)
                    .and()
                    .snippets()
                    .withDefaults(
                        curlRequest(),
                        httpRequest(),
                        httpResponse(),
                        requestFields().failOnUndocumentedFields(false),
                        responseFields(),
                        pathParameters().failOnUndocumentedParams(true),
                        requestParameters().failOnUndocumentedParams(true),
                        description(),
                        methodAndPath(),
                        links(),
                        embedded(),
                        sectionBuilder()
                            .snippetNames(
                                SnippetRegistry.AUTO_METHOD_PATH,
                                SnippetRegistry.AUTO_DESCRIPTION,
                                SnippetRegistry.AUTO_PATH_PARAMETERS,
                                SnippetRegistry.AUTO_REQUEST_PARAMETERS,
                                SnippetRegistry.AUTO_REQUEST_FIELDS,
                                SnippetRegistry.HTTP_REQUEST,
                                SnippetRegistry.CURL_REQUEST,
                                SnippetRegistry.HTTP_RESPONSE,
                                SnippetRegistry.AUTO_EMBEDDED,
                                SnippetRegistry.AUTO_LINKS)
                            .skipEmpty(true)
                            .build()))
            .build();
  }

  protected ResultActions perform(
      final MockHttpServletRequestBuilder request, String endpoint, String call) throws Exception {

    return mockMvc
        .perform(request)
        .andDo(
            document(
                endpoint + "/" + call,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
  }

  protected ResultActions performAsync(
      final MockHttpServletRequestBuilder request, String endpoint, String call) throws Exception {
    return performAsync(request, endpoint, call, status().isOk());
  }

  protected ResultActions performAsync(
      final MockHttpServletRequestBuilder request,
      String endpoint,
      String call,
      ResultMatcher resultMatcher)
      throws Exception {

    MvcResult mvcResult =
        perform(request, endpoint, call)
            .andExpect(resultMatcher)
            .andExpect(request().asyncStarted())
            .andDo(document(endpoint + "/" + call, preprocessRequest(prettyPrint())))
            .andReturn();

    return mockMvc
        .perform(asyncDispatch(mvcResult))
        .andDo(document(endpoint + "/" + call, preprocessResponse(prettyPrint())));
  }

  protected String params(Map<String, String> values) {

    return values.keySet().stream()
        .map(key -> key + "=" + values.get(key))
        .collect(Collectors.joining("&", "?", ""));
  }

  protected <T> T getEmbeddedFromResponse(
      String response, String objectName, TypeReference<T> valueTypeRef)
      throws JSONException, JsonProcessingException {

    final JSONObject respObject = new JSONObject(response);

    if (respObject.has("_embedded")) {
      final String embeddedElement =
          respObject.getJSONObject("_embedded").getJSONArray(objectName).toString();
      return objectMapper.readValue(embeddedElement, valueTypeRef);
    }

    return null;
  }


  protected <T> T parseResponse(MockHttpServletResponse response, Class<T> responseClass) {

    try {
      String contentAsString = response.getContentAsString();
      return objectMapper.readValue(contentAsString, responseClass);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected <T> List<T> parseResponse(MockHttpServletResponse response,
      TypeReference<List<T>> responseClass) {

    try {
      String contentAsString = response.getContentAsString();
      return objectMapper.readValue(contentAsString, responseClass);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
