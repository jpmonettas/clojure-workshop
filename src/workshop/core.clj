(ns workshop.core
  (:use [clojure.string :only [capitalize]]
        [clojure.pprint :only [pprint]])
  (:require [clojure.data.json :as json]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; LISP
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Literals
;;;;;;;;;;;

5

3/4

0.3

"un string"

\j

'pepe

pepe

:pepe

'+

+

'(1 2 3 4)

'(1 + ("hola" *) 5/3)

[1 2 3 4]

;; Evaluation Rule For Lists
;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(+ 1 2 3 4)

(+ 3/4 3/4)

(count "hola")

(inc 5)

(inc (count "hola"))

(map inc '(1 2 3 4))

(map count '((1 2 3 4)
             "un string"
             [3/5 "pepe"]))

(sort '(5 3 8 9 1))
(sort < '(5 3 8 9 1))
(sort > '(5 3 8 9 1))

;; Special Forms
;;;;;;;;;;;;;;;;

;;def
(def pepe 5)

;;if
(if 1 2 3)

(if nil 2 3)

(if (= pepe 5)
  "Pepe es 5"
  "Pepe NO es 5")

;;let
(let [a 5
      b (+ a 8)]
  (+ a b))

;;fn
((fn [a b] (+ a b)) 5 6)

;;do
(do 1 2 3 4)

;;macros
(defmacro five+six [] '(+ 5 6))

(defmacro cuando [cond & body-forms]
  `(if ~cond (do ~@body-forms)))

;; (function mi-suma [a b]
;;           (+ a b))

;;-->

;; (def mi-suma (fn [a b]
;;                (+ a b)))

(defmacro function [name param-vect & body-forms]
  `(def ~name (fn ~param-vect ~@body-forms)))

(defmacro gen-prints [something n]
  `(do
     ~@(for [x (range n)]
         (list 'print something))))

;; Macroexpand
(->> '(-1 2 -3 4)
     (map inc)
     (filter pos?)
     (reduce +))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Clojure
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; More Literals


{:nombre "Pepe" :edad 30}

{[1 2] "primero"
 (3 4 [5]) "segundo"
 (3 4 [6]) "tercero"}

#{1 2 3 4}

;; defn
(defn ! [n]
  (if (zero? n)
    1
    (* n (! (dec n)))))

(defn with-multiple-arity
  ([x] (dec x))
  ([x y] (+ x y)))

;; Lazyness and infinite collections
(->> (range 500000000)
     (map (fn [n] (Thread/sleep 100) n))
     (take 32)
     (println)
     (time))

;; import java.util.*;

;; public class JavaTest {
;;     public static String cleanNames(List<String> listOfNames) {
;;         StringBuilder result = new StringBuilder();
;;         for(String name : listOfNames) {
;;             if (name.length() > 1) {
;;                 result.append(capitalizeString(name)).append(",");
;;             }
;;         }
;;         return result.substring(0, result.length() - 1).toString();
;;     }

;;     public static String capitalizeString(String s) {
;;         return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
;;     }

;;     public static void main (String [] args){
;;         List <String> l=new ArrayList<String>(Arrays.asList("alejandro","gus","m","fede","g","pepe"));
;;         System.out.println(cleanNames(l));
;;     }
;; }



;; VS


(->>  '("alejandro" "gus" "m" "fede" "g" "pepe")
      (filter #(> (count %) 1))
      (map capitalize)
      (interpose ",")
      (reduce str))

;; For parallel CHANGE  TO

(time
 (->>  '("alejandro" "gus" "m" "fede" "g" "pepe")
       (filter #(> (count %) 1))
       (pmap #(do (Thread/sleep 1000) (capitalize %)))
       (interpose ",")
       (reduce str)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(->
 (slurp "http://api.openweathermap.org/data/2.5/weather?q=Montevideo,uy&units=metric")
 (json/read-str)
 (get-in ["main" "temp"]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; Destructuring

(let [[a b & resto] '(1 2 3 4)]
  {:a a
   :b b
   :resto resto})

(defn hello [{a :nombre b :apellido} age]
  (str "Hello " a " " b " of " age))

;; Java interop
;;;;;;;;;;;;;;;

(.toUpperCase "fred")

(System/getProperty "java.vm.version")

Math/PI

(-> "esto-es-una-prueba"
    (.split "-")
    (rest))


;; Testing
;;;;;;;;;

;;(use 'clojure.test)

(defn ! [n]
  (if (zero? n)
    1
    (* n (! (dec n)))))

(deftest test-! []
  (is (= (! 2) 2))
  (is (= (! 5) 120)))

(defn !
  "Factorial"
  {:test (fn []
           (is (= (! 2) 2))
           (is (= (! 5) 120)))}
  [n]
  (if (zero? n)
    1
    (* n (! (dec n)))))
