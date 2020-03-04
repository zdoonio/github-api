package module

import com.google.inject.AbstractModule
import service.StartupService

class EagerLoaderModule extends AbstractModule {
  override def configure() = {
 
    bind(classOf[StartupService]).asEagerSingleton
  }
}