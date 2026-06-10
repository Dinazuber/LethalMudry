package lethalmudry

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Skin}

import java.awt.Desktop.Action

class PopUp(text: String, stage: Stage) {
  val skinText: Skin = new Skin(Gdx.files.internal("data/styles/label/textFont.json"))
  val label: Label = new Label(text, skinText)

  label.setPosition(Gdx.graphics.getWidth / 2f - label.getWidth /2f, Gdx.graphics.getHeight * 0.7f)

  label.getColor.a = 0f

  label.addAction(Actions.sequence(
    Actions.fadeIn(0.2f),
    Actions.delay(1f),
    Actions.fadeOut(0.5f),
    Actions.removeActor()
  ))

  stage.addActor(label)
}