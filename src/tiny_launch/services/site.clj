(ns tiny-launch.services.site
  (:require [monger.core :as mg])
  (:import [com.mongodb MongoOptions ServerAddress]))

(defn get-site 
  "Gets a list of sites based on the keyword"
  [keyword]
  (case keyword
    :featured '({:label "hi"} {:label "bye"})
    :newest '({:label "Dodododot"} {:label "Baby Sharky"} {:label "My Tes site"})
    nil '({:label "You get nothing!"})))