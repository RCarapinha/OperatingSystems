import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.text.ParseException; 

class T2_RT {	
	
	//SSTF
	public static void SSTF_RT(int NrBlocks, int[] Queue, int HeadStart, int NrQueue, int[] RT_Queue, int NrRTQueue, int[] Priority) {
		int Head[] = new int[NrQueue+1];
		int RTHead[] = new int[NrRTQueue+1];
		int Shortest[] = new int[NrQueue];
		int Min = Integer.MAX_VALUE;
		int Completed[] = new int[NrQueue];
		int NrMoves = 0;
		int MinIndex = 0;
		int T = 1;
		
		for(int j = 0; j < NrQueue; j++) Completed[j] = 0;
		
		RTHead[0] = HeadStart;
		for(int j = 1; j < NrRTQueue+1; j++) {
			if(Priority[j-1] == 3) {
				RTHead[T] = RT_Queue[j-1];
				T = T + 1;
			}
		}
		
		for(int j = 1; j < NrRTQueue+1; j++) {
			if(Priority[j-1] == 2) {
				RTHead[T] = RT_Queue[j-1];
				T = T + 1;
			}
		}
		for(int j = 1; j < NrRTQueue+1; j++) {
			if(Priority[j-1] == 1) {
				RTHead[T] = RT_Queue[j-1];
				T = T + 1;
			}
		}
		
		for(int i = 0; i < NrRTQueue; i++) NrMoves = NrMoves + Math.abs(RTHead[i]-RTHead[i+1]);
		
		Head[0] = RTHead[NrRTQueue];
		
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
		
		System.out.print("Sequence -> ");
		for(int i = 0; i < NrRTQueue; i++) System.out.print(RTHead[i] + " -> ");
		for(int i = 0; i < NrQueue; i++) System.out.print(Head[i] + " -> ");
		System.out.print(Head[Head.length-1]);
		
		System.out.println();
		System.out.println("Number of Moves: " + NrMoves);
	}
	
	//SCAN
	public static void SCAN_RT(int NrBlocks, int[] Queue, int HeadStart, int NrQueue, int[] RT_Queue, int NrRTQueue, int[] Priority) {
		int NrMoves = 0;
		int RTHead[] = new int[NrRTQueue+1];
		int T = 1;
		
		RTHead[0] = HeadStart;
		for(int j = 1; j < NrRTQueue+1; j++) {
			if(Priority[j-1] == 3) {
				RTHead[T] = RT_Queue[j-1];
				T = T + 1;
			}
		}
		
		for(int j = 1; j < NrRTQueue+1; j++) {
			if(Priority[j-1] == 2) {
				RTHead[T] = RT_Queue[j-1];
				T = T + 1;
			}
		}
		for(int j = 1; j < NrRTQueue+1; j++) {
			if(Priority[j-1] == 1) {
				RTHead[T] = RT_Queue[j-1];
				T = T + 1;
			}
		}
		
		for(int i = 0; i < NrRTQueue; i++) NrMoves = NrMoves + Math.abs(RTHead[i]-RTHead[i+1]);
					
		//----------------------------------------------------
		//Let's go to the left
		//Check how many jobs we have on the left of HeadStart
		int JobsOnTheLeft = 0;
		int k = 0;
		for(int j = 0; j < NrQueue; j++){
			if(Queue[j] <= RTHead[NrRTQueue]) {
				JobsOnTheLeft++;
			}
		}
			
		//Now we have the number new queue of all the jobs on the left, lets fill it with the correct jobs
		int NewQueueLeft[] = new int[JobsOnTheLeft];
		int HeadLeft[] = new int[JobsOnTheLeft+1];
		HeadLeft[0] = RTHead[NrRTQueue];
		
		for(int j = 0; j < NrQueue; j++){
			if(Queue[j] <= HeadLeft[0]) {
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
		
		for(int j = 0; j < JobsOnTheLeft; j++) NrMoves = NrMoves + Math.abs(HeadLeft[j]-HeadLeft[j+1]);
		
		//Left is finished, now let's go to the right
		//----------------------------------------------------
		//Right Starts
		int JobsOnTheRight = 0;
		
		for(int j = 0; j < NrQueue; j++){
			if(Queue[j] > HeadLeft[0]) {
				JobsOnTheRight++;
			}
		}
		
		//Now we have the number new queue of all the jobs on the right, lets fill it with the correct jobs
		int w = 0;
		int NewQueueRight[] = new int[JobsOnTheRight];
		int HeadRight[] = new int[JobsOnTheRight+1];
		HeadRight[0] = HeadLeft[JobsOnTheLeft];
				
		for(int j = 0; j < NrQueue; j++){
			if(Queue[j] > HeadLeft[0]) {
				NewQueueRight[w] = Queue[j];
				w++;
			}
		}
		
		//Let's sort the jobs in the correct order
		Arrays.sort(NewQueueRight);
		
		//Now we have all the jobs on the right, let's execute them
		for(int j = 1; j < JobsOnTheRight+1; j++) HeadRight[j] = NewQueueRight[j-1];
				
		for(int j = 0; j < JobsOnTheRight; j++) NrMoves = NrMoves + Math.abs(HeadRight[j]-HeadRight[j+1]);
		
		System.out.println();
		System.out.println("SCAN");
		System.out.print("Sequence -> ");
		for(int i = 0; i < NrRTQueue; i++) System.out.print(RTHead[i] + " -> ");
		for(int i = 0; i < JobsOnTheLeft; i++) System.out.print(HeadLeft[i] + " -> ");
		for(int i = 0; i < JobsOnTheRight; i++) System.out.print(HeadRight[i] + " -> ");
		System.out.print(HeadRight[HeadRight.length-1]);
		
		System.out.println();
		System.out.println("Number of Moves: " + NrMoves);
	}
	
	//C-SCAN
	public static void CSCAN_RT(int NrBlocks, int[] Queue, int HeadStart, int NrQueue, int[] RT_Queue, int NrRTQueue, int[] Priority) {
		int NrMoves = 0;
		int RTHead[] = new int[NrRTQueue+1];
		int T = 1;
		
		RTHead[0] = HeadStart;
		for(int j = 1; j < NrRTQueue+1; j++) {
			if(Priority[j-1] == 3) {
				RTHead[T] = RT_Queue[j-1];
				T = T + 1;
			}
		}
		
		for(int j = 1; j < NrRTQueue+1; j++) {
			if(Priority[j-1] == 2) {
				RTHead[T] = RT_Queue[j-1];
				T = T + 1;
			}
		}
		for(int j = 1; j < NrRTQueue+1; j++) {
			if(Priority[j-1] == 1) {
				RTHead[T] = RT_Queue[j-1];
				T = T + 1;
			}
		}
		
		for(int i = 0; i < NrRTQueue; i++) NrMoves = NrMoves + Math.abs(RTHead[i]-RTHead[i+1]);
		
		//----------------------------------------------------
		//Let's go to the left
		//Check how many jobs we have on the left of HeadStart
		int JobsOnTheLeft = 0;
		int k = 0;
		for(int j = 0; j < NrQueue; j++){
			if(Queue[j] <= RTHead[NrRTQueue]) {
				JobsOnTheLeft++;
			}
		}
		
		//Now we have the number new queue of all the jobs on the left, lets fill it with the correct jobs
		int NewQueueLeft[] = new int[JobsOnTheLeft];
		int HeadLeft[] = new int[JobsOnTheLeft+1];
		HeadLeft[0] = RTHead[NrRTQueue];
		
		for(int j = 0; j < NrQueue; j++){
			if(Queue[j] <= HeadLeft[0]) {
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
			if(Queue[j] > HeadLeft[0]) {
				JobsOnTheLeft2++;
			}
		}
		
		//Now we have the number new queue of all the jobs on the left again, lets fill it with the correct jobs
		int w = 0;
		int NewQueueLeft2[] = new int[JobsOnTheLeft2];
		int HeadLeft2[] = new int[JobsOnTheLeft2+1];
		HeadLeft2[0] = NrBlocks; //Point to the previous position
				
		for(int j = 0; j < NrQueue; j++){
			if(Queue[j] > HeadLeft[0]) {
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
		
		System.out.print("Sequence -> ");
		for(int i = 0; i < NrRTQueue; i++) System.out.print(RTHead[i] + " -> ");
		for(int i = 0; i < JobsOnTheLeft+1; i++) System.out.print(HeadLeft[i] + " -> ");
		for(int i = 0; i < JobsOnTheLeft2; i++) System.out.print(HeadLeft2[i] + " -> ");
		System.out.print(HeadLeft2[HeadLeft2.length-1]);
		
		System.out.println();
		System.out.println("Number of Moves: " + (NrMoves+ChangeDir));
	}
	
	//FCFS
	public static void FCFS_RT(int NrBlocks, int[] Queue, int HeadStart, int NrQueue, int[] RT_Queue, int NrRTQueue, int[] Priority) {
		int RTHead[] = new int[NrRTQueue+1];
		int Head[] = new int[NrQueue+1];
		int NrMoves = 0;
		int T = 1;
		
		RTHead[0] = HeadStart;
		
		for(int j = 1; j < NrRTQueue+1; j++) {
			if(Priority[j-1] == 3) {
				RTHead[T] = RT_Queue[j-1];
				T = T + 1;
			}
		}
		
		for(int j = 1; j < NrRTQueue+1; j++) {
			if(Priority[j-1] == 2) {
				RTHead[T] = RT_Queue[j-1];
				T = T + 1;
			}
		}
		for(int j = 1; j < NrRTQueue+1; j++) {
			if(Priority[j-1] == 1) {
				RTHead[T] = RT_Queue[j-1];
				T = T + 1;
			}
		}
		
		Head[0] = RTHead[NrRTQueue];
		for(int j = 1; j < NrQueue+1; j++) Head[j] = Queue[j-1];
		
		for(int i = 0; i < NrRTQueue; i++) NrMoves = NrMoves + Math.abs(RTHead[i]-RTHead[i+1]);
		
		for(int i = 0; i < NrQueue; i++) NrMoves = NrMoves + Math.abs(Head[i]-Head[i+1]);
		
		System.out.println();
		System.out.println("FCFS");
		
		System.out.print("Sequence -> ");
		for(int i = 0; i < NrRTQueue+1; i++) System.out.print(RTHead[i] + " -> ");
		for(int i = 1; i < NrQueue; i++) System.out.print(Head[i] + " -> ");
		System.out.print(Head[Head.length-1]);
		
		System.out.println();
		System.out.println("Number of Moves: " + NrMoves);
	}
	
    //Main
    public static void main(String[] args) throws ParseException { 
    	int NrBlocks = 0, HeadStart = 0, NrQueue = 0, op, NrRTQueue = 0;
    	int[] Queue = new int[NrQueue];
    	int[] RT_Queue = new int[NrRTQueue];
    	int[] Priority = new int[NrRTQueue];
    	
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
    		
    		System.out.print("Number of Real-Time Tasks: ");  	
    		NrRTQueue = scan.nextInt();
    	
    		System.out.print("Head Start (0-Number of Blocks): ");  	
    		HeadStart = scan.nextInt();
    	
    		Queue = new int[NrQueue];
    		RT_Queue = new int[NrRTQueue];
    		Priority = new int[NrRTQueue];
    	
    		for(int i = 0; i < NrQueue; i++) {
    			System.out.print("Position of Disk Non-Deadline Tasks " + (i+1) + ": ");  
    			Queue[i] = scan.nextInt();
    		}
    		
    		for(int i = 0; i < NrRTQueue; i++) {
    			System.out.print("Position of Disk Deadline Tasks " + (i+1) + ": ");  
    			RT_Queue[i] = scan.nextInt();
    			System.out.print("Priority (1-3) of Task " + (i+1) + ": ");  
    			Priority[i] = scan.nextInt();
    		}
    	}
    	else if(op == 2) {	  
    		System.out.print("Number of Blocks: ");  	
    		NrBlocks = scan.nextInt();
    	
    		System.out.print("Number of Non-Deadline Tasks: ");  	
    		NrQueue = scan.nextInt();
    		
    		System.out.print("Number of Deadline Tasks: ");  	
    		NrRTQueue = scan.nextInt();
    	
    		System.out.print("Head Start (0-Number of Blocks): ");  	
    		HeadStart = scan.nextInt();
    	
    		Queue = new int[NrQueue];
    		RT_Queue = new int[NrRTQueue];
    		Priority = new int[NrRTQueue];
    		
    		for(int i = 0; i < NrQueue; i++) {
    			Queue[i] = ThreadLocalRandom.current().nextInt(1, (NrBlocks+1));  //Generate values between 0-NrBlocks
    		}
    		
    		for(int i = 0; i < NrRTQueue; i++) {  
    			RT_Queue[i] = ThreadLocalRandom.current().nextInt(1, (NrBlocks+1));
    			Priority[i] = ThreadLocalRandom.current().nextInt(1, 4);
    		}
    	}
     
    	scan.close();
    	
    	System.out.print("\n");
    	System.out.println("Task Nr. | Disk Position | Priority");
    	
    	for(int i = 0; i < NrRTQueue; i++) {
    		System.out.format("%5d%5s%9d%9s%5d", (i+1), "|", RT_Queue[i], "|", Priority[i]);
    		System.out.print("\n");
    	}
    	
    	System.out.println();
    	
    	for(int i = 0; i < NrQueue; i++) {
    		System.out.format("%5d%5s%9d", (NrRTQueue+i+1), "|", Queue[i]);
    		System.out.print("\n");
    	}
    	
    	System.out.print("\n");
    	
    	FCFS_RT(NrBlocks, Queue, HeadStart, NrQueue, RT_Queue, NrRTQueue, Priority);
    	SCAN_RT(NrBlocks, Queue, HeadStart, NrQueue, RT_Queue, NrRTQueue, Priority);
    	CSCAN_RT(NrBlocks, Queue, HeadStart, NrQueue, RT_Queue, NrRTQueue, Priority);
    	SSTF_RT(NrBlocks, Queue, HeadStart, NrQueue, RT_Queue, NrRTQueue, Priority);
    }
}