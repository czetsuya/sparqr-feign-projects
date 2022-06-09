package com.sparqr.cx.booking.configs;

import com.sparqr.cx.gig.client.CxGigServicesClient;
import com.sparqr.cx.traceability.EnableCxFeign;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableCxFeign
@EnableFeignClients(basePackageClasses = {
    CxGigServicesClient.class
})
@Import({CxGigServicesClient.class})
public class FeignConfiguration {

}