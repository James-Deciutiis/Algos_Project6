import java.io.*;
import java.util.Scanner;

public class Node{
	public int jobID;
	public int jobTitle;
	public Node next;

	public Node(int _jobID, int _jobTitle, Node _next){
		jobID = _jobID;
		jobTitle = _jobTitle;
		next = _next;
	}
}
