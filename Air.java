import java.util.ArrayList;

public class Air {
	ArrayList <Message> inbox_mess;
	static int [][]static_mat_zone =new int[StaticParameters.ARENA_X()][StaticParameters.ARENA_Y()];

	public Air(){
		this.inbox_mess = new ArrayList<Message>();
	}

	public boolean can_Send(Robot r){
		Point robot_location = r.Real_Location;
		for (int j = 0; j < inbox_mess.size(); j++) {
			double range = 0;
			if(StaticParameters.ACTIVE_MATDISTANCE() == 1){
				range = this.inbox_mess.get(j).mat_distance[r.Real_Location.x][r.Real_Location.y];
			}
			else{
				range = Point.AirDisPoints(robot_location, this.inbox_mess.get(j).real_location);

			}

			if (range < StaticParameters.MAX_MSG_RANGE() && range!=-1){
				return true;
			}
		}
		return false;
	}
	public boolean Send(Message m, Robot r){
		boolean canSend = this.can_Send(r);
		if (can_Send(r) == true){
			m.real_location = r.Real_Location;
			if(StaticParameters.ACTIVE_MATDISTANCE() ==1){
				Point.fillMatDistance(m.real_location,m.mat_distance, static_mat_zone);
			}
			inbox_mess.add(m);

		}

		return canSend;
	}
	public  Message get(Robot r){
		boolean ans = false;
		int size = inbox_mess.size();
		if(size == 0)return StaticParameters.NO_MSG();
		Message shortdis = this.inbox_mess.get(0);
		Point robot_location = r.Real_Location;
		double range = 0;
		double sum_range=0;
		for (int i = 0; i < size; i++) {
			if(StaticParameters.ACTIVE_MATDISTANCE() == 1){
				range = this.inbox_mess.get(i).mat_distance[robot_location.x][robot_location.y];
			}
			else range = Point.AirDisPoints(robot_location, this.inbox_mess.get(i).real_location);
			if(range == StaticParameters.INFINITY() && range<=0) continue;
			if(range <=StaticParameters.MIN_MSG_RANGE()){
				this.inbox_mess.get(i).Reception = (StaticParameters.MAX_MSG_RANGE()-range)*(StaticParameters.MAX_MSG_RANGE()-range);
				return this.inbox_mess.get(i);
			}
			else if(range >=StaticParameters.MAX_MSG_RANGE())continue;
			else{
				sum_range = sum_range + range;
				Point nearest_mess_loc = shortdis.real_location;
				Point messa_i = this.inbox_mess.get(i).real_location;
                if(ans == false){
                    ans = true;
                    shortdis = this.inbox_mess.get(i);
                    shortdis.Reception = (StaticParameters.MAX_MSG_RANGE() - range) * (StaticParameters.MAX_MSG_RANGE() - range);
                    return shortdis;
                }
                else if(Point.distance(Air.static_mat_zone, robot_location,nearest_mess_loc) > Point.distance(Air.static_mat_zone, robot_location,messa_i)){
                    shortdis = this.inbox_mess.get(i);
                    shortdis.Reception = (StaticParameters.MAX_MSG_RANGE() - range) * (StaticParameters.MAX_MSG_RANGE() - range);
                }
			}
		}
        if(ans) return StaticParameters.NO_MSG();
        if(sum_range==0) return StaticParameters.NO_MSG();
        if(range==0 || sum_range>= StaticParameters.MAX_MSG_RANGE())
            return StaticParameters.NO_MSG();
        else{
            shortdis.Reception = shortdis.Reception - sum_range;
            return shortdis;
        }
		
	}
}
