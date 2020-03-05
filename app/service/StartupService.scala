package service

import javax.inject._
import component.GithubComponent
import play.api.Configuration

/**
  * Created by Dominik Zduńczyk on 04.03.2020.
  *
  * Generate new gh_token on startup, injects configuration
  */
class StartupService @Inject() (configuration: Configuration) {
  GithubComponent.getAccessToken(configuration)
}
