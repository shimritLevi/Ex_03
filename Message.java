import java.util.ArrayList;

public class Message {

	int ID_Sender;
	int ID_Message;
	ArrayList <Integer> sender_hist;
	Point real_location;
	Point estimated_location;
	int [][]mat_distance;
	double Reception = 0;
	int version = 0;
	int time;

	public Message(int ID_Sender, int ID_Message, int time, Point estimated_loaction){
		this.ID_Sender = ID_Sender;
		this.ID_Message = ID_Message;
		this.sender_hist = new ArrayList<Integer>();
		this.mat_distance = new int[StaticParameters.ARENA_X()][StaticParameters.ARENA_Y()];
		this.time=time;
		this.estimated_location=estimated_location;

	}

	public int equals(Message m){
		if(this.ID_Message != m.ID_Message)return 0;
		else if (this.version > m.version) return 1;
		else return 2;
	}

	@Override
	public String toString() {
		return "Message [ID_Sender=" + ID_Sender + ", ID_Message=" + ID_Message
				+ ", version=" + version + "]";
	}



}


