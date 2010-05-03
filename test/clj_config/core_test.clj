(ns clj-config.core-test
  (:use [clj-config.core] :reload-all)
  (:use [clojure.test]))

(def data
     {:a "b"
      :b "c"
      :x 3
      :z {:omg :hai}})

(def new-data (merge data {:x 5}))

(def str-data (str data))

(deftest read-config ;; FIXME: write
  (is (= (read-config "configtestfile") data)
      (= (read-config "configtestfile" :string? true) str-data)))

(deftest write-config
  (is (= (do (write-config new-data "configtestfile") (read-config "configtestfile")) new-data)))

(deftest get-key
  (is (= (get-key :x "configtestfile") 5)))

(deftest remove-key
  (is (= (do (remove-key :x "configtestfile") (read-config "configtestfile")) (dissoc new-data :x))))

(deftest set-key
  (is (= (do (set-key :x 10 "configtestfile") (read-config "configtestfile")) (assoc new-data :x 10))))