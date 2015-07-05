(ns chocolatier.engine.systems.user-input
  "System for reacting to user input"
  (:require [chocolatier.utils.logging :as log]
            [chocolatier.engine.ces :as ces])
  (:require-macros [chocolatier.engine.ces :refer [defsystem]]))


(defsystem user-input-system {:component-id :controllable}
  ;; Call all the functions for reacting to user input
  [state fns entity-ids]
  (ces/iter-entities state fns entity-ids))
