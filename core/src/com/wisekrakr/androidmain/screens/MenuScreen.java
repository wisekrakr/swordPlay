package com.wisekrakr.androidmain.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.wisekrakr.androidmain.MainGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.EntityStyle;
import com.wisekrakr.androidmain.helpers.ButtonHelper;
import com.wisekrakr.androidmain.helpers.LabelHelper;
import com.wisekrakr.androidmain.retainers.SelectedCharacter;

public class MenuScreen extends ScreenAdapter {

    private Stage stage;
    private MainGame game;

    private TextureRegion textureRegion;
    private TextureRegion textureRegionSean;
    private TextureRegion textureRegionTitle;
    private TextureRegion textureRegionDrops;

    private Skin skin;
    private boolean characterChosen;
    private TextButton newGame;

    public MenuScreen(MainGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = game.assetManager().assetManager.get(String.valueOf(Gdx.files.internal("font/flat-earth-ui.json")));
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        characterChosen = false;

        Table table = new Table();
        table.setFillParent(true);
        table.bottom().center();

        newGame = new TextButton("start", skin);
        TextButton preferences = new TextButton("preferences", skin);
        TextButton exit = new TextButton("exit", skin);
        TextButton reset = new TextButton("reset levels", skin);

        table.add(newGame).expandX();
        table.row();
        table.add(preferences).expandX();
        table.row();
        table.add(reset).expandX();
        table.row();
        table.add(exit).expandX();

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();

            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(MainGame.PREFERENCES);
            }
        });

        reset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getGameThread().startNewLevelGeneration();
            }
        });

        textureRegion = new TextureRegion(new Texture("images/background/menubackground.jpg"));
         textureRegionDrops = new TextureRegion(new Texture("images/background/drops.png"));

//        Sound sound = game.assetManager().assetManager.get("sounds/", Sound.class);
//        sound.play(game.getGamePreferences().getSoundVolume());

        stage.addActor(table);

        chooseCharacter();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(textureRegion, Gdx.graphics.getWidth()/2f - Gdx.graphics.getWidth()/2f,
                Gdx.graphics.getHeight()/2f - Gdx.graphics.getHeight()/2f,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.getBatch().draw(textureRegionDrops, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        for (ImageButton character: ButtonHelper.getButtonList()){
            if (character.isChecked()){
                character.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.fadeIn(0.2f), Actions.repeat(10,new RepeatAction())));
            }
        }

        stage.getBatch().end();
        stage.draw();

        if (characterChosen) {
            newGame.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.changeScreen(MainGame.APPLICATION);
                }
            });
        }
    }

    private void chooseCharacter(){

        BitmapFont font = game.assetManager().assetManager.get("font/gamerFont.fnt");
        font.getData().setScale(GameConstants.FONT_SCALE);

        Label chooseLabel = LabelHelper.label("Choose your Style!", font, Color.WHITE);

        final ImageButton whiteBaldButton = ButtonHelper.characterButton("white_bald");
        final ImageButton blackBeardButton = ButtonHelper.characterButton("black_beard");
        final ImageButton blackBlackHairButton = ButtonHelper.characterButton("black_blackhair");
        final ImageButton blackGreyHairButton = ButtonHelper.characterButton("black_greyhair");
        final ImageButton whiteBlackHairButton = ButtonHelper.characterButton("white_blackhair");
        final ImageButton whiteBlondHairButton = ButtonHelper.characterButton("white_blondhair");
        final ImageButton whiteBrownHairButton = ButtonHelper.characterButton("white_brownhair");
        final ImageButton whiteRedHairButton = ButtonHelper.characterButton("white_redhair");

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight() - whiteBaldButton.getHeight());
        verticalGroup.wrap();

        verticalGroup.addActor(whiteBaldButton);
        verticalGroup.addActor(blackBeardButton);
        verticalGroup.addActor(blackBlackHairButton);
        verticalGroup.addActor(blackGreyHairButton);
        verticalGroup.addActor(whiteBlackHairButton);
        verticalGroup.addActor(whiteBlondHairButton);
        verticalGroup.addActor(whiteBrownHairButton);
        verticalGroup.addActor(whiteRedHairButton);

        chooseLabel.setPosition(verticalGroup.getX(), verticalGroup.getY() + whiteBaldButton.getHeight()/2, Align.center);

        if (!characterChosen) {
            whiteBaldButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    SelectedCharacter.setSelectedCharacter(EntityStyle.WHITE_BALD);
                    SelectedCharacter.setSwordLengthGirth(EntityStyle.WHITE_BALD.getSwordLength(), EntityStyle.WHITE_BALD.getSwordGirth());
                    whiteBaldButton.isChecked();
                    characterChosen = true;
                }
            });
            blackBeardButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    SelectedCharacter.setSelectedCharacter(EntityStyle.BLACK_BEARD);
                    SelectedCharacter.setSwordLengthGirth(EntityStyle.BLACK_BEARD.getSwordLength(), EntityStyle.BLACK_BEARD.getSwordGirth());
                    blackBeardButton.isChecked();
                    characterChosen = true;
                }
            });
            blackBlackHairButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    SelectedCharacter.setSelectedCharacter(EntityStyle.BLACK_BLACKHAIR);
                    SelectedCharacter.setSwordLengthGirth(EntityStyle.BLACK_BLACKHAIR.getSwordLength(), EntityStyle.BLACK_BLACKHAIR.getSwordGirth());
                    blackBlackHairButton.isChecked();
                    characterChosen = true;
                }
            });
            blackGreyHairButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    SelectedCharacter.setSelectedCharacter(EntityStyle.BLACK_GREYHAIR);
                    SelectedCharacter.setSwordLengthGirth(EntityStyle.BLACK_GREYHAIR.getSwordLength(), EntityStyle.BLACK_GREYHAIR.getSwordGirth());
                    blackGreyHairButton.isChecked();
                    characterChosen = true;
                }
            });
            whiteBlackHairButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    SelectedCharacter.setSelectedCharacter(EntityStyle.WHITE_BLACKHAIR);
                    SelectedCharacter.setSwordLengthGirth(EntityStyle.WHITE_BLACKHAIR.getSwordLength(), EntityStyle.WHITE_BLACKHAIR.getSwordGirth());
                    whiteBlackHairButton.isChecked();
                    characterChosen = true;
                }
            });
            whiteBlondHairButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    SelectedCharacter.setSelectedCharacter(EntityStyle.WHITE_BLONDHAIR);
                    SelectedCharacter.setSwordLengthGirth(EntityStyle.WHITE_BLONDHAIR.getSwordLength(), EntityStyle.WHITE_BLONDHAIR.getSwordGirth());
                    whiteBlondHairButton.isChecked();
                    characterChosen = true;
                }
            });
            whiteBrownHairButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    SelectedCharacter.setSelectedCharacter(EntityStyle.WHITE_BROWNHAIR);
                    SelectedCharacter.setSwordLengthGirth(EntityStyle.WHITE_BROWNHAIR.getSwordLength(), EntityStyle.WHITE_BROWNHAIR.getSwordGirth());
                    whiteBrownHairButton.isChecked();
                    characterChosen = true;
                }
            });
            whiteRedHairButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    SelectedCharacter.setSelectedCharacter(EntityStyle.WHITE_REDHAIR);
                    SelectedCharacter.setSwordLengthGirth(EntityStyle.WHITE_REDHAIR.getSwordLength(), EntityStyle.WHITE_REDHAIR.getSwordGirth());
                    whiteRedHairButton.isChecked();
                    characterChosen = true;
                }
            });

        }

        stage.addActor(verticalGroup);
        stage.addActor(chooseLabel);

    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
