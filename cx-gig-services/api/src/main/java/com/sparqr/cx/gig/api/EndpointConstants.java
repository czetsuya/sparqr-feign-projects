package com.sparqr.cx.gig.api;

public final class EndpointConstants {

  private EndpointConstants() {

  }

  public static final String PVAR_ID = "id";
  public static final String PVAR_GIG_ID = "gigId";
  public static final String PVAR_USER_ID = "userId";

  public static final String PATH_SERVICES = "/services";
  public static final String PATH_USERS = PATH_SERVICES + "/users";
  public static final String PATH_GIGS = PATH_USERS + "/{" + PVAR_USER_ID + "}/gigs";
  public static final String PATH_GIG_GALLERY_ITEMS = PATH_USERS + "/{" + PVAR_USER_ID + "}/gigs/{" + PVAR_GIG_ID +
      "}/galleryItems";
  public static final String PATH_GIG_PACKAGES = PATH_USERS + "/{" + PVAR_USER_ID + "}/gigs/{" + PVAR_GIG_ID +
      "}/packages";
  public static final String PATH_GIG_SHORT = PATH_USERS + "/gigs";
}
