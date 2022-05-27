(ns tiny-launch.services.db
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import [com.mongodb MongoOptions ServerAddress]))

(def conn (atom (mg/connect)))

(def main-db (atom (mg/get-db @conn "tiny-launch")))