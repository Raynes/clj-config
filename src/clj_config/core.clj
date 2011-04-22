(ns clj-config.core
  (:use [clojure.contrib.io :only [slurp*]])
  (:import java.io.File))

(defn format-config 
  "This function formats your config like you'd expect to see a Clojure map
  in normal code."
  [newc]
  (.replaceAll (str newc) ",s*(?=([^\"]*\"[^\"]*\")*[^\"]*$)" "\n"))

(defn read-config 
  "Reads the configuration file, calling read-string on it."
  [file] (let [cfile (slurp file)] (read-string cfile)))

(defn write-config 
  "Writes the data to the configuration file. If format? is true, formats the file with
  format-config."
  [new-info file & {:keys [format?] :or {format? false}}] 
  (spit file (if format? (format-config new-info) (str new-info))))

(defn get-key 
  "Get's a single key from the config file."
  [key file]
  (-> key ((read-config file))))

(defn remove-key 
  "Removes a single key from the configuration file."
  [key file & {:keys [format?] :or {format? false}}]
  (let [config (-> (read-config file) (dissoc key) str)]
    (write-config config file :format format?)))

(defn set-key 
  "Sets the value of a key in a configuration file."
  [key nval file & {:keys [format?] :or {format? false}}]
  (-> (read-config file) (assoc key nval) (write-config file)))