package com.sparqr.cx.be.common.configs;

import com.sparqr.cx.booking.client.CxBookingServicesClient;
import com.sparqr.cx.gig.client.CxGigServicesClient;
import com.sparqr.cx.iam.client.CxIamServicesClient;
import com.sparqr.cx.traceability.EnableCxFeign;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableCxFeign
@EnableFeignClients(basePackageClasses = {
    CxIamServicesClient.class,
    CxGigServicesClient.class,
    CxBookingServicesClient.class
})
@Import({CxIamServicesClient.class, CxGigServicesClient.class, CxBookingServicesClient.class})
public class FeignConfiguration {

}