package com.sparqr.cx.iam.client.proxies;

import static com.sparqr.cx.iam.api.EndpointConstants.PVAR_ALIAS;

import com.sparqr.cx.iam.api.EndpointConstants;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "platformUserSkillProxy", url = "${app.cx.client.iam-services.url}")
public interface PlatformUserSkillProxy {

  @PostMapping(
      path = EndpointConstants.PATH_USER_SKILLS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  Set<String> createOrUpdate(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank String alias,
      @RequestBody @Valid @NotEmpty Set<String> skills);

  @GetMapping(
      path = EndpointConstants.PATH_USER_SKILLS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  Set<String> getSkills(@PathVariable(name = PVAR_ALIAS) @Valid @NotBlank String userAlias);
}
