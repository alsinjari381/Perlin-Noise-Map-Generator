import java.util.concurrent.ThreadLocalRandom;

public class PerlinNoise {
    private class Coordinates{
        public double x;
        public double y;
        
        public Coordinates(double x_position, double y_position){
            x = x_position;
            y = y_position;
        }
        public double calculate_dot_product(double x_position, double y_position){
            return (this.x * x_position) + (this.y * y_position);
        }
    }

    public int num_pixels = 500;
    public double[][] pixel = new double [num_pixels][num_pixels];
    public double min, max; //these will be used in our graphics  to cast interpolated points to the 256 points in RGB
    
    
    public PerlinNoise(){
        min = 1000.0;
        max = -500.0; //values will be written over in init_pixel
        //stack these on top of each other to create a more complex, realistic image
        init_pixels(100); 
        init_pixels(50);
        init_pixels(25);
        init_pixels(10);
        init_pixels(5);
        init_pixels(4);
        init_pixels(1);

    }

    private void init_pixels(int pps){ //pixels per square
        
        Coordinates[][] intersection = new Coordinates[num_pixels/pps + 1][num_pixels/pps + 1];
        
        //give all grid intersections a random gradient vector
        for (int x = 0; x < num_pixels / pps + 1; x++){
            for (int y = 0; y < num_pixels / pps + 1; y++){
                double theta = ThreadLocalRandom.current().nextFloat() * Math.PI * 2; //random generated angle for unit vector
                double xVal = Math.cos(theta); // x and y position using trig
                double yVal = Math.sin(theta);
                Coordinates coordinate = new Coordinates(xVal, yVal);
                intersection[x][y] = coordinate; 
            }
        }
        
        for (int x = 0; x < 500; x++){
            for (int y = 0; y < 500; y++){
                double x_mod = x;
                double y_mod = y;
                double[] dotproduct = new double[4];
                int xpos = (int)(x_mod/pps);
                int ypos = (int)(y_mod/pps);
                double local_x = (x_mod - xpos*pps);
                double local_y = (y_mod - ypos*pps);
               
                dotproduct[0] = intersection[xpos][ypos].calculate_dot_product(local_x + 0.5, local_y + 0.5); //precalculating distance vectors
                dotproduct[1] = intersection[xpos+1][ypos].calculate_dot_product(local_x - pps + 0.5, local_y + 0.5);
                dotproduct[2] = intersection[xpos][ypos+1].calculate_dot_product(local_x + 0.5, local_y - pps + 0.5);   
                dotproduct[3] = intersection[xpos+1][ypos+1].calculate_dot_product(local_x - pps + 0.5, local_y - pps + 0.5);
                
                double x_weight = (smoothstep((double) local_x / pps)); // find position on a 0-1 scale relative to grid intersections
                double y_weight = (smoothstep((double) local_y / pps));

                double a = lerp(x_weight, dotproduct[0],dotproduct[1]);
                double b = lerp(x_weight, dotproduct[2],dotproduct[3]);
                
                pixel[x][y] += lerp(y_weight, a, b);
                
                if (pixel[x][y] < this.min){
                    this.min = pixel[x][y];
                } else if (pixel[x][y] > this.max){
                    this.max = pixel[x][y];
                }
            }
        }
    }
    
    //courtesy of wikipedia
    private static double lerp(double w, double a0, double a1){
	// if weight is 0, return a0. if weight is 1, return a1
        return a0 + w * (a1-a0);
    }
    
    //smoothstep algorithim courtesy of wikipedia
    private static double smoothstep(double x){
       return x * x * (3 - 2 * x);
    }
}
