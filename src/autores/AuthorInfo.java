package autores;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class AuthorInfo {
	private String name;
	private int soloPublications;
	private int jointPublications;
	private TreeMap<String, Integer> coauthorsInfo; 

	public AuthorInfo(String name) {
		this.name = name;
		this.soloPublications = 0;
		this.jointPublications = 0;
		this.coauthorsInfo = new TreeMap<>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getTotalPublications() {
		return this.soloPublications + this.jointPublications;
	}
	
	public int getSoloPublications() {
		return this.soloPublications;
	}
	
	public int getJointPublications() {
		return this.jointPublications;
	}
	
	public void addPublication(Collection<String> coauthors) {
		Integer coauthorTotal;
		
		for (String coauthor : coauthors) {
			if (!coauthor.equals(this.name)) {
				coauthorTotal = this.coauthorsInfo.get(name);
				if (coauthorTotal == null) this.coauthorsInfo.put(name, 1);
				else this.coauthorsInfo.put(name, coauthorTotal + 1);
			}
		}
	}
	

	public Set<Tuple<String, Integer>> topCoauthors(int numberOfCoauthors) {
		TreeSet<Tuple<String, Integer>> ret = new TreeSet<Tuple<String, Integer>>(new AuthorPubsTupleComparator());
		Tuple<String, Integer> t;
		
		for (Map.Entry<String, Integer> entry : this.coauthorsInfo.entrySet()) {
			t = new Tuple<String, Integer>(entry.getKey(), entry.getValue());
			
			if (ret.size() < numberOfCoauthors) ret.add(t);
			else if (t.getSecond() > ret.first().getSecond()) {
				ret.pollFirst();
				ret.add(t);
			}
		}
		
		return ret;
	}
	
	public boolean onlySolo() {
		return this.jointPublications == 0;
	}
	
	public boolean neverSolo() {
		return this.soloPublications == 0;
	}
	
	public Map<String, Integer> getCoauthorsInfo() {
		return this.coauthorsInfo;
	}
	
	public Set<String> getCoauthors() {
		return this.coauthorsInfo.keySet();
	}
	
	public int totalCoauthors() {
		return this.coauthorsInfo.size();
	}
}