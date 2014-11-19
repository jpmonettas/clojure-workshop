(ns seat-picker.seats-test
  (:require [seat-picker.seats :refer :all]
            [midje.sweet :refer :all]))

(def seats-diagram ["...................."
                    "...................."
                    "..FFFFFFFFFFFFFFFF.."
                    "..FFBFFBFBBBBBFFFF.."
                    "..FFFFFFFFFFFFFFFF.."
                    "..FFFBBBBBFBBBBFFF.."
                    "..FFBBBBBBBBBBBFFF.."
                    "..FBBBBBBFFBBBBBFF.."
                    "..FFFBBBBBBBBBFFFF.."
                    "...................."
                    ])

(defn create-seats-from-line-row [row-n l]
  (map-indexed (fn [col status] {:row row-n
                                :col (inc col)
                                :status ({\. :unavailable
                                          \F :available
                                          \B :booked}
                                         status)})
               l))

(defn create-seats-from-diagram [d]
  (->> d
     (map-indexed (fn [row-n status-line]
                    (create-seats-from-line-row (inc row-n) status-line)))
     (reduce concat)))



(defn get-seats-sets [partitions]
  (map (fn [p]
         (->> p
            (map :col)
            (into #{})))
       partitions))

(fact "Should return consecutive n seats in a row"
      (let [seats (create-seats-from-line-row 1 "..FFFBBBFF.")]
        
        (-> (find-row-consecutive-seats 3 seats)
           (get-seats-sets))
        => [#{3 4 5}]

        (-> (find-row-consecutive-seats 2 seats)
           (get-seats-sets))
        => [#{3 4} #{4 5} #{9 10}]))

(fact "Should return consecutive n seats in a diagram"
      (let [seats (create-seats-from-diagram ["..FFFBBBFF."
                                              "..BBFFFBBB."])]

        (-> (find-room-consecutive-seats 3 seats)
           (get-seats-sets))
        => [#{3 4 5} #{5 6 7}]))

(fact "Should check consecutives"
      (consecutives? [{:col 1} {:col 2} {:col 3}]) => true
      (consecutives? [{:col 1} {:col 2} {:col 4}]) => false)
      
      
