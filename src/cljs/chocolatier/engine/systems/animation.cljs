(ns chocolatier.engine.systems.animation
  (:require [chocolatier.engine.ces :as ces])
  (:require-macros [chocolatier.engine.ces :refer [defsystem]]))

;; Maybe return a function that given a sprite and a frame number
;; changes the sprite viewport correctly

(defsystem animation-system {:component-id :animateable}
  [state fns entity-ids]
  (ces/iter-entities state fns entity-ids))
