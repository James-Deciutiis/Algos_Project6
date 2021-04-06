import java.io.*;
import java.util.Scanner;

public class Main{
	public static void main(String args[]){
		try{

			if(args.length != 5){
				System.out.println("Invalid amount of inputs, check inputs and try again!");
				return;
			}

			FileInputStream inFileOne = new FileInputStream(args[0]);
			FileInputStream inFileTwo = new FileInputStream(args[1]);
			FileOutputStream outFileOne = new FileOutputStream(args[3]);
			FileOutputStream outFileTwo = new FileOutputStream(args[4]);
			
			Scanner inputOneScanner = new Scanner(inFileOne);
			int numNodes = Integer.parseInt(inputOneScanner.nextLine());
			Schedule schedule = new Schedule(numNodes);

			int numProcs = Integer.parseInt(args[2]);
			if(numProcs <= 0){
				System.out.println("Need one or more processors! Check inputs!");
				return;
			}
			else if(numProcs > numNodes){
				numProcs = numNodes;
			}
	
			schedule.loadMatrix(inFileOne);
			schedule.totalJobTime = schedule.loadJobTimeAry(inFileTwo);

			for(int i = 0; i < schedule.numProcs; i++){
				for(int j = 0; j < schedule.totalJobTime; j++){
					schedule.table[i][j] = 0;
				}
			}

			schedule.setMatrix();
			schedule.printMatrix(outFile2);

			schedule.jobID = schedule.findOrphan();
			if(schedule.jobID > 0){

				

		}
		catch(IOException e){
			System.out.println("ERROR");
		}
	}

}
