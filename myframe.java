import javax.swing.*;

public class myframe extends JFrame { //inherits parent JFrame
   
    GraphicsDemo graphicDemo = new GraphicsDemo();
    
    //new class to avoid overrideable methods in constructor
    public void init_myframe(){
        this.setSize(500, 525);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(graphicDemo);
        this.setVisible(true);
    }
    
}
