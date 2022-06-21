(ns tiny-launch.services.site
  (:require [monger.core :as mg]
            [tiny-launch.services.db :as db]
            [io.pedestal.log :as log])
  (:import [com.mongodb MongoOptions ServerAddress]
           [org.bson.types ObjectId]))

(defn get-site 
  "Gets a list of sites based on the keyword"
  [keyword]
  (case keyword
    :featured (tiny-launch.services.db/get-sites {} (array-map :favorites -1) 10)
    :newest (tiny-launch.services.db/get-sites {} (array-map :created -1) 10)
    nil '({:label "You get nothing!"})))

(defn favorite-site
  [site user favorite?]
  (log/debug "Favorited site" site)
  (db/favorite-site site user favorite?))

(defn site-favorited?
  "Returns true if the user has favorited the site"
  [site user]
  (db/site-favorited? site user))