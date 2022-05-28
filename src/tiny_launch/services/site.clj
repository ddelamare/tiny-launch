(ns tiny-launch.services.site
  (:require [monger.core :as mg]
            [tiny-launch.services.db :as db])
  (:import [com.mongodb MongoOptions ServerAddress]
           [org.bson.types ObjectId]))

(defn get-site 
  "Gets a list of sites based on the keyword"
  [keyword]
  (case keyword
    :featured (tiny-launch.services.db/get-sites {})
    :newest '({:label "Dodododot"} {:label "Baby Sharky"} {:label "My Tes site"})
    nil '({:label "You get nothing!"})))