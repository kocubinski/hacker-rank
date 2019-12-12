(ns dev
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application.

  Call `(reset)` to reload modified code and (re)start the system.

  The system under development is `system`, referred from
  `com.stuartsierra.component.repl/system`.

  See also https://github.com/stuartsierra/component.repl"
  (:require
   [clojure.java.io :as io]
   [clojure.java.javadoc :refer [javadoc]]
   [clojure.pprint :refer [pprint]]
   [clojure.reflect :refer [reflect]]
   [clojure.repl :refer [apropos dir doc find-doc pst source]]
   [clojure.set :as set]
   [clojure.string :as string]
   [clojure.test :as test]
   [clojure.tools.namespace.repl :refer [refresh refresh-all clear]]
   [com.stuartsierra.component :as component]
   [com.stuartsierra.component.repl :refer [reset set-init start stop system]]
   [hacker-rank.sudoku :as sudoku]))

;; Do not try to load source code from 'resources' directory
(clojure.tools.namespace.repl/set-refresh-dirs "dev" "src" "test")

(defn dev-system
  "Constructs a system map suitable for interactive development."
  []
  (component/system-map
   ;; TODO
   ))

(set-init (fn [_] (dev-system)))

(def sample-sudoku-input
  "2
0 0 0 0 0 0 0 0 0
0 0 8 0 0 0 0 4 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 6 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
2 0 0 0 0 0 0 0 0
0 0 0 0 0 0 2 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 8 0 0 0 0 4 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 6 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
2 0 0 0 0 0 0 0 0
0 0 0 0 0 0 2 0 0
0 0 0 0 0 0 0 0 0")

(def a-board
  (vector 0, 0, 8, 3, 4, 2, 9, 0, 0,
          0, 0, 9, 0, 0, 0, 7, 0, 0,
          4, 0, 0, 0, 0, 0, 0, 0, 3,
          0, 0, 6, 4, 7, 3, 2, 0, 0,
          0, 3, 0, 0, 0, 0, 0, 1, 0,
          0, 0, 2, 8, 5, 1, 6, 0, 0,
          7, 0, 0, 0, 0, 0, 0, 0, 8,
          0, 0, 4, 0, 0, 0, 1, 0, 0,
          0, 0, 3, 6, 9, 7, 5, 0, 0))

;; A -> 0  1  2
;;      9  10 11
;;      18 19 20
;;
;; B -> 3  4  5
;;      12 13 14
;;      21 22 23

;; 0 -> A
;; 1 -> A
;; 2 -> A
;; 3 -> B
;; ..
;; 9 -> A

