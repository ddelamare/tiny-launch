(ns tiny-launch.home
    (:require [hiccup.core :as h])
)

(defn home-template [request] 
    (h/html [:div "Hello deary2"]))