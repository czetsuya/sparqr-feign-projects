package com.sparqr.cx.iam.web.controllers;

import static com.sparqr.cx.iam.api.EndpointConstants.PVAR_ALIAS;

import com.sparqr.cx.iam.api.EndpointConstants;
import com.sparqr.cx.iam.api.dtos.inbound.MapUpdateOrCreateIfAbsentRequest;
import com.sparqr.cx.iam.api.dtos.inbound.UpdateAliasDto;
import com.sparqr.cx.iam.api.dtos.inbound.UpdateUserDetailDto;
import com.sparqr.cx.iam.api.dtos.outbound.PlatformUserDto;
import com.sparqr.cx.iam.api.dtos.outbound.UserDetailDto;
import com.sparqr.cx.iam.mappers.Service2WebMapper;
import com.sparqr.cx.iam.mappers.Web2ServiceMapper;
import com.sparqr.cx.iam.services.PlatformSsoService;
import com.sparqr.cx.iam.services.PlatformUserProfileService;
import com.sparqr.cx.iam.services.PlatformUserService;
import com.sparqr.cx.iam.services.pojos.UserDetail;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * SSO, User REST endpoints.
 */
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class PlatformUserController {

  public static final long TIMEOUT = 5 * 60 * 1000L;

  private final PlatformUserService platformUserService;
  private final PlatformSsoService platformSsoService;
  private final PlatformUserProfileService platformUserProfileService;
  private final Web2ServiceMapper web2ServiceMapper;
  private final Service2WebMapper service2WebMapper;

  /**
   * Creates, maps an SSO user to an internal user table.
   *
   * @param mapUpdateOrCreateIfAbsentRequest - JSON object
   * @return a CompletableFuture instance of {@link PlatformUserDto}
   */
  @PostMapping(
      path = EndpointConstants.PATH_USERS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public CompletableFuture<PlatformUserDto> mapUpdateOrCreateIfAbsent(
      @Valid @RequestBody MapUpdateOrCreateIfAbsentRequest mapUpdateOrCreateIfAbsentRequest) {

    log.info("Check if user with externalRef={} exists", mapUpdateOrCreateIfAbsentRequest.getExternalRef());

    return platformSsoService.createIfNotExists(web2ServiceMapper.toPlatformUser(mapUpdateOrCreateIfAbsentRequest))
        .thenApply(service2WebMapper::toPlatformUserDto);
  }

  /**
   * Creates or updates a user profile.
   *
   * @param alias               Unique identifier to the user. Unless set by the user, the default value is u+userId.
   * @param updateUserDetailDto Contains the user and profile information.
   * @return void {@link CompletableFuture}
   */
  @PutMapping(
      path = EndpointConstants.PATH_USER_PROFILE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public CompletableFuture<UserDetailDto> updateOrCreateIfAbsent(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias,
      @Valid @NotNull @RequestBody UpdateUserDetailDto updateUserDetailDto) {

    log.info("upsert user profile={}", updateUserDetailDto);

    return platformUserService.update(alias, web2ServiceMapper.toUserDetail(updateUserDetailDto))
        .thenApply(service2WebMapper::toUserDetailDto);
  }

  /**
   * Retrieves the user and profile information.
   *
   * @param alias alias of the user
   * @return user information
   */
  @GetMapping(
      path = EndpointConstants.PATH_USER_PROFILE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public CompletableFuture<UserDetailDto> getUserProfile(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias) {

    log.info("get user profile for alias={}", alias);

    return platformUserService.getUser(alias)
        .thenCombine(platformUserProfileService.getProfile(alias)
            ,
            (user, profile) ->
                UserDetail.builder()
                    .user(user)
                    .profile(profile)
                    .build())
        .thenApply(service2WebMapper::toUserDetailDto);
  }

  /**
   * Updates the alias of a given user.
   *
   * @param alias          old alias of the user
   * @param updateAliasDto contains the new alias of the user
   * @return status 200 if ok
   */
  @PutMapping(
      path = EndpointConstants.PATH_USER_ALIAS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<Void> updateAlias(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias,
      @RequestBody @Valid @NotNull UpdateAliasDto updateAliasDto) {

    log.info("update alias={} to={}", alias, updateAliasDto);

    return platformUserService.updateAlias(alias, updateAliasDto.getAlias());
  }
}