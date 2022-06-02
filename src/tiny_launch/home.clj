(ns tiny-launch.home
  (:require [hiccup.page :as p]
            [hiccup.element :as e]
            [tiny-launch.layout :as l]
            [tiny-launch.services.site :as ss]))

(defn site-list
  "Lists a set of sites. Takes parameters for type and page size and paging"
  [list-type pageSize pageable?]
  [:div.site-list
   [:div.list-box.striped (map (fn [kv] (e/link-to (l/route-builder :site-profile :params {:site-id (:_id kv)}) [:div.site-list-row (:label kv)])) (ss/get-site list-type))]])

(defn home-template [request]
  (l/layout request
    (p/html5
   [:head
    (p/include-css "/css/home.css")]
     [:div.content
       [:div.welcome "Welcome"]
       [:div.featured "Come see the featured" (site-list :featured 10 false)]
       [:div.newest "Come see the newest" (site-list :newest 10 true)]
       [:div.spiel "This is why this site exists"]])))

