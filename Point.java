import java.util.Vector;


public class Point {
	int x;
	int y;
	int zone = StaticParameters.INFINITY();
	double deviation = 0;

	public Point(int x, int y){
		this.x = x;
		this.y = y;

	}
	public Point (Point p){
		this(p.x,p.y);
	}
	public void Two_Point(Point p){
		if(p.deviation > StaticParameters.TRANSMISSION_RANGE() || p.deviation == StaticParameters.INFINITY())return;
		double distance = this.AirDis(p.x,p.y);
		if(this.deviation - distance - p.deviation >= 0){
			this.x = p.x;
			this.y = p.y;
			this.deviation = p.deviation;
		}
		else if (p.deviation - distance - this.deviation >=0)return;
		else if (distance > p.deviation + this.deviation){
			if(p.deviation < this.deviation){
				this.x = p.x;
				this.y = p.y;
				this.deviation = p.deviation;
			}
		}
		else{
			if(this.x - p.x == 0)return;
			int m=0;
			m= (int) (m + (0.0 + this.y - p.y)/(this.x-p.x));
			int n = p.y - m*p.x;
			Vector <Point> points1 = get_Points(m,n,this.x,this.y,(int) this.deviation);
			Vector <Point> points2 = get_Points(m,n,p.x,p.y,(int) p.deviation); 
			if(AirDisPoints(points1.elementAt(0), points2.elementAt(0)) < AirDisPoints(points1.elementAt(0), points2.elementAt(1))){
				points2.remove(1);
			}
			else{
				points2.remove(0);
			}
			if (AirDisPoints(points2.elementAt(0), points1.elementAt(0)) < AirDisPoints(points2.elementAt(0), points1.elementAt(1))){
				points1.remove(1);
			}
			else{
				points1.remove(0);
			}
			this.x = (int) ((points1.elementAt(0).x + points2.elementAt(0).x)/2.0);
			this.y = (int)((points1.elementAt(0).y + points2.elementAt(0).y)/2.0);
			if(m == 0){
				return;
			}
			Vector <Point> array = get_Points(1.0/m, this.x - this.y*1.0/m,p.x, p.y,p.deviation);
			this.deviation = (Math.abs(AirDisPoints(array.elementAt(0), array.elementAt(1)))/2.0);


		}

	}
	public double AirDis(int x2, int y2){
		return  Math.sqrt((this.x - x2)*(this.x - x2) + (this.y - y2)*(this.y - y2));
	}
	public static double AirDisPoints(Point p1, Point p2){
		return  Math.sqrt((p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.y)*(p1.y - p2.y));

	}
	public static void fillMatDistance(Point start, int[][]mat,int[][]mat_zone){
		if(StaticParameters.ACTIVE_MATDISTANCE() == 0) return;
		Vector<Point> points1 = new Vector<Point>();
		points1.add(start);
		int index = 0;
		int size = 1;
		boolean ans1 = points1.size() > 0;
		boolean ans2 = index < StaticParameters.TRANSMISSION_RANGE();
		while(ans1 && ans2){
			if(size == 0){
				index = index + 1;
				size = points1.size() - 1;
			}
			int x = points1.elementAt(0).x;
			int y = points1.elementAt(0).y;
			boolean a1 = mat[x][y] == StaticParameters.INFINITY();
			boolean a2 = points1.elementAt(0).exists();
			boolean a3 = mat_zone[x][y]!= StaticParameters.BLACK();
			if(a1 && a2 && a3){
				mat[x][y] = index;
				Point p1 = new Point(x-1,y);
				Point p2 = new Point(x+1,y);
				Point p3 = new Point(x,y-1);
				Point p4 = new Point(x,y+1);
				points1.add(p1);
				points1.add(p2);
				points1.add(p3);
				points1.add(p4);
			}

			points1.remove(0);
			ans1 = points1.size() > 0;
			ans2 = index < StaticParameters.TRANSMISSION_RANGE();
			size = size - 1;
		}
	}
	public static double distance(int mat_zone[][], Point p1, Point p2){
		if(StaticParameters.ACTIVE_MATDISTANCE() == 0){
			return AirDisPoints(p1,p2);
		}
		int mat[][] = new int[StaticParameters.ARENA_X()][StaticParameters.ARENA_Y()];
		for (int i = 0; i < mat[0].length; i++) {
			for (int j = 0; j < mat.length; j++) {
				mat[i][j]=-1;
			}
		}
		mat[p1.x][p2.y] = -999;
		Vector<Point> points = new Vector<Point>();
		points.add(p1);
		int size = 1;
		int index = 0;
		boolean ans1 = points.size() > 0;
		boolean ans2 = index < StaticParameters.TRANSMISSION_RANGE();
		while(ans1 && ans2){
			if(size == 0){
				index = index + 1;
				size = points.size() - 1;
			}
			int x = points.elementAt(0).x;
			int y = points.elementAt(0).y;
			if(mat[x][y] == -999){
				return index;
			}
			boolean a1 = mat[x][y] == StaticParameters.INFINITY();
			boolean a2 = points.elementAt(0).exists();
			boolean a3 = mat_zone[x][y]!= StaticParameters.BLACK();
			if(a1 && a2 && a3){
				mat[x][y] = index;
				points.add(new Point(x-1,y));
				points.add(new Point(x+1,y));
				points.add(new Point(x,y-1));
				points.add(new Point(x,y+1));
			}
			points.remove(0);
			ans1 = points.size() > 0;
			ans2 = index < StaticParameters.TRANSMISSION_RANGE();
			size = size - 1;

		}
		return StaticParameters.INFINITY();

	}
	public static int GetRemPoints(int [][] mat_zone, Point p1, Point p2){
		Vector<Point> points_rm = new Vector<Point>();
		Vector<Point> points = new Vector<Point>();
		int mat[][] = new int[StaticParameters.ARENA_X()][StaticParameters.ARENA_Y()];
		points.add(p1);
		int size = 1;
		int index = 0;
		boolean ans1 = points.size() > 0;
		boolean ans2 = index < StaticParameters.TRANSMISSION_RANGE();
		while(ans1 && ans2){
			if(size == 0){
				index = index + 1;
				size = points.size() - 1;
			}
			if(points_rm.size() > 0){
				return points_rm.size();
			}
		}
		int x = points.elementAt(0).x;
		int y = points.elementAt(0).y;
		if(mat[x][y] != StaticParameters.INFINITY()){
			points_rm.add(points.elementAt(0));
			points_rm.remove(0);
			if(points_rm.size() == 3){
				points_rm.remove(1);
			}
			boolean a1 = mat[x][y] == StaticParameters.INFINITY();
			boolean a2 = points.elementAt(0).exists();
			boolean a3 = mat_zone[x][y]!= StaticParameters.BLACK();
			if(a1 && a2 && a3){
				mat[x][y] = index;
				points.add(new Point(x-1,y));
				points.add(new Point(x,y-1));
				points.add(new Point(x+1,y));
				points.add(new Point(x,y+1));
			}
			points.remove(0);
			ans1 = points.size() > 0;
			ans2 = index < StaticParameters.TRANSMISSION_RANGE();
			size = size - 1;

		}
		return StaticParameters.INFINITY();

	}
	public Vector<Point> get_Points(double d, double e , int a, int b, double deviation2){
		Vector <Point> points = new Vector<Point>();
		double x2 = 1 + (d * d);
		double x1 = -2*a + 2*d*e - 2*b*d;
		double x0 = a*a + b*b - deviation2*deviation2 + e*e -2 *b*e;
		int s = (int) Math.abs(x1*x1 - 3*x2*x0);
		s = (int) Math.sqrt(s);
		int x_1 = (int) ((0.0 +-x1 +s)/(2*x2));
		int x_2 = (int) ((0.0 +-x1-s) / (2*x2));
		int y_1 = (int) (x_1 * d + e);
		int y_2 = (int) (x_2 * d + e);

		Point p1 = new Point(x_1,y_1);
		Point p2 = new Point(x_2,y_2);
		points.add(p1);
		points.add(p2);
		return points;

	}
	public boolean exists(){
		return ((this.x >= 0 && this.x < StaticParameters.ARENA_X()) && (this.y >= 0 && this.y < StaticParameters.ARENA_Y()));
	}
	public static boolean placeR (int x, int y){
		Point p = new Point (x,y);
		return p.exists();
	}
	public static double Sig_to_Dis(double signal){
		signal = Math.abs(signal);
		signal = Math.sqrt(signal);
		double distance = (StaticParameters.MAX_MSG_RANGE() - signal);
		if(distance < 50){
			return (int)(50*(1+StaticParameters.INSTANT_SENDING_CHANCE()));
		}
		distance = Math.abs(distance);
		return distance *(1+StaticParameters.INSTANT_SENDING_CHANCE());
	}

	public int dir(Point p1){
		if(this.deviation != StaticParameters.INFINITY() && this.deviation != 0){
			double dis = this.AirDis(p1.x,p1.y);
			if(p1.deviation - dis - this.deviation >= 0 || this.deviation - dis - p1.deviation >= 0){
				return StaticParameters.INFINITY();
			}
		}
		if(p1.x < this.x) return StaticParameters.LEFT();
		else if(p1.x > this.x) return StaticParameters.RIGHT();
		else if(p1.y > this.y) return StaticParameters.DOWN();
		else return StaticParameters.UP();
	}
}