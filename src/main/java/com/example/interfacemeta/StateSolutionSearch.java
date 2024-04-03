package com.example.interfacemeta;

import java.util.*;

public class StateSolutionSearch extends AbstractSolutionModel{

    private int developedNodes;

    public int getDevelopedNodes() {
        return developedNodes;
    }

    public StateSolutionSearch(MKSP mksp) {
        super(mksp);
    }


    public boolean isValide(ArrayList<Integer> solution) {
        return solution.size() == mksp.getObjectsCount();
    }

    public int evaluate(ArrayList<Integer> solution) {

        int somme = 0;
        for(int i = 0; i < solution.size(); i++) {
            if(solution.get(i) != -1) somme += mksp.getObjectValue(i);
        }

        return somme;
    }


    public ArrayList<Integer> DFS(){
        developedNodes = 0;

        ArrayList <Integer> initialSolution= new ArrayList<>();
        Stack <ArrayList<Integer> > ouvert = new Stack<>();
        ArrayList<Integer> best = new ArrayList<>();

        ouvert.add(initialSolution);

        while(!ouvert.isEmpty()) {
            ArrayList <Integer> n = ouvert.pop();
            developedNodes++;

            if(isValide(n) && ( best.isEmpty() || evaluate(n) > evaluate(best) )) {

                //int ev = evaluate(n);

                best.clear();
                for(Integer x : n) best.add(x);

               // System.out.println("new best : " + best + " evaluation : " + ev);

            }

            ArrayList<ArrayList<Integer>> childs = successeur(n);

            for(ArrayList<Integer> child : childs) ouvert.push(child);
        }

        return best;
    }

    public ArrayList<Integer> BFS(){
        developedNodes = 0;

        ArrayList<Integer> initialSolution = new ArrayList<>();
        ArrayDeque<ArrayList<Integer>> ouvert = new ArrayDeque<>(); // une Liste FIFO
        ArrayList<Integer> best = new ArrayList<>();

        ouvert.add(initialSolution);

        while(!ouvert.isEmpty()) {
            developedNodes++;
            ArrayList <Integer> n = ouvert.removeFirst();

            if(isValide(n) && ( best.isEmpty() || evaluate(n) > evaluate(best) )) {

                best.clear();
                for(Integer x : n) best.add(x);

                //System.out.println("new best : " + best + " evaluation : " + evaluate(best));

            }

            ArrayList<ArrayList<Integer>> childs = successeur(n);

            for(ArrayList<Integer> child : childs) 
                ouvert.addLast(child);
        }

        return best;
    }

    
    private ArrayList<ArrayList<Integer>> successeur(ArrayList<Integer> solution) {

        ArrayList<ArrayList<Integer>> succ = new ArrayList<>();

        int nb_obj = mksp.getObjectsCount();
        int nb_sack = mksp.getSacksCount();

        if(solution.size() < nb_obj){
            int next_obj = solution.size(); // prochain objet a considerer
            int next_obj_weight = mksp.getObjectWeight(next_obj);

            // ajouter le cas ou l'objet nest pas selectionner
            succ.add(new ArrayList<Integer>());
            for(int x: solution) succ.get(succ.size() - 1).add(x);

            succ.get(succ.size() - 1).add(-1);

            // calculer le poids cummuler des objets pour chaque sac
            HashMap<Integer, Integer> dict= new HashMap<>();
            int i = 0;
            for(int x:solution) {
                if(!dict.containsKey(x)) dict.put(x,mksp.getObjectWeight(i));
                else  dict.put(x,dict.get(x) + mksp.getObjectWeight(i));
                i++;
            }

            //verifier les cas d'ajouts :
            for(int j = 0; j < nb_sack; j++) {
                int poids_actuel = 0;
                if(dict.containsKey(j)) {
                    poids_actuel = dict.get(j);
                }

                // poids du sac reste superieur a la somme du poids actuel + le nouvel objet
                if(mksp.getSackWeight(j) >= (poids_actuel + next_obj_weight)) {
                        // ajouter un autre successeur
                        succ.add(new ArrayList<Integer>());
                        for(int x: solution) succ.get(succ.size() - 1).add(x);

                        succ.get(succ.size() - 1).add(j);

                }

            }


            }

        return succ;

    }
}
