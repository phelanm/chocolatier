(ns chocolatier.engine.systems.movement
  "System for handling entity movements"
  (:require [chocolatier.engine.ces :as ces])
  (:require-macros [chocolatier.engine.ces :refer [defsystem]]))


(defsystem movement-system {:component-id :moveable}
  [state fns entity-ids]
  (ces/iter-entities state fns entity-ids))
