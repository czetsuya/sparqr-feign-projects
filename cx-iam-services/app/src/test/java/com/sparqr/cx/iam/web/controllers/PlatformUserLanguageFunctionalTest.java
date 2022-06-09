package com.sparqr.cx.iam.web.controllers;

import static com.sparqr.cx.iam.api.EndpointConstants.PATH_USER_LANGUAGES;
import static com.sparqr.cx.iam.api.EndpointConstants.PVAR_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sparqr.cx.iam.api.commons.LanguageLevelEnum;
import com.sparqr.cx.iam.api.dtos.commons.PlatformUserLanguageDto;
import com.sparqr.cx.iam.fixtures.PlatformSsoFixtures;
import com.sparqr.cx.iam.fixtures.PlatformUserLanguageFixtures;
import com.sparqr.cx.iam.mappers.Web2ServiceMapper;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserLanguageRepository;
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
public class PlatformUserLanguageFunctionalTest extends FunctionalRestTest {

  private final String ENDPOINT_PATH = "users/languages";

  @Autowired
  private PlatformSsoService platformSsoService;

  @Autowired
  private Web2ServiceMapper web2ServiceMapper;

  @Autowired
  private PlatformUserLanguageRepository platformUserLanguageRepository;

  private String alias;

  @BeforeEach
  @SneakyThrows
  void init() {
    alias = platformSsoService.createIfNotExists(
        web2ServiceMapper.toPlatformUser(PlatformSsoFixtures.getPlatformUserInboundDto())).get().getAlias();

    cleanup();
  }

  private void cleanup() {
    platformUserLanguageRepository.deleteAll();
  }

  @Test
  @SneakyThrows
  public void shouldCreateUserLanguage() {

    PlatformUserLanguageDto platformUserLanguageDto = PlatformUserLanguageDto.builder()
        .languageCode(PlatformUserLanguageFixtures.LANGUAGE_CODE)
        .languageLevel(PlatformUserLanguageFixtures.LANGUAGE_LEVEL)
        .build();

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders
            .post(PATH_USER_LANGUAGES, alias)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(platformUserLanguageDto));

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "createUserLanguage200",
        status().isCreated())
        .andReturn()
        .getResponse();

    PlatformUserLanguageDto result = parseResponse(response, PlatformUserLanguageDto.class);

    assertThat(result.getLanguageCode()).isEqualTo(PlatformUserLanguageFixtures.LANGUAGE_CODE);
    assertThat(result.getLanguageLevel()).isEqualTo(PlatformUserLanguageFixtures.LANGUAGE_LEVEL);
  }

  @Test
  @SneakyThrows
  public void shouldUpdateUserLanguage() {

    Long languageId = 1002L;
    String languageCode = "TL";
    LanguageLevelEnum languageLevel = LanguageLevelEnum.PROFESSIONAL_WORKING;

    PlatformUserLanguageDto platformUserLanguageDto = PlatformUserLanguageDto.builder()
        .languageCode(languageCode)
        .languageLevel(languageLevel)
        .build();

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders
            .put(PATH_USER_LANGUAGES + "/{" + PVAR_ID + "}", alias, languageId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(platformUserLanguageDto));

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "updateUserLanguage200",
        status().isOk())
        .andReturn()
        .getResponse();

    PlatformUserLanguageDto result = parseResponse(response, PlatformUserLanguageDto.class);

    assertThat(result.getLanguageCode()).isEqualTo(languageCode);
    assertThat(result.getLanguageLevel()).isEqualTo(languageLevel);
  }

  @Test
  @SneakyThrows
  public void shouldDeleteUserLanguage() {

    Long languageId = 1001L;

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders
            .delete(PATH_USER_LANGUAGES + "/{" + PVAR_ID + "}", alias, languageId)
            .contentType(MediaType.APPLICATION_JSON);

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "deleteUserLanguage204",
        status().isNoContent())
        .andReturn()
        .getResponse();
  }

  @Test
  @SneakyThrows
  public void shouldListUserLanguage() {

    String dbAlias = "u1001";

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders
            .get(PATH_USER_LANGUAGES, dbAlias)
            .contentType(MediaType.APPLICATION_JSON);

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "getUserLanguages200",
        status().isOk())
        .andReturn()
        .getResponse();

    List<PlatformUserLanguageDto> result = parseResponse(response,
        new TypeReference<>() {
        });

    log.debug("languages={}", result);

    assertThat(result.size()).isEqualTo(2);
  }
}
