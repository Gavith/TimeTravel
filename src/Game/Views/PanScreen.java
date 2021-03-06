package Game.Views;

import Game.LevelBase;
import Game.UserControls;
import engine.EnigView;
import engine.Entities.Camera;
import engine.OpenGL.EnigWindow;
import engine.OpenGL.FBO;

import static Game.Views.MainView.*;

public class PanScreen extends EnigView {
	public LevelBase lvl;
	public int startTZ;
	public Camera camera;
	public float maxX = 0;
	public float maxY = 0;
	public float minX = 0;
	public float minY = 0;
	public PanScreen(EnigWindow wind) {
		super(wind, false);
	}
	
	@Override
	public void setup() {
		startTZ = MainView.currentLevel.currentTZ;
		MainView.currentLevel.currentTZ = 0;
		for (int i = 0; i < MainView.currentLevel.levelseries.size(); ++i) {
			float height = (float) MainView.currentLevel.levelseries.get(i).size();
			if (height > maxY) {
				maxY = height;
			}
			for (Character[] row:MainView.currentLevel.levelseries.get(i)) {
				float width = row.length;
				if (width > maxX) {
					maxX = width;
				}
			}
		}
		maxX *= 50;
		maxY *= 50;
		minX = 0.2f * maxX;
		minY = 0.2f * maxY;
		maxX *= 0.8f;
		maxY *= 0.8f;
		lvl = MainView.currentLevel;
		camera = new Camera((float) window.getWidth(), (float) window.getHeight());
		camera.x = minX;
		camera.y = minY;
		for (int i:UserControls.skip) {
			window.keys[i] = 0;
		}
	}
	
	@Override
	public boolean loop() {
        if(!MainView.quit) {
            if (UserControls.skip(window)) {
            	lvl.currentTZ = startTZ;
            	lvl.startSecondTime = System.nanoTime();
                return true;
            }
            FBO.prepareDefaultRender();
            MainView.main.renderBackground();
            lvl.render(camera);
	
			levelMarker.renderStr("level " + (currentLevelNum + 1));
            if (lvl.currentTZ % 2 == 0) {
                camera.x += maxX/200;
                camera.y += maxY/200;
				backgroundOffset.x += maxX/200 * 0.0003;
				backgroundOffset.y += maxY/200 * 0.0003;
                if (camera.x > maxX || camera.y > maxY) {
                    ++lvl.currentTZ;
                    if (!(lvl.currentTZ < lvl.levelseries.size())) {
                        lvl.currentTZ = startTZ;
                        lvl.startSecondTime = System.nanoTime();
                        return true;
                    }
                }
            }else {
                camera.x -= maxX/200;
                camera.y -= maxY/200;
				backgroundOffset.x -= maxX/200 * 0.0003;
				backgroundOffset.y -= maxY/200 * 0.0003;
                if (camera.x < minX || camera.y < minY) {
                    ++lvl.currentTZ;
                    if (!(lvl.currentTZ < lvl.levelseries.size())) {
                        lvl.currentTZ = startTZ;
						lvl.startSecondTime = System.nanoTime();
                        return true;
                    }
                }
            }
			if (backgroundMoveBool) {
				backgroundVelocity.mul(0.99f);
				backgroundVelocity.add((float) (delta_time * 0.000007f * (Math.random() - 0.5)), (float) (delta_time * 0.000007f * (Math.random() - 0.5)));
				backgroundOffset.add(backgroundVelocity);
			}
			ttogui.render(-1, false);
            return false;
        } else {
            return true;
        }
	}
	
	
	@Override
	public String getName() {
		return null;
	}
}
