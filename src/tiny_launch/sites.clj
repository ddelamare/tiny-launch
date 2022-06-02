(ns tiny-launch.sites
  (:require [hiccup.page :as p]
            [tiny-launch.layout :as l]
            [tiny-launch.services.site :as ss]
            [tiny-launch.services.db :as db]))

(defn site-list
  "Lists a set of sites. Takes parameters for type and page size and paging"
  [list-type pageSize pageable?]
  [:div.site-list
   [:div.list-box.striped (map (fn [kv] [:div.site-list-row (:label kv)]) (ss/get-site list-type))]])

(defn profile-template 
  [request]
  (l/layout request
    (p/html5
   [:head (prn request)
    (p/include-css "/css/site-profile.css")]
     (when-let [site (db/get-site (:site-id (:path-params request)))]
       [:div.content
        [:div.hero [:h1 (:label site)]]
        [:div.featured "This is what my site does and why you need it"]
        [:div.newest "See some picutes I took"]
        [:div.spiel "Doesn't this make you want to buy it?"]]))))

