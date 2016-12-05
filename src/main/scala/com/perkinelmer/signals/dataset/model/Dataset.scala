package com.perkinelmer.signals.dataset.model

import io.swagger.annotations._

import scala.annotation.meta.field

/**
  * The Dataset: a collection of files that are normally considered as a unit.
  * @param datasetId  The dataset descriptor identifier across the whole lineage
  * @param versionId  The identifier of a specific dataset descriptor version within the lineage
  * @param version    The version number of a specific dataset descriptor version within the lineage
  * @param name       The name of the dataset
  */
@ApiModel("Dataset")
final case class Dataset(
     @(ApiModelProperty @field) (position=2, value="datasetId") datasetId: String,
     @(ApiModelProperty @field) (position=1, value="versionId") versionId: String,
     @(ApiModelProperty @field) (position=3, value="version") version: String,
     @(ApiModelProperty @field) (position=4, value="name") name: String) {
}

