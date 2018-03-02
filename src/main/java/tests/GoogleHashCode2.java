package tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.Resources;

public class GoogleHashCode2 {


    public static void main(String[] args) throws IOException {


        List<Ride> allRides = new ArrayList<>();
        
        BufferedReader targetBF = new BufferedReader(new FileReader(Resources.getResource("a_example.in").getFile()));
        String line = targetBF.readLine();
        String grid[] = line.split(" ");
        int R = Integer.valueOf(grid[0]);
        int C = Integer.valueOf(grid[1]);
        int F = Integer.valueOf(grid[2]);
        int N = Integer.valueOf(grid[3]);
        int B = Integer.valueOf(grid[4]);
        int T = Integer.valueOf(grid[5]);
        int rideIds = 0;
        while ((line = targetBF.readLine()) != null)
        {
            String rides[] = line.split(" ");
            
            int a = Integer.valueOf(rides[0]);
            int b = Integer.valueOf(rides[1]);
            int x = Integer.valueOf(rides[2]);
            int y = Integer.valueOf(rides[3]);
            int s = Integer.valueOf(rides[4]);
            int t = Integer.valueOf(rides[5]);
            Ride ride = new Ride(a, b, x, y, s, t, rideIds);
            rideIds++;
            allRides.add(ride);
            
            
        }
        targetBF.close();
//        System.out.println(allRides.size());
        
        

        List<MyVehicle> allVehicles = new ArrayList<>();
        
        for (int i = 0 ; i < F ; i++) {
        	MyVehicle vehicle = new MyVehicle(0,0,i);
        	allVehicles.add(vehicle);
        }

        
        while (!allRides.isEmpty()) {
        	Ride firstRide = null;
        	int firstRideIndex = -1;
        	for (int i=0; i < allRides.size(); i++){
        		if (firstRide == null){
        			firstRide = allRides.get(i);
        			firstRideIndex = i;
        		}
        		if (allRides.get(i).getS() < firstRide.getS()) {
        			firstRide = allRides.get(i);
        			firstRideIndex = i;
        		}
        	}
        	allRides.remove(firstRideIndex);
        	
//        	System.out.println("Checking for ride: " + firstRide.getId());
        	MyVehicle bestVehicle = null;
        	int bestCost = -1;
        	for (MyVehicle vehicle : allVehicles) {
        		int cost = vehicle.getAssignCost(firstRide);
//        		System.out.println("Cost for " + vehicle.getId() + " " + cost);
        		if (cost > -1 && (bestVehicle == null || cost < bestCost)) {
        			bestVehicle = vehicle;
        			bestCost = cost;
        		}
        	}
        	if (bestVehicle != null) {
        		bestVehicle.assignRide(firstRide);
//        		System.out.println("Assigning " + firstRide.getId() + " to " + bestVehicle.getId());
        	}
        	
        }
        
        try (PrintWriter out = new PrintWriter("a_result.txt")) {
	        for (MyVehicle vehicle : allVehicles){
	    		List<Integer> rides = vehicle.assignedRides;
	    		StringBuilder sb = new StringBuilder();
	    		sb.append(rides.size());
	    		for (Integer ride : rides) {
	    			sb.append(" " + ride);
	    		}
	    		out.println(sb.toString());
	    	}
        }
        
        
    }

}