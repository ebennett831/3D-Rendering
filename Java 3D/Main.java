
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args)
    {
        JFrame window = new JFrame("Testing");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);


        int height = 720;
        int width = 1080;

        RenderPanel p = new RenderPanel(height, width);

        window.setContentPane(p);
        window.pack();
        window.setVisible(true);

        p.start();

        



    }

    
    
}
