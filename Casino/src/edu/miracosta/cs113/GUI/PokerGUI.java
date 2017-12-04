package edu.miracosta.cs113.GUI;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import edu.miracosta.cs113.Card;
import edu.miracosta.cs113.Player;
import edu.miracosta.cs113.Table;

/**
 * The game's main frame.
 * 
 * This is the core class of the Swing UI client application.
 */
public class PokerGUI extends JFrame {

	
    private static final int BIG_BLIND = 10; 
    private static final int START_MONEY = 500;
    private final GridBagConstraints gc;
    private final BoardPanel boardPanel;
    private final ControlPanel controlPanel;
    private final Map<String, PlayerPanel> playerPanels;
    private final Player humanPlayer;
    private String dealerName; 
    private String actorName; 

    /**
     * Constructor.
     */
    public PokerGUI() {
    	
        super("Texas Hold'em poker");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(0, 128, 0)); //Dark green
        setLayout(new GridBagLayout());

        /* The players at the table. */
        Map<String, Player> players = new LinkedHashMap<>();
        humanPlayer = new Player("Player", START_MONEY, this);
        players.put("Player", humanPlayer);
        players.put("Joe", new Player("Joe", START_MONEY, new BasicBot(0, 75)));
        players.put("Mike", new Player("Mike", START_MONEY, new BasicBot(25, 50)));
        players.put("Eddie", new Player("Eddie", START_MONEY, new BasicBot(50, 25)));
        
        // Show the frame.
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        /**
         * Start game
         */
    }
    
    /**
     * The application's entry point.
     * 
     * @param args
     *            The command line arguments.
     */
    public static void main(String[] args) {
        new PokerGUI();
    }
}