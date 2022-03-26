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
public class Vehicule {
    private int idVehiculeP;
    private int tempsChome;
    private int totalCharge=0;
    public double coutUtilisation;
    public int capacity;
    public int Dem;
     public int Open;
    public boolean Closed;
    ArrayList<ClientF> clients = new ArrayList<>();
    public ArrayList<CentreV> RouteCentre = new ArrayList<CentreV>();
    public ArrayList<ClientF> Route = new ArrayList<ClientF>();

    public Vehicule(int idVehiculeP) {
        this.idVehiculeP = idVehiculeP;
    }

    public Vehicule(int idVehiculeP, double coutUtilisation, int capacity) {
        this.idVehiculeP = idVehiculeP;
        this.coutUtilisation = coutUtilisation;
        this.capacity = capacity;
        this.Route.clear();
    }

    public Vehicule(int idVehiculeP, int capacity) {
        this.idVehiculeP = idVehiculeP;
        this.capacity = capacity;
    }

    public int getIdVehiculeP() {
        return idVehiculeP;
    }

    public int getTempsChome() {
        return tempsChome;
    }

    public int getTotalCharge() {
        return totalCharge;
    }

    public double getCoutUtilisation() {
        return coutUtilisation;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<ClientF> getRoute() {
        return clients;
    }

    public void setIdVehiculeP(int idVehiculeP) {
        this.idVehiculeP = idVehiculeP;
    }

    public void setTempsChome(int tempsChome) {
        this.tempsChome = tempsChome;
    }

    public void setTotalCharge(int totalCharge) {
        this.totalCharge = totalCharge;
    }

    public void setCoutUtilisation(double coutUtilisation) {
        this.coutUtilisation = coutUtilisation;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setRoute(ArrayList<ClientF> Route) {
        this.clients = clients;
    }
    
       public void AddNode(ClientF client )//Add Customer to Vehicle Route
    {
        
        Route.add(client);
               this.Open= client.idClient;
        this.Dem +=  client.demand;

    }

 public boolean CheckIfFits(int dem) //Check if we have Capacity Violation
    {
        return ((Dem + dem <= capacity));
    }

}
