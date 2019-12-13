(ns hacker-rank.sudoku
  "Solution to https://www.hackerrank.com/challenges/sudoku/problem"
  (:require [clojure.set :as set]))

(def bd-sz 9)

(defn ingest
  [s]
  (->> (read-string (str "[" s "]"))
       (partition 81)
       (map vec)))

(defn sub-grid
  [x y]
  (cond
    (> x 5) (cond
              (> y 5) 8
              (> y 2) 7
              :else 6)
    (> x 2) (cond
              (> y 5) 5
              (> y 2) 4
              :else 3)
    :else (cond
            (> y 5) 2
            (> y 2) 1
            :else 0)))

(defn subgrid
  [x y]
  (+ (- x (mod x 3))
     (Math/floorDiv y 3)))

(defn next-moves
  [board]
  (->> board
       (keep-indexed
        (fn [i n]
          (when (= 0 n)
            (let [x (Math/floorDiv i bd-sz)
                  row (keep-indexed (fn [j n]
                                      (when (= (Math/floorDiv j bd-sz) x)
                                        n))
                                    board)
                  y (mod i bd-sz)
                  column (keep-indexed (fn [j n]
                                         (when (= (mod j bd-sz) y)
                                           n))
                                       board)
                  sg (subgrid x y)
                  cell (keep-indexed (fn [j n]
                                       (when (= (subgrid (Math/floorDiv j bd-sz)
                                                         (mod j bd-sz))
                                                sg)
                                         n))
                                     board)
                  taken (->> (concat row column cell)
                             (remove (partial = 0))
                             (into #{}))]
              (->> (range 1 10)
                   (remove taken)
                   (map (partial assoc board i)))))))
       (first)))

(defn solve
  [board]
  )

