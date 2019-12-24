package practice.vcs;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Console {

	private static Scanner sc = new Scanner(System.in);
	private Svc svc;
	private Local local = new Local();

	public Console(Svc svc) {
		this.svc = svc;
	}

	void start() throws IOException {

		Thread t = new Thread(ExHelper.rWrapper(() -> {
			while (true) {
				String line = sc.nextLine();
				if (line.startsWith("CHECKIN")) {
					svc.checkIn(line, inputTargets(line.split(" ")[1]).toArray(new Path[] {}), Local.path);

				} else if (line.startsWith("CHECKOUT")) {
					String target = "";
					if(line.split(" ").length>1) {
						target = line.split(" ")[1];
					}
					svc.checkoutTargets(Path).sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
							.forEach(ExHelper.cWrapper(e -> {
								System.out.println(e.getKey() + ", " + e.getValue());
								local.copy(e.getKey(), e.getValue());
							}));
				}
			}
		}));
		t.setDaemon(true);
		t.start();
	}

	/**
	 * @param filesStr
	 * @return targets(relative)
	 */
	private List<Path> inputTargets(String filesStr) {
		List<Path> targets = new LinkedList<>();

		// make checkin targets in local
		Arrays.stream(filesStr.split(",")).forEach(ExHelper.cWrapper(fn -> {
			if (fn.contains("*")) {
				targets.addAll(local.getChildsRecursively(fn.replaceAll("\\*", "")));
			} else {
				if (local.isDirectory(fn)) {
					targets.addAll(local.walk(fn));
				} else {
					targets.add(Paths.get(fn));
				}
			}
		}));
		return targets;
	}

}
