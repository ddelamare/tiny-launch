(ns tiny-launch.services.db
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import [com.mongodb MongoOptions ServerAddress]
           [org.bson.types ObjectId]))

(def conn (atom (mg/connect)))

(def main-db (atom (mg/get-db @conn "tiny-launch")))

(defn get-sites
  [filter]
  (mc/find-maps @main-db "sites" filter))

(defn ->site-model
  [obj]
  {
   :_id (:_id obj)
   :label (:label obj)
   :created (:created obj)
  })

(defn upsert-site
  [site]
  (let [id (or (:_id site) (ObjectId.))]
    (mc/update @main-db "sites" {:_id id} 
               (assoc (assoc (->site-model site) :_id id) :created (or (:created site) (java.util.Date.))) {:upsert true})))