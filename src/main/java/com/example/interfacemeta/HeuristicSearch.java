package com.example.interfacemeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class HeuristicSearch extends AbstractSolutionModel {

    private int developedNodes = 0;

    public HeuristicSearch(MKSP mksp) {
        super(mksp);
    }

    class Node {
        ArrayList<Integer> solution;
        int cost = 0;
        int heuristic = 0;

        public Node(ArrayList<Integer> solution) {
            this.solution = solution;
        }

        public Node(ArrayList<Integer> solution, int cost, int heuristic) {
            this.solution = solution;
            this.cost = cost;
            this.heuristic = heuristic;
        }

        public int getF(){
            return this.cost + this.heuristic;
        }
        
    }

    public ArrayList<Integer> AStar(){
        developedNodes = 0;
        
        PriorityQueue<Node> ouvert = new PriorityQueue<>(Comparator.comparingInt(Node::getF).reversed());

        ouvert.add(new Node(new ArrayList<Integer>(),0,0));

        while(!ouvert.isEmpty()) {
            Node n = ouvert.poll();

            developedNodes++;

            if(etatFinal(n.solution)){
                //System.out.println("Solution : " + n.solution + " evaluation = " + evaluate(n.solution));
                developedNodes += ouvert.size();
                return n.solution;
            }

            ouvert.addAll(successeur(n));
        }

        return new ArrayList<Integer>();
    }


    //G(n) = value actuel
    //H(n) = Total poids - poids_actuel ? valeurs maximale possible

    private List<Node> successeur(Node solution) {
        ArrayList<Node> succ = new ArrayList<>();

        int obj = solution.solution.size();
        int poids_obj = mksp.getObjectWeight(obj);
        HashMap<Integer,Integer> sacks_weights = getSacksWeights(solution.solution);


        for (int i = -1; i<mksp.getSacksCount(); i++){
            ArrayList<Integer> curr = new ArrayList<Integer>();

            curr.addAll(solution.solution);
            curr.add(i);

            if (i == -1) {
                succ.add(
                        new Node(
                                curr,
                                solution.cost,
                                max_obj_value_left(sacks_weights,obj+1)
                        )
                );

              /*  System.out.println("successeur : " + curr + " g : " + solution.cost
                        + " h : " + max_obj_value_left(sacks_weights,obj+1));*/

            }


            else if(sacks_weights.getOrDefault(i,0) + poids_obj <= mksp.getSackWeight(i)){
                int curr_value = mksp.getCurrentValue(curr);


                HashMap<Integer,Integer> sacks_weights_curr = getSacksWeights(curr);

                int h = max_obj_value_left(sacks_weights_curr,obj+1);
               /* System.out.println("successeur : " + curr + " g : " + curr_value
                + " h : " + h);*/

                succ.add(new Node(
                            curr,
                         curr_value,
                       h
                        ));
            }

        }

        return succ;
    }

    private int max_obj_value_left(HashMap<Integer,Integer> sacks_weights,int nb_obj) {
        int max = 0;
        for (int i = nb_obj; i < mksp.getObjectsCount(); i++){
            for(int j = 0; j < mksp.getSacksCount(); j++) {

                if (!sacks_weights.containsKey(j)) {
                    if (mksp.getObjectWeight(i) <= mksp.getSackWeight(j)) {
                        if(max < mksp.getObjectValue(i))
                            max = mksp.getObjectValue(i);
                        break;
                    }
                }

                else if (mksp.getObjectWeight(i) <= mksp.getSackWeight(j) - sacks_weights.get(j)) {
                    if(max < mksp.getObjectValue(i))
                        max = mksp.getObjectValue(i);
                    break;
                }

            }
        }

        return max;
    }

    private boolean etatFinal(ArrayList<Integer> solution) {
        return solution.size() == mksp.getObjectsCount();
    }

    private HashMap<Integer,Integer> getSacksWeights(ArrayList<Integer> solution){
        HashMap<Integer, Integer> dict = new HashMap<>();
        
        int obj = 0;

        for(int x:solution) {
            if (x==-1)
                continue;

            if(!dict.containsKey(x))
                dict.put(x,mksp.getObjectWeight(obj));
            else
                dict.put(x,dict.get(x) + mksp.getObjectWeight(obj));

            obj++;
        }
        
        return dict;
    }


    public int getDevelopedNodes() {
        return developedNodes;
    }

}


