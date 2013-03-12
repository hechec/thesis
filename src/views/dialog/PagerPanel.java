package views.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PagerPanel extends JPanel 
{
	private MainPage mainPage;
	
	private Pager pager;
	private JLabel pageLabel;
	
	private JButton prevButton, nextButton;
	private int numberOfPages;
	
	public PagerPanel()
	{
		mainPage = MainPage.getInstance();
		setLayout(null);
		
		prevButton = new JButton("prev");
		prevButton.setBounds(0, 0, 60, 30);
		add(prevButton);
		prevButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prev();
			}
		});
		
		nextButton = new JButton("next");
		nextButton.setBounds(399, 0, 60, 30);
		add(nextButton);
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		
		pageLabel = new JLabel("");
		pageLabel.setBounds(prevButton.getWidth(), 0, nextButton.getX() - prevButton.getX() - prevButton.getWidth(), 30 );
		pageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pageLabel.setFont(new Font(null, Font.PLAIN, 14));
		pageLabel.setForeground(Color.WHITE);
		add(pageLabel);
		
	}
	
	public void setPager(int numberOfPages)
	{
		this.numberOfPages = numberOfPages;
		prevButton.setEnabled(false);
		nextButton.setEnabled(false);
		pager = new Pager(numberOfPages);
		if(numberOfPages > 1)
			nextButton.setEnabled(true);
		pageLabel.setText("Page 1 of "+numberOfPages);
	}
	
	public void setPageNumber(int pageNumber) 
	{
		pageLabel.setText("Page "+pageNumber+" of "+numberOfPages);
		updateUI();
	}
	
	private void next()
	{
		mainPage.setPage(pager.next());
		setPageNumber(pager.getCurrentPage());
		if(pager.isLastPage()) 
			nextButton.setEnabled(false);
		prevButton.setEnabled(true);
	}
	
	private void prev()
	{
		mainPage.setPage(pager.prev());
		setPageNumber(pager.getCurrentPage());
		if(pager.isFirstPage()) 
			prevButton.setEnabled(false);
		nextButton.setEnabled(true);
	}
	
}
