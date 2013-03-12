package views.dialog;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.Result;

import dataset.Data;


public class MainPage extends JPanel
{
	private static MainPage instance = null;
	private Result result;
	private Data testData;
	
	private JPanel resultPanel;
	
	public static MainPage getInstance() 
	{
		return instance;
	}
	
	public MainPage()
	{
		instance = this;
		setLayout(null);	
		
		JPanel panel0 = new JPanel();
		panel0.setBounds(46, 22, 80, 30);
		panel0.setBackground(Color.BLACK);
		add(panel0);
		
		JLabel numberLabel = new JLabel("Test");
		numberLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		numberLabel.setForeground(Color.WHITE);
		panel0.add(numberLabel);
		
		JPanel panel1 = new JPanel();
		panel1.setBounds(127, 22, 186, 30);
		panel1.setBackground(Color.BLACK);
		add(panel1);
		
		JLabel label1 = new JLabel("Input");
		label1.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label1.setForeground(Color.WHITE);
		panel1.add(label1);
		
		JPanel panel2 = new JPanel();
		panel2.setBounds(314, 22, 95, 30);
		panel2.setBackground(Color.BLACK);
		add(panel2);
		
		JLabel label2 = new JLabel("Expected");
		label2.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label2.setForeground(Color.WHITE);
		panel2.add(label2);
		
		JPanel panel3 = new JPanel();
		panel3.setBounds(410, 22, 95, 30);
		panel3.setBackground(Color.BLACK);
		add(panel3);
		
		JLabel label3 = new JLabel("Actual");
		label3.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label3.setForeground(Color.WHITE);
		panel3.add(label3);
		
		JPanel linePanel = new JPanel();
		linePanel.setBounds(46, 52, 400, 2);
		linePanel.setOpaque(false);
		//add(linePanel);
		
		resultPanel = new JPanel();
		//resultPanel.setOpaque(false);
		resultPanel.setLayout(null);
		resultPanel.setBounds(46, 55, 500, 310);
		resultPanel.setOpaque(false);
		add(resultPanel);

	}
	
	public void showTable(Data testData, Result result) 
	{
		this.result = result;
		this.testData = testData;
		
		PagerPanel pagerPanel = new PagerPanel();
		pagerPanel.setBounds(46, 370, 460, 30);
		pagerPanel.setOpaque(false);
		add(pagerPanel);
		
		int numberOfPages = (int)(result.size()/10)+1;
		if(result.size()%10 == 0)
			numberOfPages--;
		pagerPanel.setPager(numberOfPages);
		setPage(1);
	}

	public void setPage(int currentPage) 
	{
		resultPanel.removeAll();
		
		int firstIndex = (currentPage-1)*10;
		
		for( int i = firstIndex, y = 0; i < firstIndex + 10 && i < result.size(); i++, y += 31 ) {
			Entry e = new Entry(i+1, testData.getFilename(i), testData.getFilename2(i), result.getExpected(i), result.getActual(i));
			e.setBounds(0, y, 500, 30);
			resultPanel.add(e);
		}
		updateUI();
	}

}
