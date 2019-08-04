import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/*
 * 实现A*算法，F = G + H
 */
public class Astar {
	/*
	 * (sx,sy) is the coordinate of the start Node,(ex,ey) is the coordinate of the end(goal) Node
	 * in the Searched Map, 1 is the path & 0 is the barrier，for example：
	 * 1 1 1 1 1
	 * 1 0 0 1 0
	 * 1 1 1 1 0
	 * 1 1 1 1 1
	 */
	Node [][] map;
	int sx, sy, ex, ey;
	int lengthx, lengthy;
	ArrayList<Node> openList;
	//ArrayList<Node> closeList;
	public Astar(int sx, int sy, int ex, int ey) {
		this.sx = sx;
		this.sy = sy;
		this.ex = ex;
		this.ey = ey;
		openList = new ArrayList<Node>();
		//closeList = new ArrayList<Node>();
	}
	public void init(int lengthx, int lengthy) {
		map = new Node[lengthx + 1][lengthy + 1];
		this.lengthx = lengthx;
		this.lengthy = lengthy;
	}
	public void setNode(int x, int y, int val) {
		map[x][y] = new Node(x,y,val);
	}
	private int calH(int x, int y) {
		return 10*(Math.abs(x-ex)+Math.abs(y-ey));
	}
	public void AstarSearch() {
		//1.把起点加入openList
		if(map[sx][sy].val!=1){
			System.out.println("The start Node value must be 1");
			return;
		}
		openList.add(map[sx][sy]);
		map[sx][sy].H = this.calH(sx, sy);
		map[sx][sy].G = 0;
		map[sx][sy].F = map[sx][sy].H + map[sx][sy].G;
		int Fmin = 2147483647;

		
		//2.重复以下步骤：a.遍历open list查找F最小的节点，视作当前要处理的点
		while(!openList.isEmpty()) {
			int currentNodeIndex = 0; //这里由于openList.remove需要已知的参数，因此要初始化
			for(int i=0; i<openList.size(); i++) {
				if(openList.get(i).F < Fmin) {
					currentNodeIndex = i;
					Fmin = openList.get(i).F;
				}
			}
			Node currentNode = openList.get(currentNodeIndex);
			
			Node nextNode;
			//b.把这个点移到closeList(7.31此时有疑问？移到closeList中去要把它从openList中删除吗？假设删除！
			currentNode.closed = true;
			openList.remove(currentNodeIndex);
			//c.对当前方格的相邻8个方格中的每一个：
			for(int i=-1; i<=1; i++)
				for(int j=-1;j<=1;j++) {
					//在找到一个新的节点的时候应该先判断这个是不是终点
					if(currentNode.x+i == ex && currentNode.y+j==ey) {
						Node current = currentNode;//这两个点用于访问fatherNode输出路径
						Stack<Node> sta = new Stack<Node>();
						while(current.father!=null) {
							 sta.push(current);
							 current = current.father;
						}
						Node outputNode = null;
						System.out.print("("+sx+","+sy+")->");
						while(!sta.isEmpty()) {
							outputNode = sta.pop();
							System.out.print("("+outputNode.x+","+outputNode.y+")->");
						}
						System.out.print("("+ex+","+ey+")");
						return;
					}
					if(currentNode.x+i>0 && currentNode.y+j>0 && currentNode.x+i <= lengthx && currentNode.y+j <=lengthy
							&& map[currentNode.x+i][currentNode.y+j].val ==1 && map[currentNode.x+i][currentNode.y+j].closed==false) {
						//判断下一个点是否超出地图边界或者已经加入closeList或者是不可到达状态。
						nextNode = map[currentNode.x + i][currentNode.y +j];
						if(!openList.contains(nextNode)) {
						/*
						 *在第一种情况下，nextNode尚未被加入到openList，因此G还是初始化的无穷大
						 *可以直接将nextNode的父节点设置为currentNode 
						 */
						openList.add(nextNode);
						nextNode.father = currentNode;
						if(Math.abs(i)+Math.abs(j)==2) {
							//对角线移动的情况
							nextNode.G = currentNode.G+14;
							nextNode.H = 10*(Math.abs(nextNode.x - ex) + Math.abs(nextNode.y - ey));
							nextNode.F = nextNode.G + nextNode.H;
						}
						else {
							//水平（竖直）移动的情况
							nextNode.G = currentNode.G + 10;
							nextNode.H = 10*(Math.abs(nextNode.x - ex) + Math.abs(nextNode.y - ey));
							nextNode.F = nextNode.G + nextNode.H;
						}
						}
						else{
							nextNode = map[currentNode.x + i][currentNode.y +j];
							/*
							 * 在第二种情况下，nextNode已经存在于openList，需要判断是否有比之前
							 * 得到的更短的路径到达nextNode
							 */
							if(Math.abs(i)+Math.abs(j)==2) {
								//对角线移动的情况
								if(currentNode.G + 14 < nextNode.G) {
									nextNode.G = currentNode.G+14;
									nextNode.H = 10*(Math.abs(nextNode.x - ex) + Math.abs(nextNode.y - ey));
									nextNode.F = nextNode.G + nextNode.H;
									nextNode.father = currentNode;
								}
							}
							else {
								//水平（竖直）移动的情况
								if(currentNode.G + 10 < nextNode.G) {
									nextNode.G = currentNode.G+10;
									nextNode.H = 10*(Math.abs(nextNode.x - ex) + Math.abs(nextNode.y - ey));
									nextNode.F = nextNode.G + nextNode.H;
									nextNode.father = currentNode;
								}
							}
						}
					}	
				}
		}
		
		//如果存在openList为空的状态，即Astar搜索无法找到路径，跳出了while loop,警告无路径：
		System.out.println("Warning:There's no path between input Node!");
	}
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		int lengthx = scan.nextInt();
		int lengthy = scan.nextInt();
		Astar as = new Astar(scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt());
		as.init(lengthx, lengthy);
		for(int i=1; i<=lengthx; i++) {
			for(int j=1; j<=lengthy; j++)
				as.setNode(i,j,scan.nextInt());
		}
		scan.close();
		as.AstarSearch();
		
	}
}

class Node{
	public int F,G,H;
	public int x,y; //coordinate in Map
	public boolean closed;	//算法中closeList中的元素不会被拿出来吧？？
	public int val;
	Node father;
	public Node(int x, int y, int val) {
		this.x = x;
		this.y = y;
		this.val = val;
		G = 2147483647;
		closed = false;
	}
}
