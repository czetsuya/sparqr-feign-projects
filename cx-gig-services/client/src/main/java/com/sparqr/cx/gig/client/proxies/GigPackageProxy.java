package com.sparqr.cx.gig.client.proxies;

import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_GIG_ID;
import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_ID;
import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_USER_ID;

import com.sparqr.cx.gig.api.EndpointConstants;
import com.sparqr.cx.gig.api.dtos.commons.GigPackageDto;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

@FeignClient(name = "gigPackageProxy", url = "${app.cx.client.gig-services.url}")
public interface GigPackageProxy {

  @GetMapping(
      path = EndpointConstants.PATH_GIG_PACKAGES,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  List<GigPackageDto> list(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId);

  @PostMapping(
      path = EndpointConstants.PATH_GIG_PACKAGES,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  GigPackageDto create(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId,
      @Valid @RequestBody GigPackageDto gigPackageDto);

  @PutMapping(
      path = EndpointConstants.PATH_GIG_PACKAGES + "/{" + PVAR_ID + "}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  GigPackageDto update(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId,
      @PathVariable(name = PVAR_ID) @Valid Long id,
      @Valid @RequestBody GigPackageDto gigPackageDto);

  @DeleteMapping(
      path = EndpointConstants.PATH_GIG_PACKAGES + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  Void delete(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId,
      @PathVariable(name = PVAR_ID) @Valid Long id);
}
