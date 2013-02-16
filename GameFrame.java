import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GameFrame extends JFrame {
	private JButton[] _blocks;
	private HashMap<String,ImageIcon> _imgs;
	private Player[] _players;
	private int _currentPlayer;
	private boolean _gameOver;
	private Random _r;
	private JButton _playAgainButton;
	private JLabel _p1ScoreLabel;
	private JLabel _p2ScoreLabel;
	
	public static void main(String[] args){
		GameFrame game = new GameFrame();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.getContentPane().setPreferredSize(new Dimension(140,250));
		game.setVisible(true);
		game.pack();
		game.setResizable(false);
	}
	
	public GameFrame(){
		super("XOX");
		
		_gameOver = false;
		
		_imgs = new HashMap<String,ImageIcon>();
		//loading our images
		try {
			Image img = ImageIO.read(getClass().getResource("X.png"));
			_imgs.put("Cross", new ImageIcon(img));
			img = ImageIO.read(getClass().getResource("O.png"));
			_imgs.put("Circle", new ImageIcon(img));
			img = ImageIO.read(getClass().getResource("Blank.png"));
			_imgs.put("Blank", new ImageIcon(img));
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Images Could not be loaded");
			System.exit(1);
		}

		_blocks = new JButton[9];
		Dimension buttonPrefferedSize = new Dimension(50,50);
		
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(3,3));
		
		for(int i=0;i<9;i++){
			_blocks[i] = new JButton();
			_blocks[i].setPreferredSize(buttonPrefferedSize);
			_blocks[i].setIcon(_imgs.get("Blank"));
			boardPanel.add(_blocks[i]);
			MoveListener moveListener = new MoveListener();//when we make move we click on button tht listens
			_blocks[i].addActionListener(moveListener);
		}
		
		_players = new Player[2];
		_players[0] = new Player(_imgs.get("Cross"));
		_players[1] = new Player(_imgs.get("Circle"));
		
		_r = new Random();
		_currentPlayer = _r.nextInt(2);
		
		
		add(boardPanel,BorderLayout.NORTH);
		
		JPanel bottomPanel = new JPanel();
		
		
		JPanel scoreGrid = new JPanel();
		scoreGrid.setLayout(new GridLayout(2,2,20,0));
		scoreGrid.add(new JLabel("Player 1"));
		scoreGrid.add(new JLabel("Player 2"));
		_p1ScoreLabel = new JLabel("0");
		_p2ScoreLabel = new JLabel("0");
		scoreGrid.add(_p1ScoreLabel);
		scoreGrid.add(_p2ScoreLabel);
		bottomPanel.add(scoreGrid);
		
		_playAgainButton = new JButton("Play Again?");
		_playAgainButton.setVisible(false);//will become visible when game ends
		_playAgainButton.addActionListener(new ResetListener());
		bottomPanel.add(_playAgainButton);
		
		add(bottomPanel);
		
		//set Menu Bar
		JMenuBar bar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(GameFrame.this, "This is a TIC-TAC-TOE game that was created by Harinder Gill on 16/02/13.",
						"About",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		fileMenu.add(aboutItem);
		fileMenu.add(exitItem);
		bar.add(fileMenu);
		setJMenuBar(bar);
	}
	
	

	
	public void makeMove(JButton b){
		b.setIcon(_players[_currentPlayer].getMark());//make move on button
		b.setEnabled(false);
		
	}
	
	public void updateScore(){
		_players[_currentPlayer].addWinCount();
		if(_currentPlayer == 0)
			_p1ScoreLabel.setText(Integer.toString(_players[_currentPlayer].getNumOfWins()));
		else
			_p2ScoreLabel.setText(Integer.toString(_players[_currentPlayer].getNumOfWins()));
	}
	
	public void setVictory(){
		_playAgainButton.setVisible(true);
		updateScore();
		
	}
	
	public boolean isGameOver(){
		ImageIcon currentMark = _players[_currentPlayer].getMark();
		if(_blocks[0].getIcon() == currentMark && _blocks[1].getIcon() == currentMark
				&& _blocks[2].getIcon() == currentMark){
			return true;
		}else if(_blocks[3].getIcon() == currentMark && _blocks[4].getIcon() == currentMark
				&& _blocks[5].getIcon() == currentMark){
			return true;
		}else if(_blocks[6].getIcon() == currentMark && _blocks[7].getIcon() == currentMark
				&& _blocks[8].getIcon() == currentMark){
			return true;
		}else if(_blocks[0].getIcon() == currentMark && _blocks[3].getIcon() == currentMark
				&& _blocks[6].getIcon() == currentMark){
			return true;
		}else if(_blocks[1].getIcon() == currentMark && _blocks[4].getIcon() == currentMark
				&& _blocks[7].getIcon() == currentMark){
			return true;
		}else if(_blocks[2].getIcon() == currentMark && _blocks[5].getIcon() == currentMark
				&& _blocks[8].getIcon() == currentMark){
			return true;
		}else if(_blocks[0].getIcon() == currentMark && _blocks[4].getIcon() == currentMark
				&& _blocks[8].getIcon() == currentMark){
			return true;
		}else if(_blocks[2].getIcon() == currentMark && _blocks[4].getIcon() == currentMark
				&& _blocks[6].getIcon() == currentMark){
			return true;
		}
		return false;
	}
	
	
	public class ResetListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			_currentPlayer = _r.nextInt(2);
			_playAgainButton.setVisible(false);
			for(int i=0;i<9;i++){
				_blocks[i].setIcon(_imgs.get("Blank"));
				_blocks[i].setEnabled(true);
			}
		}
		
	}
	
	public class MoveListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(!_gameOver){
				makeMove((JButton)e.getSource());
				if(isGameOver())//winning move found
					setVictory();//launch winning message
				else
					_currentPlayer = (_currentPlayer + 1) % 2;//swap players turn
			}
		}
		
	}
}
