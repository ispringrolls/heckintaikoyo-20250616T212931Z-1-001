package heckintaikoyo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class REALTAIKO implements ActionListener {
    //Buttons
    JButton startButton;
    JButton exitButton;
    JButton howPlay;
    JButton close;
    JButton easySong1Button;
    JButton hardSong1Button;
    JButton playAgainButton;
    JFrame frame;
    //Variables
    int combo = 0;
    int score = 0;
    int numPerfect = 0;
    int numGreat = 0;
    int numOk = 0;
    int numMisses = 0;
    boolean levelEnded = false;
    boolean passed = false;
    boolean isKatsu = false; // don refers to the red notes (0), and katsu refers to the blue notes (1)
    boolean isDon = false;

    String message = " "; // Have to define this here so I can print it in my gamepanel, cause I can't acccess paintComponent in gamepanel
    String selectedSong = "";
    String rank = "F";
    Image don = new ImageIcon("heckintaikoyo/DONFINALNOBG.png").getImage();
    Image katsu = new ImageIcon("heckintaikoyo/KATSUFINALNOBG.png").getImage();
    Music music = new Music(); // music player 

    public static void main(String[] args){
        new REALTAIKO();
    }

    public REALTAIKO() {

        // TItle screen
        frame = new JFrame("TAIKO NO TATSUJIN FROM TEMU");
        startButton = new JButton("Start!");
        exitButton = new JButton("Exit");
        howPlay = new JButton("How to Play");
        close = new JButton("Close");
        easySong1Button = new JButton("Song 1 - Easy");
        hardSong1Button = new JButton("Song 1 - Hard");
        playAgainButton = new JButton("Play Again");
        // buttons 
        startButton.setBounds(500, 350, 200, 50);
        exitButton.setBounds(500, 450, 200, 50);
        howPlay.setBounds(500, 400, 200, 50);
        close.setBounds(500, 100, 200, 50);
        easySong1Button.setBounds(500, 300, 200, 50); 
        hardSong1Button.setBounds(500, 360, 200, 50);
        playAgainButton.setBounds(500, 550, 200, 50);


        // input 
        startButton.addActionListener(this);
        exitButton.addActionListener(this);
        howPlay.addActionListener(this);
        close.addActionListener(this);
        easySong1Button.addActionListener(this);
        hardSong1Button.addActionListener(this);
        playAgainButton.addActionListener(this);
        // adds buttons to frame 
        frame.setLayout(null);
        frame.add(startButton);
        frame.add(exitButton);
        frame.add(howPlay);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        easySong1Button.setVisible(false);
        hardSong1Button.setVisible(false);
        playAgainButton.setVisible(false);
        frame.add(easySong1Button);
        frame.add(hardSong1Button);
        frame.add(playAgainButton);
    }

    // makes buttons do things
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == startButton) {
            // hides main menu buttons
            startButton.setVisible(false);
            exitButton.setVisible(false);
            howPlay.setVisible(false);
            // shows level select buttons
            easySong1Button.setVisible(true);
            hardSong1Button.setVisible(true);
        } else if (source == easySong1Button || source == hardSong1Button) {
            // hides level select buttons
            easySong1Button.setVisible(false);
            hardSong1Button.setVisible(false);
            // select difficulty 
            if (source == easySong1Button) {
                selectedSong = "Song1Easy";
            } else if (source == hardSong1Button) {
                selectedSong = "Song1Hard";
            }

            // remove previous game panels
            for (Component c : frame.getContentPane().getComponents()) {
                if (c instanceof GamePanel) frame.remove(c);
            }
            frame.repaint();
            // start
            GamePanel gamePanel = new GamePanel(selectedSong);
            gamePanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            frame.add(gamePanel);
            frame.revalidate();
            frame.repaint();
            gamePanel.requestFocusInWindow();
        // exit 
        } else if (source == exitButton) {
            System.exit(0);
        } else if (source == howPlay) { // opens a thing that tells you how to play 
            JOptionPane.showMessageDialog(frame,
            "How to Play:\n" +
            "- Press 'D' for red notes\n" +
            "- Press 'K' for blue notes\n" +
            "- Hit notes in beat when they reach the gray box\n" +
            "- Try to get the highest combo and score!",
            "HOW TO PLAY TAIKO NO TATSUJIN FROM TEMU",
            JOptionPane.INFORMATION_MESSAGE);
        } else if (source == playAgainButton) {
            // wipes game panel and makes the song select visible. Also resets variables. 
            for (Component c : frame.getContentPane().getComponents()) {
                 if (c instanceof GamePanel) frame.remove(c); }
                    playAgainButton.setVisible(false);
                    easySong1Button.setVisible(true);
                    hardSong1Button.setVisible(true);
                    frame.repaint();
                    levelEnded = false;
                    rank = "F";
                    score = 0;
                    combo = 0;
                    numPerfect = 0;
                    numGreat = 0;
                    numOk = 0;
                    numMisses = 0;
                    message = " ";
                    passed = false;
        }
    }

    class GamePanel extends JPanel implements KeyListener {
        ArrayList<Note> notes = new ArrayList<>();
        int hitZoneX = 50;
        public GamePanel(String song) {
   
            // Play music in sync with notes
            if (song.equals("Song1Easy") || song.equals("Song1Hard")) {
                music.play("heckintaikoyo\\idol.wav");
            }
            // Loads notes from the note array for the map 
            if (song.equals("Song1Easy")) {
                notes.add(new Note(300, 0));
                notes.add(new Note(1100, 0));
                notes.add(new Note(1300, 1));
                notes.add(new Note(1500, 0));
                notes.add(new Note(2300, 0));
                notes.add(new Note(2500, 1));
                notes.add(new Note(2700, 0));
                notes.add(new Note(3500, 0));
                notes.add(new Note(3700, 1));
                notes.add(new Note(3900, 0));
                notes.add(new Note(4700, 0));
                notes.add(new Note(4900, 1));
                notes.add(new Note(5100, 0));
                notes.add(new Note(5400, 0));
                notes.add(new Note(5600, 0));
                notes.add(new Note(5750, 0));
                notes.add(new Note(5950, 0));
                notes.add(new Note(7550, 0));
                notes.add(new Note(7700, 1));
                notes.add(new Note(7850, 0));
                notes.add(new Note(8150, 0));
                notes.add(new Note(8300, 1));
                notes.add(new Note(8450, 0));
                notes.add(new Note(8750, 0));
                notes.add(new Note(8900, 1));
                notes.add(new Note(9050, 0));
                notes.add(new Note(9350, 0));
                notes.add(new Note(9500, 1));
                notes.add(new Note(9650, 0));
                notes.add(new Note(9950, 0));
                notes.add(new Note(10100, 1));
                notes.add(new Note(10250, 0));
                notes.add(new Note(10550,1));
                notes.add(new Note(10700, 1));
                notes.add(new Note(10850, 0));
                notes.add(new Note(11150, 0));
                notes.add(new Note(11300, 0));
                notes.add(new Note(11450, 0));
                notes.add(new Note(11750, 0));
                notes.add(new Note(11900, 1));
                notes.add(new Note(12050, 0));
                notes.add(new Note(12250, 0));
                notes.add(new Note(12450, 0));
                notes.add(new Note(12650, 1));
                notes.add(new Note(12850, 1));
                notes.add(new Note(13050, 0));
                notes.add(new Note(13250, 1));
                notes.add(new Note(13450, 0));
                notes.add(new Note(13650, 1));
                notes.add(new Note(13850, 0));
                notes.add(new Note(14050, 1));
                notes.add(new Note(14250, 0));
                notes.add(new Note(14450, 1));
                notes.add(new Note(14650, 0));
                notes.add(new Note(14850, 1));
                notes.add(new Note(15050, 0));
                notes.add(new Note(15250, 1));
                notes.add(new Note(15450, 0));
                notes.add(new Note(15650, 1));
                notes.add(new Note(15850, 0));
                notes.add(new Note(15900, 1));
                notes.add(new Note(16100, 0));
                notes.add(new Note(16300, 1));
                notes.add(new Note(16500, 1));
                notes.add(new Note(16700, 0));
                notes.add(new Note(16900, 1));
                notes.add(new Note(17100, 0));
                notes.add(new Note(17300, 0));
                notes.add(new Note(17500, 0));
                notes.add(new Note(17550, 0));
                notes.add(new Note(17750, 1));
                notes.add(new Note(17900, 0));
                notes.add(new Note(18100, 0));
                notes.add(new Note(18150, 0));
                notes.add(new Note(18350, 1));
                notes.add(new Note(18450, 0));
                notes.add(new Note(18600, 0));
                notes.add(new Note(18650, 0));
                notes.add(new Note(18800, 1));
                notes.add(new Note(18950, 1));
                notes.add(new Note(19100, 1));
                notes.add(new Note(19250, 1));
                notes.add(new Note(19400, 1));
                notes.add(new Note(19550, 0));
                notes.add(new Note(19700, 0));
                notes.add(new Note(19850, 0));
                notes.add(new Note(20000, 0));
                notes.add(new Note(20150, 0));
                notes.add(new Note(20300, 0));
                notes.add(new Note(20450, 0));
                notes.add(new Note(20600, 0));
                notes.add(new Note(20750, 0));
                notes.add(new Note(20900, 1));
                notes.add(new Note(21050, 0));
                notes.add(new Note(21200, 1));
                notes.add(new Note(21350, 0));
                notes.add(new Note(21500, 1));
                notes.add(new Note(21650, 0));
                notes.add(new Note(21800, 1));
                notes.add(new Note(21950, 0));
                notes.add(new Note(22100, 1));
                notes.add(new Note(22250, 0));
                notes.add(new Note(22400, 1));
                notes.add(new Note(22550, 0));
                notes.add(new Note(22700, 1));
                notes.add(new Note(22850, 0));
                notes.add(new Note(23000, 1));
                notes.add(new Note(23150, 0));
                notes.add(new Note(23300, 1));
                notes.add(new Note(23450, 1));
                notes.add(new Note(23600, 0));
                notes.add(new Note(23750, 1));
                notes.add(new Note(23900, 0));
                notes.add(new Note(24050, 1));
                notes.add(new Note(24200, 1));
                notes.add(new Note(24350, 0));
                notes.add(new Note(24500, 0));
                notes.add(new Note(24650, 1));
                notes.add(new Note(24800, 0));
                notes.add(new Note(24950, 1));
                notes.add(new Note(25100, 0));
                notes.add(new Note(25250, 1));
                notes.add(new Note(25400, 0));
                notes.add(new Note(25550, 1));
                notes.add(new Note(25700, 0));
                notes.add(new Note(25850, 1));
                notes.add(new Note(26000, 1));
                notes.add(new Note(26150, 0));
                notes.add(new Note(26300, 1));
                notes.add(new Note(26450, 0));
                notes.add(new Note(26600, 1));
                notes.add(new Note(26750, 1));
                notes.add(new Note(26900, 0));
                notes.add(new Note(27050, 0));
                notes.add(new Note(27200, 1));
                notes.add(new Note(27350, 0));
                notes.add(new Note(27500, 1));
                notes.add(new Note(27650, 1));
                notes.add(new Note(27800, 0));
                notes.add(new Note(27950, 1));
                notes.add(new Note(28100, 0));
                notes.add(new Note(28250, 0));
                notes.add(new Note(28400, 1));
                notes.add(new Note(28550, 1));
                notes.add(new Note(28700, 0));
                notes.add(new Note(28850, 1));
                notes.add(new Note(29000, 0));
                notes.add(new Note(29150, 1));
                notes.add(new Note(29300, 0));
                notes.add(new Note(29450, 1));
                notes.add(new Note(29600, 0));
                notes.add(new Note(29750, 1));
                notes.add(new Note(29900, 0));
                notes.add(new Note(30050, 1));
                notes.add(new Note(30200, 1));
                notes.add(new Note(30350, 0));
                notes.add(new Note(30500, 1));
                notes.add(new Note(30650, 0));
                notes.add(new Note(30800, 0));
                notes.add(new Note(30950, 1));
                notes.add(new Note(31100, 1));
                notes.add(new Note(31250, 0));
                notes.add(new Note(31400, 1));
                notes.add(new Note(31550, 0));
                notes.add(new Note(31700, 1));
                notes.add(new Note(31850, 0));
                notes.add(new Note(32000, 1));
                notes.add(new Note(32150, 0));
                notes.add(new Note(32300, 1));
                notes.add(new Note(32450, 1));
                notes.add(new Note(32600, 0));
                notes.add(new Note(32750, 1));
                notes.add(new Note(32900, 0));
                notes.add(new Note(33050, 1));
                notes.add(new Note(33200, 0));
                notes.add(new Note(33350, 1));
                notes.add(new Note(33500, 0));
                notes.add(new Note(33650, 1));
                notes.add(new Note(33800, 1));
                notes.add(new Note(33950, 0));
                notes.add(new Note(34100, 1));
                notes.add(new Note(34250, 0));
                notes.add(new Note(34400, 1));
                notes.add(new Note(34550, 0));
                notes.add(new Note(34700, 1));
                notes.add(new Note(34850, 1));
                notes.add(new Note(35000, 0));
                notes.add(new Note(35150, 1));
                notes.add(new Note(35300, 0));
                notes.add(new Note(35450, 1));
                notes.add(new Note(35600, 0));
                notes.add(new Note(35750, 1));
                notes.add(new Note(35900, 0));
                notes.add(new Note(36050, 1));
                notes.add(new Note(36200, 0));
                notes.add(new Note(36350, 1));
                notes.add(new Note(36500, 1));
                notes.add(new Note(36650, 1));
                notes.add(new Note(36800, 0));
                notes.add(new Note(36950, 1));
                notes.add(new Note(37100, 0));
                notes.add(new Note(37250, 0));
                notes.add(new Note(37400, 1));
                notes.add(new Note(37550, 0));
                notes.add(new Note(37700, 0));   
            } else if (song.equals("Song1Hard")) {            // Loads notes from the note array for the map 
                notes.add(new Note(300, 0));
                notes.add(new Note(700, 1));
                notes.add(new Note(1100, 0));
                notes.add(new Note(1200, 0));
                notes.add(new Note(1300, 1));
                notes.add(new Note(1400, 1));
                notes.add(new Note(1500, 0));
                notes.add(new Note(1900, 0));
                notes.add(new Note(2300, 0));
                notes.add(new Note(2400, 1));
                notes.add(new Note(2500, 1));
                notes.add(new Note(2600, 0));
                notes.add(new Note(2700, 0));
                notes.add(new Note(3100, 1));
                notes.add(new Note(3500, 0));
                notes.add(new Note(3600, 0));
                notes.add(new Note(3700, 1));
                notes.add(new Note(3800, 1));
                notes.add(new Note(3900, 0));
                notes.add(new Note(4300, 0));
                notes.add(new Note(4700, 0));
                notes.add(new Note(4800, 1));
                notes.add(new Note(4900, 1));
                notes.add(new Note(5000, 0));
                notes.add(new Note(5100, 0));
                notes.add(new Note(5250, 1));
                notes.add(new Note(5400, 0));
                notes.add(new Note(5500, 0));
                notes.add(new Note(5600, 0));
                notes.add(new Note(5675, 1));
                notes.add(new Note(5750, 0));
                notes.add(new Note(5850, 0));
                notes.add(new Note(5950, 0));
                notes.add(new Note(6750, 1));
                notes.add(new Note(7550, 0));
                notes.add(new Note(7625, 1));
                notes.add(new Note(7700, 1));
                notes.add(new Note(7775, 0));
                notes.add(new Note(7850, 0));
                notes.add(new Note(8000, 1));
                notes.add(new Note(8150, 0));
                notes.add(new Note(8225, 1));
                notes.add(new Note(8300, 1));
                notes.add(new Note(8375, 0));
                notes.add(new Note(8450, 0));
                notes.add(new Note(8600, 1));
                notes.add(new Note(8750, 0));
                notes.add(new Note(8825, 1));
                notes.add(new Note(8900, 1));
                notes.add(new Note(8975, 0));
                notes.add(new Note(9050, 0));
                notes.add(new Note(9200, 1));
                notes.add(new Note(9350, 0));
                notes.add(new Note(9425, 1));
                notes.add(new Note(9500, 1));
                notes.add(new Note(9575, 0));
                notes.add(new Note(9650, 0));
                notes.add(new Note(9800, 1));
                notes.add(new Note(9950, 0));
                notes.add(new Note(10025, 1));
                notes.add(new Note(10100, 1));
                notes.add(new Note(10175, 0));
                notes.add(new Note(10250, 0));
                notes.add(new Note(10400, 1));
                notes.add(new Note(10550, 1));
                notes.add(new Note(10625, 0));
                notes.add(new Note(10700, 1));
                notes.add(new Note(10775, 0));
                notes.add(new Note(10850, 0));
                notes.add(new Note(11000, 1));
                notes.add(new Note(11150, 0));
                notes.add(new Note(11225, 0));
                notes.add(new Note(11300, 0));
                notes.add(new Note(11375, 1));
                notes.add(new Note(11450, 0));
                notes.add(new Note(11600, 1));
                notes.add(new Note(11750, 0));
                notes.add(new Note(11825, 1));
                notes.add(new Note(11900, 1));
                notes.add(new Note(11975, 0));
                notes.add(new Note(12050, 0));
                notes.add(new Note(12150, 1));
                notes.add(new Note(12250, 0));
                notes.add(new Note(12350, 1));
                notes.add(new Note(12450, 0));
                notes.add(new Note(12550, 1));
                notes.add(new Note(12650, 1));
                notes.add(new Note(12750, 0));
                notes.add(new Note(12850, 1));
                notes.add(new Note(12950, 0));
                notes.add(new Note(13050, 0));
                notes.add(new Note(13150, 1));
                notes.add(new Note(13250, 1));
                notes.add(new Note(13350, 0));
                notes.add(new Note(13450, 0));
                notes.add(new Note(13550, 1));
                notes.add(new Note(13650, 1));
                notes.add(new Note(13750, 0));
                notes.add(new Note(13850, 0));
                notes.add(new Note(13950, 1));
                notes.add(new Note(14050, 1));
                notes.add(new Note(14150, 0));
                notes.add(new Note(14250, 0));
                notes.add(new Note(14350, 1));
                notes.add(new Note(14450, 1));
                notes.add(new Note(14550, 0));
                notes.add(new Note(14650, 0));
                notes.add(new Note(14750, 1));
                notes.add(new Note(14850, 1));
                notes.add(new Note(14950, 0));
                notes.add(new Note(15050, 0));
                notes.add(new Note(15150, 1));
                notes.add(new Note(15250, 1));
                notes.add(new Note(15350, 0));
                notes.add(new Note(15450, 0));
                notes.add(new Note(15550, 1));
                notes.add(new Note(15650, 1));
                notes.add(new Note(15750, 0));
                notes.add(new Note(15850, 0));
                notes.add(new Note(15875, 1));
                notes.add(new Note(15900, 1));
                notes.add(new Note(16000, 0));
                notes.add(new Note(16100, 0));
                notes.add(new Note(16200, 1));
                notes.add(new Note(16300, 1));
                notes.add(new Note(16400, 0));
                notes.add(new Note(16500, 1));
                notes.add(new Note(16600, 0));
                notes.add(new Note(16700, 0));
                notes.add(new Note(16800, 1));
                notes.add(new Note(16900, 1));
                notes.add(new Note(17000, 0));
                notes.add(new Note(17100, 0));
                notes.add(new Note(17200, 1));
                notes.add(new Note(17300, 0));
                notes.add(new Note(17400, 1));
                notes.add(new Note(17500, 0));
                notes.add(new Note(17525, 1));
                notes.add(new Note(17550, 0));
                notes.add(new Note(17650, 1));
                notes.add(new Note(17750, 1));
                notes.add(new Note(17825, 0));
                notes.add(new Note(17900, 0));
                notes.add(new Note(18000, 1));
                notes.add(new Note(18100, 0));
                notes.add(new Note(18125, 1));
                notes.add(new Note(18150, 0));
                notes.add(new Note(18250, 1));
                notes.add(new Note(18350, 1));
                notes.add(new Note(18400, 0));
                notes.add(new Note(18450, 0));
                notes.add(new Note(18525, 1));
                notes.add(new Note(18600, 0));
                notes.add(new Note(18625, 1));
                notes.add(new Note(18650, 0));
                notes.add(new Note(18725, 1));
                notes.add(new Note(18800, 1));
                notes.add(new Note(18875, 0));
                notes.add(new Note(18950, 1));
                notes.add(new Note(19025, 0));
                notes.add(new Note(19100, 1));
                notes.add(new Note(19175, 0));
                notes.add(new Note(19250, 1));
                notes.add(new Note(19325, 0));
                notes.add(new Note(19400, 1));
                notes.add(new Note(19475, 0));
                notes.add(new Note(19550, 0));
                notes.add(new Note(19625, 1));
                notes.add(new Note(19700, 0));
                notes.add(new Note(19775, 1));
                notes.add(new Note(19850, 0));
                notes.add(new Note(19925, 1));
                notes.add(new Note(20000, 0));
                notes.add(new Note(20075, 1));
                notes.add(new Note(20150, 0));
                notes.add(new Note(20225, 1));
                notes.add(new Note(20300, 0));
                notes.add(new Note(20375, 1));
                notes.add(new Note(20450, 0));
                notes.add(new Note(20525, 1));
                notes.add(new Note(20600, 0));
                notes.add(new Note(20675, 1));
                notes.add(new Note(20750, 0));
                notes.add(new Note(20825, 1));
                notes.add(new Note(20900, 1));
                notes.add(new Note(20975, 0));
                notes.add(new Note(21050, 0));
                notes.add(new Note(21125, 1));
                notes.add(new Note(21200, 1));
                notes.add(new Note(21275, 0));
                notes.add(new Note(21350, 0));
                notes.add(new Note(21425, 1));
                notes.add(new Note(21500, 1));
                notes.add(new Note(21575, 0));
                notes.add(new Note(21650, 0));
                notes.add(new Note(21725, 1));
                notes.add(new Note(21800, 1));
                notes.add(new Note(21875, 0));
                notes.add(new Note(21950, 0));
                notes.add(new Note(22025, 1));
                notes.add(new Note(22100, 1));
                notes.add(new Note(22175, 0));
                notes.add(new Note(22250, 0));
                notes.add(new Note(22325, 1));
                notes.add(new Note(22400, 1));
                notes.add(new Note(22475, 0));
                notes.add(new Note(22550, 0));
                notes.add(new Note(22625, 1));
                notes.add(new Note(22700, 1));
                notes.add(new Note(22775, 0));
                notes.add(new Note(22850, 0));
                notes.add(new Note(22925, 1));
                notes.add(new Note(23000, 1));
                notes.add(new Note(23075, 0));
                notes.add(new Note(23150, 0));
                notes.add(new Note(23225, 1));
                notes.add(new Note(23300, 1));
                notes.add(new Note(23375, 0));
                notes.add(new Note(23450, 1));
                notes.add(new Note(23525, 0));
                notes.add(new Note(23600, 0));
                notes.add(new Note(23675, 1));
                notes.add(new Note(23750, 1));
                notes.add(new Note(23825, 0));
                notes.add(new Note(23900, 0));
                notes.add(new Note(23975, 1));
                notes.add(new Note(24050, 1));
                notes.add(new Note(24125, 0));
                notes.add(new Note(24200, 1));
                notes.add(new Note(24275, 0));
                notes.add(new Note(24350, 0));
                notes.add(new Note(24425, 1));
                notes.add(new Note(24500, 0));
                notes.add(new Note(24575, 1));
                notes.add(new Note(24650, 1));
                notes.add(new Note(24725, 0));
                notes.add(new Note(24800, 0));
                notes.add(new Note(24875, 1));
                notes.add(new Note(24950, 1));
                notes.add(new Note(25025, 0));
                notes.add(new Note(25100, 0));
                notes.add(new Note(25175, 1));
                notes.add(new Note(25250, 1));
                notes.add(new Note(25325, 0));
                notes.add(new Note(25400, 0));
                notes.add(new Note(25475, 1));
                notes.add(new Note(25550, 1));
                notes.add(new Note(25625, 0));
                notes.add(new Note(25700, 0));
                notes.add(new Note(25775, 1));
                notes.add(new Note(25850, 1));
                notes.add(new Note(25925, 0));
                notes.add(new Note(26000, 1));
                notes.add(new Note(26075, 0));
                notes.add(new Note(26150, 0));
                notes.add(new Note(26225, 1));
                notes.add(new Note(26300, 1));
                notes.add(new Note(26375, 0));
                notes.add(new Note(26450, 0));
                notes.add(new Note(26525, 1));
                notes.add(new Note(26600, 1));
                notes.add(new Note(26675, 0));
                notes.add(new Note(26750, 1));
                notes.add(new Note(26825, 0));
                notes.add(new Note(26900, 0));
                notes.add(new Note(26975, 1));
                notes.add(new Note(27050, 0));
                notes.add(new Note(27125, 1));
                notes.add(new Note(27200, 1));
                notes.add(new Note(27275, 0));
                notes.add(new Note(27350, 0));
                notes.add(new Note(27425, 1));
                notes.add(new Note(27500, 1));
                notes.add(new Note(27575, 0));
                notes.add(new Note(27650, 1));
                notes.add(new Note(27725, 0));
                notes.add(new Note(27800, 0));
                notes.add(new Note(27875, 1));
                notes.add(new Note(27950, 1));
                notes.add(new Note(28025, 0));
                notes.add(new Note(28100, 0));
                notes.add(new Note(28175, 1));
                notes.add(new Note(28250, 0));
                notes.add(new Note(28325, 1));
                notes.add(new Note(28400, 1));
                notes.add(new Note(28475, 0));
                notes.add(new Note(28550, 1));
                notes.add(new Note(28625, 0));
                notes.add(new Note(28700, 0));
                notes.add(new Note(28775, 1));
                notes.add(new Note(28850, 1));
                notes.add(new Note(28925, 0));
                notes.add(new Note(29000, 0));
                notes.add(new Note(29075, 1));
                notes.add(new Note(29150, 1));
                notes.add(new Note(29225, 0));
                notes.add(new Note(29300, 0));
                notes.add(new Note(29375, 1));
                notes.add(new Note(29450, 1));
                notes.add(new Note(29525, 0));
                notes.add(new Note(29600, 0));
                notes.add(new Note(29675, 1));
                notes.add(new Note(29750, 1));
                notes.add(new Note(29825, 0));
                notes.add(new Note(29900, 0));
                notes.add(new Note(29975, 1));
                notes.add(new Note(30050, 1));
                notes.add(new Note(30125, 0));
                notes.add(new Note(30200, 1));
                notes.add(new Note(30275, 0));
                notes.add(new Note(30350, 0));
                notes.add(new Note(30425, 1));
                notes.add(new Note(30500, 1));
                notes.add(new Note(30575, 0));
                notes.add(new Note(30650, 0));
                notes.add(new Note(30725, 1));
                notes.add(new Note(30800, 0));
                notes.add(new Note(30875, 1));
                notes.add(new Note(30950, 1));
                notes.add(new Note(31025, 0));
                notes.add(new Note(31100, 1));
                notes.add(new Note(31175, 0));
                notes.add(new Note(31250, 0));
                notes.add(new Note(31325, 1));
                notes.add(new Note(31400, 1));
                notes.add(new Note(31475, 0));
                notes.add(new Note(31550, 0));
                notes.add(new Note(31625, 1));
                notes.add(new Note(31700, 1));
                notes.add(new Note(31775, 0));
                notes.add(new Note(31850, 0));
                notes.add(new Note(31925, 1));
                notes.add(new Note(32000, 1));
                notes.add(new Note(32075, 0));
                notes.add(new Note(32150, 0));
                notes.add(new Note(32225, 1));
                notes.add(new Note(32300, 1));
                notes.add(new Note(32375, 0));
                notes.add(new Note(32450, 1));
                notes.add(new Note(32525, 0));
                notes.add(new Note(32600, 0));
                notes.add(new Note(32675, 1));
                notes.add(new Note(32750, 1));
                notes.add(new Note(32825, 0));
                notes.add(new Note(32900, 0));
                notes.add(new Note(32975, 1));
                notes.add(new Note(33050, 1));
                notes.add(new Note(33125, 0));
                notes.add(new Note(33200, 0));
                notes.add(new Note(33275, 1));
                notes.add(new Note(33350, 1));
                notes.add(new Note(33425, 0));
                notes.add(new Note(33500, 0));
                notes.add(new Note(33575, 1));
                notes.add(new Note(33650, 1));
                notes.add(new Note(33725, 0));
                notes.add(new Note(33800, 1));
                notes.add(new Note(33875, 0));
                notes.add(new Note(33950, 0));
                notes.add(new Note(34025, 1));
                notes.add(new Note(34100, 1));
                notes.add(new Note(34175, 0));
                notes.add(new Note(34250, 0));
                notes.add(new Note(34325, 1));
                notes.add(new Note(34400, 1));
                notes.add(new Note(34475, 0));
                notes.add(new Note(34550, 0));
                notes.add(new Note(34625, 1));
                notes.add(new Note(34700, 1));
                notes.add(new Note(34775, 0));
                notes.add(new Note(34850, 1));
                notes.add(new Note(34925, 0));
                notes.add(new Note(35000, 0));
                notes.add(new Note(35075, 1));
                notes.add(new Note(35150, 1));
                notes.add(new Note(35225, 0));
                notes.add(new Note(35300, 0));
                notes.add(new Note(35375, 1));
                notes.add(new Note(35450, 1));
                notes.add(new Note(35525, 0));
                notes.add(new Note(35600, 0));
                notes.add(new Note(35675, 1));
                notes.add(new Note(35750, 1));
                notes.add(new Note(35825, 0));
                notes.add(new Note(35900, 0));
                notes.add(new Note(35975, 1));
                notes.add(new Note(36050, 1));
                notes.add(new Note(36125, 0));
                notes.add(new Note(36200, 0));
                notes.add(new Note(36275, 1));
                notes.add(new Note(36350, 1));
                notes.add(new Note(36425, 0));
                notes.add(new Note(36500, 1));
                notes.add(new Note(36575, 0));
                notes.add(new Note(36650, 1));
                notes.add(new Note(36725, 0));
                notes.add(new Note(36800, 0));
                notes.add(new Note(36875, 1));
                notes.add(new Note(36950, 1));
                notes.add(new Note(37025, 0));
                notes.add(new Note(37100, 0));
                notes.add(new Note(37175, 1));
                notes.add(new Note(37250, 0));
                notes.add(new Note(37325, 1));
                notes.add(new Note(37400, 1));
                notes.add(new Note(37475, 0));
                notes.add(new Note(37550, 0));
                notes.add(new Note(37625, 1));
                notes.add(new Note(37700, 0));
            }

            addKeyListener(this);
            setFocusable(true);
            // note speed - not defined at the top for clarity 
            float noteSpeed = 400.0f; 
            long[] lastTime = {System.nanoTime()};
            // I got help from my friend here, he showed me how to use deltatime. I previously had it just run every frame which made it very inconsistent. 
            Timer timer = new Timer(16, e -> {
                long now = System.nanoTime();
                float deltaTime = (now - lastTime[0]) / 1_000_000_000.0f;
                lastTime[0] = now;

                for (Note note : notes) {
                    note.x -= noteSpeed * deltaTime;
                }
                repaint();
            });
            timer.start();
}

        public void keyPressed(KeyEvent e) {
            // ends if there are no more notes 
            if(notes.isEmpty()) {
                return;
            }
            Note firstNote = notes.get(0);
            int key = e.getKeyCode();

            // checks for correct input 
            boolean isCorrectKey = (key == KeyEvent.VK_K && firstNote.type == 1) || (key == KeyEvent.VK_D && firstNote.type == 0);
            if (key == KeyEvent.VK_K && firstNote.type == 1) {
                isKatsu = true;
            } else if (key == KeyEvent.VK_D && firstNote.type == 0) {
                isDon = true;
            }
           // if input is correct play the corresponding sound 
            if (isKatsu == true) {
                new Music().play("heckintaikoyo/katsu-101soundboards.wav");
                isKatsu = false;
            } else if (isDon == true) { 
                new Music().play("heckintaikoyo/don-101soundboards.wav");
                isDon = false;
            }
            // distance from hitzone 
            int distance = Math.abs(firstNote.x - hitZoneX);

            // only judge and remove the note if it is in or past the hit zone
            if (firstNote.x <= hitZoneX + 40) { // 40 is the largest hit window
                if   (isCorrectKey) {
                    if (distance <= 7) {
                        message = "Perfect!";
                        score += 50;
                        combo++;
                        numPerfect++;
                    } else if (distance <= 25)  {
                        message = "Great!";
                        score += 25;
                        combo++;
                        numGreat++;
                    } else if (distance <= 40) {
                        message = "Ok";
                        score += 10;
                        combo++;
                        numOk++;
                    } else {
                        message = "Miss";
                        combo = 0;
                        numMisses++;
                    }
                } else {
                    message = "Miss";
                    combo = 0;
                    numMisses++;
                }
                notes.remove(0); // remove the note after judging
            } else {
                // ff the note hasn't reached the hit window, it miss
                message = "Too early!";
                numMisses++;
            }
            repaint();
        }
        public void keyReleased(KeyEvent e) {
            // Not used
        }
        public void keyTyped(KeyEvent e) {
            // Not used
        } // (must be declared because otehrwise there will be issues)
        


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Background
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());

            // Hit zone
            g.setColor(Color.GRAY);
            g.fillRect(hitZoneX - 10, 200, 32, 32);

            // Draw notes
            for (Note note : notes) {
            Image noteImg = note.type == 0 ? don : katsu;
            g.drawImage(noteImg, note.x, 200, 32, 32, this); 
    }
            // Draws the score and the combo 
            g.drawString("Score: " + score, 10, 20);
            g.drawString("Combo: " + combo, 10, 40);    
            g.drawString(message, 10, 190);

            if(!notes.isEmpty()) { // removes notes if they are missed 
                Note firstNote = notes.get(0); 
                if(firstNote.x < hitZoneX - 40) {
                    notes.remove(0);
                    message = "miss"; 
                    combo = 0;
                }
            }
            // checks if the notes array is empty and ends the level 
            if(notes.isEmpty()) {
                levelEnded = true;
                repaint();
            }

            if (levelEnded == true) {
                playAgainButton.setVisible(true);
                frame.repaint();
            }
            // stats at the end 
            if (levelEnded == true) {
                g.setColor(new Color(0,0,0,180));
                g.fillRect(100, 100, 400, 250);
                g.setColor(Color.WHITE);
                g.drawString("RESULTS:", 150, 130);
                g.drawString("Perfect: " + numPerfect, 150, 170);
                g.drawString("Great: " + numGreat, 150, 190);
                g.drawString("Ok: " + numOk, 150, 210);
                g.drawString("Miss: " + numMisses, 150, 230);
                g.drawString("Score: " + score, 150, 250);

                if (score >= 9000) {
                    rank = "S";} else if (score >= 7000) {
                        rank = "A";} else if (score >= 5000) {
                            rank = "B";} else if (score >= 4000) {
                                rank = "C";} else {
                                    rank = "F";
                                }

                g.drawString("Rank: " + rank, 150, 270);

                // Pass/Fail pass if score is above certain amount 
                if (score >= 4000) {
                    passed = true;
                    g.drawString("PASS!", 150, 290);
                } else {
                    passed = false;
                    g.drawString("FAIL!", 150, 290);
                }   

            }

        }
    }

    
}