(ns tiny-launch.layout
  (:require [hiccup.page :as p]))

(defn- header-bar
    "Renders the top navigation bar"
    [request]
    [:div.header
     [:div.logo "TinyLaunch"]
     [:span.nav-buttons
      [:ul [:li "Item 1"] [:li "Item 2"] [:li "Item 3"]]]])
  
(defn layout
  "Defines the global layouout"
  [request inner]
  (p/html5
   [:title "TinyLaunch"]
   [:body [:div (header-bar request)] inner]))

