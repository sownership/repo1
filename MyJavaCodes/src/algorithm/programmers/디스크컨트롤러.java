package algorithm.programmers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class 디스크컨트롤러 {

	public static void main(String[] args) {
		//9
		System.out.println(new 디스크컨트롤러().solution(new int[][] {{0, 3}, {1, 9}, {2, 6}}));
	}
	
	public int solution(int[][] jobs) {
        List<int[]> jl=Arrays.stream(jobs).sorted((j1,j2)->j1[0]==j2[0]?j1[1]-j2[1]:j1[0]-j2[0]).collect(Collectors.toList());
        
        Queue<int[]> q=new PriorityQueue<>((j1,j2)->j1[1]-j2[1]);
        
        int[] cj=jl.remove(0);
        int pt=cj[1];
        int endT=cj[0]+cj[1];
        
        while(true) {
        	Iterator<int[]> it=jl.iterator();
        	while(it.hasNext()) {
        		int[] j=it.next();
        		if(j[0]<=endT) {
        			q.offer(j);
        			it.remove();
        		} else break;
        	}
            for(int i=0;i<jl.size();i++) {
            	if(jl.get(i)[0]<=endT) q.offer(jl.remove(0));
            	else break;
            }
            
            if(q.isEmpty()) {
            	if(jl.size()>0) {
            		cj=jl.remove(0);
                	pt+=cj[1];
                	endT=cj[0]+cj[1];
            	} else {
            		break;
            	}
            } else {
            	cj=q.poll();
            	pt+=endT-cj[0]+cj[1];
            	endT+=cj[1];
            }
        }
        
        return pt/jobs.length;
    }
}
