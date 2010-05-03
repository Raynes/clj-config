(ns clj-config.core
  (:use [clojure.contrib.io :only [spit slurp*]])
  (:import java.io.File))

(defn format-config 
  "This function formats your config like you'd expect to see a Clojure map
  in normal code."
  [newc]
  (.replaceAll newc ",s*(?=([^\"]*\"[^\"]*\")*[^\"]*$)" "\n"))

(defn read-config 
  "Reads the config file specified. If :as-string is true, returns the string slurped
  from the configuration file, otherwise, returns the data read-string'd."
  [file & {:keys [string?] :or {as-string? false}}]
  (let [cfile (slurp file)]
    (if string? cfile (read-string cfile))))

(defn write-config 
  "Writes the data to the configuration file. If format? is true, formats the file with
  format-config."
  [new-info file & {:keys [format?] :or {format? false}}] 
  (spit file (if format? (format-config new-info) new-info)))

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