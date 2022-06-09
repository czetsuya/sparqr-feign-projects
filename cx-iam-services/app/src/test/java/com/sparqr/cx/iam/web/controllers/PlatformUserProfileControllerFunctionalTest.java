package com.sparqr.cx.iam.web.controllers;

import static com.sparqr.cx.iam.api.EndpointConstants.PATH_USER_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sparqr.cx.iam.api.commons.GenderEnum;
import com.sparqr.cx.iam.api.dtos.inbound.CreateOrUpdateUserProfileRequestDto;
import com.sparqr.cx.iam.api.dtos.inbound.UpdatePlatformUserDto;
import com.sparqr.cx.iam.api.dtos.inbound.UpdateUserDetailDto;
import com.sparqr.cx.iam.api.dtos.outbound.PlatformUserDto;
import com.sparqr.cx.iam.persistence.entities.PlatformUserEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserProfileEntity;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserProfileRepository;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserRepository;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import javax.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class PlatformUserProfileControllerFunctionalTest extends FunctionalRestTest {

  private final String ENDPOINT_PATH = "users";

  @Autowired
  private PlatformUserProfileRepository platformUserProfileRepository;

  @Autowired
  private PlatformUserRepository platformUserRepository;

  @BeforeEach
  void setup() {

    ServletRequestAttributes requestAttributes = new ServletRequestAttributes(new MockHttpServletRequest());
    RequestContextHolder.setRequestAttributes(requestAttributes);
  }

  @Test
  void shouldReturnTheControllerFromAplicationContext() {

    ServletContext servletContext = webApplicationContext.getServletContext();
    assertThat(servletContext)
        .isNotNull()
        .isInstanceOf(MockServletContext.class);
    assertThat(webApplicationContext.getBean("platformUserController")).isNotNull();
  }

  @Test
  void shouldUpdateUserAndProfile() throws Exception {

    String email = "kerri.legaspi@gmail.com";
    String firstName = "Kerri";
    String lastName = "Legaspi";
    String contactNo = "09152154717";
    LocalDate dob = LocalDate.of(1984, Month.OCTOBER, 13);
    String alias = "u1001";

    UpdatePlatformUserDto userDto = UpdatePlatformUserDto.builder()
        .alias(alias)
        .firstName(firstName)
        .lastName(lastName)
        .contactNo(contactNo)
        .dob(dob)
        .gender(GenderEnum.F)
        .email(email)
        .build();

    // get a user from database
    CreateOrUpdateUserProfileRequestDto userProfileDto = CreateOrUpdateUserProfileRequestDto.builder()
        .availability("AVAILABLE")
        .profession("Writer")
        .website("https://czetsuyatech.com")
        .about("I love reading and so I'll be a good writer.")
        .introduction("I am a writer")
        .profilePicture("thumbnail")
        .build();

    UpdateUserDetailDto updateUserDetailDto = UpdateUserDetailDto.builder()
        .profile(userProfileDto)
        .user(userDto)
        .build();

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders
            .put(PATH_USER_PROFILE, alias)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateUserDetailDto));

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "updateUserAndProfile200",
        status().isOk())
        .andReturn()
        .getResponse();

    Optional<PlatformUserEntity> optPlatformUser = platformUserRepository.findByEmail(email);
    assertThat(optPlatformUser).isPresent();

    PlatformUserEntity platformUserEntity = optPlatformUser.get();
    assertThat(platformUserEntity.getCreated()).isNotNull();
    assertThat(platformUserEntity.getEmail()).isEqualTo(email);
    assertThat(platformUserEntity.getFirstName()).isEqualTo(firstName);
    assertThat(platformUserEntity.getLastName()).isEqualTo(lastName);
    assertThat(platformUserEntity.getContactNo()).isEqualTo(contactNo);
    assertThat(platformUserEntity.getDob()).isEqualTo(dob);
    assertThat(platformUserEntity.getAlias()).isEqualTo(alias);

    Optional<PlatformUserProfileEntity> optUserProfileEntity = platformUserProfileRepository.findByPlatformUser(
        platformUserEntity);
    assertThat(optUserProfileEntity).isPresent();

    PlatformUserProfileEntity platformUserProfileEntity = optUserProfileEntity.get();
    assertThat(platformUserProfileEntity.getAbout()).isEqualTo(userProfileDto.getAbout());
    assertThat(platformUserProfileEntity.getIntroduction()).isEqualTo(userProfileDto.getIntroduction());
    assertThat(platformUserProfileEntity.getProfilePicture()).isEqualTo(userProfileDto.getProfilePicture());
    assertThat(platformUserProfileEntity.getProfession()).isEqualTo(userProfileDto.getProfession());
    assertThat(platformUserProfileEntity.getAvailability()).isEqualTo(userProfileDto.getAvailability());
    assertThat(platformUserProfileEntity.getWebsite()).isEqualTo(userProfileDto.getWebsite());
  }

  @Test
  void shouldUpdateUserAndCreateProfile() throws Exception {

    String email = "elianagraciela.legaspi@gmail.com";
    String firstName = "Eliana Graciela";
    String lastName = "Legaspi";
    String contactNo = "09152154718";
    LocalDate dob = LocalDate.of(2021, Month.JULY, 21);
    String alias = "u1002";

    UpdatePlatformUserDto userDto = UpdatePlatformUserDto.builder()
        .alias(alias)
        .firstName(firstName)
        .lastName(lastName)
        .contactNo(contactNo)
        .dob(dob)
        .gender(GenderEnum.F)
        .email(email)
        .build();

    // get a user from database
    CreateOrUpdateUserProfileRequestDto userProfileDto = CreateOrUpdateUserProfileRequestDto.builder()
        .availability("AVAILABLE")
        .profession("Illustration")
        .website("https://elianagraciela.com")
        .about("I love sketching and so I'll be a good illustrator.")
        .introduction("I am a writer")
        .profilePicture("thumbnail")
        .build();

    UpdateUserDetailDto updateUserDetailDto = UpdateUserDetailDto.builder()
        .profile(userProfileDto)
        .user(userDto)
        .build();

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders
            .put(PATH_USER_PROFILE, alias)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateUserDetailDto));

    MockHttpServletResponse response = performAsync(requestBuilder, ENDPOINT_PATH, "updateUserAndCreateProfile200",
        status().isOk())
        .andReturn()
        .getResponse();

    Optional<PlatformUserEntity> optPlatformUser = platformUserRepository.findByEmail(email);
    assertThat(optPlatformUser).isPresent();

    PlatformUserEntity platformUserEntity = optPlatformUser.get();
    assertThat(platformUserEntity.getCreated()).isNotNull();
    assertThat(platformUserEntity.getEmail()).isEqualTo(email);
    assertThat(platformUserEntity.getFirstName()).isEqualTo(firstName);
    assertThat(platformUserEntity.getLastName()).isEqualTo(lastName);
    assertThat(platformUserEntity.getContactNo()).isEqualTo(contactNo);
    assertThat(platformUserEntity.getDob()).isEqualTo(dob);
    assertThat(platformUserEntity.getAlias()).isEqualTo(alias);

    Optional<PlatformUserProfileEntity> optUserProfileEntity = platformUserProfileRepository.findByPlatformUser(
        platformUserEntity);
    assertThat(optUserProfileEntity).isPresent();

    PlatformUserProfileEntity platformUserProfileEntity = optUserProfileEntity.get();
    assertThat(platformUserProfileEntity.getAbout()).isEqualTo(userProfileDto.getAbout());
    assertThat(platformUserProfileEntity.getProfession()).isEqualTo(userProfileDto.getProfession());
    assertThat(platformUserProfileEntity.getAvailability()).isEqualTo(userProfileDto.getAvailability());
    assertThat(platformUserProfileEntity.getWebsite()).isEqualTo(userProfileDto.getWebsite());
  }
}
