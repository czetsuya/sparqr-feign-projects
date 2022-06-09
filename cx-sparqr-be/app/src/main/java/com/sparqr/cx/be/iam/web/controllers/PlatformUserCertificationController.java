package com.sparqr.cx.be.iam.web.controllers;

import static com.sparqr.cx.be.iam.EndpointConstants.PVAR_ALIAS;
import static com.sparqr.cx.be.iam.EndpointConstants.PVAR_ID;

import com.sparqr.cx.be.iam.EndpointConstants;
import com.sparqr.cx.iam.api.dtos.commons.PlatformUserCertificationDto;
import com.sparqr.cx.iam.client.proxies.PlatformUserCertificationProxyAsync;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class PlatformUserCertificationController {

  private final PlatformUserCertificationProxyAsync platformUserCertificationProxyAsync;

  /**
   * Returns a list of user certifications.
   *
   * @param alias unique user identifier
   * @return list of user certifications
   */
  @GetMapping(
      path = EndpointConstants.PATH_USER_CERTIFICATIONS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<List<PlatformUserCertificationDto>> list(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias) {

    return platformUserCertificationProxyAsync.list(alias);
  }

  /**
   * Creates a certification of a given user.
   *
   * @param alias                        unique user identifier
   * @param platformUserCertificationDto certification data
   * @return the created entity
   */
  @PostMapping(
      path = EndpointConstants.PATH_USER_CERTIFICATIONS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<PlatformUserCertificationDto> create(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias,
      @Valid @RequestBody PlatformUserCertificationDto platformUserCertificationDto) {

    return platformUserCertificationProxyAsync.create(alias, platformUserCertificationDto);
  }

  /**
   * Updates a certification given an id.
   *
   * @param alias                        unique user identifier
   * @param id                           the id of the entity to be updated
   * @param platformUserCertificationDto updated information
   * @return the updated entity
   */
  @PutMapping(
      path = EndpointConstants.PATH_USER_CERTIFICATIONS + "/{" + PVAR_ID + "}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<PlatformUserCertificationDto> update(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias,
      @PathVariable(name = PVAR_ID) @Valid @Positive Long id,
      @Valid @RequestBody PlatformUserCertificationDto platformUserCertificationDto) {

    return platformUserCertificationProxyAsync.update(alias, id, platformUserCertificationDto);
  }

  /**
   * Deletes a certification given an id.
   *
   * @param alias the user alias. It's not being used
   * @param id    the id of the entity to be deleted
   * @return CompletableFuture
   */
  @DeleteMapping(
      path = EndpointConstants.PATH_USER_CERTIFICATIONS + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public CompletableFuture<Void> delete(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias,
      @PathVariable(name = PVAR_ID) @Valid @Positive Long id) {

    return platformUserCertificationProxyAsync.delete(alias, id);
  }
}
