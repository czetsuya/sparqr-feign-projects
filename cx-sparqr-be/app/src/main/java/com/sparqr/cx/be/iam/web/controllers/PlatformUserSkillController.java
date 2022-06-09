package com.sparqr.cx.be.iam.web.controllers;

import static com.sparqr.cx.be.iam.EndpointConstants.PATH_USER_SKILLS;
import static com.sparqr.cx.be.iam.EndpointConstants.PVAR_ALIAS;

import com.sparqr.cx.be.iam.EndpointConstants;
import com.sparqr.cx.iam.client.proxies.PlatformUserSkillProxyAsync;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class PlatformUserSkillController {

  private final PlatformUserSkillProxyAsync platformUserSkillProxyAsync;

  /**
   * Creates or updates the list of user skills.
   *
   * @param alias unique user identifier
   * @return list of user skills
   */
  @PostMapping(
      path = PATH_USER_SKILLS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<Set<String>> createOrUpdate(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String alias,
      @RequestBody @Valid @NotEmpty Set<String> skills) {

    return platformUserSkillProxyAsync.createOrUpdate(alias, skills);
  }

  /**
   * Retrieves the skills of a given user.
   *
   * @param userAlias alias of the user
   * @return set of skills
   */
  @GetMapping(
      path = PATH_USER_SKILLS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<Set<String>> getSkills(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank @Pattern(regexp = EndpointConstants.CONSTRAINT_PATTERN_ALIAS) String userAlias) {

    return platformUserSkillProxyAsync.getSkills(userAlias);
  }
}
