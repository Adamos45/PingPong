package tb.sockets.client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class Rakietka implements Runnable, ActionListener
{
	double x=175;
	Shape rect;
	boolean mine;

Rakietka(boolean a)
{
	mine=a;
}
@Override
public void run() 
{
	while (true) 
		{
			rect = nextFrame();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}

protected Shape nextFrame() 
{
	double y;
	if(mine)
		y=400;
	else
		y=20;

	rect = new Rectangle2D.Double(x,y,150,20);
	Area area = new Area(rect);
	return area;
}
double getObjLocation()
{
	return 350-x;
}
void setObjLocation(double x_)
{
	if(mine)
		x+=x_;
	else
		x=x_;
}

@Override
public void actionPerformed(ActionEvent arg0) {
	OrderPane.buffer.setColor(Color.BLACK);
	OrderPane.buffer.fill(rect);
	OrderPane.buffer.draw(rect);
}
}