package template.pattern.factorymethod;

public class MagicMazeGame extends MazeGame {

	@Override
	protected Room makeRoom() {
		return new MagicRoom();
	}

}
