package com.sparqr.cx.iam.services.aws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "cloud.aws", name = "enable", havingValue = "false")
public class DummyAwsCognitoClientImpl implements AwsCognitoClient {

  @Override
  public void updateUserAttributeByAlias(String username, String aliasVal) {
    log.debug("updating aws user={username} attribute with key=alias, value={}", aliasVal);
  }

  @Override
  public void updateUserAttributeByKey(String username, String key, String val) {
    log.debug("updating aws user={} attribute with key={}, value={}", username, key, val);
  }
}