import java.io.*;
import java.util.Scanner;

public class Node{
	public int jobID;
	public int jobTime;
	public Node next;

	public Node(int _jobID, int _jobTime, Node _next){
		jobID = _jobID;
		jobTime = _jobTime;
		next = _next;
	}
}
