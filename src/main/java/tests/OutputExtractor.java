package tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.io.Resources;
import com.graphhopper.jsprit.core.algorithm.state.UpdateVehicleDependentPracticalTimeWindows.VehiclesToUpdate;

public class OutputExtractor {

	public static void main(String[] args) throws IOException {

		Map<String, List<String>> vehicles = new LinkedHashMap<>();
		
		try (PrintWriter out = new PrintWriter("result2_clean.txt")) {

			BufferedReader targetBF = new BufferedReader(new FileReader(
					Resources.getResource("result2.txt").getFile()));
			String line = targetBF.readLine();
			while ((line = targetBF.readLine()) != null) {
				if (line.contains("+") || line.contains("undef") || line.isEmpty())
					continue;
				String[] splits = line.split("\\|");
				String vehicle = splits[1].trim();
				String routes = splits[4].trim();
				if (vehicles.containsKey(vehicle)) {
					List<String> list = vehicles.get(vehicle);
					if (list.contains(routes)) {
						continue;
					}
					vehicles.get(vehicle).add(routes);
				} else {
					ArrayList<String> list = new ArrayList<>();
					list.add(routes);
					vehicles.put(vehicle, list);
				}
			}
			targetBF.close();
			for (String vehicle :vehicles.keySet()) {
				StringBuilder sb = new StringBuilder();
				sb.append(vehicles.get(vehicle).size());
				for (String route : vehicles.get(vehicle)) {
					sb.append(" " + route);
				}
				out.println(sb.toString());
			}
			
			
		}

	}

}
