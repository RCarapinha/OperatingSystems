import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.text.ParseException; 
import java.util.InputMismatchException;
import java.lang.IllegalArgumentException;
  
class T1 { 
	
	//SJF Preemptive
 	public static void SJFP(int NrProcesses, int[] ExecutionTime, int[] ArrivalTime, int[] Processes) { 
		int WaitingTime[] = new int[NrProcesses]; 
		int RemainingTime[] = new int[NrProcesses]; 
		
		int Complete = 0, ProgramTime = 0, Total_WaitingTime = 0, Min = Integer.MAX_VALUE, Shortest = 0, FinishTime;
		
		boolean flag = false;
		String seq = "";
	
		// Copy the burst time into RemainingTime[] 
		for (int i = 0; i < NrProcesses; i++) 
			RemainingTime[i] = ExecutionTime[i]; 
	
		// Process until all processes gets completed 
		while (Complete != NrProcesses) { 
			// Find process with minimum remaining time among the processes that arrives till the current time
			for (int j = 0; j < NrProcesses; j++) { 
				if ((ArrivalTime[j] <= ProgramTime) && (RemainingTime[j] < Min) && RemainingTime[j] > 0) { 
					Min = RemainingTime[j]; 
					Shortest = j; 
					flag = true; 
					seq = seq + Integer.toString(j + 1) + " -> ";
				} 
			} 
	
			if (flag == false) { 
				ProgramTime++; 
				continue; 
			} 
	
			// Reduce remaining time by one 
			RemainingTime[Shortest]--; 
	
			// Update minimum 
			Min = RemainingTime[Shortest]; 
			if (Min == 0) 
				Min = Integer.MAX_VALUE; 
	
			// If a process gets completely executed 
			if (RemainingTime[Shortest] == 0) { 
				// Increment complete 
				Complete++; 
				flag = false; 
	
				// Find finish time of current process 
				FinishTime = ProgramTime + 1; 
	
				// Calculate waiting time 
				WaitingTime[Shortest] = FinishTime - ExecutionTime[Shortest] - ArrivalTime[Shortest]; 
	
				if (WaitingTime[Shortest] < 0) 
					WaitingTime[Shortest] = 0; 
			} 
			
			// Increment time 
			ProgramTime++; 
		}
	
		// Calculate total waiting time
		for (int i = 0; i < NrProcesses; i++) { 
			Total_WaitingTime = Total_WaitingTime + WaitingTime[i]; 
		} 
	
		System.out.printf("SJF - Preemptive\n"); 
		System.out.println("Average waiting time -> " + (float)Total_WaitingTime / (float)NrProcesses); 
    	String S = seq.substring(0, seq.length() - 4);
        System.out.println("Sequence -> " + S); 
        System.out.printf("\n");
	} 
	
	//SJF Non Preemptive
	public static void SJFNP(int NrProcesses, int[] ExecutionTime, int[] ArrivalTime, int[] Processes) {
		int CompleteTime[] = new int[NrProcesses];
		int WaitingTime[] = new int[NrProcesses];
		int Finished[] = new int[NrProcesses];

		int ProgramTime = 0, t = 0, Min = Integer.MAX_VALUE, c = NrProcesses, Total_WaitingTime = 0;
		
		String seq = "";
		
		for (int j = 0; j < NrProcesses; j++){
			Finished[j] = 0;
		}
	
		while(true){
			c = NrProcesses;
			Min = Integer.MAX_VALUE; // Reset minimum
			if (t == NrProcesses) // total no of process = completed process loop will be terminated
				break;
			
			for (int i = 0; i < NrProcesses; i++){
				if ((ArrivalTime[i] <= ProgramTime) && (Finished[i] == 0) && (ExecutionTime[i] < Min)){
					Min = ExecutionTime[i];
					c = i;
				}
			}
			
			/* If c==n means c value can not updated because no process arrival time< system time so we increase the system time */
			if (c == NrProcesses) ProgramTime++;
			else{
				seq = seq + Integer.toString(c + 1) + " -> ";
				CompleteTime[c] = ProgramTime + ExecutionTime[c];
				ProgramTime += ExecutionTime[c];
				WaitingTime[c] = CompleteTime[c] - ArrivalTime[c] - ExecutionTime[c];
				Finished[c] = 1;
				t++;
			}
			
			for (int i = 0; i < NrProcesses; i++) { 
				Total_WaitingTime = Total_WaitingTime + WaitingTime[i]; 
			} 
			
		}
	
		System.out.printf("SJF - Non Preemptive\n"); 
		System.out.println("Average waiting time -> " + (float)(Total_WaitingTime/NrProcesses)); 
    	String S = seq.substring(0, seq.length() - 4);
        System.out.println("Sequence -> " + S); 
        System.out.printf("\n");
	}
	
	//FCFS    
	public static void FCFS(int NrProcesses, int[] ExecutionTime, int[] ArrivalTime, int[] Processes) {
    	int[] WaitingTime = new int[NrProcesses];
    	int ServiceTime[] = new int[NrProcesses];
    	int Total_WaitingTime = 0;
    	String seq = "";
    	
    	ServiceTime[0] = 0;
    	
    	// waiting time for first process is 0  
    	WaitingTime[0] = 0; 
  
        // calculating waiting time  
        for (int i = 1; i < NrProcesses; i++) { 
        	// Add burst time of previous processes  
        	ServiceTime[i] = ServiceTime[i-1] + ExecutionTime[i-1];  

   	     	// Find waiting time for current process =   sum - at[i]  
        	WaitingTime[i] = ServiceTime[i] - ArrivalTime[i];  

   	     	// If waiting time for a process is in negative that means it is already in the ready queue before CPU becomes idle so its waiting time is 0  
   	     	if (WaitingTime[i] < 0) WaitingTime[i] = 0;  
        }
    	  
        // Calculate total waiting time
        for (int i = 0; i < NrProcesses; i++) { 
        	Total_WaitingTime = Total_WaitingTime + WaitingTime[i]; 
        }
        
    	for (int i = 0; i < NrProcesses; i++) { 
    		seq = seq + Integer.toString(Processes[i] + 1) + " -> ";
        }
    	
    	System.out.printf("FCFS\n"); 
    	System.out.printf("Average waiting time -> %f\n", (float)Total_WaitingTime / (float)NrProcesses); 
    	String S = seq.substring(0, seq.length() - 4);
        System.out.println("Sequence -> " + S); 
        System.out.printf("\n");
    }
    
    //RR
	public static void RoundRobin(int[] Processes, int ArrivalTime[], int ExecutionTime[], int RRTime, int NrProcesses) { 
    	int Time = 0; 
        int WaitingTime[] = new int[NrProcesses]; 
        String seq = "";
        int Res_ExecutionTime[] = new int[NrProcesses]; 
        int Res_ArrivalTime[] = new int[NrProcesses]; 
        int Total_WaitingTime = 0; 
   
        for (int i = 0; i < NrProcesses; i++) { 
        	Res_ExecutionTime[i] = ExecutionTime[i]; 
        	Res_ArrivalTime[i] = ArrivalTime[i]; 
        }
   
         while (true) { 
             boolean flag = true; 
             for (int i = 0; i < NrProcesses; i++) { 
                 if (Res_ArrivalTime[i] <= Time) { 	     		  // if already arrived
                     if (Res_ArrivalTime[i] <= RRTime) { 		  // if ArrivalTime is less than the Round Robin Time
                         if (Res_ExecutionTime[i] > 0) { 		  // if still didn't finished
                             flag = false; 
                             if (Res_ExecutionTime[i] > RRTime) { //it will execute but not till the end
                            	 Time = Time + RRTime; 
                            	 Res_ExecutionTime[i] = Res_ExecutionTime[i] - RRTime; 
                            	 Res_ArrivalTime[i] = Res_ArrivalTime[i] + RRTime; 
                            	 seq = seq + Integer.toString(Processes[i] + 1) + " -> ";
                             } 
                             else { 													 //it will execute till the end
                            	 Time = Time + Res_ExecutionTime[i];  					 // for last time 
                            	 WaitingTime[i] = Time - ExecutionTime[i] - ArrivalTime[i]; // store wait time 
                                 Res_ExecutionTime[i] = 0; 								 //it finished
                                 seq = seq + Integer.toString(Processes[i] + 1) + " -> ";
                             } 
                         } 
                     } 
                     else if (Res_ArrivalTime[i] > RRTime) { 				  //if it didn't arrived, it will check the coming process and execute them
                         for (int j = 0; j < NrProcesses; j++) {  
                             if (Res_ExecutionTime[j] < Res_ArrivalTime[i]) { // compare
                                 if (Res_ExecutionTime[j] > 0) {			  // if still didn't finished
                                     flag = false; 
                                     if (Res_ExecutionTime[j] > RRTime) { 	  //it will execute but not till the end
                                    	 Time = Time + RRTime; 
                                    	 Res_ExecutionTime[j] = Res_ExecutionTime[j] - RRTime; 
                                    	 Res_ArrivalTime[j] = Res_ArrivalTime[j] + RRTime; 
                                    	 seq = seq + Integer.toString(Processes[j] + 1) + " -> ";
                                     } 
                                     else { 								  //it will execute till the end
                                    	 Time = Time + Res_ExecutionTime[j]; 
                                    	 WaitingTime[j] = Time - ExecutionTime[j] - ArrivalTime[j]; 
                                    	 Res_ExecutionTime[j] = 0; 
                                    	 seq = seq + Integer.toString(Processes[j] + 1) + " -> "; 
                                     } 
                                 } 
                             } 
                         } 
   
                         // now the previous Processes according to 
                         // ith is process 
                         if (Res_ExecutionTime[i] > 0) { 
                             flag = false; 
   
                             // Check for greaters 
                             if (Res_ExecutionTime[i] > RRTime) { 
                            	 Time = Time + RRTime; 
                            	 Res_ExecutionTime[i] = Res_ExecutionTime[i] - RRTime; 
                            	 Res_ArrivalTime[i] = Res_ArrivalTime[i] + RRTime; 
                            	 seq = seq + Integer.toString(Processes[i] + 1) + " -> ";
                             } 
                             else { 
                            	 Time = Time + Res_ExecutionTime[i]; 
                            	 WaitingTime[i] = Time - ExecutionTime[i] - ArrivalTime[i]; 
                            	 Res_ExecutionTime[i] = 0; 
                            	 seq = seq + Integer.toString(Processes[i] + 1) + " -> ";
                             } 
                         } 
                     } 
                 } 
   
                 // if no process is come on these critical 
                 else if (Res_ArrivalTime[i] > Time) { 
                	 Time++; 
                     i--; 
                 }
             }
             
             // for exit the while loop 
             if (flag) { 
                 break; 
             }
         } 

         for (int i = 0; i < NrProcesses; i++) { 
         	Total_WaitingTime = Total_WaitingTime + WaitingTime[i]; 
         } 
   
         System.out.printf("Round Robin\n");
         System.out.println("Average waiting time -> " + (float)Total_WaitingTime / NrProcesses); 
         String S = seq.substring(0, seq.length() - 4);
         System.out.println("Sequence -> " + S); 
         System.out.printf("\n");
     } 
  
    //Main
    public static void main(String[] args) throws ParseException { 
    	Scanner scan = new Scanner(System.in);
    	int op, NrProcesses = 0, RRTime = 0, ExecutionTimeMAX = 0, ArrivalTimeMAX = 0;
    	int[] Processes = new int[0];
    	int[] ExecutionTime = new int[0];
    	int[] ArrivalTime = new int[0];
    	
    	while(true) {
    		System.out.println("1 -> Create Processes");
    		System.out.println("2 -> Generate Processes");
    		System.out.print("Select : ");
    		op = scan.nextInt();
    		if(op == 1 || op == 2) {
    			break;
    		}
    	}
    		
    	if(op == 1) {
        	System.out.print("Number of Processes : ");  	
        	NrProcesses = scan.nextInt();
        
        	Processes = new int[NrProcesses];
        	ExecutionTime = new int[NrProcesses];
        	ArrivalTime = new int[NrProcesses];
        	
        	System.out.print("Round-Robin Time Of Execution : ");
        	RRTime = scan.nextInt();
        	
        	for (int i = 0; i < NrProcesses; i++) {
        		Processes[i] = i;
        		System.out.print("Execution Time of Process Nr. " + (i+1) + " : ");
        		ExecutionTime[i] = scan.nextInt();
        		System.out.print("Arrival Time of Process Nr. " + (i+1) + " : ");
        		ArrivalTime[i] = scan.nextInt();
        	}
        }
        	
        else if(op == 2) {	       	
        	System.out.print("Number of Processes : ");  	
        	NrProcesses = scan.nextInt();
        	
        	Processes = new int[NrProcesses];
        	ExecutionTime = new int[NrProcesses];
        	ArrivalTime = new int[NrProcesses];
        	
        	System.out.print("Round-Robin Time Of Execution : ");
            RRTime = scan.nextInt();
        	System.out.print("Execution Times Values Between 1 and : ");
        	ExecutionTimeMAX = scan.nextInt();
        	System.out.print("Arrival Times Values Between 0 and : ");
        	ArrivalTimeMAX = scan.nextInt();
        	
        	for (int i = 0; i < NrProcesses; i++) {
        		Processes[i] = i;
        		ExecutionTime[i] = ThreadLocalRandom.current().nextInt(1, (ExecutionTimeMAX+1)); //Generate values between 1-ExecutionTimeMAX
        		ArrivalTime[i] = ThreadLocalRandom.current().nextInt(0, (ArrivalTimeMAX+1));   //Generate values between 0-ArrivalTimeMAX
        	}
        }
    	
    	scan.close();
    	
    	System.out.print("\n");
    	System.out.println("Processes | Execution Time | Arrival Time");
    	
    	for(int i = 0; i < NrProcesses; i++) {
    		System.out.format("%5d%6s%9d%8s%8d", (Processes[i]+1), "|", ExecutionTime[i], "|", ArrivalTime[i]);
    		System.out.print("\n");
    	}
    	
    	System.out.print("\n");
    	
    	scan.close();

    	SJFP(NrProcesses, ExecutionTime, ArrivalTime, Processes);
    	SJFNP(NrProcesses, ExecutionTime, ArrivalTime, Processes);
    	FCFS(NrProcesses, ExecutionTime, ArrivalTime, Processes);
    	RoundRobin(Processes, ArrivalTime, ExecutionTime, RRTime, NrProcesses);
    }
}  