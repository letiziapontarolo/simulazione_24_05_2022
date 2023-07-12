package it.polito.tdp.itunes.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private Graph<Track, DefaultWeightedEdge> grafo;
	private ItunesDAO dao;
	private Map<Integer, Track> trackIdMap;
	private List<Arco> archi;
	
	public Model() {
		
		dao = new ItunesDAO();
		
	}
	
	public List<String> listaGeneri() {
		return this.dao.listaGeneri();
	}
	
	public void creaGrafo(String genere) {
		
		trackIdMap = new HashMap<Integer, Track>();
		grafo = new SimpleWeightedGraph<Track, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		this.dao.creaVertici(trackIdMap, genere);
		Graphs.addAllVertices(this.grafo, trackIdMap.values());
		
		archi = this.dao.listaArchi(trackIdMap, genere);
		
		for (Arco a : archi) {
			Graphs.addEdgeWithVertices(this.grafo, a.getT1(), a.getT2(), a.getPeso());
		}
	
	}
	
	public String deltaMassimo() {
		
		String result = "";
		int max = 0;
		
		for (Arco a : archi) {
			if (a.getPeso() > max) {
				max = a.getPeso();
			}
		}
		
		for (Arco aa : archi) {
			if (aa.getPeso() == max) {
				result = result + aa.getT1() + "***" + aa.getT2() + "->" + max + "\n";
			}
		}
		return result;	
	}
	
	public int numeroVertici() {
		return this.grafo.vertexSet().size();
		}
	
		 public int numeroArchi() {
		return this.grafo.edgeSet().size();
		}
	
	
}
