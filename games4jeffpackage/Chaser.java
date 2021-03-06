package games4jeffpackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.util.Random;

public class Chaser extends Enemy{

  private Handler handler;
  private Screen screen;
	private int randTimer = 0;
	private int [] imperfections = new int [2];

	private Texture tex = Main.getInstance();

  public Chaser(float x, float y, String id, Handler handler, Screen screen) {
    super(x, y, id, 20);
    this.handler = handler;
    this.screen = screen;

    width = 28;
    height = 28;
  }

  public void tick() {
    x+=velX;
    y+=velY;
		if (randTimer == 50){
			imperfections[0] = (int)(Math.random() * 51)-25;
			imperfections[1] = (int)(Math.random() * 51)-25;
			randTimer = 0;
		}
    for(int i = 0; i < handler.stuff.size(); i++){
      GameThing thing = handler.stuff.get(i);
      if (thing.getId() == "Player"){
        float pX = thing.getX();
        float pY = thing.getY();
        float d = (float)Math.sqrt(Math.pow((x-(int)pX),2) + Math.pow((y-(int)pY),2));
				if (d > 50) {
					pX = thing.getX() + thing.getWidth()/2 + imperfections[0];
	        pY = thing.getY() + thing.getHeight()/2 + imperfections[1];
					d = (float)Math.sqrt(Math.pow((x-(int)pX),2) + Math.pow((y-(int)pY),2));
				}
        if (d != 0){
          velX = -(x - (int)pX)/d*2;
          velY = -(y - (int)pY)/d*2;
        }
      }
      if (thing.getId() == "Shot"){
        if (thing.getBounds().intersects(getBounds())){
          hp-=screen.getWeapon().getDamage();
          handler.removeObject(thing);
          if (hp <= 0){
            handler.removeObject(this);
          }
        }
      }
      if (thing.getId().equals("Block") || thing.getId().equals("Door") || (thing.getId().length() >= 6 && thing.getId().substring(0,6).equals("Enemy.") && thing != this)){
        if (thing.getBounds().intersects(getBoundsRight())){
					x = thing.getX() - width;
        }
				if (thing.getBounds().intersects(getBoundsLeft())){
					x = thing.getX() + thing.getBounds().width;
        }
				if (thing.getBounds().intersects(getBoundsTop())){
					y = thing.getY() + thing.getBounds().height;
				}
				if (thing.getBounds().intersects(getBoundsBottom())){
					y = thing.getY() - height;
				}
      }
    }
		randTimer++;
  }

  public void render(Graphics g) {
		super.render(g);
		if (velX > 0) g.drawImage(tex.enemy[0], (int)x, (int)y, null);
		else g.drawImage(tex.enemy[1], (int)x, (int)y, null);
  }

  public Rectangle getBounds() {
    return new Rectangle((int)x, (int)y, (int)width, (int)height);
  }

  public Rectangle getBoundsLeft(){
    return new Rectangle((int)x, (int)y+5, (int)5, (int)height-10);
  }

  public Rectangle getBoundsRight(){
    return new Rectangle((int)x+(int)width-5, (int)y+5, (int)5, (int)height-10);
  }

  public Rectangle getBoundsTop(){
    return new Rectangle((int)x+(int)width/4, (int)y, (int)width/2, (int)height/2);
  }

  public Rectangle getBoundsBottom(){
    return new Rectangle((int)x+(int)width/4, (int)y + (int)height/2, (int)width/2, (int)height/2);
  }
}
