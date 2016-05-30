
public class Arena {
	 _mone_move =0
			    public in(){
		            int[] this,robot_id = new int[255];
		           	int[] this.Robots = new int [255];
		           	int[]this.Robots_sort_Random = new int [255];
		           	int[] this.zone = new int [255]; //*by color (white = 0    gray = 1     black = 2)}
	 

			        for (int i=0; i< ARENA_X(); i++){
			            this.robot_id.append(ARENA_Y()*(-1));
			            this.zone.append(ARENA_Y()*WHITE());}

			        //* Mark gray areas:
			        Gray_point = GRAY_POINT();
			        for (int b=0 ; b<len(Gray_point); b++){
			            Log.addLine("create gray area point between: "+str(Gray_point[b]));
			            for (int i=Gray_point[b][0]; i< (Gray_point[b][2]+1);i++){
			                for (int j=Gray_point[b][1];j< (Gray_point[b][3]+1);j++){
			                    this.zone[i][j] = GRAY();}}}


			        //* Mark black areas:
			        black_point = BLACK_POINT();
			        for (int b=0; b<len(black_point); b++){
			            Log.addLine("create black area point between: " + str(black_point[b]));
			            for (int i=black_point[b][0];i< (black_point[b][2]+1); i++){
			                for (int j=black_point[b][1];j< (black_point[b][3]+1);j++){
			                    this.zone[i][j] = BLACK();}}}

			        //* Creates a new robot 'that can move' to variable "Robots"
			        for (int s=0;s<ROBOTS_MOVE();s++){
			            this.Robots.append(Robot(s));
			            self.Robots_sort_Random.append(s);
			            Log.addLine("create new Robot- " + this.Robots[s].toString());}

			        //* Creates a new robot 'that can't move' to variable "Robots"
			        for (int s=ROBOTS_MOVE();s <ROBOTS_MOVE()+ROBOTS_NOT_MOVE();s++){
			            this.Robots.append(Robot(s));
			            this.Robots_sort_Random.append(s);
			            this.Robots[s]._can_move = False;}

			            Log.addLine("create new Robot- " + self._Robots[s].toString());

			        //* put the robots on Arena:
			        for (int s = 0; s<  len(self.Robots);s++){
			            x = (int)(Math.random()*randARENA_X()) - 2;
			            y = (int)(Math.random()*randARENA_Y()) - 2;
			            Exact1 = this.robot_id[x][y]!=-1;
			            Exact2 = this.zone[x][y] == BLACK();
			            Exact3 = this.Robots[s]._can_move == False;
			            Exact4 = this.zone[x][y] != WHITE();
			            while((Exact1 |Exact2 ) | (Exact3 & Exact4)){
			                x = (int)(Math.random()*randARENA_X()) - 2;
			                y = (int)(Math.random()*randARENA_Y()) - 2;
			                Exact1 = this.robot_id[x][y] != -1;
			                Exact2 = this.zone[x][y] == BLACK();
			                Exact3 = this.Robots[s]._can_move == False;
			                Exact4 = this.zone[x][y] != WHITE();}

			            this.Robots.append(Robot(s));
			            this.robot_id[x][y] = this._Robots[s]._id;
			            this.Robots[s]._real_location = Point(x, y);
			            //*self._Robots_sort_Random[s]._real_location = Point(x, y)
			            if(Exact3){ this.Robots[s]._can_move == False;
			                this.Robots[s]._estimated_location = Point(x, y);
			                this.Robots[s]._estimated_location._deviation = 0;}
			            Log.addLine("put Robot_" + str(this.Robots[s]._id) + this.Robots[s]._real_location.toString());

			        this.sortRandomRobotsArray();

			    //*Returns the robot can he move forward(UP, DOWN, LEFT,RIGHT)
			    //*Assumes that that the location of the robot's true, namely:  x>0, y>o, x<ARENA_X(), y<ARENA_Y()-1"""
			    public boolean[] movement(int id){
			        x = this.Robots[id]._real_location._x;
			        y = this.Robots[id]._real_location._y;
			        boolean[] array ={True,True,True,True};

			        if (Point.existsXY(x, y-1) == False){
			            array[UP()] = False;}
			        else{if (this.robot_id[x][y-1]!=-1 | this.zone[x][y-1]){
			            array[UP()]=False;}}

			        if (Point.existsXY(x, y+1) == False){
			            array[DOWN()] = False;}
			        else {if (this.robot_id[x][y+1]!=-1 | this.zone[x][y+1]){
			            array[DOWN()] =False;}}

			        if (Point.existsXY(x - 1, y) == False){
			            array[LEFT()] = False;}
			        else{if (this.robot_id[x-1][y]!=-1 | this.zone[x-1][y]){
			            array[LEFT()] =False;}}

			        if(Point.existsXY(x+1, y) == False){
			            array[RIGHT()] = False;}
			        else {if((this.robot_id[x+1][y]!=-1 | this.zone[x+1][y]){
			            array[RIGHT()] =False;}}

			        return array;

			    //*Returns the point where the robot is currently
			    public point getCurrentZone(self,id){
			        x = this.Robots[id]._real_location._x;
			        y = this.Robots[id]._real_location._y;

			        return this.zone[x][y];}

			    public boolean moveRobot(int id,int direction){
			        array = this.movement(id);
			        if(array[direction]==False)
			            return False;
			        else{
			            Arena._mone_move +=1;
			            x = this.Robots[id]._real_location._x;
			            y = this.Robots[id]._real_location._y;
			            this.robot_id[x][y]=-1;}

			            tolog = "";
			            if(direction == UP()){
			                tolog = ("Robot " + str(id) + ": move UP From [" + str(x) + "][" + str(y) + "] to [" + str(x) + "][" + str(y-1) + "]");
			                y = y - 1;}
			            else {if(direction == DOWN()){
			                tolog =("Robot " + str(id) + ": move DOWN From [" + str(x) + "][" + str(y) + "] to [" + str(x) + "][" + str(y+1) + "]");
			                y = y + 1;}
			            else {if (direction == LEFT()){
			                tolog =("Robot " + str(id) + ": move LEFT From [" + str(x) + "][" + str(y) + "] to [" + str(x-1) + "][" + str(y) + "]");
			                x = x - 1;}
			            else{
			                tolog =("Robot " + str(id) + ": move RIGHT From [" + str(x) + "][" + str(y) + "] to [" + str(x+1) + "][" + str(y) + "]");
			                x = x + 1;}

			            Log.addLine(tolog);
			            print(tolog);
			            this.robot_id[x][y] = id;
			            this.Robots[id]._real_location = Point(x,y);

			            return True;

			    public void sortRandomRobotsArray(){
			        for (int i=0;i<(len(this.Robots_sort_Random));i++){
			            sw = (int)(Math.random()*len(this.Robots_sort_Random)-1);
			            temp = this.Robots_sort_Random[sw];
			            this.Robots_sort_Random[sw] = this.Robots_sort_Random[i];
			            this.Robots_sort_Random[i] = temp;}}


}
