 (ns tiny-launch.services.user
  (:require [monger.core :as mg]
            [tiny-launch.services.db :as db]
            [io.pedestal.log :as log])
  (:import [com.mongodb MongoOptions ServerAddress]
           [org.bson.types ObjectId]))

(defn get-current-user
  "Stub method to get carl sagan"
  []
  (db/get-by-id (ObjectId. "62b0ea304844e1281caca9c9") :users)
  )