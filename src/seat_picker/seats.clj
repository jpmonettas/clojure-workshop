(ns seat-picker.seats
  (:require [clojure.math.combinatorics :refer [combinations]]
            [clojure.set :as set]))

(defn consecutives? [seats]
  (let [nums (map :col seats)]
    (empty? (set/difference (into #{} (range (reduce min nums)
                                             (inc (reduce max nums))))
                            (into #{} nums)))))

(defn consecutive-combinations [n consecutives]
  (->> (combinations consecutives n)
     (filter consecutives?)))

(defn find-row-consecutive-seats [n row-seats]
  (->> row-seats
     (sort-by :col)
     (partition-by :status)
     (filter (fn [part]
               (and (= (-> part
                        first
                        :status)
                     :available)
                  (>= (count part) n))))
     (mapcat (partial consecutive-combinations n))))

(defn find-room-consecutive-seats [n seats]
  (->> seats
     (group-by :row)
     (vals)
     (mapcat (partial find-row-consecutive-seats n))))

(defn seat-distance [x y seat]
  (let [sx (:col seat)
        sy (:row seat)]
    (Math/sqrt (+ (Math/abs (- sx x)) (Math/abs (- sy y))))))

(defn score-consecutives [best-x best-y consecutives-list]
  (map (fn [consecutives]
         {:seats consecutives
          :score (->> consecutives
                    (map (partial seat-distance best-x best-y))
                    (reduce min))})
       consecutives-list))

(defn find-ordered-seats-options [room n]
  ;; TODO Choose best-x and best-y
  (let [best-x 0
        best-y 0]
    (->> room
       (find-room-consecutive-seats n)
       (score-consecutives best-x best-y)
       (sort-by :score))))
