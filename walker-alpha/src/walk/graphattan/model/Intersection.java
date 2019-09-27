package walk.graphattan.model;

import java.util.HashSet;
import java.util.Set;

/*
 * The Intersection class describes an area in which zero or many streets, paths, 
 * or other non-pedestrian routes cross. For the present iteration of this model, 
 * these 'routes' are represented as Strings.
 * Many Vertices may be associated with the same Intersection. 
 */
public class Intersection {

	public Intersection() {
		super();
	}
	public Intersection(String description) {
		super();
		this.description = description;
	}
	private Set<String> routes = new HashSet<>();
	private String description;
	public Set<String> getRoutes() {
		return routes;
	}
	public boolean addRoute(String route) {
		return routes.add(route);
	}
	public boolean removeRoute(String route) {
		return routes.remove(route);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((routes == null) ? 0 : routes.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Intersection other = (Intersection) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (routes == null) {
			if (other.routes != null)
				return false;
		} else if (!routes.equals(other.routes))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Intersection [routes=" + routes + ", description=" + description + "]";
	}

}
