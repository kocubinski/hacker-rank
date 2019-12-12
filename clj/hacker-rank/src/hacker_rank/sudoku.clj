(ns hacker-rank.sudoku
  "Solution to https://www.hackerrank.com/challenges/sudoku/problem"
  )

(def bd-sz 9)

(defn ingest
  [s]
  (->> (read-string (str "[" s "]"))
       (partition 81)
       (map vec)))

(defn next-moves
  [board i n]
  (let [row-i (Math/floorDiv i bd-sz)
        row (keep-indexed (fn [j n]
                            (when (and (>= j (* bd-sz row-i))
                                       (< j (+ bd-sz (* bd-sz row-i))))
                              n))
                          board)
        col-i (mod i bd-sz)
        column (keep-indexed (fn [j n]
                               (when (= (mod j bd-sz) col-i)
                                 n))
                             board)
        cell (keep-indexed (fn [j n]
                             (when (= (mod j 3) (mod i 3))
                               n))
                           board)]
    cell))

(defn solve
  [board]
  (->> board
       (map-indexed (partial next-moves board))
       ))

