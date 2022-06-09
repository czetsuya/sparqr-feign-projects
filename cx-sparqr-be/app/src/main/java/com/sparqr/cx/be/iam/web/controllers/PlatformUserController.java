package com.sparqr.cx.be.iam.web.controllers;

import static com.sparqr.cx.be.iam.EndpointConstants.PATH_USERS;
import static com.sparqr.cx.be.iam.EndpointConstants.PATH_USER_ALIAS;
import static com.sparqr.cx.be.iam.EndpointConstants.PATH_USER_PROFILE;
import static com.sparqr.cx.be.iam.EndpointConstants.PVAR_ALIAS;

import com.sparqr.cx.be.iam.EndpointConstants;
import com.sparqr.cx.iam.api.dtos.inbound.MapUpdateOrCreateIfAbsentRequest;
import com.sparqr.cx.iam.api.dtos.inbound.UpdateAliasDto;
import com.sparqr.cx.iam.api.dtos.inbound.UpdateUserDetailDto;
import com.sparqr.cx.iam.api.dtos.outbound.PlatformUserDto;
import com.sparqr.cx.iam.api.dtos.outbound.UserDetailDto;
import com.sparqr.cx.iam.client.proxies.PlatformUserProxyAsync;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class PlatformUserController {

  private final PlatformUserProxyAsync platformUserProxyAsync;

  /**
   * This endpoint is use to:
   * <ul>
   *   <li>create both SSO and platform user</li>
   *   <li>create a new platform user and map an existing SSO</li>
   *   <li>create an SSO and map to an existing platform user</li>
   * </ul>
   *
   * @param mapUpdateOrCreateIfAbsentRequest - user information
   * @return created user information
   */
  @PostMapping(
      path = PATH_USERS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @Transactional
  public CompletableFuture<PlatformUserDto> mapUpdateOrCreateIfAbsent(
      @Valid @RequestBody MapUpdateOrCreateIfAbsentRequest mapUpdateOrCreateIfAbsentRequest) {

    log.debug("Check if user with externalRef={} exists", mapUpdateOrCreateIfAbsentRequest.getExternalRef());

    return platformUserProxyAsync.mapUpdateOrCreateIfAbsent(mapUpdateOrCreateIfAbsentRequest);
  }

  @PutMapping(
      path = PATH_USER_PROFILE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @Transactional
  public CompletableFuture<UserDetailDto> updateOrCreateIfAbsent(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias,
      @Valid @NotNull @RequestBody UpdateUserDetailDto userDetailDto) {

    log.debug("upsert user profile={}", userDetailDto);

    return platformUserProxyAsync.updateOrCreateIfAbsent(alias, userDetailDto);
  }

  @GetMapping(
      path = PATH_USER_PROFILE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public CompletableFuture<UserDetailDto> getUserProfile(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias) {

    log.info("get user profile for alias={}", alias);

    return platformUserProxyAsync.getUserProfile(alias);
  }

  @PutMapping(
      path = PATH_USER_ALIAS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<Void> updateAlias(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias,
      @RequestBody @Valid @NotNull UpdateAliasDto updateAliasDto) {

    log.info("update alias={} to={}", alias, updateAliasDto);

    return platformUserProxyAsync.updateAlias(alias, updateAliasDto);
  }
}