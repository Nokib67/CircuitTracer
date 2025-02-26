import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail, NOkib Hossain
 */
public class CircuitTracer {
	private Storage<TraceState> stateStore;
	private CircuitBoard board;

	/** Launch the program. 
	 * 
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		new CircuitTracer(args); //create this with args
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private void printUsage() {
        System.out.println("Usage: java CircuitTracer -s|-q -c|-g filename");
		System.out.println("first arg: -s for stack or -q for queue\r\nsecond arg: -c for console output or -g for GUI output\r\nthird arg: input file name ");
       
    }
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 */
	public CircuitTracer(String[] args) {
		//TODO: parse and validate command line args - first validation provided
		if (args.length != 3) {
			printUsage();
			return; //exit the constructor immediately
		}
		if(!(args[0].equals( "-s") || args[0].equals( "-q")))
		{
			printUsage();
			return; //exit the constructor immediately
		}
		if(!(args[1].equals("-c") || args[1].equals("-g")))
		{
			printUsage();
			return; //exit the constructor immediately
		}

		//graphic user interface is not supported in this program
		if(args[1].equals("-g")) {
			System.out.println(" GUI is not Implemented in this version");
			return;
		}
		// queue implementation of the data structure to store trace states.
		if (args[0].equals("-q")) {
			stateStore = new Storage<TraceState>(Storage.DataStructure.queue);
		} 
		// stack implementation of the data structure to store all trace states.
		else {
			stateStore = new Storage<TraceState>(Storage.DataStructure.stack);
		}
		
		
		try{
		board = new CircuitBoard(args[2]);
		}catch(FileNotFoundException e)
		{
			System.out.println(e.toString());
			return;
		}catch(InvalidFileFormatException e)
		{
			System.out.println(e.toString());
			return;
		}

		ArrayList<TraceState> bestPaths = new ArrayList<TraceState>();
		Point startingPoint = board.getStartingPoint();

		//strong the initial open states
		if(board.isOpen(startingPoint.x, startingPoint.y + 1)) {
			stateStore.store(new TraceState(board, startingPoint.x, startingPoint.y + 1));

		}
		if(board.isOpen(startingPoint.x + 1, startingPoint.y - 1)) {
			stateStore.store(new TraceState(board, startingPoint.x, startingPoint.y - 1));

		}
		
		if(board.isOpen(startingPoint.x + 1, startingPoint.y)) {
			stateStore.store(new TraceState(board, startingPoint.x + 1, startingPoint.y));

		}
		if(board.isOpen(startingPoint.x - 1, startingPoint.y)) {
			stateStore.store(new TraceState(board, startingPoint.x - 1, startingPoint.y));

		}
		
		while(!stateStore.isEmpty()) {
			TraceState trace = stateStore.retrieve();

			//checks if the object retrieved from the stateStore has a complete path.
			if (trace.isSolution() ) {
				if (bestPaths.isEmpty()) { 
					bestPaths.add(trace); 
				} 
				//checks if the object paths length is equal to the one already present in the bestPath arrayList.
				else if (trace.pathLength() == bestPaths.get(0).pathLength()) { 
					bestPaths.add(trace); 
				}
				else if (trace.pathLength() < bestPaths.get(0).pathLength()) { 
					// clears the past bestPaths
					bestPaths.clear(); 
					bestPaths.add(trace); 
				}


			} else {

				//checks if there is a valid open spot to the bottom of the row but in the same column
				if(trace.isOpen(trace.getRow()-1, trace.getCol())) { 
					stateStore.store(new TraceState(trace,trace.getRow()-1, trace.getCol())); 
				}

				//checks if there is a valid open spot to the left but in the same row.
				if(trace.isOpen(trace.getRow(), trace.getCol()-1)) { 
					stateStore.store(new TraceState(trace,trace.getRow(), trace.getCol()-1)); 
				}
				//checks if there is a valid open spot to the top of the row but in the same column
				if(trace.isOpen(trace.getRow()+1, trace.getCol())) {
					stateStore.store(new TraceState(trace,trace.getRow()+1, trace.getCol())); 
				}
				//checks if there is a valid open spot to the right but in the same column.
				if(trace.isOpen(trace.getRow(), trace.getCol()+1)) { 
					stateStore.store(new TraceState(trace,trace.getRow(), trace.getCol()+1)); 
				}

			}

		}


		//printing in the consol
		for (int i = 0; i < bestPaths.size(); i++) {
			System.out.println(bestPaths.get(i).toString());
		}
		
	}
	
} // class CircuitTracer
