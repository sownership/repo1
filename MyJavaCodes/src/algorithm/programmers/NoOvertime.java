package algorithm.programmers;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class NoOvertime {
	
	private static class Entry {
		int idx;
		int sub;
		
		private Entry(int idx, int sub) {
			this.idx = idx;
			this.sub = sub;
		}
		
		@Override
		public String toString() {
			return idx + " " + sub;
		}
	}
	
	public int noOvertime(int no, int[] works) {
		
		Stack<Entry> st = new Stack<>();
		Map<Integer, Integer> idxSub = new HashMap<>();
		
		// 최상위 노드들 push
		int idx = 0;
		for(int i=no; i>=0; i--) {
			st.push(new Entry(idx, no-i));
		}
		
		while(!st.empty()) {
			Entry e = st.pop();
			idxSub.put(e.idx, e.sub);
			
			// set remain
			int remain = no;
			for(int i=0; i<=e.idx; i++) {
				remain -= idxSub.get(i);
			}
			
			if(e.idx==works.length-1) {
				// 최하위 노드이고 remain 이 0 이면 최소값 갱신
				if(remain==0) {
					int overtotal = 0;
					for(int i=0; i<works.length; i++) {
						int overtime = works[i] - idxSub.get(i);
						overtotal += overtime*overtime;
					}
					if(result > overtotal) {
						result = overtotal;
					}
				}
			} else {
				// 최하위 노드가 아니면 가능한 자식 노드들을 push
				for(int i=remain; i>=0; i--) {
					st.push(new Entry(e.idx + 1, remain-i));
				}
			}
		}

		return result;
	}
	
	private int result = Integer.MAX_VALUE;
	
//	private void dfs(int no, int[] works, int idx) {
//		if(no==0) {
//			int tmp = 0;
//			for(int work : works) {
//				tmp += work*work;
//			}
//			if(tmp<result) {
//				result = tmp;
//			}
//			return;
//		}
//		
//		if(idx>=works.length) {
//			return;
//		}
//		
//		for(int i=no; i>=0; i--) {
//			if(works[idx] - i >= 0) {
//				int workTmp = works[idx];
//				works[idx] = works[idx] - i;
//				dfs(no-i, works, idx+1);
//				works[idx] = workTmp;
//			}
//		}
//	}
	
//	public int noOvertime(int no, int[] works) {
//		int result = 0;
//		// 야근 지수를 최소화 하였을 때의 야근 지수는 몇일까요?
//
//		return result;
//	}

	public static void main(String[] args) {
		NoOvertime c = new NoOvertime();
		int[] test = { 4, 3, 3 };
		System.out.println(c.noOvertime(4, test));
	}
}
