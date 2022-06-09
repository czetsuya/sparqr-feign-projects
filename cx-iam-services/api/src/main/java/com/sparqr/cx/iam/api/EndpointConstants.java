package com.sparqr.cx.iam.api;

public final class EndpointConstants {

  private EndpointConstants() {

  }

  public static final String CONSTRAINT_PATTERN_ALIAS = "^[A-Za-z0-9]+$";

  public static final String PVAR_ALIAS = "alias";
  public static final String PVAR_ID = "id";

  public static final String PATH_SERVICES = "/services";
  public static final String PATH_USERS = PATH_SERVICES + "/users";
  public static final String PATH_USER_PROFILE = PATH_USERS + "/{" + PVAR_ALIAS + "}/profile";
  public static final String PATH_USER_CERTIFICATIONS = PATH_USERS + "/{" + PVAR_ALIAS + "}/certifications";
  public static final String PATH_USER_LANGUAGES = PATH_USERS + "/{" + PVAR_ALIAS + "}/languages";
  public static final String PATH_USER_EDUCATIONS = PATH_USERS + "/{" + PVAR_ALIAS + "}/educations";
  public static final String PATH_USER_SKILLS = PATH_USERS + "/{" + PVAR_ALIAS + "}/skills";
  public static final String PATH_USER_ALIAS = PATH_USERS + "/{" + PVAR_ALIAS + "}/alias";
}
