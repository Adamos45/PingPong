package tb.sockets.server;

import java.awt.Color;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


public class Pilka implements Runnable, ActionListener
{
	Shape ball;
	double x, y;
	
	@Override
	public void run() 
	{
		while (true) {
				ball = nextFrame();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	Shape nextFrame()
	{
		ball = new Ellipse2D.Double(x,y,50,50);
		Area area = new Area(ball);
		Rectangle2D bounds = area.getBounds2D();
		
		if(bounds.getMaxY() > 400)
		{
			double x=425-OrderPane.rocket.getObjLocation();
			double a=bounds.getX()+100;
			double y=425-OrderPane.rocket.getObjLocation()+150;
			double b=bounds.getX()+150;
			if(!(x>b||y<a))
				{
				}
			else
				{
					ServMain.hispoints++;
				}
		}
		return area;	
	}
	void inputLoc(String str)
	{
		String str1,str2;
		int index;
		index=str.indexOf(' ');
		str1=str.substring(0, index);
		str2=str.substring(index+1,str.length());
		//System.out.println(str1+" "+str2);
		x=450-Double.parseDouble(str1);
		y=410-Double.parseDouble(str2);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		OrderPane.buffer.setColor(Color.BLUE);
		OrderPane.buffer.fill(ball);
		OrderPane.buffer.draw(ball);
	}
}
