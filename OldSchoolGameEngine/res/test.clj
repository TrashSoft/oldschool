(def gameCallback (reify trash.oldschool.clojure.ClojureWrapper
(run [this t]
  "Hello World!"
)))

(.start (new trash.oldschool.clojure.ClojureRunner) gameCallback) 