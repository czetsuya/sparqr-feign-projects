package com.sparqr.cx.iam.web.controllers;

import static com.sparqr.cx.iam.api.EndpointConstants.PATH_USER_SKILLS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sparqr.cx.iam.fixtures.PlatformSsoFixtures;
import com.sparqr.cx.iam.mappers.Web2ServiceMapper;
import com.sparqr.cx.iam.services.PlatformSsoService;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Slf4j
public class PlatformUserSkillFunctionalTest extends FunctionalRestTest {

  private final String ENDPOINT_PATH = "users/skills";

  @Autowired
  private PlatformSsoService platformSsoService;

  @Autowired
  private Web2ServiceMapper web2ServiceMapper;

  private String alias;

  @BeforeEach
  @SneakyThrows
  void init() {

    alias = platformSsoService.createIfNotExists(
        web2ServiceMapper.toPlatformUser(PlatformSsoFixtures.getPlatformUserInboundDto())).get().getAlias();
  }

  @Test
  @SneakyThrows
  public void shouldCreateUserCertification() {

    Set<String> skills = Set.of("Java", "Spring", "Microservice", "AWS");

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders
            .post(PATH_USER_SKILLS, alias)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(skills));

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "createUserSkills200",
        status().isOk())
        .andReturn()
        .getResponse();

    Set result = parseResponse(response, Set.class);

    assertThat(result.size()).isEqualTo(4);
  }

  @Test
  @SneakyThrows
  public void shouldGetUserSkills() {

    String userAlias = "u1001";

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get(PATH_USER_SKILLS, userAlias)
            .contentType(MediaType.APPLICATION_JSON);

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "getUserSkills200",
        status().isOk())
        .andReturn()
        .getResponse();

    Set<String> result = parseResponse(response, Set.class);

    assertThat(result).hasSize(2);
  }
}
