(ns chocolatier.engine.systems.debug
  (:require [chocolatier.engine.ces :as ces])
  (:require-macros [chocolatier.engine.ces :refer [defsystem]]))

(defsystem debug-collision-system {:component-id :collision-debuggable}
  [state fns entity-ids]
  ;; Adds debug information for any debuggable entity
  (ces/iter-entities state fns entity-ids))
