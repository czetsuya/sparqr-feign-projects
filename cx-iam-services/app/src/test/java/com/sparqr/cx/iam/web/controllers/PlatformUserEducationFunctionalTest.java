package com.sparqr.cx.iam.web.controllers;

import static com.sparqr.cx.iam.api.EndpointConstants.PATH_USER_EDUCATIONS;
import static com.sparqr.cx.iam.api.EndpointConstants.PVAR_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sparqr.cx.iam.api.dtos.commons.PlatformUserEducationDto;
import com.sparqr.cx.iam.fixtures.PlatformSsoFixtures;
import com.sparqr.cx.iam.fixtures.PlatformUserEducationFixtures;
import com.sparqr.cx.iam.mappers.Web2ServiceMapper;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserEducationRepository;
import com.sparqr.cx.iam.services.PlatformSsoService;
import java.util.List;
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
public class PlatformUserEducationFunctionalTest extends FunctionalRestTest {

  private final String ENDPOINT_PATH = "users/educations";

  @Autowired
  private PlatformSsoService platformSsoService;

  @Autowired
  private Web2ServiceMapper web2ServiceMapper;

  @Autowired
  private PlatformUserEducationRepository platformUserEducationRepository;

  private String alias;

  @BeforeEach
  @SneakyThrows
  void init() {
    alias = platformSsoService.createIfNotExists(
        web2ServiceMapper.toPlatformUser(PlatformSsoFixtures.getPlatformUserInboundDto())).get().getAlias();

    cleanup();
  }

  private void cleanup() {
    platformUserEducationRepository.deleteAll();
  }

  @Test
  @SneakyThrows
  public void shouldCreateUserEducation() {

    PlatformUserEducationDto platformUserEducationDto = PlatformUserEducationDto.builder()
        .school(PlatformUserEducationFixtures.SCHOOL).degree(PlatformUserEducationFixtures.DEGREE)
        .yearOfGraduation(PlatformUserEducationFixtures.YEAR_OF_GRADUATION).build();

    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(PATH_USER_EDUCATIONS, alias)
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(platformUserEducationDto));

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "createUserEducation200",
        status().isCreated()).andReturn().getResponse();

    PlatformUserEducationDto result = parseResponse(response, PlatformUserEducationDto.class);

    assertThat(result.getDegree()).isEqualTo(PlatformUserEducationFixtures.DEGREE);
    assertThat(result.getSchool()).isEqualTo(PlatformUserEducationFixtures.SCHOOL);
    assertThat(result.getYearOfGraduation()).isEqualTo(PlatformUserEducationFixtures.YEAR_OF_GRADUATION);
  }

  @Test
  @SneakyThrows
  public void shouldUpdateUserEducation() {

    Long educationId = 1001L;
    Integer yearOfGraduation = 2006;
    String degree = "BA Culinary Arts";

    PlatformUserEducationDto platformUserEducationDto = PlatformUserEducationDto.builder()
        .school(PlatformUserEducationFixtures.SCHOOL).degree(degree).yearOfGraduation(yearOfGraduation).build();

    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(
            PATH_USER_EDUCATIONS + "/{" + PVAR_ID + "}", alias, educationId).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(platformUserEducationDto));

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "updateUserEducation200",
        status().isOk()).andReturn().getResponse();

    PlatformUserEducationDto result = parseResponse(response, PlatformUserEducationDto.class);

    assertThat(result.getDegree()).isEqualTo(degree);
    assertThat(result.getSchool()).isEqualTo(PlatformUserEducationFixtures.SCHOOL);
    assertThat(result.getYearOfGraduation()).isEqualTo(yearOfGraduation);
  }

  @Test
  @SneakyThrows
  public void shouldDeleteUserEducation() {

    Long educationId = 1001L;

    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
        PATH_USER_EDUCATIONS + "/{" + PVAR_ID + "}", alias, educationId).contentType(MediaType.APPLICATION_JSON);

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "deleteUserEducation204",
        status().isNoContent()).andReturn().getResponse();
  }

  @Test
  @SneakyThrows
  public void shouldListUserEducation() {

    String dbAlias = "u1001";

    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(PATH_USER_EDUCATIONS, dbAlias)
        .contentType(MediaType.APPLICATION_JSON);

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "getUserEducations200",
        status().isOk()).andReturn().getResponse();

    List<PlatformUserEducationDto> result = parseResponse(response, new TypeReference<>() {
    });

    log.debug("educations={}", result);

    assertThat(result.size()).isEqualTo(1);
  }
}
