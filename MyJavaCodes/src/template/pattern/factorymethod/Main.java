package template.pattern.factorymethod;

/**
 * uml: https://en.wikipedia.org/wiki/Factory_method_pattern#/media/File:W3sDesign_Factory_Method_Design_Pattern_UML.jpg<br>
 * fingerface, iris 의 makeapi 가 factory method pattern 임<br>
 * @author 서효진
 *
 */
public class Main {

	public static void main(String[] args) {
		MazeGame mazeGame = new MagicMazeGame();
		mazeGame.makeRoom();
	}
}
