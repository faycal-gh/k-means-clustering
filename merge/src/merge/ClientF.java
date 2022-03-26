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
public class ClientF {

    public int idClient;
    public int PositionX;
    public int PositionY;
    private int qteClient;
    public int TempsDarrive;
    private int Penalite = 0;
    public int li;
    private int centre;
    public int demand;
    boolean isRouted;
    // private boolean IsDepot;

    public ClientF() {
    }

    public ClientF(int idClient, int PositionX, int PositionY, int Li, int demande) {
        this.idClient = idClient;
        this.PositionX = PositionX;
        this.PositionY = PositionY;
        this.qteClient = qteClient;
        this.TempsDarrive = TempsDarrive;
        this.li = li;
        this.isRouted = false;
    }

    public ClientF(int PositionX, int PositionY) {
        this.PositionX = PositionX;
        this.PositionY = PositionY;
    }

    public ClientF(int idClient, int PositionX, int PositionY) {
        this.idClient = idClient;
        this.PositionX = PositionX;
        this.PositionY = PositionY;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getPositionX() {
        return PositionX;
    }

    public int getPositionY() {
        return PositionY;
    }

    public int getQteClient() {
        return qteClient;
    }

    public int getTempsDarrive() {
        return TempsDarrive;
    }

    public int getPenalite() {
        return Penalite;
    }

    public int getLi() {
        return li;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setPositionX(int PositionX) {
        this.PositionX = PositionX;
    }

    public void setPositionY(int PositionY) {
        this.PositionY = PositionY;
    }

    public void setQteClient(int qteClient) {
        this.qteClient = qteClient;
    }

    public void setTempsDarrive(int TempsDarrive) {
        this.TempsDarrive = TempsDarrive;
    }

    public void setPenalite(int Penalite) {
        this.Penalite = Penalite;
    }

    public void setLi(int Li) {
        this.li = li;
    }

    public int getCentre() {
        return centre;
    }

    public void setCentre(int centre) {
        this.centre = centre;
    }

    protected static ClientF createRandomPoint(int min, int max) {
        Random r = new Random();
        int PositionX = min + (max - min) * r.nextInt();
        int PositionY = min + (max - min) * r.nextInt();
        return new ClientF(PositionX, PositionY);
    }

    protected static List createRandomPoints(int min, int max, int number) {
        List clients = new ArrayList(number);
        for (int i = 0; i < number; i++) {
            clients.add(createRandomPoint(min, max));
        }
        return clients;
    }
}
