package practice.photo.rearrange;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class CompareTwoFolders {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		print(minus("E:\\사진_local작업중", "E:\\사진"));
	}

	private static void print(Set<File> fileSet) {
		List<File> files = new LinkedList<>(fileSet);
		files.sort(new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
			}
		});
		for (File f : files) {
			System.out.println(f);
		}
	}

	private static Set<File> minus(String dir2, String dir1) throws NoSuchAlgorithmException, IOException {
		Map<Long, Set<File>> lenFilesMap1 = getLenFilesMap("E:\\사진");
		Map<Long, Set<File>> lenFilesMap2 = getLenFilesMap("E:\\사진_local작업중");
		return minus(lenFilesMap2, lenFilesMap1);
	}

	private static Set<File> minus(Map<Long, Set<File>> lenFilesMap2, Map<Long, Set<File>> lenFilesMap1)
			throws IOException, NoSuchAlgorithmException {
		Set<File> minusSet = new HashSet<>();

		// 같은 사이즈들을 찾는다
		Set<Long> intersectLens = intersectLens(lenFilesMap2, lenFilesMap1);

		// 파일 사이즈가 일단 같아야 같은 파일이다
		for (Long len : intersectLens) {
			if (len == 0)
				continue;

			// 파일 사이즈가 같은 dir2, dir1 의 모든 파일들의 bis 를 만든다
			Map<BufferedInputStream, File> bisFile = allBisFile(len, lenFilesMap2, lenFilesMap1);

			// 처음에는 다 하나의 그룹으라고 놓고 시작해서 내용이 다른 파일이 나오면 그룹을 분리해 나간다
			Set<Collection<BufferedInputStream>> initGroups = new HashSet<>();
			initGroups.add(bisFile.keySet());
			Collection<Collection<BufferedInputStream>> lastGroups = recursive(initGroups);

			// lastGroups 의 각 그룹에서 1개만 남아 있는 것 중에 dir2 에 속해 있는 것이 dir2 에만 있는 것이다
			for (Collection<BufferedInputStream> group : lastGroups) {
				File f = bisFile.get(group.iterator().next());
				if (group.size() == 1 && lenFilesMap2.get(len).contains((f))) {
					minusSet.add(f);
				}
			}

			// 파일들을 닫는다
			close(bisFile.keySet());
		}

		// 2에만 있는 사이즈들을 추가한다
		for (Entry<Long, Set<File>> e : lenFilesMap2.entrySet()) {
			if (!lenFilesMap1.containsKey(e.getKey())) {
				minusSet.addAll(e.getValue());
			}
		}

		return minusSet;
	}

	private static Set<Long> intersectLens(Map<Long, Set<File>> lenFilesMap2, Map<Long, Set<File>> lenFilesMap1) {
		Set<Long> intersectLens = new HashSet<Long>(lenFilesMap2.keySet());
		intersectLens.retainAll(lenFilesMap1.keySet());
		return intersectLens;
	}

	private static void close(Set<BufferedInputStream> bises) throws IOException {
		for (BufferedInputStream bis : bises) {
			bis.close();
		}
	}

	private static Map<BufferedInputStream, File> allBisFile(Long len, Map<Long, Set<File>> lenFilesMap2,
			Map<Long, Set<File>> lenFilesMap1) throws FileNotFoundException {
		Map<BufferedInputStream, File> bisFile = new HashMap<>();
		for (File f : lenFilesMap2.get(len)) {
			bisFile.put(new BufferedInputStream(new FileInputStream(f)), f);
		}
		for (File f : lenFilesMap1.get(len)) {
			bisFile.put(new BufferedInputStream(new FileInputStream(f)), f);
		}
		return bisFile;
	}

	private static Collection<Collection<BufferedInputStream>> recursive(
			Collection<Collection<BufferedInputStream>> groups) throws IOException {
		Collection<Collection<BufferedInputStream>> nextGroups = new HashSet<>();

		// 각 그룹별로 모든 파일들을 8k글자씩 읽어서 재그루핑한다
		for (Collection<BufferedInputStream> group : groups) {
			Map<ByteBuffer, Set<BufferedInputStream>> thisGroups = new HashMap<>();
			// 이미 그룹에 1개의 파일밖에 없으면 재그루핑 시도가 의미없다
			if (group.size() == 1) {
				nextGroups.add(group);
				continue;
			}
			for (BufferedInputStream bis : group) {
				byte[] b = new byte[1024 * 8];
				int len = bis.read(b);
				ByteBuffer bb = ByteBuffer.wrap(b, 0, len);
				Set<BufferedInputStream> thisGroup = thisGroups.get(bb);
				if (thisGroup == null) {
					thisGroup = new HashSet<>();
					thisGroups.put(bb, thisGroup);
				}
				thisGroup.add(bis);
			}
			nextGroups.addAll(thisGroups.values());
		}
		return nextGroups;
	}

	private static Map<Long, Set<File>> getLenFilesMap(String root) {
		Map<Long, Set<File>> lenFilesMap = new HashMap<Long, Set<File>>();
		Queue<File> q = new LinkedBlockingQueue<File>();
		q.offer(new File(root));
		while (!q.isEmpty()) {
			File f = q.poll();
			if (f.isDirectory()) {
				q.addAll(Arrays.asList(f.listFiles()));
			} else {
				long len = f.length();
				Set<File> fs = lenFilesMap.get(len);
				if (fs == null) {
					fs = new HashSet<File>();
					lenFilesMap.put(len, fs);
				}
				fs.add(f);
			}
		}
		return lenFilesMap;
	}
}
