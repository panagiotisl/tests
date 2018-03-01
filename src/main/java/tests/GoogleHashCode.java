package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.io.Resources;

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
    }

}