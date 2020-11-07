package algorithm.programmers;

import java.time.*;
import java.time.format.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class 추석트래픽 {

	public static void main(String[] args) {
		
//		String[] lines = new String[] {
//				"2016-09-15 01:00:04.001 2.0s",
//				"2016-09-15 01:00:07.000 2s"
//		};
//		String[] lines = new String[] {
//				"2016-09-15 01:00:04.002 2.0s",
//				"2016-09-15 01:00:07.000 2s"
//		};
		String[] lines = new String[] {
				"2016-09-15 01:00:04.001 2.0s", "2016-09-15 01:00:07.000 2s"
		};
		
//		String[] lines = new String[] {
//				"2016-09-15 20:59:57.421 0.351s",
//				"2016-09-15 20:59:58.233 1.181s",
//				"2016-09-15 20:59:58.299 0.8s",
//				"2016-09-15 20:59:58.688 1.041s",
//				"2016-09-15 20:59:59.591 1.412s",
//				"2016-09-15 21:00:00.464 1.466s",
//				"2016-09-15 21:00:00.741 1.581s",
//				"2016-09-15 21:00:00.748 2.31s",
//				"2016-09-15 21:00:00.966 0.381s",
//				"2016-09-15 21:00:02.066 2.62s"
//		};
		System.out.println(new 추석트래픽().solution(lines));
	}
	
	class E {
		LocalDateTime ldt;
		boolean isStart;
		public E(LocalDateTime ldt, boolean isStart) {
			this.ldt = ldt;
			this.isStart = isStart;
		}
	}
	
	public int solution(String[] lines) {
        int answer = 0;
        
        List<E> list=new ArrayList<>();
        Arrays.stream(lines).forEach(line->{
        	LocalDateTime start=null;
        	LocalDateTime end = LocalDateTime.parse(line.substring(0,23), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        	
        	line=line.replace("s", "");
        	if(line.substring(24).contains(".")) {
        		String[] sa=line.substring(24).split("\\.");
            	long sec = Long.valueOf(sa[0]);
            	String milliS = sa[1];
            	if(milliS.length()==2) milliS+="0";
            	if(milliS.length()==1) milliS+="00";
            	long milli=Long.valueOf(milliS)*1000000;
            	
            	start = end.minusSeconds(sec).minusNanos(milli-1000000);
        	} else {
        		start = end.minusSeconds(Long.valueOf(line.substring(24))).minusNanos(-1000000);
        	}
        	
        	list.add(new E(start,true));
        	list.add(new E(end,false));
        });
        
        list.sort((e1,e2)->e1.ldt.compareTo(e2.ldt));
        
        int cnt=0;
        for(int i=0;i<list.size();i++) {
        	E e = list.get(i);
        	
    		int cnt2=cnt;
    		for(int j=i+1;j<list.size();j++) {
    			if(ChronoUnit.MILLIS.between(e.ldt, list.get(j).ldt)>=1000) break;
    			if(list.get(j).isStart) cnt2++;
    		}
//    		System.out.println(i + ", " + e.ldt + ", " + e.isStart + ", " + cnt2);
    		answer=Math.max(answer, cnt2);
        	
        	if(e.isStart) {
        		cnt++;
        	} else
        		cnt--;
        }
        
        return answer;
    }
}
