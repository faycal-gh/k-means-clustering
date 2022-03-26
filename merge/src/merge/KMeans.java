package merge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KMeans {

    List<Client> data = new ArrayList<>();
    List<Cluster> clusters = new ArrayList<Cluster>();
    Map<Cluster, List<Client>> clusterRecords = new HashMap<Cluster, List<Client>>();

    public KMeans() {
        int clusterNumber = 2;      
        genereateRecord();
        initiateClusterAndCentroid(clusterNumber);
        printRecordInformation();       
    }

    public static void main(String[] args) {

    }

    private void genereateRecord() {
        Client client = new Client(1, 15, 19);
        data.add(client);
        client = new Client(2, 77, 81);
        data.add(client);
        client = new Client(3, 20, 16);
        data.add(client);
        client = new Client(4, 23, 16);
        data.add(client);
        client = new Client(5, 37, 40);
        data.add(client);
        client = new Client(7, 74, 86);
        data.add(client);
        client = new Client(8, 84, 96);
        data.add(client);
        client = new Client(9, 94, 106);
        data.add(client);
        client = new Client(10, 104, 116);
        data.add(client);

    }

    private void initiateClusterAndCentroid(int clusterNumber) {
        int counter = 1;
        Iterator<Client> iterator = data.iterator();
        Client client = null;

        while (iterator.hasNext()) {
            client = iterator.next();
            if (counter <= clusterNumber) {
                client.setClusterNumber(counter);
                initializeCluster(counter, client);
                counter++;
            } else {
                System.out.println(client);
                System.out.println("** Cluster Information **");
                for (Cluster cluster : clusters) {
                    System.out.println(cluster);
                }
                System.out.println("*********************");
                double minDistance = Integer.MAX_VALUE;
                Cluster whichCluster = null;

                for (Cluster cluster : clusters) {
                    double distance = cluster.calculateDistance(client);
                    System.out.println(distance);
                    if (minDistance > distance) {
                        minDistance = distance;
                        whichCluster = cluster;
                    }
                }

                client.setClusterNumber(whichCluster.getClusterNumber());
                whichCluster.updateCentroid(client);
                clusterRecords.get(whichCluster).add(client);

            }

            System.out.println("** Cluster Information **");
            for (Cluster cluster : clusters) {
                System.out.println(cluster);
            }
            System.out.println("*********************");

        }

    }

    private void initializeCluster(int clusterNumber, Client client) {

        Cluster cluster = new Cluster(clusterNumber, client.getPositionX(), client.getPositionY());
        clusters.add(cluster);
        List<Client> clusterRecord = new ArrayList<Client>();
        clusterRecord.add(client);
        clusterRecords.put(cluster, clusterRecord);

    }

    private void printRecordInformation() {
        System.out.println("****** Each Record INFORMATION *********");
        for (Client record : data) {
            System.out.println(record);
        }
    }

    public Map<Cluster, List<Client>> printClusterInformation() {
        System.out.println("****** FINAL CLUSTER INFORMATION *********");
        String allData = "";
        for (Map.Entry<Cluster, List<Client>> entry : clusterRecords.entrySet()) {
            allData = (entry.getKey().toString().split(",")[2].replace("}", "")
                    + ", liste clients = " + entry.getValue()).split(",", 2)[1].replace("{", "(").replace("}", ")");
        }
        /*
        Map<String, String> data = new HashMap<>();
        Matcher m = Pattern.compile("\\((.*?)\\)").matcher(allData);
        String keyID = "";
        String text[] = {};
        while (m.find()) {
            text = m.group(1).split(",", 2);
            keyID = text[0].replace("id=", "");
            data.put(keyID, text[1]);
        }
        */
        /*        
        print data Map information
        for (Map.Entry<String, String> entry : data.entrySet()) {
            System.out.println(entry.getKey() + " :" + entry.getValue());
        }
         */
        return clusterRecords;
    }
}
