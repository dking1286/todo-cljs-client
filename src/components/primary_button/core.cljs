(ns components.primary-button.core
  (:require [cljs-react-material-ui.reagent :as mui]
            [components.core :refer-macros [defcomponent]]))

(defcomponent primary-button
  "View component representing a button with
the primary color scheme of the MaterialUI theme."
  [props & children]
  [mui/raised-button
   (-> props
       (assoc :primary true)
       (assoc-in [:style :color] "white"))
   children])
