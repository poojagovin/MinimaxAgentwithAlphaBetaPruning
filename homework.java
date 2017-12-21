package homework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;

class State
{
	int row, column;
	public int n;
	int humanScore;
	int aiScore;
	String player;
	public int arr[][] = new int[n][n];
	public int score;

	public void setLocation(int x, int y)
	{
		this.row = x;
		this.column = y;
	}

	public void setHumanScore( int x)
	{
		this.humanScore =x;
	}
	public void setAIScore( int x)
	{
		this.aiScore =x;
	}

	public void setPlayer(String s)
	{
		this.player = s;
	}

	public void setArray( int size, int a[][])
	{
		n = size;
		for(int i=0;i<n; i++)
			for(int j=0;j<n;j++)
				arr[i][j]= a[i][j];
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public int getHumanScore() {
		return humanScore;
	}

	public int getAiScore() {
		return aiScore;
	}

	public String getPlayer() {
		return player;
	}

	public void setN(int n2) {
		this.n = n2;
		this.arr = new int[n2][n2];

	}
}

public class homework {


	private static State greedyMove(int[][] arr, int n) {

		int star[][] = new int[n][n];

		int maxI=0, maxJ=0, count=0,maxCount=0;
		State s = new State();
		s.setN(n);
		int chosen;

		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
			{	
				chosen = arr[i][j];
				if (chosen == -1) continue;
				star[i][j]= -1;
				int starI=i, starJ=j;
				while(starJ>=0 && arr[starI][starJ]==chosen) { star[starI][starJ] = -1; starJ--;}
				starJ=j;
				while(starJ<n && arr[starI][starJ]==chosen) { star[starI][starJ] = -1;starJ++;}
				starJ=j;
				while(starI>=0 && arr[starI][starJ]==chosen) { star[starI][starJ] = -1; starI--;}
				starI=i;
				while(starI<n && arr[starI][starJ]==chosen) { star[starI][starJ] = -1; starI++; }
				starI=i;


				boolean starsBeenAdded = true;
				while(starsBeenAdded)
				{
					starsBeenAdded = false;
					for(int presI=0;presI<n;presI++)
					{
						for(int presJ=0;presJ<n;presJ++)
						{
							if(star[presI][presJ]==-1)
							{
								starI=presI;
								starJ= presJ;
								while(starI>=0 && arr[starI][starJ]==chosen) { if(star[starI][starJ]!=-1) starsBeenAdded = true; star[starI][starJ] = -1; starI--;}
								starI=presI;
								while(starI<n && arr[starI][starJ]==chosen) { if(star[starI][starJ]!=-1) starsBeenAdded = true; star[starI][starJ] = -1; starI++; }
								starI=presI;
								while(starJ>=0 && arr[starI][starJ]==chosen) { if(star[starI][starJ]!=-1) starsBeenAdded = true; star[starI][starJ] = -1; starJ--;}
								starJ=presJ;
								while(starJ<n && arr[starI][starJ]==chosen) { if(star[starI][starJ]!=-1) starsBeenAdded = true; star[starI][starJ] = -1;starJ++;}
								starJ=presJ;
							}
						}
					}
				}

				count=0;
				for(starI=0;starI<n;starI++)
				{
					for(starJ=0;starJ<n;starJ++)
					{
						if(star[starI][starJ]==-1) count++;
					}
				}
				if(count>maxCount) 
				{
					maxCount = count;
					maxI=i;
					maxJ=j;

					for(starI=0;starI<n;starI++)
					{
						for(starJ=0;starJ<n;starJ++)
						{
							if(star[starI][starJ]==-1) s.arr[starI][starJ]=-1;
							else s.arr[starI][starJ] = arr[starI][starJ];
						}
					}
				}
				star= new int[n][n];

			}

		s.setLocation(maxI, maxJ);
		s.setHumanScore(0);
		s.setAIScore(0);


		for(int i=n-1;i>=0;i--)
		{
			for(int j=n-1;j>=0;j--)
			{
				if(s.arr[i][j]==-1)
				{
					int iter=i, loop=i;
					while(iter>=0 && s.arr[iter][j]==-1) 
					{
						iter--;
					}
					while(iter>=0 && loop>=0)
					{
						s.arr[loop][j]=s.arr[iter][j];
						loop--; iter--;
					}
					while(loop>=0)
					{
						s.arr[loop][j]= -1; loop--;
					}
				}
			}
		}


		return s;

	}

	private static int Move(int[][] arr, int[][] group, int n, int i, int j, int groupNumber) {

		int star[][] = new int[n][n];
		int chosen = arr[i][j];
		star[i][j]= -1;
		int starI=i, starJ=j;
		while(starJ>=0 && arr[starI][starJ]==chosen) { star[starI][starJ] = -1;group[starI][starJ] = groupNumber; starJ--;}
		starJ=j;
		while(starJ<n && arr[starI][starJ]==chosen) { star[starI][starJ] = -1;group[starI][starJ] = groupNumber; starJ++;}
		starJ=j;
		while(starI>=0 && arr[starI][starJ]==chosen) { star[starI][starJ] = -1; group[starI][starJ] = groupNumber; starI--;}
		starI=i;
		while(starI<n && arr[starI][starJ]==chosen) { star[starI][starJ] = -1; group[starI][starJ] = groupNumber; starI++; }
		starI=i;


		boolean starsBeenAdded = true;
		int count=0;
		while(starsBeenAdded)
		{
			starsBeenAdded = false;
			for(int presI=0;presI<n;presI++)
			{
				for(int presJ=0;presJ<n;presJ++)
				{
					if(star[presI][presJ]==-1)
					{
						starI=presI;
						starJ= presJ;
						while(starI>=0 && arr[starI][starJ]==chosen) { if(star[starI][starJ]!=-1) { starsBeenAdded = true; star[starI][starJ] = -1; group[starI][starJ] = groupNumber;} starI--;}
						starI=presI;
						while(starI<n && arr[starI][starJ]==chosen) { if(star[starI][starJ]!=-1) { starsBeenAdded = true; star[starI][starJ] = -1; group[starI][starJ] = groupNumber;} starI++; }
						starI=presI;
						while(starJ>=0 && arr[starI][starJ]==chosen) { if(star[starI][starJ]!=-1) { starsBeenAdded = true; star[starI][starJ] = -1; group[starI][starJ] = groupNumber;} starJ--;}
						starJ=presJ;
						while(starJ<n && arr[starI][starJ]==chosen) { if(star[starI][starJ]!=-1) { starsBeenAdded = true; star[starI][starJ] = -1; group[starI][starJ] = groupNumber;}starJ++;}
						starJ=presJ;
					}
				}
			}
		}

		for(starI=0;starI<n;starI++)
		{
			for(starJ=0;starJ<n;starJ++)
			{
				if(star[starI][starJ]==-1) 
				{
					count++;
				}
			}
		}
		return count*count;
	}

	private static void percolate (int[][] a,int group[][], int n, int groupNumber )
	{
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(group[i][j]==groupNumber)
				{
					a[i][j]=-1;
				}
			}
		}

		for(int i=n-1;i>=0;i--)
		{
			for(int j=n-1;j>=0;j--)
			{
				if(a[i][j]==-1)
				{
					int iter=i, loop=i;
					while(iter>=0 && a[iter][j]==-1) 
					{
						iter--;
					}
					while(iter>=0 && loop>=0)
					{
						a[loop][j]=a[iter][j];
						loop--; iter--;
					}
					while(loop>=0)
					{
						a[loop][j]= -1; loop--;
					}
				}
			}
		}
	}

	private static boolean allNotGrouped(int n, int[][] group) {

		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(group[i][j]==0) return true;
			}
		}
		return false;
	}

	private static State minimax(int[][] arr,int n, String player, int level, int numbers, int alpha,int  beta) 
	{
		int numberOfStars=0;
		boolean firstFruit=true; int startI=-1,startJ=-1;
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(arr[i][j]==-1) numberOfStars++;
				else
				{
					if(firstFruit)
					{
						startI=i;
						startJ=j;
						firstFruit=false;
					}
				}
			}
		}

		State s = new State();
		s.setPlayer(player);
		s.setN(n);

		int[][] group = new int[n][n];
		int groupNumber =1;
		int compare[][] = new int[n*n][2];
		int compareI=0;
		int rowcol[][] = new int[n*n][2];

		int val = Move(arr, group, n, startI, startJ, groupNumber);
		compare[compareI][0]=1; compare[compareI][1]=val; compareI++;
		rowcol[groupNumber][0] = startI;
		rowcol[groupNumber][1] = startJ;



		//winning
		if(Math.sqrt(val)+numberOfStars == n*n)
		{
			if(player.equalsIgnoreCase("ai"))
			{
				s.setAIScore(val);
				s.setHumanScore(0);
			}
			else 
			{
				s.setHumanScore(val);
				s.setAIScore(0);
			}
			
			int a[][] = new int[n][n];
			for(int k=0;k<n;k++)
				for(int j=0;j<n;j++)
					a[k][j]= arr[k][j];
			
			percolate(a, group, n, compare[0][0]);
			
			for(int k=0;k<n;k++)
				for(int j=0;j<n;j++)
					s.arr[k][j] = a[k][j];
			
			s.setLocation(rowcol[groupNumber][0], rowcol[groupNumber][1] );
			s.score = val;
			return s;
		}

		//level 0
		if(level ==0)
		{
			//s = greedyMove(arr, n, player);
			s.score = 0;
			return s;
		}


		while(allNotGrouped(n, group))
		{
			for(int i=0;i<n;i++)
			{
				for(int j=0;j<n;j++)
				{
					if(arr[i][j]==-1) { group[i][j]=-1; }
					if(group[i][j]==0)
					{
						groupNumber++;
						val = Move(arr, group, n, i, j, groupNumber);
						compare[compareI][0]=groupNumber; compare[compareI][1]=val; compareI++;
						rowcol[groupNumber][0] = i;
						rowcol[groupNumber][1] = j;
					}
				}
			}
		}

		for (int i = 0; i < compareI; i++)
			for (int j = 0; j < compareI-i-1; j++)
				if (compare[j][1] < compare[j+1][1])
				{
					int temp1 = compare[j][1];
					int temp0 = compare[j][0];

					compare[j][1] = compare[j+1][1];
					compare[j][0] = compare[j+1][0];

					compare[j+1][1] = temp1;
					compare[j+1][0] = temp0;

				}

		State maxState= new State();
		maxState.setN(n);

		if(player.equalsIgnoreCase("human"))
		{
			int i=0;
			while(compare[i][0]>0)
			{
				int a[][] = new int[n][n];
				for(int k=0;k<n;k++)
					for(int j=0;j<n;j++)
						a[k][j]= arr[k][j];
				
				percolate(a, group, n, compare[i][0]);
				State minimaxValue = minimax(a, n, "ai", level-1, numbers, alpha, beta);

				if(minimaxValue.score +compare[i][1] > alpha) 
				{
					alpha = compare[i][1]+ minimaxValue.score;
					maxState.setAIScore( minimaxValue.getAiScore());
					maxState.setHumanScore(compare[i][1]+ minimaxValue.getHumanScore());
					maxState.score = maxState.getHumanScore()- maxState.getAiScore();
					maxState.setLocation(rowcol[compare[i][0]][0], rowcol[compare[i][0]][1] );

					for(int l=0;l<n;l++)
					{
						for(int m=0;m<n;m++)
						{
							maxState.arr[l][m] = a[l][m];
						}
					}
				}
				if(alpha>= beta) 
				{
					break;
				}

				i++;
			}
		}

		else if(player.equalsIgnoreCase("ai"))
		{
			int i=0;
			while(compare[i][0]>0)
			{
				int a[][] = new int[n][n];
				for(int k=0;k<n;k++)
					for(int j=0;j<n;j++)
						a[k][j]= arr[k][j];
				percolate(a, group, n, compare[i][0]);
				State minimaxValue = minimax(a, n, "human", level-1, numbers, alpha, beta);
				
				if(minimaxValue.score -compare[i][1] < beta) 
				{
					beta = minimaxValue.score-compare[i][1];
					maxState.setAIScore(compare[i][1]+ minimaxValue.getAiScore());
					maxState.setHumanScore( minimaxValue.getHumanScore());
					maxState.score = maxState.getHumanScore()- maxState.getAiScore();
					maxState.setLocation(rowcol[compare[i][0]][0], rowcol[compare[i][0]][1] );
					for(int l=0;l<n;l++)
					{
						for(int m=0;m<n;m++)
						{
							maxState.arr[l][m] = a[l][m];
						}
					}

				}
				if(alpha>= beta) 
				{
					break;
				}
				i++;
			}
		}

		maxState.score= player.equalsIgnoreCase("human")? alpha : beta;
		return maxState;


	}




	public static void main(String[] args) {

		int n=0,p;
		float time;
		int level=500, numbers=0; // change level here please
		String fileName = "input.txt";
		String line = null;
		File f = new File("output.txt");
		FileOutputStream fos;
		try 
		{

			try 
			{
				fos = new FileOutputStream(f);
				PrintWriter pw = new PrintWriter(fos);
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);

				line = bufferedReader.readLine().trim();
				n = Integer.parseInt(line);
				int arr[][] = new int[n][n];

				line = bufferedReader.readLine().trim();
				p = Integer.parseInt(line);

				line = bufferedReader.readLine().trim();
				time = Float.parseFloat(line);

				for(int i=0;i<n;i++)
				{
					line = bufferedReader.readLine().trim();
					char[] a = line.toCharArray();
					for(int j=0;j<n;j++)
					{
						if(a[j]!='*')
						{
							arr[i][j] = a[j]-'0';
							numbers++;
						}
						else arr[i][j]=-1;

					}
				}

				bufferedReader.close();    
				State s = new State();
				if(level == 0)
				{
						s = greedyMove(arr,n);
				}

				else {
				long curr = System.currentTimeMillis();

				s= minimax(arr,n, "human", level, numbers, Integer.MIN_VALUE, Integer.MAX_VALUE );
				long fi = System.currentTimeMillis();
				
				long val= fi-curr;
				System.out.println(val);

				}
				StringBuilder outputLine1 = new StringBuilder();
				outputLine1.append((char)(s.column+'A'));
				outputLine1.append(s.row+1);
				outputLine1.append("\n");
				String str2 = outputLine1.toString();
				System.out.println(str2);
				pw.write(str2);
				for(int i=0;i<n;i++)
				{
					StringBuilder str = new StringBuilder();
					for(int j=0;j<n;j++)
					{ 
						String c;
						if(s.arr[i][j]==-1) c="*";
						else
						{
							c= String.valueOf(s.arr[i][j]);
						}
						str.append(c);
					}
					str.append("\n");
					str2 = str.toString();
					System.out.println(str2);
					pw.write(str2);
				}

				pw.flush();
				fos.close();
				pw.close();
			}

			catch(Exception ex) 
			{
				StringBuilder outputLine1 = new StringBuilder();
				outputLine1.append(n-1 +'A');
				outputLine1.append(n-1);
				System.out.println(ex.getMessage());
				//pw.write(outputLine1.toString());
				//print output matrix
			}



		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}

	}
}
