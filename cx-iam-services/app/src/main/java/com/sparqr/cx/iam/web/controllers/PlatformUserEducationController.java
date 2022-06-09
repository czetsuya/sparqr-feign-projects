package com.sparqr.cx.iam.web.controllers;

import static com.sparqr.cx.iam.api.EndpointConstants.PVAR_ALIAS;
import static com.sparqr.cx.iam.api.EndpointConstants.PVAR_ID;

import com.sparqr.cx.iam.api.EndpointConstants;
import com.sparqr.cx.iam.api.dtos.commons.PlatformUserEducationDto;
import com.sparqr.cx.iam.mappers.Service2WebMapper;
import com.sparqr.cx.iam.mappers.Web2ServiceMapper;
import com.sparqr.cx.iam.services.PlatformUserEducationService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
public class PlatformUserEducationController {

  private final PlatformUserEducationService platformUserEducationService;
  private final Service2WebMapper service2WebMapper;
  private final Web2ServiceMapper web2ServiceMapper;

  /**
   * Returns a list of user educations.
   *
   * @param alias unique user identifier
   * @return list of user educations
   */
  @GetMapping(
      path = EndpointConstants.PATH_USER_EDUCATIONS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<List<PlatformUserEducationDto>> list(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias) {

    return platformUserEducationService.list(alias)
        .thenApply(service2WebMapper::toPlatformUserEducationDto);
  }

  /**
   * Creates a education of a given user.
   *
   * @param alias                    unique user identifier
   * @param platformUserEducationDto education data
   * @return the created entity
   */
  @PostMapping(
      path = EndpointConstants.PATH_USER_EDUCATIONS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public CompletableFuture<PlatformUserEducationDto> create(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias,
      @Valid @RequestBody PlatformUserEducationDto platformUserEducationDto) {

    return platformUserEducationService.createOrUpdate(alias, null,
            web2ServiceMapper.toPlatformUserEducation(platformUserEducationDto))
        .thenApply(service2WebMapper::toPlatformUserEducationDto);
  }

  /**
   * Updates a education given an id.
   *
   * @param alias                    unique user identifier
   * @param id                       the id of the entity to be updated
   * @param platformUserEducationDto updated information
   * @return the updated entity
   */
  @PutMapping(
      path = EndpointConstants.PATH_USER_EDUCATIONS + "/{" + PVAR_ID + "}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<PlatformUserEducationDto> update(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias,
      @PathVariable(name = PVAR_ID) @Valid  Long id,
      @Valid @RequestBody PlatformUserEducationDto platformUserEducationDto) {

    return platformUserEducationService.createOrUpdate(alias, id,
            web2ServiceMapper.toPlatformUserEducation(platformUserEducationDto))
        .thenApply(service2WebMapper::toPlatformUserEducationDto);
  }

  /**
   * Deletes a education given an id.
   *
   * @param alias the user alias. It's not being used
   * @param id    the id of the entity to be deleted
   * @return completable future
   */
  @DeleteMapping(
      path = EndpointConstants.PATH_USER_EDUCATIONS + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public CompletableFuture<Void> delete(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias,
      @PathVariable(name = PVAR_ID) @Valid  Long id) {

    return platformUserEducationService.delete(id);
  }
}
