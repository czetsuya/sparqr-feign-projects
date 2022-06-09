package com.sparqr.cx.iam.services.aws;

public interface AwsCognitoClient {

  void updateUserAttributeByAlias(String username, String aliasVal);

  void updateUserAttributeByKey(String username, String key, String val);
}
