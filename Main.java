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
			schedule.printMatrix(outFileTwo);

			while(!schedule.isGraphEmpty()){
				int jobID = schedule.findOrphan();
				while(jobID == -9999){
					if(schedule.OPEN.jobID > 0){
						Node newNode = new Node(jobID, schedule.jobTimeAry[jobID], null);
						schedule.openInsert(newNode);
						schedule.printOPEN(outFileTwo);
					}
				
					jobID = schedule.findOrphan();
				}

			
				int availableProc = schedule.getNextProc();
				while(availableProc > 0 && schedule.OPEN.next != null && schedule.procUsed < schedule.numProcs){
					schedule.numProcs++;
					Node newJob = schedule.OPEN.next;
					schedule.OPEN.next = null;
					schedule.putJobOnTable(availableProc, schedule.currentTime, newJob.jobID, newJob.jobTitle);
				}

				schedule.printTable(outFileOne);
				boolean hasCycle = schedule.checkCycle();
				if(hasCycle){
					System.out.println("There is a cycle in the graph!!!");
					System.exit(0);
				}

				schedule.currentTime++;
				int proc = 0;
				while(proc <= schedule.procUsed){
					if(schedule.table[proc][schedule.currentTime] <= 0 && schedule.table[proc][schedule.currentTime - 1] > 0){
						jobID = schedule.table[proc][schedule.currentTime - 1];
						schedule.deleteJob(jobID);
					}
	
					schedule.printMatrix(outFileTwo);
					proc++;
				}
			}

			schedule.printTable(outFileOne);
			inFileOne.close();
			inFileTwo.close();
			outFileOne.close();
			outFileTwo.close();
		}

		catch(IOException e){
			System.out.println("ERROR");
		}
	}

}
