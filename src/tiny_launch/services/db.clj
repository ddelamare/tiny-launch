(ns tiny-launch.services.db
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.query :as mq])
  (:import [com.mongodb MongoOptions ServerAddress]
           [org.bson.types ObjectId]))

(def conn (atom (mg/connect)))

(def main-db (atom (mg/get-db @conn "tiny-launch")))

(defn get-site
  [id]
  (let [siteId
        (if (instance? ObjectId id)
          id
          (ObjectId. id))]
    (mc/find-map-by-id @main-db "sites" siteId)))

(defn get-sites
  [filter sort limit]
  (mq/with-collection @main-db "sites"
    (mq/find filter)
    (mq/sort sort)
    (mq/limit limit)))

(defn ->site-model
  "Selects only the valid database fields for saving and overwrites non-editable fields with db values"
  [obj]
  (select-keys
   (merge
    obj
    (cond
      (nil? (:_id obj))
      {:_id (ObjectId.)
       :created (java.util.Date.)}
      (not (nil? (:_id obj))) (let [saved (mc/find-map-by-id @main-db "sites" (:_id obj))]
                                {:created (:created saved)})))
   ;; This will limit the map to fields that are allowable for the db
  [:_id :label :created :rating :tags :description]))

(defn upsert-site
  [site]
  (let [model (->site-model site)]
    (mc/update-by-id @main-db "sites" (:_id model) model {:upsert true})))