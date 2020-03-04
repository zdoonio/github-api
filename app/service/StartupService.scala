package service

import javax.inject._
import component.GithubComponent
import play.api.Configuration

/**
  * Created by Dominik Zduńczyk on 04.03.2020.
  */
class StartupService @Inject() (configuration: Configuration) {
  GithubComponent.getAccessToken(configuration)
}
