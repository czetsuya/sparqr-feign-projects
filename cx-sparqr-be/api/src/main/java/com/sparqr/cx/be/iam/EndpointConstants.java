package com.sparqr.cx.be.iam;

public class EndpointConstants {

  public static final String CONSTRAINT_PATTERN_ALIAS = "^[A-Za-z0-9]+$";

  public static final String PATH_API = "/api";

  public static final String PVAR_ALIAS = "alias";
  public static final String PVAR_ID = "id";
  public static final String PVAR_GIG_ID = "gigId";
  public static final String PVAR_USER_ID = "userId";

  // IAM
  public static final String PATH_USERS = PATH_API + "/users";
  public static final String PATH_USER_PROFILE = PATH_USERS + "/{alias}/profile";
  public static final String PATH_USER_CERTIFICATIONS = PATH_USERS + "/{" + PVAR_ALIAS + "}/certifications";
  public static final String PATH_USER_LANGUAGES = PATH_USERS + "/{" + PVAR_ALIAS + "}/languages";
  public static final String PATH_USER_EDUCATIONS = PATH_USERS + "/{" + PVAR_ALIAS + "}/educations";
  public static final String PATH_USER_SKILLS = PATH_USERS + "/{" + PVAR_ALIAS + "}/skills";
  public static final String PATH_USER_ALIAS = PATH_USERS + "/{" + PVAR_ALIAS + "}/alias";

  // Gigs
  public static final String PATH_GIGS = PATH_USERS + "/{" + PVAR_USER_ID + "}/gigs";
  public static final String PATH_GIG_SHORT = PATH_API + "/users/gigs";
  public static final String PATH_GIG_GALLERY_ITEMS = PATH_USERS + "/{" + PVAR_USER_ID + "}/gigs/{" + PVAR_GIG_ID +
      "}/galleryItems";
  public static final String PATH_GIG_PACKAGES = PATH_USERS + "/{" + PVAR_USER_ID + "}/gigs/{" + PVAR_GIG_ID +
      "}/packages";
  public static final String PATH_BOOKINGS_SHORT = PATH_USERS + "/gigs/bookings";

  public static final String PATH_GIG_BOOKINGS = PATH_API + "/gigs/{" + PVAR_GIG_ID + "}/bookings";
  public static final String PATH_GIG_BOOKINGS_CAN_ORDER = PATH_GIG_BOOKINGS + "/can-order";
}
