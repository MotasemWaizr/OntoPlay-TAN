package ontoplay.controllers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ontoplay.OntologyHelper;
import ontoplay.models.ontologyModel.OntoClass;
import ontoplay.models.ontologyReading.OntologyReader;
import ontoplay.models.owlGeneration.OntologyGenerator;
import play.mvc.Controller;

public class OntologyController extends Controller {

	protected static OntologyReader ontologyReader = OntologyReader.getGlobalInstance();
	protected static OntologyGenerator ontologyGenerator = OntologyGenerator.getGlobalInstance();

	protected static OntoClass getOwlClass(String className) {
		return ontologyReader.getOwlClass(OntologyHelper.nameSpace + className);
	}

	protected static class HtmlHolder {
		public play.twirl.api.Html value;
	}

	protected static play.twirl.api.Html renderTemplateByName(
			String templateName, Object... args) {
		try {

			Class[] parameterTypes = new Class[args.length];

			for (int i = 0; i < args.length; i++) {
				parameterTypes[i] = args[i].getClass();
			}

			Method method = Class.forName("ontoplay.views.html." + templateName)
					.getMethod("render", parameterTypes);

			play.twirl.api.Html html = (play.twirl.api.Html) method.invoke(
					null, args);
			return html;

		} catch (SecurityException e) {
			throw new IllegalStateException(e);
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException(e);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		} catch (InvocationTargetException e) {
			throw new IllegalStateException(e);
		}
	}

	public OntologyController() {
		super();
	}

}