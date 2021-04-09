import java.io.*;
import java.util.Scanner;

public class Schedule{
	public int numNodes;
	public int numProcs;
	public int procUsed;
	public int currentTime;
	public int totalJobTime;
	public int jobTimeAry [];
	public int adjMatrix [][];
	public int table[][];
	public Node OPEN;

	public Schedule(int _numNodes){
		numNodes = _numNodes;
		jobTimeAry = new int [numNodes+1];
		adjMatrix = new int[numNodes+1][numNodes+1];
		table = new int[numNodes+1][numNodes+1];

		for(int i = 0; i < numNodes+1; i++){
			jobTimeAry[i] = 0;
			for(int j = 0; j < numNodes+1; j++){
				adjMatrix[i][j] = 0;
				table[i][j] = 0;
			}
		}

		procUsed = 0;
		currentTime = 0;
		OPEN = new Node(-9999, -9999, null);
	}

	public void loadMatrix(FileInputStream inFile){
		Scanner scanner = new Scanner(inFile); 
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			String row = "";
			String col = "";
			int row_or_col = 0;
			for(int i = 0; i < line.length(); i++){
				if(line.charAt(i) != ' '){
					if(row_or_col % 2 == 0){
						row += line.charAt(i);
						row_or_col++;
					}
					else{
						col += line.charAt(i);
						row_or_col++;
					}
				}
			}
			
			this.adjMatrix[Integer.parseInt(row)][Integer.parseInt(col)] = 1;
			row = "";
			col = "";
		}
	}

	public int loadJobTimeAry(FileInputStream inFile){
		Scanner scanner = new Scanner(inFile); 
		scanner.nextLine();
		int retval = 0;
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			String row = "";
			String time = "";
			int row_or_col = 0;
			for(int i = 0; i < line.length(); i++){
				if(line.charAt(i) != ' '){
					if(row_or_col % 2 == 0){
						row += line.charAt(i);
						row_or_col++;
					}
					else{
						time += line.charAt(i);
						row_or_col++;
					}
				}
			}
			
			this.jobTimeAry[Integer.parseInt(row)] = Integer.parseInt(time);
			retval += Integer.parseInt(time);
			row = "";
			time = "";
		}
				
		return retval;
	}

	public void setMatrix(){
		for(int i = 0; i < this.numNodes+1; i++){
			this.adjMatrix[i][i] = 1;
		}

		this.adjMatrix[0][0] = this.numNodes;
	}

	public void printMatrix(FileOutputStream outFile){
		try{
			outFile.write(("PRINTING ADJMATRIX \n").getBytes());
			for(int i = 0; i < this.numNodes+1; i++){
				for(int j = 0; j < this.numNodes+1; j++){
					outFile.write((this.adjMatrix[i][j] + " ").getBytes());
				}
				
				outFile.write(("\n").getBytes());
			}
		}
		catch(IOException e){
		}
	}

	public int findOrphan(){
		for(int i = 0; i < this.numNodes+1; i++){
			if(this.adjMatrix[0][i] == 0 && this.adjMatrix[i][i] == 1){
				this.adjMatrix[i][i] = 2;
				return i;
			}
		}

		return -1;
	}

	public void openInsert(Node newNode){
		Node head = this.OPEN;
		while(head.next != null && head.jobID < newNode.jobID){
			head = head.next;
		}
	
		if(head.jobID == this.OPEN.jobID){
			newNode.next = null;
		}
		else{
			newNode.next = head.next;
		}

		head.next = newNode;
	}

	public void printOPEN(FileOutputStream outFile){
		try{
			outFile.write(("PRINTING OPEN \n").getBytes());
			Node scan = this.OPEN;
			while(scan.next != null){
				String s = "(JobID: " + scan.jobID + " JobTime: " + scan.jobTime + " Next JobID: " + scan.next.jobID + ")--->";
				outFile.write(s.getBytes());
				scan = scan.next;
			}
	
			String s = "(JobID: " + scan.jobID + " JobTime: " + scan.jobTime + " Next JobID: NULL) \n";
			outFile.write(s.getBytes());
		}
		catch(IOException e){
		}
	}

	public int getNextProc(){
		for(int i = 0; i < this.numNodes+1; i++){
			if(this.table[i][this.currentTime] == 0){
				return i;
			}
		}

		return -1;
	}
	
	public void putJobOnTable(int availableProc, int jobID, int jobTime){
		int time = this.currentTime;
		int endTime = time + jobTime;
		
		while(time < endTime){
			this.table[availableProc][time] = jobID;
		}
	}

	public void printTable(FileOutputStream outFile){
		try{
			outFile.write(("PRINTING TABLE \n").getBytes());
			String cols = "       ";
			String border = "\n";
			for(int i = 0; i < this.numNodes + 1; i++){
				cols += (i + "   ");
				border += "-----";
			}
			border+="\n";
	
			outFile.write((cols).getBytes());
			outFile.write((border).getBytes());

			String row = "";
			for(int i = 0; i < this.numNodes + 1; i++){
				outFile.write(("P(" + i + ") | ").getBytes());
				for(int j = 1; j < this.numNodes + 1; j++){
					row += table[i][j] + " | ";
				}

				outFile.write((row + border).getBytes());
				row = "";
			}
		}
		catch(IOException e){
		
		}
	}

	public boolean checkCycle(){
		boolean open_empty = this.OPEN.next == null;
		boolean graph_empty = this.isGraphEmpty();
		boolean all_proc_avail = this.procUsed == 0;
		System.out.println(open_empty + " : " + graph_empty + " : " + all_proc_avail);
		return open_empty && !graph_empty && all_proc_avail;
	}

	public boolean isGraphEmpty(){
		return this.adjMatrix[0][0] == 0;
	}
	
	public void deleteJob(int jobID){
		this.adjMatrix[jobID][jobID] = 0;
		this.adjMatrix[0][0]--;
		int j = 1;
		
		while(j <= this.numNodes){
			if(this.adjMatrix[jobID][j] > 0){
				this.adjMatrix[0][j]--;
				j++;
			}
		}
	}
}
