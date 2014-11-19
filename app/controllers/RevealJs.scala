package controllers

import play.api.mvc.{Controller, Action}

/**
 * Created by mboehm on 26.03.14.
 */
object RevealJs extends Controller{
  def slides = Action(Ok(views.html.reveal.render()))
}
