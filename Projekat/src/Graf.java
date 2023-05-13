import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Graf {
	private static int subject_num;
	private static int weights;
	private static int row_num = 1;
	private static int[] new_val = new int[2]; // pomocna promjenljiva
	private static ArrayList<String> main_nodes = new ArrayList();
	private static ArrayList<String> nodes = new ArrayList();
	private static ArrayList<String> curr_list = new ArrayList();
	private static ArrayList<String> connected = new ArrayList();
	private static ArrayList<String> con = new ArrayList();

	// koristi se za cuvanje key - ime cvora values - int[] weights value
	private static Map<String, int[]> nodes_inf = new HashMap<>();

	private static void readTextFile(String filename) {
		try {
			File myObj = new File(filename);
			Scanner scan = new Scanner(myObj);
			String firstLine = scan.nextLine();
			String dimension[] = firstLine.split(" ");
			subject_num = Integer.parseInt(dimension[0]);
			weights = Integer.parseInt(dimension[1]);

			while (scan.hasNextLine()) {
				String data = scan.nextLine();
				data += " " + row_num;
				String[] inf = data.split(" ");
				int[] wv = { Integer.parseInt(inf[0]), Integer.parseInt(inf[1]) };
				nodes_inf.put(inf[2], wv);
				main_nodes.add(row_num + "");
				nodes.add(row_num + "");
				connected.add("uzima_" + row_num + "-" + wv[0] + "-" + wv[1] + "-InitialState" + "-" + row_num);
				con.add("InitialNode" + "-" + row_num);
				row_num++;
			}
			for (int i = 0; i < main_nodes.size(); i++) {
				String node = main_nodes.get(i);
				int j = i + 1;
				while (j < main_nodes.size()) {
					int[] val = nodes_inf.get(node);
					new_val[0] = val[0] + nodes_inf.get(main_nodes.get(j))[0];
					new_val[1] = val[1] + nodes_inf.get(main_nodes.get(j))[1];
					if (nodes_inf.get(node + " " + main_nodes.get(j)) == null) {
						nodes_inf.put(node + " " + main_nodes.get(j), new_val.clone());
					}
					connected.add("uzima_" + main_nodes.get(j) + "-" + new_val[0] + "-" + new_val[1] + "-" + node + "-"
							+ node + " " + main_nodes.get(j));
					con.add(node + "-" + node + " " + main_nodes.get(j));

					connected.add("uzima_" + node + "-" + new_val[0] + "-" + new_val[1] + "-" + main_nodes.get(j) + "-"
							+ node + " " + main_nodes.get(j));
					con.add(main_nodes.get(j) + "-" + node + " " + main_nodes.get(j));

					nodes.add(node + " " + main_nodes.get(j));
					curr_list.add(node + " " + main_nodes.get(j));
					j++;
				}
			}
			recursion(curr_list);

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
	}

	private static void recursion(ArrayList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			String curr_node = list.get(i);
			int[] val = nodes_inf.get(curr_node);
			String arr[] = curr_node.split(" ");
			int last_num = Integer.parseInt(arr[arr.length - 1]);
			for (int j = last_num; j < main_nodes.size(); j++) {
				if (last_num == main_nodes.size())
					continue;
				String new_node = curr_node + " " + main_nodes.get(j);
				new_val[0] = val[0] + nodes_inf.get(main_nodes.get(j))[0];
				new_val[1] = val[1] + nodes_inf.get(main_nodes.get(j))[1];
				if (nodes_inf.get(curr_node + " " + main_nodes.get(j)) == null)
					nodes_inf.put(curr_node + " " + main_nodes.get(j), new_val.clone());
				nodes.add(new_node);
				connected.add("uzima_" + main_nodes.get(j) + "-" + new_val[0] + "-" + new_val[1] + "-" + curr_node + "-"
						+ new_node);
				curr_list.add(new_node);

				String ver[] = curr_node.split(" ");
				for (int k = 0; k < ver.length; k++) {
					String fromN = "";
					for (int h = 0; h < ver.length; h++) {
						if (h != k)
							fromN += ver[h] + " ";
					}
					fromN += main_nodes.get(j);
					if (!con.contains(fromN + "-" + new_node)) {connected.add("uzima_" + ver[k] + "-" + new_val[0] + "-" + new_val[1] + "-" + fromN + "-" + new_node);
					}
				}
			}
		}

	}

	// csv za cvorove
	private static void writeToCSVFile(String filename) {
		try (PrintWriter pw = new PrintWriter(filename)) {
			pw.write("State\n");

			for (String n : nodes) {
				pw.write(n);
				pw.write('\n');
			}

			System.out.println("Finished writing to nodes.csv file");
		} catch (FileNotFoundException e) {
			System.out.println("Error creating/writing to file!");
			e.printStackTrace();
		}
	}

	// csv za veze
	private static void writeToRelCSVFile(String filename) {
		try (PrintWriter pw = new PrintWriter(filename)) {
			pw.write("Label,");
			pw.write("Weight,");
			pw.write("Value,");
			pw.write("FromNode,");
			pw.write("ToNode\n");

			for (String c : connected) {
				String[] values = c.split("-");
				pw.write(values[0] + ",");
				pw.write(values[1] + ",");
				pw.write(values[2] + ",");
				pw.write(values[3] + ",");
				pw.write(values[4] + "\n");
			}

			System.out.println("Finished writing to rel.csv file");
		} catch (FileNotFoundException e) {
			System.out.println("Error creating/writing to file!");
			e.printStackTrace();
		}
	}

	// ispis liste za node.csv
	private static void printNodes() {
		for (String n : nodes)
			System.out.println(n);
	}

	private static void printHash() {
		for (String i : nodes_inf.keySet()) {
			System.out.println("key: " + i + " value: " + nodes_inf.get(i)[0] + " " + nodes_inf.get(i)[1]);
		}
	}

	public static void main(String[] args) {
		readTextFile("velike2.txt");
	    writeToCSVFile("nodes2.csv");
		//printHash();
		writeToRelCSVFile("rel2.csv");
		System.out.println(nodes.size());
		System.out.println(connected.size());
	}

}
