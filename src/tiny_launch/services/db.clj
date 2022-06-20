(ns tiny-launch.services.db
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.query :as mq]
            [monger.operators :refer :all])
  (:import [com.mongodb MongoOptions ServerAddress]
           [org.bson.types ObjectId]))

(def conn (atom (mg/connect)))

(def main-db (atom (mg/get-db @conn "tiny-launch")))

(defn get-by-id
  [id coll]
  (let [objId
        (if (instance? ObjectId id)
          id
          (ObjectId. (name id)))]
    (mc/find-map-by-id @main-db (name coll) objId))) ;(name) allows coll to be keyword or string

(defn get-by-filter
  [filter sort limit coll]
  (mq/with-collection @main-db (name coll)
    (mq/find filter)
    (mq/sort sort)
    (mq/limit limit)))

(defn get-site
  [id]
  (get-by-id id "sites"))

(defn get-sites
  [filter sort limit]
  (get-by-filter filter sort limit :sites))

(defn ensure-metadata
  "Takes a model and embeds one-time metadata if the id is nil. If the Id is set, then it removes one-time set meta-data from the object"
  [obj]
  (if (nil? (:_id obj))
    (merge obj {:_id (ObjectId.) :created (java.util.Date.)})
    (dissoc obj :created)))

(defn ->site-model
  "Selects only the valid database fields for saving and overwrites non-editable fields with db values"
  [obj]
  (select-keys
   (ensure-metadata obj)
   ;; This will limit the map to fields that are allowable for the db
   [:_id :label :created :rating :tags :description :subdomain :images])) ;; TODO: FInd a way to sanitize nested objects.

(defn ->user-model
  "Takes a user object and projects it to only valid fields for saving"
  [obj]
  (select-keys
   (ensure-metadata obj)
   [:_id :created :first-name :last-name :email :sites]))

(defn upsert-site
  [site]
  (let [model (->site-model site)]
    (mc/update-by-id @main-db "sites" (:_id model) model {:upsert true})))

(defn upsert-user
  [user]
  (let [model (->user-model user)]
    (mc/update-by-id @main-db "users" (:_id model) model {:upsert true})))

(defn favorite-site
  "Sets or unsets a site as a favorite for a user"
  [user site favorite?]
  (let [userId (:_id user) siteId (:_id site)]
    (when (and (some? userId) (some? siteId))
      (if-let [fs (mc/find-one-as-map @main-db "user-favorites" {:user-id userId :site-id siteId})]
        (mc/update-by-id @main-db "user-favorites" (:_id fs) {$set {:favorited favorite?}})
        (mc/insert @main-db "user-favorites" (ensure-metadata {:user-id userId :site-id siteId :favorited favorite?}))))
    )
  )

(defn site-favorited?
  "Returns true if the user has favorited the site"
  [user site]
    (let [userId (:_id user) siteId (:_id site)]
      (if (and (some? userId) (some? siteId))
        (mc/any? @main-db "user-favorites" {:user-id userId :site-id siteId :favorited true})
        false
        )))