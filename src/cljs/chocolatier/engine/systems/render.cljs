(ns chocolatier.engine.systems.render
  "System for rendering entities"
  (:require [chocolatier.utils.logging :as log]
            [chocolatier.engine.ces :as ces]
            [chocolatier.engine.pixi :as pixi])
  (:require-macros [chocolatier.engine.ces :refer [defsystem]]))


(defsystem render-system {}
  ;; Renders all the changes to sprites and other Pixi objects
  [state]
  (let [{:keys [renderer stage]} (-> state :game :rendering-engine)]
    (pixi/render! renderer stage)
    state))
