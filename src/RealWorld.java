import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;


public class RealWorld extends Graph{

	public RealWorld(ArrayList<Integer> nodes) {
		super(nodes);
	}
	
	

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		int sum_of_lengths = 0;
		String file_link = null;
		ArrayList<Integer> lengths = new ArrayList<Integer>();
		BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter the location of input file :");
        try {
			file_link = b.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//****** reading from file for input nodes*******
			FileReader fr=new FileReader(file_link);
	 	   BufferedReader br=new BufferedReader(fr);  
	 	   ArrayList<Integer> nodes = new ArrayList<>();
	 	   
	 	   String line;
	 	   while ((line=br.readLine())!= null){ 	
	 		   String words[] = line.split("	");
	 		  if(isNumeric(words[0])){
		 			if(!nodes.contains(Integer.parseInt(words[0])))   
		 			   nodes.add(Integer.parseInt(words[0]));	 
	 		  }
	 		  if(words.length>1){
	 			 String word = words[1];//.substring(0, words[1].length()-1);
	 			 if(isNumeric(word)){
			 			if(!nodes.contains(Integer.parseInt(word))) 
			 			   nodes.add(Integer.parseInt(word));	 
		 		  }
	 		  }
	 	   }
	 	 Graph g = new Graph(nodes);
	 	 
	 	 //****** Reading from file for making the edgelist of the graph*********
	 	 FileReader fr1=new FileReader(file_link);
	 	 BufferedReader br1=new BufferedReader(fr1); 
	 	  while ((line=br1.readLine())!= null){
			   String words[] = line.split("	");
			   if(words.length > 1){
				   String word = words[1];
				   if(isNumeric(words[0]) && isNumeric(word)){
					   int node1 = Integer.parseInt(words[0]);
 				   		int node2 = Integer.parseInt(word);
 				   		if(node1!=node2){
	 				   		if(!g.getNeighbors(node1).contains(node2)){
							   g.addNeighbor(node1, node2);
							   g.addNeighbor(node2, node1);
	 				   		}
 				   		}else{
 				   			g.addNeighbor(node1, node2);
 				   		}
				   }	
			   }
	 	  }
	 	  System.out.println("Edgelist real world :" + g.adj);
	 	  
	 	  
	 	  //**********Calculating the clustering coefficients***************
	 		ArrayList<Float> coefficients = new ArrayList<Float>();
	 		float d = 0,sum = 0;
	 		for(int i = 0 ; i< nodes.size() ; i++){
	 			int pairs = 0;
	 			LinkedList<Integer> neighbors = g.getNeighbors(nodes.get(i));
	 			for(Integer first : g.getNeighbors(nodes.get(i))){
	 				for(Integer second : neighbors){
	 					if(first != second){
	 						if(g.getNeighbors(second).contains(first)){
	 							pairs++;
	 						}
	 					}
	 				}
	 			}
	 			
	 			d=(float)neighbors.size()*(neighbors.size()-1)/2;
	 			if(d!=0.0)
	 				coefficients.add(pairs/(2*d));
	 			else
	 				coefficients.add((float) 0);
	 		}
	 		for(float each : coefficients){
	 			sum=sum+each;
	 		}
	 		
	 		System.out.println("clustering coeffiecient real world :" + sum/coefficients.size());
	 		
	 		//************ Calculating the average path length*******************
	 		int iterations = 0;
	 		while(iterations<1000){
				ArrayList<Integer> visited= new ArrayList<Integer>(Collections.nCopies(Collections.max(nodes)+1,0));
				ArrayList<Integer> local_lengths = new ArrayList<Integer>(Collections.nCopies(Collections.max(nodes)+1,0));
				for(int node: nodes){
					local_lengths.set(node, 0);
				}
				
				int from = 0; int to = 0;
				
				Queue<Integer> q = new Queue<>();
				while(from == to){
					Random var1 = new Random();
					int from_index = var1.nextInt(nodes.size()) ;
					from = nodes.get(from_index);
					Random var2 = new Random();
					int to_index = var2.nextInt(nodes.size());
					to = nodes.get(to_index);
					
				}	
				if(from != to){
					local_lengths.set(from, 0);
					visited.set(from,1);
					q.enqueue(from);
					while(!(q.size()==0)){
						if(local_lengths.get(to) == 0){
							int current = q.dequeue();
							LinkedList<Integer> edges_current = g.adj.get(current);
							for(int each :edges_current){
									if(local_lengths.get(each) == 0 && visited.get(each) == 0){
										q.enqueue(each);
										local_lengths.set(each, local_lengths.get(current)+1);
										visited.set(each, 0);
									}
								}
						}else{
							lengths.add(local_lengths.get(to));
							iterations++;
							break;
						}
					}
				}
	 		}
				
				for(int i = 0 ; i < lengths.size(); i++){
					sum_of_lengths = sum_of_lengths + lengths.get(i);
				}
				System.out.println("average path length :" + (float)sum_of_lengths/1000);
				/*
				//*********************degree calculation******************
				
				int degree = 0;
				try {
						File file = new File("/Users/Bharath/Desktop/result.txt");
						if (!file.exists()) {
							file.createNewFile();
						}
						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						
						for(int i = 0 ; i< nodes.size();i++){
							degree = g.adj.get(nodes.get(i)).size();
							bw.write(degree + "\n");
						}
						bw.close();
				}catch (IOException e) {
				 	e.printStackTrace();
				}
				////////***********Frequency calculation**********
				
				FileReader fr2=new FileReader("/Users/Bharath/Desktop/result.txt");
			 	BufferedReader br2=new BufferedReader(fr2);  
			 	ArrayList<Integer> frequency = new ArrayList<>(Collections.nCopies(nodes.size(),0));  
			 	String line1;
			 	while ((line1=br2.readLine())!= null){ 	
			 		   String words[] = line1.split("	");
			 		   String current = words[0];
			 		  if(isNumeric(current)){
			 			  frequency.set(Integer.parseInt(current),frequency.get(Integer.parseInt(current))+1);  
			 		  }
			 	}	  
				try {
					File file = new File("/Users/Bharath/Desktop/result_frequency.txt");
					if (!file.exists()) {
						file.createNewFile();
					}
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					for(int i = 0; i<frequency.size(); i++){
						bw.write(frequency.get(i) + "\n");
					}
					bw.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
				try {
					File file = new File("/Users/Bharath/Desktop/nodes.txt");
					if (!file.exists()) {
						file.createNewFile();
					}
					FileWriter fw1 = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw1 = new BufferedWriter(fw1);
					for(int i = 0; i<5242; i++){
						bw1.write(i + "\n");
					}
					bw1.close();
				}catch (IOException e) {
					e.printStackTrace();
				}*/
	}
}
	 		   
	 		   
	 		   
	 		   
	 		   
	 		   
	 		