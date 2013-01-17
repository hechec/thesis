package views.dialog;

public class Pager 
{
	private int currentPage = 1;
	private int numberOfPages;
	
	public Pager(int numberOfPages)
	{
		this.numberOfPages = numberOfPages;
	}
	
	public int next() 
	{
		return ++currentPage;
	}
	
	public int prev()
	{
		return --currentPage;
	}

	public boolean isFirstPage()
	{
		return currentPage == 1;
	}
	
	public boolean isLastPage() 
	{
		return currentPage == numberOfPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}
	
}
