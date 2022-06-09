package com.sparqr.cx.iam.web.controllers;

import static com.sparqr.cx.iam.api.EndpointConstants.PATH_USER_CERTIFICATIONS;
import static com.sparqr.cx.iam.api.EndpointConstants.PVAR_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sparqr.cx.iam.api.dtos.commons.PlatformUserCertificationDto;
import com.sparqr.cx.iam.fixtures.PlatformSsoFixtures;
import com.sparqr.cx.iam.fixtures.PlatformUserCertificationFixtures;
import com.sparqr.cx.iam.mappers.Web2ServiceMapper;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserCertificationRepository;
import com.sparqr.cx.iam.services.PlatformSsoService;
import java.time.LocalDate;
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
public class PlatformUserCertificationFunctionalTest extends FunctionalRestTest {

  private final String ENDPOINT_PATH = "users/certifications";

  @Autowired
  private PlatformSsoService platformSsoService;

  @Autowired
  private Web2ServiceMapper web2ServiceMapper;

  @Autowired
  private PlatformUserCertificationRepository platformUserCertificationRepository;

  private String alias;

  @BeforeEach
  @SneakyThrows
  void init() {

    alias = platformSsoService.createIfNotExists(
        web2ServiceMapper.toPlatformUser(PlatformSsoFixtures.getPlatformUserInboundDto())).get().getAlias();

    cleanup();
  }

  private void cleanup() {
    platformUserCertificationRepository.deleteAll();
  }

  @Test
  @SneakyThrows
  public void shouldCreateUserCertification() {

    PlatformUserCertificationDto platformUserCertificationDto = PlatformUserCertificationDto.builder()
        .name(PlatformUserCertificationFixtures.NAME)
        .credentialId(PlatformUserCertificationFixtures.CREDENTIAL_ID)
        .issuingOrganization(PlatformUserCertificationFixtures.ISSUING_ORGANIZATION)
        .issuedDate(PlatformUserCertificationFixtures.ISSUED_DATE)
        .build();

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders
            .post(PATH_USER_CERTIFICATIONS, alias)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(platformUserCertificationDto));

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "createUserCertification200",
        status().isCreated())
        .andReturn()
        .getResponse();

    PlatformUserCertificationDto result = parseResponse(response, PlatformUserCertificationDto.class);

    assertThat(result.getName()).isEqualTo(PlatformUserCertificationFixtures.NAME);
    assertThat(result.getCredentialId()).isEqualTo(PlatformUserCertificationFixtures.CREDENTIAL_ID);
    assertThat(result.getIssuingOrganization()).isEqualTo(PlatformUserCertificationFixtures.ISSUING_ORGANIZATION);
    assertThat(result.getIssuedDate()).isEqualTo(PlatformUserCertificationFixtures.ISSUED_DATE);
    assertThat(result.getExpirationDate()).isNull();
  }

  @Test
  @SneakyThrows
  public void shouldUpdateUserCertification() {

    String certName = "XXX";
    LocalDate expirationDate = LocalDate.now();
    Long certId = 1001L;

    PlatformUserCertificationDto platformUserCertificationDto = PlatformUserCertificationDto.builder()
        .name(certName)
        .credentialId(PlatformUserCertificationFixtures.CREDENTIAL_ID)
        .issuingOrganization(PlatformUserCertificationFixtures.ISSUING_ORGANIZATION)
        .issuedDate(PlatformUserCertificationFixtures.ISSUED_DATE)
        .build();

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders
            .put(PATH_USER_CERTIFICATIONS + "/{" + PVAR_ID + "}", alias, certId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(platformUserCertificationDto));

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "updateUserCertification200",
        status().isOk())
        .andReturn()
        .getResponse();

    PlatformUserCertificationDto result = parseResponse(response, PlatformUserCertificationDto.class);

    assertThat(result.getName()).isEqualTo(certName);
    assertThat(result.getCredentialId()).isEqualTo(PlatformUserCertificationFixtures.CREDENTIAL_ID);
    assertThat(result.getIssuingOrganization()).isEqualTo(PlatformUserCertificationFixtures.ISSUING_ORGANIZATION);
    assertThat(result.getIssuedDate()).isEqualTo(PlatformUserCertificationFixtures.ISSUED_DATE);
    assertThat(result.getExpirationDate()).isNull();
  }

  @Test
  @SneakyThrows
  public void shouldDeleteUserCertification() {

    Long certId = 1001L;

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders
            .delete(PATH_USER_CERTIFICATIONS + "/{" + PVAR_ID + "}", alias, certId)
            .contentType(MediaType.APPLICATION_JSON);

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "deleteUserCertification204",
        status().isNoContent())
        .andReturn()
        .getResponse();
  }

  @Test
  @SneakyThrows
  public void shouldListUserCertification() {

    String dbAlias = "u1001";

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders
            .get(PATH_USER_CERTIFICATIONS, dbAlias)
            .contentType(MediaType.APPLICATION_JSON);

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "getUserCertifications200",
        status().isOk())
        .andReturn()
        .getResponse();

    List<PlatformUserCertificationDto> result = parseResponse(response,
        new TypeReference<>() {
        });

    assertThat(result.size()).isEqualTo(2);
  }
}
