/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merge;

import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author amin
 */
public class Principale {
  // public final double coutCarburant=1.40;
    private int nbVehicule;
    private int nbCentre;
    private int nbClient;
    static int coutTrajet;
    
    static CentreV [] centres;
    static ClientF [] clients;
    static Vehicule [] vehicule;
  
///////////////:constructeur

    public Principale( int nbVehicule, int nbCentre, int nbClient) {
         
        this.nbVehicule= nbVehicule;
        this.nbCentre = nbCentre;
        this.nbClient = nbClient;
        centres=new CentreV[nbCentre];

        clients=new ClientF[nbClient];
        vehicule =new Vehicule[nbVehicule];
         
    }
    
    
    ////////Methode Distance Eucledienne
    
    public static double euclid(int x1, int y1, int x2, int y2){
        double eucl =  (double) sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
       return eucl;
    }
    
    //////////////Distance Centre---ClientF
    public static double distanceCCus1(CentreV c, ClientF cus){
        double disDC=euclid(c.getPositionCentreX(),c.getPositionCentreX(),cus.getPositionX(),cus.getPositionY());
        //System.out.println("distaaaaaaaaaaaaance entre "+ c.getIdCentre()+ "  et  " + cus.getIdClient()+ " est " + disDC);
        return disDC;
        
    }
    //  public static double distanceCCus2(ClientF cus, CentreV c){
     //   double disDC=euclid(cus.getPositionX(),cus.getPositionY(),c.getPositionCentreX(),c.getPositionCentreX());
        //System.out.println("distaaaaaaaaaaaaance entre "+ c.getIdCentre()+ "  et  " + cus.getIdClient()+ " est " + disDC);
     //   return disDC;
        
   // }
    //////////////Distance ClientF---ClientF
    public static double distanceCusCus(ClientF cus1, ClientF cus2){
        double disDC=euclid(cus1.getPositionX(),cus1.getPositionY(),cus2.getPositionX(),cus2.getPositionY());
        return disDC;
    }
      
}
