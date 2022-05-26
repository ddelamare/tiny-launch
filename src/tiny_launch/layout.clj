(ns tiny-launch.layout
  (:require [hiccup.page :as p]))

(defn layout
  "Defines the global layouout"
  [request inner]
  (p/html5
   [:head 
    (p/include-css "css/site.css")]
   [:title "MicroLaunch"]
   [:body [:div.header inner]])) 
