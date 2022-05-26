(ns tiny-launch.home
    (:require [hiccup.core :as h]
              [tiny-launch.layout :as l]))

(defn home-template [request]
  (l/layout request (h/html [:div.content "Hello world"])))