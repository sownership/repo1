package algorithm.programmers;

public class ��ųƮ�� {

	public static void main(String[] args) {
		String skill = "CBD";
		String[] skill_trees = new String[] { "BACDE", "CBADF", "AECB", "BDA" };
		System.out.println(new ��ųƮ��().solution(skill, skill_trees));
	}

	public int solution(String skill, String[] skill_trees) {
		int answer = 0;

		char[] ca = skill.toCharArray();
		for (String skillTree : skill_trees) {
			int caIdx = 0;
			boolean fail = false;
			outerloop: for (char ste : skillTree.toCharArray()) {
				for (int i = caIdx; i < ca.length; i++) {
					if (ca[i] == ste) {
						if (i > caIdx) {
							fail = true;
							break outerloop;
						} else
							caIdx++;
					}
				}
			}
			if (!fail) {
				answer++;
				System.out.println(skillTree);
			}
		}

		return answer;
	}
}
