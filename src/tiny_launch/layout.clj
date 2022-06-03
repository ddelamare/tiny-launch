(ns tiny-launch.layout
  (:require [hiccup.page :as p]
            [hiccup.element :as e]))

(defn- header-bar
    "Renders the top navigation bar"
    [request]
    [:div.header
     [:div (e/link-to (@(:url-for request) :home) [:div.logo  "TinyLaunch"]) ]
     [:span.nav-buttons
      [:ul [:li "Item 1"] [:li "Item 2"] [:li "Item 3"]]]])
  
(defn layout
  "Defines the global layouout"
  [request inner]
  (p/html5
   [:title "TinyLaunch"]
   [:head (p/include-css "/css/header.css") (p/include-css "/css/root.css")]
   [:body [:div (header-bar request)] inner]))

