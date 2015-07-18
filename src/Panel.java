import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Panel extends JFrame{

	private static final String INITIAL_TEXT = "Nothing Pressed";
    private static final String ADDED_TEXT = " was Pressed";
    private JLabel positionLabel;
    private JButton resetButton;
    private AntTsp antTsp;
    private static int gridSize = 10;
    private static int gridCenter =  55;

    public Panel(String fileLocation){
    	antTsp = new AntTsp();    	
    	try {
			antTsp.readGraph(fileLocation);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void createAndDisplayGUI()
    {       
    	JFrame frame = new JFrame("Map");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout((LayoutManager) new FlowLayout(FlowLayout.LEFT, 20, 20));
        contentPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        leftPanel.setLayout((LayoutManager) new BoxLayout(leftPanel, BoxLayout.Y_AXIS));    
        JPanel labelPanel = new JPanel();
        positionLabel = new JLabel(INITIAL_TEXT, JLabel.CENTER);
        JPanel buttonLeftPanel = new JPanel();
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                positionLabel.setText(INITIAL_TEXT);
            }
        });
        labelPanel.add(positionLabel);
        buttonLeftPanel.add(resetButton);
        leftPanel.add(labelPanel);
        leftPanel.add(buttonLeftPanel);

        contentPane.add(leftPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(gridSize, gridSize, 10, 10));
        for (int i = 0; i < gridSize; i++)
        {
            for (int j = 0; j < gridSize; j++)
            {
            	JButton button = new JButton();
                String nodeValue = "" + antTsp.getGraph()[gridCenter][(i * gridSize)+ j];
            	
            	if(nodeValue.equals("1.0E7")) {
            		nodeValue = "pizzeria";
            		button.setBackground(Color.GREEN);
            	} 
                
            	button.setText(nodeValue);
            	button.setActionCommand(nodeValue);
                button.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                        JButton but = (JButton) ae.getSource();                        
                        positionLabel.setText(
                            but.getActionCommand() + ADDED_TEXT);
                        antTsp.solve();
                    }
                });
                buttonPanel.add(button);
            }
        }
        contentPane.add(buttonPanel);

        setContentPane(contentPane);
        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        if (args.length > 0)
        {
            gridSize = Integer.parseInt(args[0]);
        }
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new Panel("/home/cristopherson/workspace/Aco1/bin/tspadata3.txt").createAndDisplayGUI();
            }
        });
    }
}
