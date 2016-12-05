package com.perkinelmer.signals.project.model

import io.swagger.annotations._

import scala.annotation.meta.field

/** The Project: an endeavor, frequently collaborative, that occurs over a finite period of time and is intended to
  * achieve a particular aim. A project consists of metadata and zero or more datasets.
  * @param projectId  The project descriptor identifier across the whole lineage
  * @param versionId  The identifier of a specific project descriptor version within the lineage
  * @param version    The version number of a specific project descriptor version within the lineage
  * @param name       The name of the project
  */
@ApiModel("Project")
final case class Project (
  @(ApiModelProperty @field) (position=1, value="versionId") versionId: String,
  @(ApiModelProperty @field) (position=2, value="projectId") projectId: String,
  @(ApiModelProperty @field) (position=3, value="version") version: String,
  @(ApiModelProperty @field) (position=4, value="name") name: String) {
}

object Project {

  val SELF = "Project"

  sealed abstract class Attribute(
    val name         : String,
    val json         : String){
      override def toString = name + " -> " + json
  }

  // Attributes
  case object VERSION_IDENTIFIER extends Attribute("versionId", "id")
  case object PROJECT_IDENTIFIER extends Attribute("projectId", "projectId")
  case object VERSION extends Attribute("version", "version")
  case object NAME extends Attribute("name", "name")
}
