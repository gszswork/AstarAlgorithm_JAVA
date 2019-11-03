# AstarAlgorithm_JAVA  
a Java version implementation of A star algorithm  
该算法从JAVA语言实现了A*算法的简单版本，算法中的H值通过Manhattan法进行计算。  
算法具体思想可参考：https://blog.csdn.net/hitwhylz/article/details/23089415

输入：地图的大小，起点坐标，终点坐标，地图各个坐标的点（1表示路径，0表示障碍）  
输出：地图中最近的路径/输出无路径可走  
输入样例1:  
3 3   
1 1  3 3   
1 1 1   
1 0 1   
1 1 1   
  
输出样例1:  
(1,1)->(1,2)->(2,3)->(3,3)  
  
输入样例子2：  
4 4   
1 1  4 4   
1 1 1 1   
1 0 0 0   
0 1 1 1   
1 1 1 1   
  
输出样例2:  
(1,1)->(2,1)->(3,2)->(4,3)->(4,4)  
  
输入样例3:  
3 3   
1 1   
3 3   
1 0 1   
0 0 1   
1 1 1   
  
输出样例3:  
Warning:There's no path between input Node!  
