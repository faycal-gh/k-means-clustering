package merge;
public class Client {
    
		private int id;
	private int PositionX;
	private int PositionY;
	
	private int clusterNumber;

    public Client(int id, int PositionX, int PositionY) {
        this.id = id;
        this.PositionX = PositionX;
        this.PositionY = PositionY;
    }

    public int getId() {
        return id;
    }

    public int getPositionX() {
        return PositionX;
    }

    public int getPositionY() {
        return PositionY;
    }

    public int getClusterNumber() {
        return clusterNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPositionX(int PositionX) {
        this.PositionX = PositionX;
    }

    public void setPositionY(int PositionY) {
        this.PositionY = PositionY;
    }

    public void setClusterNumber(int clusterNumber) {
        this.clusterNumber = clusterNumber;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", PositionX=" + PositionX + ", PositionY=" + PositionY + ", clusterNumber=" + clusterNumber + '}';
    }

    
   
	
	

}