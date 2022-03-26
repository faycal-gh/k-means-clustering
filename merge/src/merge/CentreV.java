/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author amin
 */
public class CentreV {
    public int IdCentre;
    public int PositionCentreX;
    public int PositionCentreY;

    public CentreV() {
    }

    public CentreV(int IdCentre, int PositionCentreX, int PositionCentreY) {
        this.IdCentre = IdCentre;
        this.PositionCentreX = PositionCentreX;
        this.PositionCentreY = PositionCentreY;
    }

    public CentreV(int PositionCentreX, int PositionCentreY) {
        this.PositionCentreX = PositionCentreX;
        this.PositionCentreY = PositionCentreY;
    }

    public int getIdCentre() {
        return IdCentre;
    }

    public int getPositionCentreX() {
        return PositionCentreX;
    }

    public int getPositionCentreY() {
        return PositionCentreY;
    }

    public void setIdCentre(int IdCentre) {
        this.IdCentre = IdCentre;
    }

    public void setPositionCentreX(int PositionCentreX) {
        this.PositionCentreX = PositionCentreX;
    }

    public void setPositionCentreY(int PositionCentreY) {
        this.PositionCentreY = PositionCentreY;
    }
   
     protected static CentreV createRandomPoint(int min, int max) {
    	Random r = new Random();
    	int PositionCentreX = min + (max - min) * r.nextInt();
    	int PositionCentreY = min + (max - min) * r.nextInt();
    	return new CentreV(PositionCentreX,PositionCentreY);
    }
    
    protected static List createRandomPoints(int min, int max, int number) {
    	List centres = new ArrayList(number);
    	for(int i = 0; i < number; i++) {
    		centres.add(createRandomPoint(min,max));
    	}
    	return centres;
    }
}
