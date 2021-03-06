package org.scalameta.invariants

class InvariantFailedException(message: String) extends Exception(message)
object InvariantFailedException {
  def raise(invariant: String, failures: List[String], locals: Map[String, Any]): InvariantFailedException = {
    val mandatory = s"""
      |invariant failed:
      |when verifying $invariant
      |found that ${failures.head}
    """.trim.stripMargin
    val optionalFailures = failures.tail.headOption.map(_ => "\n" + failures.tail.map("and also " + _).mkString("\n")).getOrElse("")
    val optionalLocals = if (locals.nonEmpty) "\n" + locals.toList.sortBy(_._1).map({ case (k, v) => s"where $k = $v"}).mkString("\n")
    throw new InvariantFailedException(mandatory + optionalFailures + optionalLocals)
  }
}
