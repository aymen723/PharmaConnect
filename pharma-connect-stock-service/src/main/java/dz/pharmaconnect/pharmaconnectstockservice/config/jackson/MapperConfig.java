package dz.pharmaconnect.pharmaconnectstockservice.config.jackson;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {


//    @Bean
//    public ObjectMapper getMapper() {
//        return JsonMapper.builder() // or different mapper for other format
//                .addModule(new ParameterNamesModule())
//                .addModule(new Jdk8Module())
//                .addModule(new JavaTimeModule())
//                // and possibly other configuration, modules, then:
//                .build();
//    }
}
