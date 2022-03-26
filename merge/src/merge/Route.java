/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merge;

import java.util.ArrayList;

/**
 *
 * @author amin
 */
public class Route {
    
    ArrayList <ClientF> clients;
	double cost;
		double pen;
int load; // load of the route (initially = 0)
	int capacity; // capacity variable indicating the capacity of the vehicles
	
	Route() 
	{
            load = 0;
		capacity = 50;
		cost = 0;
                pen = 0;
		clients = new ArrayList<ClientF>();
	}
}