import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.text.ParseException; 
import java.io.*;

class T4 {		

	//Run
	public static void Run(int[] NrFramesPerProcess, int[] NrPages, int[] StrPages1, int[] StrPages2, int[] StrPages3, int[] StrPages4, int[] StrPages5, int[] StrPages6, int[] StrPages7, int[] StrPages8, int[] StrPages9, int[] StrPages10) {

		LRU(NrFramesPerProcess[0], NrPages[0], StrPages1); 
		LRU(NrFramesPerProcess[1], NrPages[1], StrPages2); 
		LRU(NrFramesPerProcess[2], NrPages[2], StrPages3); 
		LRU(NrFramesPerProcess[3], NrPages[3], StrPages4); 
		LRU(NrFramesPerProcess[4], NrPages[4], StrPages5); 
		LRU(NrFramesPerProcess[5], NrPages[5], StrPages6); 
		LRU(NrFramesPerProcess[6], NrPages[6], StrPages7); 
		LRU(NrFramesPerProcess[7], NrPages[7], StrPages8); 
		LRU(NrFramesPerProcess[8], NrPages[8], StrPages9); 
		LRU(NrFramesPerProcess[9], NrPages[9], StrPages10); 
	}

	//Proportional
	public static int[] Proportional(int NrFrames, int[] NrPages, int NrProcesses) {	
		int S = 0;
		int[] NrFramesPerProcess = new int[NrProcesses];

		for(int i = 0; i < NrProcesses; i++) {
			S = S + NrPages[i];
		}

		for(int i = 0; i < NrProcesses; i++) {
			NrFramesPerProcess[i] = (NrPages[i]*NrFrames)/S;
		}

		return NrFramesPerProcess;
	}

	//Equal
	public static int[] Equal(int NrFrames, int NrProcesses) {	
		int[] NrFramesPerProcess = new int[NrProcesses];

		for(int i = 0; i < NrProcesses; i++) {
			NrFramesPerProcess[i] = (int)NrFrames/NrProcesses;
		}

		return NrFramesPerProcess;
	}

	//LRU
	public static void LRU(int NrFramesThisProcess, int NrPages, int[] StrPages) {	
		int NrPageHit = 0, Min = Integer.MAX_VALUE, MinIndex = 0, HitIndex = -1;
		int[] Frame = new int[NrFramesThisProcess];
		int[] LastApp = new int[NrFramesThisProcess];

		//Fill Frames with Zeros
		for(int i=0; i < NrFramesThisProcess; i++){
			Frame[i] = 0;
		}

		//Go through each page
		for(int i = 0; i < NrPages; i++) {
			//Fill LastApp with -1
			for(int w=0; w < NrFramesThisProcess; w++){
				LastApp[w] = -1;
			}

			//Check all frames if page is already there
			HitIndex = -1;
			for(int j = 0; j < NrFramesThisProcess; j++){
				//There was a hit
				if(Frame[j] == StrPages[i]){
					NrPageHit++;
					HitIndex = j;
				}
			}

			//Check which page to remove
			for(int j = 0; j < NrFramesThisProcess; j++){
				//It will only check the pages that were already done with
				for(int k = 0; k < i; k++) {
					//It will fill with the number of the last appearance 
					if(Frame[j] == StrPages[k]){
						LastApp[j] = k;
						//The smallest one will be the one to switch
					}
				}
			}

			Min = Integer.MAX_VALUE;
			for(int y = 0; y < NrFramesThisProcess; y++){
				if(LastApp[y] < Min) {
					Min = LastApp[y];
					MinIndex = y;
				}
			}

			//If there was a hit
			if(HitIndex != -1) {
				Frame[HitIndex] = StrPages[i];
			}
			else{
				Frame[MinIndex] = StrPages[i];
			}
		}

		System.out.println("LRU Page Fault -> " + (NrPages - NrPageHit));
	}

	//Main
	public static void main(String[] args) throws ParseException { 
		int Length = 0, NrAllFrames = 0, NrProcesses = 10, MaxPages = 0;

		Scanner scan = new Scanner(System.in);

		System.out.print("Length: ");  	
		Length = scan.nextInt();

		System.out.print("Max Number of Pages: ");  	
		MaxPages = scan.nextInt();

		System.out.print("Number of Frames (It Must Be Bigger or Equal To 10): ");  	
		NrAllFrames = scan.nextInt();

		int[] NrFramesPerProcess = new int[NrProcesses];
		int[] NrPages = new int[NrProcesses];

		for(int i = 0; i < NrProcesses; i++) {
			NrPages[i] = ThreadLocalRandom.current().nextInt((NrAllFrames+1), (MaxPages+1));
		}

		int[] StrPages1 = new int[NrPages[0]];
		int[] StrPages2 = new int[NrPages[1]];
		int[] StrPages3 = new int[NrPages[2]];
		int[] StrPages4 = new int[NrPages[3]];
		int[] StrPages5 = new int[NrPages[4]];
		int[] StrPages6 = new int[NrPages[5]];
		int[] StrPages7 = new int[NrPages[6]];
		int[] StrPages8 = new int[NrPages[7]];
		int[] StrPages9 = new int[NrPages[8]];
		int[] StrPages10 = new int[NrPages[9]];

		for(int i = 0; i < NrPages[0]; i++) StrPages1[i] = ThreadLocalRandom.current().nextInt(1, (Length+1));
		for(int i = 0; i < NrPages[1]; i++) StrPages2[i] = ThreadLocalRandom.current().nextInt(1, (Length+1));
		for(int i = 0; i < NrPages[2]; i++) StrPages3[i] = ThreadLocalRandom.current().nextInt(1, (Length+1));
		for(int i = 0; i < NrPages[3]; i++) StrPages4[i] = ThreadLocalRandom.current().nextInt(1, (Length+1));
		for(int i = 0; i < NrPages[4]; i++) StrPages5[i] = ThreadLocalRandom.current().nextInt(1, (Length+1));
		for(int i = 0; i < NrPages[5]; i++) StrPages6[i] = ThreadLocalRandom.current().nextInt(1, (Length+1));
		for(int i = 0; i < NrPages[6]; i++) StrPages7[i] = ThreadLocalRandom.current().nextInt(1, (Length+1));
		for(int i = 0; i < NrPages[7]; i++) StrPages8[i] = ThreadLocalRandom.current().nextInt(1, (Length+1));
		for(int i = 0; i < NrPages[8]; i++) StrPages9[i] = ThreadLocalRandom.current().nextInt(1, (Length+1));
		for(int i = 0; i < NrPages[9]; i++) StrPages10[i] = ThreadLocalRandom.current().nextInt(1, (Length+1));

		//Proportional Allocation
		System.out.println();
		System.out.println("Proportional Allocation");
		NrFramesPerProcess = Proportional(NrAllFrames, NrPages, NrProcesses);
		for(int j = 0; j < NrProcesses; j++) {
			System.out.print(NrFramesPerProcess[j]);
			System.out.print(" ");
		}
		System.out.println();
		Run(NrFramesPerProcess, NrPages, StrPages1, StrPages2, StrPages3, StrPages4, StrPages5, StrPages6, StrPages7, StrPages8, StrPages9, StrPages10);

		//Equal Allocation
		System.out.println();
		System.out.println("Equal Allocation");
		NrFramesPerProcess = Equal(NrAllFrames, NrProcesses);
		for(int j = 0; j < NrProcesses; j++) {
			System.out.print(NrFramesPerProcess[j]);
			System.out.print(" ");
		}
		System.out.println();
		Run(NrFramesPerProcess, NrPages, StrPages1, StrPages2, StrPages3, StrPages4, StrPages5, StrPages6, StrPages7, StrPages8, StrPages9, StrPages10);

		while(true) {
			System.out.println();
			System.out.print("Number of Frames (It Must Be Bigger Than 10): ");  	
			NrAllFrames = scan.nextInt();

			//Proportional Allocation
			System.out.println();
			System.out.println("Proportional Allocation");
			NrFramesPerProcess = Proportional(NrAllFrames, NrPages, NrProcesses);
			for(int j = 0; j < NrProcesses; j++) {
				System.out.print(NrFramesPerProcess[j]);
				System.out.print(" ");
			}
			System.out.println();
			Run(NrFramesPerProcess, NrPages, StrPages1, StrPages2, StrPages3, StrPages4, StrPages5, StrPages6, StrPages7, StrPages8, StrPages9, StrPages10);

			//Equal Allocation
			System.out.println();
			System.out.println("Equal Allocation");
			NrFramesPerProcess = Equal(NrAllFrames, NrProcesses);
			Run(NrFramesPerProcess, NrPages, StrPages1, StrPages2, StrPages3, StrPages4, StrPages5, StrPages6, StrPages7, StrPages8, StrPages9, StrPages10);

		}
	}
}