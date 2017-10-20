import java.util.*;

/**
 * Created by lacke on 5/10/2017.
 */
public class Project {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String locationPath, connectionPath, startString, endString;
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
        startString = reader.next();

        System.out.print("Would you like to use straight line distance or number of connections(s/c)? ");
        isStraight = reader.next().equalsIgnoreCase("s");
        reader.close();

        Map<String, City> allCities = new HashMap(); // This will contain all the cities before we calculate the heuristics and put them in the priority queue


//        Do the file IO with the locations file to retrieve all the cities and put them int


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
