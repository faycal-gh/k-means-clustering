package merge;
public class Cluster {

	private int PositionCentreX;
	private int PositionCentreY;
	private int clusterNumber;

    public Cluster(int clusterNumber,int PositionCentreX, int PositionCentreY) {
        this.PositionCentreX = PositionCentreX;
        this.PositionCentreY = PositionCentreY;
        this.clusterNumber = clusterNumber;
    }

    public int getPositionCentreX() {
        return PositionCentreX;
    }

    public int getPositionCentreY() {
        return PositionCentreY;
    }

    public int getClusterNumber() {
        return clusterNumber;
    }

    public void setPositionCentreX(int PositionCentreX) {
        this.PositionCentreX = PositionCentreX;
    }

    public void setPositionCentreY(int PositionCentreY) {
        this.PositionCentreY = PositionCentreY;
    }

    public void setClusterNumber(int clusterNumber) {
        this.clusterNumber = clusterNumber;
    }

    @Override
    public String toString() {
        return "Cluster{" + "PositionCentreX=" + PositionCentreX + ", PositionCentreY=" + PositionCentreY + ", clusterNumber=" + clusterNumber + '}';
    }

	
	
	// Euclidean distance calculation
	public double calculateDistance(Client client) {
		return Math.sqrt(Math.pow((getPositionCentreX() - client.getPositionX()), 2) + Math.pow((getPositionCentreX() - client.getPositionY()),2) );
    }

	// Binod Suman Academy YouTube Video on K-Mean Algorithm
	public void updateCentroid(Client client) {
		setPositionCentreX((getPositionCentreX()+client.getPositionX())/2);
		setPositionCentreY((getPositionCentreX()+client.getPositionY())/2);
	}

}

