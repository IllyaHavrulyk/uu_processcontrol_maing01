package uu.processcontrol.main;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import uu.app.subapp.AbstractSubAppConfiguration;
import uu.app.subapp.OidcAuthenticationContextConfiguration;
import uu.app.subapp.WorkspaceContextConfiguration;
import uu.app.validation.ValidationTypeDefinitionSource;

/**
 * Spring configuration of the application.
 */
@Configuration
@Import({WorkspaceContextConfiguration.class, OidcAuthenticationContextConfiguration.class})
public class SubAppConfiguration extends AbstractSubAppConfiguration {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    return modelMapper;
  }

}
