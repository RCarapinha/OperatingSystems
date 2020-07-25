import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.text.ParseException; 
  
class T2 {	
	
	//SSTF
	public static void SSTF(int NrBlocks, int[] Queue, int HeadStart, int NrQueue) {
		int Head[] = new int[NrQueue+1];
		int Shortest[] = new int[NrQueue];
		int Min = Integer.MAX_VALUE;
		int Completed[] = new int[NrQueue];
		int NrMoves = 0;
		int MinIndex = 0;
		
		for(int j = 0; j < NrQueue; j++) Completed[j] = 0;
				
		Head[0] = HeadStart;
		
		for(int j = 0; j < NrQueue; j++) {
			Min = Integer.MAX_VALUE;
			
			//Calculate all the paths to the queues
			for(int k = 0; k < NrQueue; k++) {
				Shortest[k] = Math.abs(Head[j] - Queue[k]);
			}
			
			//Find the absolute minimum
			for(int w = 0; w < NrQueue; w++) {
				if(Shortest[w] < Min && Completed[w] == 0){
					Min = Shortest[w];
					MinIndex = w;
				}
			}
			
			//Absolute minimum found
			Head[j+1] = Queue[MinIndex];
			Completed[MinIndex] = 1;
		}
		
		//Now I have the correct order, just calculate the moves
		for(int j = 0; j < NrQueue; j++) {
			NrMoves = NrMoves + Math.abs(Head[j]-Head[j+1]);
		}
		
		System.out.println();
		System.out.println("SSTF");
		System.out.println("Number of Moves: " + NrMoves);
	}
	
	//SCAN
	public static void SCAN(int NrBlocks, int[] Queue, int HeadStart, int NrQueue) {
		int NrMoves = 0;
			
		//----------------------------------------------------
		//Let's go to the left
		//Check how many jobs we have on the left of HeadStart
		int JobsOnTheLeft = 0;
		int k = 0;
		for(int j = 0; j < NrQueue; j++){
			if(Queue[j] <= HeadStart) {
				JobsOnTheLeft++;
			}
		}
			
		//Now we have the number new queue of all the jobs on the left, lets fill it with the correct jobs
		int NewQueueLeft[] = new int[JobsOnTheLeft];
		int HeadLeft[] = new int[JobsOnTheLeft+1];
		HeadLeft[0] = HeadStart;
		
		for(int j = 0; j < NrQueue; j++){
			if(Queue[j] <= HeadStart) {
				NewQueueLeft[k] = Queue[j];
				k++;
			}
		}
		
		//Let's sort the jobs in the correct order
		int start = 0, end = NewQueueLeft.length-1, temp;
		Arrays.sort(NewQueueLeft);
		while (start < end) { 
			temp = NewQueueLeft[start];  
			NewQueueLeft[start] = NewQueueLeft[end]; 
	        NewQueueLeft[end] = temp; 
	        start++; 
	        end--; 
	    }  
			
		//Now we have all the jobs on the left, let's execute them
		for(int j = 1; j < JobsOnTheLeft+1; j++) HeadLeft[j] = NewQueueLeft[j-1];
		
		for(int j = 0; j < JobsOnTheLeft; j++) {
			NrMoves = NrMoves + Math.abs(HeadLeft[j]-HeadLeft[j+1]);
		}
		//Left is finished, now let's go to the right
		//----------------------------------------------------
		//Right Starts
		int JobsOnTheRight = 0;
		
		for(int j = 0; j < NrQueue; j++){
			if(Queue[j] > HeadStart) {
				JobsOnTheRight++;
			}
		}
		
		//Now we have the number new queue of all the jobs on the right, lets fill it with the correct jobs
		int w = 0;
		int NewQueueRight[] = new int[JobsOnTheRight];
		int HeadRight[] = new int[JobsOnTheRight+1];
		HeadRight[0] = HeadLeft[JobsOnTheLeft];
				
		for(int j = 0; j < NrQueue; j++){
			if(Queue[j] > HeadStart) {
				NewQueueRight[w] = Queue[j];
				w++;
			}
		}
		
		//Let's sort the jobs in the correct order
		Arrays.sort(NewQueueRight);
		
		//Now we have all the jobs on the left, let's execute them
		for(int j = 1; j < JobsOnTheRight+1; j++) HeadRight[j] = NewQueueRight[j-1];
				
		for(int j = 0; j < JobsOnTheRight; j++) {
			NrMoves = NrMoves + Math.abs(HeadRight[j]-HeadRight[j+1]);
		}
		
		System.out.println();
		System.out.println("SCAN");
		System.out.println("Number of Moves: " + NrMoves);
	}
	
	//C-SCAN
	public static void CSCAN(int NrBlocks, int[] Queue, int HeadStart, int NrQueue) {
		int NrMoves = 0;
		
		//----------------------------------------------------
		//Let's go to the left
		//Check how many jobs we have on the left of HeadStart
		int JobsOnTheLeft = 0;
		int k = 0;
		for(int j = 0; j < NrQueue; j++){
			if(Queue[j] <= HeadStart) {
				JobsOnTheLeft++;
			}
		}
		
		//Now we have the number new queue of all the jobs on the left, lets fill it with the correct jobs
		int NewQueueLeft[] = new int[JobsOnTheLeft];
		int HeadLeft[] = new int[JobsOnTheLeft+1];
		HeadLeft[0] = HeadStart;
		
		for(int j = 0; j < NrQueue; j++){
			if(Queue[j] <= HeadStart) {
				NewQueueLeft[k] = Queue[j];
				k++;
			}
		}
		
		//Let's sort the jobs in the correct order
		int start = 0, end = NewQueueLeft.length-1, temp;
		Arrays.sort(NewQueueLeft);
		while (start < end) { 
            temp = NewQueueLeft[start];  
            NewQueueLeft[start] = NewQueueLeft[end]; 
            NewQueueLeft[end] = temp; 
            start++; 
            end--; 
        }  
		
		//Now we have all the jobs on the left, let's execute them
		for(int j = 1; j < JobsOnTheLeft+1; j++) HeadLeft[j] = NewQueueLeft[j-1];
		
		for(int j = 0; j < JobsOnTheLeft; j++) {
			NrMoves = NrMoves + Math.abs(HeadLeft[j]-HeadLeft[j+1]);
		}
		
		//Left is finished, now let's go to the end of the disk
		//----------------------------------------------------
		//Start left Again
		int JobsOnTheLeft2 = 0;
		
		for(int j = 0; j < NrQueue; j++){
			if(Queue[j] > HeadStart) {
				JobsOnTheLeft2++;
			}
		}
		
		//Now we have the number new queue of all the jobs on the left again, lets fill it with the correct jobs
		int w = 0;
		int NewQueueLeft2[] = new int[JobsOnTheLeft2];
		int HeadLeft2[] = new int[JobsOnTheLeft2+1];
		HeadLeft2[0] = NrBlocks; //Point to the previous position
				
		for(int j = 0; j < NrQueue; j++){
			if(Queue[j] > HeadStart) {
				NewQueueLeft2[w] = Queue[j];
				w++;
			}
		}
		
		//We have the correct jobs, let's sort them from the HIGHEST to the LOWEST
		int start2 = 0, end2 = NewQueueLeft2.length-1, temp2;
		Arrays.sort(NewQueueLeft2);
		while (start2 < end2) { 
			temp2 = NewQueueLeft2[start2];  
			NewQueueLeft2[start2] = NewQueueLeft2[end2]; 
			NewQueueLeft2[end2] = temp2; 
			start2++; 
			end2--; 
		}  
		
		//Now we have all the jobs on the left, let's execute them
		for(int j = 1; j < JobsOnTheLeft2+1; j++) HeadLeft2[j] = NewQueueLeft2[j-1];
				
		for(int j = 0; j < JobsOnTheLeft2; j++) {
			NrMoves = NrMoves + Math.abs(HeadLeft2[j]-HeadLeft2[j+1]);
		}
		
		int ChangeDir = Math.abs(HeadLeft[JobsOnTheLeft]-HeadLeft2[0]);
		
		System.out.println();
		System.out.println("C-SCAN");
		System.out.println("Number of Moves: " + (NrMoves+ChangeDir));
	}
	
	//FCFS
	public static void FCFS(int NrBlocks, int[] Queue, int HeadStart, int NrQueue) {
		int Head[] = new int[NrQueue+1];
		int NrMoves = 0;
		Head[0] = HeadStart;
		
		for(int j = 1; j < NrQueue+1; j++) Head[j] = Queue[j-1];
		
		for(int i = 0; i < NrQueue; i++) NrMoves = NrMoves + Math.abs(Head[i]-Head[i+1]);
		
		System.out.println();
		System.out.println("FCFS");
		System.out.println("Number of Moves: " + NrMoves);
	}
	
    //Main
    public static void main(String[] args) throws ParseException { 
    	int NrBlocks = 0, HeadStart = 0, NrQueue = 0, op;
    	int[] Queue = new int[NrQueue];
    	int[] ArrivalTime = new int[NrQueue];
    	
    	Scanner scan = new Scanner(System.in);
    	
    	while(true) {
    		System.out.println("1 -> Create Tasks");
    		System.out.println("2 -> Generate Tasks");
    		System.out.print("Select : ");
    		op = scan.nextInt();
    		if(op == 1 || op == 2) {
    			break;
    		}
    	}
    	
    	if(op == 1) {    	
    		System.out.print("Number of Blocks: ");  	
    		NrBlocks = scan.nextInt();
    	
    		System.out.print("Number of Tasks: ");  	
    		NrQueue = scan.nextInt();
    	
    		System.out.print("Head Start (0-Number of Blocks): ");  	
    		HeadStart = scan.nextInt();
    	
    		Queue = new int[NrQueue];
    		//ArrivalTime = new int[NrQueue];
    	
    		for(int i = 0; i < NrQueue; i++) {
    			System.out.print("Position of Disk Queue " + (i+1) + ": ");  
    			Queue[i] = scan.nextInt();
    			//System.out.print("Arrival Time of Queue Nr." + (i+1) + ": ");  
    			//ArrivalTime[i] = scan.nextInt();
    		}
    	}
    	else if(op == 2) {	  
    		System.out.print("Number of Blocks: ");  	
    		NrBlocks = scan.nextInt();
    	
    		System.out.print("Number of Tasks: ");  	
    		NrQueue = scan.nextInt();
    	
    		System.out.print("Head Start (0-Number of Blocks): ");  	
    		HeadStart = scan.nextInt();
    	
    		Queue = new int[NrQueue];
    		//ArrivalTime = new int[NrQueue];
    	
    		for(int i = 0; i < NrQueue; i++) {
    			Queue[i] = ThreadLocalRandom.current().nextInt(0, (NrBlocks+1));  //Generate values between 0-NrBlocks
    			//System.out.print("Arrival Time of Queue Nr." + (i+1) + ": ");  
    			//ArrivalTime[i] = scan.nextInt();
    		}
    	}
     
    	scan.close();
    	
    	System.out.print("\n");
    	System.out.println("Task Nr. | Disk Position");
    	
    	for(int i = 0; i < NrQueue; i++) {
    		System.out.format("%5d%5s%9d", (i+1), "|", Queue[i]);
    		System.out.print("\n");
    	}
    	
    	System.out.print("\n");

    	FCFS(NrBlocks, Queue, HeadStart, NrQueue);
    	SCAN(NrBlocks, Queue, HeadStart, NrQueue);
    	CSCAN(NrBlocks, Queue, HeadStart, NrQueue);
    	SSTF(NrBlocks, Queue, HeadStart, NrQueue);
    }
}  