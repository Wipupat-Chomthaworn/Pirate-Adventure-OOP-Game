package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.PauseButton;
import ui.SoundButton;
import ui.UrmButton;
import ui.VolumeButton;
import static util.Constants.UI.PauseButtons.SOUND_SIZE;
import util.LoadSave;
import static util.Constants.UI.URMButtons.*;
import static util.Constants.UI.VolumeButtons.SLIDER_WIDTH;
import static util.Constants.UI.VolumeButtons.VOLUME_HEIGHT;

public class GameOptions extends State implements Statemethods {

    private BufferedImage backgroundImg, optionsBackgroundImg;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuB;
    private VolumeButton volumeButton;
    private SoundButton musicButton, sfxButton;

    public GameOptions(Game game) {
        super(game);
        loadImgs();
        loadButton();
        createVolumeButton();
        createSoundButtons();
    }

    private void loadButton() {
        int menuX = (int) (387 * Game.SCALE);
        int menuY = (int) (325 * Game.SCALE);

        menuB = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 2);
    }

    private void loadImgs() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        optionsBackgroundImg = LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_MENU);

        bgW = (int) (optionsBackgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (optionsBackgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (33 * Game.SCALE);
    }

    private void createVolumeButton() {
        int vX = (int) (309 * Game.SCALE);
        int vY = (int) (278 * Game.SCALE);
        volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void createSoundButtons() {
        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (140 * Game.SCALE);
        int sfxY = (int) (186 * Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    @Override
    public void update() {
        musicButton.update();
        sfxButton.update();

        menuB.update();

        volumeButton.update();

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(optionsBackgroundImg, bgX, bgY, bgW, bgH, null);

        musicButton.draw(g);
        sfxButton.draw(g);

        menuB.draw(g);

        volumeButton.draw(g);

    }

    public void mouseDragged(MouseEvent e) {
        if (volumeButton.isMousePressed()) {
            volumeButton.changeX(e.getX());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else if (isIn(e, volumeButton)) {
            volumeButton.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
            if (menuB.isMousePressed()) {
                Gamestate.state = Gamestate.MENU;
            }
        } else if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
        }
         else if (isIn(e, sfxButton)) {
                if (sfxButton.isMousePressed()) {
                    sfxButton.setMuted(!sfxButton.isMuted());
                }
            }
        musicButton.resetBools();
        sfxButton.resetBools();
        menuB.resetBools();
        volumeButton.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuB.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if (isIn(e, menuB)) {
            menuB.setMouseOver(true);
        } else if (isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        } else if (isIn(e, volumeButton)) {
            volumeButton.setMouseOver(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Gamestate.state = Gamestate.MENU;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

}
