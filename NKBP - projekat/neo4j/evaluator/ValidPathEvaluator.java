package constraint.evaluator;

import constraint.schema.Labels;
import constraint.schema.Properties;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;
import org.neo4j.logging.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ValidPathEvaluator implements Evaluator {

	private final Log log;
	private final long maxWeight;

	public ValidPathEvaluator(Log log, long w) {
		this.log = log;
		this.maxWeight = w;
	}

	public Evaluation evaluate(Path path) {
		Iterable<Relationship> relationships = path.relationships();
		Iterator<Relationship> iterator = relationships.iterator();
		int curr_weight = 0;

		while (iterator.hasNext()) {
			Relationship r = iterator.next();
			RelationshipType type = r.getType();
			String name = type.toString();

			curr_weight = ((Long) r.getProperty(Properties.WEIGHT)).intValue();

		}

		if (curr_weight <= maxWeight)
			return Evaluation.INCLUDE_AND_CONTINUE;
		else
			return Evaluation.EXCLUDE_AND_PRUNE;

	}
}
