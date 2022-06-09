package com.sparqr.cx.iam.web.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sparqr.cx.iam.api.EndpointConstants;
import com.sparqr.cx.iam.api.dtos.inbound.MapUpdateOrCreateIfAbsentRequest;
import com.sparqr.cx.iam.api.dtos.inbound.UpdateAliasDto;
import com.sparqr.cx.iam.api.dtos.outbound.PlatformUserDto;
import com.sparqr.cx.iam.fixtures.PlatformSsoFixtures;
import com.sparqr.cx.iam.persistence.entities.PlatformSsoEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserEntity;
import com.sparqr.cx.iam.persistence.repositories.PlatformSsoRepository;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserRepository;
import com.sparqr.cx.iam.services.aws.AwsCognitoClient;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import javax.servlet.ServletContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
class PlatformUserControllerFunctionalTest extends FunctionalRestTest {

  private final String ENDPOINT_PATH = "users";

  @Autowired
  private PlatformSsoRepository platformSsoRepository;

  @Autowired
  private PlatformUserRepository platformUserRepository;

  @MockBean
  private AwsCognitoClient awsCognitoClient;

  @BeforeEach
  void setup() {

    ServletRequestAttributes requestAttributes = new ServletRequestAttributes(new MockHttpServletRequest());
    RequestContextHolder.setRequestAttributes(requestAttributes);

    doNothing().when(awsCognitoClient).updateUserAttributeByAlias(any(), any());
  }

  @Test
  void Should_ReturnController_Given_ApplicationContext() {

    ServletContext servletContext = webApplicationContext.getServletContext();
    assertThat(servletContext)
        .isNotNull()
        .isInstanceOf(MockServletContext.class);
    assertThat(webApplicationContext.getBean("platformUserController")).isNotNull();
  }

  /**
   * UC1: External ref exists and user is mapped.
   */
  @Test
  void Should_FindPlatformUser_Given_ItExistsInOpenIdAndMapped() throws Exception {

    String email = "kerri.legaspi@gmail.com";
    String firstName = "Kerri";
    String lastName = "Legaspi";
    String externalRef = "G-100";
    String identityProvider = "Google";
    String contactNo = "09152154717";
    LocalDate dob = LocalDate.of(1984, Month.OCTOBER, 13);
    String alias = "u1001";

    // get a user from database
    MapUpdateOrCreateIfAbsentRequest mapUpdateOrCreateIfAbsentRequest = MapUpdateOrCreateIfAbsentRequest.builder()
        .email(email)
        .identityProvider(identityProvider)
        .externalRef(externalRef)
        .firstName(firstName)
        .lastName(lastName)
        .build();

    String endpoint = "users";
    String call = "signOnSuccess200";

    MockHttpServletRequestBuilder request =
        MockMvcRequestBuilders.post(EndpointConstants.PATH_USERS)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(mapUpdateOrCreateIfAbsentRequest));

    MvcResult mvcResult = mockMvc
        .perform(request)
        .andDo(
            document(
                endpoint + "/" + call,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())))
        .andExpect(status().isOk())
        .andExpect(request().asyncStarted())
        .andDo(document(endpoint + "/" + call, preprocessRequest(prettyPrint())))
        .andReturn();

    mockMvc
        .perform(asyncDispatch(mvcResult))
        .andDo(document(endpoint + "/" + call, preprocessResponse(prettyPrint())));

    MockHttpServletResponse response = performAsync(request,
        ENDPOINT_PATH, "signOnSuccess200")
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();

    PlatformUserDto result = parseResponse(response, PlatformUserDto.class);

    log.debug("UC1 response={}", result);

    assertThat(result.getEmail()).isEqualTo(email);
    assertThat(result.getFirstName()).isEqualTo(firstName);
    assertThat(result.getLastName()).isEqualTo(lastName);
    assertThat(result.getExternalRef()).isEqualTo(externalRef);
    assertThat(result.getContactNo()).isEqualTo(contactNo);
    assertThat(result.getDob()).isEqualTo(dob);
  }

  /**
   * UC3: External ref does not exist but email does in platform user table
   */
  @Test
  void Should_CreatePlatformUser_Given_ItOnlyExistsInSso() throws Exception {

    String email = "elianagraciela.legaspi@gmail.com";
    String firstName = "Eliana Graciela";
    String lastName = "Legaspi";
    String externalRef = "G-002";
    String identityProvider = "Google";
    String contactNo = "09152154718";
    LocalDate dob = LocalDate.of(2021, Month.JULY, 21);
    String alias = "u1002";

    // get a user from database
    MapUpdateOrCreateIfAbsentRequest mapUpdateOrCreateIfAbsentRequest = MapUpdateOrCreateIfAbsentRequest.builder()
        .email(email)
        .identityProvider(identityProvider)
        .externalRef(externalRef)
        .firstName(firstName)
        .lastName(lastName)
        .build();

    MockHttpServletRequestBuilder request =
        MockMvcRequestBuilders.post(EndpointConstants.PATH_USERS)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(mapUpdateOrCreateIfAbsentRequest));

    MockHttpServletResponse response = performAsync(request,
        ENDPOINT_PATH, "mapSsoToPlatformUser200")
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();

    PlatformUserDto result = parseResponse(response, PlatformUserDto.class);

    log.debug("UC3 response={}", result);

    assertThat(result.getEmail()).isEqualTo(email);
    assertThat(result.getFirstName()).isEqualTo(firstName);
    assertThat(result.getLastName()).isEqualTo(lastName);
    assertThat(result.getExternalRef()).isEqualTo(externalRef);
    assertThat(result.getContactNo()).isEqualTo(contactNo);
    assertThat(result.getDob()).isEqualTo(dob);

    Optional<PlatformUserEntity> optPlatformUser = platformUserRepository.findByEmail(email);
    assertThat(optPlatformUser).isPresent();

    PlatformUserEntity platformUser = optPlatformUser.get();
    Assertions.assertThat(platformUser.getCreated()).isNotNull();
    assertThat(platformUser.getEmail()).isEqualTo(email);
    assertThat(platformUser.getFirstName()).isEqualTo(firstName);
    assertThat(platformUser.getLastName()).isEqualTo(lastName);
    assertThat(platformUser.getContactNo()).isEqualTo(contactNo);
    assertThat(platformUser.getDob()).isEqualTo(dob);
    assertThat(platformUser.getAlias()).isEqualTo(alias);

    Optional<PlatformSsoEntity> optPlatformSso = platformSsoRepository.findByExternalRef(externalRef);
    assertThat(optPlatformSso).isPresent();

    PlatformSsoEntity platformSso = optPlatformSso.get();
    assertThat(platformSso.getCreated()).isNotNull();
    assertThat(platformSso.getExternalRef()).isEqualTo(externalRef);
    assertThat(platformSso.getIdentityProvider()).isEqualTo(identityProvider);
  }

  /**
   * UC4: External ref does not exist in openid table and email does not exist in app user table as well. Create an
   * entry in both openid and app user table.
   */
  @Test
  void Should_CreateSsoAndPlatformUser_Given_ExternalRefAndEmailDontExists() throws Exception {

    String alias = "u1";

    MockHttpServletRequestBuilder request =
        MockMvcRequestBuilders.post(EndpointConstants.PATH_USERS)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(PlatformSsoFixtures.getPlatformUserInboundDto()));

    MockHttpServletResponse response = performAsync(request,
        ENDPOINT_PATH, "createSsoAndPlatformUser200")
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();

    PlatformUserDto result = parseResponse(response, PlatformUserDto.class);

    assertThat(result.getEmail()).isEqualTo(PlatformSsoFixtures.EMAIL);
    assertThat(result.getFirstName()).isEqualTo(PlatformSsoFixtures.FIRSTNAME);
    assertThat(result.getLastName()).isEqualTo(PlatformSsoFixtures.LASTNAME);
    assertThat(result.getExternalRef()).isEqualTo(PlatformSsoFixtures.EXTERNAL_REF);

    Optional<PlatformSsoEntity> platformSso = platformSsoRepository.findByExternalRef(PlatformSsoFixtures.EXTERNAL_REF);

    // assertions
    assertThat(platformSso).isNotNull();
    if (platformSso.isPresent()) {
      Assertions.assertThat(platformSso.get()).isNotNull();
      assertMockPlatformSsoUser(platformSso.get());
    }
  }

  @SneakyThrows
  @Test
  void shouldThrowAnExceptionWhenUserDoesNotExists() {

    String oldAlias = "doesnotexists";
    String newAlias = "newAlias";

    MockHttpServletRequestBuilder request =
        MockMvcRequestBuilders.put(EndpointConstants.PATH_USER_ALIAS, oldAlias)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(UpdateAliasDto.builder().alias(newAlias).build()));

    performAsync(request,
        ENDPOINT_PATH, "updateUserAlias400")
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse();
  }

  @SneakyThrows
  @Test
  void shouldUpdateAliasIfExists() {

    String oldAlias = "u1003";
    String newAlias = "newAlias";

    MockHttpServletRequestBuilder request =
        MockMvcRequestBuilders.put(EndpointConstants.PATH_USER_ALIAS, oldAlias)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(UpdateAliasDto.builder().alias(newAlias).build()));

    performAsync(request,
        ENDPOINT_PATH, "updateUserAlias200")
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();

    Optional<PlatformUserEntity> platformUserEntity = platformUserRepository.findByAlias(newAlias);
    assertThat(platformUserEntity).isNotEmpty();
  }

  private void assertMockPlatformSsoUser(PlatformSsoEntity platformSso) {

    assertThat(platformSso.getId()).isNotNull();
    assertThat(platformSso.getPlatformUser()).isNotNull();
    assertThat(platformSso.getPlatformUser().getEmail()).isEqualTo(PlatformSsoFixtures.EMAIL);
    assertThat(platformSso.getPlatformUser().getFirstName()).isEqualTo(PlatformSsoFixtures.FIRSTNAME);
    assertThat(platformSso.getPlatformUser().getLastName()).isEqualTo(PlatformSsoFixtures.LASTNAME);
    assertThat(platformSso.getExternalRef()).isEqualTo(PlatformSsoFixtures.EXTERNAL_REF);
    assertThat(platformSso.getCreated()).isNotNull();
    Assertions.assertThat(platformSso.getPlatformUser().getCreated()).isNotNull();
    Assertions.assertThat(platformSso.getPlatformUser().getUpdated()).isNotNull();
    Assertions.assertThat(platformSso.getPlatformUser().getCreatedBy()).isNotNull();
    Assertions.assertThat(platformSso.getPlatformUser().getUpdatedBy()).isNotNull();
    Assertions.assertThat(platformSso.getPlatformUser().getId()).isNotNull();
  }
}