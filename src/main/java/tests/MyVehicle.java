package tests;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.MutablePair;

public class MyVehicle {

	MutablePair<Integer, Integer> location;
	Integer freeAt;
	int id;
	
	List<Integer> assignedRides;
	
	public MyVehicle(Integer x, Integer y, int id) {
		location = MutablePair.of(x, y);
		freeAt = 0;
		assignedRides = new ArrayList<>();
		this.id = id;
	}
	
	public int getAssignCost(Ride ride) {
		int lostTime = Math.abs(ride.getA()-location.getLeft()) + Math.abs(ride.getB()-location.getRight());
		int earliest = lostTime + freeAt;
		if (earliest + ride.getManhattanDistance() <= ride.getF()) {
			if (ride.getS() > earliest)
				return lostTime + (ride.getS() - earliest);
			else
				return lostTime;
		}
		return -1;
	}
	
	public void assignRide(Ride ride) {
		assignedRides.add(ride.getId());
		freeAt += Math.abs(ride.getA()-location.getLeft()) + Math.abs(ride.getB()-location.getRight());
		if (ride.getS() > freeAt)
			freeAt = ride.getS();
		freeAt += ride.getManhattanDistance() + 1;
		location.setLeft(ride.getX());
		location.setRight(ride.getY());
	}
	
	public int getId() {
		return id;
	}
	
}
