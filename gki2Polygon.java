package gki.d2.pixel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.MXBean;

import gki.d2.color.gkiColor;
import gki.d2.sort.gki2PointYMergeSort;
import gki.d2.sort.gkiDoubleMergeSort;

public class gki2Polygon {

	String id;
	public String groupId;
	public String name,subName,groupName;
    public int minX,minY,maxX,maxY;
    public int width,height;
	public gkiColor color=new gkiColor(0xff008800);
    gki2Line l=new gki2Line();
    gki2PointYMergeSort gki2=new gki2PointYMergeSort();
    

    public gkiColor boundColor1=new gkiColor(0,0,0,0),
    		        boundColor2=new gkiColor(0,0,0,0),
    		        boundColor3=new gkiColor(0,0,0,0),
    		        boundColor4=new gkiColor(0,0,0,0);
	public void boundColor1(gkiColor c) {
		boundColor1=c;
	}
	public void boundColor2(gkiColor c) {
		boundColor2=c;
	}
	public void boundColor3(gkiColor c) {
		boundColor3=c;
	}
	public void boundColor4(gkiColor c) {
		boundColor4=c;
	}
    public void color(gkiColor color) {
    	this.color=color;
    	boundColor1=boundColor2=boundColor3=boundColor4=color;
    }
    public List<gki2Point> p=new ArrayList<>();
	public void gki2Point(double x,double y) {
		gki2Point p=new gki2Point(x,y);
		p.id=id;
		
	    p.name=name;
	    p.subName=subName;
	    p.groupName=groupName;
		this.p.add(p);
	}
	public void gki2Point(double x,double y,gkiColor c) {
		gki2Point p=new gki2Point(x,y);
		p.id=id;
		p.color=c;
	    p.name=name;
	    p.subName=subName;
	    p.groupName=groupName;
		this.p.add(p);
		System.out.println(p.color); 
	}
	public void gki2Point(gki2Point p) {
		p.id=id;
		p.color=color;
	    p.name=name;
	    p.subName=subName;
	    p.groupName=groupName;
	    this.p.add(p);
	}
	
    public void gki2Polygon(gki2Polygon p) {
    	for(int i=0;i<p.p.size();i++) {
    		gki2Point g=p.p.get(i);
    		this.p.add(g);
    	}
    }
    
    
	public gki2Polygon() {
		// TODO Auto-generated constructor stub
	}
	
	int[] diff;
	public int[] col,bcol;
   public ArrayList<gki2Point> point=new ArrayList<>();
   public ArrayList<gki2Point> fill=new ArrayList<>();
	public void process(){
        //System.out.println(p);
	    
        double[] x=new double[p.size()];
        double[] y=new double[p.size()];
		for(int i=0;i<x.length;i++) {
			x[i]=p.get(i).x;
		    y[i]=p.get(i).y;
		}
        
		gkiDoubleMergeSort dsort=new gkiDoubleMergeSort();
		dsort.sortAscending(x);
		dsort.sortAscending(y);
 
		minX=(int) x[0];
		minY=(int) y[0];
		maxX=(int) x[x.length-1];
		maxY=(int) y[y.length-1];
		width=(int) (maxX-minX)+1;
		height=(int) (maxY-minY)+1;
        System.out.println(minX+":"+minY);
        System.out.println(maxX+":"+maxY);

		diff=new int[width*height];
		col=new int[width*height];
		bcol=new int[width*height];

		for(int i=0,j=1;i<p.size();i++,j++) {
			
			//System.out.println(i+":"+j);
			gki2Point u=p.get(i);
			gki2Point v=p.get(j);
			//System.out.println(u+":"+v);
			
			gki2Line s=new gki2Line();
			s.sp(u);
			s.sc(u.color);
			s.ep(v);
			s.ec(v.color);
			s.process();

			if(u.y>v.y || u.y==v.y) {
				s.direction=-1;
			}else {
				s.direction=1;
			}
            //System.out.println(u.color+":"+v.color);

			
			
			
			for(int k=0;k<s.point.size();k++) {
			
				int p=(int) (s.point.get(k).x()-minX);
				int q=(int) (s.point.get(k).y()-minY);
				diff[p+q*width]=s.direction;
				col[p+q*width]=s.point.get(k).color.argb;
             }
		//	//System.out.println(u.x()+":"+u.y()+"@"+v.x()+":"+v.y()+":"+"::"+s.direction);
			if(j==p.size()-1) {
				j=-1;
			}
			
			
		}
		
		gki2Point p1=new gki2Point(minX,minY,boundColor1);
		gki2Point p2=new gki2Point(minX,maxY,boundColor2);
		gki2Point p3=new gki2Point(maxX,maxY,boundColor3);
		gki2Point p4=new gki2Point(maxX,minY,boundColor4);
	
		gki2Point[] pq= {p1,p2,p3,p4};
		
		for(int i=0,j=1;i<pq.length;i++,j++) {
			
			if(j==pq.length) {
				j=0;
			}
			System.out.println(i+":"+j);
			gki2DotLine s=new gki2DotLine();
			s.sp(pq[i]);
			s.ep(pq[j]);
			s.sc(pq[i].color);
			s.ec(pq[j].color);
			s.processX();
			
			for(int k=0;k<s.point.size();k++) {
				
				int p=(int) (s.point.get(k).x()-minX);
				int q=(int) (s.point.get(k).y()-minY);
				//diff[p+q*width]=s.direction;
				bcol[p+q*width]=s.point.get(k).color.argb;
             }
			
			
			
		}
		
		
		
		   
	}
    
    public boolean contain(int x,int y) {
    	
    // System.out.println(minX+":"+maxX);
     x-=minX;
     y-=minY;
     int o = 0;
     int sum=0;
    	for(int i=0;i<width;i++) {
    		
    		if(i<x) {
    	 try {
    		 if(y>0 && y<height) {
    			o=diff[i+y*width];
    			}
    	 }catch(ArrayIndexOutOfBoundsException w) {
    		 
    	 }
    		if(o!=0) {
    		//	System.out.print(o+" , ");
        		sum+=o;	
    		}
    		}
    		
    	}
    	
    	
    	if(sum>=1) {
    		return true;
    	}else {
    		return false;
    	}
    
    
    }
    int sgc,egc,sx,ex,y;
    gkiColor sc,ec;
   
    public gkiColor colour(int x,int y) {
    	
    	
    int count=0; 	
    // System.out.println(minX+":"+maxX);
     x-=minX;
     y-=minY;
     int cls=0;
     for(int i=0;i<width;i++) {
    		cls=col[i+y*width];	
		    
    		if(cls!=0) {
    			
    		if(count==0) {
    			sgc=cls;
    		    sx=i;
    		    this.y=y;
    		    

    		}	
    		
    			
     		//System.out.print(cls+" , ");
    		//System.out.println(" ");
    	//	System.out.print(i+" , ");
    		
    		ex=i;
    		egc=cls;
    		
    		
    		
    		count++;
    		}
    		
    		
    		
    		
    		
    	}
    	
    	//System.out.println(sgc+":"+sx+":"+this.y+"::"+egc+":"+ex+":"+this.y);

    	int xdiff=ex-sx;
    	sc=new gkiColor(sgc);
    	ec=new gkiColor(egc);
    	
    //	double slope=ydiff/(double)xdiff;
    	double ratio=(x-sx)/(double)xdiff;
    	
    	int alpha=(int)(ec.alpha*ratio + sc.alpha*(1-ratio));
		int red=(int)(ec.red*ratio+sc.red*(1-ratio));
		int green=(int)(ec.green*ratio+sc.green*(1-ratio));
		int blue=(int)(ec.blue*ratio+sc.blue*(1-ratio));
		if(alpha<0){alpha=0;}
		if(alpha>255){alpha=255;}
		if(red<0){red=0;}
		if(red>255){red=255;}
		if(green<0){green=0;}
		if(green>255){green=255;}
		if(blue<0){blue=0;}
		if(blue>255){blue=255;}
		gkiColor col=new gkiColor(alpha, red, green, blue);
		return col;
    	
    	    
    
    }
    

    
    public gkiColor boundcolour(int x,int y) {
    int count=0; 	
    // System.out.println(minX+":"+maxX);
     x-=minX;
     y-=minY;
     int cls=0;
     for(int i=0;i<width;i++) {
    		cls=bcol[i+y*width];	
		    
    		if(cls!=0) {
    			
    		if(count==0) {
    			sgc=cls;
    		    sx=i;
    		    this.y=y;
    		    

    		}	
    		
    			
     		//System.out.print(cls+" , ");
    		//System.out.println(" ");
    	//	System.out.print(i+" , ");
    		
    		ex=i;
    		egc=cls;
    		
    		
    		
    		count++;
    		}
    		
    		
    		
    		
    		
    	}
    	
    	//System.out.println(sgc+":"+sx+":"+this.y+"::"+egc+":"+ex+":"+this.y);

    	int xdiff=ex-sx;
    	//int ydiff=y-y;
    	sc=new gkiColor(sgc);
    	ec=new gkiColor(egc);
    	
    //	double slope=ydiff/(double)xdiff;
    	double ratio=(x-sx)/(double)xdiff;
    	
    	int alpha=(int)(ec.alpha*ratio + sc.alpha*(1-ratio));
		int red=(int)(ec.red*ratio+sc.red*(1-ratio));
		int green=(int)(ec.green*ratio+sc.green*(1-ratio));
		int blue=(int)(ec.blue*ratio+sc.blue*(1-ratio));
		if(alpha<0){alpha=0;}
		if(alpha>255){alpha=255;}
		if(red<0){red=0;}
		if(red>255){red=255;}
		if(green<0){green=0;}
		if(green>255){green=255;}
		if(blue<0){blue=0;}
		if(blue>255){blue=255;}
		gkiColor col=new gkiColor(alpha, red, green, blue);
		return col;
    	
    	    
    
    }



}
