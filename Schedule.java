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
		for(int i = 0; i < numNodes+1; i++){
			jobTimeAry[i] = 0;
			for(int j = 0; j < numNodes+1; j++){
				adjMatrix[i][j] = 0;
			}
		}

		procUsed = 0;
		currentTime = 0;
		OPEN = new Node();
	}

	public void loadMatrix(FileInputStream inFile){

	}

	public int loadJobTimeAry(FileInputStream inFile){
	
		return 0;
	}

	public void setMatrix(){}

	public void printMatrix(FileOutputStream outFile){

	}

	public int findOrphan(){return 0;}
	public void openInsert(){}
	public void printOPEN(){}
	public int getNextProc(){return 0;}
	public void putJobOnTable(){}
	public void printTable(){}
	public boolean checkCycle(){return false;}
	public boolean isGraphEmpty(){return false;}
	public void deleteJob(){}
}
