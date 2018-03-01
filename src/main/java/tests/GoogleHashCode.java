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
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleType;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
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
            .addCapacityDimension(WEIGHT_INDEX, 10).setCostPerWaitingTime(1.);
        VehicleType vehicleType = vehicleTypeBuilder.build();

		/*
         * get a vehicle-builder and build a vehicle located at (10,10) with type "vehicleType"
		 */
        Builder vehicleBuilder = Builder.newInstance("vehicle");
        vehicleBuilder.setStartLocation(Location.newInstance(0, 0));
        vehicleBuilder.setType(vehicleType);
        VehicleImpl vehicle = vehicleBuilder.build();

		/*
         * build services at the required locations, each with a capacity-demand of 1.
		 */
        Service service1 = Service.Builder.newInstance("1")
            .addTimeWindow(50,100)
            .addTimeWindow(20,35)
            .addSizeDimension(WEIGHT_INDEX, 1).setLocation(Location.newInstance(10, 0)).build();

        Service service2 = Service.Builder.newInstance("2")
            .addSizeDimension(WEIGHT_INDEX, 1)
//            .setServiceTime(10)
            .setLocation(Location.newInstance(20, 0)).setServiceTime(10).build();

        Service service3 = Service.Builder.newInstance("3")
            .addTimeWindow(5, 10)
            .addTimeWindow(35, 50)
            .addSizeDimension(WEIGHT_INDEX, 1).setLocation(Location.newInstance(30, 0)).build();

        Service service4 = Service.Builder.newInstance("4")
//            .addTimeWindow(5,10)
            .addTimeWindow(20, 40)
            .addTimeWindow(45, 80)
            .addSizeDimension(WEIGHT_INDEX, 1).setLocation(Location.newInstance(40, 0)).build();

        Service service5 = Service.Builder.newInstance("5")
            .addTimeWindow(5,10)
            .addTimeWindow(20, 40)
            .addTimeWindow(60,100)
            .addSizeDimension(WEIGHT_INDEX, 1).setLocation(Location.newInstance(20, 0)).build();


        VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
        vrpBuilder.addVehicle(vehicle);
        vrpBuilder.addJob(service1).addJob(service2)
            .addJob(service3)
            .addJob(service4)
            .addJob(service5)
        ;
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