import java.awt.*;
import javax.swing.*;

public class GraphicsDemo extends JPanel{ // inherits JPanel
    PerlinNoise perlin = new PerlinNoise();
        
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
              

        Graphics2D g2D = (Graphics2D) g; //cast to 2D frame
       
        /*find line to map floats of pixel[x][y] to usable 0-256 rgb values
        used equations: 
        y = mx+b 
        m = (y2-y1)/(x2-x1) 
        y = m(x − x1) + y1
        
        
        y2 = 256
        y1 = 0
        x1 = min
        x2 = max
        */
        
        double m = 256.0 / (perlin.max - perlin.min);
        double b = m * (-1 * perlin.min);
       
        for (int x = 0; x < 500; x++){
            for (int y = 0; y < 500; y++){
                
                int c = (int)(m * perlin.pixel[x][y] + b) - 1;
                
                if(c < 0){ //error, dead pixels
                    c = (int)(m * perlin.pixel[x][y-1] + b) - 1; //revert back one
                } 
                
                
                if (c < 130){
                    g2D.setColor(Color.blue);
                } else if (c >= 130 && c < 135){
                    g2D.setColor(Color.yellow);
                } else if (c >= 135 && c < 192){
                    g2D.setColor(new Color(0,150,0)); //green
                } else if (c >= 192 && c < 230){
                    g2D.setColor(Color.gray);
                } else if (c == 999){
                    g2D.setColor(Color.orange);
                } else {
                    g2D.setColor(Color.white);
                } 
                
                //g2D.setColor(new Color(c,c,c)); // uncomment this if you want to see just the perlin noise
                g2D.fillRect(x,y,1,1);
           }
       }
    }
}
