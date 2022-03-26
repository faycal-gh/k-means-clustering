/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merge;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import merge.KMeans;

/**
 *
 * @author amin
 */
public class Fonction {

    public static void main(String[] args) {
        // TODO code application logic here

        //Creating the depot
        ClientF centre = new ClientF();
        centre.PositionX = 50;
        centre.PositionY = 50;
        centre.demand = 0;

        int myBirthNumber = 8021994;

        Random ran = new Random(myBirthNumber);

        int numberOfCustomers = 30;

        //Creating the list with the customers		
        //CLUSTER RECORD
        ArrayList<ClientF> customers = new ArrayList<>(); // with this code we initialize the new ArrayList, which is called "customers"
        for (int i = 1; i <= numberOfCustomers; i++) {
            ClientF cust = new ClientF();
            cust.PositionX = ran.nextInt(100);
            cust.PositionY = ran.nextInt(100);
            cust.li = ran.nextInt(100);
            cust.demand = 4 + ran.nextInt(7);
            customers.add(cust);
        }

        //Build the allCustomers array and the corresponding distance matrix
        ArrayList<ClientF> allCustomers = new ArrayList<>();
        KMeans kk = new KMeans();
        Map<Cluster, List<Client>> m = kk.printClusterInformation();
        System.out.println("================================================");
        for (Map.Entry<Cluster, List<Client>> entry : m.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("================================================");

        allCustomers.add(centre);
        for (int i = 0; i < customers.size(); i++) {
            ClientF cust = customers.get(i);
            allCustomers.add(cust);
        }

        for (int i = 0; i < allCustomers.size(); i++) {
            ClientF nd = allCustomers.get(i);
            nd.idClient = i;
        }

        // This is a 2-D array which will hold the distances between node pairs
        // The [i][j] element of this array is the distance required for moving 
        // from the i-th node of allNodes (node with id : i)
        // to the j-th node of allNodes list (node with id : j)
        System.out.println("======================================================");
        for (int i = 0; i < allCustomers.size(); i++) {
            System.out.println(allCustomers.get(i).PositionX);
        }
        System.out.println("======================================================");
        double[][] distanceMatrix = new double[allCustomers.size()][allCustomers.size()];
        for (int i = 0; i < allCustomers.size(); i++) {
            ClientF from = allCustomers.get(i);

            for (int j = 0; j < allCustomers.size(); j++) {
                ClientF to = allCustomers.get(j);

                double Delta_x = (from.PositionX - to.PositionX);
                double Delta_y = (from.PositionY - to.PositionY);
                double distance = Math.sqrt((Delta_x * Delta_x) + (Delta_y * Delta_y));

                distance = Math.round(distance);

                distanceMatrix[i][j] = distance;

            }
        }
        Double TempD, TempsA;
        int retard = 0;
        double[] Pénalite = new double[allCustomers.size()];
        double penalite = 0;
        for (int i = 0; i < allCustomers.size(); i++) {
            ClientF f = allCustomers.get(i);

            for (int j = i + 1; j < allCustomers.size(); j++) {
                ClientF t = allCustomers.get(j);

                TempD = distanceMatrix[i][j] / 80;
                TempsA = (f.li) + TempD;
                if (TempsA > t.li) {
                    retard++;
                } else {
                    retard = 0;

                }

                Pénalite[j] = penalite;

                penalite = retard * 50;
            }
        }

        // This is the solution object - It will store the solution as it is iteratively generated
        // The constructor of Solution class will be executed
        Solution s = new Solution();

        int numberOfVehicles = 3;

        //Let rtList be the ArrayList of Vehicles assigned to the solution "s".		
        ArrayList<Route> rtList = s.routes;
        for (int i = 1; i <= numberOfVehicles; i++) {
            Route routeTemp = new Route();
            routeTemp.load = 0;
            routeTemp.capacity = 50;
            routeTemp.cost = 0;
            routeTemp.pen = 0;
            rtList.add(routeTemp);
        }

        // indicate that all customers are non-routed
        for (int i = 0; i < customers.size(); i++) {
            customers.get(i).isRouted = false;
        }


        /* 
		 * ##################
		 * # INITIALIZATION #
		 * ##################
		 * */
        // Setting a count for customers who are not inserted in the solution yet
        int notInserted = numberOfCustomers;

        for (int j = 0; j < numberOfVehicles; j++) {
            ArrayList<ClientF> nodeSequence = rtList.get(j).clients;
            nodeSequence.add(centre);

            int capacity = rtList.get(j).capacity; // The capacity of this vehicle (=50)
            int load = rtList.get(j).load; // The initial load of this vehicle (=0)
            // Setting a boolean variable that shows the assignment is not final yet.
            boolean isFinal = false;
            // If we have no more customers to insert, we add the depot at the end of the sequence. 
            if (notInserted == 0) {
                isFinal = true;
                nodeSequence.add(centre);
            }
            while (isFinal == false) {
                //this will be the position of the nearest neighbor customer -- initialization to -1
                int positionOfTheNextOne = -1;
                // This will hold the minimal cost for moving to the next customer - initialized to something very large
                double bestCostForTheNextOne = Double.MAX_VALUE;
                //This is the last customer of the route (or the depot if the route is empty)
                ClientF lastInTheRoute = nodeSequence.get(nodeSequence.size() - 1);
                //identify nearest non-routed customer
                for (int k = 0; k < customers.size(); k++) {
                    // The examined customer is called candidate
                    ClientF candidate = customers.get(k);
                    // if this candidate has not been visited by a vehicle
                    if (candidate.isRouted == false) {
                        //This is the cost for moving from the last visited customer to the candidate customer
                        double trialCost = distanceMatrix[lastInTheRoute.idClient][candidate.idClient];
                        //If this is the minimal cost found so far -> store this cost and the position of this best candidate
                        if (trialCost < bestCostForTheNextOne && candidate.demand <= capacity) {
                            positionOfTheNextOne = k;
                            bestCostForTheNextOne = trialCost;
                        }
                    }
                } // moving on to the next (candidate) customer

                // Step 2: Push the customer in the solution
                // We have found the customer to be pushed.
                // He is located in the positionOfTheNextOne position of the customers list
                // Let's inert him and update the cost of the solution and of the route, accordingly
                if (positionOfTheNextOne != -1) {
                    ClientF insertedNode = customers.get(positionOfTheNextOne);
                    //Push the customer in the sequence
                    nodeSequence.add(insertedNode);

                    rtList.get(j).cost = rtList.get(j).cost + bestCostForTheNextOne;
                    s.cost = s.cost + bestCostForTheNextOne;

                    rtList.get(j).pen = rtList.get(j).pen + bestCostForTheNextOne;
                    s.pen = s.pen + bestCostForTheNextOne;
                    insertedNode.isRouted = true;
                    capacity = capacity - insertedNode.demand;
                    rtList.get(j).load = load + insertedNode.demand;
                    load = load + insertedNode.demand;
                    notInserted = notInserted - 1;

                } else {
                    //if the positionOfTheNextOne = -1, it means there is no suitable candidate for this vehicle. So, we add the depot.
                    nodeSequence.add(centre);
                    rtList.get(j).cost = rtList.get(j).cost + distanceMatrix[lastInTheRoute.idClient][0];
                    s.cost = s.cost + distanceMatrix[lastInTheRoute.idClient][0];

                    rtList.get(j).pen = rtList.get(j).pen + Pénalite[lastInTheRoute.idClient];
                    s.pen = s.pen + Pénalite[lastInTheRoute.idClient];
                    isFinal = true;
                }
            }
        }

        /* 
		 * ###########
		 * # RESULTS #
		 * ###########
		 * */
        for (int j = 0; j < numberOfVehicles; j++) {
            int vehicle_number = j + 1;
            System.out.println("Route for Vehicle #" + vehicle_number);
            for (int k = 0; k < s.routes.get(j).clients.size(); k++) {
                System.out.print(s.routes.get(j).clients.get(k).idClient + "  ");
            }
            System.out.println("");
            System.out.println("Route Cost = " + s.routes.get(j).cost);
//System.out.println("Final Load: " + s.routes.get(j).load);
            //	System.out.println("Final Remaining Capacity = " + (rtList.get(j).capacity - s.routes.get(j).load));
            System.out.println("----------------------------------------");

        }
        System.out.println("Total Solution Cost = " + s.cost);

        // End of GCS Instance
        /* 
		 * ###################################################
		 * # LOCAL SEARCH USING INTER+INTRA-RELOCATION MOVES #
		 * ###################################################
		 * */
        //this is a boolean flag (true/false) for terminating the local search procedure
        boolean terminationCondition = false;

        //this is a counter for holding the local search iterator
        int localSearchIterator = 0;

        //Here we apply the best relocation move local search scheme
        //This is an object for holding the best relocation move that can be applied to the candidate solution
        RelocationMove rm = new RelocationMove(); // in order to apply one relocation  move for all routes - dont want to lose previous if i change vehicle

        //Initialize the relocation move rm
        rm.positionOfRelocated = -1;
        rm.positionToBeInserted = -1;
        rm.fromRoute = 0;
        rm.toRoute = 0;
        rm.fromMoveCost = Double.MAX_VALUE;
        rm.toMoveCost = Double.MAX_VALUE;

        // Until the termination condition is set to true repeat the following block of code
        while (terminationCondition == false) {
            //With this function we look for the best relocation move
            //the characteristics of this move will be stored in the object rm
            findBestRelocationMove(rm, s, distanceMatrix, numberOfVehicles);

            // If rm (the identified best relocation move) is a cost improving move, or in other words
            // if the current solution is not a local optimum
            if (rm.moveCost < 0) {
                //This is a function applying the relocation move rm to the candidate solution
                applyRelocationMove(rm, s, distanceMatrix);

            } else {
                //if no cost improving relocation move was found,
                //or in other words if the current solution is a local optimum
                //terminate the local search algorithm
                terminationCondition = true;
            }

            localSearchIterator = localSearchIterator + 1;
        }


        /* 
		 * ###########
		 * # RESULTS #
		 * ###########
		 * */
        System.out.println("");
        for (int j = 0; j < numberOfVehicles; j++) {
            int vehicle_number = j + 1;
            System.out.println("Updated Route for Vehicle #" + vehicle_number);
            for (int k = 0; k < s.routes.get(j).clients.size(); k++) {
                System.out.print(s.routes.get(j).clients.get(k).idClient + "  ");
            }
            System.out.println("");
            System.out.println("Route Cost = " + s.routes.get(j).cost);
            System.out.println("Route Penalité = " + s.routes.get(j).pen);
            //System.out.println("Final Load: " + s.routes.get(j).load);
            //System.out.println("Final Remaining Capacity = " + (rtList.get(j).capacity - s.routes.get(j).load));
            System.out.println("----------------------------------------");
        }
        System.out.println("Total Solution Cost = " + s.cost);
        System.out.println("Total Solution Penalité = " + s.pen);

        System.out.println("Total Local Search Iterations = " + localSearchIterator);
        //System.out.println("Total Local Search Relocations = " + (localSearchIterator-1));

    } // End of main method

    private static void findBestRelocationMove(RelocationMove rm, Solution s, double[][] distanceMatrix, int numberOfVehicles) {
        //This is a variable that will hold the cost of the best relocation move
        double bestMoveCost = Double.MAX_VALUE;

        //We will iterate through all available vehicles
        //Vehicles to relocate FROM
        for (int from = 0; from < numberOfVehicles; from++) {
            // Vehicles to relocate TO
            for (int to = 0; to < numberOfVehicles; to++) {

                for (int fromIndex = 1; fromIndex < s.routes.get(from).clients.size() - 1; fromIndex++) {
                    //Node A is the predecessor of B
                    ClientF A = s.routes.get(from).clients.get(fromIndex - 1);

                    //Node B is the relocated node
                    ClientF B = s.routes.get(from).clients.get(fromIndex);

                    //Node C is the successor of B
                    ClientF C = s.routes.get(from).clients.get(fromIndex + 1);

                    //We will iterate through all possible re-insertion positions for B
                    for (int afterIndex = 0; afterIndex < s.routes.get(to).clients.size() - 1; afterIndex++) {

                        if ((afterIndex != fromIndex && afterIndex != fromIndex - 1) || from != to) {
                            //Node F the node after which B is going to be reinserted
                            ClientF F = s.routes.get(to).clients.get(afterIndex);

                            //Node G the successor of F
                            ClientF G = s.routes.get(to).clients.get(afterIndex + 1);

                            //The arcs A-B, B-C, and F-G break
                            double costRemovedFrom = distanceMatrix[A.idClient][B.idClient] + distanceMatrix[B.idClient][C.idClient];
                            double costRemovedTo = distanceMatrix[F.idClient][G.idClient];

                            //The arcs A-C, F-B and B-G are created
                            double costAddedFrom = distanceMatrix[A.idClient][C.idClient];
                            double costAddedTo = distanceMatrix[F.idClient][B.idClient] + distanceMatrix[B.idClient][G.idClient];

                            //This is the cost of the move, or in other words
                            //the change that this move will cause if applied to the current solution
                            double fromMoveCost = costAddedFrom - costRemovedFrom;
                            double toMoveCost = costAddedTo - costRemovedTo;

                            //If this move is the best found so far
                            double moveCost = fromMoveCost + toMoveCost;
                            if ((moveCost < bestMoveCost) && (from == to || (s.routes.get(to).load + s.routes.get(from).clients.get(fromIndex).demand <= s.routes.get(to).capacity))) {
                                //set the best cost equal to the cost of this solution
                                bestMoveCost = moveCost;

                                //store its characteristics
                                rm.positionOfRelocated = fromIndex;
                                rm.positionToBeInserted = afterIndex;
                                rm.toMoveCost = toMoveCost;
                                rm.fromMoveCost = fromMoveCost;
                                rm.fromRoute = from;
                                rm.toRoute = to;
                                rm.moveCost = moveCost;

                                if (from != to) {
                                    rm.fromUpdLoad = s.routes.get(from).load - s.routes.get(from).clients.get(fromIndex).demand;
                                    rm.toUpdLoad = s.routes.get(to).load + s.routes.get(from).clients.get(fromIndex).demand;
                                } else {
                                    rm.fromUpdLoad = s.routes.get(from).load;
                                    rm.toUpdLoad = s.routes.get(to).load;
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    //This function applies the relocation move rm to solution s
    private static void applyRelocationMove(RelocationMove rm, Solution s, double[][] distanceMatrix) {
        //This is the node to be relocated
        ClientF relocatedNode = s.routes.get(rm.fromRoute).clients.get(rm.positionOfRelocated);

        //Take out the relocated node
        s.routes.get(rm.fromRoute).clients.remove(rm.positionOfRelocated);

        //Reinsert the relocated node into the appropriate position
        //Where??? -> after the node that WAS (!!!!) located in the rm.positionToBeInserted of the route
        //Watch out!!! 
        //If the relocated customer is reinserted backwards we have to re-insert it in (rm.positionToBeInserted + 1)
        if (((rm.positionToBeInserted < rm.positionOfRelocated) && (rm.toRoute == rm.fromRoute)) || (rm.toRoute != rm.fromRoute)) {
            s.routes.get(rm.toRoute).clients.add(rm.positionToBeInserted + 1, relocatedNode);
        } ////else (if it is reinserted forward) we have to re-insert it in (rm.positionToBeInserted)
        else {
            s.routes.get(rm.toRoute).clients.add(rm.positionToBeInserted, relocatedNode);
        }

        //update the cost of the solution and the corresponding cost of the route object in the solution
        s.cost = s.cost + rm.moveCost;
        s.routes.get(rm.toRoute).cost = s.routes.get(rm.toRoute).cost + rm.toMoveCost;
        s.routes.get(rm.fromRoute).cost = s.routes.get(rm.fromRoute).cost + rm.fromMoveCost;
        if (rm.toRoute != rm.fromRoute) {
            s.routes.get(rm.toRoute).load = rm.toUpdLoad;
            s.routes.get(rm.fromRoute).load = rm.fromUpdLoad;
        } else {
            s.routes.get(rm.toRoute).load = rm.toUpdLoad;
        }

    }

} // end of Class Q5

class Solution {

    double cost;
    double pen;
    ArrayList<Route> routes;

    Solution() {
        routes = new ArrayList<Route>();
        cost = 0;
        pen = 0;

    }
}

class RelocationMove {

    int fromRoute; // From which route we remove the customer
    int toRoute; // To which route we insert the customer
    int positionOfRelocated; // In which position (of the initial route) we find the customer we want to relocate
    int positionToBeInserted; // In which position (of the new route) we want to insert the customer
    double fromMoveCost; // Move Cost for the route from which we remove a customer
    double toMoveCost; // Move Cost for the route to which we add a customer
    double moveCost; // Total move cost = fromMoveCost + toMoveCost
    int fromUpdLoad; // Updated load for the route from which we remove a customer 
    int toUpdLoad; // Updated load for the route to which we insert a customer

    RelocationMove() {
    }
}
