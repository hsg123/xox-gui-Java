import javax.swing.ImageIcon;


public class Player {
	private ImageIcon _mark;
	private int _wins;
	
	public Player(ImageIcon mark){
		_mark = mark;
		_wins = 0;
	}
	
	public ImageIcon getMark(){
		return _mark;
	}
	
	public void addWinCount(){
		_wins += 1;
	}
	
	public int getNumOfWins(){
		return _wins;
	}
}
