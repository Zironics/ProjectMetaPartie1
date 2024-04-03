package com.example.interfacemeta;

import java.util.ArrayList;

public class MKSP {
	int[] sackWeights;

	int[] objectWeights;
	int[] objectsValues;
	
	public MKSP(int[] sackWeights, int[] objectWeights, int[] objectsValues) {

		if(objectsValues.length != objectWeights.length) {
			System.out.println("objectsValues and objectWeights cannot have different dimensons");
			return;
		}
		
		this.objectsValues = objectsValues;
		this.objectWeights = objectWeights;
		this.sackWeights = sackWeights;
	}

	public int getSacksCount() {
		return this.sackWeights.length;
	}
	
	public int getObjectsCount() {
		return this.objectsValues.length;
	}

	public int getSackWeight(int sack) {
		return this.sackWeights[sack];
	}
	public int getObjectWeight(int object) {
		return this.objectWeights[object];
	}

	public int getObjectValue(int object) {
		return this.objectsValues[object];
	}

	public int getTotalValue(){
		int sum = 0;
		for(int i = 0; i< getObjectsCount(); i++)
			sum += getObjectValue(i);
		return sum;
	}

	public int getTotalWeight(){ // modifie pour retouner le poids des sacs au lieu d'objets
		int sum = 0;
		for(int i = 0; i< getSacksCount(); i++)
			sum += getSackWeight(i);
		return sum;
	}

	public int getCurrentValue(ArrayList<Integer> solution) {
		int somme_value = 0;

		int obj = 0;

		for(int x:solution) {
			if (x==-1)
			{
				obj++;
				continue;
			}

			somme_value += getObjectValue(obj);
			obj++;
		}

		return somme_value;

	}

	public double getPerformance(ArrayList<Integer> solution){
		return (
			getCurrentValue(solution)///getTotalValue()
		);
	}


}
