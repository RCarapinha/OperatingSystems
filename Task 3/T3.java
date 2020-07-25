import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.text.ParseException;

class T2 {
	//FIFO
	public static void FIFO(int NrFrames, int NrPages, int[] StrPages) {
		int NrPageHit = 0, OldestFrame = 0;
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
                    break;
                    //We need a break, to fetch in which frame there was a hit
                }
            }

			if(OldestFrame == NrFrames) {
				OldestFrame = 0;
			}

			//There wasn't a hit
	        if(flag){
	        	Frame[OldestFrame] = StrPages[i];
	        	OldestFrame++;
	        }

	        System.out.println("Num: " + OldestFrame);

	        System.out.print("Frame : ");

	        for(int k = 0; k < NrFrames; k++) {
	        	System.out.print(Frame[k]+" ");
	        }

	        System.out.println();
		}

		System.out.println();
		System.out.println("FIFO");
		System.out.println("Page Fault: " + (NrPages - NrPageHit));
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

    	scan.close();

    	System.out.print("\n");
    	System.out.println("String -> ");

    	for(int i = 0; i < NrPages; i++) {
    		System.out.print(StrPages[i] + ", ");
    	}

    	System.out.print("\n");

    	FIFO(NrFrames, NrPages, StrPages);
    }
}
