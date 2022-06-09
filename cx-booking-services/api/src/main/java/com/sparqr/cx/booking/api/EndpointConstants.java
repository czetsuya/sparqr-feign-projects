package com.sparqr.cx.booking.api;

public final class EndpointConstants {

  private EndpointConstants() {

  }

  public static final String PVAR_GIG_ID = "gigId";
  public static final String PVAR_USER_ID = "userId";

  public static final String PATH_SERVICES = "/services";
  public static final String PATH_GIGS = PATH_SERVICES + "/gigs";
  public static final String PATH_GIG_BOOKINGS = PATH_GIGS + "/{" + PVAR_GIG_ID + "}/bookings";
  public static final String PATH_GIG_BOOKINGS_CAN_ORDER = PATH_GIG_BOOKINGS + "/can-order";

  public static final String PATH_USERS = PATH_SERVICES + "/users";
  public static final String PATH_USER_ORDERS = PATH_USERS + "/{" + PVAR_USER_ID + "}/orders";
}
