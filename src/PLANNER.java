
public class PLANNER {
	
	private GUI Gui;
	private MAP Map;
	public int LasPosX;
	public int LasPosY;
	
	public MAP DefaultMap() {
		int Widt = 1200;
		int Hegt = 700;
		MATERIAL mat = new MATERIAL("GRASS");
		
		MAP map=new MAP(Widt, Hegt, mat);
		
		for (int i=0;i<Widt;i++) {
			for (int j=0;j<Hegt;j++) {
				int kpx1 = 300;
				int kpy1 = 400;
				int kpx2 = 900;
				int kpy2 = 400;
				
				if (Math.sqrt((i-kpx1)*(i-kpx1)+(j-kpy1)*(j-kpy1)) > 100 && Math.sqrt((i-kpx1)*(i-kpx1)+(j-kpy1)*(j-kpy1)) < 200 && i < kpx1){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					map.TrackDraw[i][j] = mat2;
				}
				else if (Math.sqrt((i-kpx2)*(i-kpx2)+(j-kpy2)*(j-kpy2)) > 200 && Math.sqrt((i-kpx2)*(i-kpx2)+(j-kpy2)*(j-kpy2)) < 200 && i > kpx2){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					map.TrackDraw[i][j] = mat2;
				}
				else if (Math.abs(j-kpy1) >= 100 && Math.abs(j-kpy1) <= 200  && i >= kpx1 && i <= kpx2){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					map.TrackDraw[i][j] = mat2;
				}
				else if (Math.sqrt((i-kpx2)*(i-kpx2)+(j-kpy2)*(j-kpy2)) > 100 && Math.sqrt((i-kpx2)*(i-kpx2)+(j-kpy2)*(j-kpy2)) < 200 && i >= kpx2){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					map.TrackDraw[i][j] = mat2;
				}
				else if (Math.sqrt((i-50)*(i-50)+(j-50)*(j-50)) < 20){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					map.TrackDraw[i][j] = mat2;
				}
		
			}
		}
		map.Blur();
		
		
		return map;
	}
	
	public MAP CreateMap() {
		int Widt = 1200;
		int Hegt = 700;
		MATERIAL mat = new MATERIAL("GRASS");
		
		MAP map=new MAP(Widt, Hegt, mat);
		this.LasPosY=Hegt/2;
		this.LasPosX=100;
		map.Blur();
		
		
		return map;
	}
	
	
	public MAP CreateStraight(MAP map, int ang, int track_length, int track_width, MATERIAL track_surface) {
		for (int i=0; i<map.GetWidth(); i++) {
			for (int j=0; j<map.GetHeight(); j++) {
				
				//check if the given coordinate is within the ractangle
				
				if((i > this.LasPosX) && (i < this.LasPosX+track_length) && (j > (this.LasPosY-track_width/2)) && (j < (this.LasPosY+track_width/2))) {
					
					//rotating the rectangle
					
					map.TrackDraw[(int)(Math.cos(ang*Math.PI/180)*(i-this.LasPosX)-Math.sin(ang*Math.PI/180)*(j-this.LasPosY)+this.LasPosX)]
							     [(int)(Math.sin(ang*Math.PI/180)*(i-this.LasPosX)+Math.cos(ang*Math.PI/180)*(j-this.LasPosY)+this.LasPosY)]=track_surface;
				}
			}
		}
		this.LasPosX=(int)(this.LasPosX+track_length*Math.cos(ang*Math.PI/180));
		this.LasPosY=(int)(this.LasPosY+track_length*Math.sin(ang*Math.PI/180));
		
		//erosion for missing pixels due to rotation's numerical error caused by implicit conversion
		/*
		for (int i=0; i<map.GetWidth(); i++) {
			for (int j=0; j<map.GetHeight(); j++) {
				if(map.TrackDraw[i][j]==track_surface) {
					map.TrackDraw[i-1][j-1]=track_surface;
					map.TrackDraw[i-1][j]=track_surface;
					map.TrackDraw[i][j-1]=track_surface;
				}
			}
		}
		*/
		//map.Blur();
		return map;
	}
	
	public MAP CreateCircle(MAP map,int ori, int ang, int track_radius,  int track_width, MATERIAL track_surface) {
		for (int i=0; i<map.GetWidth(); i++) {
			for (int j=0; j<map.GetHeight(); j++) {
				if ((Math.sqrt((i-this.LasPosX)*(i-this.LasPosX)+(j-this.LasPosY)*(j-this.LasPosY)) > track_radius-track_width/2) 
				 && (Math.sqrt((i-this.LasPosX)*(i-this.LasPosX)+(j-this.LasPosY)*(j-this.LasPosY)) < track_radius+track_width/2))
				{
					if (ang>0) {
						if(((Math.atan((i-this.LasPosX)/(j-this.LasPosY+0.001))+Math.PI/2) < Math.abs(ang)*Math.PI/180) && ((j-this.LasPosY)<0)) {
						//	map.TrackDraw[i-track_radius][j] = track_surface;
							map.TrackDraw[(int)(Math.cos(ori*Math.PI/180)*(i-this.LasPosX)-Math.sin(ori*Math.PI/180)*(j-this.LasPosY)+this.LasPosX-track_radius*Math.cos(ori*Math.PI/180))]
									     [(int)((Math.sin(ori*Math.PI/180)*(i-this.LasPosX)+Math.cos(ori*Math.PI/180)*(j-this.LasPosY)+this.LasPosY)-track_radius*Math.sin(ori*Math.PI/180))]=track_surface;

						}
					}
					else {
						if(((Math.atan((i-this.LasPosX)/(j-this.LasPosY+0.001))+Math.PI/2) > (180-Math.abs(ang))*Math.PI/180) && ((j-this.LasPosY)<0)) {
						//	map.TrackDraw[i+track_radius][j] = track_surface;
							map.TrackDraw[(int)(Math.cos(ori*Math.PI/180)*(i+track_radius-this.LasPosX)-Math.sin(ori*Math.PI/180)*(j-this.LasPosY)+this.LasPosX)]
										 [(int)(Math.sin(ori*Math.PI/180)*(i+track_radius-this.LasPosX)+Math.cos(ori*Math.PI/180)*(j-this.LasPosY)+this.LasPosY)]=track_surface;

						}
					}
					
				}
		
			}
		}
		this.LasPosX=(int)(this.LasPosX+track_radius*Math.sin(ang*Math.PI/180));
		this.LasPosY=(int)(this.LasPosY-1*track_radius*Math.sin(ang/2*Math.PI/180));
		
		/*
		for (int i=0; i<map.GetWidth(); i++) {
			for (int j=0; j<map.GetHeight(); j++) {
				if(map.TrackDraw[i][j]==track_surface) {
					map.TrackDraw[i-1][j-1]=track_surface;
					map.TrackDraw[i-1][j]=track_surface;
					map.TrackDraw[i][j-1]=track_surface;
				}
			}
		}*/
		
		//this.map.TrackDraw[i][j] =;
		return map;
	}

	public MAP LoopClosure(MAP map) {
		for(int k=0; k<50; k++) {
			for (int i=0; i<map.GetWidth(); i++) {
				for (int j=0; j<map.GetHeight(); j++) {
					if(map.TrackDraw[i][j]!=map.mat) {
						map.TrackDraw[i-1][j-1]=map.TrackDraw[i][j];
						map.TrackDraw[i-1][j]=map.TrackDraw[i][j];
						map.TrackDraw[i][j-1]=map.TrackDraw[i][j];
					}
				}
			}
		}
		for(int k=0; k<50; k++) {
			for (int i=1; i<map.GetWidth(); i++) {
				for (int j=1; j<map.GetHeight(); j++) {
					if(map.TrackDraw[i][j]==map.mat) {
						map.TrackDraw[i-1][j-1]=map.TrackDraw[i][j];
						map.TrackDraw[i-1][j]=map.TrackDraw[i][j];
						map.TrackDraw[i][j-1]=map.TrackDraw[i][j];
					}
				}
			}
		}
		return map;
	}


}
