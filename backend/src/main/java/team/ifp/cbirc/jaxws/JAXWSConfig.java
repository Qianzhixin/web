package team.ifp.cbirc.jaxws;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class JAXWSConfig {
    @Autowired
    private Bus bus;

    @Autowired
    LawLibraryService lawLibraryService;


    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, lawLibraryService);
        endpoint.publish("/lawLibrary");
        return endpoint;
    }

}
