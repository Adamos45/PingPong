package tb.sockets.client;

import java.awt.Color;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Pilka implements Runnable, ActionListener
{
	Shape ball;
	AffineTransform aft;
	double dx=6,dy=2;
	boolean gosleep=true;
	Rectangle2D bounds;
	@Override
	public void run() 
	{
		ball = new Ellipse2D.Double(100,100,50,50);
		while (true) {
			if(gosleep==false)
			{
				ball = nextFrame();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	void accesssleep(boolean b_)
	{
		gosleep=b_;
	}
	Shape nextFrame()
	{
		Area area = new Area(ball);
		aft = new AffineTransform();
		bounds = area.getBounds2D();

		if (bounds.getX() <= 0)
		{
			dx=-dx;
		}
		if(bounds.getMaxX() > 475)
		{
			dx = -dx;
		}
		if (bounds.getY() <= 35) 
		{
			dy = -dy;
		}
		if(bounds.getMaxY() > 400)
		{
			double x=425-OrderPane.rocket.getObjLocation();
			double a=bounds.getX()+100;
			double y=425-OrderPane.rocket.getObjLocation()+150;
			double b=bounds.getX()+150;
			if(!(x>b||y<a))
				{
					dy=-dy;
				}
			else
				{
					dy=-dy;
					MainFrame.hispoints++;
				}
		}

		aft.translate(dx, dy);
		area.transform(aft);
		return area;	
	}
	String getObjLoc()
	{
		String str = bounds.getCenterX()+" "+bounds.getCenterY();
		return str;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		OrderPane.buffer.setColor(Color.BLUE);
		OrderPane.buffer.fill(ball);
		OrderPane.buffer.draw(ball);
	}
}
