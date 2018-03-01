package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.io.Resources;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl.Builder;
import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Pickup;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.job.Shipment;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.solution.route.activity.TimeWindow;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleType;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Coordinate;
import com.graphhopper.jsprit.core.util.ManhattanCosts;
import com.graphhopper.jsprit.core.util.Solutions;

public class GoogleHashCode {


    public static void main(String[] args) throws IOException {


        Set<String> target = new HashSet<>();

        List<Ride> allRides = new ArrayList<>();
        
        BufferedReader targetBF = new BufferedReader(new FileReader(Resources.getResource("b_should_be_easy.in").getFile()));
        String line = targetBF.readLine();
        String grid[] = line.split(" ");
        int R = Integer.valueOf(grid[0]);
        int C = Integer.valueOf(grid[1]);
        int F = Integer.valueOf(grid[2]);
        int N = Integer.valueOf(grid[3]);
        int B = Integer.valueOf(grid[4]);
        int T = Integer.valueOf(grid[5]);
//        System.out.println(R + " " + C + " " + F + " " + N + " " + B + " " + T);
        while ((line = targetBF.readLine()) != null)
        {
            String rides[] = line.split(" ");
            
            int a = Integer.valueOf(rides[0]);
            int b = Integer.valueOf(rides[1]);
            int x = Integer.valueOf(rides[2]);
            int y = Integer.valueOf(rides[3]);
            int s = Integer.valueOf(rides[4]);
            int t = Integer.valueOf(rides[5]);
            Ride ride = new Ride(a, b, x, y, s, t);
            allRides.add(ride);
            
            
        }
        targetBF.close();
        System.out.println(allRides.size());
        
        

		/*
         * get a vehicle type-builder and build a type with the typeId "vehicleType" and one capacity dimension, i.e. weight, and capacity dimension value of 2
		 */
        final int WEIGHT_INDEX = 0;
        VehicleTypeImpl.Builder vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance("vehicleType")
            .addCapacityDimension(WEIGHT_INDEX, 1).setCostPerWaitingTime(1.);
        VehicleType vehicleType = vehicleTypeBuilder.build();

		/*
         * get a vehicle-builder and build a vehicle located at (10,10) with type "vehicleType"
		 */
        
        List<VehicleImpl> allVehicles = new ArrayList<>();
        
        for (int i = 0 ; i < F ; i++) {
        	Builder vehicleBuilder = Builder.newInstance(String.valueOf(i+1));
            vehicleBuilder.setStartLocation(Location.newInstance(0, 0));
            vehicleBuilder.setType(vehicleType);
        	VehicleImpl vehicle = vehicleBuilder.build();
        	allVehicles.add(vehicle);
        }
        
        
        List<Shipment> allShipments = new ArrayList<>();
        
        int count = 1;
        for (Ride ride : allRides) {

        	com.graphhopper.jsprit.core.problem.job.Shipment.Builder builder = Shipment.Builder.newInstance(String.valueOf(count))
        		.addSizeDimension(WEIGHT_INDEX, 1);
            Coordinate pickupCoord = Coordinate.newInstance(ride.getA(), ride.getB());
            Coordinate deliveryCoord = Coordinate.newInstance(ride.getX(), ride.getY());
            Location.Builder pickupLocationBuilder = Location.Builder.newInstance();
            Location.Builder deliveryLocationBuilder = Location.Builder.newInstance();
            builder.setPickupLocation(pickupLocationBuilder.setCoordinate(pickupCoord).build());
            builder.addPickupTimeWindow(TimeWindow.newInstance(ride.getS(), ride.getF()));
            builder.setDeliveryLocation(deliveryLocationBuilder.setCoordinate(deliveryCoord).build());
            builder.addDeliveryTimeWindow(TimeWindow.newInstance(ride.getS(), ride.getF()));
            
            allShipments.add(builder.build());
        	count++;
        }
        

        VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
        
        for (VehicleImpl vehicle : allVehicles) {
        	vrpBuilder.addVehicle(vehicle);
        }

        for (Shipment shipment : allShipments) {
        	vrpBuilder.addJob(shipment);
        }
        
        vrpBuilder.setFleetSize(VehicleRoutingProblem.FleetSize.FINITE);
        vrpBuilder.setRoutingCost(new ManhattanCosts());
        VehicleRoutingProblem problem = vrpBuilder.build();

		/*
         * get the algorithm out-of-the-box.
		 */
        VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(problem);

		/*
         * and search a solution
		 */
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();

		/*
         * get the best
		 */
        VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);

//        new VrpXMLWriter(problem, solutions).write("output/problem-with-solution.xml");

        SolutionPrinter.print(problem, bestSolution, SolutionPrinter.Print.VERBOSE);

    }

}