(ns tiny-launch.home
  (:require [hiccup.page :as p]
            [hiccup.element :as e]
            [tiny-launch.layout :as l]
            [tiny-launch.services.site :as ss]
            [tiny-launch.services.user :as us]))

(defn favorite-icon
  "Renders a clickable? favorite icon for the site"
  [site clickable?]
  (let [favorited? (ss/site-favorited? site (us/get-current-user))
        icon-class (if (true? favorited?) "yes-favorite-icon" "no-favorite-icon")]
    [:span.favorite-icon [:i.fa-thumbs-up.fa-solid {:class icon-class}]])
  )

(defn site-list
  "Lists a set of sites. Takes parameters for type and page size and paging"
  [list-type pageSize pageable? request]
  [:div.site-list
   [:div.list-box.striped 
    (map (fn [kv] [:div.site-list-row
                   (e/link-to (@(:url-for request) :site-profile :params {:site-id (:_id kv)}) [:span (:label kv)]) (favorite-icon kv true)]) (ss/get-site list-type))]])

(defn home-template [request]
  (l/layout request
    (p/html5
   [:head
    (p/include-css "/css/home.css")]
     [:div.content
       [:div.welcome "Welcome"]
       [:div.featured "Come see the featured" (site-list :featured 10 false request)]
       [:div.newest "Come see the newest" (site-list :newest 10 true request)]
       [:div.spiel "This is why this site exists"]])))

