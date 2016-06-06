import java.util.Vector;

public class Robot{

	static Arena static_arena;
	static Air static_air ;
	int ID;
	boolean Can_Move;
	float BateryStatus;
	Point Private_Location;
	Point Estimated_Location;
	int Estimated_Location_Deviation;
	Point Real_Location;
	Vector<Integer> MessageLog;
	Vector<Point> Private_Location_Log; //Holds all the robot movements as list of points
	Vector<Point> Neighbors_Location;   //Can save all his neighbors location
	int Time = -1;                 //Robots always knows the time.
	Message Currently_Sending;
	Message Currently_Get_Message;
	int Action_Time;    //When to do my next action(next sending).
	int Current_Zone;
	public Robot(int ID){
		this.ID=ID;
		this.Can_Move=true;
		this.BateryStatus=StaticParameters.BATTERY_CAPACITY();
		this.Private_Location=new Point(0,0);
		this.Estimated_Location=new Point(StaticParameters.ARENA_X()/2,StaticParameters.ARENA_Y());
		this.Estimated_Location_Deviation=StaticParameters.ARENA_X()+StaticParameters.ARENA_Y();
		this.Real_Location=new Point(0,0);
		this.MessageLog=new Vector<Integer>();
		this.Private_Location_Log=new Vector<Point>();
		this.Neighbors_Location=new Vector<Point>(StaticParameters.ROBOTS_MOVE()+StaticParameters.ROBOTS_NOT_MOVE);
		this.Currently_Sending=StaticParameters.NO_MSG();
		this.Currently_Get_Message=StaticParameters.NO_MSG();
		this.Action_Time=StaticParameters.INFINITY();
		this.Current_Zone=-1;
	}

	public Robot(Robot b){
		this.ID=b.ID;
		this.Can_Move=b.Can_Move;
		this.BateryStatus=b.BateryStatus;
		this.Private_Location=b.Private_Location;
		this.Estimated_Location=b.Estimated_Location;
		this.Estimated_Location_Deviation=b.Estimated_Location_Deviation;
		this.Real_Location=b.Real_Location;
		this.MessageLog=b.MessageLog;
		this.Private_Location_Log=b.Private_Location_Log; //Holds all the robot movements as list of points
		this.Neighbors_Location=b.Neighbors_Location;   //Can save all his neighbors location
		this.Time =b.Time;                 //Robots always knows the time.
		this.Currently_Sending=b.Currently_Sending;
		this.Currently_Get_Message=b.Currently_Get_Message;
		this.Action_Time=b.Action_Time;    //When to do my next action(next sending).
		this.Current_Zone=b.Current_Zone;
	}

	@SuppressWarnings("static-access")
	public void Action(){
		if(this.Current_Zone==StaticParameters.WHITE()){
			this.BateryStatus=this.BateryStatus+StaticParameters.BATTERY_CHARGE_LITHT_SPEED();
			if(this.BateryStatus>StaticParameters.BATTERY_CAPACITY())this.BateryStatus=StaticParameters.BATTERY_CAPACITY();
		}

		if(this.Action_Time!=StaticParameters.INFINITY()){
			if(this.Action_Time==this.Time){
				this.forwardMessage();
			}
		}
		if(this.Can_Move==false){
			if (StaticParameters.BATTERY_ABOUT_TO_END() * StaticParameters.BATTERY_CAPACITY() > this.BateryStatus){  // The battery is about to run out
				Log.addLine("Robot " + Integer.toString(this.ID) + " The battery is about to run out (" +this.BateryStatus + ") ---> The robot should rest");
				return;
			}
			this.BateryStatus = StaticParameters.BATTERY_CAPACITY();
			int x =(int) (Math.random()*10);
			if(x < StaticParameters.ROBOT_STATIC_CHANCE_SEND_MSG()*10)this.sendNewMessage(); // send new Message.

			else
				this.getMessage();
		}

		// The battery is about to run out
		if(StaticParameters.BATTERY_ABOUT_TO_END()*StaticParameters.BATTERY_CAPACITY() > this.BateryStatus){

			if (this.Current_Zone == StaticParameters.WHITE())
				Log.addLine("Robot " + Integer.toString(this.ID) + " The battery is about to run out (" + Float.toString(this.BateryStatus) + ") ---> The robot should rest");
			for (int i=0 ;i< this.Private_Location_Log.size();i++){
				int i1 = this.Private_Location_Log.size() - i-1;
				if(this.Private_Location_Log.elementAt(i1).zone == StaticParameters.WHITE()){
					int direction = this.Private_Location.dir(this.Private_Location_Log.elementAt(i1));
					if(direction == StaticParameters.INFINITY())continue;
					if(direction !=StaticParameters.INFINITY() && this.try_move_to_constant_direction(direction)== true)
						Log.addLine("Robot " + Integer.toString(this.ID) + " The battery is about to run out ---> The robot go to light on " + Integer.toString(direction) + "direction - The robot knows the point");
				}
			}

			if(this.Estimated_Location.deviation<StaticParameters.BATTERY_CAPACITY()){
				for (int i=0; i<this.Neighbors_Location.size() ;i++){
					if(this.Neighbors_Location.elementAt(i).deviation >StaticParameters.BATTERY_CAPACITY())continue;
					if((int)(Point.AirDisPoints(this.Estimated_Location, this.Neighbors_Location.elementAt(i)))>StaticParameters.BATTERY_CAPACITY())continue;
					if(this.Neighbors_Location.elementAt(i).zone == StaticParameters.WHITE()){
						int direction =this.Estimated_Location.dir(this.Neighbors_Location.elementAt(i));
						if (direction == StaticParameters.INFINITY()) continue;
						if(direction !=StaticParameters.INFINITY() && this.try_move_to_constant_direction(direction)== true){
							Log.addLine("Robot " + Integer.toString(this.ID) + " The battery is about to run out ---> the robot goes towards the robot_" + i + " (to the " + Integer.toString(direction) + ") there is light!");
							return;
						}
					}
				}
			}


			int x=(int) (((Math.random()*10)+1));
			if(x!=1)
				Log.addLine("Robot " + Integer.toString(this.ID) + " The battery is about to run out (" + Float.toString(this.BateryStatus) + ") ---> The robot rest");
			else
				Log.addLine("Robot " + Integer.toString(this.ID) + " The battery is about to run out (" + Float.toString(this.BateryStatus) + ") ---> The robot has decided to continue as usual");
		}
		int x = (int) (((Math.random()*100)+1));
		if (x < StaticParameters.ROBOT_CANMOVE_CHANCE_SEND_MSG() * 100 && this.BateryStatus > StaticParameters.BATTERY_COST_SEND_MSG()){
			this.sendNewMessage();
			return;
		}

		else if( x < StaticParameters.ROBOT_CANMOVE_CHANCE_GET_MSG()+ StaticParameters.ROBOT_CANMOVE_CHANCE_SEND_MSG() && this.BateryStatus > StaticParameters.BATTERY_COST_GET_MSG()){
			this.getMessage();
			return;
		}
		else if (this.BateryStatus > StaticParameters.BATTERY_COST_WALK()){
			int direction = this.getRandomDirection();
			this.move(direction);
			return;
		}
	}

	public boolean[] getEnv(){
		return Robot.static_arena.getEnv(this.ID);
	}


	@SuppressWarnings("static-access")
	public void move(int direction){
		int x = this.Private_Location.x;
		int y = this.Private_Location.y;
		if (direction == StaticParameters.UP()) y =y -1;
		else if (direction == StaticParameters.LEFT())  x =x -1;
		else if (direction == StaticParameters.DOWN())  y =y+ 1;
		else if (direction == StaticParameters.RIGHT())  x =x+ 1;
		else return;
		this.BateryStatus -=this.BateryStatus- StaticParameters.BATTERY_COST_GET_MSG();
		this.Private_Location= new Point(x,y);
		this.Private_Location_Log.addElement(this.Private_Location);
		this.static_arena.moveRobot(this.ID,direction);
	}


	public boolean try_move_to_constant_direction(int direction){
		int x = this.Private_Location.x;
		int y = this.Private_Location.y;
		boolean possible_direction[] = this.getEnv();

		if (possible_direction[direction] == true){
			if (direction == StaticParameters.UP()) y =y +1;
			else if (direction == StaticParameters.LEFT()) x =x -1;
			else if (direction == StaticParameters.DOWN()) y =y- 1;
			else if (direction == StaticParameters.RIGHT()) x =x+ 1;
			// Update real location:
			Robot.static_arena.moveRobot(this.ID, direction);
		}
		else if (possible_direction[(direction+1)%4] == true){
			if ((direction+1)%4 == StaticParameters.UP())  y =y +1;
			else if ((direction+1)%4 == StaticParameters.LEFT()) x =x -1;
			else if ((direction+1)%4 == StaticParameters.DOWN())  y =y- 1;
			else if ((direction+1)%4 == StaticParameters.RIGHT())x =x+ 1;
			// Update real location:
			Robot.static_arena.moveRobot(this.ID, (direction+1)%4);
		}
		else if (possible_direction[(direction-1)%4] == true){
			if ((direction-1)%4 == StaticParameters.UP())  y =y +1;
			else if ((direction-1)%4 == StaticParameters.LEFT()) x =x -1;
			else if ((direction-1)%4 == StaticParameters.DOWN())  y =y- 1;
			else if ((direction-1)%4 == StaticParameters.RIGHT())x =x+ 1;
			// Update real location:
			Robot.static_arena.moveRobot(this.ID, (direction-1)%4);
		}
		else return false;
		this.BateryStatus =this.BateryStatus- StaticParameters.BATTERY_COST_GET_MSG();
		//Update _private_location:d
		this.Private_Location =new Point(x,y);
		//Add to robots new _private_location to Log:
		this.Private_Location_Log.addElement(this.Private_Location);
		return true;
	}


	@SuppressWarnings("static-access")
	public int getRandomDirection(){
		boolean env[] = this.getEnv();
		int direction =(int)((Math.random()*3)); 
		int count = 0; //Case: Robot cant move in any direction.
		while(env[direction] == false && count < 5){
			direction = (direction+1)%4;
			count=count+ 1;
		}
		if (count>= 5){
			Log.addLine("robot "+Integer.toString(this.ID)+" Cant move in any direction. (surrounded by other robots)");
			return -1;
		}
		else return direction;
	}

	//Creats a new message and sends it if possible:
	@SuppressWarnings("static-access")
	public void sendNewMessage(){
		this.Estimated_Location.zone = this.Current_Zone; // send the true color of zone

		// creating new message:
		Message msg =new Message(this.ID,this.creatMessageId(),this.Time,this.Estimated_Location);
		this.Currently_Sending = msg;
		this.Action_Time = this.msgRandomWaitTime();

		if(this.Time < this.Action_Time)Log.addLine("Robot " + Integer.toString(this.ID) + " create a new message and send it soon");

		// checking with 'Air' that robot can send now:
		Robot temp=this;
		if (static_air.can_Send(temp) == false){
			// robot must send another time:
			Log.addLine("Robot " + Integer.toString(this.ID) + " was trying to send a message now ---> Because of congestion notifications will try next time");

			// Case: 'action time' is now but 'Air' wont let robot send:
			if (this.Time == this.Action_Time){
				this.Action_Time=this.Action_Time+ 1;
				return;
			}
		}

		else{ //Sending message
			Log.addLine("Robot " + Integer.toString(this.ID) + " sent a new message: " + this.Currently_Sending.toString());

			this.BateryStatus -= StaticParameters.BATTERY_COST_SEND_MSG();
			this.MessageLog.addElement(msg.ID_Message);
			Robot.static_air.Send(msg,this);
			this.Action_Time = StaticParameters.INFINITY();
			this.Currently_Sending = StaticParameters.NO_MSG();
			return;
		}

	}


	@SuppressWarnings("static-access")
	public void forwardMessage(){
		if(this.Currently_Sending == StaticParameters.NO_MSG()){
			this.Action_Time = StaticParameters.INFINITY();
			return;
		}
		this.Currently_Sending.sender_hist.add(this.ID);
		boolean wassent = Robot.static_air.Send(this.Currently_Sending,this);

		if(!wassent){
			this.Action_Time += 1;
			if(this.Action_Time > StaticParameters.MSG_LIFE_TIME()){
				this.Action_Time = StaticParameters.INFINITY();
				this.Currently_Sending = StaticParameters.NO_MSG();
				Log.addLine("Robot " + Integer.toString(this.ID) + ": Is Waiting to forward Message (" + Integer.toString(this.Action_Time) + ") ---> Went too long ---> throw the message!");
				return;
			}
			else{
				Log.addLine("Robot " + Integer.toString(this.ID) + ": Is Waiting to forward Message (" + Integer.toString(this.Action_Time) + ")");
				return;
			}
		}

		else{
			this.BateryStatus=this.BateryStatus- StaticParameters.BATTERY_COST_SEND_MSG();
			Log.addLine("Robot " + Integer.toString(this.ID) + " sent message  " + this.Currently_Sending.toString());
			return;
		}
	}

	@SuppressWarnings("static-access")
	public void getMessage(){
		Robot b=new Robot(this);
		Message msg = static_air.get(b);
		this.BateryStatus=this.BateryStatus- StaticParameters.BATTERY_COST_GET_MSG();
		if(msg == StaticParameters.NO_MSG()){
			Log.addLine("Robot " + Integer.toString(this.ID) + ": Receiving Messages.  --->  Not received!");
			return;
		}
		else{
			if (msg.version >= StaticParameters.MAX_NUM_OF_VERSIONS()){
				msg.version+= 1;
				Log.addLine("Robot " + Integer.toString(this.ID) + ": Receiving Messages.  --->  Received a new message! " + msg.toString());

				//Updating 'neighbors_loc' with info from recieved message:(location of the last message sender)
				Point point1 = new Point(msg.estimated_location.x, msg.estimated_location.y);
				point1.deviation = Point.Sig_to_Dis(msg.Reception);
				point1.zone = msg.estimated_location.zone;				

				if(msg.sender_hist.size()>0) this.Neighbors_Location.add(msg.sender_hist.get(msg.sender_hist.size()-1), point1);
				else this.Neighbors_Location.add(msg.ID_Sender, point1);


				this.Estimated_Location.x =this.Estimated_Location.x-this.Private_Location.x;
				this.Estimated_Location.y-= this.Private_Location.y;
				Point new_point =new Point(msg.estimated_location.x, msg.estimated_location.y);
				new_point.deviation = Point.Sig_to_Dis(msg.Reception);
				this.Estimated_Location.Two_Point(new_point);
				this.Estimated_Location.x += this.Private_Location.x;
				this.Estimated_Location.y += this.Private_Location.y;

				this.Currently_Get_Message = msg;
				this.Action_Time = this.msgRandomWaitTime();
				this.MessageLog.addElement(msg.ID_Message);
			}
		}
	}
	public int creatMessageId(){
		return this.ID + 1000*(this.MessageLog.size()+1);
	}

	public int msgRandomWaitTime(){
		return this.Time + (int)(Math.random()*StaticParameters.MSG_WAIT_TIME());
	}

	public String toString(){
		return "ROBOT[id:"+Integer.toString(this.ID)+" , battery_status:"+Float.toString(this.BateryStatus)+" ,estimated_location:"+Integer.toString(this.Estimated_Location_Deviation)+" ,can_move:"+Boolean.toString(this.Can_Move) +" ,message_Log:"+this.MessageLog.toString()+"]";
	}

	public static void main(String[] args) {
		StaticParameters Parameters=new StaticParameters("D:\\Parameters.txt");
		Log a=new Log();
		Robot.static_air=new Air();
		Robot.static_arena=new Arena();
		System.out.println(static_arena.Robots.get(0).BateryStatus);
		static_arena.Robots.get(0).Action();
		System.out.println(static_arena.Robots.get(0).BateryStatus);
		System.out.println((-1%4));
	}
}
