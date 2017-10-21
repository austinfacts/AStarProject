import java.util.*;
import java.io.*;

/**
 * Created by lacke on 5/10/2017.
 */
public class Project {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String locationPath, connectionPath, startString, endString, inputRead;
        List<String> tempConnections1 = new ArrayList<String>();
        List<String> tempConnections2 = new ArrayList<String>();
        List<String> locationInfo = new ArrayList<String>();
        
        int destx, desty;

        boolean isStraight;

//        Paths hardcoded for development purposes, to accept input at later date
        System.out.println("Welcome to the A* city program.");
        System.out.print("Please enter the path of the locations file: ");
        locationPath = reader.next();
        System.out.print("Please enter the path of the connections file: ");
        connectionPath = reader.next();
        System.out.print("Please enter the starting city: ");
        startString = reader.next();
        System.out.print("Please enter the destination city: ");
        endString = reader.next();

        System.out.print("Would you like to use straight line distance or number of connections(s/c)? ");
        isStraight = reader.next().equalsIgnoreCase("s");
        reader.close();

        Map<String, City> allCities = new HashMap<String, City>(); // This will contain all the cities before we calculate the heuristics and put them in the priority queue


//        Do the file IO with the locations file to retrieve all the cities and put them in
        
        
        // Reads the locations file and populates allCities hashmap
        try {

            FileReader filereader = new FileReader(locationPath);
            BufferedReader bufferedReader = new BufferedReader(filereader);

            while ((inputRead = bufferedReader.readLine()) != null) {
                locationInfo = Arrays.asList(inputRead.split(" "));
                if(locationInfo.size() < 3)
                	break;
                allCities.put(locationInfo.get(0), new City(locationInfo.get(0), Integer.parseInt(locationInfo.get(1)), Integer.parseInt(locationInfo.get(2))));
            }
            
            bufferedReader.close();
            
        } catch (FileNotFoundException msg){
            System.out.println("Error opening file: " + locationPath);
        } catch (IOException msg){
            System.out.println("Error reading file: " + locationPath);
        } 


    	// Reads the connections file and adds the connections list to City objects
        try {

            FileReader filereader = new FileReader(connectionPath);
            BufferedReader bufferedReader = new BufferedReader(filereader);

            int count;

            while((inputRead = bufferedReader.readLine()) != null) {
                tempConnections1 = Arrays.asList(inputRead.split(" "));
                if(tempConnections1.size() < 2)
                	break;
                count = Integer.parseInt(tempConnections1.get(1)) + 2;
                tempConnections2 = new ArrayList<String>(tempConnections1.subList(2, count));
                allCities.get(tempConnections1.get(0)).setConnections(tempConnections2);
            }
            
            bufferedReader.close();
            
        } catch (FileNotFoundException msg) {
        	System.out.println("Error opening file: " + connectionPath);
        } catch (IOException msg) {
        	System.out.println("Error reading file: " + connectionPath);
        }
        
        // Retrieves the x and y coordinates for the destination city
        destx = allCities.get(endString).getX();
        desty = allCities.get(endString).getY();

        // Adds destination x and y to each City object
        for ( City value : allCities.values()) {
            value.setDestx(destx);
            value.setDesty(desty);
        }




        if (isStraight) {
//            Initializes a priority queue with its own comparator
            PriorityQueue<City> cities = new PriorityQueue<>(new Comparator<City>() {
                @Override
                public int compare(City o1, City o2) {

                    return (int) (o1.getStraightDistance() - o2.getStraightDistance());
                }
            });
            cities.add(allCities.get(startString));


        } else {

        }

    }

}
