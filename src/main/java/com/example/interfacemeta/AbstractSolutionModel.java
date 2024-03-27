package com.example.interfacemeta;

import java.util.ArrayList;

public abstract class AbstractSolutionModel {
	protected MKSP mksp;
	
	public AbstractSolutionModel(MKSP mksp) {
		this.mksp = mksp;
	}

	// public abstract boolean isConsistent();
	
	// public abstract boolean satisfiesSackConstraints(int sackNumber);

	public boolean isValide(ArrayList<Integer> solution) {
		return solution.size() == mksp.getObjectsCount();
	}
	
	// return the number of sacks satisfied with the solution
	public int evaluate(ArrayList<Integer> solution) {
		int somme = 0;
		for(int i = 0; i < solution.size(); i++) {
			if(solution.get(i) != -1) somme += mksp.getObjectValue(i);
		}

		return somme;
	}

}
