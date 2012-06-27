package grails.plugin.gson

import groovy.util.logging.Slf4j
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware
import com.google.gson.*

@Slf4j
class GsonFactory implements GrailsApplicationAware {

	GrailsApplication grailsApplication

	GsonFactory() {}

	GsonFactory(GrailsApplication grailsApplication) {
		this.grailsApplication = grailsApplication
	}

	Gson createGson() {
		def builder = new GsonBuilder()
		for (domainClass in grailsApplication.getDomainClasses()) {
			log.debug "registering adapter for $domainClass.name"
			builder.registerTypeAdapter domainClass.clazz, new GrailsDomainDeserializer(domainClass)
		}
		builder.create()
	}

}