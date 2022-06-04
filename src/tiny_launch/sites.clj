(ns tiny-launch.sites
  (:require [hiccup.page :as p]
            [hiccup.element :as e]
            [hiccup.form :as f]
            [tiny-launch.layout :as l]
            [tiny-launch.services.site :as ss]
            [tiny-launch.services.db :as db]))

(defn site-list
  "Lists a set of sites. Takes parameters for type and page size and paging"
  [list-type pageSize pageable?]
  [:div.site-list
   [:div.list-box.striped (map (fn [kv] [:div.site-list-row (:label kv)]) (ss/get-site list-type))]])

(defn form-field
  "Takes a label and a model and generates an input or static text"
  [label site isEdit]
  (if isEdit
    (f/text-field label ((keyword label) site))
    ((keyword label) site)))

(defn profile-template
  [request]
  (l/layout request
            (p/html5
             [:head
              (p/include-css "/css/site-profile.css")]
             (when-let [site (db/get-site (:site-id (:path-params request)))]
               (let [isEdit (= "1" (:edit (:params request)))]
                 [:div.content
                  (if isEdit
                    [:div [:button "Save"] [:button (e/link-to (@(:url-for request) :site-profile :params {}) "Cancel")]]
                    [:button (e/link-to (@(:url-for request) :site-profile :params {:edit 1}) "Edit")])
                  [:div.hero [:h1 (form-field :label site isEdit)]]
                  [:div.featured (:description site)]
                  [:div.newest "See some pictures I took"]
                  [:div.spiel "Doesn't this make you want to buy it?" (str request)]])))))

