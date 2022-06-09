package com.sparqr.cx.iam.services.aws;

import java.util.Collections;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminUpdateUserAttributesRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;

@Component
@ConditionalOnProperty(prefix = "cloud.aws", name = "enable", havingValue = "true")
public class AwsCognitoClientImpl implements AwsCognitoClient {

  @Value("${cloud.aws.cognito.region}")
  private String awsRegion;

  @Value("${cloud.aws.cognito.userPoolId}")
  private String userPoolId;

  @Value("${cloud.aws.cognito.userAttribute.alias}")
  private String customUserAttributeAlias;

  private CognitoIdentityProviderClient cognitoClient;

  @PostConstruct
  private void init() {
    cognitoClient = CognitoIdentityProviderClient.builder()
        .region(Region.of(awsRegion))
        .build();
  }

  @Override
  public void updateUserAttributeByAlias(String username, String aliasVal) {
    updateUserAttributeByKey(username, customUserAttributeAlias, aliasVal);
  }

  @Override
  public void updateUserAttributeByKey(String username, String key, String val) {

    AttributeType userAttrs = AttributeType.builder()
        .name(key)
        .value(val)
        .build();

    AdminUpdateUserAttributesRequest request = AdminUpdateUserAttributesRequest.builder()
        .userPoolId(userPoolId)
        .username(username)
        .userAttributes(Collections.singleton(userAttrs))
        .build();

    cognitoClient.adminUpdateUserAttributes(request);
  }
}