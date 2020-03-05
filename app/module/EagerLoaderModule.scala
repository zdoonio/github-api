package module

import com.google.inject.AbstractModule
import service.StartupService

/**
  * Module for configure processes on startup
  */
class EagerLoaderModule extends AbstractModule {
  override def configure() = {
 
    bind(classOf[StartupService]).asEagerSingleton
  }
}