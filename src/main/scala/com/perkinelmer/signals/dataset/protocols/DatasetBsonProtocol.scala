package com.perkinelmer.signals.dataset.protocols

import com.mongodb.casbah.Imports._
import com.perkinelmer.signals.dataset.model.{Dataset, DatasetConst}
import com.perkinelmer.signals.storage.MongoFactory

/** Takes care of marshalling/unmarshalling of model objects into MongoDB objects in binary (BSON) format.
  */
object DatasetBsonProtocol {
  /** Marshalling: converts a Dataset model object into a BSON format that MongoDb can store.
    * @param dataset The Dataset model object
    * @return The Dataset MongoDb object.
    */
  def buildMongoDbObject(dataset: Dataset): MongoDBObject = {
    val builder = MongoDBObject.newBuilder
    builder += DatasetConst.VERSION_IDENTIFIER.json -> dataset.versionId
    builder += DatasetConst.DATASET_IDENTIFIER.json -> dataset.datasetId
    builder += DatasetConst.VERSION.json -> dataset.version
    builder += DatasetConst.NAME.json -> dataset.name
    builder.result
  }

  /** Unmarshalling: converts a MongoDb (BSON) object into a Dataset model object.
    * @param datasetDbObject The MongoDB BSON object for the Dataset
    * @return The Dataset model object.
    */
  def buildModelObject(datasetDbObject: MongoDBObject): Dataset = {
    new Dataset(
      getMandatoryAttribute(datasetDbObject, DatasetConst.VERSION_IDENTIFIER.json),
      getMandatoryAttribute(datasetDbObject, DatasetConst.DATASET_IDENTIFIER.json),
      getMandatoryAttribute(datasetDbObject, DatasetConst.VERSION.json),
      getMandatoryAttribute(datasetDbObject, DatasetConst.NAME.json))
  }

  /** Returns the value of a mandatory attribute or triggers an exception.
    * @param datasetDbObject The MongoDB BSON object for the Dataset
    * @param attribute The name of the attribute to retrieve.
    * @return The attribute value or exception
    */
  def getMandatoryAttribute(datasetDbObject: MongoDBObject, attribute: String): String = {
    if(datasetDbObject.getAs[String](attribute).isDefined) datasetDbObject.getAs[String](attribute).get
    else throw new Exception("Invalid " + DatasetConst.SELF + " found, '" + attribute + "' not defined.")
  }

  /** Saves the Dataset object model into MongoDB
    * @param dataset The Dataset model object
    */
  def saveDataset(dataset: Dataset) {
    val mongoObj = buildMongoDbObject(dataset)
    MongoFactory.collection.save(mongoObj)
  }

  /** Retrieves the Dataset object model from MongoDB
    * @param versionId The identifier of the specific version of Dataset to be retrieved
    * @return The Dataset model object correspondent to the given identifier
    */
  def getDataset(versionId: String): Option[Dataset] = {
    val mongoColl = MongoFactory.collection
    val q = MongoDBObject(DatasetConst.VERSION_IDENTIFIER.json -> versionId)
    val dataset = mongoColl.findOne(q)

    dataset match {
      case Some(dataset) =>
        return Some(buildModelObject(dataset))
      case None =>
        return None
    }
  }
}

