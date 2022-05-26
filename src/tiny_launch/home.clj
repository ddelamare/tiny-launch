(ns tiny-launch.home
  (:require [hiccup.core :as h]
            [tiny-launch.layout :as l]))

(defn site-list
  "Lists a set of sites. Takes parameters for type and page size and paging"
  [list-type pageSize pageable?]
  [:div.site-list (str list-type pageSize pageable?)
   [:ol (repeat pageSize [:li.site-list-row "Row"])]])

(defn home-template [request]
  (l/layout request
    (h/html
      [:div.content
       [:div.welcome "Welcome"]
       [:div.featured (site-list :featured 10 false)]
       [:div.newest (site-list :newest 10 true)]
       [:div.spiel]])))

