package gki.d2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gki.color.gkiColor;
import gki.d2.sort.gki2PointYMergeSort;
import gki.d2.sort.gkiDoubleMergeSort;

public class gki2Polygon {

	public int id,groupId;
	public String name,subName,groupName;
    public double minX,minY,maxX,maxY;
	public gkiColor color=null;
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
		p.color=null;
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
   public ArrayList<gki2Point> point=new ArrayList<>();
   public ArrayList<gki2Point> fill=new ArrayList<>();

	public void processX(){
		point.removeAll(point);
		fill.removeAll(fill);

		gki2DotLine[]   lines=new gki2DotLine[p.size()];
	
		

		if(!p.isEmpty()) {
		   int count=0;
           for(int i=0,j=1;i<p.size();i++,j++){
				
				if(j==p.size()){
					j=0;
				}
				if(p.get(i).color==null) {
					p.get(i).color=color;
				}
				if(p.get(j).color==null) {
					p.get(j).color=color;
				}
		    
	        	// System.out.println(p.get(j).color);
				 lines[count]= new gki2DotLine();

				 lines[count].id=id;
				 lines[count].groupId=groupId;
				 lines[count].groupName=groupName;
				 lines[count].name=name;
				 lines[count].subName=subName;
		         
		         
				 lines[count].sp(p.get(i));
				 lines[count].sc(p.get(i).color);
				 lines[count].ep(p.get(j));
				 lines[count].ec(p.get(j).color);
				 lines[count].processX();
		         
		         count++;
		        
				
			}
           
           for(int i=0;i<lines.length;i++){
				for(int j=0;j<lines[i].point.size();j++){
				        point.add(lines[i].point.get(j));

				}
			}
           
           gki2Point[] arr=new gki2Point[point.size()];
			
			for(int i=0;i<arr.length;i++){
				arr[i]=point.get(i);
			}
		
           gki2.sortAscending(arr);
	

			boolean hs=false;
			ArrayList<gki2Line> rw = new ArrayList<>();

			for(int i=0,j=1;i<arr.length;i++,j++) {
				if(j==point.size()) {
					j=0;
				}
				    int a=arr[i].y();
	                int b=arr[j].y();

	                if(a==b) {
	                	hs=true;
	                }else {
	                    hs=false;
	                }
	    			gki2Line lw = null;
	    			if(hs) {
	    				gki2Point u=arr[i];
	                	gki2Point v=arr[j];
	                	lw=new gki2Line();
	                	if(u.x>v.x) {
	                		gki2Point t=u;
	                		u=v;
	                		v=t;
	                	}
	                	lw.sp(arr[i]);
	                	lw.ep(arr[j]);
	                	lw.sc(arr[i].color);
	                	lw.ec(arr[j].color);
	                	lw.process();
                        rw.add(lw);
	    			}else {
                        if(rw.size()>1) {
                        	for(int bc=rw.size()-2;bc>0;bc=bc-2){
                        		rw.remove(bc);
                        	}
                        }
		               // System.out.println(rw.size());

		                
		            	for(int k=0;k<rw.size();k++) {
	                       for(int z=0;z<rw.get(k).point.size();z++ ) {
	                    	  fill.add(rw.get(k).point.get(z));   
	                       }
		            	  
		            	}

		            	rw.removeAll(rw);
	                }

				
			}
			
			
			
           

		//	System.out.println(new Date()+"-ss");

          
			
		}
	}
    
    
    
}
