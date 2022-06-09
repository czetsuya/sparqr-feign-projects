package com.sparqr.cx.iam.client.proxies;

import static com.sparqr.cx.iam.api.EndpointConstants.PVAR_ALIAS;
import static com.sparqr.cx.iam.api.EndpointConstants.PVAR_ID;

import com.sparqr.cx.iam.api.EndpointConstants;
import com.sparqr.cx.iam.api.dtos.commons.PlatformUserCertificationDto;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "platformUserCertificationProxy", url = "${app.cx.client.iam-services.url}")
public interface PlatformUserCertificationProxy {

  @GetMapping(
      path = EndpointConstants.PATH_USER_CERTIFICATIONS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  List<PlatformUserCertificationDto> list(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank String alias);

  @PostMapping(
      path = EndpointConstants.PATH_USER_CERTIFICATIONS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  PlatformUserCertificationDto create(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank String alias,
      @Valid @RequestBody PlatformUserCertificationDto platformUserCertificationDto);

  @PutMapping(
      path = EndpointConstants.PATH_USER_CERTIFICATIONS + "/{" + PVAR_ID + "}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  PlatformUserCertificationDto update(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank String alias,
      @PathVariable(name = PVAR_ID) @Valid  Long id,
      @Valid @RequestBody PlatformUserCertificationDto platformUserCertificationDto);

  @DeleteMapping(
      path = EndpointConstants.PATH_USER_CERTIFICATIONS + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  Void delete(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank String alias,
      @PathVariable(name = PVAR_ID) @Valid  Long id);
}
