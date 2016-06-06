
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class StaticParameters {
	static Vector<int[]> BlackArea;
	static Vector<int[]> GrayArea;
	static int BATTERY_CAPACITY;
	static float BATTERY_COST_WALK;
	static float BATTERY_COST_SEND_MSG;
	static float BATTERY_COST_GET_MSG;
	static float BATTERY_CHARGE_LITHT_SPEED;
	static int TRANSMISSION_RANGE;
	static int ROBOTS_NOT_MOVE;
	static int ROBOTS_MOVE;
	static int ARENA_X;
	static int ARENA_Y;
	static int LOG_FILE_DIRECTORY;
	static int WHITE;
	static int GRAY;
	static int BLACK;
	static int OLDER;
	static int SAME_AGE;
	static int YOUNGER;
	static int UP;
	static int LEFT;
	static int DOWN;
	static int RIGHT;
	static float INSTANT_SENDING_CHANCE;
	static float SENDING_KNOWN_DEVIATION;
	static int MAX_NUM_OF_VERSIONS;
	static	int MESSAGE_LIFE_TIME;
	static int ROBOT_LEANGHT;
	static int INFINITY;
	static int MIN_MSG_RANGE;
	static int MAX_MSG_RANGE;
	static Message NO_MSG;
	static int MSG_LIFE_TIME;
	static int MSG_MAX_VERSION;
	static int MSG_WAIT_TIME;
	static float BATTERY_ABOUT_TO_END;
	static float ROBOT_STATIC_CHANCE_SEND_MSG;
	static float ROBOT_CANMOVE_CHANCE_SEND_MSG;
	static float ROBOT_CANMOVE_CHANCE_GET_MSG;
	static int ROBOTS_NOT_MOVE_COLOR;
	static int ROBOTS_MOVE_COLOR;
	static int ACTIVE_MATDISTANCE;
	
	public static Vector<int[]> BlackArea() {
		return BlackArea;
	}

	public static Vector<int[]> GrayArea() {
		return GrayArea;
	}

	public static int BATTERY_CAPACITY() {
		return BATTERY_CAPACITY;
	}

	public static float BATTERY_COST_WALK() {
		return BATTERY_COST_WALK;
	}

	public static float BATTERY_COST_SEND_MSG() {
		return BATTERY_COST_SEND_MSG;
	}

	public static float BATTERY_COST_GET_MSG() {
		return BATTERY_COST_GET_MSG;
	}

	public static float BATTERY_CHARGE_LITHT_SPEED() {
		return BATTERY_CHARGE_LITHT_SPEED;
	}

	public static int TRANSMISSION_RANGE() {
		return TRANSMISSION_RANGE;
	}

	public static int ROBOTS_NOT_MOVE() {
		return ROBOTS_NOT_MOVE;
	}

	public static int ROBOTS_MOVE() {
		return ROBOTS_MOVE;
	}

	public static int ARENA_X() {
		return ARENA_X;
	}

	public static int ARENA_Y() {
		return ARENA_Y;
	}

	public static int LOG_FILE_DIRECTORY() {
		return LOG_FILE_DIRECTORY;
	}

	public static int WHITE() {
		return WHITE;
	}

	public static int GRAY() {
		return GRAY;
	}

	public static int BLACK() {
		return BLACK;
	}

	public static int OLDER() {
		return OLDER;
	}

	public static int SAME_AGE() {
		return SAME_AGE;
	}

	public static int YOUNGER() {
		return YOUNGER;
	}

	public static int UP() {
		return UP;
	}

	public static int LEFT() {
		return LEFT;
	}

	public static int DOWN() {
		return DOWN;
	}

	public static int RIGHT() {
		return RIGHT;
	}

	public static float INSTANT_SENDING_CHANCE() {
		return INSTANT_SENDING_CHANCE;
	}

	public static float SENDING_KNOWN_DEVIATION() {
		return SENDING_KNOWN_DEVIATION;
	}

	public static int MAX_NUM_OF_VERSIONS() {
		return MAX_NUM_OF_VERSIONS;
	}

	public static int MESSAGE_LIFE_TIME() {
		return MESSAGE_LIFE_TIME;
	}

	public static int ROBOT_LEANGHT() {
		return ROBOT_LEANGHT;
	}

	public static int INFINITY() {
		return INFINITY;
	}

	public static int MIN_MSG_RANGE() {
		return MIN_MSG_RANGE;
	}

	public static int MAX_MSG_RANGE() {
		return MAX_MSG_RANGE;
	}

	public static Message NO_MSG() {
		return NO_MSG;
	}

	public static int MSG_LIFE_TIME() {
		return MSG_LIFE_TIME;
	}

	public static int MSG_MAX_VERSION() {
		return MSG_MAX_VERSION;
	}

	public static int MSG_WAIT_TIME() {
		return MSG_WAIT_TIME;
	}

	public static float BATTERY_ABOUT_TO_END() {
		return BATTERY_ABOUT_TO_END;
	}

	public static float ROBOT_STATIC_CHANCE_SEND_MSG() {
		return ROBOT_STATIC_CHANCE_SEND_MSG;
	}

	public static float ROBOT_CANMOVE_CHANCE_SEND_MSG() {
		return ROBOT_CANMOVE_CHANCE_SEND_MSG;
	}

	public static float ROBOT_CANMOVE_CHANCE_GET_MSG() {
		return ROBOT_CANMOVE_CHANCE_GET_MSG;
	}

	public static int ROBOTS_NOT_MOVE_COLOR() {
		return ROBOTS_NOT_MOVE_COLOR;
	}

	public static int ROBOTS_MOVE_COLOR() {
		return ROBOTS_MOVE_COLOR;
	}

	public static int ACTIVE_MATDISTANCE() {
		return ACTIVE_MATDISTANCE;
	}





	public StaticParameters(String Path){
		File f=new File(Path);
		Scanner scanner = null;
		try {
			scanner = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BATTERY_CAPACITY=GetNumber(scanner);
		BATTERY_COST_WALK=GetNumberFloat(scanner);
		BATTERY_COST_SEND_MSG=GetNumberFloat(scanner);
		BATTERY_COST_GET_MSG=GetNumberFloat(scanner);
		BATTERY_CHARGE_LITHT_SPEED=GetNumberFloat(scanner);
		TRANSMISSION_RANGE=GetNumber(scanner);
		ROBOT_STATIC_CHANCE_SEND_MSG=GetNumberFloat(scanner);
		ROBOT_CANMOVE_CHANCE_SEND_MSG=GetNumberFloat(scanner);
		ROBOT_CANMOVE_CHANCE_GET_MSG=GetNumberFloat(scanner);
		ROBOTS_NOT_MOVE=GetNumber(scanner);
		ROBOTS_MOVE=GetNumber(scanner);
		ROBOTS_NOT_MOVE_COLOR=GetNumber(scanner);
		ROBOTS_MOVE_COLOR=GetNumber(scanner);
		ARENA_X=GetNumber(scanner);
		ARENA_Y=GetNumber(scanner);
		BlackArea=GetCord(scanner);
		GrayArea=GetCord(scanner);
		LOG_FILE_DIRECTORY=GetNumber(scanner);
		WHITE=GetNumber(scanner);
		GRAY=GetNumber(scanner);
		BLACK=GetNumber(scanner);
		OLDER=GetNumber(scanner);
		SAME_AGE=GetNumber(scanner);
		YOUNGER=GetNumber(scanner);
		UP=GetNumber(scanner);
		LEFT=GetNumber(scanner);
		DOWN=GetNumber(scanner);
		RIGHT=GetNumber(scanner);
		INSTANT_SENDING_CHANCE=GetNumberFloat(scanner);
		SENDING_KNOWN_DEVIATION=GetNumberFloat(scanner);
		MAX_NUM_OF_VERSIONS=GetNumber(scanner);
		MESSAGE_LIFE_TIME=GetNumber(scanner);
		ROBOT_LEANGHT=GetNumber(scanner);
		INFINITY=GetNumber(scanner);
		MIN_MSG_RANGE=GetNumber(scanner);
		MAX_MSG_RANGE=GetNumber(scanner);
		int temp=GetNumber(scanner);
		NO_MSG=null;
		MSG_LIFE_TIME=GetNumber(scanner);
		MSG_MAX_VERSION=GetNumber(scanner);
		MSG_WAIT_TIME=GetNumber(scanner);
		BATTERY_ABOUT_TO_END=GetNumber(scanner);
		ACTIVE_MATDISTANCE=GetNumber(scanner);
		

	}

	@Override
	public String toString() {
		return "StaticParameters [BlackArea=" + BlackArea + ", GrayArea=" + GrayArea + ", BATTERY_CAPACITY="
				+ BATTERY_CAPACITY + ", BATTERY_COST_WALK=" + BATTERY_COST_WALK + ", BATTERY_COST_SEND_MSG="
				+ BATTERY_COST_SEND_MSG + ", BATTERY_COST_GET_MSG=" + BATTERY_COST_GET_MSG
				+ ", BATTERY_CHARGE_LITHT_SPEED=" + BATTERY_CHARGE_LITHT_SPEED + ", TRANSMISSION_RANGE="
				+ TRANSMISSION_RANGE + ", ROBOTS_NOT_MOVE=" + ROBOTS_NOT_MOVE + ", ROBOTS_MOVE=" + ROBOTS_MOVE
				+ ", ARENA_X=" + ARENA_X + ", ARENA_Y=" + ARENA_Y + ", LOG_FILE_DIRECTORY=" + LOG_FILE_DIRECTORY
				+ ", WHITE=" + WHITE + ", GRAY=" + GRAY + ", BLACK=" + BLACK + ", OLDER=" + OLDER + ", SAME_AGE="
				+ SAME_AGE + ", YOUNGER=" + YOUNGER + ", UP=" + UP + ", LEFT=" + LEFT + ", DOWN=" + DOWN + ", RIGHT="
				+ RIGHT + ", INSTANT_SENDING_CHANCE=" + INSTANT_SENDING_CHANCE + ", SENDING_KNOWN_DEVIATION="
				+ SENDING_KNOWN_DEVIATION + ", MAX_NUM_OF_VERSIONS=" + MAX_NUM_OF_VERSIONS + ", MESSAGE_LIFE_TIME="
				+ MESSAGE_LIFE_TIME + ", ROBOT_LEANGHT=" + ROBOT_LEANGHT + ", INFINITY=" + INFINITY + ", MIN_MSG_RANGE="
				+ MIN_MSG_RANGE + ", MAX_MSG_RANGE=" + MAX_MSG_RANGE + ", NO_MSG=" + NO_MSG + ", MSG_LIFE_TIME="
				+ MSG_LIFE_TIME + ", MSG_MAX_VERSION=" + MSG_MAX_VERSION + ", MSG_WAIT_TIME=" + MSG_WAIT_TIME
				+ ", BATTERY_ABOUT_TO_END=" + BATTERY_ABOUT_TO_END + ", ROBOT_STATIC_CHANCE_SEND_MSG=" + ROBOT_STATIC_CHANCE_SEND_MSG
				+ ", ROBOT_CANMOVE_CHANCE_SEND_MSG=" + ROBOT_CANMOVE_CHANCE_SEND_MSG + ", ROBOT_CANMOVE_CHANCE_GET_MSG="
				+ ROBOT_CANMOVE_CHANCE_GET_MSG + ", ROBOTS_NOT_MOVE_COLOR=" + ROBOTS_NOT_MOVE_COLOR
				+ ", ROBOTS_MOVE_COLOR=" + ROBOTS_MOVE_COLOR + ", ACTIVE_MATDISTANCE=" + ACTIVE_MATDISTANCE + "]";
	}

	private int GetNumber(Scanner scanner){
		int num=-1;
		String currentLine=scanner.nextLine();
		String words[] = currentLine.split(" ");
		for(String str : words) {
			try{
				num = Integer.parseInt(str);

			}
			catch(NumberFormatException nfe) { };

		}
		return num;
	}

	private float GetNumberFloat(Scanner scanner){
		float num=-1;
		String currentLine=scanner.nextLine();
		String words[] = currentLine.split(" ");
		for(String str : words) {
			try{
				num = Float.parseFloat(str);

			}
			catch(NumberFormatException nfe) { };

		}
		return num;

	}

	private Vector<int[]> GetCord(Scanner scanner){
		Vector<int[]> answer=new Vector<int[]>();
		int arr[]=new int[4];
		String currentLine=scanner.nextLine();
		String words[] = currentLine.split("]");
		for(String str : words) {
			try{
				arr=new int[4];
				int Index=str.indexOf("[");
				str=str.substring(Index+1);
				String numbers[]=str.split(",");
				int i=0;
				int num = 0;
				boolean b=true;
				for(String s : numbers){
					b=true;
					try{
						num = Integer.parseInt(s);

					}
					catch(NumberFormatException nfe) {
						b=false;
					};
					if(b){
						arr[i]=num;
						i++;
					}
				}
				answer.add(arr);

			}

			catch(NumberFormatException nfe) { };

		}
		return answer;


	}
}
