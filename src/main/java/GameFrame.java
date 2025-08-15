import javax.swing.JFrame;

public class GameFrame extends JFrame {


    public GameFrame(String title){
        super(title);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(512, 512);
        //setPreferredSize(512, 512);

        //pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


}
