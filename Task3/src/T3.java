import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.text.ParseException; 

class T3 {		

	//LRU
	public static void LRU(int NrFrames, int NrPages, int[] StrPages) {	
		int NrPageHit = 0, Min = Integer.MAX_VALUE, MinIndex = 0, HitIndex = -1, WontAppearIndex = 0;
		int[] Frame = new int[NrFrames];
		int[] LastApp = new int[NrFrames];

		//Fill Frames with Zeros
		for(int i=0; i < NrFrames; i++){
			Frame[i] = 0;
		}

		//Go through each page
		for(int i = 0; i < NrPages; i++) {
			//Fill LastApp with -1
			for(int w=0; w < NrFrames; w++){
				LastApp[w] = -1;
			}

			//Check all frames if page is already there
			HitIndex = -1;
			for(int j = 0; j < NrFrames; j++){
				//There was a hit
				if(Frame[j] == StrPages[i]){
					NrPageHit++;
					HitIndex = j;
				}
			}

			//Check which page to remove
			for(int j = 0; j < NrFrames; j++){
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
			for(int y = 0; y < NrFrames; y++){
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

		System.out.println("LRU");
		System.out.println("Page Fault: " + (NrPages - NrPageHit));
		System.out.println();
	}

	//OPT
	public static void OPT(int NrFrames, int NrPages, int[] StrPages) {	
		int NrPageHit = 0, Max = Integer.MIN_VALUE, MaxIndex = 0, HitIndex = -1, WontAppearIndex = 0;
		boolean flag = false;
		int[] Frame = new int[NrFrames];
		int[] NextApp = new int[NrFrames];

		//Fill Frames with Zeros
		for(int i=0; i < NrFrames; i++){
			Frame[i] = 0;
		}

		//Go through each page
		for(int i = 0; i < NrPages; i++) {
			//Fill Frames with Zeros
			for(int w=0; w < NrFrames; w++){
				NextApp[w] = -1;
			}

			//Check all frames if page is already there
			HitIndex = -1;
			for(int j = 0; j < NrFrames; j++){
				//There was a hit
				if(Frame[j] == StrPages[i]){
					NrPageHit++;
					HitIndex = j;
				}
			}

			//Check which page to remove
			for(int j = 0; j < NrFrames; j++){
				//It will only check the remaining pages
				for(int k = i; k < NrPages; k++) {
					//It appeared 
					if(NextApp[j] == -1) {
						if(Frame[j] == StrPages[k]){
							NextApp[j] = k;
							//The biggest one will be the one to switch
						}
					}
				}
			}

			flag = false;
			Max = Integer.MIN_VALUE;
			for(int y = 0; y < NrFrames; y++){
				//It means it won't appear anymore
				if(NextApp[y] == -1) {
					WontAppearIndex = y;
					flag = true;
				}
				else if(NextApp[y] > Max) {
					Max = NextApp[y];
					MaxIndex = y;
				}	
			}

			//If there was a hit
			if(HitIndex != -1) {
				Frame[HitIndex] = StrPages[i];
			}

			//It means it won't appear anymore
			else if(flag == true) {
				Frame[WontAppearIndex] = StrPages[i];
			}

			//It means there's no -1  in the NextApp array
			else if(flag == false) {
				Frame[MaxIndex] = StrPages[i];
			}
		}

		System.out.println("OPT");
		System.out.println("Page Fault: " + (NrPages - NrPageHit));
		System.out.println();
	}

	//RAND
	public static void RAND(int NrFrames, int NrPages, int[] StrPages) {	
		int NrPageHit = 0, NextFrame = 0, P = 0;
		int[] Frame = new int[NrFrames];

		//Fill Frames with Zeros
		for(int i=0; i < NrFrames; i++){
			Frame[i] = 0;
		}
		
		for(int k = 0; k < NrFrames; k++) {
			Frame[k] = StrPages[k];
			P = k;
		}

		//Go through each page
		for(int i = P; i < NrPages; i++) {
			//Check all frames if page is already there
			for(int j = 0; j < NrFrames; j++){
				//There was a hit
				if(Frame[j] == StrPages[i]){
					NrPageHit++;
				}
			}

			//Randomly delete a page
			NextFrame = ThreadLocalRandom.current().nextInt(0, NrFrames);
			Frame[NextFrame] = StrPages[i];
			
			System.out.print("Frame: ");
			for(int j = 0; j < NrFrames; j++){
				System.out.print(Frame[j] + " ");
			}
			System.out.println("");
		}

		System.out.println("RAND");
		System.out.println("Page Fault: " + (NrPages - NrPageHit));
		System.out.println();
	}

	//FIFO
	public static void FIFO(int NrFrames, int NrPages, int[] StrPages) {	
		int NrPageHit = 0, NextFrame = 0, FrameHit = 0;
		boolean flag;
		int[] Frame = new int[NrFrames];

		//Fill Frames with Zeros
		for(int i=0; i < NrFrames; i++){
			Frame[i] = 0;
		}

		//Go through each page
		for(int i = 0; i < NrPages; i++) {
			flag = true;
			//Check all frames if page is already there
			for(int j = 0; j < NrFrames; j++){
				//There was a hit
				if(Frame[j] == StrPages[i]){
					flag = false;
					NrPageHit++;
					FrameHit = j;
				}
			}

			//There wasn't a hit, let's put on the first frame (it was the first in) and jump to the next one
			if(flag){
				Frame[NextFrame] = StrPages[i];
				NextFrame++;
				if(NextFrame >= NrFrames) {
					NextFrame = 0;
				}
			}
			//There was a hit
			else {
				Frame[FrameHit] = StrPages[i];
			}
		}

		System.out.println("FIFO");
		System.out.println("Page Fault: " + (NrPages - NrPageHit));
		System.out.println();
	}

	//Main
	public static void main(String[] args) throws ParseException { 
		int NrPages = 0, Length, op, NrFrames = 0;
		int[] StrPages = new int[NrPages];

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
			System.out.print("Number of Frames: ");  	
			NrFrames = scan.nextInt();

			System.out.print("Number of Pages: ");  	
			NrPages = scan.nextInt();

			System.out.print("Length: ");  	
			Length = scan.nextInt();

			StrPages = new int[NrPages];

			for(int i = 0; i < NrPages; i++) {
				System.out.print("Page Nr. " + (i+1) + ": ");  
				StrPages[i] = scan.nextInt();
			}
		}
		else if(op == 2) {	  
			System.out.print("Number of Frames: ");  	
			NrFrames = scan.nextInt();

			System.out.print("Number of Pages: ");  	
			NrPages = scan.nextInt();

			System.out.print("Length: ");  	
			Length = scan.nextInt();

			StrPages = new int[NrPages];

			for(int i = 0; i < NrPages; i++) {
				StrPages[i] = ThreadLocalRandom.current().nextInt(1, (Length+1));  //Generate values between 0-NrBlocks
			}
		}
		
		System.out.print("\n");
		System.out.print("String -> ");

		for(int i = 0; i < NrPages; i++) {
			System.out.print(StrPages[i] + ", ");
		}

		System.out.println("\n");

		FIFO(NrFrames, NrPages, StrPages);
		RAND(NrFrames, NrPages, StrPages);
		OPT(NrFrames, NrPages, StrPages);
		LRU(NrFrames, NrPages, StrPages);
		
		while(true) {
			System.out.print("Number of Frames: ");  	
			NrFrames = scan.nextInt();
			FIFO(NrFrames, NrPages, StrPages);
			RAND(NrFrames, NrPages, StrPages);
			OPT(NrFrames, NrPages, StrPages);
			LRU(NrFrames, NrPages, StrPages);
		}
	}
}