package com.sparqr.cx.be.iam.web.controllers;

import static com.sparqr.cx.be.iam.EndpointConstants.PATH_USER_LANGUAGES;
import static com.sparqr.cx.be.iam.EndpointConstants.PVAR_ALIAS;
import static com.sparqr.cx.be.iam.EndpointConstants.PVAR_ID;

import com.sparqr.cx.be.iam.EndpointConstants;
import com.sparqr.cx.iam.api.dtos.commons.PlatformUserLanguageDto;
import com.sparqr.cx.iam.client.proxies.PlatformUserLanguageProxyAsync;
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
public class PlatformUserLanguageController {

  private final PlatformUserLanguageProxyAsync platformUserLanguageProxyAsync;

  /**
   * Returns a list of user languages.
   *
   * @param alias unique user identifier
   * @return list of user languages
   */
  @GetMapping(
      path = PATH_USER_LANGUAGES,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<List<PlatformUserLanguageDto>> list(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias) {

    return platformUserLanguageProxyAsync.list(alias);
  }

  /**
   * Creates a language of a given user.
   *
   * @param alias                   unique user identifier
   * @param platformUserLanguageDto language data
   * @return the created entity
   */
  @PostMapping(
      path = PATH_USER_LANGUAGES,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<PlatformUserLanguageDto> create(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias,
      @Valid @RequestBody PlatformUserLanguageDto platformUserLanguageDto) {

    return platformUserLanguageProxyAsync.create(alias, platformUserLanguageDto);
  }

  /**
   * Updates a language given an id.
   *
   * @param alias                   unique user identifier
   * @param id                      the id of the entity to be updated
   * @param platformUserLanguageDto updated information
   * @return the updated entity
   */
  @PutMapping(
      path = PATH_USER_LANGUAGES + "/{" + PVAR_ID + "}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<PlatformUserLanguageDto> update(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias,
      @PathVariable(name = PVAR_ID) @Valid @Positive Long id,
      @Valid @RequestBody PlatformUserLanguageDto platformUserLanguageDto) {

    return platformUserLanguageProxyAsync.update(alias, id, platformUserLanguageDto);
  }

  /**
   * Deletes a language given an id.
   *
   * @param alias the user alias. It's not being used
   * @param id    the id of the entity to be deleted
   * @return CompletableFuture
   */
  @DeleteMapping(
      path = PATH_USER_LANGUAGES + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public CompletableFuture<Void> delete(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias,
      @PathVariable(name = PVAR_ID) @Valid @Positive Long id) {

    return platformUserLanguageProxyAsync.delete(alias, id);
  }
}
