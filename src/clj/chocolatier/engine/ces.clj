(ns chocolatier.engine.ces
  "Macros for the Clojurescript side that requires the macros to be
   defined in the same namespace name as the cljs side.")

;; (defmacro defcomponent
;;   "Defines a component function with vname in the current namespace

;;    Example:
;;    (defcomponent c1 [] (println ))"
;;   [vname argslist & body]
;;   (let [args (drop-last 1 argslist)
;;         f (concat '(fn [& args]) body)
;;         opts (last argslist)]
;;     '(defn ~vname [~@args]
;;        ~@(mk-component-fn (keyword vname) f opts))))

(defn mk-system-fn
  "Takes a list of forms representing a function and returns a fn with the
   body wrapped"
  [args inner-fn-sym {:keys [component-id] :as opts}]
  (if component-id
    `(fn [state#]
       (let [entities# (entities-with-component state# ~component-id)
             component-fns# (get-component-fns state# ~component-id)]
         (~inner-fn-sym state# component-fns# entities#)))
    `(fn [state#] (~inner-fn-sym state#))))

(defmacro defsystem
  "Defines a component function with vname in the current namespace

   Example:
   (defsystem s1 {:component-id :c1}
     [state component-id]
     (assoc state :foo :bar))"
  [vname opts fn-args & body]
  (let [inner-fn-sym (gensym vname)
        sys-fn (mk-system-fn fn-args inner-fn-sym opts)]
    `(do
       (defn- ~inner-fn-sym [~@fn-args] ~@body)
       (def ~vname ~sys-fn)) ))
