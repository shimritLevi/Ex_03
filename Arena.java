import java.util.Arrays;
import java.util.Vector;

public class Arena {
	    static int Move =0;
	    int Mat_Robot_ID[][];
	    int Mat_Zone[][];
	    Vector<Robot> Robots=new Vector<Robot>();
	    Vector<Integer> RandomRobots=new Vector<Integer>();
	    
	    
	    public Arena(){
	        this.Mat_Robot_ID = new int[StaticParameters.ARENA_X()][StaticParameters.ARENA_Y()];
	       // this.Robots = []
	       // this.Robots_sort_Random = []
	        this.Mat_Zone = new int[StaticParameters.ARENA_X()][StaticParameters.ARENA_Y()];
	        
	        
	        for (int i = 0; i < Mat_Robot_ID[0].length; i++) {
				for (int j = 0; j < Mat_Robot_ID.length; j++) {
					this.Mat_Robot_ID[i][j]=-1;
				}
			}
	        Vector<int[]> GrayAreaPoint = StaticParameters.GrayArea();
	        for (int t=0; t<StaticParameters.GrayArea().size();t++){
	            Log.addLine("create gray area point between: "+Arrays.toString(GrayAreaPoint.get(t)));
	            for (int i=GrayAreaPoint.get(t)[0]; i< GrayAreaPoint.get(t)[2]+1; i++){
	                for (int j=GrayAreaPoint.get(t)[1]; j<GrayAreaPoint.get(t)[3]+1;j++){
	                    this.Mat_Zone[i][j] = StaticParameters.GRAY();
	                }
	            }
	        }


	        Vector<int[]> Black_Area = StaticParameters.BlackArea();
	        for (int t=0; t< Black_Area.size(); t++){
	            Log.addLine("create black area point between: " + Arrays.toString(Black_Area.get(t)));
	            for (int i=Black_Area.get(t)[0];i< Black_Area.get(t)[2]+1; i++){
	                for (int j=Black_Area.get(t)[1]; j<Black_Area.get(t)[3]+1; j++){
	                    this.Mat_Zone[i][j] = StaticParameters.BLACK();
	                }
	            }
	        }

	        for (int s=0;s< StaticParameters.ROBOTS_MOVE();s++){
	            this.Robots.add(new Robot(s));
	            this.RandomRobots.add(s);
	            Log.addLine("create new Robot- " + this.Robots.get(s).toString());
	        }
	            

	        for (int s=StaticParameters.ROBOTS_MOVE();s< StaticParameters.ROBOTS_MOVE()+StaticParameters.ROBOTS_NOT_MOVE();s++){
	            this.Robots.add(new Robot(s));
	            this.RandomRobots.add(s);
	            this.Robots.get(s).Can_Move = false;
	            Log.addLine("create new Robot- " + this.Robots.get(s).toString());
	        }

	        for (int s=0; s<this.Robots.size(); s++){
	            int x =(int)((Math.random()*(StaticParameters.ARENA_X() - 2)+1));
	            int y =(int)((Math.random()*(StaticParameters.ARENA_Y() - 2)+1));
	            boolean bool1 = this.Mat_Robot_ID[x][y]!=-1;
	            boolean bool2 = this.Mat_Zone[x][y] == StaticParameters.BLACK();
	            boolean bool3 = this.Robots.get(s).Can_Move == false;
	            boolean bool4 = this.Mat_Zone[x][y] != StaticParameters.WHITE();
	            while((bool1 ||bool2 ) || (bool3 && bool4)){
		            x =(int)((Math.random()*(StaticParameters.ARENA_X() - 2)+1));
		            y =(int)((Math.random()*(StaticParameters.ARENA_Y() - 2)+1));
	                bool1 = this.Mat_Robot_ID[x][y] != -1;
	                bool2 = this.Mat_Zone[x][y] == StaticParameters.BLACK();
	                bool3 = this.Robots.get(s).Can_Move == false;
	                bool4 = this.Mat_Zone[x][y] != StaticParameters.WHITE();
	            }

	            this.Mat_Robot_ID[x][y] = this.Robots.get(s).ID;
	            this.Robots.get(s).Real_Location =new  Point(x, y);
	            if(bool3){
	                this.Robots.get(s).Estimated_Location = new Point(x, y);
	                this.Robots.get(s).Estimated_Location.deviation = 0;
	            }
	            Log.addLine("put Robot_" + Integer.toString(this.Robots.get(s).ID) + this.Robots.get(s).Real_Location.toString());
	        }

	        this.sortRandomRobotsArray();
	    }
	    
	    
	    public boolean[] getEnv(int ID){
	        int x = this.Robots.get(ID).Real_Location.x;
	        int y = this.Robots.get(ID).Real_Location.y;
	        boolean []array = {true,true,true,true};

	        if (Point.placeR(x, y-1) == false || y-1 ==0)array[StaticParameters.DOWN()] = false;
	        else if(this.Mat_Robot_ID[x][y-1]!=-1 || this.Mat_Zone[x][y-1]==0)array[StaticParameters.DOWN()] =false;

	        if (Point.placeR(x, y+1) == false || y+1 != StaticParameters.ARENA_Y()-1)array[StaticParameters.UP()] = false;
	        else if(this.Mat_Robot_ID[x][y+1]!=-1 || this.Mat_Zone[x][y+1]==0)array[StaticParameters.UP()] =false;

	        if (Point.placeR(x - 1, y) == false || x-1 ==0)array[StaticParameters.LEFT()] = false;
	        else if(this.Mat_Robot_ID[x-1][y]!=-1 || this.Mat_Zone[x-1][y]==0)array[StaticParameters.LEFT()] =false;

	        if(Point.placeR(x+1, y) == false || x+1 != StaticParameters.ARENA_X()-1)array[StaticParameters.RIGHT()] = false;
	        else if(this.Mat_Robot_ID[x+1][y]!=-1 || this.Mat_Zone[x+1][y]==0)array[StaticParameters.RIGHT()] =false;

	        return array;
	    }

	    public int getCurrentZone(int ID){
	        int x = this.Robots.get(ID).Real_Location.x;
	        int y = this.Robots.get(ID).Real_Location.y;

	        return this.Mat_Zone[x][y];
	    }

	    public boolean moveRobot(int ID,int  direction){
	        boolean []array = this.getEnv(ID);
	        if(array[direction]==false)return false;
	        else{
	            Arena.Move +=1;
	            int x = this.Robots.get(ID).Real_Location.x;
	            int y = this.Robots.get(ID).Real_Location.y;
	            this.Mat_Robot_ID[x][y]=-1;

	            String tolog = "";
	            if(direction == StaticParameters.UP()){
	                tolog = ("Robot " + Integer.toString(ID) + ": move UP From [" + Integer.toString(x) + "][" + Integer.toString(y) + "] to [" + Integer.toString(x) + "][" + Integer.toString(y-1) + "]");
	                y = y - 1;
	            }
	            else if(direction == StaticParameters.DOWN()){
	                tolog =("Robot " + Integer.toString(ID) + ": move DOWN From [" + Integer.toString(x) + "][" + Integer.toString(y) + "] to [" + Integer.toString(x) + "][" + Integer.toString(y+1) + "]");
	                y = y + 1;
	            }
	            else if (direction == StaticParameters.LEFT()){
	                tolog =("Robot " + Integer.toString(ID) + ": move LEFT From [" + Integer.toString(x) + "][" + Integer.toString(y) + "] to [" + Integer.toString(x-1) + "][" + Integer.toString(y) + "]");
	                x = x - 1;
	            }
	            else{
	                tolog =("Robot " + Integer.toString(ID) + ": move RIGHT From [" + Integer.toString(x) + "][" + Integer.toString(y) + "] to [" + Integer.toString(x+1) + "][" + Integer.toString(y) + "]");
	                x = x + 1;
	            }

	            Log.addLine(tolog);
	            this.Mat_Robot_ID[x][y] = ID;
	            this.Robots.get(ID).Real_Location = new Point(x,y);

	            return true;
	        }
	    }

	    public void sortRandomRobotsArray(){
	        for (int i=0; i< this.RandomRobots.size();i++){
	        	int sw=(int)(Math.random()*(this.RandomRobots.size())-1);
	            int temp = this.RandomRobots.get(sw);
	            this.RandomRobots.set(sw,this.RandomRobots.get(i));
	            this.RandomRobots.set(i, temp);
	        }
	    }
}

