package controllers;

import java.util.HashMap;
import java.util.Map;

import ontoplay.models.ClassCondition;
import ontoplay.models.ConditionDeserializer;
import ontoplay.models.ontologyModel.OntoClass;
import ontoplay.models.ontologyModel.OwlIndividual;
import play.mvc.Result;
import org.semanticweb.owlapi.model.OWLOntology;



//import jadeOWL.base.OntologyManager;

import ontoplay.controllers.OntologyController;
import ontoplay.OntologyHelper;

//TODO: This should generate individual describing the configuration, instead of constraints
public class Organization extends OntologyController {

	// TODO: Eventually this should be kept in a database
	private static Map<String, ClassCondition> conditions = new HashMap<String, ClassCondition>();

	public static Result index() {
		try {
			return ok(views.html.tan.startView.render("TAN"));// views.html.tan.tableView.render("Organizations - TAN") add.render("Add " + className, getOwlClass(className)));
		} catch (Exception e) {
			return ok("can't find the required classs" + e.getMessage());
		}

	}
	
	public static Result updateConditions(String conditionJson,
			String indiviualName) {
		try {

			ClassCondition condition = ConditionDeserializer
					.deserializeCondition(ontologyReader, conditionJson);
			OWLOntology generatedOntology = ontologyGenerator
					.convertToOwlIndividualOntology(OntologyHelper.nameSpace
							+ indiviualName, condition);

			try {
				OwlIndividual individual = ontologyReader
						.getIndividual(OntologyHelper.nameSpace + indiviualName);
				if (individual != null)
					return ok("Indvidual name is already used");
			} catch (Exception e) {
			}

			OntologyHelper.checkOntology(generatedOntology);
			OntoClass owlClass = getOwlClass("Worker");

			OntologyHelper.saveOntology(generatedOntology);

			return ok("ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ok(e.toString());
		}

	}
/*
	public static Result display(String className) {

		if(className == null)
			className  = "Offer";
		OntoClass owlClass = getOwlClass(className);

		if (owlClass == null) {
			return ok("Can't find the required classs");
		}
		
		List<OwlIndividual> individuals = ontologyReader
				.getIndividuals(owlClass);

		return ok(show.render(className, individuals));
	}
	
	*/
}