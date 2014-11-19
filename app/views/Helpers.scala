package views

import play.twirl.api.{HtmlFormat, Html}
import play.api.Play
import play.api.mvc.Call

object includeCode {
  def apply(filename: String, segment: String, style : String): Html = apply(filename, Option(segment), Option(style))

  def apply(filename: String, segment: String): Html = apply(filename, Option(segment), None)

  def apply(filename: String, segment: Option[String] = None, style: Option[String]): Html =  {
    val extension = filename.split("\\.").last
    val source = io.Source.fromFile(Play.current.getFile(filename))

    val content = segment match {
      case Some(s) =>
        val sep = "#" + s
        val lines = source.getLines.toList
        if(!lines.exists(_.contains(sep))) throw new IllegalArgumentException(s"could not find separator [$sep] in file [$filename]")

        val sepLine :: block = lines.dropWhile(!_.contains(sep))
        val spaces = sepLine.takeWhile(_ == ' ')
        block.takeWhile(!_.contains(sep)).map(_.replaceFirst(spaces, "")).mkString("\n")
      case None => source.getLines.mkString("\n")
    }

    Html(s"""<pre class="prettyprint" style="${style.getOrElse("")}"><code class="language-$extension">${HtmlFormat.escape(content)}</code></pre>""")
  }
}

object points {
  def apply(ps: Html) = Seq(Html("""<ul class="points">"""), ps, Html("</ul>"))
}

object point {
  def apply(p: Html) =
    Seq(Html("<li class='point'>"), p, Html("</li>"))
  def show(p: Html) = {
    Seq(Html("<li>"), p, Html("</li>"))
  }

  def apply(p: String) =
    Html("<li class='point'>" + HtmlFormat.escape(p) + "</li>")
  def show(p: String) =
    Html("<li>" + HtmlFormat.escape(p) + "</li>")

}

object item {
  def apply(p: Html) =
    Seq(Html("<span class='point'>"), p, Html("</span>"))
}

object image {
  def apply(width: Int, url: Call) = {
    Html("<img style='width:" + width + "px;' src='" + url.url + "'>")
  }
}

object imagePoint {
  def apply(width: Int, url: Call) = {
    Seq(Html("<div class='point' style='width: " + width + "px; margin: 0 auto;'>"), image(width, url), Html("</div>"))
  }
}

object imageCentered {
  def apply(width: Int, url: Call) = {
    Seq(Html("<div style='width: " + width + "px; margin: 0 auto;'>"), image(width, url), Html("</div>"))
  }
}

object preserveSpaces {
  def apply(s : String) : Html = {
    Html(s.replace(" ", "&nbsp;"))
  }
}
object spaces {
  def apply(i : Int) : Html = Html("&nbsp;" * i)
}
