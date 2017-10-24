import java.util.*;
import java.io.*;

/**
 * The main class that runs the A* algorithm, either by a straight line distance or by the minimum number of links
 * Austin Lackey
 * Gabriel Contreras
 */
public class Project {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String locationPath, connectionPath, startString, endString, inputRead, optimalPath;
        List<String> tempConnections1;
        List<String> tempConnections2;
        List<String> locationInfo;
        Map<String, Boolean> isVisited = new HashMap<>();
        PriorityQueue<City> cities;
        Queue<String> cityQueue;
//        This map is a bit hard to explain, but it is used in backtracking the path taken
        Map<String, String> origins = new HashMap<>();
        long setupTime, algorithmTime, startTime;

        int destx, desty;

        boolean isStraight, isStepByStep;

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
        System.out.print("Would you like to see a step by step breakdown of each move (y/n)?");
        isStepByStep = reader.next().equalsIgnoreCase("y");
        reader.close();

        Map<String, City> allCities = new HashMap<>(); // This will contain all the cities before we calculate the heuristics and put them in the priority queue

//        Do the file IO with the locations file to retrieve all the cities and put them in

        startTime = System.currentTimeMillis();
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
                tempConnections2 = new ArrayList<>(tempConnections1.subList(2, count));
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
        setupTime = System.currentTimeMillis() - startTime;


        allCities.get(startString);
        isVisited.put(startString, true);


//        We could call this the start of the algorithm

        if (isStraight) {
//            Initializes a priority queue with its own comparator
            cities = new PriorityQueue<>((o1, o2) -> (int) (o1.getStraightDistance() - o2.getStraightDistance()));
            cities.add(allCities.get(startString));
            while(!cities.peek().getName().equalsIgnoreCase(endString)) {
                City currentClosest = cities.poll();
                for (String connection : currentClosest.getConnections()) {
                    if (!isVisited.getOrDefault(connection, false)) {
                        cities.add(allCities.get(connection));
                        origins.put(connection, currentClosest.getName());
                    }
                }
                if (isStepByStep) {
                    System.out.println("Current Path: " + getPath(currentClosest.getName(), startString, origins));
                    System.out.println("Distance Traveled: " + getDistanceTraveled(currentClosest.getName(), startString, origins, allCities));
                    System.out.println("Best move is: " + cities.peek().getName());
                    try {
                        System.in.read();
                    } catch (Exception e) {

                    }
                }
                isVisited.put(currentClosest.getName(), true);
            }
//            Prints the final output of the program
            System.out.println("Optimal Path:");
            City backTracking = cities.peek();
            optimalPath = getPath(backTracking.getName(), startString, origins);
            System.out.println(optimalPath);
            System.out.println("Distance Traveled: " + getDistanceTraveled(backTracking.getName(), startString, origins, allCities));


        } else {
            cityQueue = new LinkedList<>();
            cityQueue.add(startString);
            while(!cityQueue.peek().equalsIgnoreCase(endString) && !cityQueue.isEmpty()) {
                String currentCity = cityQueue.peek();
                for (String connection : allCities.get(currentCity).getConnections()) {
                    if (!isVisited.getOrDefault(connection, false)) {
                        if (!cityQueue.contains(connection))
                            cityQueue.add(connection);
                        if (!origins.containsKey(connection))
                            origins.put(connection, currentCity);
                    }
                }
                cityQueue.remove();
                if (isStepByStep) {
                    System.out.println("Current Path: " + getPath(currentCity, startString, origins));
                    System.out.println("Number of connections: " + getConnectionSize(currentCity, startString, origins));
                    System.out.println("Best move is: " + cityQueue.peek());
                    try {
                        System.in.read();
                    } catch (Exception e) {

                    }
                }
                isVisited.put(currentCity, true);
            }
//            Prints the final output of the program
            System.out.println("Optimal Path:");
            String backtracking = cityQueue.peek();
            optimalPath = getPath(backtracking, startString, origins);
            System.out.println(optimalPath);
            System.out.println("Number of connections: " +getConnectionSize(cityQueue.peek(), startString, origins));
        }

//        The printing of the performance metrics
        algorithmTime = System.currentTimeMillis() - startTime - setupTime;
        System.out.println();
        System.out.println("The setup took: " + setupTime + " milliseconds");
        System.out.println("The algorithm took: " + algorithmTime + " milliseconds");
        System.out.println("Total time: " + (setupTime + algorithmTime) + " milliseconds");
    }

    /**
     *
     * @param currentString the name of the current node
     * @param startString the starting point
     * @param origins the map that contains the pairs
     * @return a string of the optimal path to arrive at a node
     */
    private static String getPath(String currentString, String startString, Map<String, String> origins) {
        String optimalPath = currentString;
        while (!currentString.equalsIgnoreCase(startString)) {
            currentString = origins.get(currentString);
            optimalPath = currentString + " -> " + optimalPath;
        }
        return optimalPath;
    }

    /**
     *
     * @param currentString he name of the current node
     * @param startString the starting point
     * @param origins the map that contains the pairs
     * @return The number of connections between the current city and the starting city
     */
    private static Integer getConnectionSize(String currentString, String startString, Map<String, String> origins) {
        int count = 0;
        while(!currentString.equalsIgnoreCase(startString)) {
            currentString = origins.get(currentString);
            count++;
        }
        return count;
    }

    /**
     *
     * @param currentString the name of the current node
     * @param startString the starting point
     * @param origins the map that contains the pairs
     * @param allCities The map with alle the cities
     * @return the distance traveled from the start to the current city
     */
    private static double getDistanceTraveled(String currentString, String startString, Map<String, String> origins, Map<String, City> allCities) {
        double distanceTraveled = 0;
        City currentCity, previousCity;
        while (!currentString.equalsIgnoreCase(startString)) {
            currentCity = allCities.get(currentString);
            previousCity = allCities.get(origins.get(currentString));

            distanceTraveled += Math.sqrt(Math.pow(currentCity.getX() - previousCity.getX(), 2) + Math.pow(currentCity.getY() - previousCity.getY(), 2));
            currentString = previousCity.getName();
        }
        currentCity = allCities.get(currentString);
        previousCity = allCities.get(startString);

        distanceTraveled += Math.sqrt(Math.pow(currentCity.getX() - previousCity.getX(), 2) + Math.pow(currentCity.getY() - previousCity.getY(), 2));
        return distanceTraveled;
    }

}
